/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.dao;


import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.loan.entity.RepayPush;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 还款推送结果记录-数据访问接口
 * @author likang
 * @version 2017-09-08
 */
@MyBatisDao
public interface RepayPushDAO extends BaseDao<RepayPush,String> {
	/**
	 * 根据资产id更新推送结果
	 * @param repayPush
	 * @return
	 */
	int updatePushResultByAssetId(RepayPush repayPush);
	
	/**
	 * 根据资产id统计记录条数
	 * @param assetId
	 * @return
	 */
	int countByAssetId(String assetId);
	
	/**
	 * 根据资产id删除历史记录
	 * @param repayPush
	 * @return
	 */
	int deleteAssetId(String assetId);
	
	/**
	 * 根据推送类型查询还款推送失败的数据
	 * @param pushType
	 * @return
	 */
	List<RepayPush> getPushFailByType(Integer pushType);
	
	/**
	 * 查询还款到账状态
	 * @param payComOrderNo
	 * @return
	 */
	String getPayStatus(@Param("payComOrderNo")String payComOrderNo);
}
