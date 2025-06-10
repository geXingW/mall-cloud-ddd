package com.gexingw.mall.user.service.application.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/13 17:08
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplSnowflakeUntilTest {

    @Test
    public void testTest() {
        UserServiceImpl userService = Mockito.spy(UserServiceImpl.class);
        Assertions.assertTrue(userService.number(222) > 0);
    }

}
