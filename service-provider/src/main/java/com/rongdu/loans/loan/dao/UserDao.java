/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO接口
 * @author sunda
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends BaseDao<User,String> {
	
	/**
	 * 通过角色查询用户列表
	 * @param role
	 * @return
	 */
	List<User> getUserByRole(String role);
	
	List<String> getUserMobile();
	
	public List<User> getRoleUserByCondition(Map<String, Object> condition);
	
	public User getUserByNo(String no);
	
	public User getUserByCallId(String callId);

}
