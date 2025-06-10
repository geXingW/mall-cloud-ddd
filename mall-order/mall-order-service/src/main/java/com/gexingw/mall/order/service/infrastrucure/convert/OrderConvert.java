package com.gexingw.mall.order.service.infrastrucure.convert;

import com.gexingw.mall.order.service.domain.model.Order;
import com.gexingw.mall.order.service.domain.model.OrderItem;
import com.gexingw.mall.order.service.infrastrucure.entity.OrderEntity;
import com.gexingw.mall.order.service.infrastrucure.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * mall-cloud-ddd.
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
    @Mapping(target = "items", ignore = true)
    Order toModel(OrderEntity orderEntity);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderItemEntity toEntity(OrderItem orderItem);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "count", ignore = true)
    OrderItem toModel(OrderItemEntity orderItemEntity);

}
