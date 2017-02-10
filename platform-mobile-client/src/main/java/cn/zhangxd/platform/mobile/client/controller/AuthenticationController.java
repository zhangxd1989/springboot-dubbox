package cn.zhangxd.platform.mobile.client.controller;

import cn.zhangxd.platform.common.web.security.AuthenticationTokenFilter;
import cn.zhangxd.platform.mobile.client.common.controller.BaseController;
import cn.zhangxd.platform.mobile.client.security.utils.TokenUtil;
import cn.zhangxd.platform.mobile.client.constant.Message;
import cn.zhangxd.platform.mobile.client.constant.ReturnCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Authentication controller.
 *
 * @author zhangxd
 */
@RestController
@RequestMapping("/{version}/auth")
@Api(tags = "权限管理")
public class AuthenticationController extends BaseController {

    /**
     * 权限管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * Token工具类
     */
    @Autowired
    private TokenUtil jwtTokenUtil;

    /**
     * Create authentication token map.
     *
     * @param version  the version
     * @param username the username
     * @param password the password
     * @return the map
     */
    @PostMapping(value = "/token", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取token")
    public Map<String, Object> createAuthenticationToken(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        @ApiParam(required = true, value = "用户名") @RequestParam("username") String username,
        @ApiParam(required = true, value = "密码") @RequestParam("password") String password
    ) {

        //完成授权
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails); //生成Token

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, tokenMap);

        return message;
    }

    /**
     * Refresh and get authentication token map.
     *
     * @param version the version
     * @param request the request
     * @return the map
     */
    @GetMapping(value = "/refresh", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "刷新token")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> refreshAndGetAuthenticationToken(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        //重新生成Token
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String refreshedToken = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", refreshedToken);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, tokenMap);

        return message;
    }

    /**
     * Delete authentication token map.
     *
     * @param version the version
     * @param request the request
     * @return the map
     */
    @DeleteMapping(value = "/token", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "清空token")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> deleteAuthenticationToken(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
        HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        //移除token
        jwtTokenUtil.removeToken(token);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);

        return message;
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusinessException(BadCredentialsException ex) {
        //用户名或密码错误
        return makeErrorMessage(ReturnCode.INVALID_GRANT, "Bad credentials", ex.getMessage());
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleBusinessException(DisabledException ex) {
        //用户被停用
        return makeErrorMessage(ReturnCode.DISABLED_USER, "User Disabled", ex.getMessage());
    }

}
