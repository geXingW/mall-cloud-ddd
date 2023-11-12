package com.gexingw.shop.order.service.domain.model;

import com.gexingw.shop.infrastucture.core.domain.ValueObject;
import lombok.Data;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/24 22:21
 */
@Data
public class Address implements ValueObject<Address> {

    private String name;

    private String phone;

}
