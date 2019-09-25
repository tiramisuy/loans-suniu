package com.rongdu.loans.cust.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.cust.dao.SysCustUserServiceDao;
import com.rongdu.loans.cust.entity.CustUser;

@Service
public class SysCustUserService extends BaseService {

	@Autowired
	private SysCustUserServiceDao custUserServiceDao;

	public CustUser getCustById(String id) {
		CustUser user = custUserServiceDao.getCustById(id);
		return user;
	}

	public void updateCustUser(CustUser custUser) {
		custUserServiceDao.updateCustUser(custUser);
	}

	public void updateCustUserHistory(CustUser custUser) {
		custUserServiceDao.updateCustUserHistory(custUser);
	}
}
