package com.rongdu.loans.loan.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.loan.dao.SysCollectionAssignmentDAO;

@Service
public class SysCollectionAssignmentService extends BaseService{
	
	@Autowired
	private SysCollectionAssignmentDAO sysCollectionAssignmentDAO;



	public List<Map<String, Object>> findOverdueListByCondition(
			Map<String, Object> condition) {
		List<Map<String,Object>> list =  sysCollectionAssignmentDAO.findOverdueListByCondition(condition);
		return list;
	}
}
