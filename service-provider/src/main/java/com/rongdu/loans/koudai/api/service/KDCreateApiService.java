package com.rongdu.loans.koudai.api.service;

import com.rongdu.loans.koudai.vo.create.KDCreateResultVO;

public interface KDCreateApiService {
	/**
	 * 
	* @Title: createOrder
	* @Description: 创建订单
	* @param applyId
	* @return    设定文件
	* @return KDCreateResultVO    返回类型
	* @throws
	 */
	KDCreateResultVO createOrder(String applyId);

}
