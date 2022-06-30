package com.brij.config;


import com.brij.entities.Employee;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.BasicJdbcDialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.Factory;
import javax.sql.DataSource;
import java.sql.Types;

@Configuration
public class IgniteConfig {

    @Bean
    public IgniteConfigurer nodeConfigurer() {
        return cfg -> {
            cfg.setIgniteInstanceName("instance 1");
            IgniteLogger slf4jLogger = new Slf4jLogger();
            cfg.setGridLogger(slf4jLogger);
            cfg.setClientMode(false);

            CacheConfiguration<Integer, Employee> empCache = new CacheConfiguration<>();
            empCache.setName("EmployeeCache");
            cfg.setCacheConfiguration(empCache);

            CacheJdbcPojoStoreFactory<Integer, Employee> factory = new CacheJdbcPojoStoreFactory<>();

            factory.setDialect(new BasicJdbcDialect());

            factory.setDataSourceFactory(getDataSourceFactory());

            JdbcType employeeType = getJdbcType();

            factory.setTypes(employeeType);

            empCache.setCacheStoreFactory(factory);
            empCache.setReadThrough(true);
            empCache.setWriteThrough(true);
            empCache.setWriteBehindEnabled(true);
            empCache.setWriteBehindFlushSize(2);
            empCache.setWriteBehindFlushFrequency(50000);
            empCache.setIndexedTypes(Integer.class, Employee.class);
            cfg.setCacheConfiguration(empCache);

        };
    }
    private static JdbcType getJdbcType() {
        JdbcType employeeType = new JdbcType();
        employeeType.setCacheName("EmployeeCache");
        employeeType.setDatabaseTable("employees");
        employeeType.setKeyType(Integer.class);
        employeeType.setKeyFields(new JdbcTypeField(Types.INTEGER, "id", Integer.class, "id"));
        employeeType.setValueFields(
                new JdbcTypeField(Types.INTEGER, "id", Integer.class, "id"),
                new JdbcTypeField(Types.VARCHAR, "name", String.class, "name"),
                new JdbcTypeField(Types.VARCHAR, "email", String.class, "email")
        );
        employeeType.setValueType(Employee.class);

        return employeeType;
    }

    private static Factory<DataSource> getDataSourceFactory() {
        return () -> {
            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setDriverClass("org.postgresql.Driver");
            driverManagerDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            driverManagerDataSource.setUser("postgres");
            driverManagerDataSource.setPassword("");
            return driverManagerDataSource;
        };
    }

}
