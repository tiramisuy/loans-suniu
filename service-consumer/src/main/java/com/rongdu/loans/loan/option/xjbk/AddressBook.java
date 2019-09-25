package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-24 15:47:37
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AddressBook implements Serializable{

    private static final long serialVersionUID = 5461891652821223404L;
    @JsonProperty("phone_list")
    private List<String> phoneList;
    public void setPhoneList(List<String> phoneList) {
         this.phoneList = phoneList;
     }
     public List<String> getPhoneList() {
         return phoneList;
     }

}