package com.rongdu.loans.pay.xianfeng.vo;

import java.io.Serializable;

import lombok.Data;

/**  
* @Title: XfWithdrawDataVO.java  
* @Package com.rongdu.loans.pay.xianfeng.vo  
* @Description: 先锋代付业务数据
* @author: yuanxianchu  
* @date 2018年6月20日  
* @version V1.0  
*/
@Data
public class XfWithdrawDataVO implements Serializable {
	
	private static final long serialVersionUID = 6203215715965155230L;

	/**
	 * 商户订单号
	 */
	private String merchantNo = "WD" + System.nanoTime();
	
	/**
	 * 来源（固定值：1）
	 */
	private String source;
	
	/**
	 * 金额(单位：分)
	 */
	private String amount;
	
	/**
	 * 币种,由先锋支付定义，商户传入固定值：156（表示人民币）
	 */
	private String transCur = "156";
	
	/**
	 * 用户类型  固定值：1或者2 （1：对私 2：对公）
	 */
	private String userType;
	
	/**
	 * 账户类型
	 * 由先锋支付定义，商户传入指定值：1（借记卡）2（贷记卡）4（对公账户）
	 * 约束条件：当用户类型（userType字段）为1（对私）时，账户类型只能传1或2，不传默认为1；
	 *			 当用户类型（userType字段）为2（对公）时，账户类型只能传4，不传默认为4
	 */
	private String accountType;
	
	/**
	 * 账户号
	 */
	private String accountNo;
	
	/**
	 * 账户名称
	 */
	private String accountName;
	
	/**
	 * 银行编码
	 */
	private String bankNo;
	
	/**
	 * 手机号
	 */
	private String mobileNo;
	
	/**
	 * 开户省
	 */
	private String branchProvince;
	
	/**
	 * 开户市
	 */
	private String branchCity;
	
	/**
	 * 开户支行名称
	 */
	private String branchName;
	
	/**
	 * 后台通知地址
	 */
	private String noticeUrl;
	
	/**
	 * 联行号
	 * （注：当useType=2时联行号为必填项，如userType=1时联行号为非必填项）
	 */
	private String issuer;
	
	/**
	 * 代发产品
	 * 由先锋支付定义，商户传入指定值：NORMAL（普通代发）FAST（垫资代发）
	 */
	private String tradeProduct;
	
	/**
	 * 保留域
	 */
	private String memo;

}
