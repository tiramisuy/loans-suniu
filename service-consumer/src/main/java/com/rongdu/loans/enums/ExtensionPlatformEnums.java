package com.rongdu.loans.enums;

public enum ExtensionPlatformEnums {
	ZMB("安心小贷",
		"1分钟申请,5分钟到账",
		"http://api.jubaoqiandai.com/img/logo/pro/anxin_logo.png",
		"https://axxd.fanzhoutech.com/mobile/phoneverification?par=FC79328F8C395C7AD4AD06148B527472");

	private ExtensionPlatformEnums(String name, String produce, String imageUrl, String platFormurl) {
		this.name = name;
		this.produce = produce;
		this.imageUrl = imageUrl;
		this.platFormurl = platFormurl;
	}
	
	private String name;
	private String produce;
	private String imageUrl;
	private String platFormurl;
	
	public String getPlatFormurl() {
		return platFormurl;
	}
	public void setPlatFormurl(String platFormurl) {
		this.platFormurl = platFormurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
