package com.rongdu.loans.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
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
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.*;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.Rong360Config;
import com.rongdu.loans.common.RongClient;
import com.rongdu.loans.common.TripartitePromotionConfig;
import com.rongdu.loans.common.XJ360Util;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.cust.option.BaseInfoOP;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.RegisterOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.entity.*;
import com.rongdu.loans.loan.manager.*;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.LoanApplyOP;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.option.rong360Model.*;
import com.rongdu.loans.loan.option.rongTJreportv1.*;
import com.rongdu.loans.loan.option.xjbk.FileUploadResult;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.SaveApplyResultVO;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lee on 2018/6/29.
 */

@Service("rongService")
public class Rong360ServiceImpl implements RongService {
    private static Logger logger = LoggerFactory.getLogger(Rong360ServiceImpl.class);

    @Autowired
    private CustUserService custUserService;
    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;
    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private FileInfoManager fileInfoManager;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;
    @Autowired
    private PromotionCaseManager promotionCaseManager;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private RepayPlanItemManager repayPlanItemManager;
    @Autowired
    private ContractManager contractManager;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private UserMd5Manager userMd5Manager;
    @Autowired
    private RongStatusFeedBackService rongStatusFeedBackService;
    @Autowired
    private RongPointCutService rongPointCutService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private ConfigService configService;

    private FileServerClient fileServerClient = new FileServerClient();

