package com.gexingw.mall.product.service.application.service.impl;

import com.gexingw.mall.product.client.service.ProductService;
import com.gexingw.mall.user.client.dubbo.DubboPlatformUser;
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
    private DubboPlatformUser userService;

    @Override
    public String getById(Long id) {
        String user = userService.getById(id);

        return "Product-" + id + "；User：" + user;
    }

}
