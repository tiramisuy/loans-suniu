/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.service.TreeService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.sys.dao.OfficeDao;
import com.rongdu.loans.sys.entity.Office;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 机构Service
 * @author sunda
 * @version 2014-05-16
 */
@Service
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	public static final String ADMIN_CACHE_LIST_BY_COMPANY = "ADMIN_CACHE_LIST_BY_COMPANY";
	
	@Autowired
	private OfficeDao officeDao;
	
	/*
	 * 获取商户列表 如果remark为空 查询所有商户列表
	 */
	public List<Office> getAllCompany(String remark){
		return officeDao.getAllCompany(remark);
	}
	
	public List<Office> getAllCompanyFromCache(){
		List<Office> list =  (List<Office>)JedisUtils.getObject(ADMIN_CACHE_LIST_BY_COMPANY);
		if (list == null){
			list = getAllCompany(null);
			JedisUtils.setObject(ADMIN_CACHE_LIST_BY_COMPANY,
					list,
					60*60*24);
		}
		return list;
	}

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
}
