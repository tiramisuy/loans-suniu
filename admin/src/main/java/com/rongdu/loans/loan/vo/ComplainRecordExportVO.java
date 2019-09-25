/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * 投诉工单Entity
 * @author system
 * @version 2018-08-20
 */
public class ComplainRecordExportVO implements Serializable{

	private static final long serialVersionUID = -120400966015057521L;
	/**
	 * 	创建日期
	 */
	protected Date createTime;
	/**
	 * 	创建者
	 */
	protected String createBy;
	/**
	 *  投诉渠道
	 */
	private String channel;
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
	private String statusStr;	
	/**
	  *部门
	  */
	private String type;	
	/**
	  *  投诉类型
	  */
	private String complainType;
	/**
	  *紧急情况：1-一般、2-紧急
	  */
	private String emergencyStr;	
	/**
	  *跟进人姓名
	  */
	private String handleUserName;		
	/**
	  *内容
	  */
	private String content;	
	/**
	 *  更新日期
	 */
	protected Date updateTime;
	/**
	 * 	备注
	 */
	protected String remark;
	/**
	 *  下次跟进时间
	 */
	protected Date nextTime;
	
	@ExcelField(title="日期", type=1, align=2, sort=1)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="提交人", type=1, align=2, sort=2)
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@ExcelField(title="投诉渠道", type=1, align=2, sort=3)
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@ExcelField(title="用户名称", type=1, align=2, sort=4)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="手机号码", type=1, align=2, sort=5)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="状态", type=1, align=2, sort=6)
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
			this.statusStr = statusStr;
	}
	
	@ExcelField(title="部门", type=1, align=2, sort=7)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="投诉类型", type=1, align=2, sort=8)
	public String getComplainType() {
		return complainType;
	}
	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}
	
	@ExcelField(title="紧急情况", type=1, align=2, sort=9)
	public String getEmergencyStr() {
		return emergencyStr;
	}
	public void setEmergencyStr(String emergencyStr) {
		this.emergencyStr = emergencyStr;
	}
	
	@ExcelField(title="跟进人", type=1, align=2, sort=10)
	public String getHandleUserName() {
		return handleUserName;
	}
	public void setHandleUserName(String handleUserName) {
		this.handleUserName = handleUserName;
	}
	
	@ExcelField(title="投诉详情", type=1, align=2, sort=11)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="更新日期", type=1, align=2, sort=12)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title="备注", type=1, align=2, sort=13)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="下次跟进时间", type=1, align=2, sort=14)
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
}