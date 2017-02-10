package cn.zhangxd.platform.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;


/**
 * Web层面的工具类.
 *
 * @author zhangxd
 */
public final class WebHelper {

    private WebHelper() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 获得用户远程地址
     *
     * @param request the request
     * @return the remote addr
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringHelper.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        if (StringHelper.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        if (StringHelper.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 将request请求中的参数及值转成一个Map格式.
     *
     * @param request HttpServletRequest
     * @return request中的请求及参数 request map
     */
    public static Dto getRequestMap(HttpServletRequest request) {
        Dto dto = new BaseDto();
        Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = Objects.toString(enumeration.nextElement());
            String[] values = request.getParameterValues(name);

            Object val;
            if (values.length == 1) {
                val = values[0];
            } else {
                val = Arrays.asList(values);
            }
            dto.put(name, val);
        }
        return dto;
    }

    /**
     * 获取访问路径
     *
     * @param request HttpServletRequest
     * @return 访问路径 base url
     */
    public static String getBaseURL(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort() + path + "/";
    }

}