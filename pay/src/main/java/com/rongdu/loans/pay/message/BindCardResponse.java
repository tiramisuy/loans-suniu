package com.rongdu.loans.pay.message;


/**
 * 直接绑卡-应答消息
 * @author sunda
 * @version 2017-07-10
 */
public class BindCardResponse extends BaofooResponse{
	
	/**
	 * 交易卡类型
	 */
	private String pay_card_type;
	/**
	 * 绑定标识号
	 */
	private String bind_id;

	public String getPay_card_type() {
		return pay_card_type;
	}
	public void setPay_card_type(String pay_card_type) {
		this.pay_card_type = pay_card_type;
	}
	public String getBind_id() {
		return bind_id;
	}
	public void setBind_id(String bind_id) {
		this.bind_id = bind_id;
	}

}
