package com.hnust.zsg.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext=applicationContext;
    }

    public static  <T> T getBean(Class<T> clazz){
        return applicationContext==null?null:applicationContext.getBean(clazz);
    }

    public static Object getBean(String className){
        return applicationContext==null?null:applicationContext.getBean(className);
    }

    public static  <T> T getBean(String className,Class<T> clazz){
        return applicationContext==null?null:applicationContext.getBean(className,clazz);
    }

}
