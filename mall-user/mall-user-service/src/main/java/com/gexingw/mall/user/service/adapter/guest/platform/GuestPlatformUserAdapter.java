package com.gexingw.mall.user.service.adapter.guest.platform;

import com.gexingw.mall.infrastucture.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/guest/platform/user")
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class GuestPlatformUserAdapter {

    @PostMapping("page")
    public R<Object> page() {
        return R.ok();
    }

}
