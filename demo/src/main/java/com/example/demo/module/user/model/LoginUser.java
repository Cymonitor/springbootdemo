package com.example.demo.module.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther chenyong
 * @date 2020/5/25 10:50
 *
 * 用户表对象
 */
@Data
public class LoginUser {

    private String id;

    private String roleId;

    private String username;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //对外输出格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //对内输入格式化
    private Date createTime;
}
