package com.example.demo.module.user.service;

import com.example.demo.module.user.model.LoginUser;
import sun.rmi.runtime.Log;

import java.util.List;

public interface LoginUserService {

    LoginUser getLoginUserByUserName(String userName);

    Long addLoginUser(LoginUser loginUser);

    void batchAddLoginUser(List<String> names);

    void batchAddLoginUser2(List<String> names);
}
