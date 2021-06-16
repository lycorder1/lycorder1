package com.yaocoder.myset.CustomConfiguration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 转换表名小写为大写
 */
public class UpperTableStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1383021413247872469L;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // 将表名全部转换成大写
        String tableName = name.getText().toUpperCase();
        return Identifier.toIdentifier(tableName);
    }
}
