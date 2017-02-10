package cn.zhangxd.platform.mobile.client.controller;

import cn.zhangxd.platform.mobile.client.common.controller.BaseController;
import cn.zhangxd.platform.mobile.client.constant.Message;
import cn.zhangxd.platform.mobile.client.constant.ReturnCode;
import cn.zhangxd.platform.system.api.service.ICurrencyRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Currency rate controller.
 *
 * @author zhangxd
 */
@RestController
@RequestMapping("/{version}/currency")
@Api(tags = "汇率")
public class CurrencyRateController extends BaseController {

    /**
     * 汇率服务
     */
    @Autowired
    private ICurrencyRateService currencyRateService;

    /**
     * Gets currency rate.
     *
     * @param version the version
     * @return the currency rate
     */
    @GetMapping(value = "/rate/all", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取USD对应汇率")
    public Map<String, Object> getCurrencyRate(
        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) {
        Map<String, String> rate = currencyRateService.getRate();
        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, rate);
        return message;
    }

}
