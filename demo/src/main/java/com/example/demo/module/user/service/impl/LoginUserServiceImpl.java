package com.example.demo.module.user.service.impl;

import com.example.demo.module.user.dao.LoginUserDao;
import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import com.example.demo.module.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void batchAddLoginUser(List<String> names) {
        List<LoginUser> loginUsers=new ArrayList<>();
        for(String name:names){
            LoginUser loginUser=new LoginUser();
            loginUser.setUsername(name);
            loginUser.setPassword("123456");
            loginUser.setCreateTime(new Date());
            loginUsers.add(loginUser);
            loginUser.setId(UUIDUtils.getUUIDRandom());
        }
        loginUserDao.batchInsert(loginUsers);
    }

    @Override
    public void batchAddLoginUser2(List<String> names) {
        List<LoginUser> loginUsers=new ArrayList<>();
        for(String name:names){
            LoginUser loginUser=new LoginUser();
            loginUser.setUsername(name);
            loginUser.setPassword("123456");
            loginUser.setCreateTime(new Date());
            loginUsers.add(loginUser);
            loginUser.setId(UUIDUtils.getUUIDRandom());
        }
        loginUserDao.batchInsert2(loginUsers);
    }
}
