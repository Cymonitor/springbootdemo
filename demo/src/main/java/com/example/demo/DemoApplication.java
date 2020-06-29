package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//当存在某些类不在DemoApplication同级包或者同级以下包目录下时，需要通过加上@ComponentScan
//并且指明包路径来扫描指定包下的所有类，因为默认情况下运行DemoApplication的main方法下的SpringApplication.run(DemoApplication.class, args);
//只会扫描同级包或者同级以下包目录下的所有类
//@ComponentScan(value = "com.cy.boot")
@ComponentScan(value = "com.example.demo")
//@MapperScan 使得在dao层无需创建实现类，其本身会为dao层创建代理实现类，该配置与在每个dao接口上加注解@Mapper简便很多
//同时提供如：@select，@insert等数据库操作接口，可直接访问数据库
@MapperScan("com.example.demo.module.*.dao")//(跟在每个dao上加入@Mapper注解一样)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
