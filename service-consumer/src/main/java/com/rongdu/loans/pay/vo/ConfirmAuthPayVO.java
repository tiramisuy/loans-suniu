package com.rongdu.loans.pay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 确认支付返回对象
 * @author likang
 *
 */
@Data
public class ConfirmAuthPayVO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1825056494157832806L;
	
	/**
	 * 是否成功
	 */
	protected boolean success = false;
	/**
 	 * 流水号
	 */
	private String reqNo;
	/**
 	 * 返回码
	 */
	private String code;

	/**
 	 * 返回信息
	 */
	private String msg;
	/**
 	 * 支付订单号
	 */
	private String orderNo;
	/**
 	 * 支付成功金额
	 */
	private String succAmt;
	
	/**
 	 * 支付成功时间
	 */
	private String succTime;
	
	/**
	 * code y0730
	 * 订单状态：I（支付处理中）S（支付成功）F（支付失败）
	 */
	private String status;

}
