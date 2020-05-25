package com.example.demo.module.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @antor ChenYong
 * @Date 14:54 2018/7/30
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test")
    public String getHelloWord() {
        return "hello word";
    }
}
