package com.rongdu.loans.pay.message;

/**
 * 确认支付-加密数据
 * @author sunda
 * @version 2017-07-10
 */
public class ConfirmAuthPayDataContent extends BaseDataContent{
	
	private static final long serialVersionUID = 5764135980244786792L;
	/**
	 *宝付业务流水号	
	 *由宝付返回，用于交易中唯一标识一笔交易
	 */
	private String business_no;
	/**
	 *短信验证码	
	 *支付时的短信验证码，	若开通短信类交易则必填
	 */
	private String sms_code;
	
	public String getBusiness_no() {
		return business_no;
	}
	public void setBusiness_no(String business_no) {
		this.business_no = business_no;
	}
	public String getSms_code() {
		return sms_code;
	}
	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}
	
}