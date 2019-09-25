/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service;

/**
 * 工单映射-业务逻辑接口
 * @author Lee
 * @version 2018-05-29
 */
public interface ApplyTripartiteService {

    int insertTripartiteOrder(String applyId, String orderSn);

    boolean isExistApplyId(String applyId);

    String getApplyIdByThirdId(String order_sn);

    int insertTripartiteOrderNotice(String applyId, String orderSn, String notice);

    String getThirdIdByApplyId(String applyId);
}