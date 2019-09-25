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
public class OrderList implements Serializable{

	private static final long serialVersionUID = -702743656488091370L;
	
	@JsonProperty("order_id")
    private String orderId;
    private String receiver;
    private String money;
    @JsonProperty("buy_way")
    private String buyWay;
    @JsonProperty("buy_time")
    private int buyTime;
    @JsonProperty("order_status")
    private String orderStatus;
    @JsonProperty("order_source")
    private String orderSource;
    @JsonProperty("login_name")
    private String loginName;
    @JsonProperty("receiver_addr")
    private String receiverAddr;
    @JsonProperty("receiver_post")
    private String receiverPost;
    @JsonProperty("receiver_fix_phone")
    private String receiverFixPhone;
    @JsonProperty("receiver_telephone")
    private String receiverTelephone;
    @JsonProperty("receiver_email")
    private String receiverEmail;
    @JsonProperty("product_names")
    private String productNames;
    @JsonProperty("invoice_type")
    private String invoiceType;
    @JsonProperty("invoice_header")
    private String invoiceHeader;
    @JsonProperty("invoice_content")
    private String invoiceContent;
    private List<Products> products;
    private String url;
    public void setOrderId(String orderId) {
         this.orderId = orderId;
     }
     public String getOrderId() {
         return orderId;
     }

    public void setReceiver(String receiver) {
         this.receiver = receiver;
     }
     public String getReceiver() {
         return receiver;
     }

    public void setMoney(String money) {
         this.money = money;
     }
     public String getMoney() {
         return money;
     }

    public void setBuyWay(String buyWay) {
         this.buyWay = buyWay;
     }
     public String getBuyWay() {
         return buyWay;
     }

    public void setBuyTime(int buyTime) {
         this.buyTime = buyTime;
     }
     public int getBuyTime() {
         return buyTime;
     }

    public void setOrderStatus(String orderStatus) {
         this.orderStatus = orderStatus;
     }
     public String getOrderStatus() {
         return orderStatus;
     }

    public void setOrderSource(String orderSource) {
         this.orderSource = orderSource;
     }
     public String getOrderSource() {
         return orderSource;
     }

    public void setLoginName(String loginName) {
         this.loginName = loginName;
     }
     public String getLoginName() {
         return loginName;
     }

    public void setReceiverAddr(String receiverAddr) {
         this.receiverAddr = receiverAddr;
     }
     public String getReceiverAddr() {
         return receiverAddr;
     }

    public void setReceiverPost(String receiverPost) {
         this.receiverPost = receiverPost;
     }
     public String getReceiverPost() {
         return receiverPost;
     }

    public void setReceiverFixPhone(String receiverFixPhone) {
         this.receiverFixPhone = receiverFixPhone;
     }
     public String getReceiverFixPhone() {
         return receiverFixPhone;
     }

    public void setReceiverTelephone(String receiverTelephone) {
         this.receiverTelephone = receiverTelephone;
     }
     public String getReceiverTelephone() {
         return receiverTelephone;
     }

    public void setReceiverEmail(String receiverEmail) {
         this.receiverEmail = receiverEmail;
     }
     public String getReceiverEmail() {
         return receiverEmail;
     }

    public void setProductNames(String productNames) {
         this.productNames = productNames;
     }
     public String getProductNames() {
         return productNames;
     }

    public void setInvoiceType(String invoiceType) {
         this.invoiceType = invoiceType;
     }
     public String getInvoiceType() {
         return invoiceType;
     }

    public void setInvoiceHeader(String invoiceHeader) {
         this.invoiceHeader = invoiceHeader;
     }
     public String getInvoiceHeader() {
         return invoiceHeader;
     }

    public void setInvoiceContent(String invoiceContent) {
         this.invoiceContent = invoiceContent;
     }
     public String getInvoiceContent() {
         return invoiceContent;
     }

    public void setProducts(List<Products> products) {
         this.products = products;
     }
     public List<Products> getProducts() {
         return products;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}