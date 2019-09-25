package com.rongdu.loans.cust.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.rongdu.common.persistence.DataEntity;
import com.rongdu.common.utils.excel.annotation.ExcelField;

/*
 * 导出客户信息实体
 */
public class CustUserExportVO extends DataEntity<CustUserExportVO> {

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

	private String succCount;	//成功放款次数/复贷次数
	


	public String getSuccCount() {
		return succCount;
	}

	public void setSuccCount(String succCount) {
		this.succCount = succCount;
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

	@ExcelField(title = "审核时间", type = 1, align = 2, sort = 2)
	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
