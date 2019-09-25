/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 还款-预提醒表Entity
 * @author fy
 * @version 2019-07-23
 */
@Data
public class RepayWarnVO extends BaseEntity<RepayWarnVO> implements Serializable {

	private static final long serialVersionUID = 8856247276670437308L;
	/**
	  *代还款信息id
	  */
	private String repayId;
	/**
	  *客户姓名
	  */
	private String userName;
	/**
	  *客户手机号
	  */
	private String mobile;
	/**
	  *提醒人员
	  */
	private String sysUserName;
	/**
	  *提醒内容
	  */
	private String content;
	/**
	  *当前期数
	  */
	private String thisTerm;
	/**
	  *应还日期
	  */
	private String repayDate;
	/**
	  *应还总金额
	  */
	private String totalAmount;
	/**
	  *实还日期
	  */
	private String actualRepayTime;
	/**
	  *实际还款金额
	  */
	private String actualRepayAmt;
	/**
	  *预提醒结果
	  */
	private String warnResult;

	
}