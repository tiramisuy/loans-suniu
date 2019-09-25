package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Orderinfo implements Serializable{

	private static final long serialVersionUID = -8480106789007677405L;
	
	@JsonProperty("order_no")
    private String orderNo;
    @JsonProperty("user_id")
    private int userId;
    private int id;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_mobile")
    private String userMobile;
    @JsonProperty("register_time")
    private int registerTime;
    private int status;
    @JsonProperty("order_time")
    private int orderTime;
    private String city;
    private String bank;
    private String product;
    @JsonProperty("product_id")
    private int productId;

    public void setOrderNo(String orderNo) {
         this.orderNo = orderNo;
     }
     public String getOrderNo() {
         return orderNo;
     }

    public void setUserId(int userId) {
         this.userId = userId;
     }
     public int getUserId() {
         return userId;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setUserMobile(String userMobile) {
         this.userMobile = userMobile;
     }
     public String getUserMobile() {
         return userMobile;
     }

    public void setRegisterTime(int registerTime) {
         this.registerTime = registerTime;
     }
     public int getRegisterTime() {
         return registerTime;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setOrderTime(int orderTime) {
         this.orderTime = orderTime;
     }
     public int getOrderTime() {
         return orderTime;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setBank(String bank) {
         this.bank = bank;
     }
     public String getBank() {
         return bank;
     }

    public void setProduct(String product) {
         this.product = product;
     }
     public String getProduct() {
         return product;
     }

    public void setProductId(int productId) {
         this.productId = productId;
     }
     public int getProductId() {
         return productId;
     }

}