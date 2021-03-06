package com.idea.permission.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring的上下文对象context的工具类
 */
public class ApplicationContextHelper implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> cls) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(cls);
    }

    public static <T> T popBean(String name, Class<T> cls) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, cls);
    }
}
