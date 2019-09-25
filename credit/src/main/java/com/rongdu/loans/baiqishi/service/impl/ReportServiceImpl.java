/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.baiqishi.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.anrong.op.IterationShareOP;
import com.rongdu.loans.anrong.service.AnRongService;
import com.rongdu.loans.anrong.vo.IterationShareVO;
import com.rongdu.loans.anrong.vo.MSPReprtVO;
import com.rongdu.loans.anrong.vo.ShareVO;
import com.rongdu.loans.baiqishi.common.BaiqishiConfig;
import com.rongdu.loans.baiqishi.entity.ReportCrossValidationDetail;
import com.rongdu.loans.baiqishi.entity.ReportMnoCcm;
import com.rongdu.loans.baiqishi.entity.ReportMnoPui;
import com.rongdu.loans.baiqishi.entity.ReportWebDataSource;
import com.rongdu.loans.baiqishi.service.BaiqishiService;
import com.rongdu.loans.baiqishi.vo.*;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.credit.baiqishi.vo.CuishouOP;
import com.rongdu.loans.credit.baiqishi.vo.CuishouVO;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageOP;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageVO;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.common.RedisPrefix;
import com.rongdu.loans.credit.http.HttpUtils;
import com.rongdu.loans.credit.moxie.vo.CreditcardReportVO;
import com.rongdu.loans.credit.moxie.vo.bank.BankReportVO;
import com.rongdu.loans.credit.moxie.vo.email.EmailReportVO;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.loan.option.dwd.DWDAdditionInfo;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.BehaviorCheck;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.ItemTemVo;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.rong360Model.OrderAppendInfo;
import com.rongdu.loans.loan.option.rong360Model.PhoneList;
import com.rongdu.loans.loan.option.rongTJreportv1.CallLog;
import com.rongdu.loans.loan.option.rongTJreportv1.RongContactCheck;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.loan.option.xjbk.*;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.mq.anrong.AnRongMessageService;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.AutoApproveUtils;
import com.rongdu.loans.risk.service.CreditDataInvokeService;
import com.rongdu.loans.zhicheng.entity.EchoDecisionReport;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreen;
import com.rongdu.loans.zhicheng.entity.EchoFraudScreenRiskResult;
import com.rongdu.loans.zhicheng.entity.EchoLoanRecord;
import com.rongdu.loans.zhicheng.service.EchoDecisionReportService;
import com.rongdu.loans.zhicheng.service.EchoFraudScreenRiskResultService;
import com.rongdu.loans.zhicheng.service.EchoFraudScreenService;
import com.rongdu.loans.zhicheng.service.EchoLoanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 白骑士-资信报告-业务逻辑实现类
 *
 * @author sunda
 * @version 2017-08-14
 */
@Service("reportService")
public class ReportServiceImpl extends PartnerApiService implements ReportService {
    public int DAY_1 = 24 * 60 * 60;
    @Autowired
    private CreditDataInvokeService creditDataInvokeService;
    @Autowired
    private BaiqishiService baiqishiService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private CustUserService userService;
    @Autowired
    private EchoLoanRecordService echoLoanRecordService;
    @Autowired
    private AnRongService anRongService;
    @Autowired
    private AnRongMessageService anRongMessageService;
    @Autowired
    private EchoDecisionReportService echoDecisionReportService;
    @Autowired
    private EchoFraudScreenService echoFraudScreenService;
    @Autowired
    private EchoFraudScreenRiskResultService echoFraudScreenRiskResultService;

    /**
     * 查询登录/访问令牌
     *
     * @return
     */
    public TokenVO getToken(String idNo) {
        // 配置参数
        String partnerId = BaiqishiConfig.partner_id;
        String partnerName = BaiqishiConfig.partner_name;
        String bizCode = BaiqishiConfig.gettoken_biz_code;
        String bizName = BaiqishiConfig.gettoken_biz_name;
        String url = BaiqishiConfig.gettoken_url;
        LogParam log = new LogParam();
        log.setPartnerId(partnerId);
        log.setPartnerName(partnerName);
        log.setBizCode(bizCode);
        log.setBizName(bizName);

        Map<String, String> params = new HashMap<String, String>();
        params.put("partnerId", BaiqishiConfig.partnerId);
        params.put("verifyKey", BaiqishiConfig.verifyKey);
        params.put("certNo", idNo);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        params.put("timeStamp", timeStamp);
        // 请求字符串
        String jsonParamsBody = JsonMapper.toJsonString(params);
        // 发送请求
        TokenVO vo = (TokenVO) postForJson(url, jsonParamsBody, TokenVO.class, log);
        if (vo == null) {
            vo = new TokenVO();
        }
        vo.setTimeStamp(timeStamp);
        return vo;
    }

