package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.security.MD5Utils;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.common.utils.jdq.JdqUtil;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ProductorService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.LoginUtils;
import com.rongdu.loans.common.TripartitePromotionConfig;
import com.rongdu.loans.common.XJ360Util;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.cust.option.BaseInfoOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.ApplyTripartiteJdq;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.manager.ApplyTripartiteJdqManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.option.share.CustInfo;
import com.rongdu.loans.loan.option.jdq.*;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.FileUploadResult;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.CostingResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.SaveApplyResultVO;
import com.rongdu.loans.loan.vo.jdq.*;
import com.rongdu.loans.mq.share.SharedMessageService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lee on 2018/10/12.
 */
@Slf4j
@Service("jdqService")
public class JDQServiceImpl implements JDQService {
    Logger logger = LoggerFactory.getLogger(JDQServiceImpl.class);

    @Autowired
    private ProductorService productorService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private ApplyTripartiteJdqManager applyTripartiteJdqManager;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private CustUserManager custUserManager;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private OverdueService overdueService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private JDQStatusFeedBackService jdqStatusFeedBackService;
    @Autowired
    private PromotionCaseService promotionCaseService;
    @Autowired
    private PromotionCaseManager promotionCaseManager;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private FileInfoManager fileInfoManager;
    @Autowired
    private SharedMessageService sharedMessageService;

    private FileServerClient fileServerClient = new FileServerClient();

    @Override
    public CheckUserVO checkUser(CheckUserOP checkUserOP) {
        CheckUserVO checkUserVO = new CheckUserVO();
        String userName = checkUserOP.getUserName();
        String userPhone = checkUserOP.getPhone();
        String userIdCard = checkUserOP.getIdNumber();
        userIdCard = userIdCard.replace("*", "_");
        userPhone = userPhone.replace("*", "_");
        String key = MD5Utils.MD5(userPhone + userIdCard);
        CheckUserVO checkUserVOCache = getIsUserAcceptCache(key);
        if (checkUserVOCache != null) {
            return checkUserVOCache;
        }

        // String jdqfq_limit_day = configService.getValue("jdqfq_limit_day");
        String jdqfq_limit_day = "";
        int jdqfqLimitDay = StringUtils.isBlank(jdqfq_limit_day) ? 60 : Integer.parseInt(jdqfq_limit_day);
        CustUserVO custUserVO = custUserService.isRegister(userName, userPhone, userIdCard);
        if (custUserVO != null) {
            String userId = custUserVO.getId();
            if ("JDQAPI".equals(custUserVO.getChannel())) {
                checkUserVO.setUserType("0");// 0-借点钱老用户
            } else {
                checkUserVO.setUserType("2");// 2-合作方老用户
            }
            // 聚财自有黑名单
            boolean isBlackUser = riskBlacklistService.inBlackList(userName, userPhone, userIdCard);
            if (isBlackUser) {
                checkUserVO.setIfCanLoan("0");// 是否可借，0=否 1=是
                // checkUserVO.setCanLoanTime("");
                checkUserVO.setReason("1");// 1=黑名单
                setIsUserAcceptCache(checkUserVO, key);
                return checkUserVO;
            }

            // 未完成工单
            boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
            if (isExist) {
                checkUserVO.setIfCanLoan("0");// 是否可借，0=否 1=是
                checkUserVO.setCanLoanTime(DateUtils.formatDate(DateUtils.addDay(new Date(), jdqfqLimitDay)));
                checkUserVO.setReason("2");// 2=在途订单
                setIsUserAcceptCache(checkUserVO, key);
                return checkUserVO;
            }

            // 在本平台有15天以上逾期记录
            int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
            if (maxOverdueDays > 15) {
                checkUserVO.setIfCanLoan("0");// 是否可借，0=否 1=是
                checkUserVO.setCanLoanTime(DateUtils.formatDate(DateUtils.addDay(new Date(), 365)));
                checkUserVO.setReason("3");// 3=隔离期
                setIsUserAcceptCache(checkUserVO, key);
                return checkUserVO;
            }

            // 被拒日期距今不满60天
            LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
            if (lastApply != null
                    && (ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS.getValue().equals(lastApply.getStatus()) || ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS
                    .getValue().equals(lastApply.getStatus()))) {
                Date lastUpdateTime = lastApply.getUpdateTime();
                long pastDays = DateUtils.pastDays(lastUpdateTime);
                if (pastDays < jdqfqLimitDay) {
                    checkUserVO.setIfCanLoan("0");// 是否可借，0=否 1=是
                    checkUserVO
                            .setCanLoanTime(DateUtils.formatDate(DateUtils.addDay(lastUpdateTime, jdqfqLimitDay + 1)));
                    checkUserVO.setReason("3");// 3=隔离期
                    setIsUserAcceptCache(checkUserVO, key);
                    return checkUserVO;
                }
            }
            checkUserVO.setIfCanLoan("1");// 是否可借，0=否 1=是
            setIsUserAcceptCache(checkUserVO, key);
            return checkUserVO;
        }
        checkUserVO.setIfCanLoan("1");// 是否可借，0=否 1=是
        checkUserVO.setUserType("1");// 1-新用户
        setIsUserAcceptCache(checkUserVO, key);
        return checkUserVO;
    }

    @Override
    public JDQResp intoOrder(String partnerDecode, String type, String channelCode) {
        IntoOrder intoOrder = JSONObject.parseObject(partnerDecode, IntoOrder.class);
        JDQResp jdqResp = new JDQResp();
        try {
            String key = Global.JBD_ORDER_LOCK + intoOrder.getUserInfo().getPhone();
            boolean lock = JedisUtils.setLock(key, channelCode, Global.HALF_DAY_CACHESECONDS);
            if (!lock && !channelCode.equals(JedisUtils.get(key))) {
                jdqResp.setCode(JDQResp.ERROR);
                jdqResp.setMsg("该用户已从其他渠道进件！");
                log.debug("【借点钱】进件接口失败-已从其他渠道进件！jdqOrderId={}", intoOrder.getJdqOrderId());
                return jdqResp;
            }
            intoOrder.setChannelCode(channelCode);
            productorService.sendIntoOrder(intoOrder, type);

            if (QueueConfig.PUSH_JDQ.getType().equals(type)){
                // 共享服务-推送进件用户基础信息
                CustInfo custInfo = new CustInfo();
                custInfo.setCustName(intoOrder.getUserInfo().getUserName());
                custInfo.setPhone(intoOrder.getUserInfo().getPhone());
                custInfo.setIdNo(intoOrder.getUserInfo().getIdCard());
                sharedMessageService.pushCustInfo(custInfo);
            }
            Map<String,Object> data = new HashMap<>(16);
            data.put("bind_card_flag", 1);
            data.put("supplementary_flag", 1);
            jdqResp.setData(data);
            jdqResp.setMsg("");
            jdqResp.setCode(JDQResp.SUCCESS);
        } catch (Exception e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
            log.error("【借点钱】进件接口异常！jdqOrderId={}", intoOrder.getJdqOrderId(), e);
        }
        return jdqResp;
    }

