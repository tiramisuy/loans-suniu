/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.option;

import java.io.Serializable;

import lombok.Data;

/**
 * 助贷申请表Entity
 * 
 * @author liuliang
 * @version 2018-08-28
 */
@Data
public class HelpApplyForListOP implements Serializable {

	private static final long serialVersionUID = 4875219213409839537L;
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 客户姓名
	 */
	private String userName;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 服务费
	 */
	private String serviceAmt;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 身份证号
	 */
	private String idNo;

	private String ip;

	/**
	 * 申请时间
	 */

	private String applyTimeStart;

	private String applyTimeEnd;
	/**
	 * 是否支付0：未支付 1：已支付
	 */
	private Integer status;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 交易状态
	 */
	private String retCode;
	/**
	 * 交易返回信息
	 */
	private String retMsg;

	private Integer pageNo = 1;
	private Integer pageSize = 10;
}