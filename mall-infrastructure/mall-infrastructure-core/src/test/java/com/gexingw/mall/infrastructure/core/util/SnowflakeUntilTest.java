package com.gexingw.mall.infrastructure.core.util;

import com.gexingw.mall.infrastucture.core.util.SnowflakeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/14 16:00
 */
@ExtendWith(MockitoExtension.class)
public class SnowflakeUntilTest {

    @Test
    public void getIdTest() {
        Long id = SnowflakeUtil.getId();
        Assertions.assertNotNull(id);
    }

}
