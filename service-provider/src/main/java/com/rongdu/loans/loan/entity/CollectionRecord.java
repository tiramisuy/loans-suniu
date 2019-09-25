/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.util.Date;

/**
 * 催收记录Entity
 * @author zhangxiaolong
 * @version 2017-10-09
 */
public class CollectionRecord extends BaseEntity<CollectionRecord> {
	
	private static final long serialVersionUID = 1L;
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
	private String userId;
	/**
	  *客户名称
	  */
	private String userName;
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
	
	public CollectionRecord() {
		super();
	}

	public CollectionRecord(String id){
		super(id);
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
}