package com.yaocoder.myset.CustomConfiguration;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ly
 * @version 1.0
 * @date 2021/06/15 14:46
 * 主数据源的配置
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerPrimary", //配置 事物管理器  transactionManager
        basePackages = {"com.yaocoder.myset.primaryReposition"}//设置持久层所在位置
)
public class PrimaryConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    @Qualifier("primaryDataSource")
    // 自动注入配置好的数据源
    private DataSource primaryDataSource;


    @Value("${spring.jpa.hibernate.main-dialect}")
    // 获取对应的数据库方言
    private String primaryDialect;

    /**
     *
     * @param builder
     * @return
     */
    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {

        return builder
                //设置数据源
                .dataSource(primaryDataSource)
                //设置数据源属性
                .properties(getVendorProperties(primaryDataSource))
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.yaocoder.myset.entities")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("primaryPersistenceUnit")
                .build();

    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        Map<String,String> map = new HashMap<>();
        // 设置对应的数据库方言
        map.put("hibernate.dialect",primaryDialect);
        map.put("hibernate.hbm2ddl.auto","update");
//        map.put("hibernate.default_schema","dbo");
        map.put("show-sql","true");
////        map.put("database-platform","com.yaocoder.myset.CustomConfiguration.SqlServerDialect");
//        map.put("database","sql_server");
//        map.put("hibernate.naming.strategy","org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.setProperties(map);
        return jpaProperties.getProperties();
//        return hibernateProperties.determineHibernateProperties(
//                jpaProperties.getProperties(),
//                new HibernateSettings());
    }

    /**
     * 配置事物管理器
     *
     * @param builder
     * @return
     */
    @Bean(name = "transactionManagerPrimary")
    @Primary
    PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }
}
