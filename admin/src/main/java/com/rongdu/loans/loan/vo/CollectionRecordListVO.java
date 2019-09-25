package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/9/28.
 */
public class CollectionRecordListVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private List<CollectionRecordVO> list;

    public List<CollectionRecordVO> getList() {
        return list;
    }

    public void setList(List<CollectionRecordVO> list) {
        this.list = list;
    }
}
