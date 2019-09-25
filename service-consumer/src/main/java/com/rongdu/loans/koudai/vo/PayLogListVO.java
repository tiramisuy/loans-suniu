/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.koudai.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.utils.excel.annotation.ExcelField;
import com.rongdu.loans.enums.WithdrawalSourceEnum;

/**
 * 
 * @author liuzhuang
 * @version 2018-09-19
 */
public class PayLogListVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID，实体唯一标识
	 */
	protected String id;
	/**
	 * apply_id
	 */
	private String applyId;
	/**
	 * user_id
	 */
	private String userId;
	/**
	 * user_name
	 */
	private String userName;
	/**
	 * mobile
	 */
	private String mobile;
	/**
	 * idNo
	 */
	private String idNo;
	/**
	 * bank_code
	 */
	private String bankCode;
	/**
	 * bank_name
	 */
	private String bankName;
	/**
	 * card_no
	 */
	private String cardNo;
	/**
	 * 放款金额
	 */
	private BigDecimal payAmt;
	/**
	 * 放款时间
	 */
	private Date payTime;
	/**
	 * 成功时间
	 */
	private Date paySuccTime;
	/**
	 * 聚宝放款订单号，固定30位
	 */
	private String payOrderId;
	/**
	 * 放款失败次数
	 */
	private Integer payFailCount;
	/**
	 * 放款状态,0=成功,1=失败,2=处理中
	 */
	private Integer payStatus;
	
	private String payStatusStr;

	protected String kdPayMsg;

	protected Integer kdCreateCode;
	
	/**
	 * 是否提现 1:已提现 0:未提现,2提现中
	 */
	private Integer withdrawStatus;
	
	/**
	 * 	备注
	 */
	protected String remark;
	
	/**
	 * 	备注2
	 */
	protected String remark2;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "借款订单号", type = 1, align = 2, sort = 1)
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ExcelField(title = "借款人姓名", type = 1, align = 2, sort = 3)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "手机号码", type = 1, align = 2, sort = 4)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "证件号码", type = 1, align = 2, sort = 5)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@ExcelField(title = "银行", type = 1, align = 2, sort = 6)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@ExcelField(title = "银行卡号", type = 1, align = 2, sort = 7)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@ExcelField(title = "放款金额", type = 1, align = 2, sort = 8)
	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	@ExcelField(title = "放款时间", type = 1, align = 2, sort = 9)
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@ExcelField(title = "到账时间", type = 1, align = 2, sort = 10)
	public Date getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Date paySuccTime) {
		this.paySuccTime = paySuccTime;
	}

	@ExcelField(title = "放款订单号", type = 1, align = 2, sort = 2)
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	@ExcelField(title = "放款失败次数", type = 1, align = 2, sort = 11)
	public Integer getPayFailCount() {
		return payFailCount;
	}

	public void setPayFailCount(Integer payFailCount) {
		this.payFailCount = payFailCount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
		
		if(payStatus ==0){
			this.setPayStatusStr("成功");
		}else if(payStatus ==1){
			this.setPayStatusStr("失败");
		}else{
			this.setPayStatusStr("处理中");
		}
	}

	public String getKdPayMsg() {
		return kdPayMsg;
	}

	public void setKdPayMsg(String kdPayMsg) {
		this.kdPayMsg = kdPayMsg;
	}

	public Integer getKdCreateCode() {
		return kdCreateCode;
	}

	public void setKdCreateCode(Integer kdCreateCode) {
		this.kdCreateCode = kdCreateCode;
	}

	@ExcelField(title = "放款状态", type = 1, align = 2, sort = 12)
	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark2() {
		try {
			if(remark == null){
				remark2="";
			}else if (remark.contains("_TO_")) {
				remark2 = "已转"+WithdrawalSourceEnum.getByValue(Integer.parseInt(remark.split("_TO_")[1])).getDesc()+"放款";
			}else{
				remark2 = remark;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}		

}