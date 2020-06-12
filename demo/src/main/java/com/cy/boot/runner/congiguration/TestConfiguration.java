package com.cy.boot.runner.congiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * @auther chenyong
 * @date 2020/6/4 13:53
 */
@Configuration
@AutoConfigureAfter(TestConfigurationA.class)
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("我在A后面加载");
    }
}
