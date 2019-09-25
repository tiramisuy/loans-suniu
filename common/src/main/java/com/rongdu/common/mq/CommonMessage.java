package com.rongdu.common.mq;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 通用消息体
 */
public class CommonMessage<T> implements Serializable{

	private static final long serialVersionUID = -6467599133982794823L;
    /**
     * 队列的名称
     */
    private String queueName;
	/**
     * 约定的几个消息源名称，如：pay(支付系统)
     */
    private String source;
    /**
     * 消息类型，如：AccessLog
     */
    private String type;
    /**
     * 消息的主键/唯一标识
     */
    private String id;
    /**
     * 业务数据ID
     */
    private String bizId;
    /**
     * 消息实体bean
     */
    private T message;

    public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
