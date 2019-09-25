package com.rongdu.loans.loan.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.LoanTraffic;

/**
 * 推广平台DAO接口
 * @author hdb
 * @version 2018-07-05
 */
@MyBatisDao
public interface LoanTrafficDAO extends BaseDao<LoanTraffic, String> {

	List<Map<String, Object>> getExtensionPlatformListByCondition(
			HashMap<String, Object> condition);

	void addHitLoanTraffic(String id);

}
