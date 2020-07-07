package com.example.demo.module.permission.model;

import lombok.Data;

import java.util.Date;

/**
 * @auther chenyong
 * @date 2020/7/2 14:35
 *
 * 权限表 对象
 */
@Data
public class Permission {

    private String id;

    private String permissionName;

    private String roleId;

    private Date createTime;
}
