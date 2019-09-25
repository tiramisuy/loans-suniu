package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
* @Description:  商品信息表Entity
* @author: RaoWenbiao
* @date 2018年8月30日
 */
public class GoodsOrderVO implements Serializable{
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
	 * 	创建日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	
	/**
	 *  更新日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime;	
	
	
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
	  *invoice
	  */
	private String invoice;		
	/**
	  *status
	  */
	private String status;	
	
	/**
	 * 用户银行卡编码
	 */
	private String bankCode;
	/**
	 * 银行卡名称
	 */
	private String bankName;
	/**
	 *  发货日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date deliverTime;
	/**
	 *  支付日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date payTime;
	
	
	private String goodsName;//商品名称
	private String goodsPic;//商品图片
	private String coupon;//优惠券面值
	private String userName;//客户姓名
	private String userPhone;//客户手机
	private BigDecimal goodsPrice; //商品价格
	
	private String expressNo;	//快递单号
	

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
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


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPic() {
		return goodsPic;
	}

	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	
}
