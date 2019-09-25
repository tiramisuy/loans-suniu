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
public class Ibank implements Serializable{

	private static final long serialVersionUID = 6679940130310102463L;
	
	@JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("user_name")
    private String userName;
    private List<Data> data;
    public void setBankName(String bankName) {
         this.bankName = bankName;
     }
     public String getBankName() {
         return bankName;
     }

    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}