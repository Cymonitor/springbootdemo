package com.example.demo.module.user.dao.provider;

import com.example.demo.module.user.model.LoginUser;
import com.example.demo.module.utils.ListUtil;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @auther chenyong
 * @date 2020/5/28 11:15
 */
public class LoginUserDaoProvider {

    /**
     * 批量新增
     * @param map
     * @return
     */
    public String batchAddUsers(Map map){
        List<LoginUser> loginUserList=(List<LoginUser>)map.get("list");
        StringBuffer batchAddBuffer=new StringBuffer("insert into t_user values");
        MessageFormat mf=new MessageFormat("(#'{'list[{0}].id},#'{'list[{0}].username},#'{'list[{0}].password},#'{'list[{0}].createTime})");
        for(int i=0;i<loginUserList.size();i++){
            batchAddBuffer.append(mf.format(new Object[]{i}));
            if(i<loginUserList.size()-1){
                batchAddBuffer.append(",");
            }
        }
        return batchAddBuffer.toString();
    }
}
