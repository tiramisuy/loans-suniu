package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 紧急联系人信息
 * @author likang
 * @version 2017-09-26
 */
public class ContactToCollectionVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8129749760134106723L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 *客户ID
	 */
	private String userId;
	/**
	  *贷款申请编号
	  */
	private String applyId;
	/**
	  * 与本人关系编号(1父母，2配偶，3朋友，4同事)
	  */
	private Integer relationship;
	/**
	  * 与本人关系描述(1父母，2配偶，3朋友，4同事)
	  */
	private String relationshipStr;
	/**
	  *联系人姓名
	  */
	private String name;
	/**
	  *手机号码
	  */
	private String mobile;
	/**
	  * 来源（1：进件；2：催收）
	  */
	private String source;

	/**
	 *  联系人描述
	 */
	private String detail;
	
	public String getUserId() {
		return userId;
	}
	public String getApplyId() {
		return applyId;
	}
	public Integer getRelationship() {
		return relationship;
	}
	public String getRelationshipStr() {
		return relationshipStr;
	}
	public String getName() {
		return name;
	}
	public String getMobile() {
		return mobile;
	}
	public String getSource() {
		return source;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}
	public void setRelationshipStr(String relationshipStr) {
		this.relationshipStr = relationshipStr;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
