package com.example.demo.module.user.controller;

import com.example.demo.common.model.Message;
import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public Object login(String username,String password){
        try{
            Subject subject =SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
            subject.login(usernamePasswordToken);
            return Message.suc();
        }catch (UnknownAccountException e){
            e.printStackTrace();
            return Message.errMsg("登入失败,用户名输入不正确");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return Message.errMsg("登入失败,密码输入不正确");
        }
    }
    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
    public Object getLoginUsers(String userName) {
        LoginUser loginUser = loginUserService.getLoginUserByUserName(userName);
        return loginUser;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object addUserInfo(LoginUser loginUser) {
        return loginUserService.addLoginUser(loginUser);
    }

    /**
     * 通过@InsertProvider 批量新增
     * @param userName
     * @return
     */
    @RequestMapping(value = "batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public Object batchAdd(@RequestParam List<String> userName) {
        loginUserService.batchAddLoginUser(userName);
        return null;
    }

    /**
     * 通过@Insert 批量新增
     * @param userName
     * @return
     */
    @RequestMapping(value = "batchAdd2", method = RequestMethod.POST)
    @ResponseBody
    public Object batchAdd2(@RequestParam List<String> userName) {
        loginUserService.batchAddLoginUser2(userName);
        return null;
    }
}
