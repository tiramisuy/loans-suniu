
package com.rongdu.loans.api.web.option;


public class XianJinBaiKaRequest {
	private String ua; // 开放平台标识
	private String call;// 本次请求要调用的接口标识
	private String args;// 接口的请求参数, 为JSON String.
	private String sign;// 请求签名
	private String timestamp;// 以秒为单位的UnixTimestamp时间戳

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
