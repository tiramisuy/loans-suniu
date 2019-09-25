package com.rongdu.loans.loan.op;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
@Data
public class RepayOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String id; // 待还ID
    private String contNo; // 合同编号
    private String userName; // 真实姓名
    private String idNo; // 用户证件号码
    private String mobile; // 手机号码
    private String borrowStart; // 借款时间
    private String borrowEnd;
    private String expectStart; // 应该还款时间
    private String expectEnd;
    private String actualStart; // 实际还款时间
    private String actualEnd;
    private Integer sign; // 标签 0正常，1提前， 2逾期
    private String repayType; // 还款方式 0快捷还款(主动),1系统代扣（自动）
    private Integer status; // 订单状态 0待还款，1已还款
    private String companyId; // 商户Id
    private String approverName; // 审核人
    private String productId; // 产品Id
    private Integer isDelaySettlement;// 是否延期结清，1=是，2=否

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer stage = 0;

    private String channelId; // 渠道码

    private String termType;    //申请期限类型 1:14天；2:90天；3：184天；4：其他

    private Integer withdrawalSource;    //'放款来源：0:线上,1:线下

    /**
     * 当前期数
     */
    private String thisTerm;

    /**
     * 保存工单时间
     */
    private String applyCreateStart;
    private String applyCreateEnd;

    /**
     * 是否为复贷
     */
    private String loanSucess;
    // 宜信决策分
    private String compositeScore;
}
