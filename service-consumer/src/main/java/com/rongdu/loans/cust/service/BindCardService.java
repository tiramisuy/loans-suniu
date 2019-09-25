/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.service;

import com.rongdu.loans.cust.vo.BindCardVO;

/**
 * 绑卡信息-业务逻辑接口
 * @author sunda
 * @version 2017-07-21
 */
public interface BindCardService {
	
	/**
	 * 查询绑卡信息
	 * @param id
	 * @return
	 */
	public BindCardVO get(String id);
	
	/**
	 * 保存绑卡信息
	 * @param vo
	 * @return
	 */
	public int save(BindCardVO vo);
	
	/**
	 * 更新绑卡信息
	 * @param vo
	 * @return
	 */
	public int update(BindCardVO vo);
	
	/**
	 * 新绑卡后，解除其他绑定关系
	 * @param vo
	 */
	int cancelOtherBindInfo(BindCardVO vo);

	/**
	 * 根据订单号查询绑卡信息
	 * @param orderNo
	 * @return
	 */

	public BindCardVO findByOrderNo(String orderNo);

	/**
	 * 根据绑定号，查询绑卡信息
	 * @param bindId
	 * @return
	 */
	public BindCardVO findByBindId(String bindId);

	BindCardVO getBindCard(String mobile, String idCard, String cardNo, String realName);
}