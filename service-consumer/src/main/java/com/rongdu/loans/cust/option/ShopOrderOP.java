package com.rongdu.loans.cust.option;


import java.io.Serializable;

/**
 * Created by fy on 2018/08/29.
 */
public class ShopOrderOP implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -6423601315460498460L;

	private String id;		// 订单ID
    private String userName;		// 真实姓名
    private String mobile;		// 手机号码
    private String deliveryName;		// 收货人真实姓名
    private String deliveryMobile;		// 收货人手机号码
    private String status;		// 订单状态0（未付款） 1(已付款)  2(取消) 3 (未发货) 4 (已发货)
    
    private Integer pageNo = 1;
	private Integer pageSize = 10;
	
    private String startTime;		// 申请开始时间
    private String endTime;		// 申请结束时间
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryMobile() {
		return deliveryMobile;
	}
	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
}
