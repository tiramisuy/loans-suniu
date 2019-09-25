package com.rongdu.loans.baiqishi.message;

import com.rongdu.loans.baiqishi.common.BaiqishiConfig;

import java.io.Serializable;

/**
 * 用户访问授权-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZhimaRequest implements Serializable{

	private static final long serialVersionUID = -3292416688246655557L;
	/**
	 * 第三方用户唯一凭证，白骑士分配
	 * 必填：是
	 */
	private String partnerId = BaiqishiConfig.partnerId;
	/**
	 *验证码，白骑士分配
	 * 必填：是
	 */
	private String verifyKey = BaiqishiConfig.verifyKey;
	/**
	 *商户标示
	 * 必填：是
	 */
	private String  linkedMerchantId = BaiqishiConfig.linkedMerchantId;
	/**
	 *产品ID
	 * 必填：是
	 */
	private String productId;
	/**
	 *业务参数
	 * 必填：是
	 */
	private ExtParam extParam = new ExtParam();
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getVerifyKey() {
		return verifyKey;
	}
	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}
	public String getLinkedMerchantId() {
		return linkedMerchantId;
	}
	public void setLinkedMerchantId(String linkedMerchantId) {
		this.linkedMerchantId = linkedMerchantId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public ExtParam getExtParam() {
		return extParam;
	}
	public void setExtParam(ExtParam extParam) {
		this.extParam = extParam;
	}


}