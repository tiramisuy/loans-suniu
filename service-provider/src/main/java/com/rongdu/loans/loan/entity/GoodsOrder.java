/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物订单表Entity
 * @author Lee
 * @version 2018-09-07
 */
public class GoodsOrder extends BaseEntity<GoodsOrder> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *商品id
	  */
	private String goodsId;		
	/**
	  *下单账户id
	  */
	private String accountId;		
	/**
	  *收货人手机
	  */
	private String phone;		
	/**
	  *收货姓名
	  */
	private String name;		
	/**
	  *收货地址
	  */
	private String address;		
	/**
	  *结算价格
	  */
	private BigDecimal price;
	/**
	  *支付时间
	  */
	private Date payTime;
	/**
	  *发货时间
	  */
	private Date deliverTime;		
	/**
	  *发票信息
	  */
	private String invoice;		
	/**
	  *0（未付款） 1(已付款)  2(取消)  3(已发货) 4(付款中)
	  */
	private String status;		
	/**
	  *优惠券ID
	  */
	private String couponId;		
	/**
	  *优惠券面值
	  */
	private BigDecimal coupon;		
	/**
	  *账户姓名
	  */
	private String userName;		
	/**
	  *账户手机
	  */
	private String userPhone;		
	/**
	 *订单详情
	 */
	private String remark;	
	
	private String expressNo;	//快递单号
	
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public GoodsOrder() {
		super();
	}

	public GoodsOrder(String id){
		super(id);
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}
	
	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
	public BigDecimal getCoupon() {
		return coupon;
	}

	public void setCoupon(BigDecimal coupon) {
		this.coupon = coupon;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}