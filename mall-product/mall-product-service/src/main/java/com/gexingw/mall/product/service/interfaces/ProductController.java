package com.gexingw.mall.product.service.interfaces;

import com.gexingw.mall.product.client.feign.ProductFeign;
import com.gexingw.mall.product.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 13:41
 */
@Slf4j
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController implements ProductFeign {

    private final ProductService productService;

    @Override
    @GetMapping("/{id}/decr-stock")
    public boolean decrStock(@PathVariable Long id) {
        log.info("Decr product stock: {}", id);
        System.out.println(productService.getById(id));

        return false;
    }

}
