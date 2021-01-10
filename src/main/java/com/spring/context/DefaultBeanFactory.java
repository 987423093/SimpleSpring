package com.spring.context;

import com.spring.beans.BeanDefinition;
import com.spring.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:20
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public class DefaultBeanFactory implements BeanFactory{

    /**
     * 存储所有的bd
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 单例池
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 处理器
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }

    public Map<String, Object> getSingletonObjects() {
        return singletonObjects;
    }

    public void addSingletoenObject(String beanName, Object bean) {

        singletonObjects.put(beanName, bean);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void addBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }


    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }
}
