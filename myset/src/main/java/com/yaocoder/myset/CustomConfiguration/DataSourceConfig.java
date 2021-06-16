package com.yaocoder.myset.CustomConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author ly
 * @version 1.0
 * @date 2021/06/15 14:43
 * 配置主数据源
 */

@Configuration
public class DataSourceConfig {
     private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean(name = "primaryDataSource")
    @Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.main")
    public DataSource primaryDatasource() {
//        return DataSourceBuilder.create().build();
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "masterDataSource")
//    @ConfigurationProperties(prefix = "datasource.main")
//    public DataSource masterDataSource(DataSourceProperties properties) {
//        LOGGER.info("init master data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaveDataSource(DataSourceProperties properties) {
//        LOGGER.info("init slave data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
//        Map<String, DataSource> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceEnum.MASTER.getName(), masterDataSource);
//        targetDataSources.put(DataSourceEnum.SLAVE.getName(), slaveDataSource);
//
//        return new DynamicDataSource(masterDataSource, targetDataSources);
//    }
}