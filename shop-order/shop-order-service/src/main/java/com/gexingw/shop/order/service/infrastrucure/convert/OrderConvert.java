package com.gexingw.shop.order.service.infrastrucure.convert;

import com.gexingw.shop.order.service.domain.model.Order;
import com.gexingw.shop.order.service.domain.model.OrderItem;
import com.gexingw.shop.order.service.infrastrucure.entity.OrderEntity;
import com.gexingw.shop.order.service.infrastrucure.entity.OrderItemEntity;
import org.mapstruct.Mapper;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 16:36
 */
@Mapper(componentModel = "spring")
public interface OrderConvert {

    /**
     * 实体转换为模型
     *
     * @param order Order
     * @return Order
     */
    OrderEntity toEntity(Order order);

    /**
     * 模型转换为实体
     *
     * @param orderEntity OrderEntity
     * @return OrderEntity
     */
    Order toModel(OrderEntity orderEntity);

    OrderItemEntity toEntity(OrderItem orderItem);

    OrderItem toModel(OrderItemEntity orderItemEntity);

}
