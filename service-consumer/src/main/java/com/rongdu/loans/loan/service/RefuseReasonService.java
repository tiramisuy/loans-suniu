/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

import com.rongdu.loans.loan.vo.RefuseReasonVO;

/**
 * 贷款审核拒绝原因-业务逻辑接口
 * @author zhangxiaolong
 * @version 2017-07-07
 */
public interface RefuseReasonService {

    List<RefuseReasonVO> findAll();

}