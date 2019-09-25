package com.rongdu.common.file;

import java.util.HashMap;
import java.util.Map;

/**
 * 文档所属的业务类型
 */
public class FileBizTypeMap {
	
	private static Map<String, FileBizCode> map = new HashMap<String, FileBizCode>();
	
	/**
	 * 业务名称
	 * @param code
	 * @return
	 */
	public static String getBizName(String type){
		FileBizCode resp = map.get(type);
		if (null != resp) {
			return resp.getBizName();
		}
		return null;
	}
	
	
	static{
		for (FileBizCode e : FileBizCode.values()) {
			map.put(e.getBizCode(), e);
        }
		
	}
	
}
