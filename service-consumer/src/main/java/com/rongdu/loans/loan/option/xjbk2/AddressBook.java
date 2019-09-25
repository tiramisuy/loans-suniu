package com.rongdu.loans.loan.option.xjbk2;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class AddressBook {

    @JsonProperty("phone_list")
    private List<String> phoneList;
    public void setPhoneList(List<String> phoneList) {
         this.phoneList = phoneList;
     }
     public List<String> getPhoneList() {
         return phoneList;
     }

}