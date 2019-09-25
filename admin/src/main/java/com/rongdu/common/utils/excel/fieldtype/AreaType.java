/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.common.utils.excel.fieldtype;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.sys.entity.Area;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author sunda
 * @version 2013-03-10
 */
public class AreaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : UserUtils.getAreaList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}
