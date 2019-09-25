package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.Date;

import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;

public class CheckLogVO implements Serializable {


	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1884927813462326822L;

	/**
	 *贷款申请ID
	 */
	private String applyId;
	/**
	 *贷款状态
	 */
	private Integer status;
	private String statusStr;
	/**
	 *操作人员
	 */
	private String operatorName;
	/**
	 *处理时间
	 */
	private Date time;

	/**
	 *拒绝原因ID
	 */
	private String refuseId;
	/**
	 * 拒绝原因
	 */
	private String refuse;
	/**
	 * 审批意见
	 */
	private String remark;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusStr = ApplyStatusLifeCycleEnum.getDesc(status);
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRefuseId() {
		return refuseId;
	}

	public void setRefuseId(String refuseId) {
		this.refuseId = refuseId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefuse() {
		return refuse;
	}

	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
}
