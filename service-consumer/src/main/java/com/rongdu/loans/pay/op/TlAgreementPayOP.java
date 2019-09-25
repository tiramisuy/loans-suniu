package com.rongdu.loans.pay.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通联
 *
 * @author liuzhuang
 */
@Data
public class TlAgreementPayOP implements Serializable {

    private static final long serialVersionUID = -5103606434948118357L;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 金额(单位元)
     */
    @NotBlank(message = "金额不能为空")
    private String amount;
    /**
     * 支付分类
     */
    @NotNull(message = "支付分类不能为空")
    private Integer payType;
    /**
     * 用户编号
     */
    @NotBlank(message = "用户编号不能为空")
    private String userId;
    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String applyId;
    /**
     * 合同号
     */
    private String contractId;
    /**
     * 还款计划明细id
     */
    private String repayPlanItemId;
    /**
     * ip
     */
    private String ip;
    /**
     * 来源
     */
    private String source;
    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 购物券id(多个逗号分隔)
     */
    private String couponId;

    /**
     * 绑定签约号
     */
    private String bindId;

}
