package cn.zhangxd.platform.mobile.client.controller;

import cn.zhangxd.platform.common.upload.util.FileIndex;
import cn.zhangxd.platform.common.upload.util.FileManager;
import cn.zhangxd.platform.common.web.util.WebUtils;
import cn.zhangxd.platform.mobile.client.common.controller.BaseController;
import cn.zhangxd.platform.mobile.client.constant.Message;
import cn.zhangxd.platform.mobile.client.constant.ReturnCode;
import cn.zhangxd.platform.mobile.client.security.model.AuthUser;
import cn.zhangxd.platform.system.api.entity.TripUser;
import cn.zhangxd.platform.system.api.exception.InvalidCaptchaException;
import cn.zhangxd.platform.system.api.service.ICaptchaService;
import cn.zhangxd.platform.system.api.service.ITripUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author zhangxd
 */
@Validated
@RestController
@RequestMapping("/{version}/user")
@Api(tags = "用户管理")
public class UserController extends BaseController {
    /**
     * 用户服务
     */
    @Autowired
    private ITripUserService tripUserService;
    /**
     * 验证码服务
     */
    @Autowired
    private ICaptchaService captchaService;
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 文件管理
     */
    @Autowired
    private FileManager fileManager;

    /**
     * Registry user map.
     *
     * @param version    the version
     * @param password   the password
     * @param mobile     the mobile
     * @param captcha    the captcha
     * @param invitation the invitation
     * @return the map
     * @throws InvalidCaptchaException the invalid captcha exception
     */
    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "注册用户")
    public Map<String, Object> registryUser(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @Length(min = 6, max = 20, message = "密码长度为6到20")
        @ApiParam(required = true, value = "密码") @RequestParam("password") String password,
        @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
        @ApiParam(required = true, value = "手机号") @RequestParam("mobile") String mobile,
        @Pattern(regexp = "\\d{6}", message = "验证码为6位数字")
        @ApiParam(required = true, value = "短信验证码") @RequestParam("captcha") String captcha
    ) throws InvalidCaptchaException {
        //校验验证码
        captchaService.validCaptcha(mobile, captcha);

        TripUser user = new TripUser();
        user.setMobile(mobile);
        user.setPassword(passwordEncoder.encode(password));
        // 注册
        tripUserService.registryUser(mobile, passwordEncoder.encode(password));

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        return message;
    }

    /**
     * Gets user.
     *
     * @param version the version
     * @return the user
     */
    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> getUser(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) {
        AuthUser user = WebUtils.getCurrentUser();

        TripUser tripUser = tripUserService.get(user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("mobile", tripUser.getMobile()); //手机号
        result.put("photo", fileManager.getFileUrlByRealPath(tripUser.getPhoto())); //头像
        result.put("nickname", tripUser.getNickname()); //昵称
        result.put("gender", tripUser.getGender()); //性别
        result.put("age", tripUser.getAge()); //年龄

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, result);
        return message;
    }

    /**
     * Update user map.
     *
     * @param version  the version
     * @param nickname the nickname
     * @param gender   the gender
     * @param age      the age
     * @return the map
     */
    @PutMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> updateUser(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @Length(max = 10, message = "昵称最大长度为10")
        @ApiParam(required = true, value = "昵称") @RequestParam("nickname") String nickname,
        @Pattern(regexp = "0|1|2", message = "性别类型错误")
        @ApiParam(required = true, value = "性别 [未知,男,女]", allowableValues = "0,1,2") @RequestParam("gender") String gender,
        @ApiParam(required = true, value = "年龄") @RequestParam("age") String age
    ) {
        AuthUser user = WebUtils.getCurrentUser();

        TripUser tripUser = new TripUser(user.getId());
        tripUser.setNickname(nickname);
        tripUser.setGender(gender);
        tripUser.setAge(age);
        tripUserService.updateInfo(tripUser);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        return message;
    }

    /**
     * Forget password map.
     *
     * @param version  the version
     * @param password the password
     * @param mobile   the mobile
     * @param captcha  the captcha
     * @return the map
     * @throws InvalidCaptchaException the invalid captcha exception
     */
    @PutMapping(value = "/password", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "忘记密码")
    public Map<String, Object> forgetPassword(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @Length(min = 6, max = 20, message = "密码长度为6到20")
        @ApiParam(required = true, value = "密码") @RequestParam("password") String password,
        @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
        @ApiParam(required = true, value = "手机号") @RequestParam("mobile") String mobile,
        @Pattern(regexp = "\\d{6}", message = "验证码为6位数字")
        @ApiParam(required = true, value = "验证码") @RequestParam("captcha") String captcha
    ) throws InvalidCaptchaException {
        //校验验证码
        captchaService.validCaptcha(mobile, captcha);

        // 忘记密码
        tripUserService.updatePasswordByMobile(mobile, passwordEncoder.encode(password));

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        return message;
    }

    /**
     * Upload photo map.
     *
     * @param version the version
     * @param photo   the photo
     * @return the map
     */
    @PostMapping(value = "/photo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "上传头像")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> uploadPhoto(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @ApiParam(required = true, value = "头像") @RequestParam(value = "photo") MultipartFile photo
    ) {
        String path = "";
        if (photo != null && photo.getSize() > 0) {
            AuthUser user = WebUtils.getCurrentUser();
            FileIndex ufi = WebUtils.buildFileIndex(photo, TripUser.IMAGE_FOLDER);
            ufi = fileManager.save(ufi);
            path = ufi.getPath();
            tripUserService.updatePhotoByUserId(user.getId(), path);
        }

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, Collections.singletonMap("path", fileManager.getFileUrlByRealPath(path)));
        return message;
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(InvalidCaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(InvalidCaptchaException ex) {
        return makeErrorMessage(ReturnCode.INVALID_CAPTCHA, "Invalid Captcha", ex.getMessage());
    }

}
