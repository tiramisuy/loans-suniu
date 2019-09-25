/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.credit100.service;

import com.rongdu.loans.credit100.vo.*;

import java.util.Map;

/**
 * 百融金服-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface Credit100Service {

    /**
     * 特殊名单核查
     */
    public Map<String,String> specialListc(SpecialListCOP op);

    /**
     * 当日多次申请
     */
    public ApplyLoandVO applyLoand(ApplyLoandOP op);

    /**
     * 多次申请核查月度版
     */
    public Map<String,String> applyLoanMon(ApplyLoanMonOP op);

}