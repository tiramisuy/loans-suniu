package com.rongdu.loans.baiqishi.message;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 用户访问授权-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZhimaResponse  extends CreditApiVo{

	private static final long serialVersionUID = 8926664259790809382L;
	/**
	 * 调用结果码
	 */
	private String resultCode;
	/**
	 * 应答消息
	 */
	private String resultDesc;
	/**
	 * 白骑士对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String flowNo; 
	
	@Override
	public boolean isSuccess() {
		if ("BQS000".equals(getResultCode())) {
			return true;
		}
		return false;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return getResultCode();
	}

	@Override
	public void setCode(String code) {
		this.code =code;
	}

	@Override
	public String getMsg() {
		return getResultDesc();
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

}
