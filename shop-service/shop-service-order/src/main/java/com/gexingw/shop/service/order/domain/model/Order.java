package com.gexingw.shop.service.order.domain.model;

import com.gexingw.shop.infrastructure.db.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/23 15:35
 */
@Setter
@Getter
@Accessors(chain = true)
public class Order extends BaseEntity {

    private Long id;

    private List<OrderItem> products;

}
