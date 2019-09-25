/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.basic.entity.Area;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.basic.service.AreaService;
import com.rongdu.loans.basic.vo.AreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区-业务逻辑实现类
 * @author sunda
 * @version 2017-08-23
 */
@Service("areaService")
public class AreaServiceImpl  extends BaseService implements  AreaService{
	
	/**
 	* 地区-实体管理接口
 	*/
	@Autowired
	private AreaManager areaManager;

	/**
	 * 获得所有的地区List
	 * @return
	 */
	public List<AreaVO> getAllAreaList(){
		String cacheId = Global.ALL_AREA_LIST_CACHE_KEY;
		List<AreaVO>  voList = (List<AreaVO>)JedisUtils.getObjectList(cacheId);
		JedisUtils.del(cacheId);
		voList = null;
		if(null == voList) {
			Criteria criteria = new Criteria();
			List<Area> list = areaManager.findAllByCriteria(criteria);
//			Class clazz = new ArrayList<AreaVO>().getClass();
			voList = BeanMapper.mapList(list, AreaVO.class);
			if (voList!=null){
				JedisUtils.setObjectList(cacheId,voList,Global.ONE_DAY_CACHESECONDS);
			}
		}
		return voList;
	}

	/**
	 * 获得所有的地区Map
	 * @return
	 */
	public Map<String,AreaVO> getAllAreaMap(){
		String cacheId = Global.ALL_AREA_MAP_CACHE_KEY;
		Map<String,AreaVO>  map = (Map)JedisUtils.getObjectMap(cacheId);
		map = null;
		if(null == map) {
			map = new HashMap<String,AreaVO>();
			List<AreaVO> list = getAllAreaList();
			for (AreaVO vo:list){
				map.put(vo.getId(),vo);
			}
			if (map!=null){
				JedisUtils.setObjectMap(cacheId,map,Global.ONE_DAY_CACHESECONDS);
			}
		}
		return  map;
	}
	
}