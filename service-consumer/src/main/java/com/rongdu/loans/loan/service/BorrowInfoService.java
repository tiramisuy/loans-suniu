/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.vo.BorrowerInfoVO;

/**
 * 借款标的推送-业务逻辑接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-22
 */
public interface BorrowInfoService {

	TaskResult push();

	TaskResult pushToKoudai();

	TaskResult pushToTongrong();

	TaskResult pushToLeshi();

	/**
	 * 通联商户2放款
	 * @return
	 */
	TaskResult pushToTonglian();

	/**
	 * 满标后代扣服务费成功通知到p2p平台
	 * 
	 * @param applyId
	 */
	public void whithholdServFeeSuccessNotify(String applyId);

	/**
	 * 取消借款通知到p2p平台
	 * 
	 * @param applyId
	 * @return
	 */
	public boolean cancelLoanNotify(String applyId);

	BorrowerInfoVO getByCriteria(Criteria criteria);
	
	TaskResult pushToHanJS();
}