package com.rongdu.loans.loan.vo.jdq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: CheckUserVO.java  
* @Package com.rongdu.loans.loan.vo.jdq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月19日  
* @version V1.0  
*/
@Data
public class CheckUserVO implements Serializable{

	private static final long serialVersionUID = -4068295396743532792L;
	
	/**
	 * 是否可借，0=否  1=是
	 */
	@JsonProperty("if_can_loan")
	private String ifCanLoan;
	
	/**
	 * 用户类型
	 * 0-借点钱老用户 (定义：由借点钱渠道导流到合作方的用户，非第一次申请)
	 * 1-新用户 （定义：由借点钱渠道导流到合作方的用户，第一次申请）
	 * 2-合作方老用户 （定义：该用户为机构原有用户，且非借点钱渠道注册）
	 */
	@JsonProperty("user_type")
	private String userType;
	
	/**
	 * 用户在指定日期后才可以借款，格式：yyyy-MM-dd
	 * 注：if_can_loan为0表示用户不能申请该合作机构的产品，原因有以下情况分别处理：
	 * A：命中合作机构黑名单的情况：can_loan_time传空即可
	 * B：审核失败后没有过隔离期的情况：can_loan_time需要传该用户可以申请的时间，精确到天即可例如：2017-02-29
	 */
	@JsonProperty("can_loan_time")
	private String canLoanTime;
	
	/**
	 * 可贷最大额度，单位为元
	 */
	private String amount;
	
	/**
	 * 不可借原因码,if_can_loan为0时,则该字段必须有值，值类型如下：
	 * 1=黑名单， 2=在途订单 ，3=隔离期， 4=非借点钱渠道老用户，  5=借点钱渠道老用户
	 */
	private String reason;

}
