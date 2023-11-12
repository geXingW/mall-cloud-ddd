package com.gexingw.shop.order.service.application.service;

import com.gexingw.shop.order.service.application.command.OrderCreateCommand;
import com.gexingw.shop.order.service.domain.model.Order;

import java.util.List;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 10:46
 */
public interface OrderService {

    Order info(Long id);

    List<Order> queryAll();

    Long save(OrderCreateCommand createCommand);

}
