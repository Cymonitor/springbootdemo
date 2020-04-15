package com.cy.boot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @auther chenyong
 * @date 2020/4/15 9:37
 *
 * 说明
 */
@Component
@Order(2)
public class ExtendApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Springboot项目启动后加载自定义配置类===============》"+this.getClass().getName());
    }
}
