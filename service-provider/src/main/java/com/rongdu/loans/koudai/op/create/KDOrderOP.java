package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;
/**
 * 
* @Description: 创建订单接口OP 
* @author: RaoWenbiao
* @date 2018年9月19日
 */
import java.util.List;
public class KDOrderOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer timestamp;//时间戳
	private String account;//账号
	private String id_number;//订单所属用户身份证号
	private String sign;//认证签名
	private KDUserBaseOP user_base;//用户信息
	private KDOrderBaseOP order_base;//借款订单
	private KDDebitCardOP debit_card;//打款银行卡
	private KDReceiveCardOP receive_card;//扣款银行卡，不代扣可不传
	private KDRepaymentBaseOP repayment_base;//总还款信息，债转可不传
	private List<KDPeriodBaseOP> period_base;//还款计划信息，债转可不传

	private Object other;//由厂商各自上报其他相关信息

	public Integer getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public KDUserBaseOP getUser_base() {
		return user_base;
	}

	public void setUser_base(KDUserBaseOP user_base) {
		this.user_base = user_base;
	}

	public KDOrderBaseOP getOrder_base() {
		return order_base;
	}

	public void setOrder_base(KDOrderBaseOP order_base) {
		this.order_base = order_base;
	}

	public KDDebitCardOP getDebit_card() {
		return debit_card;
	}

	public void setDebit_card(KDDebitCardOP debit_card) {
		this.debit_card = debit_card;
	}

	public KDReceiveCardOP getReceive_card() {
		return receive_card;
	}

	public void setReceive_card(KDReceiveCardOP receive_card) {
		this.receive_card = receive_card;
	}

	public KDRepaymentBaseOP getRepayment_base() {
		return repayment_base;
	}

	public void setRepayment_base(KDRepaymentBaseOP repayment_base) {
		this.repayment_base = repayment_base;
	}

	public List<KDPeriodBaseOP> getPeriod_base() {
		return period_base;
	}

	public void setPeriod_base(List<KDPeriodBaseOP> period_base) {
		this.period_base = period_base;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}
	
	
	
}
