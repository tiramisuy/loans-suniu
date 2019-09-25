package com.rongdu.loans.loan.service;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.loans.loan.option.dkqb.LoanWalletVO;

/**
 * 贷款钱包
 */
public interface LoanWalletService {

    public LoanWalletVO pushUser(JSONObject data);

    public boolean saveIntoOrder(String jsonData, String type);

    /**
     * 用户检测接口
     * @param data
     * @return
     */
    LoanWalletVO isUserAccept(JSONObject data);

    /**
     * 贷款试算接口
     * @param data
     * @return
     */
    LoanWalletVO loanCalculate(JSONObject data);

    /**
     * 获取合同接口
     * @param data
     * @return
     */
    LoanWalletVO getContractList(JSONObject data);

    /**
     * 获取银行列表
     * @param data
     * @return
     */
    LoanWalletVO getBankList(JSONObject data);

    /**
     * 贷款钱包API绑卡接口
     * @param data
     * @return
     */
    LoanWalletVO bindCard(JSONObject data);

    /**
     * 发起提现申请
     * @param data
     * @return
     */
    LoanWalletVO submitLoan(JSONObject data);

    /**
     * 主动还款
     * @param data
     * @return
     */
    LoanWalletVO repayment(JSONObject data);
}
