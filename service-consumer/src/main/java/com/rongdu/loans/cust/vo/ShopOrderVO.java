package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
* @Description: 客户购物订单 
* @author: RaoWenbiao
* @date 2018年8月28日
 */
public class ShopOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID，实体唯一标识
	 */
	protected String id;
	
	/**
	 * 	备注
	 */
	protected String remark;
	
	/**
	  *用户id
	  */
	private String userId;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *用户名
	  */
	private String userName;		
	/**
	  *商品id
	  */
	private Integer goodsId;		
	/**
	  *卡券id
	  */
	private Integer couponId;		
	/**
	  *优惠金额
	  */
	private BigDecimal discountAmt;		
	/**
	  *实付金额
	  */
	private BigDecimal actualPayAmt;		
	/**
	  *支付订单号
	  */
	private String payOrderId;		
	/**
	  *支付成功0=失败 1=成功
	  */
	private Integer status;		
	/**
	  *发货状态0=未发货 1=已发货
	  */
	private Integer deliverStatus;		
	/**
	  *发货时间
	  */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliverTime;		
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}
	
	public BigDecimal getActualPayAmt() {
		return actualPayAmt;
	}

	public void setActualPayAmt(BigDecimal actualPayAmt) {
		this.actualPayAmt = actualPayAmt;
	}
	
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(Integer deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	
	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
