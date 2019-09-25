package com.rongdu.loans.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.CrudDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.cust.vo.CustUserAllotVO;
import com.rongdu.loans.cust.vo.CustUserExportVO;

@MyBatisDao
public interface CustUserExportDAO extends CrudDao<CustUserExportVO> {
	/**
	 * 查询导出客户信息
	 *//*
	public List<CustUserExportVO> selectExprotCustUser(@Param("page") Page<CustUserExportVO> page,
			@Param("op") CustUserExportOP op);*/
	
	public List<CustUserAllotVO> findMarketingUser(@Param("page")Page<CustUserAllotVO> page,@Param("custOP")CustUserAllotVO custOP);
}
