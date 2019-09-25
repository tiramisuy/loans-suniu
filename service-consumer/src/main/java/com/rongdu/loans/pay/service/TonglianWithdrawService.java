package com.rongdu.loans.pay.service;

import com.rongdu.common.task.TaskResult;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.PayLogVO;

/**
 * 通联
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
public interface TonglianWithdrawService {


    /**
     * 对未成功的付款订单进行处理：<br>
     * 1、每隔10分钟定时跑批，查询未成功的付款订单 <br>
     * 2、通过"交易结果查询(200004)"进行查证<br>
     * 3、根据查证结果，更新本地付款订单状态 <br>
     *
     * @throws Exception
     */
    TaskResult processTlUnsolvedOrders();


    /**
     * 对未成功的放款订单进行处理：<br>
     * 1、每隔10分钟定时跑批，查询未成功的付款订单 <br>
     * 2、通过"交易结果查询"进行查证<br>
     * 3、根据查证结果，更新本地付款订单状态 <br>
     *
     * @throws Exception
     */
    TaskResult processTlLoanUnsolvedOrders();


    /**
     * 重新付款
     *
     * @param pw
     */
    AdminWebResult reWithdraw(String payNo);

    /**
     * 查询通联放款结果(商户1)
     * @param vo
     * @return
     */
    PayLogVO queryLoanResultTL1(PayLogVO vo);

    /**
     * 查询通联放款结果(商户2)
     * @param vo
     * @return
     */
    PayLogVO queryLoanResultTL2(PayLogVO vo);
}
