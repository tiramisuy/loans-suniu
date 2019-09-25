package com.rongdu.loans.koudai.vo;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
* @Description:  口袋提现记录VO
* @author: RaoWenbiao
* @String 2018年12月6日
 */
public class KDwithdrawRecodeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 申请id
	 */
	private String applyId;

    /**
     * 提现日期
     */
    private String date;

    /**
     * 提现状态
     */
    private Integer  status ;
    
    /**
     * 提现状态描述
     */
    private String  statusDesc ;


    /**
     * 提现金额
     */
    private String money;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getApplyId() {
		return applyId;
	}


	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	
    
    

	
	
}
