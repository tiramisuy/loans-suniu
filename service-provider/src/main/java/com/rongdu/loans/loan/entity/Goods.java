/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 商品Entity
 * @author Lee
 * @version 2018-07-04
 */
public class Goods extends BaseEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *贷款订单编号
	  */
	private String tjyOrderNo;		
	/**
	  *sku_id
	  */
	private String skuId;		
	/**
	  *用户购买数量
	  */
	private String buynum;		
	/**
	  *收货人收货的详细地址
	  */
	private String address;		
	/**
	  *收货人姓名
	  */
	private String name;		
	/**
	  *收货人手机号
	  */
	private String mobile;		
	/**
	  *备注
	  */
	private String note;		
	/**
	  *充值账号
	  */
	private String chargeaccount;		
	/**
	  *status
	  */
	private String status;		
	
	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	public String getTjyOrderNo() {
		return tjyOrderNo;
	}

	public void setTjyOrderNo(String tjyOrderNo) {
		this.tjyOrderNo = tjyOrderNo;
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public String getBuynum() {
		return buynum;
	}

	public void setBuynum(String buynum) {
		this.buynum = buynum;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getChargeaccount() {
		return chargeaccount;
	}

	public void setChargeaccount(String chargeaccount) {
		this.chargeaccount = chargeaccount;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}