package com.gexingw.shop.order.service.application.service.impl;

import com.gexingw.shop.infrastructure.util.EventUtil;
import com.gexingw.shop.order.service.application.command.OrderCreateCommand;
import com.gexingw.shop.order.service.application.event.order.submit.SubmitOrderEvent;
import com.gexingw.shop.order.service.application.service.OrderService;
import com.gexingw.shop.order.service.domain.factory.OrderFactory;
import com.gexingw.shop.order.service.domain.model.Order;
import com.gexingw.shop.order.service.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 10:47
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderFactory orderFactory;

    @Override
    public Order info(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public List<Order> queryAll() {
        return orderRepository.queryAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(OrderCreateCommand createCommand) {
        // 构造Order模型
        Order order = orderFactory.createOrder(createCommand);

        // 执行订单创建逻辑
        EventUtil.publish(new SubmitOrderEvent(this, order));

        return order.getId();
    }

}
