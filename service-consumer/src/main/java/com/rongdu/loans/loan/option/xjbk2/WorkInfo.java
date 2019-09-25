package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WorkInfo implements Serializable {

    private static final long serialVersionUID = -8478326478922461499L;
    @JsonProperty("profession_type")
    private String professionType;
    @JsonProperty("work_office_info")
    private WorkOfficeInfo workOfficeInfo;
    public void setProfessionType(String professionType) {
         this.professionType = professionType;
     }
     public String getProfessionType() {
         return professionType;
     }

    public void setWorkOfficeInfo(WorkOfficeInfo workOfficeInfo) {
         this.workOfficeInfo = workOfficeInfo;
     }
     public WorkOfficeInfo getWorkOfficeInfo() {
         return workOfficeInfo;
     }

}