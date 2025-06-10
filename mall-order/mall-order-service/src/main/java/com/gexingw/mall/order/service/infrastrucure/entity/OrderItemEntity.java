package com.gexingw.mall.order.service.infrastrucure.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gexingw.mall.infrastructure.db.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 18:04
 */
@Setter
@Getter
@TableName("order_item")
public class OrderItemEntity extends BaseEntity {

    private Long id;

    private Long itemId;

    private Long orderId;

}
