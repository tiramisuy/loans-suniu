package com.rongdu.loans.api.web.vo;

import java.io.Serializable;

/**
 * 
* @Description:  小黑鱼接口返回
* @author: 饶文彪
* @date 2018年7月4日 下午2:03:14
 */
public class XiaoheiyuVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	/**
	 * 响应结果  true-成功 false-失败
	 * 
	 */
	private Boolean success;
	/**
	 * 消息代码
	 */
	private String code;
	/**
	 * 消息内容
	 */
	private String msg;

	/**
	 * 响应时间 单位：毫秒；建议接口响应时间在1 秒以内
	 */
	private Integer cost_time;
	/**
	 * 响应内容
	 */
	private XiaoheiyuDataVO data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
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

	public Integer getCost_time() {
		return cost_time;
	}

	public void setCost_time(Integer cost_time) {
		this.cost_time = cost_time;
	}

	public XiaoheiyuDataVO getData() {
		return data;
	}

	public void setData(XiaoheiyuDataVO data) {
		this.data = data;
	}

}
