package com.rongdu.loans.app.vo;

import java.io.Serializable;


public class AppBannerVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8501576667063205192L;

	private String categoryId;	// 分类编号
	private String title;	// Banner名称
	private String imageId;	// 图片ID	
	private String imageUrl;  // Banner图片存放地址	
	private String href;	//	跳转链接地址
	private Integer imageOrder;  // Banner展示顺序
	
	public String getCategoryId() {
		return categoryId;
	}
	public String getTitle() {
		return title;
	}
	public String getImageId() {
		return imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getHref() {
		return href;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getImageOrder() {
		return imageOrder;
	}
	public void setImageOrder(Integer imageOrder) {
		this.imageOrder = imageOrder;
	}

}
