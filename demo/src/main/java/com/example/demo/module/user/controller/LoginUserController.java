package com.example.demo.module.user.controller;

import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther chenyong
 * @date 2020/5/25 10:46
 */
@RestController
@RequestMapping(value = "/loginUser/")
public class LoginUserController {

    @Autowired
    LoginUserService loginUserService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public Object getLoginUsers(){
        List<LoginUser> loginUserList=loginUserService.getLoginUserList();
        return loginUserList;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Object addUserInfo(LoginUser loginUser){
        return loginUserService.addLoginUser(loginUser);
    }
}
