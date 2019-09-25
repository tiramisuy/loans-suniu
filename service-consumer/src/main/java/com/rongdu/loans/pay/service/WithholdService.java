package com.rongdu.loans.pay.service;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.rongdu.loans.enums.PayTypesEnum;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.vo.WithholdRepayPlanQueryVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.WithholdQueryResultVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;

/**
 * 代付接口
 *
 * @author likang
 */
public interface WithholdService {

    /**
     * 代扣定时任务
     *
     * @return
     */
    TaskResult withholdBatch();

    TaskResult withholdBatchAfterCurdate();

    /**
     * 海尔支付 代扣定时任务
     */
    TaskResult kjtpayTradeBankWitholding();

    /**
     * 先锋 定时代扣任务
     *
     * @return
     */
    TaskResult xfWithholdTask();

    /**
     * 通联 定时代扣任务
     *
     * @return
     */
    TaskResult tlWithholdTask();


    /**
     * 逾期代扣
     *
     * @param itemId
     * @return
     */
    Boolean overdueWithhold(@NotNull(message = "参数不能为空") String itemId);

    /**
     * 逾期代扣，返回代扣信息
     *
     * @param itemId
     * @return
     */
    WithholdResultVO adminOverdueWithhold(@NotNull(message = "参数不能为空") String itemId, PayTypesEnum payTypesEnum);

    /**
     * code y0524
     *
     * @param @param  itemId
     * @param @param  delayAmt
     * @param @param  withholdType 代扣类型 baofu-> 宝付   tonglian->通联
     * @param @return 参数
     * @return WithholdResultVO 返回类型
     * @Title: delayDealWithhold
     * @Description: 延期代扣
     */
    WithholdResultVO delayDealWithhold(@NotNull(message = "参数不能为空") String itemId,
                                       @NotNull(message = "延期金额不能为空") String delayAmt, PayTypesEnum payTypesEnum);

    /**
     * 还款通知定时任务
     *
     * @return
     */
    TaskResult repayNotice();

    /**
     * 更新订单相关信息
     *
     * @param repayPlanItemId       还款计划明细Id
     * @param WithholdQueryResultVO 宝付查询支付结果
     */
    public boolean updateOrderInfo(String repayPlanItemId, WithholdQueryResultVO retVo);

    /**
     * 后台手动代扣结算查询
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    public WithholdRepayPlanQueryVO processAdminWithholdQuery(String repayPlanItemId, String actualRepayTime,
                                                              Integer type);

    /**
     * 速牛的计算规则后台手动代扣结算查询
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayTime 实际还款时间
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    public WithholdRepayPlanQueryVO processAdminWithholdQueryBySuniu(String repayPlanItemId, String actualRepayTime,
                                                                     Integer type);

    /**
     * 后台手动代扣结算
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayAmt  实际还款金额
     * @param actualRepayTime 实际还款时间
     * @param prepayFee       提前还款手续费
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    @Transactional
    public boolean processAdminWithhold(String repayPlanItemId, BigDecimal actualRepayAmt, String prepayFee,
                                        String actualRepayTime, Integer type, String deductionAmt, String repayChannel, String repayTypeName);

    /**
     * 速牛后台手动代扣结算
     *
     * @param repayPlanItemId 还款计划明细Id
     * @param actualRepayAmt  实际还款金额
     * @param actualRepayTime 实际还款时间
     * @param prepayFee       提前还款手续费
     * @param type            还款分类 1=一次性还款付息 ，2=部分还款，3=提前结清
     */
    @Transactional
    public boolean processAdminWithholdBySuniu(String repayPlanItemId, BigDecimal actualRepayAmt, String prepayFee,
                                               String actualRepayTime, Integer type, String deductionAmt, String repayChannel, String repayTypeName);


    /**
     * p2p回调代扣
     *
     * @param outsidSerialNo
     * @param repayAmt
     * @param repayTerm
     * @param repayDate
     * @return
     */
    public boolean processP2pWithhold(String outsidSerialNo, BigDecimal repayAmt, Integer repayTerm, String repayDate);

    /**
     * code y0602
     *
     * @param @param applyId 订单号
     * @param @param amount 代扣金额(分)
     * @param @param payType 支付类型
     * @return XfAgreementPayResultVO 返回类型
     * @Title: xfWithholdServFee
     * @Description: 放款成功，先锋单笔代扣(贷前代扣)
     */
    public XfAgreementPayResultVO xfWithholdByApplyId(String applyId, String amount, Integer payType);

    /**
     * code y0602
     *
     * @param @param merchantNo 商户订单号 （发起代扣时生成，获得响应后作为repayLog的ID保存）
     * @return XfAgreementPayResultVO 返回类型
     * @Title: xfWithholdServFeeQuery
     * @Description: 先锋代扣单表订单查询
     */
    public XfAgreementPayResultVO xfWithholdQuery(String merchantNo);

    @Transactional
    WithholdResultVO withholdXianJinBaiKa(String itemId);

    ConfirmAuthPayVO agreementPay(RePayOP rePayOP);

    ConfirmAuthPayVO agreementPayTest(RePayOP rePayOP);

    /**
     * 主动还款
     *
     * @param rePayOP
     * @return
     */
    ConfirmAuthPayVO agreementTonglianPay(RePayOP rePayOP);

    WithholdResultVO partWithhold(String repayPlanItemId, String amount, Integer payType);
}
