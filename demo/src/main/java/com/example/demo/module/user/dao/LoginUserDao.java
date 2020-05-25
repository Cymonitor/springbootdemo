package com.example.demo.module.user.dao;

import com.example.demo.module.user.model.LoginUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface LoginUserDao {

    @Select("select * from t_user")
    List<LoginUser> queryLoginUserList();

    @Insert("insert into t_user values (replace(UUID(),'-',''),#{username},#{password},#{createTime})")
    Long insertUserInfo(LoginUser loginUser);

}
