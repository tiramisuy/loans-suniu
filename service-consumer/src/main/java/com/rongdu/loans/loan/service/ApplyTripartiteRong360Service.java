/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;


/**
 * 工单映射（融360）-业务逻辑接口
 * @author yuanxianchu
 * @version 2018-06-29
 */
public interface ApplyTripartiteRong360Service {
	
	boolean isExistApplyId(String applyId);
	
	int insertTripartiteOrder(String applyId, String orderSn);
	
	String getThirdIdByApplyId(String applyId);
	
	String getApplyIdByThirdId(String thirdId);
	
	List<String> findThirdIdsByApplyIds(List<String> applyIdList);
	
	//测试方法
	int testInsertTripartiteOrder(String applyId, String orderSn);

    int delOrderNo(String orderNo);
}