    @Override
    public boolean saveIntoOrder(IntoOrder intoOrder, String type) {
        boolean flag = false;
        try {
            String userId = null;
            String bizCode = FileBizCode.JDQ_BASE_DATA.getBizCode();
            if (QueueConfig.PUSH_JDQ.getType().equals(type)) {
                String userPhone = intoOrder.getUserInfo().getPhone();
                userId = registerOrReturnUserId(userPhone,intoOrder.getChannelCode());
                saveDoOcr(intoOrder, userId);
            } else {
                userId = "1";
                bizCode = FileBizCode.JDQ_BASE_DATA_ADD.getBizCode();
            }

            FileInfoVO fileInfoVO = custUserService.getLastJDQBaseByOrderSn(intoOrder.getJdqOrderId(), bizCode);
            if (fileInfoVO == null) {
                String res = uploadBaseData(intoOrder, bizCode, userId);
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
/*                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                    Map<String, String> map = Maps.newHashMap();
                    map.put(intoOrder.getJdqOrderId(), String.valueOf(System.currentTimeMillis()));
                    JedisUtils.mapPut(Global.JDQ_THIRD_KEY, map);
                }*/
            } else {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("借点钱保存信息异常", e);
        }
        return flag;
    }

    @Override
    public TaskResult saveUserAndApplyInfo() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        Map<String, String> thirdKey = JedisUtils.getMap(Global.JDQ_THIRD_KEY);
        if (thirdKey != null) {
            List<Map.Entry<String, String>> thirdKeyList = new ArrayList<>(thirdKey.entrySet());
            Collections.sort(thirdKeyList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    return map1.getValue().compareTo(map2.getValue());
                }
            });
            int limit = thirdKeyList.size() >= 200 ? 200 : thirdKeyList.size();
            List<Map.Entry<String, String>> thirdListLimit = thirdKeyList.subList(0, limit);
            for (Map.Entry<String, String> map : thirdListLimit) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String orderSn = map.getKey();
                if (Long.parseLong(map.getValue()) <= (System.currentTimeMillis() - 1000 * 60 * 1)
                        && Long.parseLong(map.getValue()) >= (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    IntoOrder base = getPushBaseData(orderSn);
                    IntoOrder additional = getPushBaseTwoData(orderSn);
                    if (base != null && additional != null) {
                        try {
                            count++;
                            String channelCode = base.getChannelCode();
                            String userPhone = base.getUserInfo().getPhone();
                            String ip = "127.0.0.1";
                            String userId = registerOrReturnUserId(userPhone, channelCode);
                            if (!loanApplyService.isExistUnFinishLoanApply(userId)) {
                                // 更新基础信息的用户ID
                                fileInfoManager.updateUserIdByOrderSn(userId, orderSn, FileBizCode.JDQ_BASE_DATA_ADD.getBizCode());
//                                saveDoOcr(base, userId);
                                saveBaseInfo(base, additional, userId, channelCode);
                                saveRz(userId, ip);
                                saveLoanApply(base, userId);
                                savejdqData(base, additional);
                            }
                            JedisUtils.mapRemove(Global.JDQ_THIRD_KEY, orderSn);
                            succNum++;
                        } catch (Exception e) {
                            JedisUtils.mapRemove(Global.JDQ_THIRD_KEY, orderSn);
                            e.printStackTrace();
                            log.error("借点钱三方工单转化失败:{} 手工处理", orderSn);
                        }
                    } else {
                        Map<String, String> rePush = Maps.newHashMap();
                        rePush.put(orderSn, String.valueOf(System.currentTimeMillis()));
                        JedisUtils.mapPut(Global.JDQ_THIRD_KEY, rePush);
                    }
                } else if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    // JedisUtils.mapRemove(Global.XJBK_THIRD_KEY, orderSn);
                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }



