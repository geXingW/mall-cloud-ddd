package com.gexingw.mall.infrastructure.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 12:17
 */
@Component
@SuppressWarnings("unused")
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        SpringUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    public static Object getBean(String beanId) {
        return beanId == null ? null : context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null != beanName && !"".equals(beanName.trim())) {
            return clazz == null ? null : context.getBean(beanName, clazz);
        }

        return null;
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
        return context.getBeansOfType(type);
    }

    public static ApplicationContext getContext() {
        return context == null ? null : context;
    }

    /**
     * 获取当前环境
     *
     * @return 当前环境的大写
     */
    public static String getActiveProfile() {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();

        // 默认DEV
        return activeProfiles.length == 0 ? "DEV" : activeProfiles[0].toUpperCase();
    }

}

