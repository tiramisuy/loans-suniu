package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/7/27.
 */
public class DeductionFromVO implements Serializable {

    private RepayDetailListVO repayDetailListVO;

    private List<DeductionLogVO> list;

    private String ctx;

    private String source;

    public RepayDetailListVO getRepayDetailListVO() {
        return repayDetailListVO;
    }

    public void setRepayDetailListVO(RepayDetailListVO repayDetailListVO) {
        this.repayDetailListVO = repayDetailListVO;
    }

    public String getCtx() {
        return ctx;
    }

    public void setCtx(String ctx) {
        this.ctx = ctx;
    }

    public List<DeductionLogVO> getList() {
        return list;
    }

    public void setList(List<DeductionLogVO> list) {
        this.list = list;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
