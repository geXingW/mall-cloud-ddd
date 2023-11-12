package com.gexingw.shop.infrastructure.util;

import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 12:28
 */
public class EventUtil {

    public static void publish(ApplicationEvent event) {
        Objects.requireNonNull(SpringUtil.getContext()).publishEvent(event);
    }

}
