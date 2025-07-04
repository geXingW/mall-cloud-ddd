package com.gexingw.mall.auth.service.adapter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/auth")
public class WebAuthAdapter {

    @GetMapping("info")
    public String info() {
        return "info";
    }

}
