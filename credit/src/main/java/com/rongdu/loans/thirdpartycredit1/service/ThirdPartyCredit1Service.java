/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.thirdpartycredit1.service;

import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListOP;
import com.rongdu.loans.thirdpartycredit1.vo.ThirdPartyCredit1BlackListVO;

/**
 * 三方征信1
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
public interface ThirdPartyCredit1Service {

	/**
	 * 黑名单查询
	 */
	public ThirdPartyCredit1BlackListVO blacklist(ThirdPartyCredit1BlackListOP op);
	/**
	 * 黑名单查询2
	 */
	public ThirdPartyCredit1BlackListVO blacklist2(ThirdPartyCredit1BlackListOP op);
}