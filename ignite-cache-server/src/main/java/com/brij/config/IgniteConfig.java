package com.brij.config;


import com.brij.entities.Employee;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

    @Bean
    public IgniteConfigurer nodeConfigurer() {
        return cfg -> {
            CacheConfiguration ccfg = new CacheConfiguration("EmployeeCache");
            ccfg.setIndexedTypes(Integer.class, Employee.class);
            cfg.setCacheConfiguration(ccfg);

        };
    }

}
