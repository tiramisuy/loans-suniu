package com.rongdu.loans.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 操作说明
 */
public class Operations {
	/**
	 * 查询详情
	 */
	public static final String get = "get";
	/**
	 * 新增
	 */
	public static final String save = "save";
	/**
	 * 新增
	 */
	public static final String insert = "insert";
	/**
	 * 修改
	 */
	public static final String update = "update";
	/**
	 * 删除
	 */
	public static final String delete = "delete";
	/**
	 * 批量删除
	 */
	public static final String batchDelete = "batchDelete";
	/**
	 * 查询列表
	 */
	public static final String findPage = "findPage";
	
	private static final Map<String, String> map = new HashMap<String, String>();
	
	static{
		map.put(get, "查询详情");
		map.put(save, "新增");
		map.put(insert, "新增");
		map.put(update, "修改");
		map.put(delete, "删除");
		map.put(batchDelete, "批量删除");
		map.put(findPage, "查询列表");
	}
	
	public static String get(String key){
		return map.get(key);
	}
	
}
