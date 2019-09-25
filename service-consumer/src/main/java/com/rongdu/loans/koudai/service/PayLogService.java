/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.service;

import java.math.BigDecimal;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.koudai.op.PayLogListOP;
import com.rongdu.loans.koudai.vo.PayLogListVO;

/**
 * 口袋放款日志-业务逻辑接口
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
public interface PayLogService {
	public Page findList(Page page, PayLogListOP op);

	public BigDecimal sumCurrPayedAmt();
	
	public int updateByApplyId(PayLogListVO payLog);
}