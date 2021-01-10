package com.spring.beans;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:18
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public class BeanDefinition {

    /**
     * bean名称
     */
    private String beanName;

    /**
     * bean类型
     */
    private String scope;

    /**
     * bean类
     */
    private Class beanClass;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
