package com.rongdu.loans.basic.vo;

import java.io.Serializable;

public class NotificationVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5254972503541846800L;

    /**
     *消息标题
     */
    private String id;

    /**
     *消息标题
     */
    private String title;
    
    /**
     *消息概要
     */
    private String summary;
    
    /**
	  * 公告发布时间(yyyy-MM-dd HH:mm)
	  */
	private String notifyTime;
	
    /**
     *消息内容
     */
    private String content;
    /**
     *公告类型(0-平台公告)
     */
    private Integer type;

    /**
     *状态(0-待发布，1-已发布，2-已下架)
     */
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	@Deprecated
    public String getSynopsis() {
        return summary;
    }
    @Deprecated
    public void setSynopsis(String summary) {
        this.summary = summary;
    }

}
