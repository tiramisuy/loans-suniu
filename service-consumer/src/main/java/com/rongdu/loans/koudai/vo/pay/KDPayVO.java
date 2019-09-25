package com.rongdu.loans.koudai.vo.pay;

import java.io.Serializable;

public class KDPayVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;// 打款状态，详见PayCodeEnum
	private String msg; // 申请结果描述
	private Integer third_platform;// 第三方通道编号
	private String pay_order_id;// 口袋订单号

	public boolean isSuccess() {
		return code.intValue() == 0;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getThird_platform() {
		return third_platform;
	}

	public void setThird_platform(Integer third_platform) {
		this.third_platform = third_platform;
	}

	public String getPay_order_id() {
		return pay_order_id;
	}

	public void setPay_order_id(String pay_order_id) {
		this.pay_order_id = pay_order_id;
	}

}
