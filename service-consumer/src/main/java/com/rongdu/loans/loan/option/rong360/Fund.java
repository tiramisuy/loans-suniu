package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Fund implements Serializable{

	private static final long serialVersionUID = 153084156756447205L;
	
	private int cid;
    private String location;
    @JsonProperty("cityName")
    private String cityname;
    @JsonProperty("orderId")
    private String orderid;
    @JsonProperty("create_date")
    private String createDate;
    private Data data;
    public void setCid(int cid) {
         this.cid = cid;
     }
     public int getCid() {
         return cid;
     }

    public void setLocation(String location) {
         this.location = location;
     }
     public String getLocation() {
         return location;
     }

    public void setCityname(String cityname) {
         this.cityname = cityname;
     }
     public String getCityname() {
         return cityname;
     }

    public void setOrderid(String orderid) {
         this.orderid = orderid;
     }
     public String getOrderid() {
         return orderid;
     }

    public void setCreateDate(String createDate) {
         this.createDate = createDate;
     }
     public String getCreateDate() {
         return createDate;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}