/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.option;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * 助贷申请表Entity
 * 
 * @author liuliang
 * @version 2018-08-28
 */
@Data
public class HelpApplyOP implements Serializable {

	private static final long serialVersionUID = 4875219213409839537L;
	
	private String id;
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "手机号码格式错误")
	private String mobile;
	/**
	 * 客户姓名
	 */
	@NotBlank(message = "姓名不能为空")
	private String userName;
	/**
	 * 银行卡号
	 */
	@NotBlank(message = "银行卡号不能为空")
	// @CreditCardNumber(message="银行卡号格式错误")
	private String cardNo;
	/**
	 * 服务费
	 */
	@NotNull(message = "金额不能为空")
	// 大于等于100且最多保留两位小数
	@Min(value = 100, message = "请填写正确的金额")
	@Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "请填写正确的金额")
	private String serviceAmt;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 身份证号
	 */
	@NotBlank(message = "身份证号不能为空")
	private String idNo;

	private String ip;
	/**
	 * 支付成功时间
	 */
	private Date payTime;
	/**
	 * 支付成功日期：yyyymmdd
	 */
	private Integer payDate;
	/**
	 * 申请时间
	 */

	private String applyTimeStart;

	private String applyTimeEnd;
	/**
	 * 是否支付0：未支付 1：已支付 2:支付中
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