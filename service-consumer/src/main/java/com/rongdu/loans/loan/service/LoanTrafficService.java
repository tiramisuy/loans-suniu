package com.rongdu.loans.loan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.loan.option.LoanTrafficOP;
import com.rongdu.loans.loan.option.LoanTrafficStatisticsOP;
import com.rongdu.loans.loan.vo.LoanProductVO;
import com.rongdu.loans.loan.vo.LoanTrafficStatisticsVO;
import com.rongdu.loans.loan.vo.LoanTrafficVO;

/**
 * 推广平台Service接口
 * @author likang
 * @version 2017-06-21
 */



@Service
//@Transactional(readOnly = true)
public interface LoanTrafficService {

	/**
	 * 获取推广平台列表
	 * @return
	 */
	List<Map<String,Object>> getExtensionPlatformListByCondition(HashMap<String,Object> condition);
	
	/**
	 * 增加推广平台点击量
	 * @param condition
	 */
	void addHitLoanTraffic(String id);

	public LoanTrafficVO get(String id);
	
	public Page<LoanTrafficVO> findTrafficPage(Page<LoanTrafficVO> page,LoanTrafficOP op);
	
	public Integer saveOrUpdate(LoanTrafficVO loanTrafficVO);
	
	public Page<LoanTrafficStatisticsVO> findTrafficStatisticsPage(Page<LoanTrafficStatisticsVO> page, LoanTrafficStatisticsOP op);

	
}
