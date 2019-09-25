package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Transactions implements Serializable{

    private static final long serialVersionUID = -349821812438338848L;
    @JsonProperty("total_amt")
    private double totalAmt;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("pay_amt")
    private int payAmt;
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

    public void setPayAmt(int payAmt) {
         this.payAmt = payAmt;
     }
     public int getPayAmt() {
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