package com.rongdu.loans.credit.baiqishi.vo;


import java.util.List;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 *  查询芝麻行业关注名单-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListVO extends CreditApiVo {

	private static final long serialVersionUID = -5282438376703737629L;
	/**
	 * 白骑士对于每一次请求返回的业务号,后续可以通过此业务号进行对账
	 */
	private String flowNo; 
	/**
	 * true= 命中 在关注名单中 false= 未命中
	 */
	private boolean isMatched;	
	/**
	 *芝麻信用对于每一次请求返回的业务号。后续可以通过此业务号进行对账
	 */
	private String bizNo;
	/**
	 * 行业关注名单信息列表。 备注：1 、l level  字段当前为保留字段，只返回 0 ，当前请忽略该字段 2 、各种编码由芝麻进行维护和升级，会存在新增编码的可能
	 */
	private List<ZmWatchListDetail> details;

	public ZmWatchListVO() {
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

	public boolean isMatched() {
		return isMatched;
	}

	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}

	public List<ZmWatchListDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ZmWatchListDetail> details) {
		this.details = details;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

}
