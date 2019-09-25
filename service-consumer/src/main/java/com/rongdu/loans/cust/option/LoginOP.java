package com.rongdu.loans.cust.option;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class LoginOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5968189183759588166L;

    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 登录类型
     * (1->密码登录,2->短信密码登录)
     */
    @Pattern(regexp = "1|2", message = "登录类型有误")
    private String loginType = "1";

    private String currentIp; // 当前登录ip
    private String lastIp;    // 上次登录ip
    private Date lastLoginTime;    // 上次登录时间
    private Integer loginNum;        // 登录次数
    private String userId;	// 用户ID

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
