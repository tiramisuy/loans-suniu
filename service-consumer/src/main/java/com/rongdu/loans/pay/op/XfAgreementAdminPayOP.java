package com.rongdu.loans.pay.op;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author liuzhuang
 * 
 */
@Data
public class XfAgreementAdminPayOP implements Serializable {

	private static final long serialVersionUID = -5103606434948118357L;
	/**
	 * 签约号
	 */
	private String contractNo;
	/**
	 * 金额(单位元)
	 */
	@NotBlank(message = "金额不能为空")
	private String amount;
	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空")
	private String userName;
	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	private String mobile;
	/**
	 * 身份证
	 */
	@NotBlank(message = "身份证不能为空")
	private String idNo;
	/**
	 * 银行卡号
	 */
	@NotBlank(message = "银行卡号不能为空")
	private String cardNo;
	/**
	 * 备注
	 */
	@NotBlank(message = "备注不能为空")
	private String remark;

	private Integer payType;
	
	private String txType;
	
	private String applyId;
}
