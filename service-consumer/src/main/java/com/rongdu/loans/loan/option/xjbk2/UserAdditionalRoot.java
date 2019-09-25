package com.rongdu.loans.loan.option.xjbk2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-09-14 16:18:44
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UserAdditionalRoot {

    @JsonProperty("order_info")
    private OrderInfo orderInfo;
    @JsonProperty("user_additional")
    private UserAdditional userAdditional;
    public void setOrderInfo(OrderInfo orderInfo) {
         this.orderInfo = orderInfo;
     }
     public OrderInfo getOrderInfo() {
         return orderInfo;
     }

    public void setUserAdditional(UserAdditional userAdditional) {
         this.userAdditional = userAdditional;
     }
     public UserAdditional getUserAdditional() {
         return userAdditional;
     }

}