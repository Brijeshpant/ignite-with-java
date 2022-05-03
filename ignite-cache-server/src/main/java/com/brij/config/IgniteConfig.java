package com.brij.config;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.cluster.IgniteClusterImpl;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.springframework.boot.autoconfigure.IgniteConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static org.apache.ignite.springframework.boot.autoconfigure.IgniteAutoConfiguration.IGNITE_PROPS_PREFIX;

@Configuration
public class IgniteConfig {
    private Ignite ignite;

//    @Bean
//    @ConfigurationProperties(prefix = IGNITE_PROPS_PREFIX)
//    public IgniteConfiguration getIgniteConfig() {
//        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
//        igniteConfiguration.setIgniteInstanceName("instance");
//        igniteConfiguration.setConsistentId("consistentId");
//        return igniteConfiguration;
//    }

    @Bean
    public IgniteConfigurer nodeConfigurer() {
        return cfg -> {
            cfg.setConsistentId("consistentId1");
        };
    }

}
