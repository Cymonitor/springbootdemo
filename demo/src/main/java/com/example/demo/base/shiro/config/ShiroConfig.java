package com.example.demo.base.shiro.config;

import com.example.demo.base.shiro.realm.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @auther chenyong
 * @date 2020/7/1 15:48
 * <p>
 * 创建shiro配置类
 */
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设值权限安全管理
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设值未认证跳转的界面
        shiroFilterFactoryBean.setLoginUrl("/login");
        /**
         * shiro的内置过滤器
         1、anon：无需认证就可以访问 默认
         2、authc：必须认证了才能访问
         3、user：必须拥有记住我功能才能访问
         4、perms：必须拥有对某个的权限才能访问
         5、role：拥有某个角色权限才能访问
         */
        //添加shiro的内置过滤器  设置要拦截的url
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();//拦截
        //以“/test/” 开头的用户需要身份认证，authc 表示要进行身份认证 ** 表示通配符
        filterChainDefinitionMap.put("/test/**", "authc");
        //filterChainDefinitionMap.put("/**", "authc");//代表所有请求

        // “/goods/” 开头的用户需要角色认证，是“manager”才允许。如果同个请求路径既要角色认证，又要权限认证则
        //可用逗号隔开如：roles[manager],perms[user:goods]
        //也可通过请求方法上加@RequiresRoles(value = "manager")
        filterChainDefinitionMap.put("/goods/**", "roles[manager]");

        // “/test/” 开头的用户需要权限认证，是“user:create”才允许
        //也可在请求方法上加上注解@RequiresPermissions(value = "user:goods")
        //filterChainDefinitionMap.put("/test/**", "perms[user:goods]");

        // 设置默认登录的URL，身份认证失败会访问该URL(即未登入情况会跳转的界面)
        shiroFilterFactoryBean.setLoginUrl("/login");

        //设置注销登出，即遇到该请求则跳转至login界面即身份认证失败会访问的URL
        filterChainDefinitionMap.put("/logout","logout");

        //未授权的跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/fault");
        //设置认证成功则跳转的界面地址
        shiroFilterFactoryBean.setSuccessUrl("/success");

        //设置注销的url
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);//把设置好的过滤设置到ShiroFilterFactoryBean
        return shiroFilterFactoryBean;
    }

    /**
     * 配置shiro安全管理器
     * beanId=defaultWebSecurityManager,默认beanid等于方法名
     * @param myShiroRealm
     * @return
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //关联realm对象
        defaultWebSecurityManager.setRealm(myShiroRealm);
        return defaultWebSecurityManager;
    }

    //1. 创建realm对象 自定义的·类
    // 方法名myShiroRealm相当于在xml配置中的<bean  id="myShiroRealm" clsss="xx..."></bean>
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return new MyShiroRealm();
    }

    /**
     * 凭证匹配，加密算法
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro 注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager  securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 开启shiro授权注解，若上面Bean未生效则使用此Bean
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

}
