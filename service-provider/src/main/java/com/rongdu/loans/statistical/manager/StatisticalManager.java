package com.rongdu.loans.statistical.manager;


import com.rongdu.loans.statistical.dao.StatisticalDAO;
import com.rongdu.loans.statistical.vo.WorkbenchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 后台统计
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("statisticalManager")
public class StatisticalManager {


    @Autowired
    private StatisticalDAO statisticalDAO;

    /**
     * 查询 当日注册用户数量 & 总用户数量
     * @return
     */
    public WorkbenchVO userDataForWorkbench() {
        return statisticalDAO.userDataForWorkbench();
    }

    /**
     * 借款成功用户数,放款金额 ； 当日 & 总数
     * @return
     */
    public WorkbenchVO contractDataForWorkbench() {
        return statisticalDAO.contractDataForWorkbench();
    }

    /**
     * 待办审批任务总数
     * @return
     */
    public int applyDataForWorkbench() {
        return statisticalDAO.applyDataForWorkbench();
    }

    /**
     * 昨日还款成功笔数, 昨日还款失败总笔数, 还款失败总笔数
     * @param begin
     * @param end
     * @return
     */
    public WorkbenchVO applyDataForRepayPlanItem(Date begin, Date end) {
        return statisticalDAO.applyDataForRepayPlanItem(begin, end);
    }


}
