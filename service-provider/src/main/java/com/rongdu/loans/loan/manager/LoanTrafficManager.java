package com.rongdu.loans.loan.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.loan.dao.LoanTrafficDAO;
import com.rongdu.loans.loan.entity.LoanTraffic;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("loanTrafficManager")
public class LoanTrafficManager extends BaseManager<LoanTrafficDAO, LoanTraffic, String> {

	@Autowired
	private LoanTrafficDAO loanTrafficDAO;

	public List<Map<String, Object>> getExtensionPlatformListByCondition(
			HashMap<String, Object> condition) {
		List<Map<String, Object>> list = loanTrafficDAO.getExtensionPlatformListByCondition(condition);
		return list;
	}

	public void addHitLoanTraffic(String id) {
		loanTrafficDAO.addHitLoanTraffic(id);
		
	}
	
	
	

	
}
