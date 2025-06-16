package com.gexingw.mall.product.service.adapter.web.platform;

import com.gexingw.mall.infrastucture.core.util.R;
import com.gexingw.mall.product.service.infra.rpc.mallcloud.user.MallCloudPlatformUserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/web/platform/product")
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class WebPlatformProductAdapter {

    private final MallCloudPlatformUserClient mallCloudUserClient;

    @GetMapping("test-user/{id}")
    public R<String> testUser(@PathVariable("id") Long id) {
        return R.ok(mallCloudUserClient.getId(id));
    }

}
