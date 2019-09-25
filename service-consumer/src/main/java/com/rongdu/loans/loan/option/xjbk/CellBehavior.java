package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class CellBehavior implements Serializable{

    private static final long serialVersionUID = -4694282913272745133L;
    @JsonProperty("phone_num")
    private String phoneNum;
    private List<Behavior> behavior;
    public void setPhoneNum(String phoneNum) {
         this.phoneNum = phoneNum;
     }
     public String getPhoneNum() {
         return phoneNum;
     }

    public void setBehavior(List<Behavior> behavior) {
         this.behavior = behavior;
     }
     public List<Behavior> getBehavior() {
         return behavior;
     }

}