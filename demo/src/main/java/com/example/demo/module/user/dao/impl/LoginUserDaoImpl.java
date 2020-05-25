package com.example.demo.module.user.dao.impl;

import com.example.demo.module.user.dao.LoginUserDao;
import com.example.demo.module.user.model.LoginUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther chenyong
 * @date 2020/5/25 10:51
 */
@Repository
public class LoginUserDaoImpl implements LoginUserDao {

    @Override
    public List<LoginUser> getLoginUserList() {
        return null;
    }
}
