package com.rongdu.loans.loan.option;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CollectionRecordOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2184615380834552441L;

	/**
	 * 历史联系人id
	 */
//	@NotBlank(message = "contactId不能空")
	private String contactId;
	/**
	 * 还款计划明细ID
	 */
	@NotBlank(message = "itemId不能空")
	private String itemId;
	/**
	 *催收人员id
	 */
	@NotBlank(message = "催收人员id不能空")
	private String operatorId;
	/**
	 *催收人员姓名
	 */
	@NotBlank(message = "催收人员姓名不能空")
	private String operatorName;
	/**
	 *催收方式:1电话、2短信、3其他
	 */
//	@NotNull(message = "催收方式不能空")
	private Integer type;
	/**
	 *催收结果
	 */
//	@NotNull(message = "催收结果不能空")
	private Integer result;
	/**
	 *催收内容
	 */
	@NotBlank(message = "催收内容不能空")
	private String content;
	/**
	 *是否承诺付款（0-否，1-是）
	 */
//	@NotNull(message = "是否承诺付款不能空")
	private Integer promise;
	/**
	 *承诺付款日期
	 */
	private String promiseDateStr;
	/**
	 *下次跟进时间
	 */
//	@NotBlank(message = "下次跟进时间不能空")
	private String nextContactTimeStr;

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPromise() {
		return promise;
	}

	public void setPromise(Integer promise) {
		this.promise = promise;
	}

	public String getPromiseDateStr() {
		return promiseDateStr;
	}

	public void setPromiseDateStr(String promiseDateStr) {
		this.promiseDateStr = promiseDateStr;
	}

	public String getNextContactTimeStr() {
		return nextContactTimeStr;
	}

	public void setNextContactTimeStr(String nextContactTimeStr) {
		this.nextContactTimeStr = nextContactTimeStr;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
