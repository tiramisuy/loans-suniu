package com.rongdu.loans.api.web.option;

import java.io.Serializable;
/**
 * 
* @Description:  呼叫中心 用户信息
* @author: RaoWenbiao
* @date 2019年1月16日
 */
public class CallCenterCustOP implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	//类型uuid =1：预提醒  2：人工待审核  3：注册未申请  4：待确认借款 5：已还未复贷
	private String uuid1;
	
	private String uuid2;
	public String getUuid1() {
		return uuid1;
	}
	public void setUuid1(String uuid1) {
		this.uuid1 = uuid1;
	}
	public String getUuid2() {
		return uuid2;
	}
	public void setUuid2(String uuid2) {
		this.uuid2 = uuid2;
	}
	
	
}
