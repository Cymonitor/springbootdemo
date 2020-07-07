package com.example.demo.module.test.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @antor ChenYong
 * @Date 14:54 2018/7/30
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test")
    //@RequiresPermissions(value = "user:test")
    public String getHelloWord() {
        return "hello word";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login begin!!!";
    }

    @RequestMapping(value = "/success")
    public String success(){
        return "login success!!!";
    }


    @RequestMapping(value = "/fault")
    public String error(){
        return "login fault!!!";
    }
}
