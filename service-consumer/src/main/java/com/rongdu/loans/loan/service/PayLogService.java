/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.koudai.vo.KDwithdrawRecodeVO;
import com.rongdu.loans.loan.option.WithdrawDetailListOP;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.WithdrawDetailListVO;

/**
 * 提现/代付-业务逻辑接口
 *
 * @author zhangxiaolong
 * @version 2017-07-10
 */
public interface PayLogService {

    /**
     * 提现明细列表查询
     *
     * @param op
     * @return
     */
    Page<WithdrawDetailListVO> withdrawList(@NotNull(message = "参数不能为空") WithdrawDetailListOP op);

    List<WithdrawDetailListVO> exportWithdrawList(@NotNull(message = "参数不能为空") WithdrawDetailListOP op);

    /**
     * 查询提现/代付信息
     *
     * @param id
     * @return
     */
    public PayLogVO get(String id);

    /**
     * 保存提现/代付信息
     *
     * @param vo
     * @return
     */
    public int save(PayLogVO vo);

    /**
     * 更新提现/代付信息
     *
     * @param vo
     * @return
     */
    public int update(PayLogVO vo);

    /**
     * 根据原收款订单号查询代付订单
     *
     * @param origOrderNo
     * @return
     */
    public PayLogVO findByOrigOrderNo(String origOrderNo);

    /**
     * 更新代付订单状态
     *
     * @param vo
     * @return
     */
    public int updatePayResult(PayLogVO vo);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo
     * @return
     */
    public PayLogVO findByChlOrderNo(String orderNo);

    /**
     * 查询未成功的付款订单
     *
     * @return
     */
    public List<PayLogVO> findUnsolvedOrders();

    /**
     * 查询充值订单已经付款(成功、处理中)的金额
     *
     * @return
     */
    public Double findPayedAmt(String origOrderNo);

    /**
     * 删除代付订单
     *
     * @param vo
     * @return
     */
    public int delete(PayLogVO vo);

    /**
     * 是否有代付记录
     *
     * @param applyId
     * @return
     */
    public int findPaymentRecord(String applyId);

    /**
     * code y0621
     *
     * @Title: findPayUnsolvedOrders
     * @Description: 查找先锋处理中记录
     * @param @return 参数
     * @return List<PayLog> 返回类型
     */
    List<PayLogVO> findPayUnsolvedOrders();

    /**
     * 查询乐视宝付代付处理中记录
     */

    List<PayLogVO> findBaofooPayUnsolvedOrders(List<String> statusList);


    /**
     * 查询通联放款处理中记录
     */

    List<PayLogVO> findTonglianPayUnsolvedOrders(List<String> statusList);

    /**
     * 查询通联独立放款账户处理中记录
     */

    List<PayLogVO> findTonglianLoanPayUnsolvedOrders(List<String> statusList);

    /**
     * 查询通融宝付代付处理中记录
     */

    List<PayLogVO> findTRBaofooPayUnsolvedOrders(List<String> statusList);

    /**
     * code y0621
     *
     * @Title: findPayedXfWithdrawRecord
     * @Description: 查找指定applyId先锋代付记录
     * @param @param applyId
     */
    int findUnsolvedXfWithdrawRecord(String applyId);

    /**
     * 根据payLogId生成合同
     *
     * @param payLogId
     */
    String generatorContract(String payLogId);


    public Page getBFPayCount(KDPayCountOP payOP);

    /**
     * 查询代付处理中和成功的数量
     *
     * @param applyId
     * @param statusList
     * @return
     */
    Long countBaofooPayUnsolvedAndSuccess(String applyId, List<String> statusList);

    /**
     * 查询通联代付处理中和成功的数量
     *
     * @param applyId
     * @param statusList
     * @return
     */
    Long countTonglianPayUnsolvedAndSuccess(String applyId, List<String> statusList);

    /**
     * 根据userId查询汉金所最新一笔未结清可提现的记录
     * @param userId
     * @return
     */
    PayLogVO findWithdrawAmount(String userId);

    /**
     * 生成债权转让合同
     * @param op
     * @return
     */
    String generateEquitableAssignment(WithdrawDetailListOP op);

    /**
     * 汉金所查询提现记录
     * @return
     */
    List<KDwithdrawRecodeVO> findHJSWithdrawRecode(String userId);

    /**
     * 统计汉金所当日放款金额
     * @return
     */
    BigDecimal sumHanjsCurrPayedAmt();

    PayLogVO findWithdrawLogByApplyId(String applyId);

}