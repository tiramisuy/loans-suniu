/*package com.rongdu.loans.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.basic.dao.AreaDao;
import com.rongdu.loans.basic.entity.Area;
import com.sun.tools.jdi.LinkedHashMap;

public class AreaDaoTest extends SpringTransactionalContextTests {
	
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void  findList() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		System.out.println("==========findList==========");
		
//		List<Area> list = areaDao.findList(new Area());
//		List<Map<String, Object>>  provinceList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> province = null;
//		for (Area area:list) {
//			if ("1".equals(area.getLevelType())) {
//				province = new LinkedHashMap();
//				province.put("id", area.getId());
//				province.put("name", area.getName());
//				province.put("parentId", area.getParentId());
//				province.put("levelType", area.getLevelType());
//				province.put("cityList", getCityList(list,area.getId()));
//				provinceList.add(province);
//			}
//		}
//		System.out.println(JsonMapper.toJsonString(provinceList));		
	}

	private List<Map<String, Object>> getCityList(List<Area> list, String provinceId) {
		List<Map<String, Object>>  cityList = new ArrayList<Map<String, Object>>();
		Map<String, Object> city = null;
		for (Area area:list) {
			if (provinceId.equals(area.getParentId())) {
				city = new LinkedHashMap();
				city.put("id", area.getId());
				city.put("name", area.getName());
				city.put("parentId", area.getParentId());
				city.put("levelType", area.getLevelType());
				city.put("districtList",getDistrictList(list,area.getId()));
				cityList.add(city);
			}
		}
		return cityList;
	}

	private List<Map<String, String>> getDistrictList(List<Area> list, String cityId) {
		List<Map<String, String>> districtList = new ArrayList<Map<String, String>>();
		Map<String, String> district = null;
		for (Area area:list) {
			if (cityId.equals(area.getParentId())) {
				district = new LinkedHashMap();
				district.put("id", area.getId());
				district.put("name", area.getName());
				district.put("parentId", area.getParentId());
				district.put("levelType", area.getLevelType());
				districtList.add(district);
			}
		}
		return districtList;
	}

	
}*/