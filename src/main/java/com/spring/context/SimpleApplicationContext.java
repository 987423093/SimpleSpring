package com.spring.context;

import com.spring.beans.*;
import com.spring.context.Annotation.Autowired;
import com.spring.context.Annotation.Component;
import com.spring.context.Annotation.ComponentScan;
import com.spring.context.Annotation.Scope;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhoutao29203
 * @Date: 2021/1/10 22:16
 * @Copyright: 2020 Hundsun All rights reserved.
 */
public class SimpleApplicationContext {

    /**
     * bean工厂
     */
    private DefaultBeanFactory defaultBeanFactory;

    /**
     * 对外暴露接口,获取bean工厂
     * @return
     */
    public BeanFactory getBeanFactory() {
        return defaultBeanFactory;
    }

    /**
     * 对外暴露接口,获取bdm工厂
     * @return
     */
    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return defaultBeanFactory.getBeanDefinitionMap();
    }

    /**
     * 对外暴露接口,获取bdm工厂
     * @return
     */
    public Map<String, Object> getSingletonObjects() {
        return defaultBeanFactory.getSingletonObjects();
    }

    /**
     * 对外暴露接口,获取beanPostProcessor
     * @return
     */
    public List<BeanPostProcessor> getBeanPostProcessors () {
        return defaultBeanFactory.getBeanPostProcessors();
    }

    /**
     * 无参构造
     */
    public SimpleApplicationContext() {
        defaultBeanFactory = new DefaultBeanFactory();
    }

    /**
     * 带参构造
     *
     * @param configClass
     */
    public SimpleApplicationContext(Class<?> configClass) {

        this();
        scan(configClass);
        preInstantiateSingletons();
    }

    /**
     * 扫描配置类
     *
     * @param configClass
     */
    public void scan(Class<?> configClass) {

        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        String packages = componentScan.basePackages();
        List<Class> allClass = getAllClass(packages);

        // 扫描PostProcessor
        for (Class beanClass : allClass) {
            if (BeanPostProcessor.class.isAssignableFrom(beanClass)) {
                try {
                    BeanPostProcessor beanPostProcessor = (BeanPostProcessor) beanClass.newInstance();
                    defaultBeanFactory.addBeanPostProcessor(beanPostProcessor);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // 扫描beanDefinition
        for (Class beanClass : allClass) {
            if (beanClass.isAnnotationPresent(Component.class)) {
                // 创建bd
                BeanDefinition beanDefinition = new BeanDefinition();
                Component component = (Component) beanClass.getAnnotation(Component.class);
                String beanName;
                if (component.value().equals("")) {
                    String simpleName = beanClass.getSimpleName();
                    beanName = simpleName.substring(0, 1).toLowerCase();
                    if (simpleName.length() >= 2) {
                        beanName += simpleName.substring(1);
                    }
                } else {
                    beanName = component.value();
                }
                beanDefinition.setBeanName(beanName);
                beanDefinition.setBeanClass(beanClass);

                // 解析scope
                if (beanClass.isAnnotationPresent(Scope.class)) {
                    Scope scope = (Scope) beanClass.getAnnotation(Scope.class);
                    beanDefinition.setScope(scope.value());
                } else {
                    beanDefinition.setScope("singleton");
                }

                defaultBeanFactory.addBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    /**
     * 提前实例化单例池
     */
    public void preInstantiateSingletons() {

        for (String beanName : getBeanDefinitionMap().keySet()) {
            BeanDefinition beanDefinition = getBeanDefinitionMap().get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanDefinition);
                defaultBeanFactory.addSingletoenObject(beanName, bean);
            }
        }
    }

    /**
     * 实例化bean
     *
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {

        String beanName = beanDefinition.getBeanName();
        Class beanClass = beanDefinition.getBeanClass();

        Object instanceBean = null;

        // 实例化前
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            instanceBean = beanPostProcessor.postProcessBeforeInstantiation(beanName, beanClass);
            if (instanceBean != null) {
                break;
            }
        }
        if (instanceBean != null) {
            return instanceBean;
        }

        try {
            instanceBean = beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // 实例化后
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            beanPostProcessor.postProcessAfterInstantiation(beanName, instanceBean);
        }

        // 填充属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String filedName = field.getName();
                Object filedBean = getBean(filedName);

                field.setAccessible(true);
                try {
                    field.set(instanceBean, filedBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }

        // 回调
        if (instanceBean instanceof Aware) {
            if (instanceBean instanceof BeanNameAware) {
                ((BeanNameAware) instanceBean).setBeanName(beanName);
            }
        }

        // 初始化之前
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            instanceBean = beanPostProcessor.postProcessBeforeInitialization(beanName, instanceBean);
        }
        // 初始化
        if (instanceBean instanceof InitializingBean) {
            ((InitializingBean) instanceBean).afterPropertiesSet();
        }
        // 初始化之后
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            instanceBean = beanPostProcessor.postProcessAfterInitialization(beanName, instanceBean);
        }
        return instanceBean;
    }

    /**
     * 根据路径获取所有的类
     *
     * @param packagePath
     * @return
     */
    private List<Class> getAllClass(String packagePath) {

        List<Class> beanClasses = new ArrayList<>();
        packagePath = packagePath.replace(".", "/");
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(packagePath);
        if (resource == null) {
            return beanClasses;
        }
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
           parseFile(beanClasses, file, classLoader);
        }
        return beanClasses;
    }

    private void parseFile(List<Class> beanClasses, File file, ClassLoader classLoader) {

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                parseFile(beanClasses, f, classLoader);
            }
            String fileName = f.getAbsolutePath();
            if (fileName.endsWith(".class")) {
                String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                className = className.replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    beanClasses.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据beanName获取bean
     *
     * @param beanName
     */
    public Object getBean(String beanName) {

        if (getBeanDefinitionMap().get(beanName).getScope().equals("singleton")) {
            if (getSingletonObjects().containsKey(beanName)) {
                return getSingletonObjects().get(beanName);
            }
        }
        BeanDefinition beanDefinition = getBeanDefinitionMap().get(beanName);
        return createBean(beanDefinition);
    }
}
