package com.rongdu.loans.credit.baiqishi.vo;

import java.io.Serializable;

/**
 * 查询芝麻行业关注名单-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmWatchListOP implements Serializable{

	private static final long serialVersionUID = -7325974613223330375L;
	/**
	 *  用户ID
	 */
	private  String	userId;
	/**
	 *  applyId
	 */
	private  String	applyId;
	/**
	 *  用户openId
	 */
	private  String	openId;
	
	public ZmWatchListOP(){
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
}
