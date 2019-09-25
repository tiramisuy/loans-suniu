/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.borrow.entity.HelpResult;
import com.rongdu.loans.borrow.manager.HelpResultManager;
import com.rongdu.loans.borrow.service.HelpResultService;
import com.rongdu.loans.borrow.vo.HelpResultVO;
/**
 * 助贷结果表-业务逻辑实现类
 * @author liuliang
 * @version 2018-08-28
 */
@Service("helpResultService")
public class HelpResultServiceImpl  extends BaseService implements  HelpResultService{
	
	/**
 	* 助贷结果表-实体管理接口
 	*/
	@Autowired
	private HelpResultManager helpResultManager;
	
	
	/**
	 * 根据助贷申请表id 查询营销结果记录
	 * 
	 * @return
	 */
	public List<HelpResultVO> getHelpResultByUserId(String userId){
		return helpResultManager.getHelpResultByUserId(userId);
	}
	
	
	public Integer insertHelpResult(HelpResultVO vo){
		return helpResultManager.insert(BeanMapper.map(vo, HelpResult.class));
	}
}