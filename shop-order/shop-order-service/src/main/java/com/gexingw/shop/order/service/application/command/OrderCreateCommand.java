package com.gexingw.shop.order.service.application.command;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 11:00
 */
@Data
public class OrderCreateCommand implements Serializable {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    @Size(min = 1)
    private List<Long> productIds;

}
