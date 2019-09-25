package com.rongdu.loans.loan.option.rong360Model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-04 15:10:35
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class GoodsInfo implements Serializable{

    private static final long serialVersionUID = -8563239667786358368L;
    @JsonProperty("tjy_order_no")
    private String tjyOrderNo;
    @JsonProperty("sku_id")
    private String skuId;
    @JsonProperty("r360_sku_id")
    private String r360SkuId;
    private int buynum;
    private String address;
    private String name;
    private String mobile;
    private String note;
    private String chargeaccount;
    public void setTjyOrderNo(String tjyOrderNo) {
         this.tjyOrderNo = tjyOrderNo;
     }
     public String getTjyOrderNo() {
         return tjyOrderNo;
     }

    public void setSkuId(String skuId) {
         this.skuId = skuId;
     }
     public String getSkuId() {
         return skuId;
     }

    public void setR360SkuId(String r360SkuId) {
         this.r360SkuId = r360SkuId;
     }
     public String getR360SkuId() {
         return r360SkuId;
     }

    public void setBuynum(int buynum) {
         this.buynum = buynum;
     }
     public int getBuynum() {
         return buynum;
     }

    public void setAddress(String address) {
         this.address = address;
     }
     public String getAddress() {
         return address;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setMobile(String mobile) {
         this.mobile = mobile;
     }
     public String getMobile() {
         return mobile;
     }

    public void setNote(String note) {
         this.note = note;
     }
     public String getNote() {
         return note;
     }

    public void setChargeaccount(String chargeaccount) {
         this.chargeaccount = chargeaccount;
     }
     public String getChargeaccount() {
         return chargeaccount;
     }

}