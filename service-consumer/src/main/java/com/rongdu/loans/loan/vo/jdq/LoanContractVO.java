package com.rongdu.loans.loan.vo.jdq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**  
* @Title: LoanContractVO.java  
* @Package com.rongdu.loans.loan.vo.jdq  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年10月16日  
* @version V1.0  
*/
@Data
public class LoanContractVO implements Serializable{

	private static final long serialVersionUID = -5648067203102843742L;
	
	/**
	 * 合同名称
	 */
	@JsonProperty("contract_name")
	private String contractName;
	
	/**
	 * 同h5静态链接地址（非接口地址，用户点击协议直接打开）
	 */
	private String url;



}
