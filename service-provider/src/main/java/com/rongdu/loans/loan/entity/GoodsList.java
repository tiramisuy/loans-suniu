/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 商品信息表Entity
 * @author Lee
 * @version 2018-09-04
 */
public class GoodsList extends BaseEntity<GoodsList> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *商品名称
	  */
	private String goodsName;		
	/**
	  *商品单价
	  */
	private BigDecimal goodsPrice;
	/**
	  *市场价
	  */
	private BigDecimal marketPrice;		
	/**
	  *商品图片url
	  */
	private String goodsPic;		
	/**
	  *简称
	  */
	private String simpleName;		
	/**
	  *销量
	  */
	private String salesVolume;		
	/**
	  *展示图片
	  */
	private String picBanner;		
	/**
	  *详情
	  */
	private String picDetail;		
	/**
	  *max_coupon
	  */
	private BigDecimal maxCoupon;

	private String status;
	
	public GoodsList() {
		super();
	}

	public GoodsList(String id){
		super(id);
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}
	
	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	
	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	
	public String getPicBanner() {
		return picBanner;
	}

	public void setPicBanner(String picBanner) {
		this.picBanner = picBanner;
	}
	
	public String getPicDetail() {
		return picDetail;
	}

	public void setPicDetail(String picDetail) {
		this.picDetail = picDetail;
	}
	
	public BigDecimal getMaxCoupon() {
		return maxCoupon;
	}

	public void setMaxCoupon(BigDecimal maxCoupon) {
		this.maxCoupon = maxCoupon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}