package com.rongdu.loans.loan.option;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * 生成债权转让OP
 * @author admin
 * 2018年12月17日
 */
@Data
public class GeneratorEquitableAssignmentDataOP implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1073145083785087480L;
	/**
     * 合同编号
     */
    @NotBlank(message = "合同编号不可为空")
    private String id;
    /**
     * 借款人姓名
     */
    @NotBlank(message = "借款人姓名不可为空")
    private String name;
    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不可为空")
    private String idNo;
    /**
     * 	借款金额
     */
    @NotBlank(message = "借款金额不可为空")
    private String txAmt;
    /**
     *放款日期
     */
    @NotBlank(message = "放款日期不可为空")
    private String loanStartDate;
    
    /**
     *债权转让日期
     */
    @NotBlank(message = "债权转让日期不可为空")
    private String txDate;
    
    /**
     *还款日期	
     */
    @NotBlank(message = "还款日期不可为空")
    private String loanEndDate;
   
}
