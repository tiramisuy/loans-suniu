package com.rongdu.loans.pay.utils;

/**
 * 宝付支付-代付结果描述
 * 
 * @author sunda
 *
 */
public enum WithdrawErrInfo{
	
	//自定义返回码，保持统一
	P("P","处理中"),
	S("S","成功"),
	F("F","失败"),
	REPAY("REPAY","重新付款"),
	
	//外部返回码及描述信息
	E0("0","转账中"),
	E1("1","转账成功"),
	E_1("-1","转账失败"),
	E2("2","转账退款"),
	E0000("0000","代付请求交易成功（交易已受理）"),
	E200("200","代付交易成功"),
	E0001("0001","商户代付公共参数格式不正确"),
	E0002("0002","商户代付证书无效"),
	E0003("0003","商户代付报文格式不正确"),
	E0004("0004","交易请求记录条数超过上限!"),
	E0201("0201","商户未开通代付业务"),
	E0202("0202","商户不存在，请联系宝付技术支持"),
	E0203("0203","商户代付业务未绑定IP，请联系宝付技术支持"),
	E0204("0204","商户代付终端号不存在，请联系宝付技术支持"),
	E0205("0205","商户代付收款方账号{}被列入黑名单代付失败"),
	E0206("0206","商户代付交易受限"),
	E0207("0207","商户和委托商户不能相同"),
	E0208("0208","商户和委托商户绑定关系不存在"),
	E0300("0300","代付交易未明，请发起该笔订单查询");



	private String code;
	private String msg;

	WithdrawErrInfo(String code, String msg){
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
