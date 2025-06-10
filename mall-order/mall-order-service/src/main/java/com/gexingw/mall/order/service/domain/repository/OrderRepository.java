package com.gexingw.mall.order.service.domain.repository;

import com.gexingw.mall.order.service.domain.model.Order;

import java.util.List;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 11:29
 */
public interface OrderRepository {

    Order getById(Long id);

    List<Order> queryAll();

    boolean create(Order order);

}