    /**
     * 查询资信云报告页面URL
     *
     * @return
     */
    @Override
    public ReportPageVO getReportPage(ReportPageOP op) {
        // 配置参数
        String partnerId = BaiqishiConfig.partner_id;
        String partnerName = BaiqishiConfig.partner_name;
        String bizCode = BaiqishiConfig.getreportpage_biz_code;
        String bizName = BaiqishiConfig.getreportpage_biz_name;
        String url = BaiqishiConfig.getreportpage_url;
        String reportPageCachePrefix = "ReportPage_";
        String reportPageCacheId = reportPageCachePrefix + op.getIdNo();
        String reportPageUrl = JedisUtils.get(reportPageCacheId);
        ReportPageVO vo = new ReportPageVO();
        if (StringUtils.isBlank(reportPageUrl)) {
            TokenVO token = getToken(op.getIdNo());
            if (vo != null) {
                if (token.isSuccess()) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("partnerId", BaiqishiConfig.partnerId);
                    params.put("certNo", op.getIdNo());
                    params.put("name", op.getName());
                    params.put("mobile", op.getMobile());
                    params.put("timeStamp", token.getTimeStamp());
                    params.put("token", token.getData());

                    reportPageUrl = HttpUtils.makeQueryString(url, params);
                    vo.setReportPageUrl(reportPageUrl);
                    // 白骑士Token缓存24小时，我们缓存20小时
                    JedisUtils.set(reportPageCacheId, reportPageUrl, 20 * 3600);
                }
                vo.setSuccess(token.isSuccess());
                vo.setCode(token.getCode());
                vo.setMsg(token.getMsg());
            }
        } else {
            vo.setSuccess(true);
            vo.setCode(ErrInfo.SUCCESS.getCode());
            vo.setCode(ErrInfo.SUCCESS.getMsg());
            vo.setReportPageUrl(reportPageUrl);
        }
        logger.debug("{}-{}-请求地址：{}", partnerName, bizName, reportPageUrl);
        return vo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getContactConnectInfo(String applyId) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return list;
        }
        // 大王贷
        if (ChannelEnum.DAWANGDAI.getCode().equals(apply.getChannelId()) && "4".equals(apply.getSource())) {
            AutoApproveContext context = new AutoApproveContext(applyId);
            context.setUserId(apply.getUserId());
            DWDReport dwdReport = creditDataInvokeService.getdwdReport(context);
            List<ContactCheck> contactChecks = dwdReport.getContactCheckList();
            for (ContactCheck c : contactChecks) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("name", c.getName());
                m.put("mobile", c.getMobile());
                m.put("connectCount", c.getCallCnt());
                m.put("connectTime", c.getCallLen());
                list.add(m);
            }
            return list;
        }
        // 融360
        else if (("RONG".equals(apply.getChannelId()) || "RONGJHH".equals(apply.getChannelId()))
                && "4".equals(apply.getSource())) {
            Map<String, Object> data = (Map<String, Object>) getRongConnectInfo(applyId, apply.getUserId());
            List<RongContactCheck> data1 = (List<RongContactCheck>) data.get("data1");
            List<RongContactCheck> data2 = (List<RongContactCheck>) data.get("data2");
            List<RongContactCheck> allData = new ArrayList<RongContactCheck>();
            allData.addAll(data1);
            allData.addAll(data2);
            for (RongContactCheck c : allData) {
                if (c.getPhone().length() < 8) {
                    continue;
                }
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("name", c.getName());
                m.put("mobile", c.getPhone());
                m.put("connectCount", c.getTalkCnt());
                m.put("connectTime", c.getTalkSeconds());
                list.add(m);
            }
            return list;
        }
        // 借点钱
        else if ("JDQAPI".equals(apply.getChannelId()) && "4".equals(apply.getSource())) {
            AutoApproveContext context = new AutoApproveContext(applyId);
            context.setUserId(apply.getUserId());
            JDQReport jdqReport = creditDataInvokeService.getjdqReport(context);
            List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
            for (ContactCheck c : contactChecks) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("name", c.getName());
                m.put("mobile", c.getMobile());
                m.put("connectCount", c.getCallCnt());
                m.put("connectTime", c.getCallLen());
                list.add(m);
            }
            return list;
        }

        // 通讯录
        DeviceContactVO deviceContactVO = creditDataInvokeService.getBaiqishiContactInfoFromFile(apply.getUserId(),
                applyId);
        if (deviceContactVO == null || deviceContactVO.getContactsInfo() == null
                || deviceContactVO.getContactsInfo().isEmpty()) {
            // 现金白卡
            deviceContactVO = getBqsContactByXjbkContact(applyId, apply.getUserId());
        }
        if (deviceContactVO == null || deviceContactVO.getContactsInfo() == null
                || deviceContactVO.getContactsInfo().isEmpty()) {
            return list;
        }
        // 资信云报告
        String cacheKey = RedisPrefix.BAIQISHI_REPORT + applyId;
        ReportDataVO reportDataVO = (ReportDataVO) JedisUtils.getObject(cacheKey);
        if (reportDataVO == null) {
            reportDataVO = creditDataInvokeService.getBaishiqiReportDataFromFile(applyId);
        }
        // 通讯记录
        List<ReportMnoCcm> ccmList = new ArrayList<ReportMnoCcm>();
        if (reportDataVO != null && reportDataVO.isSuccess() && reportDataVO.getData() != null
                && reportDataVO.getData().getMnoCommonlyConnectMobiles() != null) {
            ccmList = reportDataVO.getData().getMnoCommonlyConnectMobiles();
        }
        int matchCount = 0;
        List<String> mobileList = new ArrayList<String>();// 防止重复手机号
        for (Contact contact : deviceContactVO.getContactsInfo()) {
            String contactName = contact.getName();
            String contactMobile = AutoApproveUtils.parseContactMobile(contact.getMobile());
            Integer connectCount = 0;
            Integer connectTime = 0;
            boolean isMatch = false;
            if (mobileList.contains(contactMobile)) {
                continue;
            }
            mobileList.add(contactMobile);
            for (ReportMnoCcm ccm : ccmList) {
                String ccmMobile = ccm.getMobile();
                if (ccmMobile.length() < 8 || contactMobile.length() < 8) {
                    if (StringUtils.equals(ccmMobile, contactMobile)) {
                        isMatch = true;
                    }
                } else {
                    if (StringUtils.contains(ccmMobile, contactMobile)
                            || StringUtils.contains(contactMobile, ccmMobile)) {
                        isMatch = true;
                    }
                }
                if (isMatch) {
                    matchCount++;
                    connectCount = ccm.getConnectCount();
                    connectTime = ccm.getConnectTime();
                    break;
                }
            }
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("name", contactName);
            m.put("mobile", contactMobile);
            m.put("connectCount", connectCount);
            m.put("connectTime", connectTime);
            list.add(m);
        }
        if (matchCount > 0)
            return AutoApproveUtils.getTopContactList(list, matchCount > 200 ? matchCount : 200);
        return AutoApproveUtils.getTopContactList(list, 1000);
    }

    /**
     * 上传查询催收指标用户
     */
    public CuishouVO uploadCuishou(CuishouOP cuishouOP) {
        CuishouVO vo = new CuishouVO();
        vo.setSuccess(true);
        vo.setCode(ErrInfo.SUCCESS.getCode());
        vo.setMsg(ErrInfo.SUCCESS.getMsg());

        String uploadCuishouLockCacheKey = "uploadCuishouLockCacheKey_" + cuishouOP.getApplyId();
        synchronized (ReportServiceImpl.class) {
            if (JedisUtils.get(uploadCuishouLockCacheKey) == null) {
                // 加锁，防止并发
                JedisUtils.set(uploadCuishouLockCacheKey, "locked", 5 * 60);
            } else {
                vo.setSuccess(false);
                vo.setCode(ErrInfo.ERROR.getCode());
                vo.setMsg(ErrInfo.ERROR.WAITING.getMsg());
                return vo;
            }
        }
        try {
            AutoApproveContext context = creditDataInvokeService.createAutoApproveContext(cuishouOP.getApplyId());
            ReportDataVO reportDataVO = creditDataInvokeService.getBaishiqiReportData(context);
            if (reportDataVO == null || reportDataVO.getData() == null || !reportDataVO.isSuccess()) {
                vo.setSuccess(false);
                vo.setCode(ErrInfo.ERROR.getCode());
                vo.setMsg("资信云报告为空");
                return vo;
            }
            String uploadCuishouSuccessCacheKey = "uploadCuishouSuccessCacheKey_" + cuishouOP.getApplyId();
            MnoCuiShouInfo mnoCuiShouInfo = reportDataVO.getData().getMnoCuiShouInfo();
            if (mnoCuiShouInfo == null && JedisUtils.get(uploadCuishouSuccessCacheKey) == null) {
                CuishouDataOP op = new CuishouDataOP();
                op.setName(cuishouOP.getName());
                op.setCertNo(cuishouOP.getIdNo());
                op.setMobile(cuishouOP.getMobile());
                CuishouDataVO cuishouDataVO = baiqishiService.uploadCuishou(op);
                if (cuishouDataVO.isSuccess()) {
                    String baiqishiReportCacheKey = RedisPrefix.BAIQISHI_REPORT + cuishouOP.getApplyId();
                    JedisUtils.del(baiqishiReportCacheKey);

                    JedisUtils.set(uploadCuishouSuccessCacheKey, "locked", 3 * 60 * 60);
                } else {
                    vo.setSuccess(false);
                    vo.setCode(ErrInfo.ERROR.getCode());
                    vo.setMsg(cuishouDataVO.getResultDesc());
                }
            } else {
                logger.debug("催收指标已开通，请勿重复上传 : {}", JsonMapper.toJsonString(cuishouOP));
            }
        } finally {
            // 移除锁
            JedisUtils.del(uploadCuishouLockCacheKey);
        }
        return vo;
    }

    /**
     * 上报联系人
     */
    @Override
    public int reportContact(String userId) {
        int rz = 0;
        try {
            String cacheKey = RedisPrefix.USER_INFO + userId;
            JedisUtils.del(cacheKey);
            rz = creditDataInvokeService.reportContactToBaiqishi(userId);
        } catch (Exception e) {
            logger.debug("reportContact error：{},{}", userId, e.toString());
        }
        return rz;
    }

    /**
     * 获取资信云报告
     *
     * @param applyId
     * @param type
     * @return
     */
    public String getReportData(String applyId, int type) {
        Map<String, Object> result = new HashMap<String, Object>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return JsonMapper.toJsonString(result);
        }
        // 资信云报告
        String cacheKey = RedisPrefix.BAIQISHI_REPORT + applyId;
        ReportDataVO vo = (ReportDataVO) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            vo = creditDataInvokeService.getBaishiqiReportDataFromFile(applyId);
        }
        if (vo == null || !vo.isSuccess() || vo.getData() == null) {
            return JsonMapper.toJsonString(result);
        }
        if (type == 1) {
            List<ReportMnoCcm> ccmListSource1 = vo.getData().getMnoCommonlyConnectMobiles();
            List<ReportMnoCcm> ccmListSource2 = null;
            if (ccmListSource1 != null) {
                for (ReportMnoCcm c : ccmListSource1) {
                    c.setBeginTime(DateUtils.formatDate(new Date(Long.valueOf(c.getBeginTime()))));
                    c.setEndTime(DateUtils.formatDate(new Date(Long.valueOf(c.getEndTime()))));
                }
                ccmListSource2 = BeanMapper.mapList(ccmListSource1, ReportMnoCcm.class);
            }
            List<ReportMnoCcm> ccmList1 = AutoApproveUtils.getTopCcmTerminatingCallList(ccmListSource1, 10);
            List<ReportMnoCcm> ccmList2 = AutoApproveUtils.getTopCcmOriginatingCallList(ccmListSource2, 10);
            DeviceContactVO deviceContactVO = creditDataInvokeService.getBaiqishiContactInfoFromFile(apply.getUserId(),
                    applyId);
            if (deviceContactVO == null || deviceContactVO.getContactsInfo() == null
                    || deviceContactVO.getContactsInfo().isEmpty()) {
                deviceContactVO = getBqsContactByXjbkContact(applyId, apply.getUserId());
            }
            List ccmList3 = ccmContactMatch(ccmList1, deviceContactVO);
            List ccmList4 = ccmContactMatch(ccmList2, deviceContactVO);
            // 呼入
            result.put("ccmList1", ccmList3);
            // 呼出
            result.put("ccmList2", ccmList4);
            // 本人通话活动地区
            result.put("mnoCommonlyConnectAreas", vo.getData().getMnoCommonlyConnectAreas());
            // 联系人通话活动地区
            result.put("mnoContactsCommonlyConnectAreas", vo.getData().getMnoContactsCommonlyConnectAreas());

            List<ReportMnoPui> mnoPeriodUsedInfos = vo.getData().getMnoPeriodUsedInfosNew();
            List<Map<String, Object>> mnoPeriodUsedInfos2 = new ArrayList<Map<String, Object>>();
            if (mnoPeriodUsedInfos != null) {
                int totalCount1 = 0, totalCount2 = 0, totalCount = 0;
                String periodType1 = "06:00 ~ 23:29", periodType2 = "23:30 ~ 05:59";
                Map<String, Object> m1 = new HashMap<String, Object>();
                Map<String, Object> m2 = new HashMap<String, Object>();
                for (ReportMnoPui p : mnoPeriodUsedInfos) {
                    int tmpCount = p.getOriginatingCallCount() + p.getTerminatingCallCount();
                    if (p.getPeriodType().startsWith("23:30")) {
                        totalCount2 = totalCount2 + tmpCount;
                        periodType2 = p.getPeriodType();
                    } else {
                        totalCount1 = totalCount1 + tmpCount;
                    }
                    totalCount = totalCount + tmpCount;
                }
                BigDecimal percentage1 = totalCount > 0
                        ? new BigDecimal(totalCount1).divide(new BigDecimal(totalCount), 4, BigDecimal.ROUND_HALF_UP)
                        : BigDecimal.ZERO;
                BigDecimal percentage2 = totalCount > 0 ? new BigDecimal(1).subtract(percentage1) : BigDecimal.ZERO;
                m1.put("periodType", periodType1);
                m1.put("count", totalCount1);
                m1.put("percentage",
                        percentage1.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                mnoPeriodUsedInfos2.add(m1);

                m2.put("periodType", periodType2);
                m2.put("count", totalCount2);
                m2.put("percentage",
                        percentage2.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                mnoPeriodUsedInfos2.add(m2);
            }
            // 分时间段统计
            result.put("mnoPeriodUsedInfos", mnoPeriodUsedInfos2);
        } else {
            // 个人信息
            result.put("petitioner", vo.getData().getPetitioner());
            // 运营商
            result.put("mnoBaseInfo", vo.getData().getMnoBaseInfo());
            boolean passRealName = false;
            boolean equalToPetitioner = false;
            if (vo.getData().getWebDataSources() != null) {
                for (ReportWebDataSource item : vo.getData().getWebDataSources()) {
                    if ("运营商".equals(item.getSourceType())) {
                        if (item.getPassRealName()) {
                            passRealName = true;
                        }
                        if (item.getEqualToPetitioner()) {
                            equalToPetitioner = true;
                        }
                        break;
                    }
                }
            }
            // 是否实名认证
            result.put("passRealName", passRealName);
            // 是否匹配到本人
            result.put("equalToPetitioner", equalToPetitioner);
            if (vo.getData().getCrossValidation() != null) {
                // 入网时间
                ReportCrossValidationDetail openTime = vo.getData().getCrossValidation().getOpenTime();
                if (openTime != null) {
                    result.put("crossValidationOpenTime", openTime.getInspectionItems() + ":" + openTime.getResult()
                            + "(" + openTime.getEvidence() + ")");
                }
            }
            // 紧急联系人
            result.put("emergencyContacts", vo.getData().getEmergencyContacts());
            // 反欺诈云分析
            result.put("bqsAntiFraudCloud", vo.getData().getBqsAntiFraudCloud());
            // 用户行为检测
            result.put("crossValidation", vo.getData().getCrossValidation());
        }
        return JsonMapper.toJsonString(result);
    }

    /**
     * 联系人匹配结果
     *
     * @param applyId
     * @return
     */
    public List<CustContactVO> contactMatch(String userId, String applyId, List<CustContactVO> contactList) {
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return contactList;
        }
        // 资信云报告
        String cacheKey = RedisPrefix.BAIQISHI_REPORT + applyId;
        ReportDataVO reportVO = (ReportDataVO) JedisUtils.getObject(cacheKey);
        if (reportVO == null) {
            reportVO = creditDataInvokeService.getBaishiqiReportDataFromFile(applyId);
        }
        if (reportVO != null && reportVO.isSuccess() && reportVO.getData() != null
                && reportVO.getData().getMnoCommonlyConnectMobiles() != null) {
            List<ReportMnoCcm> ccmListSource1 = reportVO.getData().getMnoCommonlyConnectMobiles();
            List<ReportMnoCcm> ccmListSource2 = BeanMapper.mapList(ccmListSource1, ReportMnoCcm.class);
            List<ReportMnoCcm> ccmList1 = AutoApproveUtils.getTopCcmTerminatingCallList(ccmListSource1, 10);
            List<ReportMnoCcm> ccmList2 = AutoApproveUtils.getTopCcmOriginatingCallList(ccmListSource2, 10);
            for (CustContactVO c : contactList) {
                for (ReportMnoCcm ccm : ccmList1) {
                    if (c.getMobile().equals(ccm.getMobile())) {
                        c.setIsTerminatingCall(1);
                        ;
                        break;
                    }
                }
                for (ReportMnoCcm ccm : ccmList2) {
                    if (c.getMobile().equals(ccm.getMobile())) {
                        c.setIsOriginatingCall(1);
                        break;
                    }
                }
            }
        }
        // 通讯录
        DeviceContactVO deviceContactVO = creditDataInvokeService.getBaiqishiContactInfoFromFile(userId, applyId);
        if (deviceContactVO == null || deviceContactVO.getContactsInfo() == null
                || deviceContactVO.getContactsInfo().isEmpty()) {
            deviceContactVO = getBqsContactByXjbkContact(applyId, apply.getUserId());
        }
        if (deviceContactVO != null && deviceContactVO.getContactsInfo() != null
                && !deviceContactVO.getContactsInfo().isEmpty()) {
            for (CustContactVO c : contactList) {
                for (Contact dc : deviceContactVO.getContactsInfo()) {
                    String deviceMobile = AutoApproveUtils.parseContactMobile(dc.getMobile());
                    if (c.getMobile().equals(deviceMobile)) {
                        c.setIsDeviceContact(1);
                        break;
                    }
                }
            }
        }
        return contactList;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List ccmContactMatch(List<ReportMnoCcm> ccmList, DeviceContactVO deviceContactVO) {
        List ccmList2 = new ArrayList();
        for (ReportMnoCcm c : ccmList) {
            String ccmMobile = c.getMobile();
            String contactName = "";
            if (deviceContactVO != null && deviceContactVO.getContactsInfo() != null) {
                for (Contact c1 : deviceContactVO.getContactsInfo()) {
                    String contactMobile = AutoApproveUtils.parseContactMobile(c1.getMobile());
                    if (ccmMobile.length() < 8 || contactMobile.length() < 8) {
                        if (StringUtils.equals(ccmMobile, contactMobile)) {
                            contactName = c1.getName();
                            break;
                        }
                    } else {
                        if (StringUtils.contains(ccmMobile, contactMobile)
                                || StringUtils.contains(contactMobile, ccmMobile)) {
                            contactName = c1.getName();
                            break;
                        }
                    }
                }
            }
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("mobile", ccmMobile);
            m.put("belongTo", c.getBelongTo());
            m.put("beginTime", c.getBeginTime());
            m.put("endTime", c.getEndTime());
            m.put("terminatingCallCount", c.getTerminatingCallCount());
            m.put("terminatingTime", c.getTerminatingTime());
            m.put("originatingCallCount", c.getOriginatingCallCount());
            m.put("originatingTime", c.getOriginatingTime());
            m.put("contactName", contactName);
            ccmList2.add(m);
        }
        return ccmList2;
    }

    /**
     * 魔蝎信用卡邮箱报告
     *
     * @param userId
     * @return
     */
    public CreditcardReportVO getMoxieCreditcardReport(String userId) {
        String cacheKey = RedisPrefix.MOXIE_CREDITCARD_REPORT + userId;
        CreditcardReportVO vo = (CreditcardReportVO) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            vo = new CreditcardReportVO();
            FileInfoVO emailFileInfoVO = userService.getLastEmailReportByUserId(userId);
            FileInfoVO bankFileInfoVO = userService.getLastBankReportByUserId(userId);
            if (emailFileInfoVO == null && bankFileInfoVO == null)
                return vo;
            if (emailFileInfoVO != null && bankFileInfoVO == null) {
                // 信用卡邮箱报告
                List<EmailReportVO> list = creditDataInvokeService.getMoxieEmailReport(userId);
                vo.setType(1);
                vo.setReportData(list);
                JedisUtils.setObject(cacheKey, vo, DAY_1);
                return vo;
            }
            if (emailFileInfoVO == null && bankFileInfoVO != null) {
                // 网银报告
                BankReportVO data = creditDataInvokeService.getMoxieBankReport(userId);
                vo.setType(2);
                vo.setReportData(data);
                JedisUtils.setObject(cacheKey, vo, DAY_1);
                return vo;
            }
            if (emailFileInfoVO.getCreateTime().getTime() > bankFileInfoVO.getCreateTime().getTime()) {
                // 信用卡邮箱报告
                List<EmailReportVO> list = creditDataInvokeService.getMoxieEmailReport(userId);
                vo.setType(1);
                vo.setReportData(list);
                JedisUtils.setObject(cacheKey, vo, DAY_1);
                return vo;
            } else {
                // 网银报告
                BankReportVO data = creditDataInvokeService.getMoxieBankReport(userId);
                vo.setType(2);
                vo.setReportData(data);
                JedisUtils.setObject(cacheKey, vo, DAY_1);
                return vo;
            }
        }
        return vo;
    }

    @Override
    public String getjdqData(String applyId) {


        Map<String, Object> result = new HashMap<String, Object>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return JsonMapper.toJsonString(result);
        }
        AutoApproveContext autoApproveContext = new AutoApproveContext(applyId);
        autoApproveContext.setUserId(apply.getUserId());

        //报告数据
        JDQReport reportStr = creditDataInvokeService.getjdqReport(autoApproveContext);
        List<BehaviorCheck> behaviorCheckArrayList = Lists.newArrayList();
        BehaviorCheck behaviorCheck = new BehaviorCheck();

        behaviorCheck.setCheckPointCn("号码使用时长");
        behaviorCheck.setResult(String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheck.setEvidence("根据运营商提供的认证时间,推算该号码使用" + String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheckArrayList.add(behaviorCheck);

        BehaviorCheck behaviorCheck1 = new BehaviorCheck();
        behaviorCheck1.setCheckPointCn("呼出电话的号码数量");
        behaviorCheck1.setResult(reportStr.getZjsize() + "个");
        behaviorCheck1.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck1);

        BehaviorCheck behaviorCheck2 = new BehaviorCheck();
        behaviorCheck2.setCheckPointCn("呼入电话的号码数量");
        behaviorCheck2.setResult(reportStr.getBjsize() + "个");
        behaviorCheck2.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck2);

        BehaviorCheck behaviorCheck3 = new BehaviorCheck();
        behaviorCheck3.setCheckPointCn("夜间通话数量(23点-6点)");
        behaviorCheck3.setResult(reportStr.getCallatnight() + "个");
        behaviorCheck3.setEvidence("根据运营商提供的通话记录统计占比" + reportStr.getBl() + "%");
        behaviorCheckArrayList.add(behaviorCheck3);

        BehaviorCheck behaviorCheck4 = new BehaviorCheck();
        behaviorCheck4.setCheckPointCn("手机静默情况(连续24小时内无通话记录计为静默一天)");
        behaviorCheck4.setResult(
                reportStr.getDays() + "天内有" + reportStr.getDays1() + "天无通话记录，" + reportStr.getDays3() + "次连续3天以上无通话记录");
        behaviorCheck4.setEvidence("根据运营商详单数据，连续三天以上无通话记录" + reportStr.getDays3() + "次");
        behaviorCheckArrayList.add(behaviorCheck4);

        BehaviorCheck behaviorCheck5 = new BehaviorCheck();
        behaviorCheck5.setCheckPointCn("110话通话情况");
        behaviorCheck5.setResult(reportStr.getCall110() + "个");
        behaviorCheck5.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck5);

        BehaviorCheck behaviorCheck6 = new BehaviorCheck();
        behaviorCheck6.setCheckPointCn("互通电话的号码数量");
        behaviorCheck6.setResult(reportStr.getCountcall() + "个");
        behaviorCheck6.setEvidence("占总电话号码比率" + reportStr.getBigdecimal() + "%");
        behaviorCheckArrayList.add(behaviorCheck6);

        result.put("behaviorCheckArrayList", behaviorCheckArrayList);
        result.put("report", reportStr);

        //所有数据
        IntoOrder intoOrder = creditDataInvokeService.getjdqBase(autoApproveContext);
        Map<String, Object> ioMap = new HashMap<>();
        List<MoxieTelecomReport.CellPhoneBean> cellPhone = intoOrder.getMoxieTelecomReport().cell_phone;
        for (MoxieTelecomReport.CellPhoneBean bean : cellPhone) {
            //手机号归属地
            if ("phone_attribution".equals(bean.key)) {
                ioMap.put("phone_attribution", bean.value);
            }
            //余额
            if ("available_balance".equals(bean.key)) {
                ioMap.put("available_balance", bean.value);
                try {
                    ioMap.put("available_balance_yuan", Double.valueOf(bean.value) / 100d);
                } catch (NumberFormatException e) {
                    ioMap.put("available_balance_yuan", bean.value);
                }
            }

            //开户时长
            if ("in_time".equals(bean.key)) {
                ioMap.put("in_time", bean.value);
            }
        }
        //生肖
        ioMap.put("shengxiao", SHUtils.getShengXiao(reportStr.getBasic().getIdcard()));


        //用户信息检测
        List<MoxieTelecomReport.UserInfoCheckBean> infoChecks = intoOrder.getMoxieTelecomReport().user_info_check;
        MoxieTelecomReport.UserInfoCheckBean userInfoCheckBean1 = infoChecks.get(0);
        MoxieTelecomReport.UserInfoCheckBean.CheckSearchInfoBean searchInfoBean = userInfoCheckBean1.check_search_info;
        //用户查询信息
        List<BehaviorCheck> userInfoChecks = Lists.newArrayList();
        BehaviorCheck des1 = new BehaviorCheck();
        des1.setCheckPointCn("查询过该用户的相关企业数量");
        des1.setResult(searchInfoBean.searched_org_cnt + "个");
        userInfoChecks.add(des1);

        BehaviorCheck des2 = new BehaviorCheck();
        des2.setCheckPointCn("电话号码注册过的相关企业数量");
        des2.setResult(searchInfoBean.register_org_cnt + "个");
        userInfoChecks.add(des2);

        BehaviorCheck des3 = new BehaviorCheck();
        des3.setCheckPointCn("身份证组合过的其他姓名");
        if (searchInfoBean.idcard_with_other_names != null && searchInfoBean.idcard_with_other_names.size() > 0) {
            des3.setResult(org.apache.commons.lang3.StringUtils.join(searchInfoBean.idcard_with_other_names, ","));
        } else {
            des3.setResult("");
        }
        userInfoChecks.add(des3);


        BehaviorCheck des4 = new BehaviorCheck();
        des4.setCheckPointCn("身份证组合过其他电话");
        if (searchInfoBean.idcard_with_other_phones != null && searchInfoBean.idcard_with_other_phones.size() > 0) {
            des4.setResult(org.apache.commons.lang3.StringUtils.join(searchInfoBean.idcard_with_other_phones, ","));
        } else {
            des4.setResult("");
        }
        userInfoChecks.add(des4);

        BehaviorCheck des5 = new BehaviorCheck();
        des5.setCheckPointCn("电话号码组合过其他姓名");
        if (searchInfoBean.phone_with_other_names != null && searchInfoBean.phone_with_other_names.size() > 0) {
            des5.setResult(org.apache.commons.lang3.StringUtils.join(searchInfoBean.phone_with_other_names, ","));
        } else {
            des5.setResult("");
        }
        userInfoChecks.add(des5);

        BehaviorCheck des6 = new BehaviorCheck();
        des6.setCheckPointCn("电话号码组合过其他身份证");
        if (searchInfoBean.phone_with_other_idcards != null && searchInfoBean.phone_with_other_idcards.size() > 0) {
            des6.setResult(org.apache.commons.lang3.StringUtils.join(searchInfoBean.phone_with_other_idcards, ","));
        } else {
            des6.setResult("");
        }
        userInfoChecks.add(des6);
        //用户查询信息
        result.put("infoChecks", userInfoChecks);


        //用户关注名单
        MoxieTelecomReport.UserInfoCheckBean.CheckBlackInfoBean blackInfo = userInfoCheckBean1.check_black_info;
        List<BehaviorCheck> blackInfos = Lists.newArrayList();
        BehaviorCheck bi1 = new BehaviorCheck();
        bi1.setCheckPointCn("关注名单综合评分(分数范围0-100，40分以下为高危人群）");
        bi1.setResult(blackInfo.phone_gray_score + "");
        blackInfos.add(bi1);
        //用户关注名单
        result.put("blackInfos", blackInfos);


        //朋友圈
        List<MoxieTelecomReport.FriendCircleBean.SummaryBean> summary = intoOrder.getMoxieTelecomReport().friend_circle.summary;
        List<ItemTemVo> itemTemVos = Lists.newArrayList();


        ItemTemVo temVo1 = new ItemTemVo();
        temVo1.setItemName("朋友圈大小");
        temVo1.setRemark("近3/6月联系号码数");

        ItemTemVo temVo2 = new ItemTemVo();
        temVo2.setItemName("朋友圈亲密度");
        temVo2.setRemark("近3/6月联系十次以上的号码数量");

        ItemTemVo temVo3 = new ItemTemVo();
        temVo3.setItemName("朋友圈中心地");
        temVo3.setRemark("近3/6月联系次数最多的归属地");

        ItemTemVo temVo4 = new ItemTemVo();
        temVo4.setItemName("朋友圈是否在本地");
        temVo4.setRemark("近3/6月朋友圈中心地是否与手机归属地一致");

        ItemTemVo temVo5 = new ItemTemVo();
        temVo5.setItemName("互通电话的号码数目");
        temVo5.setRemark("近3/6月互有主叫和被叫的联系人电话号码数目（去重）");
        for (MoxieTelecomReport.FriendCircleBean.SummaryBean summaryBean : summary) {
            if ("friend_num_3m".equals(summaryBean.key)) {
                temVo1.setItem1(summaryBean.value);
            }
            if ("friend_num_6m".equals(summaryBean.key)) {
                temVo1.setItem2(summaryBean.value);
            }
            if ("good_friend_num_3m".equals(summaryBean.key)) {
                temVo2.setItem1(summaryBean.value);
            }
            if ("good_friend_num_6m".equals(summaryBean.key)) {
                temVo2.setItem2(summaryBean.value);
            }
            if ("friend_city_center_3m".equals(summaryBean.key)) {
                temVo3.setItem1(summaryBean.value);
            }
            if ("friend_city_center_6m".equals(summaryBean.key)) {
                temVo3.setItem2(summaryBean.value);
            }
            if ("is_city_match_friend_city_center_3m".equals(summaryBean.key)) {
                temVo4.setItem1(summaryBean.value);
            }
            if ("is_city_match_friend_city_center_6m".equals(summaryBean.key)) {
                temVo4.setItem2(summaryBean.value);
            }
            if ("inter_peer_num_3m".equals(summaryBean.key)) {
                temVo5.setItem1(summaryBean.value);
            }
            if ("inter_peer_num_6m".equals(summaryBean.key)) {
                temVo5.setItem2(summaryBean.value);
            }

        }
        itemTemVos.add(temVo1);
        itemTemVos.add(temVo2);
        itemTemVos.add(temVo3);
        itemTemVos.add(temVo4);
        itemTemVos.add(temVo5);
        result.put("friendCircleSummary", itemTemVos);

        // 魔蝎报告中通话风险分析
        List<MoxieTelecomReport.CallRiskAnalysisBean> callRiskAnalysis = intoOrder.getMoxieTelecomReport().call_risk_analysis;
        result.put("callRiskAnalysis", callRiskAnalysis);
        // 魔蝎报告中运营商出行分析
        List<MoxieTelecomReport.TripInfoBean> tripInfo = intoOrder.getMoxieTelecomReport().trip_info;
        result.put("tripInfo", tripInfo);
        // 魔蝎运营商报告通话服务分析
        List<MoxieTelecomReport.CallServiceAnalysisBean> callServiceAnalysis = intoOrder.getMoxieTelecomReport().getCall_service_analysis();
        result.put("callServiceAnalysis", callServiceAnalysis);
        // 魔蝎运营商账单
        List<MoxieTelecomReport.CellBehaviorBean.BehaviorBean> behaviorList = intoOrder.getMoxieTelecomReport().getCell_behavior().get(0).behavior;
        result.put("behaviorList", behaviorList);
        // 近三月与近六月通话时段分析
        List<MoxieTelecomReport.CallDurationDetailBean> callDurationDetail = intoOrder.getMoxieTelecomReport().getCall_duration_detail();
        for (MoxieTelecomReport.CallDurationDetailBean callDurationDetailBean : callDurationDetail) {
            if ("call_duration_detail_3m".equals(callDurationDetailBean.key)){
                result.put("callDurationDetail3m", callDurationDetailBean.duration_list);
            }
            if ("call_duration_detail_6m".equals(callDurationDetailBean.key)){
                result.put("callDurationDetail6m", callDurationDetailBean.duration_list);
            }
        }

        CustUserVO user = new CustUserVO();
        user.setIdNo(apply.getIdNo());
        user.setMobile(apply.getMobile());
        user.setRealName(apply.getUserName());
        autoApproveContext.setUser(user);
        // 根据applyId获取宜信发福命中数据
        List<EchoLoanRecord> list = echoLoanRecordService.getLoanRecordListByApplyId(applyId);
        result.put("zhiChengList", list);
        EchoDecisionReport echoDecisionReport = echoDecisionReportService.getDecisionReport(applyId);
        result.put("zhiChengDecision", echoDecisionReport);
        EchoFraudScreen echoFraudScreen = echoFraudScreenService.getFraudScreen(applyId);
        List<EchoFraudScreenRiskResult> echoFraudScreenRiskResults = echoFraudScreenRiskResultService.getFraudScreenRiskResult(applyId);
        result.put("zhiChengFraudScreen", echoFraudScreen);
        if (echoFraudScreen != null && StringUtils.isNotBlank(echoFraudScreen.getSocialNetwork())){
            result.put("zhiChengFraudScreenSocial", JsonMapper.fromJsonString(echoFraudScreen.getSocialNetwork(), Map.class));
        }
        result.put("zhiChengFraudScreenRiskResult", echoFraudScreenRiskResults);

        //所有数据
        IntoOrder intoOrderAdd = creditDataInvokeService.getjdqBaseAdd(autoApproveContext);
        //定位信息
        result.put("gpsAddress", intoOrderAdd.getGps() == null ? "" : intoOrderAdd.getGps().getAddress());
        //工作信息
        result.put("applyId", applyId);
        //工作信息
        result.put("livingAddress", intoOrderAdd.getUserInfo().getLivingAddress());
        result.put("companyName", intoOrderAdd.getUserInfo().getCompanyName());
        result.put("companyAddress", intoOrderAdd.getUserInfo().getCompanyAddress());

        //活跃程度
        List<MoxieTelecomReport.ActiveDegreeBean> activeDegrees = intoOrder.getMoxieTelecomReport().active_degree;
        result.put("activeDegrees", activeDegrees);

        result.put("ioMap", ioMap);
        return JsonMapper.toJsonString(result);
    }

    @Override
    public String getXianJinCardData(String applyId) {
        Map<String, Object> result = new HashMap<String, Object>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return JsonMapper.toJsonString(result);
        }
        AutoApproveContext autoApproveContext = new AutoApproveContext(applyId);
        autoApproveContext.setUserId(apply.getUserId());

        XianJinBaiKaCommonOP xianJinBaiKaBase = creditDataInvokeService.getXianJinBaiKaBase(autoApproveContext);
        XianJinBaiKaCommonOP xianJinBaiKaAdditional = creditDataInvokeService
                .getXianJinBaiKaAdditional(autoApproveContext);

        List<ContactList> contactList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify().getContactList();
        List<ContactList> contactList2 = null;
        if (contactList != null) {
            contactList2 = BeanMapper.mapList(contactList, ContactList.class);
        }
        List<ContactList> calInCntList = AutoApproveUtils.getTopCalInCntList(contactList, 10);
        List<ContactList> callOutCntList = AutoApproveUtils.getTopCallOutCntList(contactList2, 10);

        List<ContactRegion> contactRegionList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify()
                .getContactRegion();
        List<ContactRegion> contactRegionsV = AutoApproveUtils.getTopContactRegion(contactRegionList, 10);

        List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook().getPhoneList();
        List calInCntListV = xianJinCardContactMatch(calInCntList, phoneList);
        List callOutCntListV = xianJinCardContactMatch(callOutCntList, phoneList);

        ContactInfo contactInfo = xianJinBaiKaAdditional.getUser_additional().getContactInfo();
        List<UrgentContact> urgentContactArrayList = new ArrayList<>();
        UrgentContact urgentContact = new UrgentContact();
        urgentContact.setMobile(contactInfo.getMobile());
        urgentContact.setName(contactInfo.getName());
        urgentContact.setRelation(XianJinCardUtils.getRelation(contactInfo.getRelation()));
        for (ContactList contactList1 : contactList) {
            if (contactInfo.getMobile().equals(contactList1.getPhoneNum())) {
                urgentContact.setCallCnt(contactList1.getCallCnt());
                urgentContact.setCallLen(new Double(contactList1.getCallLen() * 60).intValue());
                urgentContact.setContact1m(contactList1.getContact1m());
                urgentContact.setContact1w(contactList1.getContact1w());
                urgentContact.setContact3m(contactList1.getContact3m());
                urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact);

        UrgentContact urgentContact1 = new UrgentContact();
        urgentContact1.setMobile(contactInfo.getMobileSpare());
        urgentContact1.setName(contactInfo.getNameSpare());
        urgentContact1.setRelation(XianJinCardUtils.getRelation(contactInfo.getRelationSpare()));
        for (ContactList contactList1 : contactList) {
            if (contactInfo.getMobileSpare().equals(contactList1.getPhoneNum())) {
                urgentContact1.setCallCnt(contactList1.getCallCnt());
                urgentContact1.setCallLen(new Double(contactList1.getCallLen() * 60).intValue());
                urgentContact1.setContact1m(contactList1.getContact1m());
                urgentContact1.setContact1w(contactList1.getContact1w());
                urgentContact1.setContact3m(contactList1.getContact3m());
                urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact1);
        // List<HitRuleVO> hitRuleList = riskService.getHitRuleList(applyId,
        // null);
        // List<HitRuleVO> baiRongApply = new ArrayList<>();
        // for (HitRuleVO vo : hitRuleList) {
        // if (vo.getRuleId().equals("10110007") ||
        // vo.getRuleId().equals("10110010")) {
        // baiRongApply.add(vo);
        // }
        // }
        String gpsLatitude = xianJinBaiKaAdditional.getUser_additional().getDeviceInfo().getGpsLatitude();
        String gpsLongitude = xianJinBaiKaAdditional.getUser_additional().getDeviceInfo().getGpsLongitude();
        String gpsAddress = xianJinBaiKaAdditional.getUser_additional().getDeviceInfo().getGpsAddress();
        String gpsAddressTime = apply.getApplyTime();

        GpsAddress gpsAddress1 = new GpsAddress();
        gpsAddress1.setGpsAddress(gpsAddress);
        gpsAddress1.setGpsAddressTime(gpsAddressTime);

        result.put("gpsAddress1", gpsAddress1);
        // result.put("baiRongApply", baiRongApply);
        result.put("calInCntListV", calInCntListV);
        result.put("callOutCntListV", callOutCntListV);
        result.put("contactRegionsV", contactRegionsV);
        result.put("urgentContact", urgentContactArrayList);
        result.put("all", xianJinBaiKaBase);
        return JsonMapper.toJsonString(result);
    }

    private List xianJinCardContactMatch(List<ContactList> contactLists, List<String> phoneList) {
        List ccmList2 = new ArrayList();
        for (ContactList contactList : contactLists) {
            String phoneNum = contactList.getPhoneNum();
            String contactName = "";
            if (phoneList != null) {
                for (String phone : phoneList) {
                    String contactMobile = phone.substring(phone.lastIndexOf("_") + 1);
                    if (StringUtils.equals(phoneNum, contactMobile)) {
                        contactName = phone.substring(0, (phone.lastIndexOf("_")));
                        break;
                    }
                }
            }
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("mobile", phoneNum);
            m.put("belongTo", contactList.getPhoneNumLoc());
            m.put("contact_1w", contactList.getContact1w());
            m.put("contact_1m", contactList.getContact1m());
            m.put("contact_3m", contactList.getContact3m());
            m.put("contact_3m_plus", contactList.getContact3mPlus());
            m.put("call_cnt", contactList.getCallCnt());
            m.put("call_len", new Double(contactList.getCallLen() * 60).intValue());
            m.put("terminatingCallCount", contactList.getCallInCnt());
            m.put("terminatingTime", new Double(contactList.getCallInLen() * 60).intValue());
            m.put("originatingCallCount", contactList.getCallOutCnt());
            m.put("originatingTime", new Double(contactList.getCallOutLen() * 60).intValue());
            m.put("contactName", contactName);
            ccmList2.add(m);
        }
        return ccmList2;
    }

    @Override
    public String getRong360Data(String applyId) {
        /*
         * Map<String, Object> result = new HashMap<String, Object>();
         * LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
         * if (apply == null || apply.getProcessStatus() <=
         * XjdLifeCycle.LC_APPLY_1) { return JsonMapper.toJsonString(result); }
         * AutoApproveContext autoApproveContext = new
         * AutoApproveContext(applyId);
         * autoApproveContext.setUserId(apply.getUserId());
         *
         * RongCommonOP rongBase =
         * creditDataInvokeService.getRongBase(autoApproveContext); RongCommonOP
         * rongAdditional =
         * creditDataInvokeService.getRongAdditional(autoApproveContext);
         *
         * //根据原始通话记录分析“主叫”，“被叫” List<Tel> tels =
         * rongBase.getOrderBaseInfo().getAddinfo().getMobile().getTel();
         * List<Teldata> callInTels = new ArrayList<>();//被叫呼入 List<Teldata>
         * callOutTels = new ArrayList<>();//主叫呼出 for (Tel tel : tels) { for
         * (Teldata teldata : tel.getTeldata()) { if
         * ("1".equals(teldata.getCallType())) { callOutTels.add(teldata); }else
         * if ("2".equals(teldata.getCallType())) { callInTels.add(teldata); } }
         * }
         *
         * //统计同一号码的通话次数（呼入） Map<String, Integer> callInTelMap = new
         * HashMap<>(); for (Teldata teldata : callInTels) { Integer count =
         * callInTelMap.get(teldata.getReceivePhone());
         * callInTelMap.put(teldata.getReceivePhone(), count == null ? 1:count +
         * 1); } Set<Teldata> callInTelSet = new HashSet<>(callInTels); for
         * (Teldata teldata : callInTelSet) {
         * teldata.setCallCount(callInTelMap.get(teldata.getReceivePhone())); }
         * //呼入前十 List<Teldata> topCallInTels = new ArrayList<>(callInTelSet);
         * topCallInTels = AutoApproveUtils.getRongTopCallCntList(topCallInTels,
         * 10); // 统计同一号码的通话次数（呼出） Map<String, Integer> callOutTelMap = new
         * HashMap<>(); for (Teldata teldata : callInTels) { Integer count =
         * callOutTelMap.get(teldata.getReceivePhone());
         * callOutTelMap.put(teldata.getReceivePhone(), count == null ? 1 :
         * count + 1); } Set<Teldata> callOutTelSet = new HashSet<>(callInTels);
         * for (Teldata teldata : callOutTelSet) {
         * teldata.setCallCount(callOutTelMap.get(teldata.getReceivePhone())); }
         * //呼出前十 List<Teldata> topCallOutTels = new ArrayList<>(callOutTelSet);
         * topCallOutTels =
         * AutoApproveUtils.getRongTopCallCntList(topCallOutTels, 10);
         *
         * // return null;
         */
        String result = null;
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            result = "订单状态不正确，无法获取运营商报告！";
            return result;
        }
        AutoApproveContext context = new AutoApproveContext(applyId);
        context.setUserId(apply.getUserId());
        TianjiReportDetailResp rongTJReportDetail = creditDataInvokeService.getRongTJReportDetail(context);
        if (rongTJReportDetail == null) {
            result = "异常，无法获取运营商报告！";
            return result;
        }
        result = rongTJReportDetail.getHtml();
        return result;
    }

    @Override
    public String getdwdData(String applyId) {
        Map<String, Object> result = new HashMap<String, Object>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return JsonMapper.toJsonString(result);
        }
        AutoApproveContext autoApproveContext = new AutoApproveContext(applyId);
        autoApproveContext.setUserId(apply.getUserId());
        DWDReport reportStr = creditDataInvokeService.getdwdReport(autoApproveContext);
        List<BehaviorCheck> behaviorCheckArrayList = Lists.newArrayList();
        BehaviorCheck behaviorCheck = new BehaviorCheck();

        behaviorCheck.setCheckPointCn("号码使用时长");
        behaviorCheck.setResult(String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheck.setEvidence("根据运营商提供的认证时间,推算该号码使用" + String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheckArrayList.add(behaviorCheck);

        BehaviorCheck behaviorCheck1 = new BehaviorCheck();
        behaviorCheck1.setCheckPointCn("呼出电话的号码数量");
        behaviorCheck1.setResult(reportStr.getZjsize() + "个");
        behaviorCheck1.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck1);

        BehaviorCheck behaviorCheck2 = new BehaviorCheck();
        behaviorCheck2.setCheckPointCn("呼入电话的号码数量");
        behaviorCheck2.setResult(reportStr.getBjsize() + "个");
        behaviorCheck2.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck2);

        BehaviorCheck behaviorCheck3 = new BehaviorCheck();
        behaviorCheck3.setCheckPointCn("夜间通话数量(23点-6点)");
        behaviorCheck3.setResult(reportStr.getCallatnight() + "个");
        behaviorCheck3.setEvidence("根据运营商提供的通话记录统计占比" + reportStr.getBl() + "%");
        behaviorCheckArrayList.add(behaviorCheck3);

        BehaviorCheck behaviorCheck4 = new BehaviorCheck();
        behaviorCheck4.setCheckPointCn("手机静默情况(连续24小时内无通话记录计为静默一天)");
        behaviorCheck4.setResult(
                reportStr.getDays() + "天内有" + reportStr.getDays1() + "天无通话记录，" + reportStr.getDays3() + "次连续3天以上无通话记录");
        behaviorCheck4.setEvidence("根据运营商详单数据，连续三天以上无通话记录" + reportStr.getDays3() + "次");
        behaviorCheckArrayList.add(behaviorCheck4);

        BehaviorCheck behaviorCheck5 = new BehaviorCheck();
        behaviorCheck5.setCheckPointCn("110话通话情况");
        behaviorCheck5.setResult(reportStr.getCall110() + "个");
        behaviorCheck5.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck5);

        BehaviorCheck behaviorCheck6 = new BehaviorCheck();
        behaviorCheck6.setCheckPointCn("互通电话的号码数量");
        behaviorCheck6.setResult(reportStr.getCountcall() + "个");
        behaviorCheck6.setEvidence("占总电话号码比率" + reportStr.getBigdecimal() + "%");
        behaviorCheckArrayList.add(behaviorCheck6);

        CustUserVO user = new CustUserVO();
        user.setIdNo(apply.getIdNo());
        user.setMobile(apply.getMobile());
        user.setRealName(apply.getUserName());
        autoApproveContext.setUser(user);
        // 根据applyId获取宜信发福命中数据
        List<EchoLoanRecord> list = echoLoanRecordService.getLoanRecordListByApplyId(applyId);
        result.put("zhiChengList", list);
        EchoDecisionReport echoDecisionReport = echoDecisionReportService.getDecisionReport(applyId);
        result.put("zhiChengDecision", echoDecisionReport);
        EchoFraudScreen echoFraudScreen = echoFraudScreenService.getFraudScreen(applyId);
        List<EchoFraudScreenRiskResult> echoFraudScreenRiskResults = echoFraudScreenRiskResultService.getFraudScreenRiskResult(applyId);
        result.put("zhiChengFraudScreen", echoFraudScreen);
        if (echoFraudScreen != null && StringUtils.isNotBlank(echoFraudScreen.getSocialNetwork())){
            result.put("zhiChengFraudScreenSocial", JsonMapper.fromJsonString(echoFraudScreen.getSocialNetwork(), Map.class));
        }
        result.put("zhiChengFraudScreenRiskResult", echoFraudScreenRiskResults);

        // 获取大王贷基本信息
        DWDAdditionInfo dwdAdditionInfo = creditDataInvokeService.getdwdAdditionInfo(autoApproveContext);
        result.put("livingAddress", dwdAdditionInfo.getAddrDetail());
        result.put("companyName", dwdAdditionInfo.getCompanyName());
        result.put("companyAddress", dwdAdditionInfo.getCompanyAddrDetail());
        // 获取聚信立数据展示
        com.rongdu.loans.loan.option.dwd.report.Report report = creditDataInvokeService.getdwdChargeInfo(autoApproveContext).getReportInfo().getData().getReport();
        if (report != null) {
            List<com.rongdu.loans.loan.option.dwd.report.ApplicationCheck> applicationCheck = report.getApplicationCheck();
            if (null != applicationCheck) {
                for (com.rongdu.loans.loan.option.dwd.report.ApplicationCheck check : applicationCheck) {
                    if ("cell_phone".equals(check.getAppPoint())) {
                        // 运营商地址
                        result.put("phoneLoc", check.getCheckPoints().website);
                        // 计算入网时长
                        if (StringUtils.isNotBlank(check.getCheckPoints().reg_time)) {
                            result.put("regLong", DateUtils.getMonth(DateUtils.parse(check.getCheckPoints().reg_time), new Date()));
                        }
                    }
                    if ("id_card".equals(check.getAppPoint())) {
                        // 生肖
                        result.put("shengxiao", SHUtils.getShengXiao(check.getCheckPoints().getKey_value()));
                    }
                }
            }
            // 用户信息检测
            com.rongdu.loans.loan.option.dwd.report.UserInfoCheck userInfoCheck = report.getUserInfoCheck();
            if (null != userInfoCheck) {
                // 运营商地址
                result.put("userInfoCheck", userInfoCheck);
            }
            // 用户行为信息检测
            List<com.rongdu.loans.loan.option.dwd.report.BehaviorCheck> behaviorCheckJXL = report.getBehaviorCheck();
            if (null != behaviorCheckJXL) {
                result.put("behaviorCheckJXL", behaviorCheckJXL);
            }
            // 运营商数据
            if (null != report.getCellBehavior() && null != report.getCellBehavior().get(0).getBehavior()) {
                result.put("cellBehavior", report.getCellBehavior().get(0).getBehavior());
            }
            // 出行数据
            if (null != report.getTripInfo()) {
                List<String> tripInfo = report.getTripInfo();
                List<Map> map = new ArrayList<>();
                for (String s : tripInfo) {
                    Map mapType = JSON.parseObject(s,Map.class);
                    map.add(mapType);
                }
                result.put("tripInfo", map);
            }
        }

        result.put("behaviorCheckArrayList", behaviorCheckArrayList);
        result.put("zhiChengList", list);
        result.put("report", reportStr);
        result.put("applyId", applyId);
        return JsonMapper.toJsonString(result);
    }

    @Override
    public String getsllData(String applyId) {
        Map<String, Object> result = new HashMap<String, Object>();
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return JsonMapper.toJsonString(result);
        }
        AutoApproveContext autoApproveContext = new AutoApproveContext(applyId);
        autoApproveContext.setUserId(apply.getUserId());
        JDQReport reportStr = creditDataInvokeService.getsllReport(autoApproveContext);
        List<BehaviorCheck> behaviorCheckArrayList = Lists.newArrayList();
        BehaviorCheck behaviorCheck = new BehaviorCheck();

        behaviorCheck.setCheckPointCn("号码使用时长");
        behaviorCheck.setResult(String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheck.setEvidence("根据运营商提供的认证时间,推算该号码使用" + String.valueOf(reportStr.getMonth()) + "个月");
        behaviorCheckArrayList.add(behaviorCheck);

        BehaviorCheck behaviorCheck1 = new BehaviorCheck();
        behaviorCheck1.setCheckPointCn("呼出电话的号码数量");
        behaviorCheck1.setResult(reportStr.getZjsize() + "个");
        behaviorCheck1.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck1);

        BehaviorCheck behaviorCheck2 = new BehaviorCheck();
        behaviorCheck2.setCheckPointCn("呼入电话的号码数量");
        behaviorCheck2.setResult(reportStr.getBjsize() + "个");
        behaviorCheck2.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck2);

        BehaviorCheck behaviorCheck3 = new BehaviorCheck();
        behaviorCheck3.setCheckPointCn("夜间通话数量(23点-6点)");
        behaviorCheck3.setResult(reportStr.getCallatnight() + "个");
        behaviorCheck3.setEvidence("根据运营商提供的通话记录统计占比" + reportStr.getBl() + "%");
        behaviorCheckArrayList.add(behaviorCheck3);

        BehaviorCheck behaviorCheck4 = new BehaviorCheck();
        behaviorCheck4.setCheckPointCn("手机静默情况(连续24小时内无通话记录计为静默一天)");
        behaviorCheck4.setResult(
                reportStr.getDays() + "天内有" + reportStr.getDays1() + "天无通话记录，" + reportStr.getDays3() + "次连续3天以上无通话记录");
        behaviorCheck4.setEvidence("根据运营商详单数据，连续三天以上无通话记录" + reportStr.getDays3() + "次");
        behaviorCheckArrayList.add(behaviorCheck4);

        BehaviorCheck behaviorCheck5 = new BehaviorCheck();
        behaviorCheck5.setCheckPointCn("110话通话情况");
        behaviorCheck5.setResult(reportStr.getCall110() + "个");
        behaviorCheck5.setEvidence("根据运营商提供的通话记录统计");
        behaviorCheckArrayList.add(behaviorCheck5);

        BehaviorCheck behaviorCheck6 = new BehaviorCheck();
        behaviorCheck6.setCheckPointCn("互通电话的号码数量");
        behaviorCheck6.setResult(reportStr.getCountcall() + "个");
        behaviorCheck6.setEvidence("占总电话号码比率" + reportStr.getBigdecimal() + "%");
        behaviorCheckArrayList.add(behaviorCheck6);

        result.put("behaviorCheckArrayList", behaviorCheckArrayList);
        result.put("report", reportStr);
        return JsonMapper.toJsonString(result);
    }

    @Override
    public Object getRongConnectInfo(String applyId, String userId) {
        String cacheKey = "rong_connectinfo_" + userId;
        Object dataResult = JedisUtils.getObject(cacheKey);
        if (null == dataResult) {
            AutoApproveContext context = new AutoApproveContext(applyId);
            context.setUserId(userId);
            OrderAppendInfo orderAppendInfo = creditDataInvokeService.getRongAdditional(context);
            if (orderAppendInfo == null) {
                logger.warn("从文件获取用户附加信息为空！！！applyId={},userId={}", context.getApplyId(), context.getUserId());
                return null;
            }
            TianjiReportDetailResp tianjiReportDetail = creditDataInvokeService.getRongTJReportDetail(context);
            if (tianjiReportDetail == null || tianjiReportDetail.getJson() == null) {
                logger.warn("从文件获取用户运营商报告信息为空！！！applyId={},userId={}", context.getApplyId(), context.getUserId());
                return null;
            }
            List<PhoneList> phoneLists = orderAppendInfo.getContacts().getPhoneList();
            if (phoneLists == null)
                phoneLists = Collections.emptyList();
            List<CallLog> callLogs = tianjiReportDetail.getJson().getCallLog();

            // 联系人通讯录统计（按通话时长排序）
            // List<RongContactCheck> rongContactChecks = new
            // ArrayList<RongContactCheck>();
            Set<RongContactCheck> rongContactCheckSet = new HashSet<>();
            for (PhoneList phoneList : phoneLists) {
                if (!StringUtils.isBlank(phoneList.getPhone())) {
                    RongContactCheck rongContactCheck = new RongContactCheck();
                    rongContactCheck.setPhone(phoneList.getPhone());
                    rongContactCheck.setName(phoneList.getName());
                    rongContactCheck.setTalkCnt(0);
                    rongContactCheck.setTalkSeconds(0);
                    for (CallLog callLog : callLogs) {
                        if (phoneList.getPhone().equals(callLog.getPhone())) {
                            boolean result = rongContactCheck.setTalkSeconds(callLog.getTalkSeconds());
                            if (result) {
                                rongContactCheck.setTalkCnt(callLog.getTalkCnt());
                            }
                        }
                    }
                    rongContactCheckSet.add(rongContactCheck);
                }
            }
            List<RongContactCheck> rongContactChecks = new ArrayList<>(rongContactCheckSet);
            Map<String, Object> data = new HashMap<>();
            List<RongContactCheck> data1 = null;
            List<RongContactCheck> data2 = null;
            if (rongContactChecks.size() == 1) {
                data1 = rongContactChecks;
            } else if (rongContactChecks.size() > 1) {
                Collections.sort(rongContactChecks);
                List<List<RongContactCheck>> result = new ArrayList<List<RongContactCheck>>();
                int remaider = rongContactChecks.size() % 2; // 余数1
                int number = rongContactChecks.size() / 2; // 商0
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<RongContactCheck> value = null;
                    if (remaider > 0) {
                        value = new ArrayList<>(
                                rongContactChecks.subList(i * number + offset, (i + 1) * number + offset + 1));
                        remaider--;
                        offset++;
                    } else {
                        value = new ArrayList<>(
                                rongContactChecks.subList(i * number + offset, (i + 1) * number + offset));
                    }
                    result.add(value);
                }
                int size = result.get(0).size();
                for (RongContactCheck rongContactCheck : result.get(1)) {
                    rongContactCheck.setIndex(size);
                    size++;
                }
                data1 = result.get(0);
                data2 = result.get(1);
            }
            data.put("data1", data1);
            data.put("data3", data2);
            data.put("data2", rongContactChecks);

            // 呼入呼出前十（按通话次数排序）
            List<CallLog> callLogs2 = null;
            if (callLogs != null) {
                callLogs2 = BeanMapper.mapList(callLogs, CallLog.class);
            }
            List<CallLog> callInCntList = AutoApproveUtils.getRongTopCallInCntList(callLogs, 10);
            List<CallLog> callOutCntList = AutoApproveUtils.getRongTopCallOutCntList(callLogs2, 10);
            callInCntList = rongContactMatch(callInCntList, phoneLists);
            callOutCntList = rongContactMatch(callOutCntList, phoneLists);
            data.put("callInCntList", callInCntList);
            data.put("callOutCntList", callOutCntList);
            data.put("phoneList", phoneLists);
            dataResult = data;
            JedisUtils.setObject(cacheKey, data, DAY_1);
        }
        return dataResult;
    }

    /**
     * @param @param callLogs
     * @param @param phoneLists
     * @Title: rongContactMatch
     * @Description: 运营商通话记录匹配通讯录
     */
    private List<CallLog> rongContactMatch(List<CallLog> callLogs, List<PhoneList> phoneLists) {
        for (CallLog callLog : callLogs) {
            String contactName = "--";
            if (phoneLists != null) {
                for (PhoneList phoneList : phoneLists) {
                    String phone = AutoApproveUtils.parseContactMobile(phoneList.getPhone());
                    if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(callLog.getPhone())
                            && StringUtils.contains(phone, callLog.getPhone())) {
                        contactName = phoneList.getName();
                        break;
                    }
                }
            }
            callLog.setContactName(contactName);
        }
        return callLogs;
    }

    private DeviceContactVO getBqsContactByXjbkContact(String applyId, String userId) {
        DeviceContactVO vo = new DeviceContactVO();
        List<Contact> contactsInfo = new ArrayList<Contact>();
        vo.setContactsInfo(contactsInfo);

        AutoApproveContext autoApproveContext = new AutoApproveContext(applyId);
        autoApproveContext.setUserId(userId);

        XianJinBaiKaCommonOP xianJinBaiKaAdditional = creditDataInvokeService
                .getXianJinBaiKaAdditional(autoApproveContext);
        if (xianJinBaiKaAdditional == null)
            return vo;
        List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook().getPhoneList();
        if (!phoneList.isEmpty()) {
            for (String phone : phoneList) {
                String contactMobile = phone.substring(phone.lastIndexOf("_") + 1);
                String contactName = phone.substring(0, (phone.lastIndexOf("_")));
                Contact c1 = new Contact();
                c1.setName(contactName);
                c1.setMobile(contactMobile);
                contactsInfo.add(c1);
            }
        }
        return vo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CustContactVO> rongContactMatch(String userId, String applyId, List<CustContactVO> contactList) {
        LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
        if (apply == null || apply.getProcessStatus() <= XjdLifeCycle.LC_APPLY_1) {
            return contactList;
        }
        Map<String, Object> connectInfo = (Map<String, Object>) this.getRongConnectInfo(applyId, userId);
        List<CallLog> callInCntList = (List<CallLog>) connectInfo.get("callInCntList");
        List<CallLog> callOutCntList = (List<CallLog>) connectInfo.get("callOutCntList");
        List<PhoneList> phoneLists = (List<PhoneList>) connectInfo.get("phoneList");
        for (CustContactVO custContactVO : contactList) {
            if (!StringUtils.isBlank(custContactVO.getMobile())) {
                for (CallLog callLog : callOutCntList) {
                    if (custContactVO.getMobile().equals(callLog.getPhone())) {
                        custContactVO.setIsOriginatingCall(1);
                    }
                }
                for (CallLog callLog : callInCntList) {
                    if (custContactVO.getMobile().equals(callLog.getPhone())) {
                        custContactVO.setIsTerminatingCall(1);
                    }
                }
            }
            if (!StringUtils.isBlank(custContactVO.getName())) {
                for (PhoneList phoneList : phoneLists) {
                    if (custContactVO.getName().equals(phoneList.getName())) {
                        custContactVO.setIsDeviceContact(1);
                    }
                }
            }
        }
        return contactList;
    }

    @Override
    public MSPReprtVO getAnRongData(String applyId) {
        LoanApplySimpleVO vo = loanApplyService.getLoanApplyById(applyId);
        // 获取用户地址
        UserInfoVO user = userService.getUserInfoByMobile(vo.getMobile());
        if (vo != null) {
            return anRongService.getMSPReport(vo, user.getRegCity());
        }
        return null;
    }

    @Override
    public TaskResult dealWithAnRong() {
        logger.info("开始执行安融数据共享任务。");
        long starTime = System.currentTimeMillis();
        // 查询待共享审批结果数据
        IterationShareOP op = new IterationShareOP();
        op.setType("1");
        IterationShareVO vo = anRongService.getIterationShareList(op);
        // 迭代查询
        getShareApprove(vo,op.getType(),QueueConfig.PUSH_ANRONG_SHARED_APPROVERESULT);

        // 查询待共享合同结清数据
        IterationShareOP op2 = new IterationShareOP();
        op2.setType("2");
        IterationShareVO vo2 = anRongService.getIterationShareList(op2);
        // 迭代查询
        getShareApprove(vo2,op2.getType(),QueueConfig.PUSH_ANRONG_SHARED_ORDERRESULT);

        // 查询待共享逾期状态数据
        IterationShareOP op3 = new IterationShareOP();
        op3.setType("3");
        IterationShareVO vo3 = anRongService.getIterationShareList(op3);
        // 迭代查询
        getShareApprove(vo3,op3.getType(),QueueConfig.PUSH_ANRONG_SHARED_ORDERRESULT);

        long endTime = System.currentTimeMillis();
        logger.info("迭代获取待共享项目接口,执行耗时{}", endTime - starTime);
        return new TaskResult();
    }

    private void getShareApprove(IterationShareVO vo,String type,QueueConfig config){
        if (vo != null && null != vo.getTodoList() && vo.getTodoList().size() > 0){
            // 放入队列处理
            List<ShareVO> todoList = vo.getTodoList();
            for (ShareVO shareVO : todoList) {
                anRongMessageService.sendShareMessage(shareVO,config,type);
            }
            IterationShareOP op = new IterationShareOP();
            op.setMaxId(vo.getMaxItemId());
            op.setType(type);
            IterationShareVO iterationShareVO = anRongService.getIterationShareList(op);
            getShareApprove(iterationShareVO,type,config);
        }
    }
}