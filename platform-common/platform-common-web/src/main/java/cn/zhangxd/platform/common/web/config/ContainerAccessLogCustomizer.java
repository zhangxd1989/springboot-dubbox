package cn.zhangxd.platform.common.web.config;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

/**
 * access log config
 *
 * @author zhangxd
 */
public class ContainerAccessLogCustomizer implements EmbeddedServletContainerCustomizer {

    /**
     * 访问日志文件
     */
    private String logbackAccessFile;

    public ContainerAccessLogCustomizer(String logbackAccessFile) {
        this.logbackAccessFile = logbackAccessFile;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        if (container instanceof TomcatEmbeddedServletContainerFactory) {
            TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;

            //访问日志设置
            LogbackValve logbackValve = new LogbackValve();
            logbackValve.setQuiet(true);
            logbackValve.setFilename(logbackAccessFile);
            containerFactory.addContextValves(logbackValve);
        }
    }
}
