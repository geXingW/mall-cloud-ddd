package com.gexingw.shop.order.service.interfaces;

import com.gexingw.shop.infrastucture.core.util.R;
import com.gexingw.shop.order.service.application.command.OrderCreateCommand;
import com.gexingw.shop.order.service.application.service.OrderService;
import com.gexingw.shop.order.service.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 10:51
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public R<Object> list() {
        return R.ok(orderService.queryAll());
    }

    @GetMapping("/{id}")
    public R<Order> info(@PathVariable Long id) {
        return R.ok(orderService.info(id));
    }

    @PostMapping
    public R<Long> save(@RequestBody @Validated OrderCreateCommand createCommand) {
        return R.ok(orderService.save(createCommand));
    }

}
