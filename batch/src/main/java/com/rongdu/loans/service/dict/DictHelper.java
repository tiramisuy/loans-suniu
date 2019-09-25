package com.rongdu.loans.service.dict;

import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.rongdu.core.utils.spring.SpringContextHolder;

public class DictHelper {
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> get(String code){
		CacheManager ehcacheManager = SpringContextHolder.getBean("ehcacheManager");
		Cache cache = ehcacheManager.getCache("DictItemMap");
		Element element = cache.get(code);
		Map<String, String> map = null;
		if (element==null||element.getObjectValue()==null) {
			DictItemManager dictItemManager = SpringContextHolder.getBean("dictItemManager");
			map  = dictItemManager.findDictItemMap(code);
			cache.put(new Element(code, map));				
		}else {
			map = (Map<String, String>) element.getObjectValue();
		}
//	    System.out.println("========DictHelper.get========");
//	    System.out.println(JsonBinder.buildNonNullBinder().toJson(map));
		return map;
	}
	
}
