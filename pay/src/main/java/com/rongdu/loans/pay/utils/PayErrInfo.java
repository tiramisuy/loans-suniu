package com.rongdu.loans.pay.utils;

/**
 * 宝付支付-支付结果描述
 * 
 * @author sunda
 *
 */
public enum PayErrInfo {
	
	//自定义返回码，保持统一
	P("P","处理中"),
	S("S","成功"),
	F("F","失败"),
	
	//外部返回码及描述信息
	E0("0","处理中"),
	E1("1","支付成功"),
	E01("01","支付成功"),
	E0000("0000","交易已受理"),
	E0001("0001","系统错误"),
	E0002("0002","订单超时"),
	E0011("0011","系统维护"),
	E0012("0012","无效商户"),
	E0013("0013","余额不足"),
	E0014("0014","超过支付限额"),
	E0015("0015","卡号和卡密错误"),
	E0016("0016","不合法的IP地址"),
	E0017("0017","重复订单金额不符"),
	E0018("0018","卡密已被使用"),
	E0019("0019","订单金额错误"),
	E0020("0020","支付的类型错误"),
	E0021("0021","卡类型有误"),
	E0022("0022","卡信息不完整"),
	E0023("0023","卡号、卡密、金额不正确"),
	E0024("0024","不能用此卡继续做交易"),
	E0025("0025","订单无效");

	private String code;
	private String msg;

	PayErrInfo(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
