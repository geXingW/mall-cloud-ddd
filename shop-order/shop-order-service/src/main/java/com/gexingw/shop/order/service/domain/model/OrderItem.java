package com.gexingw.shop.order.service.domain.model;

import com.gexingw.shop.infrastucture.core.domain.ValueObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/23 15:55
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class OrderItem implements ValueObject<OrderItem> {

    private Long itemId;

    private String name;

    private BigDecimal price;

    private Integer count;

    public OrderItem(Long itemId) {
        this.itemId = itemId;
    }

}
