package com.rongdu.loans.loan.option.dwd;

import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class CallLog implements Serializable {

    private static final long serialVersionUID = -7896710412472523408L;
    private String date;
    private String duration;
    private String phone;
    private String type;
    public void setDate(String date) {
         this.date = date;
     }
     public String getDate() {
         return date;
     }

    public void setDuration(String duration) {
         this.duration = duration;
     }
     public String getDuration() {
         return duration;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

}