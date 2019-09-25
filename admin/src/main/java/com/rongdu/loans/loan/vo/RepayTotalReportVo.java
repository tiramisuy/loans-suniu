package com.rongdu.loans.loan.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.excel.annotation.ExcelField;
import com.rongdu.loans.enums.RepayMethodEnum;

public class RepayTotalReportVo implements Serializable {
	
	/**
     * 商户id
     */
    private String companyId;
    private String companyName;
    /**
     * 合同编号
     */
    private String contNo;
    /**
     * 放款时间
     */
    private Date loanStartDate;
    private Date signDate;
    /**
     * 客户名称
     */
    private String userName;
    /**
     * 应还本金
     */
    private BigDecimal approveAmt;
    private BigDecimal loanMoney;
    /**
     * 期数
     */
    private Integer thisTerm;
    private Integer contractTerm;
    /**
     * 基准利率
     */
    private BigDecimal basicRate;
    private String mouthRateStr;
    private String termRateStr;
    /**
	 * 结束日期
	 */
	private Date loanEndDate;
	private String loanEndDate1;
	/**
     * 还款方式
     */
    private Integer repayMethod;
    private String repayMethodStr;  
    /**
     * 服务费率
     */
    private BigDecimal servFeeRate;
    private String servFeeRateStr; 
    /**
     * 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
     */
    private BigDecimal totalAmount;
    /**
     * 中介服务手续费
     */
    private BigDecimal servFee;


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
    @ExcelField(title="合作机构", type=1, align=2, sort=1)
    public String getCompanyName() {   	
		return companyName;
	}
    
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@ExcelField(title="合同编号", type=1, align=2, sort=2)
    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }
 
    @ExcelField(title="放款时间", type=1, align=2, sort=3)
    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }
    
    @ExcelField(title="客户姓名", type=1, align=2, sort=4)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelField(title="合同金额", type=1, align=2, sort=5)
    public BigDecimal getPrincipal() {
        return approveAmt;
    }

    public void setPrincipal(BigDecimal approveAmt) {
        this.approveAmt = approveAmt;
    }
    
    @ExcelField(title="合同期数", type=1, align=2, sort=6)
    public Integer getContractTerm() {
		return contractTerm;
	}

    
    public void setContractTerm(Integer contractTerm) {
		this.contractTerm = contractTerm;
	}

	public void setThisTerm(Integer thisTerm) {
    	this.thisTerm = thisTerm;
    	//this.contractTerm = thisTerm/2;
    }
    
    @ExcelField(title="月利率（%）", type=1, align=2, sort=7)
    public String getMouthRateStr() {
        return mouthRateStr;
    }
    
    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }
    
    @ExcelField(title="签署日期", type=1, align=2, sort=8)
	public Date getSignDate() {
		return loanStartDate;
	}
    
    public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	@ExcelField(title="终止日期", type=1, align=2, sort=9)
	public String getLoanEndDate1() {
		return loanEndDate1;
	}

	public void setLoanEndDate1(String loanEndDate1) {
		this.loanEndDate1 = loanEndDate1;
	}

	@ExcelField(title="还款方式", type=1, align=2, sort=10)
    public String getRepayMethodStr() {
        return repayMethodStr;
    }
    
    public Integer getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(Integer repayMethod) {
    	this.repayMethod = repayMethod;
        this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
    }

    @ExcelField(title="放款金额", type=1, align=2, sort=11)
    public BigDecimal getLoanMoney() {
		return approveAmt;
	}

	@ExcelField(title="管理费率（%）", type=1, align=2, sort=12)
    public String getServFeeRateStr() {
        return servFeeRateStr;
    }
    
    public BigDecimal getServFeeRate() {
        return servFeeRate;
    }

    public void setServFeeRate(BigDecimal servFeeRate) {
        this.servFeeRate = servFeeRate;
        this.servFeeRateStr = servFeeRate.multiply(BigDecimal.valueOf(100)).setScale(Global.DEFAULT_AMT_SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }

    @ExcelField(title="管理费", type=1, align=2, sort=13)
    public BigDecimal getServFee() {
        return approveAmt.multiply(servFeeRate).setScale(Global.DEFAULT_AMT_SCALE);
    }

    public void setServFee(BigDecimal servFee) {
        this.servFee = servFee;
    }
    
    @ExcelField(title="还款次数", type=1, align=2, sort=14)
    public Integer getThisTerm() {
    	return thisTerm;
    }
    
    @ExcelField(title="期利率（%）", type=1, align=2, sort=15)
    public String getTermRateStr() {
		return termRateStr;
	}

	@ExcelField(title="期还款额", type=1, align=2, sort=16)
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

	public void setMouthRateStr(String mouthRateStr) {
		this.mouthRateStr = mouthRateStr;
	}

	public void setTermRateStr(String termRateStr) {
		this.termRateStr = termRateStr;
	}
    
    
}
