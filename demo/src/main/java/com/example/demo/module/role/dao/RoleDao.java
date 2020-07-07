package com.example.demo.module.role.dao;


import com.example.demo.module.role.model.Role;
import org.apache.ibatis.annotations.Select;

public interface RoleDao {

    @Select("select a.id,a.name,a.level,a.create_time from t_role a left join t_user b on a.id=b.role_id where b.username=#{userName}")
    Role getRoleInfoByUser(String userName);
}
