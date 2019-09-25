/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.dao.AreaDAO;
import com.rongdu.loans.basic.entity.Area;
import com.rongdu.loans.basic.vo.AreaSimpleVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区-实体管理实现类
 * 
 * @author sunda
 * @version 2017-08-23
 */
@Service("areaManager")
public class AreaManager extends BaseManager<AreaDAO, Area, String> {

	@Override
	public Area getById(String id) {
		return null;
	}

	public Map<String, String> getAreaCodeAndName() {
		Map<String, String> areaMap = JedisUtils.getMap(Global.AREA_CACHE_KEY);
		if (null == areaMap) {
			areaMap = new HashMap<String, String>();
			List<AreaSimpleVO> list = getDao().getAreaCodeAndName();
			if (null != list) {
				int size = list.size();
				for (int i = 0; i < size; i++) {
					AreaSimpleVO temp = list.get(i);
					if (null != temp && null != temp.getAreaCode() && null != temp.getAreaName()) {
						areaMap.put(temp.getAreaCode(), temp.getAreaName());
					}
				}
			}
			if (null != areaMap && areaMap.size() > 0) {
				// 更新缓存值
				JedisUtils.setMap(Global.AREA_CACHE_KEY, areaMap, Global.ONE_DAY_CACHESECONDS);
			}
		}
		return areaMap;
	}

	public String getAreaName(String areaCode) {
		String areaName = null;
		// 获取不到重新数据库查询
		if (StringUtils.isBlank(areaCode)) {
			areaName = getDao().getAreaName(areaCode);
		}
		return areaName;
	}

}