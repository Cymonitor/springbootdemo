package com.example.demo.module.user.dao;

import com.example.demo.module.user.model.LoginUser;

import java.util.List;

public interface LoginUserDao {
    List<LoginUser> getLoginUserList();
}
