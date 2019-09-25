/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.service;

import java.util.List;

import com.rongdu.loans.borrow.vo.HelpResultVO;

/**
 * 助贷结果表-业务逻辑接口
 * 
 * @author liuliang
 * @version 2018-08-28
 */
public interface HelpResultService {

	/**
	 * 根据助贷申请表id 查询营销结果记录
	 * 
	 * @return
	 */
	public List<HelpResultVO> getHelpResultByUserId(String userId);
	
	public Integer insertHelpResult(HelpResultVO vo);
}