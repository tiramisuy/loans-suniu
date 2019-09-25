package com.rongdu.loans.koudai.vo.pay;

import java.io.Serializable;

public class KDPayQueryVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;// 打款状态，详见PayCodeEnum
	private String msg; // 申请结果描述
	private KDPayQueryDataVO data;

	public boolean isSuccess() {
		return code.intValue() == 0;
	}

	public boolean isIng() {
		return code.intValue() == 1002;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public KDPayQueryDataVO getData() {
		return data;
	}

	public void setData(KDPayQueryDataVO data) {
		this.data = data;
	}

}
