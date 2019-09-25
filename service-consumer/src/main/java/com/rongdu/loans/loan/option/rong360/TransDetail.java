package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class TransDetail implements Serializable{

	private static final long serialVersionUID = 8053241631523256344L;
	
	@JsonProperty("trans_date")
    private String transDate;
    @JsonProperty("post_date")
    private String postDate;
    private String description;
    @JsonProperty("rmb_amount")
    private String rmbAmount;
    @JsonProperty("rmb_org_amount")
    private String rmbOrgAmount;
    private String currency;
    @JsonProperty("trans_area")
    private String transArea;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("update_time")
    private String updateTime;
    public void setTransDate(String transDate) {
         this.transDate = transDate;
     }
     public String getTransDate() {
         return transDate;
     }

    public void setPostDate(String postDate) {
         this.postDate = postDate;
     }
     public String getPostDate() {
         return postDate;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setRmbAmount(String rmbAmount) {
         this.rmbAmount = rmbAmount;
     }
     public String getRmbAmount() {
         return rmbAmount;
     }

    public void setRmbOrgAmount(String rmbOrgAmount) {
         this.rmbOrgAmount = rmbOrgAmount;
     }
     public String getRmbOrgAmount() {
         return rmbOrgAmount;
     }

    public void setCurrency(String currency) {
         this.currency = currency;
     }
     public String getCurrency() {
         return currency;
     }

    public void setTransArea(String transArea) {
         this.transArea = transArea;
     }
     public String getTransArea() {
         return transArea;
     }

    public void setCreateTime(String createTime) {
         this.createTime = createTime;
     }
     public String getCreateTime() {
         return createTime;
     }

    public void setUpdateTime(String updateTime) {
         this.updateTime = updateTime;
     }
     public String getUpdateTime() {
         return updateTime;
     }

}