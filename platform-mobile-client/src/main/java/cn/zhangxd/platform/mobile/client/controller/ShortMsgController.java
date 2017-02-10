package cn.zhangxd.platform.mobile.client.controller;

import cn.zhangxd.platform.mobile.client.common.annotation.RequestLimit;
import cn.zhangxd.platform.mobile.client.common.controller.BaseController;
import cn.zhangxd.platform.mobile.client.constant.Message;
import cn.zhangxd.platform.mobile.client.constant.ReturnCode;
import cn.zhangxd.platform.system.api.exception.IllegalMobileException;
import cn.zhangxd.platform.system.api.exception.SmsTooMuchException;
import cn.zhangxd.platform.system.api.exception.UserExistException;
import cn.zhangxd.platform.system.api.exception.UserNotExistException;
import cn.zhangxd.platform.system.api.exception.base.BusinessException;
import cn.zhangxd.platform.system.api.service.ICaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Short msg controller.
 *
 * @author zhangxd
 */
@Validated
@RestController
@RequestMapping("/{version}/sms")
@Api(tags = "短信")
public class ShortMsgController extends BaseController {
    /**
     * 注册
     */
    private static final String TYPE_REGISTRY = "registry";
    /**
     * 忘记密码
     */
    private static final String TYPE_FORGET = "forget";
    /**
     * 验证码服务
     */
    @Autowired
    private ICaptchaService captchaService;

    /**
     * Send captcha map.
     *
     * @param request the request
     * @param version the version
     * @param type    the type
     * @param mobile  the mobile
     * @return the map
     * @throws BusinessException the business exception
     */
    @RequestLimit(count = 10)
    @PostMapping(value = "/captcha", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取短信验证码")
    public Map<String, Object> sendCaptcha(
        HttpServletRequest request,
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @Pattern(regexp = TYPE_REGISTRY + "|" + TYPE_FORGET, message = "注册类型错误")
        @ApiParam(required = true, value = "验证码类型 [注册,忘记密码]", allowableValues = TYPE_REGISTRY + "," + TYPE_FORGET) @RequestParam("type") String type,
        @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
        @ApiParam(required = true, value = "手机号") @RequestParam("mobile") String mobile
    ) throws BusinessException {
        if (TYPE_REGISTRY.equals(type)) {
            captchaService.sendCaptcha4Registry(mobile);
        } else if (TYPE_FORGET.equals(type)) {
            captchaService.sendCaptcha4Forget(mobile);
        }
        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        return message;
    }

    /**
     * Handle user exist exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleUserExistException(UserExistException ex) {
        return makeErrorMessage(ReturnCode.USER_EXIST, "User Exist", ex.getMessage());
    }

    /**
     * Handle user not exist exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
        return makeErrorMessage(ReturnCode.USER_NOT_EXIST, "User Not Exist", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(SmsTooMuchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleSmsTooMuchException(SmsTooMuchException ex) {
        return makeErrorMessage(ReturnCode.SMS_TOO_MUCH, "SMS Too Much", ex.getMessage());
    }

    /**
     * Handle illegal mobile exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(IllegalMobileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalMobileException(IllegalMobileException ex) {
        return makeErrorMessage(ReturnCode.INVALID_FIELD, "Illegal Mobile", ex.getMessage());
    }

}
