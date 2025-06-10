package com.gexingw.mall.user.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;


/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/13 10:51
 */
@EnableDubbo
@SpringBootApplication
public class ShopUserServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ShopUserServiceApplication.class, args);
        new CountDownLatch(1).await();
    }

}
