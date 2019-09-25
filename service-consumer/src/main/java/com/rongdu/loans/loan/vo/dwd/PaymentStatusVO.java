package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: PaymentStatusVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class PaymentStatusVO implements Serializable{
	
	private static final long serialVersionUID = -1495258936025488065L;

	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 *还款期数
	 */
	@JsonProperty("period_nos")
	private String periodNos;
	
	/**
	 * 本次还款金额，单位元
	 */
	@JsonProperty("repay_amount")
	private BigDecimal repayAmount;
	
	/**
	 * 还款状态：1=还款成功 2=还款失败
	 */
	@JsonProperty("repay_status")
	private Integer repayStatus;
	
	/**
	 * 还款触发方式：1=主动还款 2=自动代扣
	 */
	@JsonProperty("repay_place")
	private Integer repayPlace;
	
	/**
	 * 执行还款时间
	 */
	@JsonProperty("success_time")
	private String success_time;
	
	private String remark;

}
