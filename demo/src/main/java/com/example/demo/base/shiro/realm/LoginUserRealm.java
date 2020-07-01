package com.example.demo.base.shiro.realm;

import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther chenyong
 * @date 2020/7/1 15:49
 */
public class LoginUserRealm extends AuthorizingRealm {

    @Autowired
    LoginUserService loginUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("开始执行认证方法:doGetAuthorizationInfo");
        Subject subject=SecurityUtils.getSubject();
        //获取登入信息
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;
        System.out.println("当前登入人："+usernamePasswordToken.getUsername());
        LoginUser loginUser=loginUserService.getLoginUserByUserName(usernamePasswordToken.getUsername());
        if(loginUser==null){ //无用户
            throw new RuntimeException("用户信息为空，无法登入");
        }
        if(!usernamePasswordToken.getUsername().equals(loginUser.getUsername())){
            throw new RuntimeException("用户信息有误，无法登入");
        }
        //获取session，session为null则创建
        Session session=subject.getSession();
        if(session.getAttribute("loginUser")==null){
            session.setAttribute("loginUser",loginUser.getUsername());
        }else{
            System.out.println(session.getAttribute("loginUser"));
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(),"");
    }
}
