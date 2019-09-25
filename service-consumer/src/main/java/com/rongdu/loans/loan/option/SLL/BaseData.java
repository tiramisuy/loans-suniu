package com.rongdu.loans.loan.option.SLL;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-12-06 17:2:2
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class BaseData implements Serializable {

    private static final long serialVersionUID = -5665941406063661440L;
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