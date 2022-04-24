package com.brij;


import com.brij.entities.Employee;
import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.apache.ignite.*;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.store.jdbc.CacheJdbcBlobStoreFactory;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.BasicJdbcDialect;
import org.apache.ignite.cache.store.jdbc.dialect.H2Dialect;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.jetbrains.annotations.NotNull;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;


public class IgniteApplication {
    public static void main(String[] args) throws IgniteException {
        // Preparing IgniteConfiguration using Java APIs
        IgniteConfiguration cfg = new IgniteConfiguration();
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
        Ignite ignite = Ignition.start(cfg);
        IgniteCache<Integer, Employee> empC = ignite.cache("EmployeeCache");
        empC.loadCache(null);
        Iterator<Cache.Entry<Integer, Employee>> iterator = empC.iterator();

        if (!iterator.hasNext()) {
            System.out.println("No value available");
        } else {
            iterator.forEachRemaining(d -> {
                System.out.printf("value for key %s is %s", d.getKey(), d.getValue());
            });
        }
        // Disconnect from the cluster.
//        ignite.close();
    }

    @NotNull
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