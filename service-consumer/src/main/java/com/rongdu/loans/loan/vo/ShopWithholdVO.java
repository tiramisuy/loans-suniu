package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;
import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * 直接绑卡应答消息
 * 
 * @author sunda
 * @version 2017-07-20
 */
public class ShopWithholdVO implements Serializable {

	private static final long serialVersionUID = 5451809413349822648L;

	private String id;
	/**
	 * 申请Id
	 */
	private String applyId;
	/**
	  *扣款用户ID
	  */
	private String custUserId;
	/**
	 * 代扣次数
	 */
	private Integer withholdNumber;
	/**
	 * 代扣时间
	 */
	private Date withholdTime;
	/**
	 * 代扣状态
	 */
	private Integer withholdStatus;
	/**
	 * 代扣金额
	 */
	private BigDecimal withholdFee;
	
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
	protected int del = BaseEntity.DEL_NORMAL;
	
	
	private String realName;
	
	private String idNo;
	
	private String mobile;
	
	private String statusStr;
	
	@ExcelField(title = "备注", type = 1, align = 2, sort = 9)
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

	@ExcelField(title = "创建时间", type = 1, align = 2, sort = 4)
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

	private Integer pageNo = 1;
	
	private Integer pageSize = 10;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@ExcelField(title = "代扣次数", type = 1, align = 2, sort = 8)
	public Integer getWithholdNumber() {
		return withholdNumber;
	}

	public void setWithholdNumber(Integer withholdNumber) {
		this.withholdNumber = withholdNumber;
	}

	@ExcelField(title = "代扣时间", type = 1, align = 2, sort = 6)
	public Date getWithholdTime() {
		return withholdTime;
	}

	public void setWithholdTime(Date withholdTime) {
		this.withholdTime = withholdTime;
	}

	public Integer getWithholdStatus() {
		return withholdStatus;
	}

	public void setWithholdStatus(Integer withholdStatus) {
		if(withholdStatus == 0){
			statusStr = "成功";
		}else if(withholdStatus ==1){
			statusStr = "失败";
		}else if(withholdStatus ==2){
			statusStr = "处理中";
		}
		this.withholdStatus = withholdStatus;
	}

	@ExcelField(title = "代扣金额", type = 1, align = 2, sort = 5)
	public BigDecimal getWithholdFee() {
		return withholdFee;
	}

	public void setWithholdFee(BigDecimal withholdFee) {
		this.withholdFee = withholdFee;
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

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		this.custUserId = custUserId;
	}

	@ExcelField(title = "借款人姓名", type = 1, align = 2, sort = 1)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@ExcelField(title = "证件号码", type = 1, align = 2, sort = 2)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title = "手机号码", type = 1, align = 2, sort = 3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "代扣状态", type = 1, align = 2, sort = 7)
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	
	
}