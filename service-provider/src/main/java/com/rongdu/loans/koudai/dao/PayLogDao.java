/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.op.PayLogListOP;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;

/**
 * 口袋放款日志-数据访问接口
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
@MyBatisDao
public interface PayLogDao extends BaseDao<PayLog, String> {

	List<PayLog> findList(@Param(value = "page") Page page, @Param(value = "payLogListOP") PayLogListOP payLogListOP);

	/**
	 * 
	 * @Title: findCreatingList
	 * @Description: 查询需要创建订单数据
	 * @return List<PayLog> 返回类型
	 * @throws
	 */
	public List<PayLog> findCreatingList(@Param("param") Map<String, Object> param);

	public BigDecimal sumCurrPayedAmt();
	/**
	 * 
	* @Title: findWithdrawRecode
	* @Description: 查询口袋存管提现记录
	* @return List<PayLog>    返回类型
	* @throws
	 */
	List<PayLog> findWithdrawRecode(@Param(value = "payLog") PayLog payLog);
	
	/**
	 * 
	* @Title: findKDWaitingLendingList
	* @Description: 查询口袋存管 待放款订单
	* @return List<PayLog>    返回类型
	* @throws
	 */
	List<PayLog> findKDWaitingLendingList();
	
	/**
	 * 
	* @Title: findKDUnWithdrawOrderList
	* @Description: 查询口袋存管 已放款未提现订单
	* @return List<PayLog>    返回类型
	* @throws
	 */
	List<PayLog> findKDUnWithdrawOrderList(@Param(value = "payTime") String payTime);
	
	
	
	/**
	 * 口袋放款统计
	 * @param payop
	 * @return
	 */
	public List<Map<String, Object>> getPayCount(@Param(value = "op")KDPayCountOP op);
	
	/**
	 * 
	* @Title: findPayLogList
	* @Description: 查询处理中payLog
	* @return List<PayLog>    返回类型
	* @throws
	 */
	List<PayLog> findPayLogList(@Param(value = "pay_channel") Integer pay_channel,@Param(value = "pay_status") Integer pay_status,@Param(value = "pay_time") String pay_time);

}