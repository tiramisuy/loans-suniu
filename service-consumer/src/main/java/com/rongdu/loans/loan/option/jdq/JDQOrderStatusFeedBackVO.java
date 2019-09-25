package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**  
* @Title: JDQOrderStatusFeedBackOP.java  
* @Package com.rongdu.loans.loan.option.jdq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月12日  
* @version V1.0  
*/
@Data
public class JDQOrderStatusFeedBackVO implements Serializable{
	
	private static final long serialVersionUID = -2877767528073164236L;
	
	/**
	 * 借点钱订单号
	 */
	@JsonProperty("jdq_order_id")
	private String jdqOrderId;
	
	/**
	 * 机构方订单号，如果机构方在进件时就返回了机构的订单号，这里就可以不传
	 */
	@JsonProperty("channel_order_id")
	private String channelOrderId;
	
	/**
	 * 订单状态，0:待审核，1:已取消，2:审核失败，3:审核成功，4:待签约，5:签约失败，
	 * 6:待放款，7:已放款，8:已还清，10:逾期还清，12:已坏账，13:已逾期，
	 * 14:续期，15:放款失败（最终状态）,16:额度失效 22：待绑卡，24：待开户，25：待授权
	 */
	private String status;

	/**
	 * 审批金额，单位元，审批通过后必传 (提现后为提现金额，提现后可不传)
	 */
	@JsonProperty("approval_amount")
	private BigDecimal approvalAmount;
	
	/**
	 * 审批期数，审批通过后必传 (提现后为提现期数，提现后可不传)
	 */
	@JsonProperty("approval_periods")
	private int approvaPperiods;
	
	/**
	 * 审批每期天数，审批通过后必传 (提现后可不传)
	 */
	@JsonProperty("approval_period_days")
	private int approvalPeriodDays;
	
	/**
	 * 审批总天数，审批通过后必传 (提现后可不传)
	 */
	@JsonProperty("approval_days")
	private int approvaldays;

	/**
	 * 确认借款/合同金额，单位元，确认借款后必传
	 */
	@JsonProperty("loan_amount")
	private BigDecimal loanAmount;

	/**
	 * 确认借款/合同期数，确认借款后必传
	 */
	@JsonProperty("loan_periods")
	private int loanPeriods;

	/**
	 * 确认借款/合同每期天数，确认借款后必传（月按30计算）
	 */
	@JsonProperty("loan_period_days")
	private int loanPeriodDays;

	/**
	 * 实际到手金额，确认借款后必传
	 */
	@JsonProperty("card_amount")
	private BigDecimal cardAmount;

	/**
	 * 是否可换卡，1-是，0-否
	 */
	@JsonProperty("change_card_flag")
	private int changeCardFlag;

	/**
	 * 0-不需要存管账户提款，1-需要去存管账户提款，不需要再次去提时需再传0过来。放款后必传
	 */
	@JsonProperty("withdraw_flag")
	private int withdrawFlag;

	/**
	 *1-支持还款账户于还款日进行自动划扣（如果机构支持主动还款，用户也可进行主动还款）。 2-不支持还款账户于还款日进行自动划扣(机构必须支持用户主动还款)。 放款后必传
	 */
	@JsonProperty("autopay_flag")
	private int autopayFlag;

	/**
	 * 还款计划，放款后必传
	 */
	@JsonProperty("repayment_plan")
	private List<JDQRepaymentPlan> jdqRepaymentPlan;
	
	/**
	 * 银行卡信息，放款后必传
	 */
	@JsonProperty("bank_card_info")
	private List<JDQBankCardInfo> jdqBankCardInfo;

	@JsonIgnore
	private String channelCode;

}
