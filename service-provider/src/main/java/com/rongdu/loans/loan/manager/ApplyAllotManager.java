/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.ApplyAllotDao;
import com.rongdu.loans.loan.entity.ApplyAllot;
import com.rongdu.loans.loan.option.ApplyAllotOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.vo.ApplyAllotVO;

/**
 * 贷款订单分配表-实体管理实现类
 * @author liuliang
 * @version 2018-07-12
 */
@Service("applyAllotManager")
public class ApplyAllotManager extends BaseManager<ApplyAllotDao, ApplyAllot, String> {
	
	public List<ApplyAllotVO> getAllotApplyList(Page page, ApplyListOP op) {
		return dao.getAllotApplyList(page, op);
	}
	
	public int insertAllot(ApplyAllotVO allot){
		return dao.insertAllot(allot);
	}
	
	public int updateAllot(ApplyAllotOP op){
		return dao.updateAllot(op);
	}
}