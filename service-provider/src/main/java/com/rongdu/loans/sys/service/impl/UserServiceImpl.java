/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.entity.User;
import com.rongdu.loans.loan.manager.UserManager;
import com.rongdu.loans.sys.service.UserService;
import com.rongdu.loans.sys.vo.UserVO;

/**
 * 用户表-业务逻辑实现类
 * @author tfl
 * @version 2019-01-21
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	/**
	* 用户表-实体管理接口
	*/
	@Autowired
	private UserManager userManager;

	@Override
	public UserVO get(String id) {
		// TODO Auto-generated method stub
		return BeanMapper.map(userManager.get(id), UserVO.class);
	}

	@Override
	public UserVO findByNo(String no) {		
		return BeanMapper.map(userManager.getUserByNo(no), UserVO.class);
	}

	@Override
	public UserVO getUserByCallId(String callId) {
		// TODO Auto-generated method stub
		User user = userManager.getUserByCallId(callId);
		if(user == null){
			return null;
		}
		return BeanMapper.map(user, UserVO.class);
	}

}