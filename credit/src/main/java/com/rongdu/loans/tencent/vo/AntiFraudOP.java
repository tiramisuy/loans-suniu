package com.rongdu.loans.tencent.vo;

import java.io.Serializable;
/**
 * 腾讯-反欺诈服务-请求参数
 * @author sunda
 * @version 2017-08-14
 */
public class AntiFraudOP implements Serializable {

	private static final long serialVersionUID = 6297666052880082771L;

	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 手机号码：国家代码
	 手机号，如0086-15912345678
	 注意0086前不需要+号
	 */
	private String phoneNumber;
	/**
	 * 用户请求来源IP
	 */
	private String userIp;
	/**
	 * imei 国际移动用户识别码
	 */
	private String imei;
	/**
	 * idfa ios系统广告标示符
	 */
	private String idfa;

	//其他可选字段
	/**
	 * 业务场景ID， 0：借贷（默认值） 1：支付
	 */
	private String scene;
	/**
	 * 姓名(注意：使用中文参与鉴权签名)
	 */
	private String name;
	/**
	 * 用户邮箱地址
	 */
	private String emailAddress;
	/**
	 * 用户住址
	 */
	private String address;
	/**
	 * MAC地址
	 */
	private String mac;
	/**
	 * 国际移动用户识别码
	 */
	private String imsi;
	/**
	 * 关联的腾讯帐号1：QQ开放帐号 2：微信开放帐号
	 */
	private String accountType;
	/**
	 * 可选的QQ或微信openid
	 */
	private String uid;
	/**
	 * qq或微信分配网站或应用的appid，用来唯一标识网站或应用
	 */
	private String appId;
	/**
	 * wifimac
	 */
	private String wifiMac;
	/**
	 * WIFI服务集标识
	 */
	private String wifiSSID;
	/**
	 * WIFI-BSSID
	 */
	private String wifiBSSID;
	/**
	 * 业务ID，在多个业务中使用此服务，通过此ID区分统计数据
	 */
	private String businessId;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWifiBSSID() {
		return wifiBSSID;
	}

	public void setWifiBSSID(String wifiBSSID) {
		this.wifiBSSID = wifiBSSID;
	}

	public String getWifiSSID() {
		return wifiSSID;
	}

	public void setWifiSSID(String wifiSSID) {
		this.wifiSSID = wifiSSID;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getWifiMac() {
		return wifiMac;
	}

	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}