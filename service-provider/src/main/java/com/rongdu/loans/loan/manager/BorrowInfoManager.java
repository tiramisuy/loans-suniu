/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.BorrowInfoDAO;
import com.rongdu.loans.loan.entity.BorrowInfo;

/**
 * 借款标的推送-实体管理实现类
 * 
 * @author zhangxiaolong
 * @version 2017-07-22
 */
@Service("borrowInfoManager")
public class BorrowInfoManager extends BaseManager<BorrowInfoDAO, BorrowInfo, String> {

	public int updatePushStatus(BorrowInfo borrowInfo) {
		return dao.updatePushStatus(borrowInfo);
	}

	public BorrowInfo getByOutsideSerialNo(String outsideSerialNo) {
		return dao.getByOutsideSerialNo(outsideSerialNo);
	}

	public BorrowInfo getByApplyId(String applyId) {
		return dao.getByApplyId(applyId);
	}

	public String getOutSideNumByApplyId(String applyId) {
		return dao.getOutSideNumByApplyId(applyId);
	}

	public String getApplyIdByOutSideNum(String outSideNum) {
		return dao.getApplyIdByOutSideNum(outSideNum);
	}

	public BigDecimal getHanjscurrPayedAmt() {
		BigDecimal hanjscurrPayedAmt = dao.getHanjscurrPayedAmt();
		if (null != hanjscurrPayedAmt) {
			return hanjscurrPayedAmt;
		}
		return BigDecimal.ZERO;
	}

	public int delByApplyId(String applyId) {
		return dao.delByApplyId(applyId);
	}

	public BigDecimal sumLeshiPayedAmt() {
		return dao.sumLeshiPayedAmt();
	}
}