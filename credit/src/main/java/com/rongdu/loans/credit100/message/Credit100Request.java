package com.rongdu.loans.credit100.message;

import com.rongdu.loans.credit100.common.Credit100Config;

import java.io.Serializable;

/**
 * 特殊名单核查-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class Credit100Request<T> implements Serializable{

	/**
	 * Api名称
	 */
	public String apiName = Credit100Config.api_name;
	/**
	 * TokenId
	 */
	public String tokenid;
	/**
	 *业务参数
	 */
	private T reqData;

	public  Credit100Request(T reqData){
		this.reqData = reqData;
	}


	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public T getReqData() {
		return reqData;
	}

	public void setReqData(T reqData) {
		this.reqData = reqData;
	}
}