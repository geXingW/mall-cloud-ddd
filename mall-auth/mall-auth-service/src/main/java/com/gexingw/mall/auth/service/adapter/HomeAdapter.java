package com.gexingw.mall.auth.service.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeAdapter {

    @GetMapping
    public String home() {
        return "home";
    }

}
