package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: OrderStatusVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class OrderStatusVO implements Serializable {
	
	private static final long serialVersionUID = -1773129047136959658L;

	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * 订单状态
	 */
	@JsonProperty("order_status")
	private Integer orderStatus;
	
	/**
	 * 订单状态更新时间
	 */
	@JsonProperty("update_time")
	private String updateTime;
	
	private String remark;

	@JsonIgnore
	private String channelCode;
}
