package com.cy.boot.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @auther chenyong
 * @date 2020/4/15 9:36
 *  * 说明ApplicationRunner和CommandLineRunner区别在于方法入参不一样，但是ApplicationRunner的入参
 *  * ApplicationArguments包含CommandLineRunne的入参，可通过getSourceArgs方法获取CommandLineRunner
 *  * 的入参，追踪SpringBoot.run方法可发现在最底下有个this.callRunners(context, applicationArguments);
 *  * 这个便是收集所有ApplicationRunner和CommandLineRunner的所有实现类并执行它们的run方法
 */
@Component
@Order(1)
public class ExtendCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Springboot项目启动后加载自定义配置类======================》"+this.getClass().getName());
    }
}
