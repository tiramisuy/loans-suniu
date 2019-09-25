/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.UserDao;
import com.rongdu.loans.loan.entity.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户-实体管理实现类
 * @author zhangxiaolong
 * @version 2017-09-26
 */
@Service("userManager")
public class UserManager extends BaseManager<UserDao, User, String> {

    public List<User> getUserByRole(String role){
        return dao.getUserByRole(role);
    }
    
    public List<String> getUserMobile(){
    	return dao.getUserMobile();
    }
    
    public List<User> getRoleUserByCondition(Map<String, Object> condition){
    	return dao.getRoleUserByCondition(condition);
    			
    }
    
    public User getUserByNo(String no){
    	return dao.getUserByNo(no);
    }
    
    public User getUserByCallId(String callId){
    	return dao.getUserByCallId(callId);
    }

}