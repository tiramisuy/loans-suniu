package com.rongdu.loans.pay.message;

/**
 * 分账代扣-加密数据
 * 
 * @author zhangxiaolong
 * @version 2017-08-08
 */
public class CutwithholdDataContent extends BaseDataContent {

	private static final long serialVersionUID = -1102657274087235200L;

	/**
	 * 交易子类
	 */
	private String txn_sub_type = "13";
	/**
	 * 银行代码
	 */
	private String pay_code;
	/**
	 * 安全标识
	 */
	private String pay_cm = "2";
	/**
	 * 绑定卡号 请求绑定的银行卡号
	 */
	private String acc_no;
	/**
	 * 身份证类型 默认01为身份证号
	 */
	private String id_card_type = "01";
	/**
	 * 身份证号 末尾X不区分大小写
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
	 * 卡有效期 选填
	 */
	private String valid_date = "";
	/**
	 * 卡安全码 银行卡背后最后三位数字 选填
	 */
	private String valid_no = "";
	/**
	 * 交易金额:分
	 */
	private String txn_amt;
	/**
	 * 分账信息
	 */
	private String share_info;
	/**
	 * 分账手续费商户
	 */
	private String fee_member_id;

	@Override
	public String getTxn_sub_type() {
		return txn_sub_type;
	}

	@Override
	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getPay_cm() {
		return pay_cm;
	}

	public void setPay_cm(String pay_cm) {
		this.pay_cm = pay_cm;
	}

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

	public String getTxn_amt() {
		return txn_amt;
	}

	public void setTxn_amt(String txn_amt) {
		this.txn_amt = txn_amt;
	}

	public String getShare_info() {
		return share_info;
	}

	public void setShare_info(String share_info) {
		this.share_info = share_info;
	}

	public String getFee_member_id() {
		return fee_member_id;
	}

	public void setFee_member_id(String fee_member_id) {
		this.fee_member_id = fee_member_id;
	}

}