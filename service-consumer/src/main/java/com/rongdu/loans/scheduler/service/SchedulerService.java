package com.rongdu.loans.scheduler.service;

import com.rongdu.common.task.TaskResult;

/**
 * 定时任务 Created by zhangxiaolong on 2017/8/4.
 */
public interface SchedulerService {

    /**
     * 逾期数据计算
     */
    TaskResult overdueDataCalc();

    /**
     * 逾期数据计算(TFL and CCD)
     */
    TaskResult overdueDataCalcForTFL();

    /**
     * 推送资产
     *
     * @return
     */
    TaskResult push();

    /**
     * 口袋放款
     *
     * @return
     */
    TaskResult pushToKoudai();

    /**
     * 乐视放款
     *
     * @return
     */
    TaskResult pushToLeshi();

    /**
     * 通联放款
     *
     * @return
     */
    TaskResult pushToTonglian();

    /**
     * 汉金所放款
     *
     * @return
     */
    TaskResult pushToHanJS();

    /**
     * 代扣
     *
     * @return
     */
    TaskResult withholdBatch();

    TaskResult withholdBatchAfterCurdate();

    /**
     * 海尔代扣
     *
     * @return
     */
    TaskResult kjtpayTradeBankWitholding();

    /**
     * 先锋定时代扣
     *
     * @return
     */
    TaskResult xfWithholdTask();

    /**
     * 通联定时代扣
     *
     * @return
     */
    TaskResult tlWithholdTask();

    /**
     * 还款通知定时任务
     *
     * @return
     */
    TaskResult repayNotice();

    /**
     * 提前还款推送资产端（聚宝钱包）定时任务
     *
     * @return
     */
    TaskResult preRepayPushAssetSide();

    /**
     * 正常还款推送资产端（聚宝钱包）定时任务
     *
     * @return
     */
    TaskResult onTimeRepayPushAssetSide();

    /**
     * 逾期还款推送资产端（聚宝钱包）定时任务
     *
     * @return
     */
    TaskResult overdueRepayPushAssetSide();

    /**
     * 收集汇总芝麻信用接入数据
     *
     * @return
     */
    TaskResult collectZhima();

    /**
     * 上送当日的芝麻接口数据
     *
     * @return
     */
    TaskResult pushZhima();

    /**
     * 催收分配
     *
     * @return
     */
    TaskResult batchInsertOverdue();

    /**
     * 催收分配退回任务
     *
     * @return
     */
    TaskResult returnBack();

    /**
     * 每天零点清理统计短信数缓存
     *
     * @return
     */
    TaskResult cleanMsgSendStatistics();

    /**
     * 查询支付/代扣结果并更新订单
     *
     * @return
     */
    TaskResult processRepayUnsolvedOrders();

    /**
     * 查询通联代扣结果并更新订单
     *
     * @return
     */
    TaskResult processTLRepayUnsolvedOrders();

    /**
     * 执行第二三次购物款代扣定时任务
     *
     * @return
     */
    TaskResult batchShopWithHold();

    /**
     * code y0621
     *
     * @Title: processPayUnsolvedOrders
     * @Description: 查询先锋代发处理中订单
     */
    TaskResult processPayUnsolvedOrders();

    /**
     * 营销管理分配
     *
     * @return
     */
    TaskResult marketManagement();

    /**
     * 助贷产品分配
     *
     * @return
     */
    TaskResult borrowHelpAllot();

    /**
     * 口袋放款处理中订单
     *
     * @return
     */
    TaskResult processKdPaying();

    /**
     * 口袋创建订单
     *
     * @return
     */
    public TaskResult processCreateOrderTask();

    /**
     * 通融放款
     *
     * @return
     */
    TaskResult pushToTongrong();

    TaskResult saveUserAndApplyInfo();

    TaskResult approveFeedbackOfRedis();

    TaskResult lendFeedbackOfRedis();

    TaskResult settlementFeedbackOfRedis();

    /*
     * 处理口袋存管待放款订单查询
     */
    public TaskResult processKDWaitingLending();

    /*
     * 处理口袋存管 24小时未提现订单
     */
    public TaskResult processKDWithdrawOrder();

    /*
     * 逾期15天催收分配,查询逾期15天的数据修改催收人信息
     */
    public TaskResult batchOverdueOfFiveTeen();

    /*
     * 定时取消借点钱通过审核的待推送订单
     */
    TaskResult cancelLoanApply();

    /*
     * 定时取消大王贷通过审核的待推送订单
     */
    TaskResult cancelLoanApplyByDwd();

    /**
     * 终审通过，发送绑卡通知短信
     *
     * @return
     */
    TaskResult sendBindCardMsg();

    /**
     * 还款日前一天，发送还款提醒短信
     *
     * @return
     */
    TaskResult sendRepayNotice();

    /**
     * 安融定时处理共享数据
     */
    TaskResult dealWithAnRong();

    /**
     * 还款预提醒分配
     *
     * @return
     */
    TaskResult allotRepayWarn();
}
