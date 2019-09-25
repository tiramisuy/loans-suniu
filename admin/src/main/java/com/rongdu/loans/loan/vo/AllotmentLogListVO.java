package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/9/28.
 */
public class AllotmentLogListVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private List<CollectionAssignmentVO> list;

    public List<CollectionAssignmentVO> getList() {
        return list;
    }

    public void setList(List<CollectionAssignmentVO> list) {
        this.list = list;
    }
}
