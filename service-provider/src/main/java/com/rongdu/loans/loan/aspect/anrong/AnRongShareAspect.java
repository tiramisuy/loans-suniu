package com.rongdu.loans.loan.aspect.anrong;

import com.rongdu.common.mq.QueueConfig;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.service.ApplyTripartiteAnrongService;
import com.rongdu.loans.loan.vo.OverdueVO;
import com.rongdu.loans.mq.jdq.MessageProductorService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/6/24
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class AnRongShareAspect {
    @Autowired
    private ApplyTripartiteAnrongService applyTripartiteAnrongService;
    @Autowired
    private MessageProductorService messageProductorService;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;

    /**
     * @Title: overduePointcut
     * @Description: 贷款逾期-共享新增逾期反馈切点
     */
    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.RongPointCutService.overduePointForAnRong(..))")
    void overduePointcut() {
    }
    @Async
    @AfterReturning(value = "overduePointcut()&&args(overdueVOS)")
    void overdueFeedBack(List<OverdueVO> overdueVOS) throws Exception {
        for (OverdueVO overdueVO : overdueVOS) {
            if (!overdueVO.getThisTerm().equals(overdueVO.getTotalTerm())) {
                // 不是最后一期逾期，不调用共享新增逾期接口
                continue;
            }

            //Thread.sleep(2000);
            boolean exist = applyTripartiteAnrongService.isExistApplyId(overdueVO.getApplyId(), "01");// 已共享审批通过订单
            if (!exist) {
                // 不在安融三方表中，不调用共享新增逾期接口
                continue;
            }

            BigDecimal actualRepayAmt = BigDecimal.ZERO;
            List<RepayPlanItem> items = repayPlanItemManager.getByApplyId(overdueVO.getApplyId());
            for (RepayPlanItem item : items) {
                actualRepayAmt = CostUtils.add(actualRepayAmt, item.getActualRepayAmt());
            }

            ShareVO shareVO = new ShareVO();
            shareVO.setCustomerName(overdueVO.getUserName());
            shareVO.setPaperNumber(overdueVO.getIdNo());
            shareVO.setLoanId(overdueVO.getApplyId());
            shareVO.setLoanTypeDesc("信用借款");
            shareVO.setNbMoney(CostUtils.sub(overdueVO.getTotalAmount(),actualRepayAmt).toString());
            shareVO.setOverdueStartDate(DateUtils.formatDate(DateUtils.addDay(overdueVO.getRepayDate(), 1)));
            shareVO.setState("02");
            // mq
            messageProductorService.sendOverdueStatusAnRong(shareVO, QueueConfig.PUSH_ANRONG_SHARED_OVERDUESTATUS);
        }
    }
}
