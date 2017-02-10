package cn.zhangxd.platform.system.provider.serviceimpl;

import cn.zhangxd.platform.system.api.service.ICurrencyRateService;
import cn.zhangxd.platform.system.provider.thirdapi.YahooRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 汇率服务
 *
 * @author zhangxd
 */
@Service
@Transactional(readOnly = true)
public class CurrencyRateService implements ICurrencyRateService {

    /**
     * yahoo 汇率服务
     */
    @Autowired
    private YahooRateService yahooRateService;

    @Override
    public Map<String, String> getRate() {
        return yahooRateService.get();
    }
}
