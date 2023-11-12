package com.gexingw.shop.order.service.domain.factory;

import com.gexingw.shop.infrastucture.core.util.SnowflakeUtil;
import com.gexingw.shop.order.service.application.command.OrderCreateCommand;
import com.gexingw.shop.order.service.domain.model.Order;
import com.gexingw.shop.order.service.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 12:00
 */
@Component
public class OrderFactory {

    public Order createOrder(OrderCreateCommand createCommand) {
        Order order = new Order();
        // 订单Id
        order.setId(SnowflakeUtil.getId());
        // 订单金额
        order.setAmount(createCommand.getAmount());
        // 订单商品
        order.setItems(createCommand.getProductIds().stream().map(OrderItem::new).collect(Collectors.toList()));

        return order;
    }

}
