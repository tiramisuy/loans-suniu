package com.rongdu.loans.bankDeposit.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 四合一授权参数对象类
 * @author likang
 * @version 2017-08-25
 */
public class TermsAuthOP implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5490117973814345970L;

	/**
     * 进件来源（1-PC, 2-ios, 3-android,4-h5）
     */
    @NotBlank(message="进件来源不能为空")
    @Pattern(regexp="1|2|3|4",message="消息来源类型有误")
    private String source;
    
	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 短信验证码
	 */
	@NotBlank(message="短信验证码不能为空")
	private String smsCode;
	
	/**
	 * 短信序列号
	 */
	private String smsSeq;
	
	/**
	 * 电子账号
	 */
	private String accountId;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 是否维护标志位
	 */
	private String bitMap;
	
	/**
	 * 开通自动投标功能标志
	 *    (0：取消;1：开通)
	 */
	private Integer autoBid;
	
	/**
	 * 开通自动债转功能标志
	 *    (0：取消;1：开通)
	 */
	private Integer autoTransfer;
	
	/**
	 * 开通预约取现功能标志
	 *    (0：取消;1：开通)
	 */
	private Integer agreeWithdraw;
	
	/**
	 * 开通无密消费功能标识
	 *    (0：取消;1：开通)
	 */
	private Integer directConsume;
	
	/**
	 * 交易渠道
	 *    (000001手机APP;000002网页;000003微信;000004柜面)
	 */
//	private String channel;
	
//	/**
//	 * 保留域
//	 */
//	private String reserved;
//	
//	/**
//	 * 第三方保留域
//	 */
//	private String acqRes;

	public String getMobile() {
		return mobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getBitMap() {
		return bitMap;
	}

	public Integer getAutoBid() {
		return autoBid;
	}

	public Integer getAutoTransfer() {
		return autoTransfer;
	}

	public Integer getAgreeWithdraw() {
		return agreeWithdraw;
	}

	public Integer getDirectConsume() {
		return directConsume;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setBitMap(String bitMap) {
		this.bitMap = bitMap;
	}

	public void setAutoBid(Integer autoBid) {
		this.autoBid = autoBid;
	}

	public void setAutoTransfer(Integer autoTransfer) {
		this.autoTransfer = autoTransfer;
	}

	public void setAgreeWithdraw(Integer agreeWithdraw) {
		this.agreeWithdraw = agreeWithdraw;
	}

	public void setDirectConsume(Integer directConsume) {
		this.directConsume = directConsume;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
