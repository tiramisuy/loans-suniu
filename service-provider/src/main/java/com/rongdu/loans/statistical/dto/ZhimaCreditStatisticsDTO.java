package com.rongdu.loans.statistical.dto;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/28.
 */
public class ZhimaCreditStatisticsDTO implements Serializable {

    /**
     * 第三方用户唯一凭证，白骑士分配
     */
    private String partnerId;
    /**
     * 验证码，白骑士分配
     */
    private String verifyKey;
    /**
     * 商户标示
     */
    private String linkedMerchantId;

    private String productId = "102003";
    /**
     * 业务参数
     */
    private ZhimaCreditStatisticsExtParamDTO extParam;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getLinkedMerchantId() {
        return linkedMerchantId;
    }

    public void setLinkedMerchantId(String linkedMerchantId) {
        this.linkedMerchantId = linkedMerchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZhimaCreditStatisticsExtParamDTO getExtParam() {
        return extParam;
    }

    public void setExtParam(ZhimaCreditStatisticsExtParamDTO extParam) {
        this.extParam = extParam;
    }
}
