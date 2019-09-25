package com.rongdu.loans.api.web.option;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/3.
 */
public class LendPayNotifyOP implements Serializable {

    private String strbgData;
    @NotBlank
    private String batchLendPayDate;
    @NotBlank
    private String productId;

    public String getStrbgData() {
        return strbgData;
    }

    public void setStrbgData(String strbgData) {
        this.strbgData = strbgData;
    }

    public String getBatchLendPayDate() {
        return batchLendPayDate;
    }

    public void setBatchLendPayDate(String batchLendPayDate) {
        this.batchLendPayDate = batchLendPayDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
