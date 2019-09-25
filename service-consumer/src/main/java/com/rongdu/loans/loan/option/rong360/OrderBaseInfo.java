package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class OrderBaseInfo implements Serializable{

	private static final long serialVersionUID = -7617006013171080996L;
	
	@JsonProperty("orderInfo")
    private Orderinfo orderinfo;
    @JsonProperty("applyDetail")
    private Applydetail applydetail;
    @JsonProperty("addInfo")
    private Addinfo addinfo;
    public void setOrderinfo(Orderinfo orderinfo) {
         this.orderinfo = orderinfo;
     }
     public Orderinfo getOrderinfo() {
         return orderinfo;
     }

    public void setApplydetail(Applydetail applydetail) {
         this.applydetail = applydetail;
     }
     public Applydetail getApplydetail() {
         return applydetail;
     }

    public void setAddinfo(Addinfo addinfo) {
         this.addinfo = addinfo;
     }
     public Addinfo getAddinfo() {
         return addinfo;
     }

}