package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
/**
 * 
* @Description:  用户信息
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public class KDUserBaseOP implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
}
