package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class PhoneList implements Serializable{

	private static final long serialVersionUID = 6606809015817915734L;
	
	private String uid;
    @JsonProperty("phone_dirty")
    private String phoneDirty;
    private String phone;
    private String name;
    private String createtime;
    public void setUid(String uid) {
         this.uid = uid;
     }
     public String getUid() {
         return uid;
     }

    public void setPhoneDirty(String phoneDirty) {
         this.phoneDirty = phoneDirty;
     }
     public String getPhoneDirty() {
         return phoneDirty;
     }

    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCreatetime(String createtime) {
         this.createtime = createtime;
     }
     public String getCreatetime() {
         return createtime;
     }

}