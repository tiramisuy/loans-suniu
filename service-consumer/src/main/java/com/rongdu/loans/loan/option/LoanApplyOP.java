package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 提交贷款申请参数对象
 * 
 * @author likang
 * 
 */
@Data
public class LoanApplyOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1005646992743628884L;

	/**
	 * 贷款申请金额
	 */
	@NotNull(message = "贷款金额不能为空")
	@DecimalMin(value = "0", message = "贷款金额必须>0")
	private BigDecimal applyAmt;

	/**
	 * 申请期限(按天)
	 */
	@NotNull(message = "申请期限不能为空")
	@Min(value = 0, message = "申请期限必须>0")
	private Integer applyTerm;

	/**
	 * 进件来源（1-ios,2-android,3-h5,4-api）
	 */
	@NotBlank(message = "进件来源不能为空")
	@Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
	private String source;
	/**
	 * 还款方式
	 */
	private Integer repayMethod;

	/**
	 * 借款用途
	 */
	private String purpose;
	/**
	 * 服务费
	 */
	private BigDecimal servFee;

	/**
	 * 日利息
	 */
	private BigDecimal dayInterest;

	/**
	 * 产品ID
	 */
	@NotBlank(message = "产品ID不能为空")
	private String productId;
	/**
	 * 产品类型
	 */
	@NotBlank(message = "产品类型不能为空")
	private String productType;

	/**
	 * 白骑士设备唯一标识
	 */
	@NotBlank(message = "白骑士设备唯一标识不能为空")
	private String bqsTokenKey;
	/**
	 * 同盾设备唯一标识
	 */
	private String tdBlackBox;

	private String applyId; // 申请编号
	private String userId; // 客户ID
	private String ip; // IP地址

	private String orgId; // 受理机构代码
	private String orgName; // 受理机构名称
	private String channelId; // 渠道码（树形结构）
	private String salerId; // 推荐人ID
	private Integer term; // 还款期数

}
