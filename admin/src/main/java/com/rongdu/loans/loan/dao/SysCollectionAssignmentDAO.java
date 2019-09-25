package com.rongdu.loans.loan.dao;

import java.util.List;
import java.util.Map;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.vo.ChannelVO;

@MyBatisDao
public interface SysCollectionAssignmentDAO  {
	

	public List<Map<String, Object>> findOverdueListByCondition(
			Map<String, Object> condition);
}
