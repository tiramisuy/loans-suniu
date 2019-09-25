package com.rongdu.loans.cust.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class MessageOP  implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3633979767551577231L;

    /**
     *消息id
     */
    @NotBlank(message="消息id不能为空")
    private String msgId;
    
	/**
	  *在哪个终端查看（1-ios，2-android，3-H5，4-网站）^([1-4])$
	  */
    @NotBlank(message="消息来源不能为空")
    @Pattern(regexp="1|2|3|4",message="消息来源类型有误")
	private String viewSource;	

    /**
     * 消息状态（0-未读，1-已读）
     */
    private Integer viewStatus;

    /**
     *查阅消息的IP地址
     */
    private String ip;

    /**
	 *通知方式(1-站内信，2-消息推送，3-短信)
	 */
	private Integer notifyType;

    /**
     *用户ID
     */
    private String userId;

	public String getMsgId() {
		return msgId;
	}

	public String getViewSource() {
		return viewSource;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public String getUserId() {
		return userId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setViewSource(String viewSource) {
		this.viewSource = viewSource;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
