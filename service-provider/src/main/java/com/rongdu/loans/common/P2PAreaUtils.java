package com.rongdu.loans.common;

import java.util.List;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.ProvinceService;
import com.rongdu.loans.basic.vo.CityVO;
import com.rongdu.loans.basic.vo.ProvinceVO;

public class P2PAreaUtils {
	public static final String provinceCacheKey = "P2PArea_province";
	public static final String cityCacheKey = "P2PArea_city";

	public static int getProvince(String name) {
		ProvinceService provinceService = SpringContextHolder.getBean(ProvinceService.class);
		List<ProvinceVO> list = (List<ProvinceVO>) JedisUtils.getObject(provinceCacheKey);
		if (list == null) {
			list = provinceService.getAllList();
			JedisUtils.setObject(provinceCacheKey, list, 60 * 60 * 24);
		}
		if (list != null) {
			for (ProvinceVO vo : list) {
				if (name.equals(vo.getName())) {
					return vo.getId();
				}
			}
		}
		return 0;
	}

	public static int getCity(String provinceName, String cityName) {
		CityService cityService = SpringContextHolder.getBean(CityService.class);
		List<CityVO> list = (List<CityVO>) JedisUtils.getObject(cityCacheKey);
		if (list == null) {
			list = cityService.getAll();
			JedisUtils.setObject(cityCacheKey, list, 60 * 60 * 24);
		}
		if (list != null) {
			for (CityVO vo : list) {
				if (provinceName.equals(vo.getFname()) && cityName.equals(vo.getName())) {
					return vo.getId();
				}
			}
		}
		return 0;
	}
}
