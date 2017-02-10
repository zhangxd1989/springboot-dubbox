package cn.zhangxd.platform.system.provider.config;

import com.alibaba.druid.pool.DruidDataSource;
import cn.zhangxd.platform.common.service.datasource.DynamicDataSource;
import cn.zhangxd.platform.common.service.datasource.DynamicDataSourceTransactionManager;
import cn.zhangxd.platform.system.api.exception.base.SystemException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author zhangxd
 */
@Configuration
public class DatasourceConfig {

    /**
     * Write data source druid data source.
     *
     * @return the druid data source
     */
    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DruidDataSource writeDataSource() {
        return new DruidDataSource();
    }

    /**
     * Read data source druid data source.
     *
     * @return the druid data source
     */
    @Bean(name = "readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DruidDataSource readDataSource() {
        return new DruidDataSource();
    }

    /**
     * Dynamic data source data source.
     *
     * @return the data source
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setWriteDataSource(writeDataSource());
        dynamicDataSource.setReadDataSource(readDataSource());

        return dynamicDataSource;
    }

    /**
     * Dynamic transaction manager data source transaction manager.
     *
     * @param dynamicDataSource the dynamic data source
     * @return the data source transaction manager
     */
    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DynamicDataSourceTransactionManager(dynamicDataSource);
    }

    /**
     * Dynamic sql session factory sql session factory.
     *
     * @param dynamicDataSource the dynamic data source
     * @param properties        the properties
     * @return the sql session factory
     */
    @Bean
    @ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
    public SqlSessionFactory dynamicSqlSessionFactory(
        @Qualifier("dynamicDataSource") DataSource dynamicDataSource,
        MybatisProperties properties) {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(properties.getConfigLocation()));
        sessionFactory.setMapperLocations(properties.resolveMapperLocations());
        try {
            return sessionFactory.getObject();
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

}
