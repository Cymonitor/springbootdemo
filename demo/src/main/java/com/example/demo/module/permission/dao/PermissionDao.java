package com.example.demo.module.permission.dao;

import com.example.demo.module.permission.model.Permission;
import org.apache.ibatis.annotations.Select;

public interface PermissionDao {

    @Select("select * from t_permission where role_id =#{roleId}")
    Permission getPermissionInfo(String roleId);
}
