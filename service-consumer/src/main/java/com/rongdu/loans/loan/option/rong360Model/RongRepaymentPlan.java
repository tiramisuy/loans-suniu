package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.common.utils.DateUtils;

import lombok.Data;

/**  
* @Title: RepaymentPlant.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongRepaymentPlan implements Serializable {

	private static final long serialVersionUID = -6536564274143743424L;

	/**
	 * 期数
	 */
	@JsonProperty("period_no")
    private Integer periodNo;
	
	/**
	 * 到期时间
	 */
	@JsonProperty("due_time")
    private int dueTime;
    
    /**
	 * 还款总金额
	 */
    private BigDecimal amount;
    
    /**
	 * 已还金额
	 */
    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;
    
    /**
	 * 支持的还款方式类型
	 * 1=主动还款；2=跳转H5；4=银行代扣；8=跳转支付宝还款 
	 * 除了3,7,11,15外，支持以上数字的其他组合，
	 * 例如：5=1+4同时支持主动还款和银行代扣; 6=2+4同时支持跳转H5和银行代扣;
	 */
    @JsonProperty("pay_type")
    private Integer payType;
    
    /**
	 * 账单状态
	 * 1未到期；2已还款；3逾期
	 */
    @JsonProperty("bill_status")
    private Integer billStatus;
    
    /**
	 * 还款成功时间
	 * 时间戳，秒，当账单状态bill_status为2时必传
	 */
    @JsonProperty("success_time")
    private int successTime;
    
    /**
	 * 可以还款时间
	 * 时间戳，秒，当期最早可以还款的时间
	 */
    @JsonProperty("can_repay_time")
    private int canRepayTime;
    
    /**
	 * 描述	
	 */
    private String remark;
    
    /**
	 * 是否可以展期
	 * 支持展期时传递，必须传1。否则传0或不传
	 */
    @JsonProperty("is_able_defer")
    private Integer isAbleDefer;
    
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
