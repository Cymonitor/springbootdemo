package com.example.demo.module.permission.service.impl;

import com.example.demo.module.permission.dao.PermissionDao;
import com.example.demo.module.permission.model.Permission;
import com.example.demo.module.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther chenyong
 * @date 2020/7/2 14:34
 */
@Service
public class PermissionImpl implements PermissionService {


    @Autowired
    PermissionDao permissionDao;

    @Override
    public Permission getPermissionInfoByRole(String roleId) {
        return permissionDao.getPermissionInfo(roleId);
    }
}
