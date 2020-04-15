package com.cy.boot.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @auther chenyong
 * @date 2020/4/15 9:36
 */
@Component
@Order(1)
public class ExtendCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Springboot项目启动后加载自定义配置类======================》"+this.getClass().getName());
    }
}
