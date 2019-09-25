package com.rongdu.loans.loan.option.dwd.charge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-11-01 16:25:0
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Transaction implements Serializable{

    private static final long serialVersionUID = -5758682999000646733L;
    @JsonProperty("total_amt")
    private double totalAmt;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("pay_amt")
    private double payAmt;
    @JsonProperty("plan_amt")
    private int planAmt;
    @JsonProperty("bill_cycle")
    private String billCycle;
    @JsonProperty("cell_phone")
    private String cellPhone;
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }
    public double getTotalAmt() {
        return totalAmt;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setPayAmt(double payAmt) {
        this.payAmt = payAmt;
    }
    public double getPayAmt() {
        return payAmt;
    }

    public void setPlanAmt(int planAmt) {
        this.planAmt = planAmt;
    }
    public int getPlanAmt() {
        return planAmt;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }
    public String getBillCycle() {
        return billCycle;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
    public String getCellPhone() {
        return cellPhone;
    }

}