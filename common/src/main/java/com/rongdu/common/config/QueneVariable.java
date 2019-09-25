package com.rongdu.common.config;

/**
 * 队列相关公共变量类
 * @author likang
 *
 */
public class QueneVariable {

	/**
	 * 接口调用日志信息队列名称
	 */
	public static final String LOG_QUENE_NAME = "accessLogQuene";
	// public static final String LOG_QUENE_NAME = "devAccessLogQuene";
	
	/**
	 * 提交贷款申请推送风控队列
	 */
//	public static final String APPLY_QUENE_NAME = "prepareAutoApproveQueue";
	
	/**
	 * 提前还款信息队列名称
	 */
	public static final String PREPAY_QUENE_NAME = "prePayQuene";
}
