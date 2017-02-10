package cn.zhangxd.platform.mobile.client.common.annotation;

import cn.zhangxd.platform.common.utils.WebHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * The type Request limit contract.
 *
 * @author zhangxd
 */
@Aspect
@Component
public class RequestLimitContract {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitContract.class);
    /**
     * 缓存前缀
     */
    private static final String PREFIX = "req_limit_";
    /**
     * RedisTemplate
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Request limit.
     *
     * @param joinPoint the join point
     * @param limit     the limit
     * @throws RequestLimitException the request limit exception
     */
    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")
    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {

        try {
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                    break;
                }
            }
            if (request == null) {
                throw new RequestLimitException("方法中缺失HttpServletRequest参数");
            }
            String ip = WebHelper.getRemoteAddr(request);
            String url = request.getRequestURL().toString();
            String key = PREFIX.concat(url).concat(ip);

            long count = redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                redisTemplate.expire(key, limit.time(), TimeUnit.MILLISECONDS);
            }
            if (count > limit.count()) {
                LOGGER.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
                throw new RequestLimitException();
            }
        } catch (RequestLimitException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("发生异常: ", e);
        }
    }
}