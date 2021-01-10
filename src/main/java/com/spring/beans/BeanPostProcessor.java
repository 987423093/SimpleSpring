package com.spring.beans;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:22
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public interface BeanPostProcessor {

    /**
     * 实例化之前
     * @param beanName
     * @param beanClass
     */
    Object postProcessBeforeInstantiation(String beanName, Class<?> beanClass);

    /**
     * 实例化之后
     * @param beanName
     * @param bean
     */
    Boolean postProcessAfterInstantiation(String beanName, Object bean);

    /**
     * 初始化之前
     * @param beanName
     * @param bean
     */
    Object postProcessBeforeInitialization(String beanName, Object bean);

    /**
     * 初始化之后
     * @param beanName
     * @param bean
     */
    Object postProcessAfterInitialization(String beanName, Object bean);
}
