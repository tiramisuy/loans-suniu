package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongApproveFeedBackOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongApproveFeedBackOP implements Serializable{

	private static final long serialVersionUID = 5396657334223952730L;
	
	/**
	 * 订单编号	
	 */
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * 审批结论		
	 */
	private String conclusion;
	
	/**
	 * 审批通过时间	
	 */
	@JsonProperty("approval_time")
	private String approvalTime;
	
	/**
	 * 审批金额是否固定	
	 */
	@JsonProperty("amount_type")
	private String amountType;
	
	/**
	 * 审批金额	
	 */
	@JsonProperty("approval_amount")
	private BigDecimal approvalAmount;
	
	/**
	 * 期限类型	
	 */
	@JsonProperty("term_unit")
	private String termUnit;
	
	/**
	 * 审批期限是否固定
	 */
	@JsonProperty("term_type")
	private String termType;
	
	/**
	 * 审批天（月）数-固定	
	 */
	@JsonProperty("approval_term")
	private Integer approvalTerm;
	
	/**
	 * 总还款额
	 */
	@JsonProperty("pay_amount")
	private BigDecimal payAmount;
	
	/**
	 * 总还款额组成说明	
	 */
	private String remark;
	
	/**
	 * 总到账金额
	 */
	@JsonProperty("receive_amount")
	private BigDecimal receiveAmount;
	
	/**
	 * 是否需要缴纳会员费
	 */
	@JsonProperty("is_vip")
	private String isVip;
	
	/**
	 * 会员等模式扣费金额
	 */
	@JsonProperty("recharge_amount")
	private String rechargeAmount;
	
	/**
	 * 审批拒绝时间
	 */
	@JsonProperty("refuse_time")
	private String refuseTime;
}
