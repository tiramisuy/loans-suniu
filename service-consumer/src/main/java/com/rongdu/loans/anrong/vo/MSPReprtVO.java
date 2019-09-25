package com.rongdu.loans.anrong.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 安融-MSPbean
 * @author fy
 * @version 2019-06-17
 */
@Data
public class MSPReprtVO implements Serializable {

    /**
     * 信贷交易详情—异常还款记录明细
     */
    private List<AbnormalCreditDetails> abnormalCreditDetails;
    /**
     * 信贷交易详情
     */
    private List<ApplyDetail> applyDetail;
    /**
     * 行业不良记录
     */
    private List<BlackDatas> blackDatas;
    /**
     * 本人异议申告明细
     */
    private List<String> blackSelfComplain;
    /**
     * 错误信息
     */
    private List<Object> errors;
    /**
     * 信贷交易详情—正常还款账户明细
     */
    private List<NormalCreditDetails> normalCreditDetails;
    /**
     * 信贷交易详情
     */
    private List<QueryDetails> queryDetails;
    /**
     * 风险速查结果
     */
    private QuickRisk quickRisk;
    /**
     * 信贷交易统计概况
     */
    private Title title;

}