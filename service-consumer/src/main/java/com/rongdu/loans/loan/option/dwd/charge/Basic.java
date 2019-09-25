package com.rongdu.loans.loan.option.dwd.charge;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Basic implements Serializable{

    private static final long serialVersionUID = -4682289148326657150L;
    @JsonProperty("update_time")
    private String updateTime;
    private String idcard;
    @JsonProperty("reg_time")
    private String regTime;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("cell_phone")
    private String cellPhone;
    public void setUpdateTime(String updateTime) {
         this.updateTime = updateTime;
     }
     public String getUpdateTime() {
         return updateTime;
     }

    public void setIdcard(String idcard) {
         this.idcard = idcard;
     }
     public String getIdcard() {
         return idcard;
     }

    public void setRegTime(String regTime) {
         this.regTime = regTime;
     }
     public String getRegTime() {
         return regTime;
     }

    public void setRealName(String realName) {
         this.realName = realName;
     }
     public String getRealName() {
         return realName;
     }

    public void setCellPhone(String cellPhone) {
         this.cellPhone = cellPhone;
     }
     public String getCellPhone() {
         return cellPhone;
     }

}