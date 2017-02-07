package com.ehsy.common.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by zhuangmg on 6/7/16.
 */
public class CMApplicationContext implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(CMApplicationContext.class);

    private static ApplicationContext applicationContext;

    private static String id;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.debug("设置application context");
        CMApplicationContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return CMApplicationContext.applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> c){
        return CMApplicationContext.applicationContext.getBean(beanName, c);
    }

    public static String getId() {
        return id;
    }

    public void setId(String id) {
        CMApplicationContext.id = id;
    }
}
