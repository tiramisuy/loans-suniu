package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 
* @Description:  旅游产品
* @author: 饶文彪
* @date 2018年7月11日
 */
public class LoanTripProduct extends BaseEntity<LoanTripProduct> {
	private static final long serialVersionUID = 1L;
	
		
	private String name;		// 产品名称
	private String description;		// 产品描述
	private String status;		// 产品状态(0-下架，1-正常)
	private String imgUrl; //图片路径
	
	private String descUrl;//产品描述url
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDescUrl() {
		return descUrl;
	}
	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}
	
	
	
	
}
