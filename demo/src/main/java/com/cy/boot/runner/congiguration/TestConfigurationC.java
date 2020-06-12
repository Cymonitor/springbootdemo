package com.cy.boot.runner.congiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @auther chenyong
 * @date 2020/6/5 15:38
 */
@Configuration
@AutoConfigureAfter(TestConfiguration.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class TestConfigurationC {
    public TestConfigurationC(){
        System.out.println("我【C】在【TestConfiguration】加载完后再加载");
    }
}
