package com.gexingw.mall.user.service.application.service.impl;

import com.gexingw.mall.user.interfaces.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @time 2023/11/13 10:31
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Value("random")
    @Setter
    @Getter
    private String random;

    @Override
    public String getById(Long id) {
        return "User-" + id + ": " + random;
    }

    @Override
    public int number(int num) {
        if(num < 0) {
            return -1;
        }

        return num;
    }

}
