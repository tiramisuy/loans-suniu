/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.borrow.dao.HelpResultDao;
import com.rongdu.loans.borrow.entity.HelpResult;
import com.rongdu.loans.borrow.vo.HelpResultVO;

/**
 * 助贷结果表-实体管理实现类
 * 
 * @author liuliang
 * @version 2018-08-28
 */
@Service("helpResultManager")
public class HelpResultManager extends BaseManager<HelpResultDao, HelpResult, String> {
	
	public List<HelpResultVO> getHelpResultByUserId(String userId) {
		return dao.getHelpResultByUserId(userId);
	}
}