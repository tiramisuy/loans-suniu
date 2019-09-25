package com.rongdu.loans.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rongdu.loans.api.common.LoanWalletUtil;
import com.rongdu.loans.api.web.option.LoanWalletRequest;
import com.rongdu.loans.loan.option.dkqb.LoanWalletVO;
import com.rongdu.loans.loan.service.LoanWalletService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 贷款钱包
 */
@Controller
@RequestMapping(value = "loanWallet")
public class LoanWalletController {

    private Logger logger = LoggerFactory.getLogger(LoanWalletController.class);

    @Autowired
    LoanWalletService loanWalletService;

    @RequestMapping(value = "route")
    @ResponseBody
    public LoanWalletVO loanWallet(@RequestBody LoanWalletRequest request) throws IOException {
        long sessionId = System.currentTimeMillis();
        LoanWalletVO response = new LoanWalletVO();
        try {
            logger.info(sessionId + "LoanWalletController loanWallet method");
            // 获取业务参数
            String code = request.getCode();
            String method = request.getMethod();
            String sign = request.getSign();
            Integer timestamp = request.getTimestamp();
            JSONObject data = request.getData();
            logger.info(sessionId + "code={},sign={},method={},timestamp={},data={}", new Object[]{code, sign, method, timestamp, data});

            if (StringUtils.isBlank(code) || StringUtils.isBlank(sign)  || StringUtils.isBlank(method) || data == null) {
                response.setCode(LoanWalletVO.CODE_ERROR);
                response.setMessage("入参不合法");
                logger.error(sessionId + "：结束loanWallet：" + JSON.toJSONString(response));
                return response;
            }

            // 验证签名
            boolean flag = LoanWalletUtil.checkSign(sign, code, method, data);
            if (!flag) {
                response.setCode(LoanWalletVO.CODE_ERROR);
                response.setMessage("sign错误");
                logger.info(sessionId + "：结束loanWallet：" + JSON.toJSONString(response));
                return response;
            }


            // 处理业务
            if ("pushUser".equals(method)) {
                response =  loanWalletService.pushUser(data);
            } else if ("isUserAccept".equals(method)){
                // 用户检测
                response =  loanWalletService.isUserAccept(data);
            } else if ("loanCalculate".equals(method)){
                // 借款试算
                response =  loanWalletService.loanCalculate(data);
            } else if ("getContractList".equals(method)){
                // 获取合同
                response =  loanWalletService.getContractList(data);
            } else if ("getBankList".equals(method)){
                // 获取绑卡银行列表
                response =  loanWalletService.getBankList(data);
            } else if ("bindCard".equals(method)){
                // 贷款钱包API绑卡接口
                response =  loanWalletService.bindCard(data);
            } else if ("submitLoan".equals(method)){
                // 发起提现
                response =  loanWalletService.submitLoan(data);
            } else if ("repayment".equals(method)){
                // 主动还款
                response =  loanWalletService.repayment(data);
            } else {
                response.setCode(LoanWalletVO.CODE_ERROR);
                response.setMessage("method错误");
                logger.info(sessionId + "：结束loanWallet：" + JSON.toJSONString(response));
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(LoanWalletVO.CODE_ERROR);
            response.setMessage("数据不完整");
        }
        logger.info("：结束loanWallet：" + JSON.toJSONString(response));
        return response;
    }



}
