package com.rongdu.loans.baiqishi.message;


/**
 * 芝麻信用认证-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class AuthorizeResponse  extends ZhimaResponse{

	private static final long serialVersionUID = 4259339350684462661L;

	private AuthorizeResultData resultData;
	
	public AuthorizeResponse(){
	}

	public AuthorizeResultData getResultData() {
		return resultData;
	}

	public void setResultData(AuthorizeResultData resultData) {
		this.resultData = resultData;
	}
	
}
