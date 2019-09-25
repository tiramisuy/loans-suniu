package com.rongdu.loans.pay.message;

/**
 * 确认绑卡-加密数据
 * @author sunda
 * @version 2017-07-10
 */
public class ConfirmBindCardDataContent extends BaseDataContent{
	
	private static final long serialVersionUID = 2902248943867038897L;
	/**
	 * 短信验证码
	 */
	private String sms_code;

	public String getSms_code() {
		return sms_code;
	}

	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}

	
}