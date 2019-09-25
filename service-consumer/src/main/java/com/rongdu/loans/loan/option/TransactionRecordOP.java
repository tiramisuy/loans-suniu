package com.rongdu.loans.loan.option;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 交易记录入参对象
 * @author likang
 *
 */
public class TransactionRecordOP implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8351870088849704923L;
	
    @NotBlank(message="进件来源不能为空")
    @Pattern(regexp="1|2|3|4",message="消息来源类型有误")
    private String source;
    
    /**
     * 恒丰银行电子账户
     */
    private String accountId;
    
    /**
     * 交易渠道
     *    [1]–PC;[2]–ios;[3]–android;[4]–H5
     */
    private String channel;
    
    /**
     * 起始日期   YYYYMMDD
     */
    private String startDate;
    
    /**
     * 结束日期  YYYYMMDD
     */
    private String endDate;
    
    /**
     * 交易类型
     *    0-所有交易;1-转入交易;2-转出交易;9-指定交易类型
     */
    private String tranType;

    
	public String getSource() {
		return source;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getChannel() {
		return channel;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getTranType() {
		return tranType;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
    
    

}
