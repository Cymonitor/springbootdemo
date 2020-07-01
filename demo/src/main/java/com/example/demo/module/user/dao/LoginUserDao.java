package com.example.demo.module.user.dao;

import com.example.demo.module.user.dao.provider.LoginUserDaoProvider;
import com.example.demo.module.user.model.LoginUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface LoginUserDao {

    @Select("select * from t_user where username=#{userName}#")
    LoginUser queryLoginUserByUserName(String userName);

    @Insert("insert into t_user values (replace(UUID(),'-',''),#{username},#{password},now())")
    Long insertUserInfo(LoginUser loginUser);

    @InsertProvider(type = LoginUserDaoProvider.class,method = "batchAddUsers")
    void batchInsert(@Param("list") List<LoginUser> loginUsers);

    @Insert("<script>insert into t_user values" +
            "<foreach collection ='list' item='loginUser'  separator=','>" +
            "(#{loginUser.id},#{loginUser.username},#{loginUser.password},#{loginUser.createTime})" +
            "</foreach>" +
            "</script>")
    void batchInsert2(List<LoginUser> list);

}
