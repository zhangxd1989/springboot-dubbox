package cn.zhangxd.platform.mobile.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Client mobile application.
 *
 * @author zhangxd
 */
@RestController
@SpringBootApplication
@ImportResource("classpath:dubbo-consumer.xml")
public class ClientMobileApplication {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMobileApplication.class);

    /**
     * Hello string.
     *
     * @return the string
     */
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ClientMobileApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        LOGGER.info("Client mobile started!!!");
    }
}
