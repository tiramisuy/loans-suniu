package com.rongdu.loans.baiqishi.message;


/**
 *查询芝麻信用分-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmScoreResponse  extends ZhimaResponse{

	private static final long serialVersionUID = 4259339350684462661L;

	private ZmScoreResultData resultData;
	
	public ZmScoreResponse(){
	}

	public ZmScoreResultData getResultData() {
		return resultData;
	}

	public void setResultData(ZmScoreResultData resultData) {
		this.resultData = resultData;
	}
	
}
