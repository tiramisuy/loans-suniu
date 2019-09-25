package com.rongdu.loans.loan.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.XJ360FQUtil;
import com.rongdu.loans.common.XJ360Util;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.cust.option.BaseInfoOP;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.MsgTypeEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.loan.dao.UserInfoHistoryDAO;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.OperationLog;
import com.rongdu.loans.loan.entity.PromotionCase;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.PromotionCaseManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.option.*;
import com.rongdu.loans.loan.option.xjbk.*;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.BaofooAuthPayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("xianJinBaiKaService")
public class XianJinBaiKaServiceImpl implements XianJinBaiKaService {
    private static Logger logger = LoggerFactory.getLogger(XianJinBaiKaServiceImpl.class);

    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ApplyTripartiteService applyTripartiteService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private PromotionCaseManager promotionCaseManager;
    @Autowired
    private BaofooAuthPayService baofooAuthPayService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private FileInfoManager fileInfoManager;
    @Autowired
    public OverdueService overdueService;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;
    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private UserInfoHistoryDAO userInfoHistoryDAO;
    @Autowired
    private BorrowInfoManager borrowInfoManager;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ShortMsgService shortMsgService;
    private FileServerClient fileServerClient = new FileServerClient();

    @Override
    public boolean savePushUserBaseInfo(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        boolean flag = false;
        try {
            String userPhone = xianJinBaiKaCommonRequest.getUser_info().getUserPhone();
            String userId = registerOrReturnUserId(userPhone);
            FileInfoVO fileInfoVO = custUserService.getLastBaseDataByOrderSn(xianJinBaiKaCommonRequest.getOrder_info()
                    .getOrderSn());
            if (fileInfoVO == null) {
                logger.info("开始上传基础信息:{}", userPhone);
                String res = uploadData(xianJinBaiKaCommonRequest, FileBizCode.XJBK_BASE_DATA.getBizCode(), userId);
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

    private String registerOrReturnUserId(String userPhone) {
        String userId = "";
        if (!custUserService.isRegister(userPhone)) {
            RegisterOP registerOP = new RegisterOP();
            registerOP.setAccount(userPhone);
            String password = XianJinCardUtils.setData(4);
            registerOP.setPassword(XianJinCardUtils.pwdToSHA1(String.valueOf(password)));
            registerOP.setChannel("XJBK");
            userId = custUserService.saveRegister(registerOP);
            sendMsg(password, userId, userPhone);
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
        sendShortMsgOP.setChannelId("XJBK");
        shortMsgService.sendMsg(sendShortMsgOP);
    }

    @Override
    @Transactional
    public boolean savePushUserAdditionalInfo(XianJinBaiKaCommonOP additional) {
        boolean flag = false;
        try {
            String orderSn = additional.getOrder_info().getOrderSn();
            FileInfoVO fileInfoVO = custUserService.getLastAdditionalDataByOrderSn(orderSn);
            if (fileInfoVO == null) {
                String res = uploadData(additional, FileBizCode.XJBK_ADDITIONAL_DATA.getBizCode(), "xjbkAdditional");
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                    Map<String, String> map = Maps.newHashMap();
                    map.put(orderSn, String.valueOf(System.currentTimeMillis()));
                    JedisUtils.mapPut(Global.XJBK_THIRD_KEY, map);
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public TaskResult saveUserAndApplyInfo() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        Map<String, String> thirdKey = JedisUtils.getMap(Global.XJBK_THIRD_KEY);
        if (thirdKey != null) {
            List<Map.Entry<String, String>> thirdKeyList = new ArrayList<>(thirdKey.entrySet());
            Collections.sort(thirdKeyList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    return map1.getValue().compareTo(map2.getValue());
                }
            });
            int limit = thirdKeyList.size() >= 100 ? 100 : thirdKeyList.size();
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
                    XianJinBaiKaCommonOP base = getPushBaseData(orderSn);
                    XianJinBaiKaCommonOP additional = getPushAdditionalData(orderSn);
                    if (base != null && additional != null) {
                        try {
                            count++;
                            String userPhone = base.getUser_info().getUserPhone();
                            String ip = additional.getUser_additional().getDeviceInfo().getIp();
                            String userId = registerOrReturnUserId(userPhone);
                            if (!loanApplyService.isExistUnFinishLoanApply(userId)) {
                                int data = fileInfoManager.updateUserIdByOrderSn(userId, orderSn, "xjbk_addition_data");
                                saveDoOcr(base, userId);
                                saveBaseInfo(base, additional, userId, "XJBK");
                                saveRz(userId, ip);
                                saveLoanApply(additional, userId);
                            }
                            JedisUtils.mapRemove(Global.XJBK_THIRD_KEY, orderSn);
                            succNum++;
                        } catch (Exception e) {
                            JedisUtils.mapRemove(Global.XJBK_THIRD_KEY, orderSn);
                            logger.error("三方工单转化失败:{} 手工处理", orderSn);
                        }
                    } else {
                        Map<String, String> rePush = Maps.newHashMap();
                        rePush.put(orderSn, String.valueOf(System.currentTimeMillis()));
                        JedisUtils.mapPut(Global.XJBK_THIRD_KEY, rePush);
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

    public void saveRz(String userId, String ip) {
        /** 保存操作记录数据 */
        // OperationLog operationLog = new OperationLog();
        // operationLog.setUserId(userId);
        // operationLog.setStage(XjdLifeCycle.LC_TEL);
        // operationLog.setStatus(XjdLifeCycle.LC_TEL_1);
        // operationLog.setIp(ip);
        // operationLog.setSource(Global.DEFAULT_SOURCE);
        // operationLog.defOperatorIdAndName();
        // operationLog.preInsert();
        // loanApplyManager.saveOperationLog(operationLog);

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

    private SaveApplyResultVO saveLoanApply(XianJinBaiKaCommonOP additional, String userId) {
        String orderSn = additional.getOrder_info().getOrderSn();
        String loanAmount = additional.getOrder_info().getLoanAmount();
        String loanTerm = additional.getOrder_info().getLoanTerm();
        LoanApplyOP loanApplyOP = new LoanApplyOP();
        loanApplyOP.setProductId("XJD");
        loanApplyOP.setUserId(userId);
        BigDecimal loanAmountBigDecimal = new BigDecimal(loanAmount);
        BigDecimal toYuan = new BigDecimal("100");
        loanApplyOP.setApplyAmt(loanAmountBigDecimal.divide(toYuan));
        loanApplyOP.setApplyTerm(Integer.valueOf(loanTerm));
        loanApplyOP.setSource("4");
        loanApplyOP.setProductType("0");
        loanApplyOP.setPurpose("10");
        cleanCustUserInfoCache(userId);
        SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
        if (!applyTripartiteService.isExistApplyId(rz.getApplyId())) {
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("id", rz.getApplyId()));
            LoanApply loanApply = new LoanApply();
            loanApply.setChannelId("XJBK");
            loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
            int saveTripartiteOrderResult = applyTripartiteService.insertTripartiteOrder(rz.getApplyId(), orderSn);
        }
        return rz;
    }

    private void saveBaseInfo(XianJinBaiKaCommonOP base, XianJinBaiKaCommonOP additional, String userId,
                              String channelId) {
        String userPhone = base.getUser_info().getUserPhone();
        String userName = base.getUser_info().getUserName();
        String userIdcard = base.getUser_info().getUserIdcard();
        String degree = additional.getUser_additional().getEducationInfo().getDegree();
        String homeAddress = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_address();
        String professionType = additional.getUser_additional().getWorkInfo().getProfessionType();
        String relation = additional.getUser_additional().getContactInfo().getRelation();
        String mobile = additional.getUser_additional().getContactInfo().getMobile();
        String name = additional.getUser_additional().getContactInfo().getName();
        String relation1 = XJ360Util.convertRelation(relation);
        String inRelation = relation1 + "," + name + "," + mobile;
        String relationSpare = additional.getUser_additional().getContactInfo().getRelationSpare();
        String nameSpare = additional.getUser_additional().getContactInfo().getNameSpare();
        String mobileSpare = additional.getUser_additional().getContactInfo().getMobileSpare();
        String relationSpare1 = XJ360Util.convertRelation(relationSpare);
        String inRelationSpare = relationSpare1 + "," + nameSpare + "," + mobileSpare;
        String qq = additional.getUser_additional().getQq().getQq();
        String marriage = additional.getUser_additional().getMarriage().getMarriage();
        BaseInfoOP baseInfoOP = new BaseInfoOP();
        String companyName = "";
        String address = "";
        String tel = "";
        String[] telSplit = tel.split("-");
        if (professionType.equals("1")) {
            companyName = additional.getUser_additional().getWorkInfo().getWorkOfficeInfo().getCompanyName();
            address = additional.getUser_additional().getWorkInfo().getWorkOfficeInfo().getCompanyAddress();
            tel = additional.getUser_additional().getWorkInfo().getWorkOfficeInfo().getCompanyTel();
            telSplit = tel.split("-");
        }
        baseInfoOP.setUserName(userName);
        baseInfoOP.setMobile(userPhone);
        baseInfoOP.setIdNo(userIdcard);
        baseInfoOP.setIdType("0");
        baseInfoOP.setUserId(userId);
        baseInfoOP.setComName(companyName);
        baseInfoOP.setWorkAddr(address);
        baseInfoOP.setComTelZone(telSplit.length >= 1 ? telSplit[0] : "");
        baseInfoOP.setComTelNo(telSplit.length >= 2 ? telSplit[1] : "");
        baseInfoOP.setComTelExt(telSplit.length >= 3 ? telSplit[2] : "");
        baseInfoOP.setMarital(Integer.valueOf(XJ360Util.convertMarriage(marriage)));
        baseInfoOP.setDegree(Integer.valueOf(degree) > 4 ? "4" : degree);
        baseInfoOP.setResideAddr(homeAddress);
        baseInfoOP.setContactParent(inRelation);
        baseInfoOP.setContactFriend(inRelationSpare);
        baseInfoOP.setQq(qq);
        baseInfoOP.setChannelId(channelId);
        int saveRz = custUserService.saveBaseInfo(baseInfoOP);
    }

    private void saveDoOcr(XianJinBaiKaCommonOP base, String userId) {
        try {
            String userName = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_name();
            String idNo = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_id_number();
            // 已绑卡客户，姓名,身份证以绑卡时为准
            CustUserVO custUser = custUserService.getCustUserById(userId);
            if (custUser != null && StringUtils.isNotBlank(custUser.getCardNo())) {
                userName = custUser.getRealName();
                idNo = custUser.getIdNo();
            }

            String faceRecognitionPicture = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail()
                    .getFace_recognition_picture();
            String zPicture = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getId_number_z_picture();
            String fPicture = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getId_number_f_picture();
            String ocrName = userName;
            String ocrAddress = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_address();
            String ocrIdNumber = idNo;
            String ocrIssuedBy = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_issued_by();
            String ocrSex = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_sex();
            String ocrRace = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_race();
            String ocrStartTime = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_start_time();
            String ocrEndTime = base.getUser_verify().getIdcardInfo().getIdcardInfoDetail().getOcr_end_time();

            String faceRecognitionPictureBase64 = "";
            String zPictureBase64 = "";
            String fPictureBase64 = "";
            faceRecognitionPictureBase64 = ImageUtil.getURLImage(faceRecognitionPicture);
            zPictureBase64 = ImageUtil.getURLImage(zPicture);
            fPictureBase64 = ImageUtil.getURLImage(fPicture);
            UploadParams faceImage = new UploadParams();
            faceImage.setUserId(userId);
            faceImage.setIp("127.0.0.1");
            faceImage.setSource("4");
            faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
            uploadBase64Image(faceRecognitionPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
            uploadBase64Image(zPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
            uploadBase64Image(fPictureBase64, faceImage);
            OcrOP ocrOP = new OcrOP();
            ocrOP.setName(ocrName);
            ocrOP.setAddress(ocrAddress);
            ocrOP.setIdcard(ocrIdNumber);
            ocrOP.setAuthority(ocrIssuedBy);
            ocrOP.setSex(ocrSex);
            ocrOP.setNation(ocrRace);
            ocrOP.setValidDate(ocrStartTime.replace("-", ".") + "-" + ocrEndTime.replace("-", "."));
            ocrOP.setUserId(userId);
            int saveDoOcrResult = custUserService.saveDoOcr(ocrOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String uploadData(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest, String code, String userId) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(xianJinBaiKaCommonRequest.getOrder_info().getOrderSn());
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        String fileBodyText = JsonMapper.toJsonString(xianJinBaiKaCommonRequest);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    @Override
    public XianJinBaiKaCommonOP getPushBaseData(String orderSn) {
        String cacheKey = "XJBK:PUSH_BASE_" + orderSn;
        XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastBaseDataByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户基础信息", fileInfoVO.getUrl());
                    vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                            XianJinBaiKaCommonOP.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "现金白卡", "从文件获取用户基础信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public XianJinBaiKaCommonOP getPushAdditionalData(String orderSn) {
        String cacheKey = "XJBK:PUSH_ADDITIONAL_" + orderSn;
        XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastAdditionalDataByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户附加信息", fileInfoVO.getUrl());
                    vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                            XianJinBaiKaCommonOP.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "现金白卡", "从文件获取用户附加信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public TaskResult approveFeedback() {
        TaskResult taskResult = new TaskResult();
        Page<ApproveFeedbackOP> page = new Page<ApproveFeedbackOP>();
        String notice = "approveFeedback";
        page.setPageSize(100);
        page.setPageNo(1);
        ApplyThirdOP op = new ApplyThirdOP();
        Date now = new Date();
        Date startTime = DateUtils.addDay(new Date(), -30);// 精确到天
        Date endTime = new Date(now.getTime() - 3 * 60 * 1000);// 精确到分钟
        op.setApplyTimeStart(startTime);
        op.setApplyTimeEnd(endTime);
        op.setStatusList(Arrays.asList(XjdLifeCycle.LC_RAISE_0, XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2,
                XjdLifeCycle.LC_AUTO_AUDIT_2, XjdLifeCycle.LC_CHANNEL_0));
        op.setNotice(notice);
        page = loanApplyService.getLoanApplyThirdList(page, op);
        List<ApproveFeedbackOP> list = page.getList();
        int succNum = 0;
        int count = 0;
        for (ApproveFeedbackOP vo : list) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            count++;
            ApproveFeedbackOP approveFeedbackOP = new ApproveFeedbackOP();
            approveFeedbackOP.setOrderSn(vo.getOrderSn());
            approveFeedbackOP.setApproveTerm("28");
            approveFeedbackOP.setApproveAmount(vo.getApproveAmount().multiply(BigDecimal.valueOf(100)));
            if (vo.getApproveStatus().equals("410")) {
                approveFeedbackOP.setApproveStatus("200");
                approveFeedbackOP.setApproveRemark("ok");
            } else {
                approveFeedbackOP.setApproveStatus("403");
                approveFeedbackOP.setApproveRemark("评分不足");
            }
            approveFeedbackOP.setCanLoanTime(DateUtils.formatDate(
                    DateUtils.addDay(DateUtils.parse(vo.getCanLoanTime()), 30), "yyyy-MM-dd HH:mm:ss"));
            approveFeedbackOP.setUpdatedAt(String.valueOf(new Date().getTime()));
            approveFeedbackOP.setTermType("1");
            try {
                String call = "Partner.Order.approveFeedback";
                String result = XJ360FQUtil.XianJinBaiKaRequest(approveFeedbackOP, call);
                ThirdResponse thirdResponse = JSON.parse(result, ThirdResponse.class);
                if (thirdResponse.getStatus().equals("1")) {
                    applyTripartiteService.insertTripartiteOrderNotice(vo.getApplyId(), vo.getOrderSn(), notice);
                    succNum++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult lendingFeedback() {
        TaskResult taskResult = new TaskResult();
        Page<ApproveFeedbackOP> page = new Page<ApproveFeedbackOP>();
        String notice = "lendingFeedback";
        page.setPageSize(100);
        page.setPageNo(1);
        ApplyThirdOP op = new ApplyThirdOP();
        Date now = new Date();
        Date startTime = DateUtils.addDay(new Date(), -30);// 精确到天
        Date endTime = new Date(now.getTime() - 3 * 60 * 1000);// 精确到分钟
        op.setApplyTimeStart(startTime);
        op.setApplyTimeEnd(endTime);
        op.setStatusList(Arrays.asList(XjdLifeCycle.LC_CASH_4));
        op.setNotice(notice);
        page = loanApplyService.getLoanApplyThirdList(page, op);
        List<ApproveFeedbackOP> list = page.getList();
        int succNum = 0;
        int count = 0;
        for (ApproveFeedbackOP vo : list) {
            try {
                Thread.sleep(2000);
                count++;
                ThirdResponse thirdResponse = lendingFeedback(vo.getOrderSn(), Global.XJBK_SUCCESS);
                if (thirdResponse.getStatus().equals("1")) {
                    applyTripartiteService.insertTripartiteOrderNotice(vo.getApplyId(), vo.getOrderSn(), notice);
                    succNum++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult xianJinCardCallback() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        Map<String, String> xianJinCardCallBack = JedisUtils.getMap(Global.REPAY_PLAN_FEEDBACK);
        if (xianJinCardCallBack != null) {
            List<Map.Entry<String, String>> callBackList = new ArrayList<>(xianJinCardCallBack.entrySet());
            Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    return map1.getValue().compareTo(map2.getValue());
                }
            });
            for (Map.Entry<String, String> map : callBackList) {
                if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 200) {
                        break;
                    }
                    count++;
                    String applyId = map.getKey();
                    String orderSn = applyTripartiteService.getThirdIdByApplyId(applyId);
                    if (StringUtils.isNotBlank(orderSn)) {
                        ThirdResponse thirdResponse = repayPlanFeedback(orderSn);
                        if (thirdResponse.getStatus().equals("1")) {
                            JedisUtils.mapRemove(Global.REPAY_PLAN_FEEDBACK, applyId);
                            succNum++;
                        }
                        if (thirdResponse.getStatus().equals("500") && thirdResponse.getMessage().contains("不属于该机构的订单")) {
                            JedisUtils.mapRemove(Global.REPAY_PLAN_FEEDBACK, applyId);
                            succNum++;
                        }
                    } else {
                        JedisUtils.mapRemove(Global.REPAY_PLAN_FEEDBACK, applyId);
                        succNum++;
                    }
                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult repayStatusFeedbackToRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        Map<String, String> repayFailFeedbackToRedis = JedisUtils.getMap(Global.REPAY_STATUS_FEEDBACK);
        if (repayFailFeedbackToRedis != null) {
            List<Map.Entry<String, String>> callBackList = new ArrayList<>(repayFailFeedbackToRedis.entrySet());
            Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                    CallbackOP callbackOP1 = (CallbackOP) JsonMapper.fromJsonString(map1.getValue(), CallbackOP.class);
                    CallbackOP callbackOP2 = (CallbackOP) JsonMapper.fromJsonString(map2.getValue(), CallbackOP.class);
                    return callbackOP1.getTime().compareTo(callbackOP2.getTime());
                }
            });
            for (Map.Entry<String, String> map : callBackList) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (count >= 200) {
                    break;
                }
                count++;
                String applyId = map.getKey();
                String orderSn = applyTripartiteService.getThirdIdByApplyId(applyId);
                if (orderSn != null) {
                    CallbackOP callbackOP = (CallbackOP) JsonMapper.fromJsonString(map.getValue(), CallbackOP.class);
                    if (callbackOP.getStatus() == Global.XJBK_SUCCESS) {
                        ThirdResponse thirdResponse = repayStatusFeedback(orderSn, Global.XJBK_SUCCESS, "ok");
                        if (thirdResponse.getStatus().equals("1")) {
                            JedisUtils.mapRemove(Global.REPAY_STATUS_FEEDBACK, applyId);
                            succNum++;
                        }
                    } else {
                        ThirdResponse thirdResponse = repayStatusFeedback(orderSn, Global.XJBK_FAIL, "");
                        if (thirdResponse.getStatus().equals("1")) {
                            JedisUtils.mapRemove(Global.REPAY_STATUS_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                } else {
                    JedisUtils.mapRemove(Global.REPAY_STATUS_FEEDBACK, applyId);
                    succNum++;
                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult repayPlanFeedback() {
        TaskResult taskResult = new TaskResult();
        Page<ApproveFeedbackOP> page = new Page<ApproveFeedbackOP>();
        String notice = "repayPlanFeedback";
        page.setPageSize(100);
        page.setPageNo(1);
        ApplyThirdOP op = new ApplyThirdOP();
        Date now = new Date();
        Date startTime = DateUtils.addDay(new Date(), -30);// 精确到天
        Date endTime = new Date(now.getTime() - 3 * 60 * 1000);// 精确到分钟
        op.setApplyTimeStart(startTime);
        op.setApplyTimeEnd(endTime);
        op.setStatusList(Arrays.asList(XjdLifeCycle.LC_CASH_4, XjdLifeCycle.LC_REPAY_0, XjdLifeCycle.LC_OVERDUE_0));
        op.setNotice(notice);
        page = loanApplyService.getLoanApplyThirdList(page, op);
        List<ApproveFeedbackOP> list = page.getList();
        int succNum = 0;
        int count = 0;
        for (ApproveFeedbackOP vo : list) {
            try {
                Thread.sleep(2000);

                String sum = JedisUtils.get("XJBK:repayPlanFeedback_" + vo.getApplyId());
                int sumData = sum == null ? 0 : (Integer.parseInt(sum) + 1);
                if (sumData == 0) {
                    count++;
                    ThirdResponse thirdResponse = repayPlanFeedback(vo.getOrderSn());
                    if (thirdResponse.getStatus().equals("1")) {
                        JedisUtils.set("XJBK:repayPlanFeedback_" + vo.getApplyId(), String.valueOf(sumData), 60 * 60);
                        succNum++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult repayStatusFeedback() {
        TaskResult taskResult = new TaskResult();
        Page<ApproveFeedbackOP> page = new Page<ApproveFeedbackOP>();
        String notice = "repayStatusFeedback";
        page.setPageSize(100);
        page.setPageNo(1);
        ApplyThirdOP op = new ApplyThirdOP();
        Date now = new Date();
        Date startTime = DateUtils.addDay(new Date(), -30);// 精确到天
        Date endTime = new Date(now.getTime() - 3 * 60 * 1000);// 精确到分钟
        op.setApplyTimeStart(startTime);
        op.setApplyTimeEnd(endTime);
        op.setStatusList(Arrays.asList(XjdLifeCycle.LC_REPAY_2, XjdLifeCycle.LC_REPAY_1, XjdLifeCycle.LC_OVERDUE_1));
        op.setNotice(notice);
        page = loanApplyService.getLoanApplyThirdList(page, op);
        List<ApproveFeedbackOP> list = page.getList();
        int succNum = 0;
        int count = 0;
        for (ApproveFeedbackOP vo : list) {
            try {
                Thread.sleep(2000);
                count++;

                ThirdResponse thirdResponse = repayStatusFeedback(vo.getOrderSn(), Global.XJBK_SUCCESS, "ok");
                if (thirdResponse.getStatus().equals("1")) {
                    applyTripartiteService.insertTripartiteOrderNotice(vo.getApplyId(), vo.getOrderSn(), notice);
                    succNum++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public XianJinBaiKaRepaymentPlan getRepayplan(String order_sn) {
        XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = new XianJinBaiKaRepaymentPlan();
        String applyId = applyTripartiteService.getApplyIdByThirdId(order_sn);
        LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        if (loanApplySimpleVO == null || loanApplySimpleVO.getProcessStatus() == null
                || loanApplySimpleVO.getProcessStatus().intValue() < XjdLifeCycle.LC_CASH_4) {
            return xianJinBaiKaRepaymentPlan;
        }
        LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);
        if (repayPlan == null) {
            return xianJinBaiKaRepaymentPlan;
        }
        xianJinBaiKaRepaymentPlan.setOrderSn(order_sn);
        xianJinBaiKaRepaymentPlan.setAlreadyPaid((repayPlan.getPayedPrincipal().add(repayPlan.getPayedInterest()))
                .multiply(new BigDecimal(100)).intValue());
        xianJinBaiKaRepaymentPlan.setFinishPeriod(repayPlan.getPayedTerm());
        xianJinBaiKaRepaymentPlan.setReceivedAmount((loanApplySimpleVO.getApproveAmt().subtract(loanApplySimpleVO
                .getApproveAmt().multiply(loanApplySimpleVO.getServFeeRate()))).multiply(new BigDecimal(100))
                .intValue());
        xianJinBaiKaRepaymentPlan.setTotalAmount(loanApplySimpleVO.getCurToltalRepayAmt().multiply(new BigDecimal(100))
                .intValue());
        xianJinBaiKaRepaymentPlan.setTotalPeriod(repayPlan.getTotalTerm());
        xianJinBaiKaRepaymentPlan.setTotalSvcFee(loanApplySimpleVO.getApproveAmt()
                .multiply(loanApplySimpleVO.getServFeeRate()).multiply(new BigDecimal(100)).intValue());
        RepayDetailListOP op = new RepayDetailListOP();
        op.setContNo(repayPlan.getContNo());
        op.setIsDelaySettlement(2);
        List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
        ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
        for (RepayDetailListVO repayDetailListVO : voList) {
            RepaymentPlan repaymentPlan = new RepaymentPlan();
            repaymentPlan.setTotalAmount(repayDetailListVO.getTotalAmount().multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setAlreadyPaid(repayDetailListVO.getActualRepayAmt() == null ? new BigDecimal(0).intValue()
                    : repayDetailListVO.getActualRepayAmt().multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setBillStatus(repayDetailListVO.getStatus());
            repaymentPlan.setCanPayTime((int) (repayDetailListVO.getStartDate().getTime() / 1000));
            repaymentPlan.setDueTime((int) (repayDetailListVO.getRepayDate().getTime() / 1000));
            repaymentPlan.setFinishPayTime(repayDetailListVO.getActualRepayTime() == null ? 0
                    : (int) (repayDetailListVO.getActualRepayTime().getTime() / 1000));
            repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                    : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setLoanTime(repayDetailListVO.getStartDate() == null ? 0 : (int) (repayDetailListVO
                    .getStartDate().getTime() / 1000));
            repaymentPlan.setOverdueDay(repayDetailListVO.getOverdue() == null ? 0 : repayDetailListVO.getOverdue());
            repaymentPlan.setPayType(repayDetailListVO.getStatus());
            repaymentPlan.setOverdueFee(repayDetailListVO.getOverdueFee() == null ? new BigDecimal(0).intValue()
                    : repayDetailListVO.getOverdueFee().multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setPeriodFeeDesc("");
            repaymentPlan.setServiceFee(repayDetailListVO.getPrincipal().multiply(loanApplySimpleVO.getServFeeRate())
                    .multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setPrinciple(repayDetailListVO.getPrincipal() == null ? new BigDecimal(0).intValue()
                    : repayDetailListVO.getPrincipal().multiply(new BigDecimal(100)).intValue());
            repaymentPlan.setPeriodNo(repayDetailListVO.getThisTerm().toString());
            list.add(repaymentPlan);
        }
        xianJinBaiKaRepaymentPlan.setRepaymentPlan(list);
        return xianJinBaiKaRepaymentPlan;
    }

    @Override
    public XianJinBaiKaVO applyRepay(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        Map<String, Object> map = new HashMap<>();
        String applyId = applyTripartiteService.getApplyIdByThirdId(xianJinBaiKaCommonRequest.getOrder_sn());
        LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        try {
            String loanDate = loanApplySimpleVO.getLoanDate();
            Date loan = DateUtils.parse(loanDate);
            long days = DateUtils.pastDays(loan);
            // if (days <= 1) {
            // map.put("repay_result", "503");
            // xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
            // xianJinBaiKaResponse.setMessage("借款至少持有一天方可还款");
            // xianJinBaiKaResponse.setResponse(map);
            // return xianJinBaiKaResponse;
            // }

            RePayOP rePayOP = new RePayOP();
            rePayOP.setApplyId(applyId);
            rePayOP.setUserId(loanApplySimpleVO.getUserId());
            rePayOP.setRepayPlanItemId(loanApplySimpleVO.getRepayPlanItemId());
            rePayOP.setTxAmt(loanApplySimpleVO.getCurToltalRepayAmt().toString());
            rePayOP.setPayType(1);
            rePayOP.setPrePayFlag(RePayOP.PREPAY_FLAG_NO);
            rePayOP.setSource(loanApplySimpleVO.getSource());
            rePayOP.setIp(loanApplySimpleVO.getIp());
            /*
             * WithholdResultVO withholdResultVO =
			 * withholdService.withholdXianJinBaiKa(loanApplySimpleVO
			 * .getRepayPlanItemId());
			 */
            String orderSn = xianJinBaiKaCommonRequest.getOrder_sn();
            String userAgreementPayLockCacheKey = "XJBK:pay_lock_" + orderSn;
            synchronized (XianJinBaiKaServiceImpl.class) {
                String userAgreementPayLock = JedisUtils.get(userAgreementPayLockCacheKey);
                if (userAgreementPayLock == null) {
                    // 加锁，防止并发
                    JedisUtils.set(userAgreementPayLockCacheKey, "locked", 30);
                } else {
                    logger.warn("协议直接支付接口调用中，lockId= {}", orderSn);
                    repayPlanFeedback(xianJinBaiKaCommonRequest.getOrder_sn());
                    map.put("repay_result", "100");
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
            }

            ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPay(rePayOP);
            if (confirmAuthPayVO.isSuccess()) {
                repayStatusFeedback(xianJinBaiKaCommonRequest.getOrder_sn(), Global.XJBK_SUCCESS, "ok");
                repayPlanFeedback(xianJinBaiKaCommonRequest.getOrder_sn());
                XianJinCardUtils.setRepayPlanFeedbackToRedis(applyId);
                map.put("repay_result", "200");
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(map);
                return xianJinBaiKaResponse;
            }
            if ("I".equals(confirmAuthPayVO.getStatus())) {
                repayPlanFeedback(xianJinBaiKaCommonRequest.getOrder_sn());
                map.put("repay_result", "100");
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(map);
                return xianJinBaiKaResponse;
            } else {
                repayStatusFeedback(xianJinBaiKaCommonRequest.getOrder_sn(), Global.XJBK_FAIL,
                        confirmAuthPayVO.getMsg());
                repayPlanFeedback(xianJinBaiKaCommonRequest.getOrder_sn());
                map.put("repay_result", "503");
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(confirmAuthPayVO.getMsg());
                xianJinBaiKaResponse.setResponse(map);
                return xianJinBaiKaResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO isUserAccept(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        logger.info("现金白卡准入开始");
        try {
            IsUserAcceptVO isUserAcceptVO = new IsUserAcceptVO();
            Map<String, Object> map = new HashMap<>();
            String userName = xianJinBaiKaCommonRequest.getUser_name();
            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
            String userIdCard = xianJinBaiKaCommonRequest.getUser_idcard();
            userIdCard = userIdCard.replace("*", "%");
            userPhone = userPhone.replace("*", "%");
            logger.info("现金白卡用户过滤:{},{},{}", userName, userPhone, userIdCard);
            // 聚宝自有黑名单
            boolean isBlackUser = riskBlacklistService.inBlackList(userName, userPhone, userIdCard);
            if (isBlackUser) {
                logger.info("现金白卡用户过滤：自有黑名单,{}", userName);
                map.put("result", 403);
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage("用户在机构有不良借款记录");
                xianJinBaiKaResponse.setResponse(map);
                return xianJinBaiKaResponse;
            }
            CustUserVO custUserVO = custUserService.isRegister(userName, userPhone, userIdCard);
            if (custUserVO != null) {
                String userId = custUserVO.getId();
                long registDays = DateUtils.pastDays(custUserVO.getRegisterTime());
                if (custUserVO.getChannel() != null && !custUserVO.getChannel().equals("XJBK") && registDays <= 7) {
                    logger.info("现金白卡用户过滤：未完结订单,{}", userName);
                    map.put("result", 401);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有未完成的借款");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
                boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
                if (isExist) {
                    logger.info("现金白卡用户过滤：未完结订单,{}", userName);
                    map.put("result", 401);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有未完成的借款");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
                // 被拒日期距今不满30天
                LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
                if (null != lastApply) {
                    if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                            || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                        Date lastUpdateTime = lastApply.getUpdateTime();
                        long pastDays = DateUtils.pastDays(lastUpdateTime);
                        if (pastDays < Global.MIN_XJD_APPLY_DAY) {
                            logger.info("现金白卡用户过滤：被拒日期距今不满30天,{}", userName);
                            map.put("result", 403);
                            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                            xianJinBaiKaResponse.setMessage("用户在机构有不良借款记录");
                            xianJinBaiKaResponse.setResponse(map);
                            return xianJinBaiKaResponse;
                        }
                    }
                }
                // 在本平台有15天以上逾期记录
                int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
                if (maxOverdueDays > 15) {
                    logger.info("现金白卡用户过滤：本平台逾期15天以上,{}", userName);
                    map.put("result", 403);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有不良借款记录");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
            }
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
            xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
            isUserAcceptVO.setResult(200);
            isUserAcceptVO.setAmount(3000 * 100);
            // isUserAcceptVO.setTerms(14);
            isUserAcceptVO.setTermType(1);
            isUserAcceptVO.setLoanMode(0);
            xianJinBaiKaResponse.setResponse(isUserAcceptVO);
            logger.info("现金白卡准入结束");
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO loanCalculate(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        logger.info("现金白卡试算开始");
        try {
            LoanCalculate loanCalculateCache = getLoanCalculate(String.valueOf(xianJinBaiKaCommonRequest
                    .getLoan_amount()));
            if (loanCalculateCache == null) {
                LoanCalculate loanCalculate = new LoanCalculate();
                Map<String, Object> map = new HashMap<String, Object>();
                BigDecimal loan_amount = xianJinBaiKaCommonRequest.getLoan_amount();

                PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
                promotionCaseOP.setApplyAmt(loan_amount.divide(new BigDecimal(100)));
                promotionCaseOP.setApplyTerm(xianJinBaiKaCommonRequest.getLoan_term());
                promotionCaseOP.setProductId("XJD");
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
                if (null != promotionCase) {
                    BigDecimal discountVal = promotionCase.getDiscountValue();
                    BigDecimal dayInterest = CostUtils.calActualDayInterest(promotionCase.getRatePerDay(), loan_amount,
                            discountVal);
                    List<RepayPlan> repayPlanList = new ArrayList<>();
                    RepayPlan repayPlan = new RepayPlan();
                    repayPlan.setRepayAmount(loan_amount.add(CostUtils.calTotalInterest(dayInterest, 14)));
                    repayPlan.setPeriodNo(1);
                    repayPlan.setRepayAmountDesc("本金"
                            + loan_amount.divide(new BigDecimal(100))
                            + "元,利息"
                            + CostUtils.calTotalInterest(dayInterest, 14).divide(new BigDecimal(100))
                            + "元,服务费"
                            + CostUtils.calServFee(promotionCase.getServFeeRate(), loan_amount).divide(
                            new BigDecimal(100)) + "元");
                    repayPlanList.add(repayPlan);

                    loanCalculate.setServiceFee(CostUtils.calServFee(promotionCase.getServFeeRate(), loan_amount));
                    loanCalculate.setServiceFeeDesc("包含"
                            + CostUtils.calServFee(promotionCase.getServFeeRate(), loan_amount).divide(
                            new BigDecimal(100)) + "元的风控服务费");
                    loanCalculate.setReceiveAmount(loan_amount.subtract(CostUtils.calServFee(
                            promotionCase.getServFeeRate(), loan_amount)));
                    loanCalculate.setInterestFee(CostUtils.calTotalInterest(dayInterest, 14));
                    loanCalculate.setRepayAmount(loan_amount.add(CostUtils.calTotalInterest(dayInterest, 14)));
                    loanCalculate.setRepayPlan(repayPlanList);
                    cacheLoanCalculate(loanCalculate, String.valueOf(xianJinBaiKaCommonRequest.getLoan_amount()));
                    logger.info("现金白卡试算结束");
                }
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(loanCalculate);
            } else {
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(loanCalculateCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO applyBindCard(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        ApplyBindCardVO applyBindCardVO = new ApplyBindCardVO();
        String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
        String userId = registerOrReturnUserId(userPhone);
        logger.info("开始执行绑卡:{}", userPhone);
        String verify_code = xianJinBaiKaCommonRequest.getVerify_code();
        try {
            String ip = "127.0.0.1";
            DirectBindCardOP bindCardOP = new DirectBindCardOP();
            bindCardOP.setCardNo(xianJinBaiKaCommonRequest.getCard_number());
            bindCardOP.setBankCode(xianJinBaiKaCommonRequest.getBank_code());
            bindCardOP.setRealName(xianJinBaiKaCommonRequest.getUser_name());
            bindCardOP.setIdNo(xianJinBaiKaCommonRequest.getUser_idcard());
            bindCardOP.setMobile(xianJinBaiKaCommonRequest.getCard_phone());
            bindCardOP.setIpAddr(ip);
            bindCardOP.setUserId(userId);
            bindCardOP.setSource("4");
            if (verify_code == null) {
                BindCardResultVO bindCardResult = baofooAgreementPayService.agreementPreBind(bindCardOP);
                if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getBindId())) {
                    applyBindCardVO.setBindStatus("505");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                            || "BF00127".equals(bindCardResult.getCode())) {
                        applyBindCardVO.setRemark(bindCardResult.getMsg());
                    } else {
                        applyBindCardVO.setRemark(bindCardResult.getMsg());
                    }
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    return xianJinBaiKaResponse;
                } else {
                    JedisUtils.set("XJBK:bindCardBindId_" + userId, bindCardResult.getBindId(), 60 * 3);
                    JedisUtils.set("XJBK:bindCardOrderNo_" + userId, bindCardResult.getOrderNo(), 60 * 3);
                    applyBindCardVO.setBindStatus("100");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                }
            } else {
                ConfirmBindCardOP confirmBindCardOP = new ConfirmBindCardOP();
                confirmBindCardOP.setUserId(userId);
                confirmBindCardOP.setMsgVerCode(xianJinBaiKaCommonRequest.getVerify_code());
                String bindId = JedisUtils.get("XJBK:bindCardBindId_" + userId);
                String orderNo = JedisUtils.get("XJBK:bindCardOrderNo_" + userId);
                confirmBindCardOP.setBindId(bindId);
                confirmBindCardOP.setOrderNo(orderNo);
                confirmBindCardOP.setSource("4");
                confirmBindCardOP.setType(1);
                BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(confirmBindCardOP);
                if (!bindCardResult.isSuccess()) {
                    applyBindCardVO.setBindStatus("505");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                            || "BF00127".equals(bindCardResult.getCode())) {
                        applyBindCardVO.setRemark(bindCardResult.getMsg());
                    } else {
                        applyBindCardVO.setRemark(bindCardResult.getMsg());
                    }
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    return xianJinBaiKaResponse;
                }
                if (StringUtils.isBlank(bindCardResult.getBindId())) {
                    applyBindCardVO.setBindStatus("505");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    return xianJinBaiKaResponse;
                }
                BindCardVO bindInfo = bindCardService.findByOrderNo(orderNo);
                if (bindInfo == null) {
                    applyBindCardVO.setBindStatus("505");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    return xianJinBaiKaResponse;
                }
                bindInfo.setBindId(bindCardResult.getBindId());
                bindInfo.setStatus(bindCardResult.getCode());
                bindInfo.setRemark(bindCardResult.getMsg());
                bindInfo.setChlName("宝付确认绑卡");
                bindInfo.setSource("4");
                bindInfo.setIp("127.0.0.1");
                int saveBc = bindCardService.update(bindInfo);
                if (saveBc == 0) {
                    applyBindCardVO.setBindStatus("505");
                    applyBindCardVO.setRemark(bindCardResult.getMsg());
                    xianJinBaiKaResponse.setResponse(applyBindCardVO);
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                    return xianJinBaiKaResponse;
                }
                if (bindCardResult.isSuccess()) {
                    IdentityInfoOP identityInfoOP = new IdentityInfoOP();
                    identityInfoOP.setTrueName(bindInfo.getName());
                    identityInfoOP.setCardNo(bindInfo.getCardNo());
                    identityInfoOP.setIdNo(bindInfo.getIdNo());
                    identityInfoOP.setProtocolNo(bindCardResult.getBindId());
                    identityInfoOP.setUserId(userId);
                    identityInfoOP.setSource("4");
                    identityInfoOP.setBankCode(bindCardResult.getBankCode());
                    identityInfoOP.setProductId("XJD");
                    identityInfoOP.setAccount(bindInfo.getMobile());
                    identityInfoOP.setBankMobile(xianJinBaiKaCommonRequest.getCard_phone());
                    int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
                    if (saveRz == 1) {
                        XJ360Util.cleanCustUserInfoCache(userId);
                        applyBindCardVO.setBindStatus("200");
                        applyBindCardVO.setRemark(Global.XJBK_SUCCESS);
                        xianJinBaiKaResponse.setResponse(applyBindCardVO);
                        xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                        xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                        return xianJinBaiKaResponse;
                    } else {
                        applyBindCardVO.setBindStatus("505");
                        applyBindCardVO.setRemark(bindCardResult.getMsg());
                        xianJinBaiKaResponse.setResponse(applyBindCardVO);
                        xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                        xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                        return xianJinBaiKaResponse;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        // BindCardResultVO bindCardResult =
        // baofooAuthPayService.directBindCard(bindCardOP);
        // bindCardResult.setSuccess(bindCardResult.isSuccess());
        // bindCardResult.setBindId(bindCardResult.getBindId());
        // if (bindCardResult.isSuccess()) {
        // IdentityInfoOP identityInfoOP = new IdentityInfoOP();
        // identityInfoOP.setBindId(bindCardResult.getBindId());
        // identityInfoOP.setCardNo(xianJinBaiKaCommonRequest.getCard_number());
        // identityInfoOP.setBankCode(xianJinBaiKaCommonRequest.getBank_code());
        // identityInfoOP.setTrueName(xianJinBaiKaCommonRequest.getUser_name());
        // identityInfoOP.setIdNo(xianJinBaiKaCommonRequest.getUser_idcard());
        // identityInfoOP.setBankMobile(xianJinBaiKaCommonRequest.getCard_phone());
        // identityInfoOP.setUserId(userId);
        // int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
        // if (saveRz == 1) {
        // applyBindCardVO.setBindStatus("200");
        // applyBindCardVO.setRemark(Global.XJBK_SUCCESS);
        // xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
        // xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
        // xianJinBaiKaResponse.setResponse(applyBindCardVO);
        // }
        // } else {
        // applyBindCardVO.setBindStatus("401");
        // applyBindCardVO.setRemark(bindCardResult.getMsg());
        // xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
        // xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
        // xianJinBaiKaResponse.setResponse(applyBindCardVO);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
        // xianJinBaiKaResponse.setMessage("请求失败");
        // }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO confirmLoan(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        try {
            if (xianJinBaiKaCommonRequest.getConfirm_result().equals("200")) {
                String applyId = applyTripartiteService.getApplyIdByThirdId(xianJinBaiKaCommonRequest.getOrder_sn());
                LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
                CustUserVO custUserVO = custUserService.getCustUserById(loanApplySimpleVO.getUserId());
                if (custUserVO.getCardNo() == null || custUserVO.getCardNo().length() < 1) {
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
                    xianJinBaiKaResponse.setMessage(Global.XJBK_FAIL);
                    xianJinBaiKaResponse.setResponse(false);
                    return xianJinBaiKaResponse;
                }
                Integer status = loanApplySimpleVO.getProcessStatus();
                if (!status.equals(XjdLifeCycle.LC_CASH_4) && !status.equals(XjdLifeCycle.LC_CHANNEL_0)) {
                    contractService.processAdminLendPay(applyId, new Date(), true);
                    XianJinCardUtils.setRepayPlanFeedbackToRedis(applyId);
                }
            } else {
                logger.info("取消借款三方单号：{}", xianJinBaiKaCommonRequest.getOrder_sn());
            }
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
            xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
            xianJinBaiKaResponse.setResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    public ThirdResponse lendingFeedback(String orderSn, String status) {
        ThirdResponse thirdResponse = new ThirdResponse();
        LendingFeedback lendingFeedback = new LendingFeedback();
        lendingFeedback.setOrderSn(orderSn);
        lendingFeedback.setUpdatedAt(String.valueOf(new Date().getTime()));
        try {
            if (status.equals(Global.XJBK_SUCCESS)) {
                lendingFeedback.setFailReason("ok");
                lendingFeedback.setLendingStatus("200");
            }
            if (status.equals(Global.XJBK_FAIL)) {
                lendingFeedback.setFailReason(Global.XJBK_FAIL);
                lendingFeedback.setLendingStatus("401");
            }
            String call = "Partner.Order.lendingFeedback";
            String result = XJ360FQUtil.XianJinBaiKaRequest(lendingFeedback, call);
            thirdResponse = JSON.parse(result, ThirdResponse.class);
            logger.info("工单 {} 放款状态回调结果：{}", orderSn, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thirdResponse;
    }

    public ThirdResponse repayStatusFeedback(String orderSn, String status, String msg) {
        ThirdResponse thirdResponse = new ThirdResponse();
        try {
            RepayStatusFeedback repayStatusFeedback = new RepayStatusFeedback();
            repayStatusFeedback.setOrderSn(orderSn);
            repayStatusFeedback.setUpdatedAt(String.valueOf(new Date().getTime()));
            if (status.equals(Global.XJBK_SUCCESS)) {
                repayStatusFeedback.setFailReason(msg);
                repayStatusFeedback.setRepayResult("200");
            }
            if (status.equals(Global.XJBK_FAIL)) {
                repayStatusFeedback.setFailReason(msg);
                repayStatusFeedback.setRepayResult("505");
            }
            String call = "Partner.Order.repayStatusFeedback";
            String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            String result = null;
            if (applyAllotVO.getApproveTerm().equals(90) || applyAllotVO.getApproveTerm().equals(28)) {
                result = XJ360FQUtil.XianJinBaiKaRequest(repayStatusFeedback, call);
            } else {
                result = XJ360Util.XianJinBaiKaRequest(repayStatusFeedback, call);
            }
            thirdResponse = JSON.parse(result, ThirdResponse.class);
            logger.info("工单 {} 还款状态回调结果：{}", orderSn, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thirdResponse;
    }

    public ThirdResponse repayPlanFeedback(String orderSn) {
        ThirdResponse thirdResponse = new ThirdResponse();
        try {
            String call = "Partner.Order.repayPlanFeedback";
            String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            String result = null;
            if (applyAllotVO.getApproveTerm().equals(90) || applyAllotVO.getApproveTerm().equals(28)) {
                XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = getRepayplanFQ(orderSn);
                if (xianJinBaiKaRepaymentPlan != null) {
                    result = XJ360FQUtil.XianJinBaiKaRequest(xianJinBaiKaRepaymentPlan, call);
                }
            } else {
                XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = getRepayplan(orderSn);
                if (xianJinBaiKaRepaymentPlan != null) {
                    result = XJ360Util.XianJinBaiKaRequest(xianJinBaiKaRepaymentPlan, call);
                }
            }
            thirdResponse = JSON.parse(result, ThirdResponse.class);
            logger.info("工单 {} 还款计划回调结果：{}", orderSn, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thirdResponse;
    }

    @Override
    public XianJinBaiKaVO getOrderStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
        xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
        Integer actType = xianJinBaiKaCommonRequest.getAct_type();
        String orderSn = xianJinBaiKaCommonRequest.getOrder_sn();
        String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
        logger.info("拉取工单状态工单号为{},{}", orderSn, applyId);
        if (applyId != null) {
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            Integer status = loanApplySimpleVO.getProcessStatus();
            switch (actType) {
                case 1:
                    ApproveResult approveResult = new ApproveResult();
                    approveResult.setOrderSn(orderSn);
                    approveResult.setResultType(actType);
                    if ((status <= XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3 && status >= XjdLifeCycle.LC_APPLY_1)
                            && (status != XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2 && status != XjdLifeCycle.LC_AUTO_AUDIT_2)
                            && (status != XjdLifeCycle.LC_ARTIFICIAL_AUDIT_1 && status != XjdLifeCycle.LC_AUTO_AUDIT_1)) {
                        approveResult.setApproveStatus("100");
                        approveResult.setApproveRemark("ok");
                        approveResult.setApproveAmount("");
                        approveResult.setApproveTerm("");
                        approveResult.setApproveTime("");
                        approveResult.setTermType("");
                    }
                    if (status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2 || status == XjdLifeCycle.LC_AUTO_AUDIT_2) {
                        approveResult.setApproveStatus("403");
                        approveResult.setApproveRemark("评分不足");
                        approveResult.setApproveAmount("");
                        approveResult.setApproveTerm("");
                        approveResult.setApproveTime("");
                        approveResult.setTermType("");
                    }
                    if (status >= XjdLifeCycle.LC_RAISE_0) {
                        approveResult.setApproveStatus("200");
                        approveResult.setApproveRemark("ok");
                        approveResult.setApproveAmount(String.valueOf(loanApplySimpleVO.getApproveAmt()
                                .multiply(new BigDecimal(100)).intValue()));
                        approveResult.setApproveTerm(String.valueOf(loanApplySimpleVO.getApproveTerm()));
                        approveResult.setApproveTime(String.valueOf(new Date().getTime() / 1000 - 100));
                        approveResult.setTermType("1");
                    }
                    xianJinBaiKaResponse.setResponse(approveResult);
                    break;
                case 2:
                    ConfirmResult confirmResult = new ConfirmResult();
                    confirmResult.setOrderSn(orderSn);
                    confirmResult.setResultType(actType);
                    if (status == XjdLifeCycle.LC_RAISE_0) {
                        confirmResult.setConfirmStatus("100");
                        confirmResult.setConfirmRemark("ok");
                        confirmResult.setConfirmAmount("");
                        confirmResult.setConfirmTerm("");
                        confirmResult.setConfirmTime("");
                        confirmResult.setTermType("");
                    }
                    if (status == XjdLifeCycle.LC_CHANNEL_0) {
                        confirmResult.setConfirmStatus("402");
                        confirmResult.setConfirmRemark("客户主动取消订单");
                        confirmResult.setConfirmAmount("");
                        confirmResult.setConfirmTerm("");
                        confirmResult.setConfirmTime("");
                        confirmResult.setTermType("");
                    }
                    if (status <= XjdLifeCycle.LC_CASH_4 && status > XjdLifeCycle.LC_LENDERS_0) {
                        confirmResult.setConfirmStatus("200");
                        confirmResult.setConfirmRemark("ok");
                        confirmResult.setConfirmAmount(String.valueOf(loanApplySimpleVO.getApproveAmt()
                                .multiply(new BigDecimal(100)).intValue()));
                        confirmResult.setConfirmTerm(String.valueOf(loanApplySimpleVO.getApproveTerm()));
                        confirmResult.setConfirmTime(String.valueOf(new Date().getTime() / 1000));
                        confirmResult.setTermType("1");
                    }
                    xianJinBaiKaResponse.setResponse(confirmResult);
                    break;
                case 3:
                    LendingResult lendingResult = new LendingResult();
                    lendingResult.setOrderSn(orderSn);
                    lendingResult.setResultType(actType);
                    if (status <= XjdLifeCycle.LC_LENDERS_0 && status != XjdLifeCycle.LC_CHANNEL_0) {
                        lendingResult.setLendingStatus("100");
                        lendingResult.setLendingRemark("ok");
                        lendingResult.setLendingAmount("");
                        lendingResult.setLendingTerm("");
                        lendingResult.setLendingTime("");
                        lendingResult.setTermType("");
                    }
                    if (status == XjdLifeCycle.LC_CASH_5) {
                        lendingResult.setLendingStatus("401");
                        lendingResult.setLendingRemark("信息错误");
                        lendingResult.setLendingAmount("");
                        lendingResult.setLendingTerm("");
                        lendingResult.setLendingTime("");
                        lendingResult.setTermType("");
                    }
                    if (status <= XjdLifeCycle.LC_CASH_4 && status > XjdLifeCycle.LC_LENDERS_0) {
                        lendingResult.setLendingStatus("200");
                        lendingResult.setLendingRemark("ok");
                        lendingResult.setLendingAmount(String.valueOf(loanApplySimpleVO.getApproveAmt()
                                .multiply(new BigDecimal(100)).intValue()));
                        lendingResult.setLendingTerm(String.valueOf(loanApplySimpleVO.getApproveTerm()));
                        lendingResult.setLendingTime(String.valueOf(new Date().getTime() / 1000));
                        lendingResult.setTermType("1");
                    }
                    xianJinBaiKaResponse.setResponse(lendingResult);
                    break;
                default:
                    break;
            }
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO getContracts(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
        xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);

        String orderSn = xianJinBaiKaCommonRequest.getOrder_sn();
        String applyId = applyTripartiteService.getApplyIdByThirdId(orderSn);
        String userId = "";
        BigDecimal approveAmt = new BigDecimal(3000);
        Integer applyTerm = 14;
        if (applyId != null) {
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            userId = loanApplySimpleVO.getUserId();
            approveAmt = loanApplySimpleVO.getApproveAmt();
            applyTerm = loanApplySimpleVO.getApplyTerm();
        }
        List<Contracts> contractsList = new ArrayList<>();
        Contracts contracts = new Contracts();
        contracts.setName("聚宝钱包贷款合同");
        Map<String, String> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("applyAmt", String.valueOf(approveAmt));
        map.put("applyTerm", String.valueOf(applyTerm));
        map.put("applyId", applyId);
        map.put("productId", "XJD");
        String link = XJ360Util.getRqstUrl("https://api.jubaoqiandai.com/#/agreement7?", map);
        logger.info("协议地址：{}", link);
        contracts.setLink(link);
        contractsList.add(contracts);
        xianJinBaiKaResponse.setResponse(contractsList);
        return xianJinBaiKaResponse;
    }

    public static FileVO uploadBase64Image(String base64Image, UploadParams params) {
        // 图片上传
        FileServerClient fileServerClient = new FileServerClient();
        String rz = fileServerClient.uploadBase64Image(base64Image, params);
        FileResult obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null == obj || !StringUtils.equals(obj.getCode(), "SUCCESS")) {
            rz = fileServerClient.uploadBase64Image(base64Image, params);
        }
        obj = (FileResult) JsonMapper.fromJsonString(rz, FileResult.class);
        if (null != obj && StringUtils.equals(obj.getCode(), "SUCCESS") && null != obj.getData()) {
            return obj.getData();
        }
        return null;
    }

    public static LoanCalculate getLoanCalculate(String s) {
        if (StringUtils.isNotBlank(s)) {
            return (LoanCalculate) JedisUtils.getObject("XJBK:LoanCalculate_" + s);
        }
        return null;
    }

    public static LoanCalculate getLoanCalculateFQ(String s) {
        if (StringUtils.isNotBlank(s)) {
            return (LoanCalculate) JedisUtils.getObject("XJBK:LoanCalculateFQ_" + s);
        }
        return null;
    }

    public static LoanCalculate cacheLoanCalculate(LoanCalculate loanCalculate, String s) {
        if (null != loanCalculate) {
            JedisUtils.setObject("XJBK:LoanCalculate_" + s, loanCalculate, 60 * 60 * 24 * 30);
        } else {
            logger.error("试算数据不存在");
        }
        return loanCalculate;
    }

    public static LoanCalculate cacheLoanCalculateFQ(LoanCalculate loanCalculate, String s) {
        if (null != loanCalculate) {
            JedisUtils.setObject("XJBK:LoanCalculateFQ_" + s, loanCalculate, 60 * 60 * 24 * 30);
        } else {
            logger.error("试算数据不存在");
        }
        return loanCalculate;
    }

    private boolean isRespSuccess(String status) {
        return StringUtils.isNotBlank(status) && (status.equals("0000") || status.equals("BF00114"));
    }

    private boolean isRespUnsolved(String status) {
        if (StringUtils.isBlank(status)) {
            return false;
        }
        String[] statusArr = {"BF00100", "BF00112", "BF00113", "BF00115", "BF00144", "BF00202"};
        for (String str : statusArr) {
            if (status.equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public XianJinBaiKaCommonOP getXianJinBaiKaBase(String userId) {
        String cacheKey = "XJBK:BASE_" + userId;
        XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            FileInfoVO fileInfoVO = custUserService.getLastXianJinCardBaseByUserId(userId);
            if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户基础信息", fileInfoVO.getUrl());
                vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                        XianJinBaiKaCommonOP.class);
                if (vo != null) {
                    JedisUtils.setObject(cacheKey, vo, 60 * 5);
                }
            }
        }
        return vo;
    }

    @Override
    public XianJinBaiKaCommonOP getXianJinBaiKaAdditional(String userId) {
        String cacheKey = "XJBK:ADDITIONAL_" + userId;
        XianJinBaiKaCommonOP vo = (XianJinBaiKaCommonOP) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            FileInfoVO fileInfoVO = custUserService.getLastXianJinCardAdditionalByUserId(userId);
            if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                logger.info("{}-{}-请求地址：{}", "现金白卡", "从文件获取用户附加信息", fileInfoVO.getUrl());
                vo = (XianJinBaiKaCommonOP) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                        XianJinBaiKaCommonOP.class);
                if (vo != null) {
                    JedisUtils.setObject(cacheKey, vo, 60 * 5);
                }
            }
        }
        return vo;
    }

    public static long cleanCustUserInfoCache(String userId) {
        long result = 0;
        if (StringUtils.isNotBlank(userId)) {
            // 缓存用户信息
            result = JedisUtils.delObject(Global.USER_CACHE_PREFIX + userId);
        } else {
            logger.error("用户id为空");
        }
        return result;
    }

    @Override
    public void updateUserInfo(String orderSn, String applyId) {
        XianJinBaiKaCommonOP base = getPushBaseData(orderSn);
        XianJinBaiKaCommonOP additional = getPushAdditionalData(orderSn);
        if (base != null && additional != null) {
            try {
                String userPhone = base.getUser_info().getUserPhone();
                String userId = registerOrReturnUserId(userPhone);
                saveDoOcr(base, userId);
                saveBaseInfo(base, additional, userId, "XJBK");
            } catch (Exception e) {

            }
        }
    }

    @Override
    public XianJinBaiKaVO isUserAcceptFQ(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        logger.info("现金白卡准入开始");
        String xjbkfq_limit_day = configService.getValue("xjbkfq_limit_day");
        int xjbkfqLimitDay = StringUtils.isBlank(xjbkfq_limit_day) ? 60 : Integer.parseInt(xjbkfq_limit_day);
        try {
            IsUserAcceptVO isUserAcceptVO = new IsUserAcceptVO();
            Map<String, Object> map = new HashMap<>();
            String userName = xianJinBaiKaCommonRequest.getUser_name();
            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
            String userIdCard = xianJinBaiKaCommonRequest.getUser_idcard();
            userIdCard = userIdCard.replace("*", "%");
            userPhone = userPhone.replace("*", "%");
            logger.info("现金白卡用户过滤:{},{},{}", userName, userPhone, userIdCard);
            // 聚宝自有黑名单
            boolean isBlackUser = riskBlacklistService.inBlackList(userName, userPhone, userIdCard);
            if (isBlackUser) {
                logger.info("现金白卡用户过滤：自有黑名单,{}", userName);
                map.put("result", 403);
                map.put("can_loan_time", DateUtils.formatDate(DateUtils.addDay(new Date(), 365)));
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage("用户在机构有不良借款记录");
                xianJinBaiKaResponse.setResponse(map);
                return xianJinBaiKaResponse;
            }
            CustUserVO custUserVO = custUserService.isRegister(userName, userPhone, userIdCard);
            if (custUserVO != null) {
                String userId = custUserVO.getId();
                long registDays = DateUtils.pastDays(custUserVO.getRegisterTime());
                if (custUserVO.getChannel() != null && !custUserVO.getChannel().equals("XJBK") && registDays <= 7) {
                    logger.info("现金白卡用户过滤：未完结订单,{}", userName);
                    map.put("result", 401);
                    map.put("can_loan_time", DateUtils.formatDate(DateUtils.addDay(new Date(), xjbkfqLimitDay)));
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有未完成的借款");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
                boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
                if (isExist) {
                    logger.info("现金白卡用户过滤：未完结订单,{}", userName);
                    map.put("result", 401);
                    map.put("can_loan_time", DateUtils.formatDate(DateUtils.addDay(new Date(), xjbkfqLimitDay)));
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有未完成的借款");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
                // 被拒日期距今不满60天
                LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
                if (null != lastApply) {
                    if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                            || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                        Date lastUpdateTime = lastApply.getUpdateTime();
                        long pastDays = DateUtils.pastDays(lastUpdateTime);

                        if (pastDays < xjbkfqLimitDay) {
                            String canLoanTime = DateUtils.formatDate(DateUtils.addDay(lastUpdateTime,
                                    xjbkfqLimitDay + 1));
                            logger.info("现金白卡用户过滤：被拒日期距今不满{}天,{},{},{}", xjbkfqLimitDay, userName, pastDays,
                                    canLoanTime);
                            map.put("result", 402);
                            map.put("can_loan_time", canLoanTime);
                            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                            xianJinBaiKaResponse.setMessage("用户在机构被拒过");
                            xianJinBaiKaResponse.setResponse(map);
                            return xianJinBaiKaResponse;
                        }
                    }
                }
                // 在本平台有15天以上逾期记录
                int maxOverdueDays = overdueService.getMaxOverdueDays(userId);
                if (maxOverdueDays > 15) {
                    logger.info("现金白卡用户过滤：本平台逾期15天以上,{}", userName);
                    map.put("result", 403);
                    map.put("can_loan_time", DateUtils.formatDate(DateUtils.addDay(new Date(), 365)));
                    xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                    xianJinBaiKaResponse.setMessage("用户在机构有不良借款记录");
                    xianJinBaiKaResponse.setResponse(map);
                    return xianJinBaiKaResponse;
                }
            }
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
            xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
            List<Integer> list = new ArrayList<>();
            list.add(28);
            // list.add(30);
            isUserAcceptVO.setResult(200);
            isUserAcceptVO.setAmount(3000 * 100);
            // isUserAcceptVO.setMinAmount(2000 * 100);
            isUserAcceptVO.setTerms(list);
            isUserAcceptVO.setTermType(1);
            isUserAcceptVO.setLoanMode(0);
            xianJinBaiKaResponse.setResponse(isUserAcceptVO);
            logger.info("现金白卡准入结束");
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaVO loanCalculateFQ(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        logger.info("现金白卡试算开始");
        try {
            LoanCalculate loanCalculateCache = getLoanCalculateFQ(String.valueOf(xianJinBaiKaCommonRequest
                    .getLoan_amount()));
            if (loanCalculateCache == null) {
                LoanCalculate loanCalculate = new LoanCalculate();
                BigDecimal loan_amount = xianJinBaiKaCommonRequest.getLoan_amount();

                PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
                promotionCaseOP.setApplyAmt(loan_amount.divide(new BigDecimal(100)));
                promotionCaseOP.setApplyTerm(28);
                promotionCaseOP.setProductId("XJD");
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
                if (null != promotionCase) {

                    RepayPlanOP repayPlanOP = new RepayPlanOP();
                    repayPlanOP.setApplyAmt(promotionCaseOP.getApplyAmt());
                    repayPlanOP.setChannelId(promotionCaseOP.getChannelId());
                    repayPlanOP.setProductId(promotionCaseOP.getProductId());
                    repayPlanOP.setRepayTerm(promotionCaseOP.getApplyTerm());
                    repayPlanOP.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue());
                    Map<String, Object> map1 = loanRepayPlanService.getRepayPlan(repayPlanOP);

                    BigDecimal totalAmt = ((BigDecimal) map1.get("totalAmt")).multiply(new BigDecimal(100));
                    BigDecimal totalInterest = ((BigDecimal) map1.get("totalInterest")).multiply(new BigDecimal(100));
                    List<Map<String, Object>> mapList = (List<Map<String, Object>>) map1.get("list");
                    List<RepayPlan> repayPlanList = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        Map<String, Object> map = mapList.get(i);
                        BigDecimal repayAmt = ((BigDecimal) map.get("repayAmt")).multiply(new BigDecimal(100));
                        RepayPlan repayPlan = new RepayPlan();
                        repayPlan.setRepayAmount(repayAmt);
                        repayPlan.setPeriodNo(i + 1);
                        repayPlan.setRepayAmountDesc("本金625元，利息17元");
                        repayPlanList.add(repayPlan);
                    }

                    loanCalculate.setServiceFee(new BigDecimal(0));
                    loanCalculate.setServiceFeeDesc("包含"
                            + CostUtils.calServFee(promotionCase.getServFeeRate(), loan_amount).divide(
                            new BigDecimal(100)) + "元的旅游券");
                    loanCalculate.setReceiveAmount(new BigDecimal(300000));
                    loanCalculate.setInterestFee(totalInterest);
                    loanCalculate.setRepayAmount(totalAmt);
                    loanCalculate.setRepayPlan(repayPlanList);
                    cacheLoanCalculateFQ(loanCalculate, String.valueOf(xianJinBaiKaCommonRequest.getLoan_amount()));
                    logger.info("现金白卡试算结束");
                }
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(loanCalculate);
            } else {
                xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
                xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
                xianJinBaiKaResponse.setResponse(loanCalculateCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }

    @Override
    public XianJinBaiKaRepaymentPlan getRepayplanFQ(String order_sn) {
        XianJinBaiKaRepaymentPlan xianJinBaiKaRepaymentPlan = new XianJinBaiKaRepaymentPlan();
        String applyId = applyTripartiteService.getApplyIdByThirdId(order_sn);
        LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
        if (loanApplySimpleVO == null || loanApplySimpleVO.getProcessStatus() == null
                || loanApplySimpleVO.getProcessStatus().intValue() < XjdLifeCycle.LC_CASH_4) {
            return xianJinBaiKaRepaymentPlan;
        }
        LoanRepayPlanVO repayPlan = loanRepayPlanService.getByApplyId(applyId);
        if (repayPlan == null) {
            return xianJinBaiKaRepaymentPlan;
        }
        if (loanApplySimpleVO.getApproveTerm().equals(28)) {
            xianJinBaiKaRepaymentPlan.setOrderSn(order_sn);
            xianJinBaiKaRepaymentPlan.setAlreadyPaid((repayPlan.getPayedPrincipal().add(repayPlan.getPayedInterest()))
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setFinishPeriod(repayPlan.getPayedTerm());
            xianJinBaiKaRepaymentPlan.setReceivedAmount(repayPlan.getPrincipal().intValue() * 100);
            xianJinBaiKaRepaymentPlan.setTotalAmount(loanApplySimpleVO.getCurToltalRepayAmt()
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setTotalPeriod(repayPlan.getTotalTerm());
            xianJinBaiKaRepaymentPlan.setTotalSvcFee(0);
            RepayDetailListOP op = new RepayDetailListOP();
            op.setContNo(repayPlan.getContNo());
            op.setIsDelaySettlement(2);
            List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
            ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
            for (int i = 0; i < voList.size(); i++) {
                RepayDetailListVO repayDetailListVO = voList.get(i);
                RepaymentPlan repaymentPlan = new RepaymentPlan();
                repaymentPlan.setTotalAmount(repayDetailListVO.getTotalAmount().multiply(new BigDecimal(100))
                        .intValue());
                repaymentPlan.setAlreadyPaid(repayDetailListVO.getActualRepayAmt() == null ? new BigDecimal(0)
                        .intValue() : repayDetailListVO.getActualRepayAmt().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setBillStatus(repayDetailListVO.getStatus());
                repaymentPlan.setCanPayTime((int) (repayDetailListVO.getStartDate().getTime() / 1000));
                repaymentPlan.setDueTime((int) (repayDetailListVO.getRepayDate().getTime() / 1000 + 86399));
                repaymentPlan.setFinishPayTime(repayDetailListVO.getActualRepayTime() == null ? 0
                        : (int) (repayDetailListVO.getActualRepayTime().getTime() / 1000));
                repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setLoanTime(repayDetailListVO.getStartDate() == null ? 0 : (int) (repayDetailListVO
                        .getStartDate().getTime() / 1000));
                repaymentPlan
                        .setOverdueDay(repayDetailListVO.getOverdue() == null ? 0 : repayDetailListVO.getOverdue());
                repaymentPlan.setPayType(repayDetailListVO.getStatus());
                repaymentPlan.setOverdueFee(repayDetailListVO.getOverdueFee() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getOverdueFee().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodFeeDesc("");
                repaymentPlan.setServiceFee(0);
                repaymentPlan.setPrinciple(repayDetailListVO.getPrincipal() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getPrincipal().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodNo(repayDetailListVO.getThisTerm().toString());
                list.add(repaymentPlan);
                xianJinBaiKaRepaymentPlan.setRepaymentPlan(list);
            }

        } else {
            xianJinBaiKaRepaymentPlan.setOrderSn(order_sn);
            xianJinBaiKaRepaymentPlan.setAlreadyPaid((repayPlan.getPayedPrincipal().add(repayPlan.getPayedInterest()))
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setFinishPeriod(repayPlan.getPayedTerm());
            xianJinBaiKaRepaymentPlan.setReceivedAmount(200000);
            xianJinBaiKaRepaymentPlan.setTotalAmount(loanApplySimpleVO.getCurToltalRepayAmt()
                    .multiply(new BigDecimal(100)).intValue());
            xianJinBaiKaRepaymentPlan.setTotalPeriod(repayPlan.getTotalTerm());
            xianJinBaiKaRepaymentPlan.setTotalSvcFee(0);
            RepayDetailListOP op = new RepayDetailListOP();
            op.setContNo(repayPlan.getContNo());
            op.setIsDelaySettlement(2);
            List<RepayDetailListVO> voList = repayPlanItemManager.repayDetailList(null, op);
            ArrayList<RepaymentPlan> list = new ArrayList<RepaymentPlan>();
            for (int i = 0; i < voList.size(); i++) {
                RepayDetailListVO repayDetailListVO = voList.get(i);
                RepaymentPlan repaymentPlan = new RepaymentPlan();
                repaymentPlan.setTotalAmount(repayDetailListVO.getTotalAmount().multiply(new BigDecimal(100))
                        .intValue());
                repaymentPlan.setAlreadyPaid(repayDetailListVO.getActualRepayAmt() == null ? new BigDecimal(0)
                        .intValue() : repayDetailListVO.getActualRepayAmt().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setBillStatus(repayDetailListVO.getStatus());
                repaymentPlan.setCanPayTime((int) (repayDetailListVO.getStartDate().getTime() / 1000));
                repaymentPlan.setDueTime((int) (repayDetailListVO.getRepayDate().getTime() / 1000 + 86399));
                repaymentPlan.setFinishPayTime(repayDetailListVO.getActualRepayTime() == null ? 0
                        : (int) (repayDetailListVO.getActualRepayTime().getTime() / 1000));
                if (i == 0) {
                    repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                            : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue() + 100);
                } else {
                    repaymentPlan.setInterest(repayDetailListVO.getInterest() == null ? new BigDecimal(0).intValue()
                            : repayDetailListVO.getInterest().multiply(new BigDecimal(100)).intValue());
                }
                repaymentPlan.setLoanTime(repayDetailListVO.getStartDate() == null ? 0 : (int) (repayDetailListVO
                        .getStartDate().getTime() / 1000));
                repaymentPlan
                        .setOverdueDay(repayDetailListVO.getOverdue() == null ? 0 : repayDetailListVO.getOverdue());
                repaymentPlan.setPayType(repayDetailListVO.getStatus());
                repaymentPlan.setOverdueFee(repayDetailListVO.getOverdueFee() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getOverdueFee().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodFeeDesc("");
                repaymentPlan.setServiceFee(0);
                repaymentPlan.setPrinciple(repayDetailListVO.getPrincipal() == null ? new BigDecimal(0).intValue()
                        : repayDetailListVO.getPrincipal().multiply(new BigDecimal(100)).intValue());
                repaymentPlan.setPeriodNo(repayDetailListVO.getThisTerm().toString());
                list.add(repaymentPlan);
                xianJinBaiKaRepaymentPlan.setRepaymentPlan(list);
            }
        }

        return xianJinBaiKaRepaymentPlan;
    }

    @Override
    public XianJinBaiKaVO authStatus(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest) {
        XianJinBaiKaVO xianJinBaiKaResponse = new XianJinBaiKaVO();
        xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_SUCCESS);
        xianJinBaiKaResponse.setMessage(Global.XJBK_SUCCESS);
        AuthStatus authStatus = new AuthStatus();
        try {
            if ("8".equals(xianJinBaiKaCommonRequest.getAuth_type())) {
                logger.info("现金白卡H5认证状态查询-8用款确认");
                String orderNo = xianJinBaiKaCommonRequest.getOrder_sn();
                String applyId = applyTripartiteService.getApplyIdByThirdId(orderNo);
                BorrowInfo borrowInfo = borrowInfoManager.getByApplyId(applyId);
                authStatus.setAuthResult("200");
                authStatus.setSuccessTime(String.valueOf(System.currentTimeMillis() / 1000));
                if (borrowInfo == null) {
                    authStatus.setAuthResult("401");
                }
                xianJinBaiKaResponse.setResponse(authStatus);
            }
        } catch (Exception e) {
            logger.error("现金白卡H5认证状态查询异常-actType={}", xianJinBaiKaCommonRequest.getAct_type(), e);
            xianJinBaiKaResponse.setStatus(XianJinBaiKaVO.CODE_FAILURE);
            xianJinBaiKaResponse.setMessage("请求失败");
        }
        return xianJinBaiKaResponse;
    }
}
