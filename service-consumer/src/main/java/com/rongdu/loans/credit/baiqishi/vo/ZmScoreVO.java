package com.rongdu.loans.credit.baiqishi.vo;


import com.rongdu.loans.credit.common.CreditApiVo;

/**
 *  查询芝麻信用分-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmScoreVO extends CreditApiVo {

	private static final long serialVersionUID = -5080226885193678756L;
	/**
	 * 白骑士对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String flowNo; 
	/**
	 * 芝麻信用对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String bizNo;
	/**
	 *  用户的芝麻信用评分。分值范围 [350,950] 。如果用户数据不足，无法评分时，返回字符串 "N/A"
	 */
	private String zmScore;
	
	/**
	 * 查询芝麻信用评分日期
	 */
	private String queryDate;

	public ZmScoreVO() {

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

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getZmScore() {
		return zmScore;
	}

	public void setZmScore(String zmScore) {
		this.zmScore = zmScore;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}


}
