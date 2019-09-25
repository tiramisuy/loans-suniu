/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.enums.UrgentRecallResult;

import java.io.Serializable;
import java.util.Date;

/**
 * 催收记录Entity
 * @author zhangxiaolong
 * @version 2017-10-09
 */
public class CollectionRecordVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 *ID
	 */
	private String id;
	/**
	  *还款明细ID
	  */
	private String repayPlanItemId;
	/**
	  *申请编号
	  */
	private String applyId;
	/**
	  *合同编号
	  */
	private String contNo;
	/**
	  *客户编号
	  */
	private String custId;
	/**
	  *客户名称
	  */
	private String custName;
	/**
	  *催收人员id
	  */
	private String operatorId;
	/**
	  *催收人员姓名
	  */
	private String operatorName;
	/**
	  *催收方式:1电话、2短信、3其他
	  */
	private Integer type;
	/**
	  *与本人关系(0白骑士，1父母，2配偶，3朋友，4同事)
	  */
	private Integer relationship;
	private String relationshipStr;
	/**
	  *联系人姓名
	  */
	private String contactName;
	/**
	  *手机号码
	  */
	private String contactMobile;
	/**
	  *催收结果
	  */
	private Integer result;
	private String resultStr;
	/**
	  *催收内容
	  */
	private String content;
	/**
	  *是否承诺付款（0-否，1-是）
	  */
	private Integer promise;
	/**
	  *承诺付款日期
	  */
	private Date promiseDate;
	/**
	  *下次跟进时间
	  */
	private Date nextContactTime;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
		this.relationshipStr = RelationshipEnum.getDesc(relationship);
	}

	public String getRelationshipStr() {
		return relationshipStr;
	}

	public void setRelationshipStr(String relationshipStr) {
		this.relationshipStr = relationshipStr;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
		UrgentRecallResult urgentRecallResult = UrgentRecallResult.get(result);
		if (urgentRecallResult != null){
			this.resultStr = result + "-" + urgentRecallResult.getDesc();
		}
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getPromise() {
		return promise;
	}

	public void setPromise(Integer promise) {
		this.promise = promise;
	}
	
	public Date getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}
	
	public Date getNextContactTime() {
		return nextContactTime;
	}

	public void setNextContactTime(Date nextContactTime) {
		this.nextContactTime = nextContactTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}