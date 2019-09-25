package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.rongdu.common.utils.DateUtils;

import lombok.Data;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
@Data
public class CarefreeCounterfoilDataOP implements Serializable {
    /** 购物协议所需参数 */
    /**
     * 承诺归还日期
     */
    @NotBlank(message = "承诺归还日期不可为空")
    private String repaymentDate;
    /**
     * 归还商品费用
     */
    @NotBlank(message = "归还商品费用不可为空")
    private String repaymentProductMoney;
    /**
     * 商品垫资费
     */
    @NotBlank(message = "商品垫资费不可为空")
    private String advanceFundMoney;
    /**
     * 甲方全额借款金额
     */
    @NotBlank(message = "甲方全额借款金额不可为空")
    private String sumLoanAmt;
    /**
     * 垫资天数
     */
    @NotBlank(message = "垫资天数不可为空")
    private String advanceFundDay;
    /**
     * 商品垫资费用:购买商品总价*垫资天数__天*36%/360，总计人民币
     */
    @NotBlank(message = "商品垫资总费用不可为空")
    private String pruductAdvanceFundSumMoney;


    /** 以下为借款合同所需参数 */
    /**
     * 编号,(建议设置为放款编号)
     */
    @NotBlank(message = "编号不可为空")
    private String pactNo;
    /**
     * 借款人(甲方)=>也是购物协议中 甲方采购人
     */
    @NotBlank(message = "借款人(甲方)不可为空")
    private String borrowerName;
    /**
     * 借款人身份证号
     */
    @NotBlank(message = "借款人身份证号不可为空")
    private String borrowerIdCard;
    /**
     * 借款人住址
     */
    @NotBlank(message = "借款人住址不可为空")
    private String borrowerAddress;
    /**
     * 借款人联系电话
     */
    @NotBlank(message = "借款人联系电话不可为空")
    private String borrowerPhone;
    /**
     * 借款人邮件
     */
    @NotBlank(message = "借款人邮件不可为空")
    private String borrowerEmail;
//    /**
//     * 贷款人地址(甲方)
//     */
//    @NotBlank(message = "贷款人地址不可为空")
//    private String loanAddress;
//    /**
//     * 贷款人电话
//     */
//    @NotBlank(message = "贷款人电话不可为空")
//    private String loanPhone;
//    /**
//     * 贷款人邮件
//     */
//    @NotBlank(message = "贷款人邮件不可为空")
//    private String loanEmail;
//    /**
//     * 服务方地址(丙方)
//     */
//    @NotBlank(message = "服务方地址不可为空")
//    private String serviceAddress;
//    /**
//     * 服务方电话
//     */
//    @NotBlank(message = "服务方电话不可为空")
//    private String servicePhone;
//    /**
//     * 服务方邮件
//     */
//    @NotBlank(message = "服务方邮件不可为空")
//    private String serviceEmail;
    /**
     * 贷款金额(大写)
     */
    @NotBlank(message = "大写的贷款金额不可为空")
    private String loanAmt;
    /**
     * 贷款期限
     */
    @NotBlank(message = "贷款期限不可为空")
    private String loanLimit;
    /**
     * 贷款开始日期,格式 yyyy-MM-dd
     */
    @NotBlank(message = "贷款开始日期不可为空")
    private String beginLoanDate;
    /**
     * 贷款结束时间,格式 yyyy-MM-dd
     */
    @NotBlank(message = "贷款结束日期不可为空")
    private String endLoanDate;
    /**
     * 借款用途
     */
//    @NotBlank(message = "借款用途不可为空")
    private String loanUse = "购物";
    /**
     * 拒绝放款的其他先决条件
     */
    @NotNull(message = "拒绝放款的其他先决条件不能为空")
    private String otherCondition;
    /**
     * 收款人 账户名
     */
    @NotBlank(message = "借款用途不可为空")
    private String payee;
    /**
     * 收款 开户行
     */
    @NotBlank(message = "收款开户行不可为空")
    private String reciptBank;
    /**
     * 收款 银行卡号
     */
    @NotBlank(message = "收款银行卡号不可为空")
    private String reciptBankNo;
    /**
     * 授权扣划银行
     */
    @NotBlank(message = "授权扣划银行不可为空")
    private String transferBank;

    public void setBeginLoanDate(String beginLoanDate) {
        this.beginLoanDate = beginLoanDate;
        Date bDate = DateUtils.parse(beginLoanDate, "yyyy-MM-dd");
        this.beginYear = DateUtils.formatDate(bDate,"yyyy");
        this.beginMonth = DateUtils.formatDate(bDate,"MM");
        this.beginDay = DateUtils.formatDate(bDate,"dd");

    }

    public void setEndLoanDate(String endLoanDate) {
        this.endLoanDate = endLoanDate;
        Date bDate = DateUtils.parse(endLoanDate, "yyyy-MM-dd");
        this.endYear = DateUtils.formatDate(bDate,"yyyy");
        this.endMonth = DateUtils.formatDate(bDate,"MM");
        this.endDay = DateUtils.formatDate(bDate,"dd");
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
        Date bDate = DateUtils.parse(repaymentDate, "yyyy-MM-dd");
        this.repaymentYear = DateUtils.formatDate(bDate,"yyyy");
        this.repaymentMonth = DateUtils.formatDate(bDate,"MM");
        this.repaymentDay = DateUtils.formatDate(bDate,"dd");
    }

    /**
     * 开始年份,年月日拆开
     */
    private String beginYear;
    /**
     * 开始月份,年月日拆开
     */
    private String beginMonth;
    /**
     * 开始日期,年月日拆开
     */
    private String beginDay;
    /**
     * 结束年份,年月日拆开
     */
    private String endYear;
    /**
     * 结束月份,年月日拆开
     */
    private String endMonth;
    /**
     * 结束日期,年月日拆开
     */
    private String endDay;

    /**
     * 归还日期年份,年月日拆开
     */
    private String repaymentYear;
    /**
     * 归还日期月份,年月日拆开
     */
    private String repaymentMonth;
    /**
     * 归还日期日期,年月日拆开
     */
    private String repaymentDay;
    
    /**
     * 还款期数
     */
    private List<LoanRepayPlanItemOP> repayPlanItemList = new ArrayList<>();
    
    /**
     * 还款期数
     * @author admin
     * 2019年2月14日
     */
    public class LoanRepayPlanItemOP {
    	
    	private String itemNumber;
    	
    	private String repayDate;
    	
    	private BigDecimal repayAmount;
    	
		public LoanRepayPlanItemOP() {
			super();
		}

		public LoanRepayPlanItemOP(String itemNumber, String repayDate, BigDecimal repayAmount) {
			super();
			this.itemNumber = itemNumber;
			this.repayDate = repayDate;
			this.repayAmount = repayAmount;
		}

		public String getItemNumber() {
			return itemNumber;
		}

		public void setItemNumber(String itemNumber) {
			this.itemNumber = itemNumber;
		}

		public String getRepayDate() {
			return repayDate;
		}

		public void setRepayDate(String repayDate) {
			this.repayDate = repayDate;
		}

		public BigDecimal getRepayAmount() {
			return repayAmount;
		}

		public void setRepayAmount(BigDecimal repayAmount) {
			this.repayAmount = repayAmount;
		}
    	
    }

}
