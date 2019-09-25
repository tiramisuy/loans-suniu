package com.rongdu.loans.loan.vo.dwd;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.common.utils.DateUtils;

import lombok.Data;

/**  
* @Title: RepaymentPlanDetailVO.java  
* @Package com.rongdu.loans.loan.vo.dwd  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月29日  
* @version V1.0  
*/
@Data
public class RepaymentPlanDetail implements Serializable{
	
	private static final long serialVersionUID = -5089543324324582740L;
	
	@JsonProperty("period_no")
	private Integer periodNo;
	
	@JsonProperty("bill_status")
	private Integer billStatus;
	
	@JsonProperty("due_time")
	private String dueTime;
	
	@JsonProperty("can_repay_time")
	private String canRepayTime;
	
	@JsonProperty("pay_type")
	private String payType;
	
	private BigDecimal amount;
	
	@JsonProperty("period_amount")
	private BigDecimal periodAmount;
	
	@JsonProperty("paid_amount")
	private BigDecimal paidAmount;
	
	@JsonProperty("overdue_fee")
	private BigDecimal overdueFee;
	
	@JsonProperty("success_time")
	private String successTime;
	
	private String remark;
	
	private List<Billitem> billitem;
	
	public void setBillStatus(Integer status,Date repayDate) {
    	if (status.intValue() == 0) {
			this.billStatus = 1;
		}
    	if (DateUtils.daysBetween(repayDate, new Date()) > 0) {
    		this.billStatus = 3;
    	}
    	if (status.intValue() == 1) {
			this.billStatus = 2;
		}
    	
    }

}
