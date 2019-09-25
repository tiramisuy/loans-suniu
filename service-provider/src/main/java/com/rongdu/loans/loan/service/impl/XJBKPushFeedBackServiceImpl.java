package com.rongdu.loans.loan.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.loans.common.XJ360FQUtil;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.xjbk.LoanRepayPlanVO;
import com.rongdu.loans.loan.option.xjbk.RepaymentPlan;
import com.rongdu.loans.loan.option.xjbk.ThirdResponse;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaRepaymentPlan;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.XJBKPushFeedBackService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: XJBKPushFeedBackServiceImpl.java
 * @Package com.rongdu.loans.loan.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: yuanxianchu
 * @date 2018年8月20日
 */
@Slf4j
@Service("xjbkPushFeedBackService")
public class XJBKPushFeedBackServiceImpl implements XJBKPushFeedBackService {

    @Autowired
    private ApplyTripartiteService applyTripartiteService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;

    @Override
    public void pushRepayPlan(String orderNo) {
        log.info("----------开始异步推送【现金白卡-还款计划】----------");
        // 还款计划推送数据组合
        String applyId = applyTripartiteService.getApplyIdByThirdId(orderNo);
        ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
        if (applyAllotVO.getApproveTerm().equals(90) || applyAllotVO.getApproveTerm().equals(28)) {
            XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = pullRepaymentPlan(orderNo);
            if (xianJinBaiKaRepaymentPlan == null) {
                log.error("工单：{}异常，还款计划为空！！！", orderNo);
                return;
            }

            ThirdResponse thirdResponse = new ThirdResponse();
            try {
                String call = "Partner.Order.repayPlanFeedback";
                String response = XJ360FQUtil.XianJinBaiKaRequest(xianJinBaiKaRepaymentPlan, call);
                thirdResponse = JSON.parse(response, ThirdResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.debug("工单：{}，还款计划回调结果：{}", orderNo, thirdResponse);
            log.info("----------结束异步推送【现金白卡-还款计划】----------");
        }

    }

    @Override
    public XianJinBaiKaRepaymentPlan pullRepaymentPlan(String orderNo) {
        XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = new XianJinBaiKaRepaymentPlan();
        String applyId = applyTripartiteService.getApplyIdByThirdId(orderNo);
        LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        if (loanApplySimpleVO == null || loanApplySimpleVO.getProcessStatus() == null
                || loanApplySimpleVO.getProcessStatus().intValue() < XjdLifeCycle.LC_CASH_4) {
            return xianJinBaiKaRepaymentPlan;
        }
        LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);
        if (repayPlan == null) {
            return xianJinBaiKaRepaymentPlan;
        }
        if (loanApplySimpleVO.getApproveTerm().equals(28)) {
            xianJinBaiKaRepaymentPlan.setOrderSn(orderNo);
            xianJinBaiKaRepaymentPlan.setAlreadyPaid((repayPlan.getPayedPrincipal().add(repayPlan.getPayedInterest()))
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setFinishPeriod(repayPlan.getPayedTerm());
            xianJinBaiKaRepaymentPlan.setReceivedAmount(repayPlan.getPrincipal().intValue() * 100);
            xianJinBaiKaRepaymentPlan.setTotalAmount(loanApplySimpleVO.getTotalRepayAmount().multiply(new BigDecimal(100))
                    .intValue());
            xianJinBaiKaRepaymentPlan.setTotalPeriod(repayPlan.getTotalTerm());
            xianJinBaiKaRepaymentPlan.setTotalSvcFee(0);
            RepayDetailListOP op = new RepayDetailListOP();
            op.setContNo(repayPlan.getContNo());
            op.setIsDelaySettlement(2);
            List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
            ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
            for (int i = 0; i < voList.size(); i++) {
                RepayDetailListVO repayDetailListVO = voList.get(i);
                RepaymentPlan repaymentPlan = new RepaymentPlan();
                repaymentPlan.setTotalAmount(repayDetailListVO.getTotalAmount().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setAlreadyPaid(repayDetailListVO.getActualRepayAmt() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getActualRepayAmt().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setBillStatus(repayDetailListVO.getStatus());
                repaymentPlan.setCanPayTime((int) (repayDetailListVO.getStartDate().getTime() / 1000));
                repaymentPlan.setDueTime((int) (repayDetailListVO.getRepayDate().getTime() / 1000 + 86399));
                repaymentPlan.setFinishPayTime(repayDetailListVO.getActualRepayTime() == null ? 0
                        : (int) (repayDetailListVO.getActualRepayTime().getTime() / 1000));
                repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setLoanTime(repayDetailListVO.getStartDate() == null ? 0 : (int) (repayDetailListVO
                        .getStartDate().getTime() / 1000));
                repaymentPlan.setOverdueDay(repayDetailListVO.getOverdue() == null ? 0 : repayDetailListVO.getOverdue());
                repaymentPlan.setPayType(repayDetailListVO.getStatus());
                repaymentPlan.setOverdueFee(repayDetailListVO.getOverdueFee() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getOverdueFee().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodFeeDesc("");
                repaymentPlan.setServiceFee(0);
                repaymentPlan.setPrinciple(repayDetailListVO.getPrincipal() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getPrincipal().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodNo(repayDetailListVO.getThisTerm().toString());
                list.add(repaymentPlan);
                xianJinBaiKaRepaymentPlan.setRepaymentPlan(list);
            }

        } else {
            xianJinBaiKaRepaymentPlan.setOrderSn(orderNo);
            xianJinBaiKaRepaymentPlan.setAlreadyPaid((repayPlan.getPayedPrincipal().add(repayPlan.getPayedInterest()))
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setFinishPeriod(repayPlan.getPayedTerm());
            xianJinBaiKaRepaymentPlan.setReceivedAmount(200000);
            xianJinBaiKaRepaymentPlan.setTotalAmount(loanApplySimpleVO.getTotalRepayAmount().multiply(new BigDecimal(100))
                    .intValue());
            xianJinBaiKaRepaymentPlan.setTotalPeriod(repayPlan.getTotalTerm());
            xianJinBaiKaRepaymentPlan.setTotalSvcFee(0);
            RepayDetailListOP op = new RepayDetailListOP();
            op.setContNo(repayPlan.getContNo());
            op.setIsDelaySettlement(2);
            List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
            ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
            for (int i = 0; i < voList.size(); i++) {
                RepayDetailListVO repayDetailListVO = voList.get(i);
                RepaymentPlan repaymentPlan = new RepaymentPlan();
                repaymentPlan.setTotalAmount(repayDetailListVO.getTotalAmount().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setAlreadyPaid(repayDetailListVO.getActualRepayAmt() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getActualRepayAmt().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setBillStatus(repayDetailListVO.getStatus());
                repaymentPlan.setCanPayTime((int) (repayDetailListVO.getStartDate().getTime() / 1000));
                repaymentPlan.setDueTime((int) (repayDetailListVO.getRepayDate().getTime() / 1000 + 86399));
                repaymentPlan.setFinishPayTime(repayDetailListVO.getActualRepayTime() == null ? 0
                        : (int) (repayDetailListVO.getActualRepayTime().getTime() / 1000));
                if (i == 0) {
                    repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                            : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue() + 100);
                } else {
                    repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                            : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue());
                }
                repaymentPlan.setLoanTime(repayDetailListVO.getStartDate() == null ? 0 : (int) (repayDetailListVO
                        .getStartDate().getTime() / 1000));
                repaymentPlan.setOverdueDay(repayDetailListVO.getOverdue() == null ? 0 : repayDetailListVO.getOverdue());
                repaymentPlan.setPayType(repayDetailListVO.getStatus());
                repaymentPlan.setOverdueFee(repayDetailListVO.getOverdueFee() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getOverdueFee().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodFeeDesc("");
                repaymentPlan.setServiceFee(0);
                repaymentPlan.setPrinciple(repayDetailListVO.getPrincipal() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getPrincipal().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodNo(repayDetailListVO.getThisTerm().toString());
                list.add(repaymentPlan);
                xianJinBaiKaRepaymentPlan.setRepaymentPlan(list);
            }
        }
        return xianJinBaiKaRepaymentPlan;
    }

}
