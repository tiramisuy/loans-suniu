package com.rongdu.loans.loan.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rongdu.common.config.Global;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class ContentTableVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    /** ---------- 贷款订单数据  ------------ */
    /**
     * 合同编号
     */
    private String contNo;
    /**
     * 贷款申请订单ID
     */
    private String id;
    /**
     * 借款人姓名
     */
    private String userName;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * 借款产品
     */
    private String productName;
    /**
     * 贷款申请金额
     */
    private Double applyAmt;
    /**
     * 贷款审批金额
     */
    private Double approveAmt;
    /**
     * 申请期限(按天)
     */
    private Integer applyTerm;
    /**
     * 审批期限(按天)
     */
    private Integer approveTerm;
    /**
     * 计息方式
     */
    private String repayMethod;
    /**
     * 基准利率
     */
    private Double basicRate;
    /**
     * 优惠利率
     */
    private Double discountRate;

    /**
     * 借款利率（年化）= 基准利率 * （1-折扣率）
     */
    private BigDecimal rate;
    private String rateStr;
    /**
     * 每日利息=借款本金*借款利率/365
     */
    private BigDecimal everydayInterest;
    /**
     * 借款服务费率
     */
    private BigDecimal servFeeRate;
    private String servFeeRateStr;
    /**
     * 借款服务费
     */
    private Double servFee;
    /**
     * 申请时间
     */
    private Date applyTime;
    /** ---------- 贷款订单数据end  ------------ */



    /** ---------- 产品数据 ------------ */
    /**
     * 是否支持提前还款(0-否，1-是)
     */
    private Integer prepay;
    /**
     * 提前还款锁定天数
     */
    private Integer minLoanDay;
    /**
     *计息开始时间 = 起息日延后期限（默认从放款当天计息）
     */
    private Integer startInterest;

    /** ---------- 产品数据end ------------ */

    /** ---------- 营销方案 ------------ */
    /**
     *提前还款服务费收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer prepayFeeType;
    /**
     *提前还款服务费
     */
    private BigDecimal prepayValue;
    /**
     * 提前还款罚率
     */
    private String prepayFeeRate;
    /**
     *逾期还款收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private Integer overdueFeeType;
    /**
     *每天逾期还款服务费
     */
    private BigDecimal overdueValue;
    /**
     *逾期还款收费类型（0-按照百分比收取，1-按照固定金额收取）
     */
    private String overdueFeeRate;
    /**
     * 逾期管理费 （每日）
     */
    private BigDecimal overdueFee;
    /** ---------- 营销方案end ------------ */

    /** ---------- 合同 ------------ */
    /**
     *宽限期类型（1-产品默认允许宽限，2-运营特许宽限）
     */
    private Integer graceType;
    /**
     * 逾期开始时间 = 逾期宽限天数
     */
    private Integer graceDay;

    /**
     *贷款终止日期
     */
    private Date loanEndDate;
    /** ---------- 合同 end------------ */

    /**
     * 还款详情
     */
    private List<RepayDetailListVO> list;

    public BigDecimal getEverydayInterest() {
        if (approveAmt == null || basicRate == null || discountRate == null){
            return null;
        }
        everydayInterest = BigDecimal.valueOf(approveAmt)
                .multiply(BigDecimal.valueOf(basicRate))
                .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discountRate)))
                .divide(BigDecimal.valueOf(Global.YEAR_DAYS), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
        return everydayInterest;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getApplyAmt() {
        return applyAmt;
    }

    public void setApplyAmt(Double applyAmt) {
        this.applyAmt = applyAmt;
    }

    public Double getApproveAmt() {
        return approveAmt;
    }

    public void setApproveAmt(Double approveAmt) {
        this.approveAmt = approveAmt;
    }

    public Integer getApplyTerm() {
        return applyTerm;
    }

    public void setApplyTerm(Integer applyTerm) {
        this.applyTerm = applyTerm;
    }

    public Integer getApproveTerm() {
        return approveTerm;
    }

    public void setApproveTerm(Integer approveTerm) {
        this.approveTerm = approveTerm;
    }

    public String getRepayMethod() {
        return repayMethod;
    }

    public void setRepayMethod(String repayMethod) {
        this.repayMethod = repayMethod;
    }

    public Double getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(Double basicRate) {
        this.basicRate = basicRate;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getRate() {
        if (basicRate == null || discountRate == null){
            return null;
        }
        rate = BigDecimal.valueOf(basicRate).multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discountRate)));
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setEverydayInterest(BigDecimal everydayInterest) {
        this.everydayInterest = everydayInterest;
    }

    public BigDecimal getServFeeRate() {
        return servFeeRate;
    }

    public void setServFeeRate(BigDecimal servFeeRate) {
        this.servFeeRate = servFeeRate;
        if (servFeeRate != null){
            this.servFeeRateStr = servFeeRate.multiply(BigDecimal.valueOf(100)).setScale(Global.DEFAULT_AMT_SCALE,BigDecimal.ROUND_HALF_UP).toString()+"%";
        }
    }

    public String getServFeeRateStr() {
        return servFeeRateStr;
    }

    public void setServFeeRateStr(String servFeeRateStr) {
        this.servFeeRateStr = servFeeRateStr;
    }

    public Double getServFee() {
        return servFee;
    }

    public void setServFee(Double servFee) {
        this.servFee = servFee;
    }

    public Integer getPrepay() {
        return prepay;
    }

    public void setPrepay(Integer prepay) {
        this.prepay = prepay;
    }

    public Integer getMinLoanDay() {
        return minLoanDay;
    }

    public void setMinLoanDay(Integer minLoanDay) {
        this.minLoanDay = minLoanDay;
    }

    public Integer getStartInterest() {
        return startInterest;
    }

    public void setStartInterest(Integer startInterest) {
        this.startInterest = startInterest;
    }

    public Integer getPrepayFeeType() {
        return prepayFeeType;
    }

    public void setPrepayFeeType(Integer prepayFeeType) {
        this.prepayFeeType = prepayFeeType;
    }

    public BigDecimal getPrepayValue() {
        return prepayValue;
    }

    public void setPrepayValue(BigDecimal prepayValue) {
        this.prepayValue = prepayValue;
    }

    public Integer getOverdueFeeType() {
        return overdueFeeType;
    }

    public void setOverdueFeeType(Integer overdueFeeType) {
        this.overdueFeeType = overdueFeeType;
    }

    public BigDecimal getOverdueValue() {
        return overdueValue;
    }

    public void setOverdueValue(BigDecimal overdueValue) {
        this.overdueValue = overdueValue;
    }

    public Integer getGraceType() {
        return graceType;
    }

    public void setGraceType(Integer graceType) {
        this.graceType = graceType;
    }

    public Integer getGraceDay() {
        return graceDay;
    }

    public void setGraceDay(Integer graceDay) {
        this.graceDay = graceDay;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public List<RepayDetailListVO> getList() {
        return list;
    }

    public void setList(List<RepayDetailListVO> list) {
        this.list = list;
    }

    public String getRateStr() {
        if (rate == null){
            return null;
        }
        rateStr = rate.multiply(BigDecimal.valueOf(100)).setScale(Global.DEFAULT_AMT_SCALE,BigDecimal.ROUND_HALF_UP).toString()+"%";
        return rateStr;
    }

    public void setRateStr(String rateStr) {
        this.rateStr = rateStr;
    }

    public String getPrepayFeeRate() {
        if (prepayFeeType == null || !prepayFeeType.equals(0) || prepayValue == null){
            return null;
        }
        prepayFeeRate = prepayValue.multiply(BigDecimal.valueOf(100)).setScale(Global.DEFAULT_AMT_SCALE,BigDecimal.ROUND_HALF_UP).toString()+"%";
        return prepayFeeRate;
    }

    public void setPrepayFeeRate(String prepayFeeRate) {
        this.prepayFeeRate = prepayFeeRate;
    }

    public String getOverdueFeeRate() {
        if (overdueFeeType == null || !overdueFeeType.equals(0) || overdueValue == null){
            return null;
        }
        overdueFeeRate = overdueValue.multiply(BigDecimal.valueOf(100)).setScale(Global.FOUR_SCALE,BigDecimal.ROUND_HALF_UP).toString()+"%";
        return overdueFeeRate;
    }

    public void setOverdueFeeRate(String overdueFeeRate) {
        this.overdueFeeRate = overdueFeeRate;
    }

    public BigDecimal getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee) {
        this.overdueFee = overdueFee;
    }
}
