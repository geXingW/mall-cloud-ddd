package com.gexingw.shop.order.service.domain.model;

import com.gexingw.shop.infrastucture.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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
public class Order implements AggregateRoot {

    private Long id;

    private BigDecimal amount;

    private List<OrderItem> items;

}
