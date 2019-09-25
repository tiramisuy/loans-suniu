package com.rongdu.loans.cust.dao;

import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.entity.CustUser;

@MyBatisDao
public interface SysCustUserServiceDao  {
	
	public CustUser getCustById(String id);

	public void updateCustUser(CustUser custUser);

	public void updateCustUserHistory(CustUser custUser);
}
