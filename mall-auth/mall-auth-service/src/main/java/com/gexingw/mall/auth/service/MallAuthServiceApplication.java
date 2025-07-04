package com.gexingw.mall.auth.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.gexingw.mall.auth", "com.gexingw.mall.infrastructure"})
public class MallAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAuthServiceApplication.class, args);
    }

}
