package com.jd.quant.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * DataSource Config
 *
 * @author Zhiguo.Chen
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.jd.quant.core.dao")
public class MyBatisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisConfig.class);

    @Autowired
    private MybatisProperties mybatisProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        LOGGER.debug("Configuring Datasource...");
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true); //使用jdbc的getGeneratedKeys获取数据库自增主键值
        configuration.setUseColumnLabel(true); //使用列别名替换列名 select user as User
        configuration.setMapUnderscoreToCamelCase(true); //自动使用驼峰命名属性映射字段   userId    user_id
        configuration.setDefaultExecutorType(mybatisProperties.getExecutorType());
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setFailFast(true);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //TODO 此处实现暂不优雅
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mybatisProperties.getMapperLocations()[0]));
        sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        return transactionManager;
//    }
}