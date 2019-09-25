/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

import java.util.List;

/**
 * 工单映射（安融）-业务逻辑接口
 * @author fy
 * @version 2019-06-21
 */
public interface ApplyTripartiteAnrongService {

    void save(String applyId);

    void update(String loanId, String checkResult);

    boolean isExistApplyId(String applyId,String status);

    List<String> findThirdIdsByApplyIds(List<String> applyIds);
}