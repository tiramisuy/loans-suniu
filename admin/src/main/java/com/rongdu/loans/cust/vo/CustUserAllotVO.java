package com.rongdu.loans.cust.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.rongdu.common.persistence.DataEntity;
import com.rongdu.common.utils.excel.annotation.ExcelField;

/*
 * 导出客户信息实体
 */
public class CustUserAllotVO extends DataEntity<CustUserAllotVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String userName;

	private String mobile;

	private Date approveTime;
	
	private Integer status;

	private Integer approveResult;
	
	private String checkStart; // 查询开始时间

	private String checkEnd; // 查询结束时间

	private String channelName; //渠道名称
	
	
	private String updateStart;
	
	private String updateEnd;
	
	private Date updateTime;
	
	private Date allotDate;
	
	public Date getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(Date allotDate) {
		this.allotDate = allotDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateStart() {
		return updateStart;
	}

	public void setUpdateStart(String updateStart) {
		this.updateStart = updateStart;
	}

	public String getUpdateEnd() {
		return updateEnd;
	}

	public void setUpdateEnd(String updateEnd) {
		this.updateEnd = updateEnd;
	}

	private String allotName;	//分配人
	
	private String allotUserId; //分配人ID
	
	private String allotId;		//营销分配表ID
	
	private String isPush;		//拨打情况；1：永久拒绝营销，2：无人接听；3：考虑；4：需要；5：不需要；6,：第三方接听

	private String isPushStr;
	
	private String warnTime;	//提醒时间
	
	
	
	private Integer pageNo = 1;
	
    private Integer pageSize = 10;
	
	


	public String getAllotUserId() {
		return allotUserId;
	}

	public void setAllotUserId(String allotUserId) {
		this.allotUserId = allotUserId;
	}

	public String getAllotName() {
		return allotName;
	}

	public void setAllotName(String allotName) {
		this.allotName = allotName;
	}

	public String getAllotId() {
		return allotId;
	}

	public void setAllotId(String allotId) {
		this.allotId = allotId;
	}

	public String getIsPush() {
		return isPush;
	}

	public void setIsPush(String isPush) {
		this.isPush = isPush;
		if(isPush.equals("1")){
			this.isPushStr ="永久拒绝营销";
		}else if(isPush.equals("2")){
			this.isPushStr ="无人接听";
		}
		else if(isPush.equals("3")){
			this.isPushStr ="考虑";
		}
		else if(isPush.equals("4")){
			this.isPushStr ="需要";
		}
		else if(isPush.equals("5")){
			this.isPushStr ="不需要";
		}
		else if(isPush.equals("6")){
			this.isPushStr ="第三方接听";
		}
	}
	@ExcelField(title = "拨打时间", type = 1, align = 2, sort = 5)
	public String getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(String warnTime) {
		this.warnTime = warnTime;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCheckStart() {
		return checkStart;
	}

	public void setCheckStart(String checkStart) {
		this.checkStart = checkStart;
	}

	public String getCheckEnd() {
		return checkEnd;
	}

	public void setCheckEnd(String checkEnd) {
		this.checkEnd = checkEnd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "客户姓名", type = 1, align = 2, sort = 1)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "客户手机号", type = 1, align = 2, sort = 2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "注册时间", type = 1, align = 2, sort = 3)
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	@ExcelField(title = "拨打情况", type = 1, align = 2, sort = 4)
	public String getIsPushStr() {
		return isPushStr;
	}

	public void setIsPushStr(String isPushStr) {
		this.isPushStr = isPushStr;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
