package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Alipay implements Serializable{

	private static final long serialVersionUID = 8949085728184611787L;
	
	@JsonProperty("alipay_info")
    private List<AlipayInfo> alipayInfo;
    @JsonProperty("alipay_list")
    private List<AlipayList> alipayList;
    private List<Bankcard> bankcard;
    @JsonProperty("alipay_charge_account")
    private List<AlipayChargeAccount> alipayChargeAccount;
    @JsonProperty("crawl_time")
    private String crawlTime;
    public void setAlipayInfo(List<AlipayInfo> alipayInfo) {
         this.alipayInfo = alipayInfo;
     }
     public List<AlipayInfo> getAlipayInfo() {
         return alipayInfo;
     }

    public void setAlipayList(List<AlipayList> alipayList) {
         this.alipayList = alipayList;
     }
     public List<AlipayList> getAlipayList() {
         return alipayList;
     }

    public void setBankcard(List<Bankcard> bankcard) {
         this.bankcard = bankcard;
     }
     public List<Bankcard> getBankcard() {
         return bankcard;
     }

    public void setAlipayChargeAccount(List<AlipayChargeAccount> alipayChargeAccount) {
         this.alipayChargeAccount = alipayChargeAccount;
     }
     public List<AlipayChargeAccount> getAlipayChargeAccount() {
         return alipayChargeAccount;
     }

    public void setCrawlTime(String crawlTime) {
         this.crawlTime = crawlTime;
     }
     public String getCrawlTime() {
         return crawlTime;
     }

}