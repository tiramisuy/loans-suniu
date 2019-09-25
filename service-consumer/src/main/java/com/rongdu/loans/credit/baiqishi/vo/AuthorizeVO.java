package com.rongdu.loans.credit.baiqishi.vo;


import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 用户访问授权-应答报文
 * 
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeVO extends CreditApiVo {

	private static final long serialVersionUID = -8739060836060138767L;

	/**
	 * 白骑士对于每一次请求返回的业务号，后续可以通过此业务号进行对账
	 */
	private String flowNo;
	/**
	 * 芝麻信用认证界面
	 */
	private String authInfoUrl;

	public AuthorizeVO() {

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getAuthInfoUrl() {
		return authInfoUrl;
	}

	public void setAuthInfoUrl(String authInfoUrl) {
		this.authInfoUrl = authInfoUrl;
	}

}
