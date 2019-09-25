package com.rongdu.loans.credit.baiqishi.vo;


import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 查询芝麻信用授权结果-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResultVO extends CreditApiVo {

	private static final long serialVersionUID = -5080226885193678756L;
	/**
	 * 白骑士对于每一次请求返回的业务号，后续可以通过此业务号进行对账
	 */
	private String flowNo;
	/**
	 * 是否授权  true, false
	 */
	private boolean authorized;
	/**
	 * 用户  openId
	 */
	private String openId;

	public AuthorizeResultVO() {

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

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
