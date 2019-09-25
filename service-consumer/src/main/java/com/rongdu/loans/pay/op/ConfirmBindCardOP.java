package com.rongdu.loans.pay.op;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 确认绑卡
 * @author sunda
 */
public class ConfirmBindCardOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2620786142378971777L;

	/**
 	 * 预支付后推送的短信验证码
	 */
	@NotBlank(message = "短信验证码不能为空")
	private String msgVerCode;

	/**
 	 * 支付订单号 （预支付后返回订单号）
	 */
	@NotBlank(message = "订单号不能为空")
	private String orderNo;

	/**
	 * 预签约唯一码
	 */
	@NotBlank(message = "预签约唯一码不能为空")
	private String bindId;

	private String userId;

	/**
	 * 开户行所在地址
	 */
	private String cityId;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 来源（1-ios, 2-android, 3-h5, 4-api）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;

	/**
	 * 绑卡还是换卡
	 */
	@NotNull(message = "绑卡操作类型不能不空")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getMsgVerCode() {
		return msgVerCode;
	}

	public void setMsgVerCode(String msgVerCode) {
		this.msgVerCode = msgVerCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
