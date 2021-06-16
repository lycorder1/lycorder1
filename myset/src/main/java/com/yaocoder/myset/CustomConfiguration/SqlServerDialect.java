package com.yaocoder.myset.CustomConfiguration;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * 自定义方言类，处理使用原生Sql查询，数据类型和hibernate对应关系

 继承SQLServer2012Dialect，如果sqlServer版本是2008则继承
 SQLServer2008Dialect，其他版本继承各自的版本

 */
public class SqlServerDialect extends SQLServer2012Dialect {

    /**
     * 设置sql server 数据库方言转换
     */
    public SqlServerDialect() {
        registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
    }
}
