package com.rongdu.loans.risk.service;

import com.rongdu.common.persistence.Page;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.option.WhitelistOP;
import com.rongdu.loans.risk.vo.BlacklistVO;
import com.rongdu.loans.risk.vo.WhitelistVO;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/1/24
 * @since 1.0.0
 */
public interface RiskWhitelistService {

    long countByUserId(String userId);

    Page<WhitelistVO> selectWhiteList(WhitelistOP op);

    int deleteWhiteList(String whiteId);
}
