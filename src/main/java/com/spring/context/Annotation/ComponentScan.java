package com.spring.context.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:46
 * @Copyright: 2020 Hundsun All rights reserved.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {

    String basePackages() default "";
}
