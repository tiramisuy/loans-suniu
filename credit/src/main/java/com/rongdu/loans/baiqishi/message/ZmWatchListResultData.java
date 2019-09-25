package com.rongdu.loans.baiqishi.message;

import com.rongdu.loans.credit.baiqishi.vo.ZmWatchListDetail;

import java.util.List;

/**
 * 查询芝麻行业关注名单-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListResultData{

	/**
	 * true= 命中 在关注名单中 false= 未命中
	 */
	private boolean isMatched;
	/**
	 * true=成功 false= 失败
	 */
	private boolean success;
	
	/**
	 *白骑士对于每一次请求返回的业务号。后续可以通过此业务号进行对账
	 */
	private String bizNo;
	/**
	 * 行业关注名单信息列表。 备注：1 、l level  字段当前为保留字段，只返回 0 ，当前请忽略该字段 2 、各种编码由芝麻进行维护和升级，会存在新增编码的可能
	 */
	private List<ZmWatchListDetail> details;
	
	public boolean isMatched() {
		return isMatched;
	}
	public void setMatched(boolean isMatched) {
		this.isMatched = isMatched;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public List<ZmWatchListDetail> getDetails() {
		return details;
	}
	public void setDetails(List<ZmWatchListDetail> details) {
		this.details = details;
	}
	

}
