package com.rongdu.loans.pay.message;

/**
 * 直接绑卡-加密数据
 * @author sunda
 * @version 2017-07-10
 */
public class BindCardDataContent extends BaseDataContent{
	
	private static final long serialVersionUID = -1102657274087235200L;
	/**
	 * 绑定卡号
	 *	请求绑定的银行卡号
	 */
	private String acc_no;
	/**
	 * 身份证类型
	 *	默认01为身份证号
	 */
	private String id_card_type = "01";
	/**
	 * 身份证号
	 *末尾X不区分大小写
	 */
	private String id_card;
	/**
	 * 持卡人姓名
	 */
	private String id_holder;
	/**
	 * 银行卡绑定手机号
	 */
	private String mobile;
	/**
	 * 	卡有效期
	 * 选填
	 */
	private String valid_date = "";
	/**
	 * 	卡安全码
	 * 银行卡背后最后三位数字
	 * 选填
	 */
	private String valid_no ="";
	/**
	 * 银行代码
	 */
	private String pay_code;
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getId_card_type() {
		return id_card_type;
	}
	public void setId_card_type(String id_card_type) {
		this.id_card_type = id_card_type;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getId_holder() {
		return id_holder;
	}
	public void setId_holder(String id_holder) {
		this.id_holder = id_holder;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getValid_date() {
		return valid_date;
	}
	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	public String getValid_no() {
		return valid_no;
	}
	public void setValid_no(String valid_no) {
		this.valid_no = valid_no;
	}
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	
	
}