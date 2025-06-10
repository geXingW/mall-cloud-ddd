package com.gexingw.mall.order.service.infrastrucure.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gexingw.mall.infrastructure.db.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 15:13
 */
@Setter
@Getter
@TableName("`order`")
public class OrderEntity extends BaseEntity {

    private Long id;

    private BigDecimal totalAmount;

}
