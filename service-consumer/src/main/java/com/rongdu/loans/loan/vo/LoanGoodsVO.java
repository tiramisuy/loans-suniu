package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2018/5/8.
 */
public class LoanGoodsVO implements Serializable {

    private static final long serialVersionUID = -994042789785972681L;

    private String bagAmt;
    private String loanAmt;
    private String goodsName;
    private String goodsPrice;
    private String goodsPic;
    private String goodsNum;

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getBagAmt() {
        return bagAmt;
    }

    public void setBagAmt(String bagAmt) {
        this.bagAmt = bagAmt;
    }
}
