/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.StringUtils;

/**
 * 投诉工单Entity
 * @author system
 * @version 2018-08-20
 */
public class ComplainRecord extends BaseEntity<ComplainRecord> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *标题
	  */
	private String subject;		
	/**
	  *用户名称
	  */
	private String name;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *状态：1-未处理，2-待跟进，3-已处理
	  */
	private Integer status;		
	/**
	  *部门
	  */
	private String type;		
	/**
	  *紧急情况：1-一般、2-紧急
	  */
	private Integer emergency;		
	/**
	  *跟进人id
	  */
	private String handleUserId;		
	/**
	  *跟进人姓名
	  */
	private String handleUserName;		
	/**
	  *内容
	  */
	private String content;	
	
	
	/**
	 * 	备注
	 */
	protected String remark;
	
	/**
	 * 	创建者userId
	 */
	protected String createBy;
	/**
	 * 	创建日期
	 */
	protected Date createTime;
	/**
	 * 	最后修改者userId
	 */
	protected String updateBy;
	/**
	 *  更新日期
	 */
	protected Date updateTime;	

	/**
	 *  删除标识（0：正常；1：删除）
	 */
	protected int del = 0;
	
	/**
	 * 时间搜索条件
	 */
	private String startTime; // 开始时间
	private String endTime; // 结束时间
	private String channel; // 投诉渠道
	private String complainType; // 投诉类型
	private String complainPoint; // 投诉点
	private String nextTime; // 下次跟进时间
	/**
	  *状态：1-未处理，2-待跟进，3-已处理
	  */
	private String statusStr;
	/**
	  *紧急情况：1-一般、2-紧急
	  */
	private String emergencyStr;
	/**
	  *是否查看：1-未读、2-已读
	  */
	private Integer message;	
	
	
	private Date lastCreaterTime;
	
	private Date lastHanderTime;
	
	public Date getLastCreaterTime() {
		return lastCreaterTime;
	}

	public void setLastCreaterTime(Date lastCreaterTime) {
		this.lastCreaterTime = lastCreaterTime;
	}

	public Date getLastHanderTime() {
		return lastHanderTime;
	}

	public void setLastHanderTime(Date lastHanderTime) {
		this.lastHanderTime = lastHanderTime;
	}

	/**
	 * 1,创建人
	 * 2,跟进人
	 */
	private Integer isHhandleUser;
	
	public Integer getIsHhandleUser() {
		return isHhandleUser;
	}

	public void setIsHhandleUser(Integer isHhandleUser) {
		this.isHhandleUser = isHhandleUser;
	}

	public String getComplainPoint() {
		return complainPoint;
	}

	public void setComplainPoint(String complainPoint) {
		this.complainPoint = complainPoint;
	}

	public ComplainRecord() {
		super();
	}

	public ComplainRecord(String id){
		super(id);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getEmergency() {
		return emergency;
	}

	public void setEmergency(Integer emergency) {
		this.emergency = emergency;
	}
	
	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}
	
	public String getHandleUserName() {
		return handleUserName;
	}

	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		
		this.createTime = new Date();
		this.updateTime = this.createTime;
		
		this.createBy = getCurrentUser().getName();
		this.updateBy = this.createBy;
	}
	
	
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		if (StringUtils.isBlank(this.updateBy)) {
			this.updateBy = "system";
		}
		this.updateTime = new Date();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getComplainType() {
		return complainType;
	}

	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}

	public String getNextTime() {
		return nextTime;
	}

	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}

	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getEmergencyStr() {
		return emergencyStr;
	}
	public void setEmergencyStr(String emergencyStr) {
		this.emergencyStr = emergencyStr;
	}

	public Integer getMessage() {
		return message;
	}

	public void setMessage(Integer message) {
		this.message = message;
	}

	
}