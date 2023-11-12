package com.gexingw.shop.service.order.interfaces;

import com.gexingw.shop.infrastucture.core.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/23 15:17
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/{id}")
    public R<Object> info(@PathVariable String id) {
        return R.ok();
    }

}
