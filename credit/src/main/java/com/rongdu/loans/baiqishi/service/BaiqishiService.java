/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service;

import com.rongdu.loans.baiqishi.vo.*;

import java.util.Map;

/**
 * 白骑士-业务逻辑接口
 * @author sunda
 * @version 2017-08-14
 */
public interface BaiqishiService {

    /**
     * 白骑士反欺诈云风险决策
     */
    public DecisionVO doDecision(DecisionOP op, Map<String, String> extParams);

    /**
     * 白骑士-资信云报告数据
     */
    public ReportDataVO getReportData(ReportDataOP op);

    /**
     * 白骑士-给资信云上报额外参数
     */
    public ReportExtVO reportExt(ReportExtOP op);

    /**
     * 查询设备信息
     * @return
     */
    public DeviceInfoVO getDeviceInfo(DeviceInfoOP op);

    /**
     * 获取手机通讯录信息
     * @return
     */
    public DeviceContactVO getDeviceContact(DeviceContactOP op);

    /**
     * 获取运营商通讯信息
     * @return
     */
    public MnoContactVO getMnoContact(MnoContactOP op);


    /**
     * 白骑士-上传查询催收指标用户
     */
    public CuishouDataVO uploadCuishou(CuishouDataOP op);
}