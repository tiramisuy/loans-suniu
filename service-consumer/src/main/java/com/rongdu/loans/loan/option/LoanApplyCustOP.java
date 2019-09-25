package com.rongdu.loans.loan.option;

import java.io.Serializable;

/**
 * 申请单查询出参数对象
 * @author likang
 *
 */
public class LoanApplyCustOP  implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5619359528293372265L;

	/**
	 *  未完结
	 */
	public static final Integer APPLY_STATUS_FINISHED = 1;
	/**
	 *  完结
	 */
	public static final Integer APPLY_STATUS_UNFINISH = 0;
	
	private String account;  // 账户
	private String idType;	// 用户证件类型
	private String idNo;	// 用户证件号码
	private Integer applyStatus; // 申请单状态
	private Integer processStage; // 申请阶段
	
	public String getAccount() {
		return account;
	}
	public String getIdType() {
		return idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Integer getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Integer getProcessStage() {
		return processStage;
	}
	public void setProcessStage(Integer processStage) {
		this.processStage = processStage;
	}
	
	
}
