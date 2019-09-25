package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rongdu.loans.loan.option.xjbk.RepaymentPlan;

import lombok.Data;

/**  
* @Title: RongPushrepaymentOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongPushrepaymentOP implements Serializable{

	private static final long serialVersionUID = 8437328012766314248L;

	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
    private String orderNo;
	
	/**
	 * 银行名称
	 */
    @JsonProperty("open_bank")
    private String openBank;
    
    /**
	 * 银行卡号
	 */
    @JsonProperty("bank_card")
    private String bankCard;
    
    /**
	 * 是否支持提前全部结清
	 * 仅多期产品需回传，1=支持；0=不支持
	 */
    @JsonProperty("can_prepay")
    private String canPrepay;
    
    /**
	 * 可提前全部结清的开始时间
	 * 当支持提前全部结清时需回传。十位时间戳
	 */
    @JsonProperty("can_prepay_time")
    private String canPrepayTime;
    
    /**
	 * 还款计划
	 */
    @JsonProperty("repayment_plan")
    private List<RepaymentPlan> repaymentPlan;
}
