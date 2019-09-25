/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.sys.entity.User;

/**
 * 用户DAO接口
 * @author sunda
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 通过角色查询用户列表
	 * @param role
	 * @return
	 */
	List<User> getUserByRole(String role);
	
	/**
	 * 通过角色和部门查询用户列表
	 * @param role
	 * @return
	 */
	List<User> getUserByRoleAndDept(@Param("role")String role,@Param("deptCodes")List<String> deptCodes);
	
	/*
	 * 查询所有office表中remark为2的用户
	 */
	List<User> findUserByOffice();
	
	/**
	 * code y0516
	* @param @param userIds
	* @param @return    参数  
	* @return List<User>    返回类型  
	 */
	List<User> findUserByUserIds(@Param("userIds") List<String> userIds);
	
	
	
	/**
	 * 根据不同的条件查询用户
	 * @param condition
	 * @return
	 */

	public List<Map<String, Object>> getUserListByCondition(
			Map<String, Object> condition);

	
	/**
	 * 根据不同条件查出有催收部门的公司
	 * @param condition
	 * @return
	 */

	public List<Map<String, Object>> getCSCompanyListByCondition(
			Map<String, Object> condition);
	
	
	/**
	 * 通过条件查询对应角色用户列表
	 * @param role
	 * @return
	 */
	public List<User> getRoleUserByCondition(Map<String, Object> condition);

}
