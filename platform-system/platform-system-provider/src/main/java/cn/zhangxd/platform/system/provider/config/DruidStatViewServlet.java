package cn.zhangxd.platform.system.provider.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * The type Druid stat view servlet.
 *
 * @author zhangxd
 */
@WebServlet(urlPatterns = "/druid/*",
    initParams = {
//            @WebInitParam(name="allow",value="192.168.199.227,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
//            @WebInitParam(name="deny",value="192.168.199.237"),// IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
        @WebInitParam(name = "loginPassword", value = "admin"),// 密码
        @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
    })
public class DruidStatViewServlet extends StatViewServlet {

}