/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.service.TreeService;
import com.rongdu.loans.sys.dao.AreaDao;
import com.rongdu.loans.sys.entity.Area;
import com.rongdu.loans.sys.entity.Location;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 区域Service
 * @author sunda
 * @version 2014-05-16
 */
@Service
public class AreaService extends TreeService<AreaDao, Area> {
	
	@Autowired
	private AreaDao areaDAO;

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	public List<Location> getLocationInfo(String userId){
		return areaDAO.getLocationInfo(userId);
	}
	
	public Area getById(String areaId){
		return areaDAO.get(areaId);
	}
	
	public List<Area> getAll(){
		return areaDAO.findAllList(new Area());
	}
	
	
}
