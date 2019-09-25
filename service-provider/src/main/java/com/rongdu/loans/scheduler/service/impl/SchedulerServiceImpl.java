package com.rongdu.loans.scheduler.service.impl;

import com.rongdu.common.config.Global;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.borrow.service.HelpApplyService;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.external.manager.PushAssetManager;
import com.rongdu.loans.koudai.service.KDCreateService;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.scheduler.service.SchedulerService;
import com.rongdu.loans.statistical.service.ZhimaStatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangxiaolong on 2017/8/4.
 */
@Service("schedulerService")
public class SchedulerServiceImpl extends BaseService implements SchedulerService {

    @Autowired
    private BorrowInfoService borrowInfoService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private PushAssetManager pushAssetManager;
    @Autowired
    private ZhimaStatisticalService zhimaStatisticalService;
    @Autowired
    private OverdueService overdueService;
    @Autowired
    private CollectionAssignmentService collectionAssignmentService;
    @Autowired
    private RepayUnsolvedService repayUnsolvedService;
    @Autowired
    private ShopWithholdService shopWithholdService;
    @Autowired
    private PayUnsolvedService payUnsolvedService;
    @Autowired
    private HelpApplyService helpApplyService;
    @Autowired
    private LoanMarketManagementService loanMarketManagementService;
    @Autowired
    private KDPayService kdPayService;
    @Autowired
    private KDCreateService kdCreateService;
    @Autowired
    private DWDService dwdService;
    @Autowired
    private KDDepositService kdDepositService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RepayWarnService repayWarnService;

    /*
     * 更新逾期还款计划
     */
    @Override
    public TaskResult overdueDataCalc() {
        // 次日0点执行
        TaskResult calcResult = repayPlanItemService.overdueDataCalc(1);
        return new TaskResult(calcResult.getSuccNum(), calcResult.getFailNum());
    }

    /*
     * 更新投复利逾期还款（已移除）
     */
    @Override
    public TaskResult overdueDataCalcForTFL() {
        // 每天6点执行
        TaskResult calcResult = repayPlanItemService.overdueDataCalc(2);
        return new TaskResult(calcResult.getSuccNum(), calcResult.getFailNum());
    }

    /*
     * 平台推标
     */
    @Override
    public TaskResult push() {
        return borrowInfoService.push();
    }

    /*
     * 口袋推标
     */
    public TaskResult pushToKoudai() {
        return borrowInfoService.pushToKoudai();
    }

    /*
     * 还款当日代扣
     */
    @Override
    public TaskResult withholdBatch() {
        return withholdService.withholdBatch();
    }

    @Override
    public TaskResult withholdBatchAfterCurdate() {
        return withholdService.withholdBatchAfterCurdate();
    }

    /*
     * 还款当日代扣，海尔支付
     */
    @Override
    public TaskResult kjtpayTradeBankWitholding() {
        return withholdService.kjtpayTradeBankWitholding();
    }

    /*
     * 还款当日代扣，先锋支付
     */
    @Override
    public TaskResult xfWithholdTask() {
        return withholdService.xfWithholdTask();
    }

    /*
     * 还款当日代扣，通联支付
     */
    @Override
    public TaskResult tlWithholdTask() {
        return withholdService.tlWithholdTask();
    }


    /*
     * 还款提醒
     */
    @Override
    public TaskResult repayNotice() {
        return withholdService.repayNotice();
    }

    @Override
    public TaskResult preRepayPushAssetSide() {
        return pushAssetManager.preRepayPushTask();
    }

    @Override
    public TaskResult onTimeRepayPushAssetSide() {
        return pushAssetManager.onTimeRepayPushTask();
    }

    @Override
    public TaskResult overdueRepayPushAssetSide() {
        return pushAssetManager.overdueRepayPushTask();
    }

    @Override
    public TaskResult collectZhima() {
        return zhimaStatisticalService.collect();
    }

    @Override
    public TaskResult pushZhima() {
        return zhimaStatisticalService.pushZhimaCreditStatistics(new Date());
    }

    @Override
    public TaskResult returnBack() {
        return collectionAssignmentService.returnBack();
    }

    /*
     * 聚宝钱包逾期分配
     */
    @Override
    public TaskResult batchInsertOverdue() {
        return overdueService.batchInsertOverdue(1);
    }

    /*
     * 逾期15天催收分配,查询逾期15天的数据修改催收人信息
     */
    @Override
    public TaskResult batchOverdueOfFiveTeen() {
        return overdueService.batchOverdueOfFiveTeen();
    }

    /*
     * 定时取消通过审核的待推送订单
     */
    @Override
    public TaskResult cancelLoanApply() {
        return loanApplyService.cancelLoanApply();
    }

