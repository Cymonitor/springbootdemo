package com.example.demo.module.role.service.impl;

import com.example.demo.module.role.dao.RoleDao;
import com.example.demo.module.role.model.Role;
import com.example.demo.module.role.service.RoleService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther chenyong
 * @date 2020/7/2 14:21
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    RoleDao roleDao;

    @Override
    public Role getRoleInfoByUserName(String userName) {
        return roleDao.getRoleInfoByUser(userName);
    }
}
