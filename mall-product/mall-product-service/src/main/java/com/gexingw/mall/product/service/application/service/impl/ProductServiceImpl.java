package com.gexingw.mall.product.service.application.service.impl;

import com.gexingw.mall.product.interfaces.service.ProductService;
import com.gexingw.mall.user.interfaces.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 22:49
 */
@DubboService
public class ProductServiceImpl implements ProductService {

    @DubboReference
    private UserService userService;

    @Override
    public String getById(Long id) {
        String user = userService.getById(id);

        return "Product-" + id + "；User：" + user;
    }

}
