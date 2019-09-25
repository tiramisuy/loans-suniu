package com.rongdu.loans.loan.option.dwd.report;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class MainService implements Serializable {

    private static final long serialVersionUID = -4226623363368278095L;
    @JsonProperty("service_details")
    private List<ServiceDetails> serviceDetails;
    @JsonProperty("total_service_cnt")
    private int totalServiceCnt;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("company_name")
    private String companyName;
    public void setServiceDetails(List<ServiceDetails> serviceDetails) {
         this.serviceDetails = serviceDetails;
     }
     public List<ServiceDetails> getServiceDetails() {
         return serviceDetails;
     }

    public void setTotalServiceCnt(int totalServiceCnt) {
         this.totalServiceCnt = totalServiceCnt;
     }
     public int getTotalServiceCnt() {
         return totalServiceCnt;
     }

    public void setCompanyType(String companyType) {
         this.companyType = companyType;
     }
     public String getCompanyType() {
         return companyType;
     }

    public void setCompanyName(String companyName) {
         this.companyName = companyName;
     }
     public String getCompanyName() {
         return companyName;
     }

}