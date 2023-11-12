package com.gexingw.shop.product.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 15:59
 */
@EnableAsync
@SpringBootApplication
public class ShopProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopProductServiceApplication.class, args);
    }

}