    @Override
    public IntoOrder getPushBaseData(String orderSn) {
        String cacheKey = "JDQ:PUSH_BASE_" + orderSn;
        IntoOrder vo = (IntoOrder) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastJDQBaseByOrderSn(orderSn, FileBizCode.JDQ_BASE_DATA.getBizCode());
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户基础信息", fileInfoVO.getUrl());
                    vo = (IntoOrder) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), IntoOrder.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户基础信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public IntoOrder getPushBaseTwoData(String orderSn) {
        String cacheKey = "JDQ:PUSH_BASE_ADD_" + orderSn;
        IntoOrder vo = (IntoOrder) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastJDQBaseByOrderSn(orderSn, FileBizCode.JDQ_BASE_DATA_ADD.getBizCode());
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户补充信息", fileInfoVO.getUrl());
                    vo = (IntoOrder) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), IntoOrder.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户补充信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    // 原有获取报告
    public IntoOrder getPushBaseDataOLD(String orderSn) {
        String cacheKey = "JDQ:PUSH_BASE_" + orderSn;
        IntoOrder vo = (IntoOrder) JedisUtils.getObject(cacheKey);
        String cacheKey2 = "JDQ:PUSH_BASE_ADD_" + orderSn;
        IntoOrder vo2 = (IntoOrder) JedisUtils.getObject(cacheKey2);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastJDQBaseByOrderSn(orderSn, FileBizCode.JDQ_BASE_DATA.getBizCode());
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户基础信息", fileInfoVO.getUrl());
                    vo = (IntoOrder) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), IntoOrder.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户基础信息", false);
                    }
                }
            }
            if (vo2 == null) {
                FileInfoVO addFileInfoVO = custUserService.getLastJDQBaseByOrderSn(orderSn, FileBizCode.JDQ_BASE_DATA_ADD.getBizCode());
                if (addFileInfoVO != null && StringUtils.isNotBlank(addFileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户补充信息", addFileInfoVO.getUrl());
                    vo2 = (IntoOrder) RestTemplateUtils.getInstance().getForObject(addFileInfoVO.getUrl(), IntoOrder.class);
                    if (vo2 != null) {
                        JedisUtils.setObject(cacheKey2, vo2, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户补充信息", false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("异常", e);
        }
        return null;
        /*return mergeIntoOrder(vo, vo2);*/
    }

/*    private IntoOrder mergeIntoOrder(IntoOrder vo, IntoOrder vo2) {
        if(vo != null && vo2 != null) {
            UserInfo userInfo = vo.getUserInfo();
            UserInfo userInfo2 = vo2.getUserInfo();
            if(userInfo != null && userInfo2 != null) {
                if(StringUtils.isNotBlank(userInfo2.getMarry())){
                    userInfo.setMarry(userInfo2.getMarry());
                }
                if(StringUtils.isNotBlank(userInfo2.getEducate())){
                    userInfo.setEducate(userInfo2.getEducate());
                }
                if(StringUtils.isNotBlank(userInfo2.getLivingAddress())){
                    userInfo.setLivingAddress(userInfo2.getLivingAddress());
                }
                if(StringUtils.isNotBlank(userInfo2.getCompanyAddress())){
                    userInfo.setCompanyAddress(userInfo2.getCompanyAddress());
                }
                if(StringUtils.isNotBlank(userInfo2.getCompanyName())){
                    userInfo.setCompanyName(userInfo2.getCompanyName());
                }
                if(StringUtils.isNotBlank(userInfo2.getPhone())){
                    userInfo.setPhone(userInfo2.getPhone());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdCard())){
                    userInfo.setIdCard(userInfo2.getIdCard());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdCard())){
                    userInfo.setIdCard(userInfo2.getIdCard());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdCardAddress())){
                    userInfo.setIdCardAddress(userInfo2.getIdCardAddress());
                }
                if(StringUtils.isNotBlank(userInfo2.getNation())){
                    userInfo.setNation(userInfo2.getNation());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdPositive())){
                    userInfo.setIdPositive(userInfo2.getIdPositive());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdNegative())){
                    userInfo.setIdNegative(userInfo2.getIdNegative());
                }
                if(StringUtils.isNotBlank(userInfo2.getFace())){
                    userInfo.setFace(userInfo2.getFace());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdSigningAuthority())){
                    userInfo.setIdSigningAuthority(userInfo2.getIdSigningAuthority());
                }
                if(StringUtils.isNotBlank(userInfo2.getIdExpiryDate())){
                    userInfo.setIdExpiryDate(userInfo2.getIdExpiryDate());
                }
            }
            if(vo2.getUserContact() != null) {
                vo.setUserContact(vo2.getUserContact());
            }
            if(vo2.getAddressBook() != null) {
                vo.setAddressBook(vo2.getAddressBook());
            }
            if(vo2.getGps() != null) {
                vo.setGps(vo2.getGps());
            }
            if(vo2.getDeviceInfo() != null) {
                vo.setDeviceInfo(vo2.getDeviceInfo());
            }

            return vo;
        }

        return null;
    }*/

    public void saveRz(String userId, String ip) {
        OperationLog operationLog1 = new OperationLog();
        operationLog1.setUserId(userId);
        operationLog1.setStage(XjdLifeCycle.LC_FACE);
        operationLog1.setStatus(XjdLifeCycle.LC_FACE_1);
        operationLog1.setIp(ip);
        operationLog1.setSource(Global.DEFAULT_SOURCE);
        operationLog1.defOperatorIdAndName();
        operationLog1.preInsert();
        loanApplyManager.saveOperationLog(operationLog1);
    }

    private SaveApplyResultVO saveLoanApply(IntoOrder intoOrder, String userId) {
        String orderSn = intoOrder.getJdqOrderId();
        LoanInfo loanInfo = intoOrder.getLoanInfo();
        String productFlag = Global.JDQ_PRODUCT_FLAG;
        String status = null;
        if (ChannelEnum.JIEDIANQIAN2.getCode().equals(intoOrder.getChannelCode())) {
            productFlag = Global.JDQ_PRODUCT_FLAG2;
            status = ChannelEnum.JIEDIANQIAN2.getCode();
        }
        PromotionCaseOP promotionCaseOP =
                TripartitePromotionConfig.getPromotionCase(configService.getValue(productFlag));
        LoanApplyOP loanApplyOP = new LoanApplyOP();
        loanApplyOP.setProductId(promotionCaseOP.getProductId());
        loanApplyOP.setUserId(userId);
        loanApplyOP.setApplyAmt(promotionCaseOP.getApplyAmt());
        loanApplyOP.setApplyTerm(promotionCaseOP.getApplyTerm());
        loanApplyOP.setSource("4");// 进件来源（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
        loanApplyOP.setProductType(LoanProductEnum.get(loanApplyOP.getProductId()).getType());// 产品类型
        loanApplyOP.setTerm(promotionCaseOP.getTerm());// 还款期数
        loanApplyOP.setChannelId(intoOrder.getChannelCode());
        /*if (LoanProductEnum.JDQ.getId().equals(promotionCaseOP.getProductId())) {
            loanApplyOP.setProductType("0");// 产品类型
        } else {
            loanApplyOP.setProductType("4");
        }*/
        loanApplyOP.setPurpose("10");
        XJ360Util.cleanCustUserInfoCache(userId);
        SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
        if (!isExistApplyId(rz.getApplyId())) {
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("id", rz.getApplyId()));
            LoanApply loanApply = new LoanApply();
            loanApply.setChannelId(intoOrder.getChannelCode());
            loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
            int saveTripartiteOrderResult = insertTripartiteOrder(rz.getApplyId(), orderSn, status);
        }

        /*LoanApplyOP loanApplyOP = new LoanApplyOP();
        String productId = configService.getValue("jdq_product_id");
        loanApplyOP.setProductId(productId);
        loanApplyOP.setUserId(userId);
//            loanApplyOP.setApplyAmt(promotionCaseOP.getApplyAmt());
//            loanApplyOP.setApplyTerm(promotionCaseOP.getApplyTerm());
        loanApplyOP.setSource("4");
        if ("XJD".equals(productId)) {
            loanApplyOP.setProductType("0");
        } else if ("XJDFQ".equals(productId)) {
            loanApplyOP.setProductType("4");
        } else {
            loanApplyOP.setProductType("0");
        }

        loanApplyOP.setApplyAmt(new BigDecimal(loanInfo.getLoanAmount()));
        loanApplyOP.setApplyTerm(Integer.parseInt(loanInfo.getLoanTerm()));
        loanApplyOP.setChannelId(ChannelEnum.JIEDIANQIAN.getCode());

        loanApplyOP.setPurpose("10");
        cleanCustUserInfoCache(userId);
        SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
        if (!isExistApplyId(rz.getApplyId())) {
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("id", rz.getApplyId()));
            LoanApply loanApply = new LoanApply();
            loanApply.setChannelId(ChannelEnum.JIEDIANQIAN.getCode());
            loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
            int saveTripartiteOrderResult = insertTripartiteOrder(rz.getApplyId(), orderSn);
        }*/
        return rz;
    }

    @Override
    public boolean isExistApplyId(String applyId){
        return isExistApplyId(applyId, null);
    }

    @Override
    public boolean isExistApplyId(String applyId, String status) {
        if (applyId == null) {
            applyId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        if (StringUtils.isNotBlank(status)) {
            criteria.and(Criterion.eq("status", status));
        }
        long count = applyTripartiteJdqManager.countByCriteria(criteria);
        if (count > 0) {
            return true;
        }
        return false;
    }



    @Override
    public int insertTripartiteOrder(String applyId, String orderSn, String status) {
        ApplyTripartiteJdq applyTripartiteJdq = new ApplyTripartiteJdq();
        applyTripartiteJdq.setApplyId(applyId);
        applyTripartiteJdq.setTripartiteNo(orderSn);
        applyTripartiteJdq.setStatus(status);// status=2 对应渠道 JDQAPI2
        return applyTripartiteJdqManager.insert(applyTripartiteJdq);
    }

    @Override
    public String getOrderNo(String applyId) {
        if (applyId == null) {
            applyId = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("apply_id", applyId));
        ApplyTripartiteJdq applyTripartiteJdq = applyTripartiteJdqManager.getByCriteria(criteria);
        return applyTripartiteJdq.getTripartiteNo();
    }

    public static long cleanCustUserInfoCache(String userId) {
        long result = 0;
        if (StringUtils.isNotBlank(userId)) {
            // 缓存用户信息
            result = JedisUtils.delObject(Global.USER_CACHE_PREFIX + userId);
        } else {
            log.error("用户id为空");
        }
        return result;
    }

    private void saveBaseInfo(IntoOrder base, IntoOrder additional, String userId, String channelId) {
        UserInfo userInfo = base.getUserInfo();
        UserContact userContact = additional.getUserContact();
        String userPhone = userInfo.getPhone();
        String userName = userInfo.getUserName();
        String userIdcard = userInfo.getIdCard();
        String degree = JdqUtil.convertDegree(userInfo.getEducate());
        String marry = JdqUtil.convertMarryCode(userInfo.getMarry());
        String homeAddress = userInfo.getLivingAddress();

        String relation = userContact.getRelation();
        String mobile = userContact.getMobile();
        String name = userContact.getName();
        String relation1 = "1";// 父母配偶
        String inRelation = relation1 + "," + name + "," + mobile;

        String relationSpare = userContact.getRelationSpare();
        String nameSpare = userContact.getNameSpare();
        String mobileSpare = userContact.getMobileSpare();
        String relationSpare1 = "3";// 同事朋友
        String inRelationSpare = relationSpare1 + "," + nameSpare + "," + mobileSpare;

        BaseInfoOP baseInfoOP = new BaseInfoOP();
        String companyName = userInfo.getCompanyName();
        String address = userInfo.getCompanyAddress();

        baseInfoOP.setUserName(userName);
        baseInfoOP.setMobile(userPhone);
        baseInfoOP.setIdNo(userIdcard);
        baseInfoOP.setIdType("0");
        baseInfoOP.setUserId(userId);
        baseInfoOP.setComName(companyName);
        baseInfoOP.setWorkAddr(address);
        baseInfoOP.setMarital(Integer.valueOf(marry));
        baseInfoOP.setDegree(degree);
        baseInfoOP.setResideAddr(homeAddress);
        baseInfoOP.setContactParent(inRelation);
        baseInfoOP.setContactFriend(inRelationSpare);
        baseInfoOP.setChannelId(channelId);
        int saveRz = custUserService.saveBaseInfo(baseInfoOP);
    }

    private void saveDoOcr(IntoOrder base, String userId) throws Exception {
        UserInfo userInfo = base.getUserInfo();

        String ocrName = userInfo.getUserName();
        String ocrAddress = userInfo.getIdCardAddress();
        String ocrIdNumber = userInfo.getIdCard();
        String ocrIssuedBy = userInfo.getIdSigningAuthority();
        String ocrSex = "";
        String idCard = userInfo.getIdCard();
        if (userInfo.getIdCard().length() == IdcardUtils.CHINA_ID_MIN_LENGTH) {
            idCard = IdcardUtils.conver15CardTo18(idCard);
        }
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            ocrSex = "1";//男
        } else {
            ocrSex = "2";//女
        }
        String ocrRace = userInfo.getNation();
        String ocrEndTime = userInfo.getIdExpiryDate();
        String ocrStartTime = "2018-10-24";
        UploadParams faceImage = new UploadParams();
        faceImage.setUserId(userId);
        faceImage.setIp("127.0.0.1");
        faceImage.setSource("4");
        faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
        faceImage.setRemark(base.getChannelCode());// 区分渠道JDQAPI 和JDQAPI2
        XJ360Util.uploadBase64Image(ImageUtil.getURLImage(userInfo.getFace()), faceImage);
        faceImage.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
        XJ360Util.uploadBase64Image(ImageUtil.getURLImage(userInfo.getIdPositive()), faceImage);
        faceImage.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
        XJ360Util.uploadBase64Image(ImageUtil.getURLImage(userInfo.getIdNegative()), faceImage);

        OcrOP ocrOP = new OcrOP();
        ocrOP.setName(ocrName);
        ocrOP.setAddress(ocrAddress);
        ocrOP.setIdcard(ocrIdNumber);
        ocrOP.setAuthority(ocrIssuedBy);
        ocrOP.setSex(ocrSex);
        ocrOP.setNation(ocrRace.length() >= 2 ? ocrRace.substring(0, 2) : ocrRace);
        ocrOP.setValidDate(ocrStartTime.replace("-", ".") + "-" + ocrEndTime.replace("-", "."));
        ocrOP.setUserId(userId);
        int saveDoOcrResult = custUserService.saveDoOcr(ocrOP);

    }

    private String registerOrReturnUserId(String userPhone,String channelCode) {
        String userId = "";
        if (!custUserService.isRegister(userPhone)) {
            RegisterOP registerOP = new RegisterOP();
            registerOP.setAccount(userPhone);
            String password = XianJinCardUtils.setData(4);
            registerOP.setPassword(XianJinCardUtils.pwdToSHA1(String.valueOf(password)));
            registerOP.setChannel(channelCode);
            userId = custUserService.saveRegister(registerOP);
            //sendMsg(password, userId, userPhone);
        } else {
            CustUserVO custUserVO = custUserService.getCustUserByMobile(userPhone);
            userId = custUserVO.getId();
        }
        return userId;
    }

    private void sendMsg(String password, String userId, String mobile) {
        SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
        sendShortMsgOP.setIp("127.0.0.1");
        sendShortMsgOP.setMobile(mobile);
        sendShortMsgOP.setMessage(String.format(ShortMsgTemplate.MSG_TEMP_SEND_PWD, password));
        sendShortMsgOP.setUserId(userId);
        sendShortMsgOP.setMsgType(MsgTypeEnum.PUSH.getValue());
        sendShortMsgOP.setSource(SourceEnum.API.getCode());
        sendShortMsgOP.setChannelId(ChannelEnum.JIEDIANQIAN.getCode());
        shortMsgService.sendMsg(sendShortMsgOP);
    }


    private String uploadBaseData(IntoOrder intoOrder, String code, String userId) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(intoOrder.getJdqOrderId());
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        params.setRemark(intoOrder.getChannelCode());// 区分渠道JDQAPI 和JDQAPI2
        String fileBodyText = JsonMapper.toJsonString(intoOrder);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    private String uploadBaseData(JDQReport jdqReport, String code, String userId, String orderSn) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(orderSn);
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        params.setRemark(jdqReport.getChannelCode());// 区分渠道JDQAPI 和JDQAPI2
        String fileBodyText = JsonMapper.toJsonString(jdqReport);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public JDQCalculateInfoVO calculate(CalculateOP calculateOP) {
        BigDecimal amount = calculateOP.getLoanAmount();
        JDQCalculateInfoVO jdqCalculateInfo = new JDQCalculateInfoVO();

        String applyId = this.getApplyId(calculateOP.getJdqOrderId());
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            throw new RuntimeException("用户数据异常，订单尚未生成");
        }
        PromotionCase promotionCase = promotionCaseManager.getById(loanApply.getPromotionCaseId());
        PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
        promotionCaseOP.setApplyAmt(loanApply.getApproveAmt());
        promotionCaseOP.setApplyTerm(loanApply.getApproveTerm());
        promotionCaseOP.setProductId(loanApply.getProductId());
        promotionCaseOP.setChannelId(promotionCase.getChannelId());

        CostingResultVO costingResultVO = promotionCaseService.Costing(promotionCaseOP);

        jdqCalculateInfo.setMinAmount(promotionCaseOP.getApplyAmt());
        jdqCalculateInfo.setMaxAmount(promotionCaseOP.getApplyAmt());
        jdqCalculateInfo.setMultiple(new BigDecimal(100));
        jdqCalculateInfo.setCardAmount(costingResultVO.getToAccountAmt());
        ArrayList listTerm = new ArrayList();
        listTerm.add(String.valueOf(loanApply.getTerm()));
        jdqCalculateInfo.setLoanTerms(listTerm);
        if ("D".equals(loanApply.getRepayFreq())) {
            jdqCalculateInfo.setLoanTermUnit("2");
        } else if ("M".equals(loanApply.getRepayFreq())) {
            jdqCalculateInfo.setLoanTermUnit("1");
        }
        jdqCalculateInfo.setLoanTermDays(loanApply.getRepayUnit().toString());// 每期天数

        /*// 前期固定数据
        jdqCalculateInfo.setMinAmount(BigDecimal.valueOf(2000));
        jdqCalculateInfo.setMaxAmount(BigDecimal.valueOf(2000));
        jdqCalculateInfo.setMultiple(new BigDecimal(100));
        jdqCalculateInfo.setCardAmount(BigDecimal.valueOf(1500));
        ArrayList listTerm = new ArrayList();
        listTerm.add(String.valueOf(loanApply.getTerm()));
        jdqCalculateInfo.setLoanTerms(listTerm);
        jdqCalculateInfo.setLoanTermUnit("2");
        jdqCalculateInfo.setLoanTermDays("7");*/

        RepayPlanOP repayPlanOP = new RepayPlanOP();
        repayPlanOP.setApplyAmt(loanApply.getApproveAmt());
        repayPlanOP.setChannelId(loanApply.getChannelId());
        repayPlanOP.setProductId(loanApply.getProductId());
        repayPlanOP.setRepayTerm(loanApply.getApproveTerm());
        repayPlanOP.setRepayMethod(loanApply.getRepayMethod());
        repayPlanOP.setApplyId(loanApply.getId());
        Map<String, Object> repayPlanMap = loanRepayPlanService.getJDQRepayPlan(repayPlanOP);

        if (!repayPlanMap.isEmpty()) {
            BigDecimal totalAmt = (BigDecimal) repayPlanMap.get("totalAmt");
            List<Map<String, Object>> repayPlanDetails = (List<Map<String, Object>>) repayPlanMap.get("list");
            List<RepayPlan> repayPlans = new ArrayList<>();
            RepayPlan repayPlan = new RepayPlan();

            List<LoanTerm> loanTerms = new ArrayList<>();
            Date repayDate = new Date();
            for (int i = 0; i < repayPlanDetails.size(); i++) {
                Map<String, Object> repayPlanDetailMap = repayPlanDetails.get(i);
                LoanTerm loanTerm = new LoanTerm();
                loanTerm.setRepayAmount((BigDecimal) repayPlanDetailMap.get("repayAmt"));
                repayDate = DateUtils.addDay(repayDate, loanApply.getRepayUnit().intValue() - 1);
                loanTerm.setRepayTime(DateUtils.formatDate(repayDate, "yyyy/MM/dd"));
                loanTerms.add(loanTerm);
            }
            repayPlan.setLoanTerm(loanTerms);
            repayPlan.setSumAmount(totalAmt);
            repayPlans.add(repayPlan);
            jdqCalculateInfo.setRepayPlan(repayPlans);
            String loanDesc = "";
            ArrayList listDesc = new ArrayList();
            listDesc.add(loanDesc);
            jdqCalculateInfo.setLoanDesc(listDesc);
        }
        return jdqCalculateInfo;
    }

    @Override
    public JDQResp repayment(RepaymentOP repaymentOP) {
        JDQResp jdqResp = new JDQResp();
        String orderId = repaymentOP.getJdqOrderId();
        String applyId = this.getApplyId(repaymentOP.getJdqOrderId());
        String lockKey = Global.JBD_PAY_LOCK + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        // 根据orderId防并发加锁
        JSONObject result = new JSONObject();
        boolean lock = JedisUtils.setLock(lockKey, requestId, 60);
        if (!lock) {
            log.warn("【借点钱】协议直接支付接口调用中，applyId={},jdqOrderId= {}",applyId, orderId);
            jdqResp.setCode(JDQResp.ERROR);
            jdqResp.setMsg("还款处理中，请勿重复操作");
            result.put("repay_result", 606);
            jdqResp.setData(result);
            return jdqResp;
        }

        try {
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(loanApplySimpleVO.getUserId());
            if (custUserVO == null){
                custUserVO = custUserService.getCustUserById(loanApplySimpleVO.getUserId());
            }

            RePayOP rePayOP = new RePayOP();
            rePayOP.setApplyId(applyId);
            rePayOP.setUserId(loanApplySimpleVO.getUserId());
            rePayOP.setRepayPlanItemId(loanApplySimpleVO.getRepayPlanItemId());
            rePayOP.setTxAmt(loanApplySimpleVO.getCurToltalRepayAmt().toString());
            rePayOP.setPayType(1);
            rePayOP.setPrePayFlag(RePayOP.PREPAY_FLAG_NO);
            rePayOP.setSource(loanApplySimpleVO.getSource());
            rePayOP.setIp(loanApplySimpleVO.getIp());

            rePayOP.setBindId(custUserVO.getBindId());
            rePayOP.setFullName(custUserVO.getRealName());
            rePayOP.setTxType(Global.REPAY_TYPE_MANUAL);// 主动还款


            //ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementTonglianPay(rePayOP);
            ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPayTest(rePayOP);
            if (confirmAuthPayVO.isSuccess()) {
                jdqResp.setCode(JDQResp.SUCCESS);
                jdqResp.setMsg("还款成功");
                result.put("repay_result", 200);
                jdqResp.setData(result);
                return jdqResp;
            }
            if ("I".equals(confirmAuthPayVO.getStatus())) {
                jdqResp.setCode(JDQResp.SUCCESS);
                jdqResp.setMsg("还款处理中，请稍后查询");
                result.put("repay_result", 606);
                jdqResp.setData(result);
                return jdqResp;
            } else {
                jdqResp.setCode(JDQResp.ERROR);
                jdqResp.setMsg(confirmAuthPayVO.getMsg());
                result.put("repay_result", 505);
                jdqResp.setData(result);
                return jdqResp;
            }
        } catch (Exception e) {
            log.error("【借点钱】主动还款接口异常!jdqOrderId={}", repaymentOP.getJdqOrderId(), e);
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("还款异常");
            result.put("repay_result", 505);
            jdqResp.setData(result);
        } finally {
            // 解除orderId并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
        return jdqResp;
    }

    @Override
    public CardInfoVO cardInfo(CardInfoOP cardInfoOP) {
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("id_no", cardInfoOP.getIdNumber()));
        if (!StringUtils.isBlank(cardInfoOP.getPhone())) {
            criteria.and(Criterion.eq("mobile", cardInfoOP.getPhone()));
        }
        CustUser custUser = custUserManager.getByCriteria(criteria);
        if (custUser == null) {
            log.error("【借点钱】用户数据异常！custUser=null，idNumber={}，phone={}", cardInfoOP.getIdNumber(), cardInfoOP.getPhone());
            return null;
        }

        CardInfoVO cardInfo = new CardInfoVO();
        cardInfo.setAddCardFlag(1);

        if (!StringUtils.isBlank(custUser.getBankCode())) {
            JDQBankCardInfo bankCardInfo = new JDQBankCardInfo();
            bankCardInfo.setBankCode(custUser.getBankCode());
            bankCardInfo.setBankName(BankCodeEnum.getName(custUser.getBankCode()));
            bankCardInfo.setCardNo(custUser.getCardNo());
            bankCardInfo.setCardType("1");
            List<JDQBankCardInfo> list = new ArrayList<>();
            list.add(bankCardInfo);
            cardInfo.setJdqBankCardInfo(list);

        }
        return cardInfo;
    }

    @Override
    public JDQResp bindCard(String partnerDecode) {
        return null;
    }

    @Override
    public void withdraw(WithdrawOP withdrawOP) {
        String applyId = this.getApplyId(withdrawOP.getJdqOrderId());
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null || StringUtils.isBlank(loanApply.getId())) {
            throw new RuntimeException("该用户尚未进件！");
        }
        CustUserVO custUserVO = custUserService.getCustUserById(loanApply.getUserId());
        if (custUserVO == null || custUserVO.getCardNo() == null) {
            throw new RuntimeException("该用户尚未绑卡！");
        }
//        if (custUserVO.getAccountId() == null) {
//            throw new RuntimeException("该用户尚未开户！");
//        }
        boolean flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
        logger.info("flag:{}", flag);
        if (!flag) {
            throw new RuntimeException("系统异常！");
        } else {
            productorService.sendOrderStatus(applyId, "1", "withdraw");
        }

    }

    @Override
    public String getApplyId(String orderNo) {
        String applyId = "";
        if (orderNo == null) {
            orderNo = "";
        }
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("tripartite_no", orderNo));
        ApplyTripartiteJdq applyTripartiteJdq = applyTripartiteJdqManager.getByCriteria(criteria);
        if (applyTripartiteJdq != null) {
            applyId = applyTripartiteJdq.getApplyId();
        }
        return applyId;
    }

    @Override
    public List<String> findThirdIdsByApplyIds(List<String> applyIds) {
        return applyTripartiteJdqManager.findThirdIdsByApplyIds(applyIds);
    }

    public static CheckUserVO getIsUserAcceptCache(String key) {
        CheckUserVO checkUserVO = new CheckUserVO();
        if (StringUtils.isNotBlank(key)) {
            return (CheckUserVO) JedisUtils.getObject("JDQ:IsUserAccept_" + key);
        }
        return checkUserVO;
    }

    public static CheckUserVO setIsUserAcceptCache(CheckUserVO checkUserVO, String key) {
        if (null != checkUserVO) {
            JedisUtils.setObject("JDQ:IsUserAccept_" + key, checkUserVO, 30);
        }
        return checkUserVO;
    }

    @Override
    public TaskResult orderStatusFeedbackToRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> jdqCallBackMap = JedisUtils.getMap(Global.JDQ_ORDERSTATUS_FEEDBACK);
            if (jdqCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(jdqCallBackMap.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(2000);
                        if (count >= 200) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderSn = this.getOrderNo(applyId);
                        if (orderSn != null) {
                            log.info("----------【借点钱】定时任务-订单状态反馈applyId={}----------", applyId);
                            boolean result = jdqStatusFeedBackService.orderStatusFeedBack(applyId);
                            if (result) {
                                JedisUtils.mapRemove(Global.JDQ_ORDERSTATUS_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.JDQ_ORDERSTATUS_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("----------【借点钱】定时任务-订单状态反馈异常----------");
            e.printStackTrace();
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public JDQReport getReportData(String orderSn) {
        String cacheKey = "JDQ:PUSH_REPORT_" + orderSn;
        JDQReport vo = (JDQReport) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastJDQReportByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    log.info("{}-{}-请求地址：{}", "借点钱", "从文件获取用户报告信息", fileInfoVO.getUrl());
                    vo = (JDQReport) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(), JDQReport.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        log.info("{}-{}-应答结果：{}", "借点钱", "从文件获取用户报告信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public void savejdqData(IntoOrder intoOrder,IntoOrder additional) {
        Map<String, Object> result = new HashMap<>();
        Basic basic = intoOrder.getTelecom().getBasic();
        List<Transactions> transactions = intoOrder.getTelecom().getTransactions();
        if (transactions != null) {
            Transactions sum = new Transactions();
            for (Transactions t : transactions) {
                sum.setPayAmt(t.getPayAmt() + sum.getPayAmt());
                sum.setPlanAmt(t.getPlanAmt() + sum.getPlanAmt());
                sum.setTotalAmt(t.getTotalAmt() + sum.getTotalAmt());
            }
            Transactions avg = new Transactions();
            int size = transactions.size() == 0 ? 1 : transactions.size();
            avg.setPayAmt(sum.getPayAmt() / size);
            avg.setTotalAmt(sum.getTotalAmt() / size);
            avg.setPlanAmt(sum.getPlanAmt() / size);
            avg.setBillCycle("平均");
            transactions.add(avg);
            Collections.sort(transactions, new Comparator<Transactions>() {
                @Override
                public int compare(Transactions t1, Transactions t2) {
                    return t1.getBillCycle().compareTo(t2.getBillCycle());
                }
            });
        }
        List<AddressBook> addressBookList = additional.getAddressBook();
        List<Calls> callsList = intoOrder.getTelecom().getCalls();
        Map<String, Count> zj = Maps.newHashMap();
        Map<String, Count> bj = Maps.newHashMap();
        int callAtNight = 0;
        int callAtDay = 0;
        int call110 = 0;
        List<String> stringList = Lists.newArrayList();
        for (Calls calls : callsList) {
            stringList.add(calls.getStartTime());
            int hour = Integer.parseInt(calls.getStartTime().substring(11, 13));
            if (hour >= 23 || hour < 6) {
                callAtNight++;
            }
            if (hour < 23 && hour > 6) {
                callAtDay++;
            }
            if (calls.getOtherCellPhone().equals("110")) {
                call110++;
            }
            bj = CallCount("被叫", calls, bj);
            zj = CallCount("主叫", calls, zj);
        }
        BigDecimal count = new BigDecimal(callAtNight + callAtDay);
        BigDecimal bl = new BigDecimal(callAtNight).divide(count, 2, BigDecimal.ROUND_HALF_UP).multiply(
                new BigDecimal(100));
        Set<String> bjSet = bj.keySet();
        Set<String> zjSet = zj.keySet();
        Set<String> countSet = Sets.newHashSet();
        countSet.addAll(bjSet);
        countSet.retainAll(zjSet);
        int countCall = countSet.size();
        countSet.clear();
        countSet.addAll(bjSet);
        countSet.addAll(zjSet);
        int countAll = countSet.size();
        BigDecimal bigDecimal = new BigDecimal(countCall).divide(new BigDecimal(countAll), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));
        int days1 = 0;
        int days3 = 0;
        List<String> listNew = new ArrayList<>(new TreeSet<>(stringList));
        for (int i = 0; i < listNew.size() - 1; i++) {
            Date start = DateUtils.parse(listNew.get(i));
            Date end = DateUtils.parse(listNew.get(i + 1));
            int days = DateUtils.daysBetween(start, end) - 1;
            if (days >= 1) {
                days1+=days;
            }
            if (days > 2) {
                days3++;
            }
        }
        Date start = DateUtils.parse(listNew.get(0));
        Date end = DateUtils.parse(listNew.get(listNew.size() - 1));
        int days = DateUtils.daysBetween(start, end);
        List<ContactList> listbj = Lists.newArrayList();
        for (String k : bj.keySet()) {
            ContactList contactList = new ContactList();
            contactList.setCallInCnt(bj.get(k).getCount());
            contactList.setCallInLen(bj.get(k).getSum());
            contactList.setPhoneNum(k);
            contactList.setPhoneNumLoc(bj.get(k).getGsd());
            contactList.setLastCall(bj.get(k).getLastCall());
            contactList.setFirstCall(bj.get(k).getFirstCall());
            listbj.add(contactList);
        }
        List<ContactList> listzj = Lists.newArrayList();
        for (String k : zj.keySet()) {
            ContactList contactList = new ContactList();
            contactList.setCallOutCnt(zj.get(k).getCount());
            contactList.setCallOutLen(zj.get(k).getSum());
            contactList.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(zj.get(k).getSum()).longValue()));
            contactList.setPhoneNum(k);
            contactList.setPhoneNumLoc(zj.get(k).getGsd());
            contactList.setLastCall(zj.get(k).getLastCall());
            contactList.setFirstCall(zj.get(k).getFirstCall());
            listzj.add(contactList);
        }
        List<ContactList> calInCntList = getTopCalInCntList(listbj, 50);
        List<ContactList> callOutCntList = getTopCallOutCntList(listzj, 50);
        List calInCntListV = jdqContactMatch(calInCntList, addressBookList);
        List callOutCntListV = jdqContactMatch(callOutCntList, addressBookList);
        UserContact contactInfo = additional.getUserContact();
        List<JDQUrgentContact> urgentContactArrayList = new ArrayList<>();
        JDQUrgentContact urgentContact = new JDQUrgentContact();
        urgentContact.setMobile(contactInfo.getMobile());
        urgentContact.setName(contactInfo.getName());
        urgentContact.setRelation(contactInfo.getRelation());
        for (ContactList contactList1 : listbj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobile())) {
                urgentContact.setFirstCallIn(contactList1.getFirstCall());
                urgentContact.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
                urgentContact.setLastCallIn(contactList1.getLastCall());
                urgentContact.setCallInCnt(contactList1.getCallInCnt());
                urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        for (ContactList contactList1 : listzj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobile())) {
                urgentContact.setFirstCallOut(contactList1.getFirstCall());
                urgentContact.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
                urgentContact.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
                urgentContact.setLastCallOut(contactList1.getLastCall());
                urgentContact.setCallOutCnt(contactList1.getCallOutCnt());
                urgentContact.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact);
        JDQUrgentContact urgentContact1 = new JDQUrgentContact();
        urgentContact1.setMobile(contactInfo.getMobileSpare());
        urgentContact1.setName(contactInfo.getNameSpare());
        urgentContact1.setRelation(contactInfo.getRelationSpare());
        for (ContactList contactList1 : listbj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobileSpare())) {
                urgentContact1.setFirstCallIn(contactList1.getFirstCall());
                urgentContact1.setCallInLen(new Double(contactList1.getCallInLen()).intValue());
                urgentContact1.setCallInLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallInLen()).longValue()));
                urgentContact1.setLastCallIn(contactList1.getLastCall());
                urgentContact1.setCallInCnt(contactList1.getCallInCnt());
                urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        for (ContactList contactList1 : listzj) {
            if (contactList1.getPhoneNum().equals(contactInfo.getMobileSpare())) {
                urgentContact1.setFirstCallOut(contactList1.getFirstCall());
                urgentContact1.setCallOutLen(new Double(contactList1.getCallOutLen()).intValue());
                urgentContact1.setCallOutLenStr(DateUtils.formatDateTimeStr(new Double(contactList1.getCallOutLen()).longValue()));
                urgentContact1.setLastCallOut(contactList1.getLastCall());
                urgentContact1.setCallOutCnt(contactList1.getCallOutCnt());
                urgentContact1.setPhoneNumLoc(contactList1.getPhoneNumLoc());
            }
        }
        urgentContactArrayList.add(urgentContact1);
        String regTime = basic.getRegTime();
        Date date = DateUtils.parse(regTime);
        int month = DateUtils.getMonth(date, new Date());

        List<ContactCheck> contactChecks = new ArrayList<>();
        Map<String, Count> countMap = Maps.newHashMap();
        for (Calls calls : callsList) {
            Count object = countMap.get(calls.getOtherCellPhone());
            if (object == null) {
                Count count1 = new Count();
                count1.setCount(1);
                count1.setSum(calls.getUseTime());
                countMap.put(calls.getOtherCellPhone(), count1);
            } else {
                object.setCount(object.getCount() + 1);
                object.setSum(object.getSum() + calls.getUseTime());
                countMap.put(calls.getOtherCellPhone(), object);
            }
        }
        for (AddressBook addressBook : addressBookList) {
            ContactCheck contactCheck = new ContactCheck();
            String contactMobile = addressBook.getMobile();
            if (StringUtils.isBlank(contactMobile) || contactMobile.length() < 5){
                continue;
            }
            String contactName = addressBook.getName();
            contactCheck.setMobile(contactMobile);
            contactCheck.setName(contactName);
            contactCheck.setCallCnt(0);
            contactCheck.setCallLen(0);
            for (String k : countMap.keySet()) {
                if (contactMobile.trim().contains(k.trim()) || k.trim().contains(contactMobile.trim())) {
                    contactCheck.setCallCnt(countMap.get(k).getCount());
                    contactCheck.setCallLen(countMap.get(k).getSum());
                }
            }
            contactCheck.setCallLenStr(DateUtils.formatDateTimeStr(new Double(contactCheck.getCallLen()).longValue()));
            contactChecks.add(contactCheck);
        }
        Collections.sort(contactChecks);

        JDQReport jdqReport = new JDQReport();
        jdqReport.setCallatnight(callAtNight);
        jdqReport.setBl(bl);
        jdqReport.setDays(days);
        jdqReport.setDays1(days1);
        jdqReport.setDays3(days3);
        jdqReport.setCall110(call110);
        jdqReport.setCountcall(countCall);
        jdqReport.setBigdecimal(bigDecimal);
        jdqReport.setBjsize(listbj.size());
        jdqReport.setZjsize(listzj.size());
        jdqReport.setMonth(month);
        jdqReport.setCalincntlistv(calInCntListV);
        jdqReport.setCalloutcntlistv(callOutCntListV);
        //紧急联系人
        jdqReport.setUrgentcontact(urgentContactArrayList);
        jdqReport.setTransactions(transactions);
        jdqReport.setBasic(basic);
        jdqReport.setContactCheckList(contactChecks);
        //top20 通话与本地通讯录 匹配个数
        jdqReport.setCallCountTop20MatchAddressBookNum(callsMatchAddressBook(20,addressBookList,countMap, "count"));
        jdqReport.setCallLongTop20MatchAddressBookNum(callsMatchAddressBook(20,addressBookList,countMap, "long"));

        jdqReport.setChannelCode(intoOrder.getChannelCode());
        saveReport(jdqReport, intoOrder);
    }

    public Map<String, Count> CallCount(String type, Calls calls, Map<String, Count> call) {
        if (calls.getInitType().equals(type)) {
            Count object = call.get(calls.getOtherCellPhone());
            if (object == null) {
                Count count1 = new Count();
                count1.setCount(1);
                count1.setSum(calls.getUseTime());
                count1.setGsd(calls.getPlace());
                count1.setFirstCall(calls.getStartTime());
                count1.setLastCall(calls.getStartTime());
                call.put(calls.getOtherCellPhone(), count1);
            } else {
                object.setCount(object.getCount() + 1);
                object.setSum(object.getSum() + calls.getUseTime());
                if (DateUtils.compareDate(object.getFirstCall(), calls.getStartTime()) == 1) {
                    object.setFirstCall(calls.getStartTime());
                }
                if (DateUtils.compareDate(object.getLastCall(), calls.getStartTime()) == -1) {
                    object.setLastCall(calls.getStartTime());
                }
                call.put(calls.getOtherCellPhone(), object);
            }
        }
        return call;
    }

    public static List<ContactList> getTopCalInCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    ContactList ccm1 = o1;
                    ContactList ccm2 = o2;
                    if (ccm1.getCallInCnt() > ccm2.getCallInCnt()) {
                        return -1;
                    }
                    if (ccm1.getCallInCnt() < ccm2.getCallInCnt()) {
                        return 1;
                    }
                    return 0;
                }
            });
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    public static List<ContactList> getTopCallOutCntList(List<ContactList> ccmList, int top) {
        if (ccmList != null) {
            Collections.sort(ccmList, new Comparator<ContactList>() {
                @Override
                public int compare(ContactList o1, ContactList o2) {
                    ContactList ccm1 = o1;
                    ContactList ccm2 = o2;
                    if (ccm1.getCallOutCnt() > ccm2.getCallOutCnt()) {
                        return -1;
                    }
                    if (ccm1.getCallOutCnt() < ccm2.getCallOutCnt()) {
                        return 1;
                    }
                    return 0;
                }
            });
            top = (ccmList.size() < top) ? ccmList.size() : top;
            List<ContactList> list = ccmList.subList(0, top);
            return list;
        } else {
            List<ContactList> list = new ArrayList<ContactList>();
            return list;
        }
    }

    private List jdqContactMatch(List<ContactList> contactLists, List<AddressBook> phoneList) {
        List ccmList2 = new ArrayList();
        for (ContactList contactList : contactLists) {
            String phoneNum = contactList.getPhoneNum();
            String contactName = "";
            if (phoneList != null) {
                for (AddressBook phone : phoneList) {
                    String contactMobile = phone.getMobile();
                    if (StringUtils.equals(phoneNum, contactMobile)) {
                        contactName = phone.getName();
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
            m.put("call_len", new Double(contactList.getCallLen()).intValue());
            m.put("terminatingCallCount", contactList.getCallInCnt());
            m.put("terminatingTime", new Double(contactList.getCallInLen()).intValue());
            m.put("terminatingTimeStr", DateUtils.formatDateTimeStr(new Double(contactList.getCallInLen()).longValue()));
            m.put("originatingCallCount", contactList.getCallOutCnt());
            m.put("originatingTime", new Double(contactList.getCallOutLen()).intValue());
            m.put("originatingTimeStr", DateUtils.formatDateTimeStr(new Double(contactList.getCallOutLen()).longValue()));
            m.put("contactName", contactName);
            m.put("lastCall", contactList.getLastCall());
            m.put("firstCall", contactList.getFirstCall());
            ccmList2.add(m);
        }
        return ccmList2;
    }

    public boolean saveReport(JDQReport jdqReport, IntoOrder intoOrder) {
        boolean flag = false;
        try {
            String userPhone = intoOrder.getUserInfo().getPhone();
            String userId = registerOrReturnUserId(userPhone,intoOrder.getChannelCode());
            FileInfoVO fileInfoVO = custUserService.getLastJDQReportByOrderSn(intoOrder.getJdqOrderId());
            if (fileInfoVO == null) {
                String res = uploadBaseData(jdqReport, FileBizCode.JDQ_REPORT_DATA.getBizCode(), userId,
                        intoOrder.getJdqOrderId());
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *
     * @param top
     * @param addressBooks
     * @param countMap
     * @param type
     * @return
     */
    private static int callsMatchAddressBook(int top, List<AddressBook> addressBooks, Map<String, Count> countMap, String type) {
        int matchNum = 0;
        Map<String,Integer> map = new HashMap<>();
        for (String k : countMap.keySet()) {
            //通话次数
            if ("count".equals(type)) {
                map.put(k,countMap.get(k).getCount());
            } else {
                map.put(k,countMap.get(k).getSum());
            }
        }
        //value 降序
        List<Map.Entry<String,Integer>> list = new LinkedList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>()
        {
            @Override
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        for (int j = 0; j < list.size(); j++) {
            if(j == top){
                break;
            }
            String k = list.get(j).getKey();
            for (int i = 0; i < addressBooks.size(); i++) {
                if(k.equals(addressBooks.get(i).getMobile())){
                    matchNum ++;
                    break;
                }
            }
        }
        return matchNum;
    }
}
