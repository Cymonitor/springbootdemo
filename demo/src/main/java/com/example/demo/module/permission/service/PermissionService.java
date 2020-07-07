package com.example.demo.module.permission.service;

import com.example.demo.module.permission.model.Permission;

public interface PermissionService {

    Permission getPermissionInfoByRole(String roleId);
}
