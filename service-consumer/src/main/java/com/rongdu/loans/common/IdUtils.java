package com.rongdu.loans.common;

import org.apache.commons.lang3.RandomUtils;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;

/**
 * 主键生成 帮助类
 * @author likang
 *
 */
public class IdUtils {

	/**
	 *  产品类型变形基数
	 */
	private static int DEF_PRODUCT_TYPE = 10;
	
	/**
	 * 生成申请编号
	 * @param productType
	 * @return
	 */
	public static String getApplyId(String productType) {
		// 构造拼接对象
		StringBuilder bf = new StringBuilder();
		// 申请编号第一部分, 产品类型适配：产品类型+10
		try {
			int temp = Integer.parseInt(productType);
			bf.append(temp+DEF_PRODUCT_TYPE);
		} catch(Exception e) {
			bf.append(DEF_PRODUCT_TYPE);
		}
		// 申请编号第二部分, 当前时间
		try {
			String date = DateUtils.getDate("yyyyMMddHHmmss");
			bf.append(date);
		} catch(Exception e) {
			bf.append("99991231235959");
		}
		// 申请编号第三部分, 6位随机数
		try {
			Long randomLong = RandomUtils.nextLong(1, 999999);
			bf.append(StringUtils.leftPad(String.valueOf(randomLong), 6, '0'));
		} catch(Exception e) {
			bf.append("000000");
		}
		return bf.toString();
	}
	
	/**
	 * 敏感数据掩码处理
	 * @param id
	 * @return
	 */
	public static String idMask(String id) {
		if(StringUtils.isNotBlank(id) && id.length() > 4){
			StringBuffer bf = new StringBuffer();
			bf.append(StringUtils.substring(id, 0, 2));
			if(id.length() > 11) {
				bf.append(" **** **** ");
			} else {
				bf.append("****");
			}
			bf.append(StringUtils.substring(id, (id.length() - 3), id.length()));
			return bf.toString();
		} 
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(idMask("420984198802262435"));
	}
}
