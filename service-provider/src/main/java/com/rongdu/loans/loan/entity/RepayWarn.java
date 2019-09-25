/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

/**
 * 还款-预提醒表Entity
 * @author fy
 * @version 2019-07-23
 */
@Data
public class RepayWarn extends BaseEntity<RepayWarn> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *还款明细id
	  */
	private String repayId;		
	/**
	  *订单id
	  */
	private String applyId;		
	/**
	  *提醒人员
	  */
	private String sysUserName;		
	/**
	  *提醒人员id
	  */
	private String sysUserId;		
	/**
	  *提醒内容
	  */
	private String content;		

}