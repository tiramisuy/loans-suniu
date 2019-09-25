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
public class CollectionContact implements Serializable {

    private static final long serialVersionUID = 4177183634991284839L;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("contact_name")
    private String contactName;
    @JsonProperty("total_amount")
    private int totalAmount;
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("begin_date")
    private String beginDate;
    @JsonProperty("contact_details")
    private List<ContactDetails> contactDetails;
    public void setEndDate(String endDate) {
         this.endDate = endDate;
     }
     public String getEndDate() {
         return endDate;
     }

    public void setContactName(String contactName) {
         this.contactName = contactName;
     }
     public String getContactName() {
         return contactName;
     }

    public void setTotalAmount(int totalAmount) {
         this.totalAmount = totalAmount;
     }
     public int getTotalAmount() {
         return totalAmount;
     }

    public void setTotalCount(int totalCount) {
         this.totalCount = totalCount;
     }
     public int getTotalCount() {
         return totalCount;
     }

    public void setBeginDate(String beginDate) {
         this.beginDate = beginDate;
     }
     public String getBeginDate() {
         return beginDate;
     }

    public void setContactDetails(List<ContactDetails> contactDetails) {
         this.contactDetails = contactDetails;
     }
     public List<ContactDetails> getContactDetails() {
         return contactDetails;
     }

}