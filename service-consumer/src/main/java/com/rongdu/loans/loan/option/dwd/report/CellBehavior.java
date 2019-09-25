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
public class CellBehavior implements Serializable {

    private static final long serialVersionUID = -4059654096508020313L;
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