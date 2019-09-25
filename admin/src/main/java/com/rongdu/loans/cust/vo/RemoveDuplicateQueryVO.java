package com.rongdu.loans.cust.vo;

import com.rongdu.loans.loan.vo.ContactHistoryVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/8/3.
 */
public class RemoveDuplicateQueryVO implements Serializable{

    private List<ContactHistoryVO> list;

    private String flag;

    private String ctx;

    public List<ContactHistoryVO> getList() {
        return list;
    }

    public void setList(List<ContactHistoryVO> list) {
        this.list = list;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCtx() {
        return ctx;
    }

    public void setCtx(String ctx) {
        this.ctx = ctx;
    }
}
