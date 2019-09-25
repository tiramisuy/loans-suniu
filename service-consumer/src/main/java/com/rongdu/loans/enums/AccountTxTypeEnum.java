package com.rongdu.loans.enums;

import com.rongdu.common.config.Global;

/**
 * 存管电子账户交易类型
 * @author likang
 *
 */
public enum AccountTxTypeEnum {

	CASH(Global.TX_TYPE_CASH, "提现"),
	LENDERS(Global.TX_TYPE_LENDERS, "放款"),
	REPAY(Global.TX_TYPE_REPAY, "还款"),
	SERV_FEE(Global.TX_TYPE_SERV_FEE, "服务费"),
	OVERDUE_FEE(Global.TX_TYPE_OVERDUE_FEE, "逾期管理费");
	
	private Integer value;
	private String desc;
	
	

	AccountTxTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

	public Integer getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
