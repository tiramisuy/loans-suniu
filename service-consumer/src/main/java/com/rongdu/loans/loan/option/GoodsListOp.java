package com.rongdu.loans.loan.option;

import java.io.Serializable;

/**
 * @author qifeng
 * @date 2018/11/29 0029
 */
public class GoodsListOp implements Serializable {

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品上下架状态(0:下架 1：上架)
     */
    private String status;

    private Integer pageNo = 1;
    private Integer pageSize = 10;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
