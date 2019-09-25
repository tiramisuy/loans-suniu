package com.rongdu.loans.tongdun.vo;

import java.io.Serializable;

/**
 * 同盾-命中规则详情查询-请求报文
 * @author sunda
 * @version 2017-07-10
 */
public class RuleDetailOP implements Serializable{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 之前调用riskService得到的SequenceID
	 */
	private String sequenceId;

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

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
}
