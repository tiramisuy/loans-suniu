package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * @author qifeng
 * @date 2019/3/1 0001
 */
public class ApplyListByApi extends ApplyListVO implements Serializable{

    private String tripartiteNo;

    public String getTripartiteNo() {
        return tripartiteNo;
    }

    public void setTripartiteNo(String tripartiteNo) {
        this.tripartiteNo = tripartiteNo;
    }
}
