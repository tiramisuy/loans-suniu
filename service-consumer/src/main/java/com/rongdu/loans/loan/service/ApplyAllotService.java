/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import javax.validation.constraints.NotNull;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.ApplyAllotOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.vo.ApplyAllotVO;

/**
 * 贷款订单分配表-业务逻辑接口
 * @author liuliang
 * @version 2018-07-12
 */
public interface ApplyAllotService {
	int insertAllot(ApplyAllotVO vo);

	
	Page<ApplyAllotVO> getAllotApplyList(@NotNull(message = "分页参数不能为空") Page page,
			@NotNull(message = "参数不能为空") ApplyListOP applyListOP);
	
	public ApplyAllotVO getById(String id);
	
	public int updateAllot(ApplyAllotOP op);
	
	public int updateCancel(String applyId);
	
	public int updateResetCheck(String applyId);
}