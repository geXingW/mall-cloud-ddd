package com.gexingw.shop.service.order.domain.model;

import com.gexingw.shop.infrastucture.core.domain.ValueObject;
import lombok.Data;

import java.math.BigDecimal;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/23 15:55
 */
@Data
public class OrderItem implements ValueObject<OrderItem> {

    private String name;

    private BigDecimal price;

    private Integer count;

}
