package com.example.demo.base.shiro.realm;

import com.example.demo.module.permission.model.Permission;
import com.example.demo.module.permission.service.PermissionService;
import com.example.demo.module.role.model.Role;
import com.example.demo.module.role.service.RoleService;
import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.user.service.LoginUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @auther chenyong
 * @date 2020/7/1 15:49
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    LoginUserService loginUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户,这里的值是下面方面doGetAuthenticationInfo的最后一行
        //return new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(),"");中设置的
        LoginUser user = (LoginUser) principalCollection.getPrimaryPrincipal();
        System.out.println(user.getUsername());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色，角色信息存在 t_role 表中取
        Set<String> setRoles=new HashSet<>();
        Role role=roleService.getRoleInfoByUserName(user.getUsername());
        setRoles.add(role.getName());
        authorizationInfo.setRoles(setRoles);
        // 给该用户设置权限，权限信息存在 t_permission 表中取
        Permission permission=permissionService.getPermissionInfoByRole(role.getId());
        Set<String> setPermissions=new HashSet<>();
        setPermissions.add(permission.getPermissionName());
        authorizationInfo.setStringPermissions(setPermissions);
        return authorizationInfo;
    }

    /**
     * 权限认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken.getPrincipal());
        System.out.println(authenticationToken.getCredentials().toString());
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

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
