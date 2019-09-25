package com.rongdu.loans.statistical.service.impl;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.statistical.manager.StatisticalManager;
import com.rongdu.loans.statistical.service.StatisticalService;
import com.rongdu.loans.statistical.vo.WorkbenchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/17.
 */
@Service("statisticalService")
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private StatisticalManager statisticalManager;

    @Override
    public WorkbenchVO workbench() {
        /**  注册用户数量,申请用户数 ; 当日 & 总数 */
        WorkbenchVO vo = statisticalManager.userDataForWorkbench();
        /** 借款成功用户数,放款金额 ； 当日 & 总数 */
        WorkbenchVO contractData = statisticalManager.contractDataForWorkbench();
        /** 待办审批任务总数 */
        int taskNumber = statisticalManager.applyDataForWorkbench();
        /** 昨日还款成功笔数, 昨日还款失败总笔数, 还款失败总笔数 */
        Date yesterday = DateUtils.addDay(new Date(), -1);
        Date begin = DateUtils.getDayBegin(yesterday);
        Date end = DateUtils.getDayEnd(yesterday);
        WorkbenchVO itemData = statisticalManager.applyDataForRepayPlanItem(begin, end);

        /** 组装数据 **/
        vo.setBorrowerCurrent(contractData.getBorrowerCurrent());
        vo.setBorrowerTotal(contractData.getBorrowerTotal());
        vo.setLoanCurrent(contractData.getLoanCurrent());
        vo.setLoanTotal(contractData.getLoanTotal());
        vo.setTaskNumber(taskNumber);
        vo.setSuccesNumber(itemData.getSuccesNumber());
        vo.setFailNumber(itemData.getFailNumber());
        vo.setFailTotalNumber(itemData.getFailTotalNumber());

        return vo;
    }
}
