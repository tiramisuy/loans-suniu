package com.rongdu.loans.pay.vo;

import java.io.Serializable;

import com.rongdu.common.exception.ErrInfo;

import lombok.Data;

/**  
 * code y0602
* @Title: XfWithholdResultVO.java  
* @Description: 先锋代扣响应结果 
* @author: yuanxianchu  
* @date 2018年6月2日  
* @version V1.0  
*/
@Data
public class XfWithholdResultVO implements Serializable {

	private static final long serialVersionUID = -5809672592176488983L;
	
	/**
	 * 是否成功
	 */
	private Boolean success = false;
	
	/**
	 * 应答代码
	 */
	private String resCode;
	/**
	 * 应答消息
	 */
	private String resMessage;
	
	/**
	 * 商户号
	 */
	private String merchantId;
	
	/**
	 * 商户订单号
	 */
	private String merchantNo;
	
	/**
	 * 交易订单号
	 */
	private String tradeNo;
	
	/**
	 * 订单状态：I（支付处理中）S（支付成功）F（支付失败）
	 */
	private String status;
	
	/**
	 *交易完成时间（格式：YYYYMMDDhhmmss 订单状态为终态S或F时存在）
	 */
	private String tradeTime;
	
	/**
	 *保留域（商户保留域原样回传）
	 */
	private String memo;
	
	/**
	 *订单签名数据
	 */
	private String sign;
	
	/**
	 * 代扣成功金额（分）
	 */
	private String amount = "0.00";
	
	/**
	 * 代扣成功金额（元）
	 */
	private String amountYuan;
	
	/**
	 * 币种
	 */
	private String transCur;
	
	public XfWithholdResultVO() {
		this.resCode = ErrInfo.ERROR.getCode();
		this.resMessage = ErrInfo.ERROR.getMsg();
	}
	
	public boolean isSuccess() {
		return "S".equals(getStatus());
	}
	
	public boolean isFail() {
		return "F".equals(getStatus());
	}

}
