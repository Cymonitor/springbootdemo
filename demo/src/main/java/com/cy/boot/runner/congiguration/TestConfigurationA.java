package com.cy.boot.runner.congiguration;

import org.springframework.context.annotation.Configuration;

/**
 * @auther chenyong
 * @date 2020/6/4 13:55
 */
@Configuration
public class TestConfigurationA {

    public TestConfigurationA(){
        System.out.println("我【A】要加载了");
    }
}
