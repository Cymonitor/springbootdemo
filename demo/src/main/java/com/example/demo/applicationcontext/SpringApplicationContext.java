package com.example.demo.applicationcontext;

import org.springframework.context.ApplicationContext;

/**
 * create by chenyong
 * Date 2019/11/26  15:10
 * Description
 */
public class SpringApplicationContext {

    private static ApplicationContext ac;

    private static ApplicationContext mvcAc;


    public static void initApplicationContext(ApplicationContext ac){
        SpringApplicationContext.ac=ac;
    }

    public static ApplicationContext getApplicationContext(){
        return ac;
    }

    public static void initMvcApplicationContext(ApplicationContext mvcAc){
        SpringApplicationContext.mvcAc=mvcAc;
    }

    public static<T> T getBean(Class<T> c){
        if(null!=mvcAc){
            return mvcAc.getBean(c);
        }
        if(null!=ac){
            return ac.getBean(c);
        }
        return null;
    }

    public static Object getBean(String beanId){
        if(null!=mvcAc){
            return mvcAc.getBean(beanId);
        }
        if(null!=ac){
            return ac.getBean(beanId);
        }
        return null;
    }

    public static Object getBean(ApplicationContext ac, Class clazz){
        SpringApplicationContext.ac=ac;
        return getBean(clazz);
    }

    public static Object getBean(ApplicationContext ac, String beanId){
        SpringApplicationContext.ac=ac;
        return getBean(beanId);
    }
}
