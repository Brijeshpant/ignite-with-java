package com.brij;

import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(SpringBootApp.class, args);
        Ignite ignite = (Ignite) context.getBean("ignite");
        ignite.cluster().state(ClusterState.ACTIVE);

    }
}
