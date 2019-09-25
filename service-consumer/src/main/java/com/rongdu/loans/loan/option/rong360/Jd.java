package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 10:36:45
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Jd implements Serializable{

	private static final long serialVersionUID = 2145617740152196114L;
	
	@JsonProperty("login_name")
    private String loginName;
    @JsonProperty("auth_info")
    private AuthInfo authInfo;
    @JsonProperty("bt_info")
    private BtInfo btInfo;
    @JsonProperty("user_info")
    private UserInfo userInfo;
    @JsonProperty("shipping_addrs")
    private List<ShippingAddrs> shippingAddrs;
    private List<Bankcards> bankcards;
    @JsonProperty("order_list")
    private List<OrderList> orderList;
    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setAuthInfo(AuthInfo authInfo) {
         this.authInfo = authInfo;
     }
     public AuthInfo getAuthInfo() {
         return authInfo;
     }

    public void setBtInfo(BtInfo btInfo) {
         this.btInfo = btInfo;
     }
     public BtInfo getBtInfo() {
         return btInfo;
     }

    public void setUserInfo(UserInfo userInfo) {
         this.userInfo = userInfo;
     }
     public UserInfo getUserInfo() {
         return userInfo;
     }

    public void setShippingAddrs(List<ShippingAddrs> shippingAddrs) {
         this.shippingAddrs = shippingAddrs;
     }
     public List<ShippingAddrs> getShippingAddrs() {
         return shippingAddrs;
     }

    public void setBankcards(List<Bankcards> bankcards) {
         this.bankcards = bankcards;
     }
     public List<Bankcards> getBankcards() {
         return bankcards;
     }

    public void setOrderList(List<OrderList> orderList) {
         this.orderList = orderList;
     }
     public List<OrderList> getOrderList() {
         return orderList;
     }

}