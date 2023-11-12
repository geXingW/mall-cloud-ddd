package com.gexingw.shop.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 18:16
 */
@EnableAsync
@EnableFeignClients({"com.gexingw.shop.*.interfaces.feign"})
@SpringBootApplication(scanBasePackages = {"com.gexingw.shop.order", "com.gexingw.shop.infrastructure"})
public class ShopOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderServiceApplication.class, args);
    }

}
