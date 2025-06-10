package com.gexingw.mall.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 18:16
 */
@EnableAsync
@EnableFeignClients({"com.gexingw.mall.*.interfaces.feign"})
@SpringBootApplication(scanBasePackages = {"com.gexingw.mall.order", "com.gexingw.mall.infrastructure"})
public class ShopOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderServiceApplication.class, args);
    }

}
