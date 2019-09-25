/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.dao.RefuseReasonDAO;
import com.rongdu.loans.loan.entity.RefuseReason;
import org.springframework.stereotype.Service;

/**
 * 贷款审核拒绝原因-实体管理接口
 * @author zhangxiaolong
 * @version 2017-07-07
 */
@Service("refuseReasonManager")
public class RefuseReasonManager extends BaseManager<RefuseReasonDAO, RefuseReason, String> {

    /**
     * 记录拒绝次数，在原有次数上加一
     * @param id
     * @return
     */
    public int record(String id) {
        int result = dao.record(id);
        if (result > 0){
            RefuseReason refuseReason = dao.getById(id);
            if (StringUtils.isNotBlank(refuseReason.getPid())){
                dao.record(refuseReason.getPid());
            }
        }
        return result;
    }
	
}