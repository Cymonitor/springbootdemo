package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//当存在某些类不在DemoApplication同级包或者同级一下包目录下时，需要通过加上@ComponentScan
//并且指明包路径来扫描指定包下的所有类，因为默认情况下运行DemoApplication的main方法下的SpringApplication.run(DemoApplication.class, args);
//只会扫描同级包或者同级以下包目录下的所有类
@ComponentScan(value = "com.cy.boot")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
