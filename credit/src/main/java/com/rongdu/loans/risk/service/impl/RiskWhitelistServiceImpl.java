/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service.impl;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseService;
import com.rongdu.loans.risk.manager.RiskWhitelistManager;
import com.rongdu.loans.risk.option.WhitelistOP;
import com.rongdu.loans.risk.service.RiskWhitelistService;
import com.rongdu.loans.risk.vo.WhitelistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控白名单-业务逻辑实现类
 * @author yuanxianchu
 * @version 2018-12-27
 */
@Service("riskWhitelistService")
public class RiskWhitelistServiceImpl  extends BaseService implements  RiskWhitelistService{
	
	/**
 	* 风控白名单-实体管理接口
 	*/
	@Autowired
	private RiskWhitelistManager riskWhitelistManager;

    @Override
    public long countByUserId(String userId) {
        return riskWhitelistManager.countByUserId(userId);
    }

    @Override
	public Page<WhitelistVO> selectWhiteList(WhitelistOP op) {
		Page<WhitelistVO> voPage = new Page<WhitelistVO>(op.getPageNo(), op.getPageSize());
		List<WhitelistVO> voList = (List<WhitelistVO>) riskWhitelistManager.selectWhiteList(voPage, op);
		voPage.setList(voList);
		return voPage;
	}

	@Override
	public int deleteWhiteList(String whiteId) {
		return riskWhitelistManager.deleteTruely(whiteId);
	}
}