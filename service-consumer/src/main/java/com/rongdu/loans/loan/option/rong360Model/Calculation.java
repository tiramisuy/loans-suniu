package com.rongdu.loans.loan.option.rong360Model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-04 10:38:23
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Calculation implements Serializable {

    private static final long serialVersionUID = 3575932730725790905L;
    @JsonProperty("order_no")
    private String orderNo;
    private String amount;
    private int peroid;
    @JsonProperty("term_unit")
    private int termUnit;
    public void setOrderNo(String orderNo) {
         this.orderNo = orderNo;
     }
     public String getOrderNo() {
         return orderNo;
     }

    public void setAmount(String amount) {
         this.amount = amount;
     }
     public String getAmount() {
         return amount;
     }

    public void setPeroid(int peroid) {
         this.peroid = peroid;
     }
     public int getPeroid() {
         return peroid;
     }

    public void setTermUnit(int termUnit) {
         this.termUnit = termUnit;
     }
     public int getTermUnit() {
         return termUnit;
     }

}