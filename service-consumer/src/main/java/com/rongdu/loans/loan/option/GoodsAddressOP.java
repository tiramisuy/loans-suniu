package com.rongdu.loans.loan.option;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lee on 2018/5/9.
 */
public class GoodsAddressOP implements Serializable {

    private static final long serialVersionUID = -7088578057112324156L;


    /**
     * 审批金额
     */
    private BigDecimal approveAmt;

    /**
     * 进件来源（1-PC, 2-ios, 3-android,4-h5）
     */
    @NotBlank(message = "进件来源不能为空")
    @Pattern(regexp = "1|2|3|4", message = "消息来源类型有误")
    private String source;

    /**
     * 产品ID
     */
    @NotBlank(message = "产品ID不能为空")
    private String productId;
    /**
     *用户id
     */
    private String userId;
    /**
     *用户姓名
     */
    private String userName;
    /**
     *订单id
     */
    private String applyId;
    /**
     *手机号
     */
    private String mobile;
    /**
     *省
     */
    private String province;
    /**
     *市
     */
    private String city;
    /**
     *区
     */
    private String district;
    /**
     *详细地址
     */
    private String address;

    public BigDecimal getApproveAmt() {
        return approveAmt;
    }

    public void setApproveAmt(BigDecimal approveAmt) {
        this.approveAmt = approveAmt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
