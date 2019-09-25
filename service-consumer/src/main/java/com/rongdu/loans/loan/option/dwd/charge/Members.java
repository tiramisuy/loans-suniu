package com.rongdu.loans.loan.option.dwd.charge;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-11-01 15:58:48
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Members implements Serializable{

    private static final long serialVersionUID = -54025173250382774L;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("error_msg")
    private String errorMsg;
    @JsonProperty("request_args")
    private List<RequestArgs> requestArgs;
    @JsonProperty("error_code")
    private int errorCode;
    private List<Transactions> transactions;
    private String status;
    public void setUpdateTime(String updateTime) {
         this.updateTime = updateTime;
     }
     public String getUpdateTime() {
         return updateTime;
     }

    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
     }
     public String getErrorMsg() {
         return errorMsg;
     }

    public void setRequestArgs(List<RequestArgs> requestArgs) {
         this.requestArgs = requestArgs;
     }
     public List<RequestArgs> getRequestArgs() {
         return requestArgs;
     }

    public void setErrorCode(int errorCode) {
         this.errorCode = errorCode;
     }
     public int getErrorCode() {
         return errorCode;
     }

    public void setTransactions(List<Transactions> transactions) {
         this.transactions = transactions;
     }
     public List<Transactions> getTransactions() {
         return transactions;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

}