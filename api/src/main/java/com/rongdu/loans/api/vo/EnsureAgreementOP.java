
package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class EnsureAgreementOP implements Serializable{

	private static final long serialVersionUID = 4002595697459450610L;

	private String userId; //用户Id
	private String cityId; //开户行地址
	private String openAccount; //是否需要开户 1-需要 0-不需要
	private String goodsAddress; //收货地址
	private String goodsProvince; //省
	private String goodsCity; //市
	private String goodsDistrict; //区
	private String email; //邮箱
	
	private String tripProductId;//旅游产品id
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getOpenAccount() {
		return openAccount;
	}
	public void setOpenAccount(String openAccount) {
		this.openAccount = openAccount;
	}
	public String getGoodsAddress() {
		return goodsAddress;
	}
	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}
	public String getGoodsProvince() {
		return goodsProvince;
	}
	public void setGoodsProvince(String goodsProvince) {
		this.goodsProvince = goodsProvince;
	}
	public String getGoodsCity() {
		return goodsCity;
	}
	public void setGoodsCity(String goodsCity) {
		this.goodsCity = goodsCity;
	}
	public String getGoodsDistrict() {
		return goodsDistrict;
	}
	public void setGoodsDistrict(String goodsDistrict) {
		this.goodsDistrict = goodsDistrict;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTripProductId() {
		return tripProductId;
	}
	public void setTripProductId(String tripProductId) {
		this.tripProductId = tripProductId;
	}
		
	
}
