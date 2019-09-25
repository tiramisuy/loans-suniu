/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.entity;

import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 风控白名单Entity
 * @author yuanxianchu
 * @version 2018-12-27
 */
@Data
public class RiskWhitelist extends BaseEntity<RiskWhitelist> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *证件号码
	  */
	private String idNo;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *用户姓名
	  */
	private String name;		
	/**
	  *来源类型：1-平台优质客户; 2-外部导入数据;3-特殊名单
	  */
	private Integer sourceType;		
	/**
	  *来源渠道
	  */
	private String sourceChannel;		
	/**
	  *进出白名单的时间
	  */
	private Date time;
	/**
	  *白名单状态：0-注销;1-生效
	  */
	private Integer status;		
	
}