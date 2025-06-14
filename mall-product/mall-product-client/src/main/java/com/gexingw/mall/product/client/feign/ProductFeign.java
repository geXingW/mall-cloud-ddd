package com.gexingw.mall.product.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 13:39
 */
@FeignClient(value = "shop-product-service", contextId = "ProductFeign", path = "/product")
public interface ProductFeign {

    @PostMapping("/{id}/decr-stock")
    boolean decrStock(@PathVariable Long id);

}
