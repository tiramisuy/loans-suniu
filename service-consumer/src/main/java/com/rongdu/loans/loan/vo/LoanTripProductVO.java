package com.rongdu.loans.loan.vo;

import java.io.Serializable;
/**
 * 
* @Description:  旅游产品VO
* @author: 饶文彪
* @date 2018年7月12日
 */
public class LoanTripProductVO implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2943308870425637382L;
	
	private String id;          // 产品代码
	private String name;		// 产品名称
	
	private String description;		// 产品描述	
	private String status;		// 产品状态(0-下架，1-正常)
	private String imgUrl; //图片路径	
	private String descUrl;//产品描述url
	private String remark; //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDescUrl() {
		return descUrl;
	}
	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}
	
	
		
}
