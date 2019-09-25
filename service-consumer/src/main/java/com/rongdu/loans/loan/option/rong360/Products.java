package com.rongdu.loans.loan.option.rong360;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Products implements Serializable{

	private static final long serialVersionUID = -2763277962727311629L;
	
	private int price;
    @JsonProperty("product_id")
    private String productId;
    private String name;
    public void setPrice(int price) {
         this.price = price;
     }
     public int getPrice() {
         return price;
     }

    public void setProductId(String productId) {
         this.productId = productId;
     }
     public String getProductId() {
         return productId;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

}