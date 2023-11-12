package com.gexingw.shop.order.service.domain.repository;

import com.gexingw.shop.order.service.domain.model.Order;

import java.util.List;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 11:29
 */
public interface OrderRepository {

    Order getById(Long id);

    List<Order> queryAll();

    boolean create(Order order);

}