    /*
     * 定时取消大王贷通过审核的待推送订单
     */
    @Override
    public TaskResult cancelLoanApplyByDwd() {
        return loanApplyService.cancelLoanApplyByDwd();
    }

    @Override
    public TaskResult sendBindCardMsg() {
        return shortMsgService.sendBindCardMsg();
    }

    @Override
    public TaskResult sendRepayNotice() {
        return shortMsgService.sendRepayNotice();
    }

    @Override
    public TaskResult dealWithAnRong() {
        return reportService.dealWithAnRong();
    }

    /*
     * 查询支付/代扣结果并更新订单
     */
    @Override
    public TaskResult processRepayUnsolvedOrders() {
        return repayUnsolvedService.processRepayUnsolvedOrders();
    }    /*
     * 查询支付/代扣结果并更新订单
     */
    @Override
    public TaskResult processTLRepayUnsolvedOrders() {
        return repayUnsolvedService.processTLRepayUnsolvedOrders();
    }

    @Override
    public TaskResult cleanMsgSendStatistics() {
        // 清除缓存
        long rz = JedisUtils.delObject(Global.MW_SEND_DAY_COUNT);
        if (rz == 0) {
            return new TaskResult(0, 1);
        }
        return new TaskResult(1, 0);
    }

    /**
     * 第一笔放款(服务费)代扣定时任务
     */
    @Override
    public TaskResult batchShopWithHold() {
        return shopWithholdService.doShopWithholdTask();
    }

    /*
     * 查询先锋代付结果
     */
    @Override
    public TaskResult processPayUnsolvedOrders() {
        return payUnsolvedService.processPayUnsolvedOrders();
    }

    /**
     * 营销管理分配
     */
    @Override
    public TaskResult marketManagement() {
        return loanMarketManagementService.batchInsertMarketManagemetn();
    }

    /**
     * 助贷产品分配
     *
     * @return
     */
    @Override
    public TaskResult borrowHelpAllot() {
        return helpApplyService.borrowHelpAllot();
    }

    /**
     * 口袋放款处理中订单
     *
     * @return
     */
    @Override
    public TaskResult processKdPaying() {
        return kdPayService.processPayingTask();
    }

    /**
     * 口袋创建订单
     *
     * @return
     */
    @Override
    public TaskResult processCreateOrderTask() {
        return kdCreateService.processCreateOrderTask();
    }

    @Override
    public TaskResult pushToTongrong() {
        return borrowInfoService.pushToTongrong();
    }

    @Override
    public TaskResult pushToLeshi() {
        return borrowInfoService.pushToLeshi();
    }

    @Override
    public TaskResult pushToTonglian() {
        return borrowInfoService.pushToTonglian();
    }

    @Override
    public TaskResult saveUserAndApplyInfo() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int failNum = 0;
        Map<String, String> thirdKey = JedisUtils.getMap(Global.DWD_THIRD_KEY);
        if (thirdKey != null) {
            List<Map.Entry<String, String>> thirdKeyList = new ArrayList<>(thirdKey.entrySet());
            Collections.sort(thirdKeyList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    return map1.getValue().compareTo(map2.getValue());
                }
            });
            int limit = thirdKeyList.size() >= 200 ? 200 : thirdKeyList.size();
            List<Map.Entry<String, String>> thirdListLimit = thirdKeyList.subList(0, limit);
            for (Map.Entry<String, String> map : thirdListLimit) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Long.parseLong(map.getValue()) <= (System.currentTimeMillis() - 1000 * 60 * 1)
                        && Long.parseLong(map.getValue()) >= (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    String orderNo = map.getKey();
                    try {
                        dwdService.saveUserAndApplyInfo(orderNo);
                        succNum++;
                    } catch (Exception e) {
                        JedisUtils.mapRemove(Global.DWD_THIRD_KEY, orderNo);
                        failNum++;
                        logger.error("【大王贷】三方工单转化失败:orderNo={} 手工处理", orderNo,e);
                    }
                }else {

                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(failNum);
        return taskResult;
    }

    @Override
    public TaskResult approveFeedbackOfRedis() {
        return dwdService.approveFeedbackOfRedis();
    }

    @Override
    public TaskResult lendFeedbackOfRedis() {
        return dwdService.lendFeedbackOfRedis();
    }

    @Override
    public TaskResult settlementFeedbackOfRedis() {
        return dwdService.settlementFeedbackOfRedis();
    }

    @Override
    public TaskResult processKDWaitingLending() {
        return kdDepositService.processKDWaitingLending();
    }

    @Override
    public TaskResult pushToHanJS() {
        return borrowInfoService.pushToHanJS();
    }

    @Override
    public TaskResult processKDWithdrawOrder() {
        return kdDepositService.processKDWithdrawOrder();
    }

    @Override
    public TaskResult allotRepayWarn() {
        return repayWarnService.allotRepayWarn() ;
    }
}