    @Override
    public boolean saveUserAdditionalInfo(OrderAppendInfo orderAppendInfo) {
        boolean flag = false;
        try {
            String orderSn = orderAppendInfo.getOrderNo();
            FileInfoVO fileInfoVO = custUserService.getLastRongAdditionalByOrderSn(orderSn);
            if (fileInfoVO == null) {
                String res = uploadAppendData(orderAppendInfo, FileBizCode.RONG_ADDITIONAL_DATA.getBizCode(),
                        "rongAdditional");
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                    Map<String, String> map = Maps.newHashMap();
                    map.put(orderSn, String.valueOf(System.currentTimeMillis()));
                    JedisUtils.mapPut(Global.RONG_THIRD_KEY, map);
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
    public boolean saveUserBaseInfo(OrderBaseInfo orderBaseInfo) {
        boolean flag = false;
        try {
            String userPhone = orderBaseInfo.getOrderinfo().getUserMobile();
            String channelId = "RONG";
            // ytodo 0301 JHH
            int productId = orderBaseInfo.getOrderinfo().getProductId();
            if (productId == Global.RONG_JQB_PRODUCTID) {
                channelId = "RONG";
            } else if (productId == Global.RONG_JHH_PRODUCTID) {
                channelId = "RONGJHH";
            }
            String userId = registerOrReturnUserId(userPhone, channelId);
            FileInfoVO fileInfoVO = custUserService.getLastRongBaseByOrderSn(orderBaseInfo.getOrderinfo().getOrderNo());
            if (fileInfoVO == null) {
                String res = uploadBaseData(orderBaseInfo, FileBizCode.RONG_BASE_DATA.getBizCode(), userId);
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

    @Override
    public Rong360Resp bindCard(BindCardOP bindCardOP) {
        Rong360Resp result = new Rong360Resp();
        try {
            if (bindCardOP.getBindCardSrc() == 2) {
                BindStatus bindStatus = new BindStatus();
                bindStatus.setBindStatus("1");
                result.setCode("200");
                result.setMsg("");
                result.setData(bindStatus);
                return result;
            }
            String applyIdByThirdId = applyTripartiteRong360Service.getApplyIdByThirdId(bindCardOP.getOrderNo());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyIdByThirdId);
            String userId = loanApplySimpleVO.getUserId();
            DirectBindCardOP bindCardOp = new DirectBindCardOP();
            bindCardOp.setCardNo(bindCardOP.getBankCard());
            bindCardOp.setIdNo(bindCardOP.getIdNumber());
            bindCardOp.setMobile(bindCardOP.getUserMobile());
            bindCardOp.setRealName(bindCardOP.getUserName());
            bindCardOp.setUserId(userId);
            bindCardOp.setBankCode(bindCardOP.getOpenBank());
            bindCardOp.setSource("4");
            bindCardOp.setIpAddr("127.0.0.1");
            BindCardResultVO bindCardResult = baofooAgreementPayService.agreementPreBind(bindCardOp);
            if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getBindId())) {
                result.setCode("400");
                result.setMsg(bindCardResult.getMsg());
                if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                        || "BF00127".equals(bindCardResult.getCode())) {
                    result.setMsg(bindCardResult.getMsg());
                } else {
                    result.setMsg(bindCardResult.getMsg());
                }
                return result;
            } else {
                JedisUtils.set("RONG:bindCardBindId_" + userId, bindCardResult.getBindId(), 60 * 3);
                JedisUtils.set("RONG:bindCardOrderNo_" + userId, bindCardResult.getOrderNo(), 60 * 3);
                BindStatus bindStatus = new BindStatus();
                bindStatus.setBindStatus("0");
                result.setCode("200");
                result.setMsg("");
                result.setData(bindStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isBindCard(String userId, String newCardNo) {
        CustUserVO custUserVO = custUserService.getCustUserById(userId);
        if (newCardNo.equals(custUserVO.getCardNo())) {
            return true;
        }
        return false;
    }

    @Override
    public Rong360Resp confirmBindCard(BindCardOP bindCardOP) {
        Rong360Resp result = new Rong360Resp();
        try {
            String applyIdByThirdId = applyTripartiteRong360Service.getApplyIdByThirdId(bindCardOP.getOrderNo());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyIdByThirdId);
            String userId = loanApplySimpleVO.getUserId();
            // String userId =
            // registerOrReturnUserId(bindCardOP.getUserMobile());
            ConfirmBindCardOP confirmBindCardOP = new ConfirmBindCardOP();
            confirmBindCardOP.setUserId(userId);
            confirmBindCardOP.setMsgVerCode(bindCardOP.getVerifyCode());
            String bindId = JedisUtils.get("RONG:bindCardBindId_" + userId);
            String orderNo = JedisUtils.get("RONG:bindCardOrderNo_" + userId);
            confirmBindCardOP.setBindId(bindId);
            confirmBindCardOP.setOrderNo(orderNo);
            confirmBindCardOP.setSource("4");
            confirmBindCardOP.setType(1);
            BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(confirmBindCardOP);
            if (!bindCardResult.isSuccess()) {
                result.setCode("400");
                result.setMsg(bindCardResult.getMsg());
                if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                        || "BF00127".equals(bindCardResult.getCode())) {
                    result.setMsg(bindCardResult.getMsg());
                } else {
                    result.setMsg(bindCardResult.getMsg());
                }
                return result;
            }
            if (StringUtils.isBlank(bindCardResult.getBindId())) {
                result.setCode("400");
                result.setMsg("银行卡信息有误，请重试或换卡");
                return result;
            }

            BindCardVO bindInfo = bindCardService.findByOrderNo(orderNo);
            if (bindInfo == null) {
                result.setCode("400");
                result.setMsg("银行卡信息有误，请重试或换卡");
                return result;
            }
            bindInfo.setBindId(bindCardResult.getBindId());
            bindInfo.setStatus(bindCardResult.getCode());
            bindInfo.setRemark(bindCardResult.getMsg());
            bindInfo.setChlName("宝付确认绑卡");
            bindInfo.setSource("4");
            bindInfo.setIp("127.0.0.1");

            int saveBc = bindCardService.update(bindInfo);
            if (saveBc == 0) {
                result.setCode("400");
                result.setMsg("系统异常");
                return result;
            }

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
            identityInfoOP.setBankMobile(bindInfo.getMobile());

            int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
            if (saveRz == 0) {
                result.setCode("400");
                result.setMsg("系统异常");
                return result;
            }
            XJ360Util.cleanCustUserInfoCache(userId);
            result.setCode("200");
            result.setMsg("");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String registerOrReturnUserId(String userPhone, String channelId) {
        String userId = "";
        if (!custUserService.isRegister(userPhone)) {
            RegisterOP registerOP = new RegisterOP();
            registerOP.setAccount(userPhone);
            String password = XianJinCardUtils.setData(4);
            registerOP.setPassword(XianJinCardUtils.pwdToSHA1(String.valueOf(password)));
            registerOP.setChannel(channelId);
            userId = custUserService.saveRegister(registerOP);
//			sendMsg(password, userId, userPhone);
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
        sendShortMsgOP.setChannelId("RONG");
        shortMsgService.sendMsg(sendShortMsgOP);
    }

    private String uploadBaseData(OrderBaseInfo orderBaseInfo, String code, String userId) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(orderBaseInfo.getOrderinfo().getOrderNo());
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        String fileBodyText = JsonMapper.toJsonString(orderBaseInfo);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    private String uploadAppendData(OrderAppendInfo orderAppendInfo, String code, String userId) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(orderAppendInfo.getOrderNo());
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        String fileBodyText = JsonMapper.toJsonString(orderAppendInfo);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    private String uploadRongTJReportDetail(TianjiReportDetailResp tianjiReportDetail, String code, String userId) {
        UploadParams params = new UploadParams();
        String clientIp = "127.0.0.1";
        String source = "4";
        params.setUserId(userId);
        params.setApplyId(tianjiReportDetail.getOrderNo());
        params.setIp(clientIp);
        params.setSource(source);
        params.setBizCode(code);
        String fileBodyText = JsonMapper.toJsonString(tianjiReportDetail);
        String fileExt = "txt";
        String res = fileServerClient.uploadDocumentString(fileBodyText, fileExt, params);
        return res;
    }

    @Override
    public TaskResult saveUserAndApplyInfo() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int failNum = 0;
        Map<String, String> thirdKey = JedisUtils.getMap(Global.RONG_THIRD_KEY);
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
                if (Long.parseLong(map.getValue()) <= (System.currentTimeMillis() - 1000 * 60 * 5)
                        && Long.parseLong(map.getValue()) >= (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    OrderBaseInfo base = getPushBaseData(orderSn);
                    OrderAppendInfo additional = getPushAdditionalData(orderSn);
                    if (base != null && additional != null) {
                        try {
                            String userPhone = base.getOrderinfo().getUserMobile();
                            String ip = "127.0.0.1";
                            String channelId = "RONG";
                            // ytodo 0301 JHH
                            int productId = base.getOrderinfo().getProductId();
                            if (productId == Global.RONG_JQB_PRODUCTID) {
                                channelId = "RONG";
                            } else if (productId == Global.RONG_JHH_PRODUCTID) {
                                channelId = "RONGJHH";
                            }
                            String userId = registerOrReturnUserId(userPhone, channelId);
                            if (loanApplyService.isExistUnFinishLoanApply(userId)) {
                                handRefuse(orderSn);
                            }
                            if (!loanApplyService.isExistUnFinishLoanApply(userId)) {
                                int data = fileInfoManager.updateUserIdByOrderSn(userId, orderSn, "rong_addition_data");
                                saveDoOcr(base, additional, userId);
                                saveBaseInfo(base, additional, userId, channelId);
                                saveRz(userId, ip);
                                saveLoanApply(additional, base, userId, channelId);
                            }
                            JedisUtils.mapRemove(Global.RONG_THIRD_KEY, orderSn);
                            succNum++;
                        } catch (Exception e) {
                            JedisUtils.mapRemove(Global.RONG_THIRD_KEY, orderSn);
                            failNum++;
                            logger.error("融360三方工单转化失败:{} 手工处理", orderSn);
                            e.printStackTrace();
                        }
                    } else {
                        Map<String, String> rePush = Maps.newHashMap();
                        rePush.put(orderSn, String.valueOf(System.currentTimeMillis()));
                        JedisUtils.mapPut(Global.RONG_THIRD_KEY, rePush);
                    }
                } else if (Long.parseLong(map.getValue()) < (System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3)) {
                    // JedisUtils.mapRemove(Global.XJBK_THIRD_KEY, orderSn);
                }
            }
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(failNum);
        return taskResult;
    }

    private void saveDoOcr(OrderBaseInfo base, OrderAppendInfo appendInfo, String userId) {
        try {
            String homeAreas = appendInfo.getIdAddressOcr();
            String faceRecognitionPicture = appendInfo.getPhotoAssay().get(0);
            String zPicture = appendInfo.getIdPositive().get(0);
            String fPicture = null;
            if (appendInfo.getIdNegative() != null) {
                fPicture = appendInfo.getIdNegative().get(0);
            }
            String ocrName = appendInfo.getNameOcr();
            String ocrAddress = appendInfo.getIdAddressOcr();
            String ocrIdNumber = appendInfo.getIdNumberOcr();
            String ocrIssuedBy = appendInfo.getIdIssueOrgOcr();
            String ocrSex = appendInfo.getIdSexOcr();
            String ocrRace = appendInfo.getIdEthnicOcr();
            String ocrValidDate = appendInfo.getIdDueTimeOcr();

            String faceRecognitionPictureBase64 = "";
            String zPictureBase64 = "";
            String fPictureBase64 = "";
            String orderNo = appendInfo.getOrderNo();
            faceRecognitionPictureBase64 = rongImageFetch(orderNo, faceRecognitionPicture);
            zPictureBase64 = rongImageFetch(orderNo, zPicture);
            if (fPicture != null) {
                fPictureBase64 = rongImageFetch(orderNo, fPicture);
            }
            UploadParams faceImage = new UploadParams();
            faceImage.setUserId(userId);
            faceImage.setIp("127.0.0.1");
            faceImage.setSource("4");
            faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
            XJ360Util.uploadBase64Image(faceRecognitionPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
            XJ360Util.uploadBase64Image(zPictureBase64, faceImage);
            faceImage.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
            XJ360Util.uploadBase64Image(fPictureBase64, faceImage);
            OcrOP ocrOP = new OcrOP();
            ocrOP.setName(ocrName);
            ocrOP.setAddress(ocrAddress);
            ocrOP.setIdcard(ocrIdNumber);
            ocrOP.setAuthority(ocrIssuedBy);
            ocrOP.setSex(ocrSex);
            ocrOP.setNation(ocrRace);
            ocrOP.setValidDate(ocrValidDate);
            ocrOP.setUserId(userId);
            int saveDoOcrResult = custUserService.saveDoOcr(ocrOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBaseInfo(OrderBaseInfo base, OrderAppendInfo additional, String userId, String channelId) {
        String userPhone = base.getApplydetail().getPhoneNumberHouse();
        String userName = base.getApplydetail().getBureauUserName();
        String userIdcard = base.getApplydetail().getUserId();
        String degree = RongUtils.convertDegree(base.getApplydetail().getUserEducation());
        String homeAddress = additional.getIdAddressOcr();
        String homeAreas = base.getOrderinfo().getCity();
        String professionType = base.getApplydetail().getIsOpType();

        String relation = additional.getEmergencyContactPersonaRelationship();
        String mobile = additional.getEmergencyContactPersonaPhone();
        String name = filterEmoji(additional.getEmergencyContactPersonaName());
        String relation1 = RongUtils.convertARelation(relation);
        String inRelation = relation1 + "," + name + "," + mobile;

        String relationSpare = additional.getEmergencyContactPersonbRelationship();
        String nameSpare = filterEmoji(additional.getEmergencyContactPersonbName());
        String mobileSpare = additional.getEmergencyContactPersonbPhone();
        String relationSpare1 = RongUtils.convertBRelation(relationSpare);
        String inRelationSpare = relationSpare1 + "," + nameSpare + "," + mobileSpare;

        String qq = additional.getSnsQq();
        BaseInfoOP baseInfoOP = new BaseInfoOP();
        String companyName = filterEmoji(additional.getCompanyName());
        String address = filterEmoji(additional.getCompanyAddrDetail());
        if (address.length() >= 180) {
            address = address.substring(0, 180);
        }
        String tel = additional.getCompanyNumber();
        String[] telSplit = tel.split("-");

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

        baseInfoOP.setDegree(degree);
        baseInfoOP.setResideAddr(homeAddress);
        baseInfoOP.setContactParent(inRelation);
        baseInfoOP.setContactFriend(inRelationSpare);
        baseInfoOP.setQq(qq);
        baseInfoOP.setChannelId(channelId);
        int saveRz = custUserService.saveBaseInfo(baseInfoOP);
    }

    private void saveRz(String userId, String ip) {
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

    private SaveApplyResultVO saveLoanApply(OrderAppendInfo additional, OrderBaseInfo base, String userId,
                                            String channelId) {
        String orderSn = additional.getOrderNo();
        PromotionCaseOP promotionCaseOP =
                TripartitePromotionConfig.getPromotionCase("2");
        LoanApplyOP loanApplyOP = new LoanApplyOP();
        loanApplyOP.setProductId(promotionCaseOP.getProductId());
        loanApplyOP.setUserId(userId);
        loanApplyOP.setApplyAmt(promotionCaseOP.getApplyAmt());
        loanApplyOP.setApplyTerm(promotionCaseOP.getApplyTerm());
        loanApplyOP.setSource("4");
        if ("XJD".equals(promotionCaseOP.getProductId())) {
            loanApplyOP.setProductType("0");
        } else if ("XJDFQ".equals(promotionCaseOP.getProductId())) {
            loanApplyOP.setProductType("4");
        }
        XJ360Util.cleanCustUserInfoCache(userId);
        SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
        if (!applyTripartiteRong360Service.isExistApplyId(rz.getApplyId())) {
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("id", rz.getApplyId()));
            LoanApply loanApply = new LoanApply();
            loanApply.setChannelId(channelId);
            loanApplyManager.updateByCriteriaSelective(loanApply, criteria);
            int saveTripartiteOrderResult = applyTripartiteRong360Service.insertTripartiteOrder(rz.getApplyId(),
                    orderSn);
        }
        return rz;
    }

    @Override
    public OrderBaseInfo getPushBaseData(String orderSn) {
        String cacheKey = "RONG:PUSH_BASE_" + orderSn;
        OrderBaseInfo vo = (OrderBaseInfo) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastRongBaseByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户基础信息", fileInfoVO.getUrl());
                    vo = (OrderBaseInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                            OrderBaseInfo.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "融360", "从文件获取用户基础信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public OrderAppendInfo getPushAdditionalData(String orderSn) {
        String cacheKey = "RONG:PUSH_ADDITIONAL_" + orderSn;
        OrderAppendInfo vo = (OrderAppendInfo) JedisUtils.getObject(cacheKey);
        try {
            if (vo == null) {
                FileInfoVO fileInfoVO = custUserService.getLastRongAdditionalByOrderSn(orderSn);
                if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                    logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户附加信息", fileInfoVO.getUrl());
                    vo = (OrderAppendInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                            OrderAppendInfo.class);
                    if (vo != null) {
                        JedisUtils.setObject(cacheKey, vo, 60);
                    } else {
                        logger.info("{}-{}-应答结果：{}", "融360", "从文件获取用户附加信息", false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public Rong360Resp calculation(Calculation calculation) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            CalculationVO loanCalculateCache = getLoanCalculate(String.valueOf(calculation.getAmount()));
            if (loanCalculateCache == null) {
                CalculationVO loanCalculate = new CalculationVO();
                Map<String, Object> map = new HashMap<String, Object>();
                BigDecimal loan_amount = new BigDecimal(calculation.getAmount());

                PromotionCaseOP promotionCaseOP = new PromotionCaseOP();
                promotionCaseOP.setApplyAmt(loan_amount);
                promotionCaseOP.setApplyTerm(calculation.getPeroid());
                promotionCaseOP.setProductId("XJD");
                promotionCaseOP.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                PromotionCase promotionCase = promotionCaseManager.getByApplyInfo(promotionCaseOP);
                if (null != promotionCase) {
                    BigDecimal discountVal = promotionCase.getDiscountValue();
                    BigDecimal dayInterest = CostUtils.calActualDayInterest(promotionCase.getRatePerDay(), loan_amount,
                            discountVal);
                    BigDecimal allInterest = CostUtils.calTotalInterest(dayInterest, 15);
                    BigDecimal servicefee = CostUtils.calServFee(promotionCase.getServFeeRate(), loan_amount);

                    loanCalculate.setServicefee(new BigDecimal(0));
                    loanCalculate.setRemark("本金" + loan_amount + "元,利息" + allInterest + "元");
                    loanCalculate.setActualAmount(loan_amount.subtract(servicefee));
                    loanCalculate.setRepayAmount(loan_amount.add(allInterest));
                    loanCalculate.setGoodsAmount(servicefee);

                    cacheLoanCalculate(loanCalculate, String.valueOf(calculation.getAmount()));
                    logger.info("融360试算结束");
                }
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("");
                rong360Resp.setData(loanCalculate);
            } else {
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("");
                rong360Resp.setData(loanCalculateCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp confirmLoan(ApproveOP approveOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(approveOP.getOrderNo());
            approveOP.setApplyId(applyId);
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(loanApplySimpleVO.getMobile());
            if (custUserVO.getCardNo() == null) {
                rong360Resp.setCode(Rong360Resp.FAILURE);
                rong360Resp.setMsg("未绑卡");
                return rong360Resp;
            }
            Integer status = loanApplySimpleVO.getProcessStatus();
            if (!status.equals(XjdLifeCycle.LC_CASH_4) && !status.equals(XjdLifeCycle.LC_CHANNEL_0)) {
                boolean flag = true;
                if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(loanApplySimpleVO.getPayChannel())) {
                    JedisUtils.set(Global.RONG_CREATE_ACCOUNT + applyId, DateUtils.getDateTime(), 60 * 60 * 24 * 7);
                } else {
                    flag = loanApplyService.saveShopedBorrowInfo(applyId, LoanApplySimpleVO.APPLY_PAY_TYPE_1);
                }
                if (flag) {
                    NeedVerify needVerify = new NeedVerify();
                    needVerify.setIsNeedVerify("0");
                    rong360Resp.setCode(Rong360Resp.SUCCESS);
                    rong360Resp.setMsg("");
                    rong360Resp.setData(needVerify);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getRepaymentDetails(RepaymentDetailsOP repaymentDetailsOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(repaymentDetailsOP.getOrderNo());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);

            String toltalRepayAmt = RongUtils.formatFloat4(loanApplySimpleVO.getCurToltalRepayAmt().floatValue());
            String approveAmt = RongUtils.formatFloat2(loanApplySimpleVO.getApproveAmt().floatValue());
            String overdueFee = RongUtils.formatFloat2(loanApplySimpleVO.getOverdueFee().floatValue());
            String allInterest = RongUtils.formatFloat2(loanApplySimpleVO.getCurToltalRepayAmt().floatValue()
                    - loanApplySimpleVO.getApproveAmt().floatValue() - loanApplySimpleVO.getOverdueFee().floatValue());

            String remark = "含本金" + approveAmt + "元，利息" + allInterest + "元，逾期费" + overdueFee + "元";

            RepaymentDetailsVO repaymentDetailsVO = new RepaymentDetailsVO();
            repaymentDetailsVO.setRemark(remark);
            repaymentDetailsVO.setAmount(toltalRepayAmt);
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(repaymentDetailsVO);
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp applyRepay(ApplyRepayOP applyRepayOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(applyRepayOP.getOrderNo());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            String loanDate = loanApplySimpleVO.getLoanDate();
            Date loan = DateUtils.parse(loanDate);
            long days = DateUtils.pastDays(loan);
            if (days <= 1) {
                rong360Resp.setCode(Rong360Resp.FAILURE);
                rong360Resp.setMsg("借款至少持有一天方可还款");
                return rong360Resp;
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
            /*
             * WithholdResultVO withholdResultVO =
             * withholdService.withholdXianJinBaiKa(loanApplySimpleVO
             * .getRepayPlanItemId());
             */
            String orderSn = applyRepayOP.getOrderNo();
            String userAgreementPayLockCacheKey = "RONG:pay_lock_" + orderSn;
            synchronized (Rong360ServiceImpl.class) {
                String userAgreementPayLock = JedisUtils.get(userAgreementPayLockCacheKey);
                if (userAgreementPayLock == null) {
                    // 加锁，防止并发
                    JedisUtils.set(userAgreementPayLockCacheKey, "locked", 30);
                } else {
                    logger.warn("协议直接支付接口调用中，lockId= {}", orderSn);
                    rong360Resp.setCode(Rong360Resp.SUCCESS);
                    rong360Resp.setMsg("还款处理中，请稍后查询");
                    return rong360Resp;
                }
            }

            ConfirmAuthPayVO confirmAuthPayVO = withholdService.agreementPay(rePayOP);
            if (confirmAuthPayVO.isSuccess()) {
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("还款成功");
                return rong360Resp;
            }
            if ("I".equals(confirmAuthPayVO.getStatus())) {
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("还款处理中，请稍后查询");
                return rong360Resp;
            } else {
                rong360Resp.setCode(Rong360Resp.FAILURE);
                rong360Resp.setMsg(confirmAuthPayVO.getMsg());
                return rong360Resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("还款异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp delayDetails(DelayDetailsOP delayDetailsOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String orderNo = delayDetailsOP.getOrderNo();
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderNo);
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            CalculateInfo calculateInfo = getOrderInfo(loanApplySimpleVO);
            int deferDueTime = (int) (calculateInfo.getDeferRepayDay().getTime() / 1000);
            int delayDays = calculateInfo.getDelayDays();
            float afterDeferAmount = calculateInfo.getTotalAmount().floatValue();
            float delayAmt = calculateInfo.getDelayAmt().floatValue();
            float delayBaseAmt = calculateInfo.getDelayBaseAmt().floatValue();
            float overDuefee = calculateInfo.getOverdueFee().floatValue();
            float interest = calculateInfo.getInterest().floatValue();
            float principal = calculateInfo.getPrincipal().floatValue();
            float delayInterest = calculateInfo.getDelayInterest().floatValue();
            float oldInterest = interest - delayInterest;

            DeferOption deferOption = new DeferOption();
            deferOption.setDeferDueTime(deferDueTime);
            deferOption.setDeferDay(delayDays);
            deferOption.setAfterDeferAmount(afterDeferAmount);
            deferOption.setRemark("本金" + principal + "元，利息" + oldInterest + "元，展期利息" + delayInterest + "元");
            List<DeferOption> deferOptionList = new ArrayList<>();
            deferOptionList.add(deferOption);

            DeferAmountOption deferAmountOption = new DeferAmountOption();
            deferAmountOption.setDeferAmount(delayAmt);
            deferAmountOption.setDeferDay(delayDays);
            deferAmountOption.setRemark("管理费" + delayBaseAmt + "元，逾期费" + overDuefee + "元");
            List<DeferAmountOption> deferAmountOptionList = new ArrayList<>();
            deferAmountOptionList.add(deferAmountOption);

            DelayDetailsVO delayDetailsVO = new DelayDetailsVO();
            delayDetailsVO.setOrderNo(orderNo);
            delayDetailsVO.setDeferAmountOption(deferAmountOptionList);
            delayDetailsVO.setDeferOption(deferOptionList);
            delayDetailsVO.setDeferAmountType(0);
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(delayDetailsVO);

        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("展期详情异常");
        }
        return rong360Resp;
    }

    @Override
    public CalculateInfo getOrderInfo(LoanApplySimpleVO loanApplySimpleVO) {
        RepayPlanItem sourceItem = repayPlanItemManager.get(loanApplySimpleVO.getRepayPlanItemId());
        LoanApply loanApply = loanApplyManager.getLoanApplyById(loanApplySimpleVO.getApplyId());
        Contract contract = contractManager.getByApplyId(loanApplySimpleVO.getApplyId());
        Date delayStartDate = DateUtils.parseYYMMdd(DateUtils.getDate());
        int overdueDays = DateUtils.daysBetween(sourceItem.getRepayDate(), new Date());// 逾期天数
        if (overdueDays < 0) {
            delayStartDate = sourceItem.getRepayDate();
            overdueDays = 0;
        }
        BigDecimal loanApplyInterest = loanApply.getInterest();
        // 延期天数
        int delayDays = loanApply.getApplyTerm();
        // 逾期管理费
        BigDecimal overdueFee = sourceItem.getOverdueFee() == null ? BigDecimal.ZERO : sourceItem.getOverdueFee();
        // 减免
        BigDecimal deduction = sourceItem.getDeduction() == null ? BigDecimal.ZERO : sourceItem.getDeduction();
        // 延期利息
        BigDecimal delayInterest = CostUtils.calCurInterest(loanApply.getApproveAmt(), loanApply.getActualRate(),
                delayDays);
        // 延期金额
        BigDecimal principal = contract.getPrincipal();
        BigDecimal delayBaseAmt = principal.multiply(new BigDecimal(Global.DELAY_RATE)).subtract(deduction)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal delayAmt = delayBaseAmt.add(overdueFee);
        Date deferRepayDay = DateUtils.addDay(delayStartDate, delayDays);
        BigDecimal totalAmount = sourceItem.getTotalAmount().add(delayInterest).subtract(overdueFee).add(deduction);
        BigDecimal interest = sourceItem.getInterest().add(delayInterest);
        CalculateInfo calculateInfo = new CalculateInfo();
        calculateInfo.setDeduction(deduction);
        calculateInfo.setDeferRepayDay(deferRepayDay);
        calculateInfo.setDelayAmt(delayAmt);
        calculateInfo.setDelayDays(delayDays);
        calculateInfo.setDelayInterest(delayInterest);
        calculateInfo.setInterest(interest);
        calculateInfo.setTotalAmount(totalAmount);
        calculateInfo.setOverdueFee(overdueFee);
        calculateInfo.setDelayBaseAmt(delayBaseAmt);
        calculateInfo.setPrincipal(principal);
        calculateInfo.setOverdueDays(overdueDays);
        calculateInfo.setLoanApplyInterest(loanApplyInterest);
        return calculateInfo;
    }

    @Override
    public Rong360Resp applyDelay(ApplyDelayOP applyRepayOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(applyRepayOP.getOrderNo());
            LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
            BigDecimal curDelayRepayAmt = loanApplySimpleVO.getCurDelayRepayAmt();
            WithholdResultVO vo = withholdService.delayDealWithhold(loanApplySimpleVO.getRepayPlanItemId(),
                    String.valueOf(curDelayRepayAmt), PayTypesEnum.TONGLIAN);
            if (vo.getSuccess()) {
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("");
                return rong360Resp;
            } else if (vo.getUnsolved()) {
                rong360Resp.setCode(Rong360Resp.FAILURE);
                rong360Resp.setMsg("延期扣款正在处理中，请勿重复执行！");
                return rong360Resp;
            }
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("延期失败");
            return rong360Resp;
        } catch (Exception e) {
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("延期异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getBindBankCard(GetCardOP getCardOP) {
        Rong360Resp rong360Resp = new Rong360Resp();

        try {
            // String applyId =
            // applyTripartiteRong360Service.getApplyIdByThirdId(getCardOP.getOrderNo());
            // LoanApplySimpleVO loanApplySimpleVO =
            // loanApplyService.getLoanApplyById(applyId);
            // BindInfoVO vo =
            // custUserService.getBindInfoById(loanApplySimpleVO.getUserId());
            BindBankCardVO bindBankCardVO = new BindBankCardVO();
            // if (StringUtils.isNoneBlank(vo.getCardNo())) {
            // BankCardList bankCardList = new BankCardList();
            // bankCardList.setBankCard(vo.getCardNo());
            // bankCardList.setCardType(1);
            // bankCardList.setOpenBank(vo.getBankCode());
            // bankCardList.setIsRepayCard(1);
            // List<BankCardList> bankCardLists = new ArrayList<>();
            // bankCardLists.add(bankCardList);
            //
            // bindBankCardVO.setOrderNo(getCardOP.getOrderNo());
            // bindBankCardVO.setBankCardList(bankCardLists);
            // rong360Resp.setCode(Rong360Resp.SUCCESS);
            // rong360Resp.setMsg("");
            // rong360Resp.setData(bindBankCardVO);
            // return rong360Resp;
            // } else {
            bindBankCardVO.setOrderNo(getCardOP.getOrderNo());
            bindBankCardVO.setBankCardList(null);
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(bindBankCardVO);
            return rong360Resp;
            // }
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("获取银行卡列表异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getContract(ContractOP contractOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String orderSn = contractOP.getOrderNo();
            String applyId = applyTripartiteRong360Service.getApplyIdByThirdId(orderSn);
            String userId = "";
            BigDecimal approveAmt = new BigDecimal(3000);
            Integer applyTerm = 15;
            String channelId = "";
            if (applyId != null) {
                LoanApplySimpleVO loanApplySimpleVO = loanApplyService.getLoanApplyById(applyId);
                userId = loanApplySimpleVO.getUserId();
                approveAmt = loanApplySimpleVO.getApproveAmt();
                applyTerm = loanApplySimpleVO.getApplyTerm();
                channelId = loanApplySimpleVO.getChannelId();
            }
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userId);
            map.put("applyAmt", String.valueOf(approveAmt));
            map.put("applyTerm", String.valueOf(applyTerm));
            map.put("applyId", applyId);
            map.put("productId", "XJD");
            String link = XJ360Util.getRqstUrl("https://api.jubaoqiandai.com/#/agreement7", map);
            if ("RONGJHH".equals(channelId)) {
                link = XJ360Util.getRqstUrl("https://api.jubaoqiandai.com/#/agreement16", map);
            }
            logger.info("协议地址：{}", link);

            ContractVO contractVO = new ContractVO();
            contractVO.setContractUrl(link);
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(contractVO);
            return rong360Resp;
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("获取合同异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp isUserAccept(AcceptOP acceptOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            String md5 = acceptOP.getMd5();
            if (md5 != null) {
                String key = JedisUtils.get("RONG:APPLY_LOCK_" + md5);
                if (key != null) {
                    RefuseVO refuseVO = new RefuseVO();
                    refuseVO.setReason("C001");
                    rong360Resp.setCode(Rong360Resp.FAILURE);
                    rong360Resp.setMsg("");
                    rong360Resp.setData(refuseVO);
                    setIsUserAcceptCache(rong360Resp, md5);
                    return rong360Resp;
                }

                Rong360Resp rong360Resp1 = getIsUserAcceptCache(md5);
                if (rong360Resp1 != null) {
                    return rong360Resp1;
                }
                Criteria criteria = new Criteria();
                criteria.add(Criterion.eq("md5", md5));
                UserMd5 userMd5 = userMd5Manager.getByCriteria(criteria);
                if (userMd5 != null) {
                    String userId = userMd5.getUserNo();
                    if (userId != null) {
                        boolean isExist = loanApplyService.isExistUnFinishLoanApply(userId);
                        if (isExist) {
                            RefuseVO refuseVO = new RefuseVO();
                            refuseVO.setReason("C001");
                            rong360Resp.setCode(Rong360Resp.FAILURE);
                            rong360Resp.setMsg("");
                            rong360Resp.setData(refuseVO);
                            setIsUserAcceptCache(rong360Resp, md5);
                            return rong360Resp;
                        }
                        long isBlackUser = riskBlacklistService.countInBlacklist(userId);
                        if (isBlackUser > 0) {
                            RefuseVO refuseVO = new RefuseVO();
                            refuseVO.setReason("C002");
                            rong360Resp.setCode(Rong360Resp.FAILURE);
                            rong360Resp.setMsg("");
                            rong360Resp.setData(refuseVO);
                            setIsUserAcceptCache(rong360Resp, md5);
                            return rong360Resp;
                        }
                        LoanApply lastApply = loanApplyManager.getLastFinishApplyByUserId(userId);
                        if (null != lastApply) {
                            if (lastApply.getStatus().equals(XjdLifeCycle.LC_AUTO_AUDIT_2)
                                    || lastApply.getStatus().equals(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2)) {
                                Date lastUpdateTime = lastApply.getUpdateTime();
                                long pastDays = DateUtils.pastDays(lastUpdateTime);
                                if (pastDays < Global.MIN_AGAIN_APPLY_DAY) {
                                    RefuseVO refuseVO = new RefuseVO();
                                    refuseVO.setReason("C003");
                                    rong360Resp.setCode(Rong360Resp.FAILURE);
                                    rong360Resp.setMsg("");
                                    rong360Resp.setData(refuseVO);
                                    setIsUserAcceptCache(rong360Resp, md5);
                                    return rong360Resp;
                                }
                            }
                        }
                    }
                }
                List<Integer> termList = new ArrayList<>();
                int term = 15;
                termList.add(term);
                AcceptVO acceptVO = new AcceptVO();
                acceptVO.setApprovalAmount(1500);
                acceptVO.setApprovalTerm(termList);
                acceptVO.setIsReloan(0);
                acceptVO.setTermUnit(1);
                rong360Resp.setCode(Rong360Resp.SUCCESS);
                rong360Resp.setMsg("");
                rong360Resp.setData(acceptVO);
                setIsUserAcceptCache(rong360Resp, md5);
                return rong360Resp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("准入异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getApproval(GetOP getOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            RongApproveFeedBackOP rongApproveFeedBackOP = rongStatusFeedBackService.pullApproveStatusFeedBack(getOP
                    .getOrderNo());
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(rongApproveFeedBackOP);
            return rong360Resp;
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("拉取审批异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getRepaymentPlan(GetOP getOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            RongPushRepaymentOP rongPushRepaymentOP = rongStatusFeedBackService.pullRepaymentPlan(getOP.getOrderNo());
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(rongPushRepaymentOP);
            return rong360Resp;
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("拉取还款计划异常");
        }
        return rong360Resp;
    }

    @Override
    public Rong360Resp getOrderStatus(GetOP getOP) {
        Rong360Resp rong360Resp = new Rong360Resp();
        try {
            RongOrderFeedBackOP rongOrderFeedBackOP = rongStatusFeedBackService.pullOrderStatusFeedBack(getOP
                    .getOrderNo());
            rong360Resp.setCode(Rong360Resp.SUCCESS);
            rong360Resp.setMsg("");
            rong360Resp.setData(rongOrderFeedBackOP);
            return rong360Resp;
        } catch (Exception e) {
            e.printStackTrace();
            rong360Resp.setCode(Rong360Resp.FAILURE);
            rong360Resp.setMsg("拉取工单状态异常");
        }
        return rong360Resp;
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

    public static CalculationVO getLoanCalculate(String s) {
        if (StringUtils.isNotBlank(s)) {
            return (CalculationVO) JedisUtils.getObject("RONG:LoanCalculate_" + s);
        }
        return null;
    }

    public static CalculationVO cacheLoanCalculate(CalculationVO loanCalculate, String s) {
        if (null != loanCalculate) {
            JedisUtils.setObject("RONG:LoanCalculate_" + s, loanCalculate, 60 * 60 * 24 * 1);
        } else {
            logger.error("试算数据不存在");
        }
        return loanCalculate;
    }

    public static Rong360Resp getIsUserAcceptCache(String key) {
        Rong360Resp rong360Resp = new Rong360Resp();
        if (StringUtils.isNotBlank(key)) {
            return (Rong360Resp) JedisUtils.getObject("RONG:IsUserAccept_" + key);
        }
        return rong360Resp;
    }

    public static Rong360Resp setIsUserAcceptCache(Rong360Resp rong360Resp, String key) {
        if (null != rong360Resp) {
            JedisUtils.setObject("RONG:IsUserAccept_" + key, rong360Resp, 60 * 3);
        }
        return rong360Resp;
    }

    @Override
    public boolean saveRongTJReportDetail(TianjiReportDetailResp tianjiReportDetail) {
        boolean flag = false;
        try {
            String userId = tianjiReportDetail.getUserId();
            FileInfoVO fileInfoVO = custUserService.getLastRongTJReportDetailByOrderSn(tianjiReportDetail.getOrderNo());
            if (fileInfoVO == null) {
                String res = uploadRongTJReportDetail(tianjiReportDetail,
                        FileBizCode.RONGTJ_REPORT_DETAIL.getBizCode(), userId);
                FileUploadResult fileUploadResult = (FileUploadResult) JsonMapper.fromJsonString(res,
                        FileUploadResult.class);
                if (fileUploadResult.getCode().equals(ErrInfo.SUCCESS.getCode())) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        } catch (Exception e) {
            logger.warn("融天机运营商报告保存异常！！！orderNo={}", tianjiReportDetail.getOrderNo(), e);
        }
        return flag;
    }

    @Override
    public RongTJResp crawlGenerateReport(String orderNo, String type, String notifyUrl, String version)
            throws Exception {
        logger.debug("----------开始执行【天机生成报告接口】请求orderNo={}----------", orderNo);
        RongTJGeneratereportOP op = new RongTJGeneratereportOP();
        op.setOrderNo(orderNo);
        op.setType(type);
        if (StringUtils.isBlank(notifyUrl)) {
            notifyUrl = Rong360Config.rong_tianji_report_notifyurl;
        }
        op.setNotifyUrl(notifyUrl);
        op.setVersion(version);
        Map<String, String> params = new HashMap<>();
        params.put("biz_data", JsonMapper.toJsonString(op));
        // params.put("biz_data", JSONObject.toJSONString(op));
        RongTJResp resp = RongClient.rongTianJiPost(Rong360ServiceEnums.TAOJINYUNREPORT_GENERATEREPORT.getMethod(),
                params, RongTJResp.class);
        logger.debug("orderNo={}【天机生成报告接口】响应结果：{}", orderNo, resp.toString());
        logger.debug("----------结束执行【天机生成报告接口】请求orderNo={}----------", orderNo);
        return resp;
    }

    @Override
    public RongTJReportDetailResp crawlReportDetail(String searchId, String reportType) throws Exception {
        logger.debug("----------开始执行【天机互联网报告信息详情接口】请求searchId={}----------", searchId);
        RongTJReportDetailOP op = new RongTJReportDetailOP();
        op.setSearchId(searchId);
        op.setReportType(reportType);
        Map<String, String> params = new HashMap<>();
        params.put("biz_data", JsonMapper.toJsonString(op));
        // params.put("biz_data", JSONObject.toJSONString(op));
        RongTJReportDetailResp resp = RongClient.rongTianJiPost(
                Rong360ServiceEnums.TAOJINYUNREPORT_REPORTDETAIL.getMethod(), params, RongTJReportDetailResp.class);
        // logger.debug("【天机互联网报告信息详情接口】响应结果：" + resp.toString());
        logger.debug("----------结束执行【天机互联网报告信息详情接口】请求searchId={}----------", searchId);
        return resp;
    }

    @Override
    public RongTJReportDetailResp crawlScore(String searchId) throws Exception {
        logger.debug("----------开始执行【天机风控模型详情接口】请求----------");
        RongTJReportDetailOP op = new RongTJReportDetailOP();
        op.setSearchId(searchId);
        Map<String, String> params = new HashMap<>();
        params.put("biz_data", JsonMapper.toJsonString(op));
        // params.put("biz_data", JSONObject.toJSONString(op));
        RongTJReportDetailResp resp = RongClient.rongTianJiPost(Rong360ServiceEnums.TAOJINYUNREPORT_SCORE.getMethod(),
                params, RongTJReportDetailResp.class);
        // logger.debug("【天机风控模型详情接口】响应结果：" + resp.toString());
        logger.debug("----------结束执行【天机风控模型详情接口】请求----------");
        return resp;
    }

    @Override
    public RongTJReportDetailResp crawlScoreplus(String searchId) throws Exception {
        logger.debug("----------开始执行【天机风控模型plus详情接口】请求----------");
        RongTJReportDetailOP op = new RongTJReportDetailOP();
        op.setSearchId(searchId);
        Map<String, String> params = new HashMap<>();
        params.put("biz_data", JsonMapper.toJsonString(op));
        // params.put("biz_data", JSONObject.toJSONString(op));
        RongTJReportDetailResp resp = RongClient.rongTianJiPost(
                Rong360ServiceEnums.TAOJINYUNREPORT_SCOREPLUS.getMethod(), params, RongTJReportDetailResp.class);
        // logger.debug("【天机风控模型plus详情接口】响应结果：" + resp.toString());
        logger.debug("----------结束执行【天机风控模型plus详情接口】请求----------");
        return resp;
    }

    @Override
    public OrderBaseInfo getRongBase(String userId) {
        String cacheKey = "RONG360:BASE_" + userId;
        OrderBaseInfo vo = (OrderBaseInfo) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            FileInfoVO fileInfoVO = custUserService.getLastRongAdditionalByUserId(userId);
            if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户基础信息", fileInfoVO.getUrl());
                vo = (OrderBaseInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                        OrderBaseInfo.class);
                if (vo != null) {
                    JedisUtils.setObject(cacheKey, vo, 60 * 5);
                }
            }
        }
        return vo;
    }

    @Override
    public OrderAppendInfo getRongAdditional(String userId) {
        String cacheKey = "RONG360:ADDITIONAL_" + userId;
        OrderAppendInfo vo = (OrderAppendInfo) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            FileInfoVO fileInfoVO = custUserService.getLastRongAdditionalByUserId(userId);
            if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户附加信息", fileInfoVO.getUrl());
                vo = (OrderAppendInfo) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                        OrderAppendInfo.class);
                if (vo != null) {
                    JedisUtils.setObject(cacheKey, vo, 60 * 5);
                }
            }
        }
        return vo;
    }

    @Override
    public TianjiReportDetailResp getRongTJReportDetail(String userId) {
        String cacheKey = "RONGTJ:REPORT_" + userId;
        TianjiReportDetailResp vo = (TianjiReportDetailResp) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            FileInfoVO fileInfoVO = custUserService.getLastRongTJReportDetailByUserId(userId);
            if (fileInfoVO != null && StringUtils.isNotBlank(fileInfoVO.getUrl())) {
                logger.info("{}-{}-请求地址：{}", "融360", "从文件获取用户运营商报告信息", fileInfoVO.getUrl());
                vo = (TianjiReportDetailResp) RestTemplateUtils.getInstance().getForObject(fileInfoVO.getUrl(),
                        TianjiReportDetailResp.class);
                if (vo != null) {
                    JedisUtils.setObject(cacheKey, vo, 60 * 5);
                }
            }
        }
        return vo;
    }

    @Override
    public String rongImageFetch(String orderNo, String token) throws Exception {
        logger.debug("----------开始执行【天机图片获取接口】请求orderNo={}----------", orderNo);
        RongImageFetchOP rongPushRepaymentOP = new RongImageFetchOP();
        rongPushRepaymentOP.setOrderNo(orderNo);
        rongPushRepaymentOP.setToken(token);

        Map<String, String> params = new HashMap<>();
        params.put("biz_data", JsonMapper.toJsonString(rongPushRepaymentOP));
        // params.put("biz_data", JSONObject.toJSONString(rongPushRepaymentOP));
        String resp = RongClient.rongPost(Rong360ServiceEnums.TJY_IMAGE_API_FETCH.getMethod(), params, String.class);
        logger.debug("----------结束执行【天机图片获取接口】请求orderNo={}----------", orderNo);
        return resp;
    }

    private boolean doRongReportDetail(String orderNo, String searchId, String userId) {
        boolean result = true;
        try {
            FileInfoVO fileInfoVO = custUserService.getLastRongTJReportDetailByOrderSn(orderNo);
            if (null != fileInfoVO) {
                return true;
            }
            logger.debug("----------开始执行【融360预审批-融天机运营商报告异步回调】----------orderNo={},searchId={}", orderNo, searchId);
            String reportType = "html";
            RongTJReportDetailResp reportDetailResp = this.crawlReportDetail(searchId, reportType);
            TianjiReportDetailResp tianjiReportDetail = reportDetailResp
                    .getTianjiApiTaojinyunreportReportdetailResponse();
            if (tianjiReportDetail.getJson() == null) {
                logger.warn("融天机运营商报告获取异常，运营商报告数据为空！！！orderNo={},searchId={}", orderNo, searchId);
                return true;
            }
            if (StringUtils.isBlank(tianjiReportDetail.getHtml()) || "false".equals(tianjiReportDetail.getHtml())) {
                tianjiReportDetail.setHtml("运营商报告页面为空！！！");
                logger.warn("融天机运营商报告获取异常，运营商报告html页面为空！！！orderNo={},searchId={}", orderNo, searchId);
                return false;
            }
            tianjiReportDetail.setOrderNo(orderNo);
            tianjiReportDetail.setUserId(userId);
            result = this.saveRongTJReportDetail(tianjiReportDetail);
            if (result) {
                logger.debug("----------成功执行【融360预审批-融天机运营商报告异步回调】----------orderNo={},searchId={}", orderNo, searchId);
            }
        } catch (Exception e) {
            logger.warn("融天机运营商报告异步回调执行异常！！！orderNo={},searchId={}", orderNo, searchId, e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean rongReportDetail(String orderNo, String searchId, String userId, String applyId, String state) {
        boolean result = true;
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (!ApplyStatusLifeCycleEnum.APPLY_SUCCESS.getValue().equals(loanApply.getStatus())) {
            return result;
        }
        if ("report_fail".equals(state)) {
            // 运营商报告生成失败，流程继续
            logger.warn("融天机运营商报告生成失败！！！state={},orderNo={},searchId={}", state, orderNo, searchId);
            loanApplyService.updateApplyStatus(applyId, ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue());
        } else if ("report".equals(state)) {
            // 运营商报告生成成功，并成功保存，流程继续
            result = this.doRongReportDetail(orderNo, searchId, userId);
            if (result) {
                loanApplyService.updateApplyStatus(applyId, ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK.getValue());
            }
        }
        return result;
    }

    @Override
    public void resetImage(String orderSn) {
        logger.info("===============开始================{}", orderSn);
        OrderBaseInfo base = getPushBaseData(orderSn);
        OrderAppendInfo additional = getPushAdditionalData(orderSn);
        String userPhone = base.getOrderinfo().getUserMobile();
        String userId = registerOrReturnUserId(userPhone, "RONG");
        saveDoOcr(base, additional, userId);
        logger.info("==============结束================{}", orderSn);
    }

    public static String filterEmoji(String source) {
        if (!StringUtils.isEmpty(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        } else {
            return source;
        }
    }

    @Override
    public TaskResult repayStatusFeedbackToRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> xianJinCardCallBack = JedisUtils.getMap(Global.RONG_PAY_FEEDBACK);
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
                        Thread.sleep(1000);
                        if (count >= 400) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderSn = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
                        if (orderSn != null) {
                            logger.info("360放款反馈{}", applyId);
                            boolean result = rongStatusFeedBackService.rongLendStatusFeedBack(applyId);
                            if (result) {
                                JedisUtils.mapRemove(Global.RONG_PAY_FEEDBACK, applyId);
                                succNum++;
                            }
							/*Rong360FeedBackResp rong360FeedBackResp = rongStatusFeedBackService
									.rongLendStatusFeedBack(applyId);
							if (rong360FeedBackResp.getError().equals("200")
									|| rong360FeedBackResp.getError().equals("1022")) {
								JedisUtils.mapRemove(Global.RONG_PAY_FEEDBACK, applyId);
								succNum++;
							}*/
                        } else {
                            JedisUtils.mapRemove(Global.RONG_PAY_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("360放款反馈异常");
            e.printStackTrace();
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult orderStatusFeedbackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> rongCallBackMap = JedisUtils.getMap(Global.RONG_ORDERSTATUS_FEEDBACK);
            if (rongCallBackMap != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(rongCallBackMap.entrySet());
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
                        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
                        if (orderNo != null) {
                            logger.info("----------【融360-订单状态推送】定时任务applyId={},orderNo={}----------", applyId, orderNo);
                            Rong360FeedBackResp orderResp = rongStatusFeedBackService.rongPushOrderStatus(applyId);
                            if (orderResp != null && "200".equals(orderResp.getError())) {
                                JedisUtils.mapRemove(Global.RONG_ORDERSTATUS_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.RONG_ORDERSTATUS_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("----------【融360-订单状态推送】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult settlementFeedBackOfRedis() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> xianJinCardCallBack = JedisUtils.getMap(Global.RONG_SETTLEMENT_FEEDBACK);
            if (xianJinCardCallBack != null) {
                List<Map.Entry<String, String>> callBackList = new ArrayList<>(xianJinCardCallBack.entrySet());
                Collections.sort(callBackList, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> map1, Map.Entry<String, String> map2) {
                        return map1.getValue().compareTo(map2.getValue());
                    }
                });
                for (Map.Entry<String, String> map : callBackList) {
                    String[] strings = map.getValue().split("_");
                    boolean flag = Boolean.valueOf(strings[0]);//还款结果
                    Integer repayType = Integer.valueOf(strings[1]);//还款方式
                    String time = strings[2];//还款时间
                    if (Long.parseLong(time) < (System.currentTimeMillis() - 1000 * 60 * 5)) {
                        Thread.sleep(1000);
                        if (count >= 400) {
                            break;
                        }
                        count++;
                        String repayPlanItemId = map.getKey();
                        String applyId = repayPlanItemService.getApplyIdByRepayPlanItemId(repayPlanItemId);
                        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
                        if (orderNo != null) {
                            logger.info("----------【融360-还款-订单状态，还款或展期结果，还款计划反馈】定时任务applyId={},orderNo={}," +
                                            "repayPlanItemId={}----------",
                                    applyId, orderNo, repayPlanItemId);
                            boolean result = rongStatusFeedBackService.rongSettlementStatusFeedBack(repayPlanItemId,
                                    applyId, flag, repayType);
                            if (result) {
                                JedisUtils.mapRemove(Global.RONG_SETTLEMENT_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.RONG_SETTLEMENT_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("----------【融360-还款-订单状态，还款或展期结果，还款计划反馈】定时任务异常----------", e);
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public TaskResult rongApproveStatusFeedBack() {
        TaskResult taskResult = new TaskResult();
        int succNum = 0;
        int count = 0;
        try {
            Map<String, String> xianJinCardCallBack = JedisUtils.getMap(Global.RONG_APPROVE_FEEDBACK);
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
                        Thread.sleep(1000);
                        if (count >= 400) {
                            break;
                        }
                        count++;
                        String applyId = map.getKey();
                        String orderSn = applyTripartiteRong360Service.getThirdIdByApplyId(applyId);
                        if (orderSn != null) {
                            logger.info("360审批反馈{}", applyId);
                            boolean result = rongStatusFeedBackService.rongApproveStatusFeedBack(applyId);
                            if (result) {
                                JedisUtils.mapRemove(Global.RONG_APPROVE_FEEDBACK, applyId);
                                succNum++;
                            }
                        } else {
                            JedisUtils.mapRemove(Global.RONG_APPROVE_FEEDBACK, applyId);
                            succNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("360审批反馈异常");
            e.printStackTrace();
        }
        taskResult.setSuccNum(succNum);
        taskResult.setFailNum(count - succNum);
        return taskResult;
    }

    @Override
    public void handPush(String start, String end, int size) {
        Date startDate = DateUtils.parse(start);
        Date endDate = DateUtils.parse(end);
        // Page<ApplyListVO> page = new Page<ApplyListVO>();
        // page.setPageSize(size);
        // page.setPageNo(1);
        // page.setOrderBy("apply_time asc");
        ApplyListOP op = new ApplyListOP();
        Date now = new Date();
        Date startTime = startDate;// 精确到天
        Date endTime = endDate;// 精确到分钟
        op.setApplyTimeStart(startTime);
        op.setApplyTimeEnd(endTime);
        op.setStatusList(Arrays.asList(514, 610, 611, 612, 710, 711));
        List<ApplyListVO> page1 = loanApplyManager.getLoanApplyList1(op);
        for (ApplyListVO vo : page1) {
            logger.info("=================360测试工单号================" + vo.getId());
            try {
                if (!applyTripartiteRong360Service.isExistApplyId(vo.getId())) {
                    logger.debug("【融360】三方订单表不存在此订单-{}", vo.getId());
                    return;
                }
                Thread.sleep(500);
                rongStatusFeedBackService.rongLendStatusFeedBack(vo.getId());
                logger.debug("【融360】回调成功{}", vo.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handPush1(String applyId) {
        try {
            rongStatusFeedBackService.rongLendStatusFeedBack(applyId);
            logger.debug("【融360】单独回调{}", applyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void HandleData() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("SELECT * from loan_apply_tripartite_rong360 b where  b.tripartite_no IN (SELECT REPLACE(temp,\"\t\",\"\") FROM a_temp_360) ");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(stringBuffer.toString());
        for (Map<String, Object> map : list) {
            String id = String.valueOf(map.get("apply_id"));
            if (id.equals(null)) {
                break;
            }
            XianJinCardUtils.rongPayFeedback(id);
        }
    }

    @Override
    public String handRefuse(String orderNo) {
        String result = "";
        try {
            RongApproveFeedBackOP rongApproveFeedBackOP = new RongApproveFeedBackOP();
            rongApproveFeedBackOP.setOrderNo(orderNo);
            rongApproveFeedBackOP.setConclusion("40");// 审批不通过
            rongApproveFeedBackOP.setRefuseTime(String.valueOf(System.currentTimeMillis() / 1000));
            rongApproveFeedBackOP.setRemark("有未完成订单");
            Map<String, String> params = new HashMap<>();
            params.put("biz_data", JsonMapper.toJsonString(rongApproveFeedBackOP));
            Rong360FeedBackResp resp = RongClient.rongPost(Rong360ServiceEnums.ORDER_APPROVEFEEDBACK.getMethod(),
                    params, Rong360FeedBackResp.class);
            resp.getMSg();
            result = resp.getMSg();
        } catch (Exception e) {
            e.printStackTrace();
            result = "异常";
        }
        return result;
    }

    @Override
    public String handCancle(String orderNo) {
        String result = "";
        try {
            RongOrderFeedBackOP rongOrderFeedBackOP = new RongOrderFeedBackOP();
            rongOrderFeedBackOP.setOrderNo(orderNo);
            rongOrderFeedBackOP.setOrderStatus(161);// 贷款取消
            rongOrderFeedBackOP.setUpdateTime((int) (System.currentTimeMillis() / 1000));
            Map<String, String> params = new HashMap<>();
            params.put("biz_data", JsonMapper.toJsonString(rongOrderFeedBackOP));
            Rong360FeedBackResp resp = RongClient.rongPost(Rong360ServiceEnums.ORDER_ORDERFEEDBACK.getMethod(), params,
                    Rong360FeedBackResp.class);
            if (resp.getError().equals("200")) {
                // 三方订单表逻辑删除
                int r = applyTripartiteRong360Service.delOrderNo(orderNo);
            }
            logger.debug("orderNo={}【融360-订单状态反馈APP】响应结果：{}", orderNo, resp.toString());
            logger.debug("----------结束执行【融360-贷款取消-订单状态反馈APP】请求orderNo={}----------", orderNo);
        } catch (Exception e) {
            e.printStackTrace();
            result = "异常";
        }
        return result;
    }

    @Override
    public CreateAccountVO createAccount(String serviceUrl, Map<String, String> params, Map<String, String> headerMap) {
        CreateAccountVO vo = new CreateAccountVO();
        String result = HttpClientUtils.postForPair(serviceUrl, params, headerMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null && jsonObject.getJSONObject("data") != null) {
            String openUrl = jsonObject.getJSONObject("data").getString("url");
            vo.setBindCardUrl(openUrl);
        }
        return vo;
    }

    @Override
    public ConfirmWithdrawVO confirmWithdraw(String serviceUrl, Map<String, String> params,
                                             Map<String, String> headerMap) {
        ConfirmWithdrawVO vo = new ConfirmWithdrawVO();
        String result = HttpClientUtils.postForPair(serviceUrl, params, headerMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null) {
            String cashUrl = jsonObject.getString("url");
            vo.setCashUrl(cashUrl);
        }
        return vo;
    }

}
