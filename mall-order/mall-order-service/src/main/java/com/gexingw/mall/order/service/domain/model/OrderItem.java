package com.gexingw.mall.order.service.domain.model;

import com.gexingw.mall.infrastucture.core.domain.ValueObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * mall-cloud-ddd.
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
