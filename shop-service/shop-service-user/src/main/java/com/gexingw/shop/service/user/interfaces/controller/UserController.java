package com.gexingw.shop.service.user.interfaces.controller;

import com.gexingw.shop.infrastucture.core.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/18 17:47
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public R<Object> info(@PathVariable Long id) {
        return R.ok("User-" + id);
    }

}
