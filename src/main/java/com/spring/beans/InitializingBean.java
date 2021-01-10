package com.spring.beans;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 23:36
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public interface InitializingBean {

    /**
     * 初始化
     */
    void afterPropertiesSet();
}
