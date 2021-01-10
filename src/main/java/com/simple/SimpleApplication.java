package com.simple;

import com.simple.server.PrototypeBean;
import com.simple.server.SingletonBean;
import com.spring.context.Annotation.ComponentScan;
import com.spring.context.SimpleApplicationContext;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:42
 * @Copyright: 2020 Hundsun All rights reserved.
 */
@ComponentScan(basePackages = "com.simple")
public class SimpleApplication {

    public static void main(String[] args) {
        SimpleApplicationContext applicationContext = new SimpleApplicationContext(SimpleApplication.class);
        System.out.println("测试");
        PrototypeBean prototypeBean1 = (PrototypeBean) applicationContext.getBean("prototypeBean");
        PrototypeBean prototypeBean2 = (PrototypeBean) applicationContext.getBean("prototypeBean");
        SingletonBean singletonBean1 = (SingletonBean) applicationContext.getBean("singletonBean");
        SingletonBean singletonBean2 = (SingletonBean) applicationContext.getBean("singletonBean");

        System.out.println(prototypeBean1);
        System.out.println(prototypeBean2);
        System.out.println(singletonBean1);
        System.out.println(singletonBean2);
    }
}
