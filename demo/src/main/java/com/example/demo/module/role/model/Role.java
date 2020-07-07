package com.example.demo.module.role.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @auther chenyong
 * @date 2020/7/2 14:22
 *
 * 角色表对象
 */
@Data
public class Role {

    private String id;

    private String name;

    private Integer level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //对外输出格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //对内输入格式化
    private Date createTime;
}
