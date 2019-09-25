package com.rongdu.loans.baiqishi.message;


/**
 *查询芝麻行业关注名单-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListResponse  extends ZhimaResponse{

	private static final long serialVersionUID = 4259339350684462661L;

	private ZmWatchListResultData resultData;
	
	public ZmWatchListResponse(){
	}

	public ZmWatchListResultData getResultData() {
		return resultData;
	}

	public void setResultData(ZmWatchListResultData resultData) {
		this.resultData = resultData;
	}
	
}
