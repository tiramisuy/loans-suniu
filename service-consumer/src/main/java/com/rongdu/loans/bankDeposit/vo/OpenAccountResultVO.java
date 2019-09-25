package com.rongdu.loans.bankDeposit.vo;

import java.io.Serializable;

/**
 * 开户返回结果对象
 * @author likang
 *
 */
public class OpenAccountResultVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 152113193933858860L;
    
    /**
	 * 是否成功
	 */
    private boolean success = false;
	/**
	 * 应答代码
	 */
    private String code;
	/**
	 * 应答消息
	 */
    private String msg;
    /**
	 * 电子账户
	 */
    private String accountId;
    
    private Integer isNewUser;
    
    
    private Integer open_account;
    //开户地址
    private String url;
    
	public boolean isSuccess() {
		return success;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getIsNewUser() {
		return isNewUser;
	}
	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getOpen_account() {
		return open_account;
	}
	public void setOpen_account(Integer open_account) {
		this.open_account = open_account;
	}
	
	
}
