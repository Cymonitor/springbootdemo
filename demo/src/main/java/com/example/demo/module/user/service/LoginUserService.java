package com.example.demo.module.user.service;

import com.example.demo.module.user.model.LoginUser;
import sun.rmi.runtime.Log;

import java.util.List;

public interface LoginUserService {

    List<LoginUser> getLoginUserList();

    Long addLoginUser(LoginUser loginUser);
}
