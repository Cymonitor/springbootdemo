package com.cy.boot.runner.congiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @auther chenyong
 * @date 2020/6/5 15:36
 */
@Configuration
@AutoConfigureAfter(TestConfiguration.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE+10)
public class TestConfigurationB {

    public TestConfigurationB(){
        System.out.println("我【B】在【TestConfiguration】加载完后再加载");
    }
}
