package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Bankcard implements Serializable{

	private static final long serialVersionUID = -631592018198545349L;
	
	@JsonProperty("card_last_num")
    private String cardLastNum;
    @JsonProperty("open_status")
    private String openStatus;
    @JsonProperty("apply_time")
    private String applyTime;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("card_type")
    private String cardType;
    private String mobile;
    @JsonProperty("active_date")
    private Date activeDate;
    @JsonProperty("show_user_name")
    private String showUserName;
    @JsonProperty("alipay_name")
    private String alipayName;
    public void setCardLastNum(String cardLastNum) {
         this.cardLastNum = cardLastNum;
     }
     public String getCardLastNum() {
         return cardLastNum;
     }

    public void setOpenStatus(String openStatus) {
         this.openStatus = openStatus;
     }
     public String getOpenStatus() {
         return openStatus;
     }

    public void setApplyTime(String applyTime) {
         this.applyTime = applyTime;
     }
     public String getApplyTime() {
         return applyTime;
     }

    public void setBankName(String bankName) {
         this.bankName = bankName;
     }
     public String getBankName() {
         return bankName;
     }

    public void setCardType(String cardType) {
         this.cardType = cardType;
     }
     public String getCardType() {
         return cardType;
     }

    public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setActiveDate(Date activeDate) {
         this.activeDate = activeDate;
     }
     public Date getActiveDate() {
         return activeDate;
     }

    public void setShowUserName(String showUserName) {
         this.showUserName = showUserName;
     }
     public String getShowUserName() {
         return showUserName;
     }

    public void setAlipayName(String alipayName) {
         this.alipayName = alipayName;
     }
     public String getAlipayName() {
         return alipayName;
     }

}