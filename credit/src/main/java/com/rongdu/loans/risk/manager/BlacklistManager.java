/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;


import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.BlacklistDao;
import com.rongdu.loans.risk.entity.Blacklist;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.vo.BlacklistVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控黑名单-实体管理实现类
 *
 * @author sunda
 * @version 2017-08-16
 */
@Service("blacklistManager")
public class BlacklistManager extends BaseManager<BlacklistDao, Blacklist, String> {
    public long findBlacklistCount(String userId) {
        return dao.findBlacklistCount(userId);
    }


    public long countInBlacklist(String userId) {
        return dao.countInBlacklist(userId);
    }

    public List<BlacklistVO> selectBlackList(Page<BlacklistVO> page, BlacklistOP op) {
        return dao.selectBlackList(page, op);
    }

    public long updateBlackById(String id, int status,String updateBy) {
        return dao.updateBlackById(id, status,updateBy);
    }
    
    public int inBlackList(String userName, String userPhone, String userIdCard) {
        return dao.inBlackList(userName, userPhone, userIdCard);
    }

    public List<Blacklist> getALLBlackCust() {
        return dao.getALLBlackCust();
    }

    public List<Blacklist> getBlacklistByPhoneList(List<String> phoneList) {
        return dao.getBlacklistByPhoneList(phoneList);
    }
}