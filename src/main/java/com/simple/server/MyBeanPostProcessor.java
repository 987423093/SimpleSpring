package com.simple.server;

import com.spring.beans.BeanPostProcessor;
import com.spring.context.Annotation.Component;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/11 0:23
 * @Copyright: 2020 Hundsun All rights reserved.
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 实例化之前
     *
     * @param beanName
     * @param beanClass
     */
    @Override
    public Object postProcessBeforeInstantiation(String beanName, Class<?> beanClass) {
        System.out.println(beanName + "实例化之前");
        return null;
    }

    /**
     * 实例化之后
     *
     * @param beanName
     * @param bean
     */
    @Override
    public Boolean postProcessAfterInstantiation(String beanName, Object bean) {
        System.out.println(beanName + "实例化之后");
        return null;
    }

    /**
     * 初始化之前
     *
     * @param beanName
     * @param bean
     */
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        System.out.println(beanName + "初始化之前");
        return bean;
    }

    /**
     * 初始化之后
     *
     * @param beanName
     * @param bean
     */
    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        System.out.println(beanName + "初始化之后");
        return bean;
    }
}
