package com.spring.beans;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 23:49
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public interface BeanNameAware extends Aware{

    /**
     * 设置beanName
     * @param beanName
     */
    void setBeanName(String beanName);
}
