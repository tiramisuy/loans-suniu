package com.allinpay.demo.xstruct.ver;

public class RNPARET {
	private String ISSENDSMS;	//是否已发送短信验证码
	private String RET_CODE;	//返回码	
	private String ERR_MSG;	    //错误文本	
	
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE) {
		RET_CODE = rETCODE;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG) {
		ERR_MSG = eRRMSG;
	}
	public String getISSENDSMS() {
		return ISSENDSMS;
	}
	public void setISSENDSMS(String iSSENDSMS) {
		ISSENDSMS = iSSENDSMS;
	}
}
