package com.example.demo.base.shiro.config;

import com.example.demo.base.shiro.realm.LoginUserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设值权限安全管理
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设值未认证跳转的界面
        shiroFilterFactoryBean.setLoginUrl("/login");
        /**
         * shiro的内置过滤器
         anon：无需认证就可以访问 默认
         authc：必须认证了才能访问
         user：必须拥有记住我功能才能访问
         perms：必须拥有对某个的权限才能访问
         role：拥有某个角色权限才能访问
         */
        //添加shiro的内置过滤器  设置要拦截的url
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();//拦截
        filterChainDefinitionMap.put("goods/**", "authc");// /该请求必须认证才能访问
        // filterChainDefinitionMap.put("user/**","authc");//支持通配符
        //授权
        filterChainDefinitionMap.put("goods/**", "perms[user:goods/**]");//没有这个user:add权限的会被拦截下来
        //未授权的跳转的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        //设置注销的url
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);//把设置好的过滤设置到ShiroFilterFactoryBean
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("loginUserRealm") LoginUserRealm loginUserRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //关联realm对象
        defaultWebSecurityManager.setRealm(loginUserRealm);
        return defaultWebSecurityManager;
    }

    //1. 创建realm对象 自定义的·类
    // 方法名loginUserRealm相当于在xml配置中的<bean  id="loginUserRealm" clsss="xx..."></bean>
    @Bean
    public LoginUserRealm loginUserRealm(){
        return new LoginUserRealm();
    }
}
