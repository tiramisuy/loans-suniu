package com.rongdu.loans.api.web.vo;

import com.rongdu.loans.cust.vo.UserRecordVO;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/21.
 */
public class RecordParamsVO implements Serializable {

    private String tx = "202";

    private String version = "v3";

    private UserRecordVO data;

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public UserRecordVO getData() {
        return data;
    }

    public void setData(UserRecordVO data) {
        this.data = data;
    }
}
