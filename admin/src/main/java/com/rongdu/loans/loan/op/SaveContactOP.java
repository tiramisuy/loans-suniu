/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.op;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 历次贷款申请时的紧急联系人信息Entity
 * @author likang
 * @version 2017-09-28
 */
public class SaveContactOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7433001497656764991L;
	/**
	 *用户ID
	 */
	@NotBlank(message = "用户ID不能为空")
	private String userId;
	/**
	  *贷款申请编号
	  */
	@NotBlank(message = "贷款申请编号不能为空")
	private String applyId;
	/**
	 *联系人姓名
	 */
	@NotBlank(message = "联系人姓名不能为空")
	private String name;
	/**
	  *手机号码
	  */
	@NotBlank(message = "联系人手机号码不能为空")
	private String mobile;
	
	/**
	  *与本人关系(1父母，2配偶，3朋友，4同事)
	  */
	@NotBlank(message = "与本人关系不能为空")
	private String relation;
	
	/**
	  * 来源  （1：进件；2：催收）
	  */
	@NotBlank(message = "来源不能为空")
	private String source;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
}