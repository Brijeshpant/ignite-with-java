package com.brij;

import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.brij")
@EnableIgniteRepositories
public class SpringBootClientApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootClientApp.class, args);

    }
}
