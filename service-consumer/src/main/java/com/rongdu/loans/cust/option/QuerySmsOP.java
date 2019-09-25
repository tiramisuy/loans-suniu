package com.rongdu.loans.cust.option;

import java.io.Serializable;

import com.rongdu.common.persistence.BaseEntity;


public class QuerySmsOP extends BaseEntity<QuerySmsOP> implements Serializable {

	private static final long serialVersionUID = -5037936964652985149L;
	
	private String mobile;
	private String productId;
	private String sendStart;
	private String sendEnd;
	private String type;
	
	private Integer pageNo = 1;
    private Integer pageSize = 10;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSendStart() {
		return sendStart;
	}
	public void setSendStart(String sendStart) {
		this.sendStart = sendStart;
	}
	public String getSendEnd() {
		return sendEnd;
	}
	public void setSendEnd(String sendEnd) {
		this.sendEnd = sendEnd;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
