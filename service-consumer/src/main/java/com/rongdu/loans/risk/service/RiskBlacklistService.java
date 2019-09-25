/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.risk.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.vo.BlacklistVO;

/**
 * 风控黑名单-业务逻辑接口
 *
 * @author sunda
 * @version 2017-08-14
 */
public interface RiskBlacklistService {
    public long countInBlacklist(String userId);

    public int insertBlacklist(String userId, String remark, String applyId,Integer status,String updateUser);

    public Page<BlacklistVO> selectBlackList(BlacklistOP op);

    public long updateBlackById(String id, int status,String updateBy);

    public BlacklistVO getById(String id);

    boolean inBlackList(String userName, String userPhone, String userIdCard);
    
    public int deleteBlickList(String blackId);
}