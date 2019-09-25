package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/8/4.
 */
public class BorrowlistVO implements Serializable {

    private List<RepayItemDetailVO> list;

    private String ctx;

    public List<RepayItemDetailVO> getList() {
        return list;
    }

    public void setList(List<RepayItemDetailVO> list) {
        this.list = list;
    }

    public String getCtx() {
        return ctx;
    }

    public void setCtx(String ctx) {
        this.ctx = ctx;
    }
}
