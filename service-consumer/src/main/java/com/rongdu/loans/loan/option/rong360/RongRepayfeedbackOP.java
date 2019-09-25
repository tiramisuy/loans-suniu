package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: RongRepayfeedbackOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongRepayfeedbackOP implements Serializable {

	private static final long serialVersionUID = -6468721436822553138L;
	
	/**
	 * 订单编号
	 */
	@JsonProperty("order_no")
	private String order_no;
	
	/**
	 * 还款期数
	 */
	@JsonProperty("period_nos")
	private String period_nos;
	
	/**
	 * 还款触发/展期触发方式
	 * 1：主动还款；2：机构方自动发起代扣 3：在融360主动申请展期
	 */
	@JsonProperty("repay_place")
	private String repay_place;
	
	/**
	 * 还款/展期状态
	 * 1：还款成功（展期成功），2：还款失败（展期失败）
	 */
	@JsonProperty("repay_status")
	private String repay_status;
	
	/**
	 * 还款成功时间
	 */
	@JsonProperty("success_time")
	private String success_time;
	
	/**
	 * 备注
	 * 失败时：返回失败原因(如余额不足，卡不支持线上还款) 
	 * 成功时：返回还款具体的组成。
	 */
	private String remark;

}
