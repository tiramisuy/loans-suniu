package com.rongdu.loans.koudai.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.koudai.vo.pay.KDPayVO;
import com.rongdu.loans.loan.vo.AdminWebResult;

import java.util.Map;

public interface KDPayService {

	public KDPayVO pay(String applyId);

	public TaskResult processPayingTask();

	public AdminWebResult adminPay(String payLogId);
	
	/**
	 * 
	* @Title: adminCreate
	* @Description: 管理员创建订单
	* @return AdminWebResult    返回类型
	* @throws
	 */
	public AdminWebResult adminCreate(String payLogId);
	
	/**
	 * 
	* @Title: adminCancel
	* @Description: 管理员取消订单
	* @return AdminWebResult    返回类型
	* @throws
	 */
	public AdminWebResult adminCancel(String payLogId);

	/**
	 * 
	* @Title: getConctract
	* @Description: 获取合同
	* @return MAP    返回类型
	* @throws
	 */
	public Map<String, Object> getConctract(String applyId);

	/**
	 * 
	* @Title: queryStatus
	* @Description: 查询当前订单放款状态
	* @return MAP    返回类型
	* @throws
	 */
	public Map<String, Object> queryStatus(String applyId);
	
	
	public Page getPayCount(KDPayCountOP payOP);
	/**
	 * 修改放款渠道
	 */
	public void changePaychannel(String id, String paychannel);

	/**
	 *
	 * @Title: queryAccount
	 * @Description: 查询口袋开户信息
	 * @return MAP    返回类型
	 * @throws
	 */
	public Map<String, Object> queryAccount(String idNo);
}
