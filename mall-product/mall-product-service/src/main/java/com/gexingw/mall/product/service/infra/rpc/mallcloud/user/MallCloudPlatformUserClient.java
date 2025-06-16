package com.gexingw.mall.product.service.infra.rpc.mallcloud.user;

import com.gexingw.mall.user.client.dubbo.DubboPlatformUser;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class MallCloudPlatformUserClient {

    @DubboReference
    private DubboPlatformUser dubboPlatformUser;

    public String getId(Long id) {
        return dubboPlatformUser.getById(id);
    }

}
