package cn.zhangxd.platform.mobile.client.common.controller;

import cn.zhangxd.platform.mobile.client.constant.Message;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ERROR处理控制器
 *
 * @author zhangxd
 */
@Controller
@RequestMapping("/error")
public class CustomErrorController extends BasicErrorController {

    public CustomErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping
    @ResponseBody
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
            isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = this.getStatus(request);
        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, Message.STATUS_CODE_MAP.get(status.value()));
        message.put(Message.RETURN_FIELD_ERROR, body.get("error"));
        message.put(Message.RETURN_FIELD_ERROR_DESC, status.value() == 500 ? "服务端错误" : body.get("message"));
        return new ResponseEntity<>(message, status);
    }

}
