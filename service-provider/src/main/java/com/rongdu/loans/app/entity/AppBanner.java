package com.rongdu.loans.app.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 移动端banner表映射数据实体
 * @author likang
 *
 */
public class AppBanner extends BaseEntity<AppBanner> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3471286924272515911L;

	private String categoryId;	// 分类编号	
	private String title;	// Banner名称
	private String imageId;	// 图片ID	
	private String imageUrl;  // Banner图片存放地址	
	private String href;	//	跳转链接地址
	private Integer imageOrder;  // Banner展示顺序
	private String remark;  // 备注信息
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getImageOrder() {
		return imageOrder;
	}
	public void setImageOrder(Integer imageOrder) {
		this.imageOrder = imageOrder;
	}
}
