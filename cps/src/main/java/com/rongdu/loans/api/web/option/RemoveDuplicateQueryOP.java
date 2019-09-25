package com.rongdu.loans.api.web.option;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 历次贷款申请时的紧急联系人信息Entity
 * @author zhangxiaolong
 * @version 2017-08-01
 */
public class RemoveDuplicateQueryOP implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 *用户ID
	 */
	private String userId;
	/**
	 *用户姓名
	 */
	private String userName;
	/**
	  *贷款申请编号
	  */
	private String applyId;
	/**
	  *手机号码
	  */
	private String mobile;
	/**
	 * 是否允许点击链接
	 */
	private String flag;

	private String ctx;

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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
}
