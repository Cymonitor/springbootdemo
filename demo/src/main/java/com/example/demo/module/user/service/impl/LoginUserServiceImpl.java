package com.example.demo.module.user.service.impl;

import com.example.demo.module.user.dao.LoginUserDao;
import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther chenyong
 * @date 2020/5/25 10:49
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserDao loginUserDao;

    @Override
    public List<LoginUser> getLoginUserList() {
        return loginUserDao.queryLoginUserList();
    }

    @Override
    public Long addLoginUser(LoginUser loginUser) {
        return loginUserDao.insertUserInfo(loginUser);
    }
}
