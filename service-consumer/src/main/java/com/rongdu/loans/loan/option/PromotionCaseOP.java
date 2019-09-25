package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;


@Data
public class PromotionCaseOP implements Serializable{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3056109832349117834L;
	
    /**
     * 贷款申请金额
     */
	private BigDecimal applyAmt;
    
	/**
     * 申请期限(按天)
     */
	private Integer applyTerm;
	      
    /**
     * 产品ID
     */
	@NotBlank(message="产品ID不能为空")
    private String productId;
    
    /**
     * 渠道码
     */
    private String channelId;

    private Integer repayMethod;//还款方式
    private Integer repayUnit;//还款间隔
    private Integer term;//还款期数
    private String repayFreq;//还款间隔单位
    
}
