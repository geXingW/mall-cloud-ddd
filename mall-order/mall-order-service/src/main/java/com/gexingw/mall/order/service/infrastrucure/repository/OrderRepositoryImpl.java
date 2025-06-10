package com.gexingw.mall.order.service.infrastrucure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gexingw.mall.infrastructure.db.repository.BaseRepository;
import com.gexingw.mall.order.service.domain.model.Order;
import com.gexingw.mall.order.service.domain.repository.OrderRepository;
import com.gexingw.mall.order.service.infrastrucure.convert.OrderConvert;
import com.gexingw.mall.order.service.infrastrucure.entity.OrderEntity;
import com.gexingw.mall.order.service.infrastrucure.entity.OrderItemEntity;
import com.gexingw.mall.order.service.infrastrucure.mapper.OrderItemMapper;
import com.gexingw.mall.order.service.infrastrucure.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 11:27
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class OrderRepositoryImpl extends BaseRepository<OrderMapper, OrderEntity> implements OrderRepository {

    private final OrderConvert orderConvert;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Order getById(Long id) {
        // 查询订单主信息
        OrderEntity orderEntity = baseMapper.selectById(id);
        Order order = orderConvert.toModel(orderEntity);
        // 查询订单商品信息
        LambdaQueryWrapper<OrderItemEntity> queryWrapper = Wrappers.lambdaQuery(OrderItemEntity.class);
        queryWrapper.eq(OrderItemEntity::getOrderId, id);
        order.setItems(orderItemMapper.selectList(queryWrapper).stream().map(orderConvert::toModel).collect(Collectors.toList()));

        return order;
    }

    @Override
    public List<Order> queryAll() {
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();

        return this.list(queryWrapper).stream().map(orderConvert::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean create(Order order) {
        // 保存订单数据
        OrderEntity orderEntity = orderConvert.toEntity(order);
        baseMapper.insert(orderEntity);

        // 保存订单商品数据
        order.getItems().forEach(item -> {
            OrderItemEntity orderItemEntity = orderConvert.toEntity(item);
            orderItemEntity.setOrderId(order.getId());
            orderItemMapper.insert(orderItemEntity);
        });

        return true;
    }

}
