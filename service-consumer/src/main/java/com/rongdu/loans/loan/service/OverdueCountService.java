/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.OverdueCountOP;
import com.rongdu.loans.loan.vo.OverdueCountVO;

/**
 * 还款计划明细-业务逻辑接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-08
 */
public interface OverdueCountService {

	
	
	
	/**
	 * 后台还款提醒列表
	 * @param op
	 * @return
	 */
	Page<OverdueCountVO> overdueCountList(OverdueCountOP op);

	
}