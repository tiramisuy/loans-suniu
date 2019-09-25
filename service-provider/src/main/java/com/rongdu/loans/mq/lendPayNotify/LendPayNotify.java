package com.rongdu.loans.mq.lendPayNotify;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/3.
 */
public class LendPayNotify implements Serializable {

    private String strbgData;
    private String batchLendPayDate;
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
