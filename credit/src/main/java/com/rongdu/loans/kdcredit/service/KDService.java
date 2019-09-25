/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.kdcredit.service;

import com.rongdu.loans.kdcredit.vo.KDBlackListOP;
import com.rongdu.loans.kdcredit.vo.KDBlackListVO;
import com.rongdu.loans.kdcredit.vo.KDTokenVO;

/**
 * 口袋
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public interface KDService {

	/**
	 * 口袋获取token
	 */
	public KDTokenVO getToken();

	/**
	 * 口袋黑名单查询
	 */
	public KDBlackListVO searchOne(KDBlackListOP op);
}