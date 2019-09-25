package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.common.utils.DateUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**  
* @Title: JDQRepaymentPlan.java  
* @Package com.rongdu.loans.loan.option.jdq
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
@Data
public class JDQRepaymentPlan implements Serializable{

	private static final long serialVersionUID = 5301861514988468507L;
	
	/**
	 *实际还款日期 ，格式：yyyy-MM-dd
	 *注：在实际还款完成必传
	 */
	@JsonProperty("true_repayment_time")
	private String trueRepaymentTime;
	
	/**
	 * 计划还款日期，格式：yyyy-MM-dd
	 */
	@JsonProperty("plan_repayment_time")
	private String planRepaymentTime;
	
	/**
	 * 本期还款本金，单位元
	 */
	private BigDecimal amount;
	
	/**
	 * 本期手续（利息）费，单位元
	 */
	@JsonProperty("period_fee")
	private BigDecimal periodFee;
	
	/**
	 * 本期期数
	 */
	private int period;
	
	/**
	 * 还款计划状态，0:未出账， 1:待还款,，2:正常结清，3:逾期结清， 
	 * 4:逾期， 5:部分还款（已逾期），6:部分还款（未逾期），7:还款中
	 */
	private String status;
	
	/**
	 * 逾期罚款，单位元
	 * 注：如果状态为还款计划状态为0、1、2、3，overdue_fee为0
	 */
	@JsonProperty("overdue_fee")
	private BigDecimal overdueFee;
	
	/**
	 * 逾期天数
	 * 注：如果状态为还款计划状态为0、1、2、3，overdue_fee为0
	 */
	@JsonProperty("overdue_day")
	private int overdueDay;
	
	/**
	 * 是否逾期，0未逾期，1逾期
	 */
	private int overdue;

	public void setStatus(Integer status,Date repayDate,Date actualRepayTime) {
		if (status.intValue() == 0) {
			//待还款
			this.status = "1";
			if (DateUtils.daysBetween(repayDate, new Date()) > 0) {
	    		//逾期
	    		this.status = "4";
	    	}
		}else if (status.intValue() == 1) {
			//正常结清
			this.status = "2";
			if (DateUtils.daysBetween(repayDate,actualRepayTime) > 0) {
				//逾期结清
	    		this.status = "3";
			}
		}
	}
}
