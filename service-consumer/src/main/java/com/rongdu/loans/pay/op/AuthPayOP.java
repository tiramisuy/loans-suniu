package com.rongdu.loans.pay.op;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/14
 * @since 1.0.0
 */
@Data
public class AuthPayOP implements Serializable {
    private static final long serialVersionUID = -5489280755205384042L;

    /**
     * 金额(单位元)
     */
    private String amount;
    /**
     * 支付分类
     */
    private Integer payType;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 订单号
     */
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
     * 真实姓名
     */
    private String realName;

    /**
     * 绑定签约号
     */
    private String bindId;

    /**
     * 交易类型
     */
    private String txType;

    /**
     * 支付渠道
     */
    private String payChannel;
}
