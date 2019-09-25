/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.service;

import com.rongdu.loans.sys.vo.UserVO;

/**
 * 用户表-业务逻辑接口
 * @author tfl
 * @version 2019-01-21
 */
public interface UserService {
	
	UserVO get(String id);
	
	UserVO findByNo(String no);
	
	public UserVO getUserByCallId(String callId);
}