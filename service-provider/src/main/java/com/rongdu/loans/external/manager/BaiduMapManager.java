package com.rongdu.loans.external.manager;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.config.BaiduMap;
import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.BaiduSnCal;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.entity.IpLocation;
import com.rongdu.loans.basic.manager.IpLocationManager;
import com.rongdu.loans.external.vo.BaiduIpLocationVO;

/**
 * 百度地图-实体管理接口
 * 
 * @author likang
 * @version 2017-08-15
 */
@Service("baiduMapManager")
public class BaiduMapManager extends BaseService {

	@Autowired
	private IpLocationManager ipLocationManager;

	// 缓存时间
	private static final int CACHESECONDS = 60 * 60;
	// 分布式锁前缀
	private static final String LOCK_PREFIX = "GeocoderLocation_Lock_";
	// 锁状态
	private static final String LOCK = "lock";

	/**
	 * ip定位
	 * 
	 * @return
	 */
	public BaiduIpLocationVO IpLocation(String ip) {
		if (StringUtils.isBlank(ip)) {
			logger.error("the param ip is null");
			return null;
		}
		// 参数设置
		LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
		// IP地址
		paramsMap.put(BaiduMap.IP_KEY, ip);
		// sk 开发者密钥
		String ak = Global.getConfig("baidu.map.ak");
		paramsMap.put(BaiduMap.AK_KEY, ak);
		// 输出的坐标格式
		paramsMap.put(BaiduMap.COOR_KEY, BaiduMap.COOR_BD09LL);
		// 用户的权限签名
		// 签名密钥
		String sk = Global.getConfig("baidu.map.sk");
		String intfaceName = Global.getConfig("baidu.map.intfacename");
		String sn = BaiduSnCal.genSn(paramsMap, sk, intfaceName);
		paramsMap.put(BaiduMap.SN_KEY, sn);

		// 获取配置文件中配置的调用url
		String url = Global.getConfig("baidu.map.url");
		if (logger.isDebugEnabled()) {
			logger.debug("url:[{}], paramsMap:[{}]", url, JsonMapper.toJsonString(paramsMap));
		}
		// 接口远程调用
		String respString = RestTemplateUtils.getInstance().getForJson(url, paramsMap);
		// 返回数据解析
		BaiduIpLocationVO ipLocationVO = (BaiduIpLocationVO) JsonMapper.fromJsonString(respString,
				BaiduIpLocationVO.class);
		return ipLocationVO;
	}

	/**
	 * ip定位
	 * 
	 * @return
	 */
	public Map geocoderLocation(String lat, String lng) {
		// ak 开发者密钥
		String ak = Global.getConfig("baidu.map.ak");

		// 获取配置文件中配置的调用url
		String url = Global.getConfig("baidu.map.geocoder.url");
		url += "?location=" + lat + "," + lng + "&output=json&pois=1&ak=" + ak;
		// 接口远程调用
		String respString = RestTemplateUtils.getInstance().getByUrl(url);
		if (StringUtils.isBlank(respString)) {
			return null;
		}

		Map result = (Map) JsonMapper.fromJsonString(respString, Map.class);
		if (result == null || (int) result.get("status") != 0) {
			return null;
		}

		return (Map) result.get("result");
	}

	/**
	 * 经纬度定位
	 * 
	 * @param ip
	 */
	public int saveLocation(String lat, String lng, String ip, String userId) {
		// 拼接分布式锁key
		String lockKey = LOCK_PREFIX + ip + "_" + userId;
		// 获取锁状态
		String sta = JedisUtils.get(lockKey);
		int rz = 0;
		// 判断锁状态
		if (null == sta) {
			// 添加缓存锁
			JedisUtils.set(lockKey, LOCK, CACHESECONDS);
			// 从百度接口获取定位信息
			Map locationMap = geocoderLocation(lat, lng);
			if (null != locationMap) {
				IpLocation entity = new IpLocation();
				entity.setUserId(userId);
				entity.setIp(ip);
				entity.setStatus(Global.IP_NOTIN_BLACKLIST);
				if (null != locationMap.get("addressComponent")) {
					Map addressDetail = (Map) locationMap.get("addressComponent");
					if (null != addressDetail) {
						// 省份
						entity.setProvince((String) addressDetail.get("province"));
						// 城市
						entity.setCity((String) addressDetail.get("city"));
						if (StringUtils.isBlank((String) addressDetail.get("city"))) {
							return rz;
						}
						// 区县
						entity.setDistrict((String) addressDetail.get("district"));
						// 街道
						entity.setStreet((String) addressDetail.get("street"));
						// 门牌
						entity.setStreetNumber((String) addressDetail.get("street_number"));
					}
					// 详细地址
					entity.setAddress((String) locationMap.get("formatted_address"));
					Map point = (Map) locationMap.get("location");
					if (null != point) {
						// 经度
						entity.setLongitude(new BigDecimal((double) point.get("lng")));
						// 纬度
						entity.setLatitude(new BigDecimal((double) point.get("lat")));
					}
				}
				entity.preInsert();
				rz = ipLocationManager.insert(entity);
				logger.info("经纬度[{},{}] 定位信息入库结果：[{}]", lng, lat, rz);
			}
			// 释放缓存锁
			// JedisUtils.del(lockKey);
		} else {
			rz = 1;
		}

		return rz;
	}

}
