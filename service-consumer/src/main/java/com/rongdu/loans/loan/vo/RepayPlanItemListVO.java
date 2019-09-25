package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class RepayPlanItemListVO implements Serializable {

    private static final long serialVersionUID = -6219847033510500874L;

    private List<RepayDetailListVO> list;

    public List<RepayDetailListVO> getList() {
        return list;
    }

    public void setList(List<RepayDetailListVO> list) {
        this.list = list;
    }
}
