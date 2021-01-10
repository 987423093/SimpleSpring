package com.simple.server;

import com.spring.context.Annotation.Autowired;
import com.spring.context.Annotation.Component;
import com.spring.context.Annotation.Scope;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/11 0:24
 * @Copyright: 2020 Hundsun All rights reserved.
 */
@Scope("prototype")
@Component
public class PrototypeBean {

    @Autowired
    private SingletonBean singletonBean;
}
