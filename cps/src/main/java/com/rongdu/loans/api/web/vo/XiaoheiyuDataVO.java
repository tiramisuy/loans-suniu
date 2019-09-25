package com.rongdu.loans.api.web.vo;

import java.io.Serializable;

/**
 * 
* @Description:  小黑鱼接口响应内容
* @author: 饶文彪
* @date 2018年7月4日 下午2:03:14
 */
public class XiaoheiyuDataVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	/**
	 * 用户状态  1-老用户 0-新用户
	 */
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
