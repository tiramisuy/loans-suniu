/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.option;

import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 还款-预提醒表Entity
 * @author fy
 * @version 2019-07-23
 */
@Data
public class RepayWarnOP extends BaseEntity<RepayWarnOP> implements Serializable{

	private static final long serialVersionUID = -2653456751130553008L;

	private Integer pageNo = 1;
	private Integer pageSize = 10;
	/**
	  *被分配的用户id
	  */
	private String sysUserId;
	/**
	 * 客户名称
	 */
	private String userName;
	/**
	 * 客户名称
	 */
	private String mobile;
	
}