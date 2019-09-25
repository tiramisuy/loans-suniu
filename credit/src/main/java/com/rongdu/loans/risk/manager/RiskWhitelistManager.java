/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.manager;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.risk.dao.RiskWhitelistDao;
import com.rongdu.loans.risk.entity.RiskWhitelist;
import com.rongdu.loans.risk.option.WhitelistOP;
import com.rongdu.loans.risk.vo.WhitelistVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控白名单-实体管理实现类
 * @author yuanxianchu
 * @version 2018-12-27
 */
@Service("riskWhitelistManager")
public class RiskWhitelistManager extends BaseManager<RiskWhitelistDao, RiskWhitelist, String> {

    public long countByUserId(String userId) {
        return dao.countByUserId(userId);
    }

    public List<WhitelistVO> selectWhiteList(Page<WhitelistVO> page, WhitelistOP op) {
        return dao.selectWhiteList(page, op);
    }
}