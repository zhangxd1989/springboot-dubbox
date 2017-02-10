package cn.zhangxd.platform.system.provider.thirdapi;

import com.google.gson.Gson;
import cn.zhangxd.platform.common.redis.RedisRepository;
import cn.zhangxd.platform.common.utils.StringHelper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Yahoo汇率服务
 *
 * @author zhangxd
 */
@Service
public class YahooRateService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(YahooRateService.class);

    /**
     * redis repository
     */
    @Autowired
    private RedisRepository redisRepository;
    /**
     * Yahoo汇率接口地址
     */
    private static final String YAHOO_RATE_URL = "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json";
    /**
     * 缓存前缀
     */
    private static final String REDIS_PREFIX = "yahoo_rate";
    /**
     * 访问超时时间
     */
    private static final int READ_TIMEOUT = 180;

    /**
     * Request.
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void request() {

        OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build();

        HttpUrl getUrl = HttpUrl.parse(YAHOO_RATE_URL).newBuilder()
            .build();
        LOGGER.info("调用雅虎汇率接口。");
        Request request = new Request.Builder()
            .url(getUrl)
            .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String message = response.body().string();
                YahooRateBean rateBean = new Gson().fromJson(message, YahooRateBean.class);
                Map<String, String> usdRateMap = new HashMap<>();
                rateBean.getList().getResources().forEach(resource -> {
                    YahooRateBean.ListBean.ResourcesBean.ResourceBean bean = resource.getResource();
                    YahooRateBean.ListBean.ResourcesBean.ResourceBean.FieldsBean fields = bean.getFields();
                    String name = StringHelper.remove(fields.getSymbol(), "=X");
                    String price = fields.getPrice();
                    usdRateMap.put(name, price);
                });
                redisRepository.putHashValues(REDIS_PREFIX, usdRateMap);
            }
        } catch (IOException e) {
            LOGGER.error("雅虎汇率接口请求失败", e);
        }
    }

    /**
     * Get map.
     *
     * @return the map
     */
    public Map<String, String> get() {
        return redisRepository.getHashValue(REDIS_PREFIX);
    }
}
