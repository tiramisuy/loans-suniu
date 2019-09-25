package com.rongdu.loans.basic.vo;

import java.io.Serializable;
import java.util.Date;

public class FileInfoVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5254972503541846800L;

    /**
     *业务代码
     */
    private String bizCode;
    /**
     * 第三方贷款申请编号
     */
    private String applyId;
    /**
     *用户ID
     */
    private String userId;
    /**
     *业务名称
     */
    private String bizName;
    /**
     *文件名称
     */
    private String origName;
    /**
     *访问地址
     */
    private String url;
    /**
     *文件唯一标识
     */
    private String id;

    private Date createTime;

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
