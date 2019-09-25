package com.rongdu.loans.baiqishi.message;


/**
 * 查询芝麻信用认证结果-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResultResponse  extends ZhimaResponse{

	private static final long serialVersionUID = 4259339350684462661L;

	private AuthorizeResultResultData resultData;
	
	public AuthorizeResultResponse(){
	}

	public AuthorizeResultResultData getResultData() {
		return resultData;
	}

	public void setResultData(AuthorizeResultResultData resultData) {
		this.resultData = resultData;
	}
	
}
