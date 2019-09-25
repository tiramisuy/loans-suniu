package com.rongdu.common.task;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 定时任务处理结果
 * 
 * task规范：
 * 1、任务实现：需要置于task包下，并且以Task结尾，如：com.rongdu.loans.task.XxxxTask
 * 2、任务调度方法：需要返回：TaskResult，必须记录succNum、failNum
 * 
 * @author sunda
 * @version 2017-07-14
 */
public class TaskResult implements Serializable{
	
	private static final long serialVersionUID = -3595267498409997443L;
	
	/**
	  *  任务是否执行成功
   */
	private boolean success;
	/**
	  *  成功，可以返回执行情况
	  *  失败，必须返回失败原因
    */
	private String message;
	/**
	  *  成功数量
     */
	private int succNum;
	/**
	  *  失败数量
     */
	private int failNum;
	
	public TaskResult(){	
	}
	
	public TaskResult(int succNum, int failNum) {
		super();
		this.succNum = succNum;
		this.failNum = failNum;
	}
	
	public TaskResult(int succNum, int failNum, String message) {
		super();
		this.succNum = succNum;
		this.failNum = failNum;
		this.message = message;
	}

	public boolean isSuccess() {
		this.success = (0==failNum);
		return this.success;
	}

	public void setSuccess(boolean success) { 
		this.success = (0==failNum);
	}

	public String getMessage() {
		if (StringUtils.isBlank(this.message)) {
			if (0==failNum) {
				this.message = "任务处理成功";
			}else {
				this.message = "任务处理失败";
			}			
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSuccNum() {
		return succNum;
	}

	public void setSuccNum(int succNum) {
		this.succNum = succNum;
	}

	public int getFailNum() {
		return failNum;
	}

	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}

}
