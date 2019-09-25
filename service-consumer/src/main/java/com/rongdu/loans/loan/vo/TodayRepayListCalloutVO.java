package com.rongdu.loans.loan.vo;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.excel.annotation.ExcelField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出到呼叫系统数据对象
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/1/16
 */
public class TodayRepayListCalloutVO implements Serializable {

    private static final long serialVersionUID = -6219847033510500874L;
    /**
     * 客户名称
     */
    private String userName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 贷款审批金额
     */
    private BigDecimal approveAmt;
    /**
     * 申请期限(按天)
     */
    private Integer applyTerm;
    /**
     * 贷款期数(月)
     */
    private Integer totalTerm;
    /**
     * 期数
     */
    private Integer thisTerm;
    private String contractTerm;
    /**
     * 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
     */
    private BigDecimal totalAmount;

    /**
     * 还款日期
     */
    private Date repayDate;
    private String repayDateStr;

    /**
     * 审核人
     */
    private String approverName;

    private Date approveTime;

    private Integer status;

    private String statusStr;

    private String orderNo;

    private String url = "";

    private String exportType = "1";  // 预提醒

    @ExcelField(title = "uuid", type = 1, align = 2, sort = 2)
    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    //    @ExcelField(title = "审核人", type = 1, align = 2, sort = 9)
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ExcelField(title = "主号码", type = 1, align = 2, sort = 1)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    //    @ExcelField(title = "借款本金", type = 1, align = 2, sort = 4)
    public BigDecimal getApproveAmt() {
        return approveAmt;
    }

    public void setApproveAmt(BigDecimal approveAmt) {
        this.approveAmt = approveAmt;
    }


    public Integer getTotalTerm() {
        return totalTerm;
    }

    public void setTotalTerm(Integer totalTerm) {
        this.totalTerm = totalTerm;
    }

    public Integer getThisTerm() {
        return thisTerm;
    }

    public void setThisTerm(Integer thisTerm) {
        this.thisTerm = thisTerm;
    }

//    @ExcelField(title = "应还本金", type = 1, align = 2, sort = 7)
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @ExcelField(title = "uuid2", type = 1, align = 2, sort = 3)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

//    @ExcelField(title = "应还日期", type = 1, align = 2, sort = 8)
    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
        this.repayDateStr = DateUtils.formatDate(DateUtils.addDay(this.repayDate, 0));
    }


    public String getRepayDateStr() {
        return repayDateStr;
    }

    public void setRepayDateStr(String repayDateStr) {
        this.repayDateStr = repayDateStr;
    }


//    @ExcelField(title = "借款期限", type = 1, align = 2, sort = 5)
    public Integer getApplyTerm() {
        return applyTerm;
    }

    public void setApplyTerm(Integer applyTerm) {
        this.applyTerm = applyTerm;
    }

//    @ExcelField(title = "借款时间", type = 1, align = 2, sort = 3)
    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    @ExcelField(title = "期数", type = 1, align = 2, sort = 6)
    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(String contractTerm) {
        this.contractTerm = contractTerm;
    }

//    @ExcelField(title = "状态", type = 1, align = 2, sort = 10)
    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @ExcelField(title = "URL", type = 1, align = 2, sort = 4)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
