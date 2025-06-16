package com.gexingw.mall.user.service.adapter.web.platform;

import com.gexingw.mall.infrastucture.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商城/运营端/认证
 */
@RestController
@RequestMapping("/web/platform/auth")
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class WebPlatformAuthAdapter {

    /**
     * 登录信息
     *
     * @return @seeR
     */
    @GetMapping("info")
    public R<Object> info() {
        return R.ok();
    }

    /**
     * 用户登录
     *
     * @return R
     */
    @PostMapping("login")
    public R<Object> login() {
        return R.ok();
    }

    /**
     * 退出登录
     *
     * @return R
     */
    @PostMapping("logout")
    public R<Object> logout() {
        return R.ok();
    }

}
