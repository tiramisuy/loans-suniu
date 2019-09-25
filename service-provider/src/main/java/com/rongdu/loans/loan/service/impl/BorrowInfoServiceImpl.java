/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.service.impl;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.getDesc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.compute.CostUtils;
import com.rongdu.loans.loan.vo.BorrowerInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.FTPUtil;
import com.rongdu.common.utils.IdcardUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.oss.OOSUtils;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.AreaManager;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.common.P2PAreaUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.entity.CustUserInfo;
import com.rongdu.loans.cust.manager.CustUserInfoManager;
import com.rongdu.loans.cust.manager.CustUserManager;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.DegreeEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.MaritalEnum;
import com.rongdu.loans.enums.MaritalTFLEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.hanjs.op.HanJSOrderOP;
import com.rongdu.loans.hanjs.service.HanJSUserService;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.koudai.service.PayLogService;
import com.rongdu.loans.loan.dto.TFLAssetResponseDTO;
import com.rongdu.loans.loan.dto.TFLSmartAssetDTO;
import com.rongdu.loans.loan.dto.UpdateLoanInfoDTO;
import com.rongdu.loans.loan.dto.UploadPhotoDTO;
import com.rongdu.loans.loan.entity.BorrowInfo;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.manager.BorrowInfoManager;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.service.BorrowInfoService;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.tongrong.manager.TongrongPayLogManager;
import com.rongdu.loans.tongrong.service.TRPayService;
import com.rongdu.loans.tongrong.vo.TRPayVO;

/**
 * 借款标的推送-业务逻辑实现类
 *
 * @author zhangxiaolong
 * @version 2017-07-22
 */
@Service("borrowInfoService")
public class BorrowInfoServiceImpl extends BaseService implements BorrowInfoService {

    public static Logger logger = LoggerFactory.getLogger(BorrowInfoServiceImpl.class);

    public static String PRODUCT_NAME_PREFIX = "小钱包-FN";
    public static final boolean STOP_PUSH = false;

    /**
     * 借款标的推送-实体管理接口
     */
    @Autowired
    private BorrowInfoManager borrowInfoManager;
    @Autowired
    private LoanApplyManager loanApplyManager;
    @Autowired
    private FileInfoManager fileInfoManager;
    @Autowired
    private CustUserManager userManager;
    @Autowired
    private CustUserInfoManager userInfoManager;
    @Autowired
    private AreaManager areaManager;
    @Autowired
    private ContractService contractService;
    @Autowired
    private KDPayService kdPayService;
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TongrongPayLogManager tongrongPayLogManager;
    @Autowired
    private TRPayService tRPayService;
    @Autowired
    private HanJSUserService hanJSUserService;
    @Autowired
    private com.rongdu.loans.loan.service.PayLogService loanPaylogService;

    @Override
    public TaskResult push() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("推送资产开始。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("push_status", ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getValue()));
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("推送资产结束，暂无待推送数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        wirteLog(list);

        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            try {
                Thread.sleep(2000);

                int payChannel = info.getPayChannel();
                // 线上推标放款
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_ONLINE.getValue()) {
                    boolean flag = processAdminSendBorrow(info);
                    if (flag) {
                        success++;
                    } else {
                        logger.error("推送资产失败,info = " + JsonMapper.toJsonString(info));
                        fail++;
                    }
                } else {
                    logger.error("推送资产失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                logger.error("推送资产失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            }

        }

        long endTime = System.currentTimeMillis();
        logger.info("推送资产结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    public TaskResult pushToKoudai() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("口袋放款开始。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Criteria criteria = new Criteria();
        criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_KOUDAI.getValue()));
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("口袋放款结束，暂无待放款数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        wirteLog(list);

        BigDecimal koudaiDayMaxAmt = new BigDecimal(configService.getValue("koudai_day_max_amt"));
        BigDecimal currPayedAmt = payLogService.sumCurrPayedAmt();
        logger.info("口袋每日放款限额:" + koudaiDayMaxAmt.toString());
        logger.info("口袋今日已放款:" + currPayedAmt.toString());
        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            try {
                Thread.sleep(2000);

                BigDecimal servAmt = info.getBorrowAmt().multiply(info.getServFeeRate());
                BigDecimal payAmt = info.getBorrowAmt().subtract(servAmt);
                currPayedAmt = currPayedAmt.add(payAmt);
                if (currPayedAmt.compareTo(koudaiDayMaxAmt) > 0) {
                    logger.error("口袋放款当日金额超限");
                    break;
                }
                int payChannel = info.getPayChannel();
                // 口袋放款
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI.getValue()) {
                    processKoudaiPay(info);
                    success++;
                } else {
                    logger.error("口袋放款失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                logger.error("口袋放款失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            }

        }

        long endTime = System.currentTimeMillis();
        logger.info("口袋放款结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    public TaskResult pushToLeshi() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("乐视放款开始。");
        int leshiPayType = Integer.parseInt(configService.getValue("leshi_pay_type"));// 放款类型
        // 1=全部，2=单期，3=多期
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Date pushEndTime = new Date(new Date().getTime() - 10 * 60 * 1000);// 精确到分钟
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("push_status", ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(pushEndTime, "yyyy-MM-dd HH:mm:ss")));
        if (leshiPayType == 2) {// 单期
            criteria.and(Criterion.eq("repay_method", RepayMethodEnum.ONE_TIME.getValue()));
        } else if (leshiPayType == 3) {// 多期
            criteria.and(Criterion.eq("repay_method", RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue()));
        }
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("乐视放款结束，暂无待放款数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }

        BigDecimal leshiDayMaxAmt = new BigDecimal(configService.getValue("leshi_day_max_amt"));
        BigDecimal currPayedAmt = borrowInfoManager.sumLeshiPayedAmt();
        logger.info("乐视每日放款限额:" + leshiDayMaxAmt.toString());
        logger.info("乐视今日已放款:" + currPayedAmt.toString());

        wirteLog(list);
        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            try {
                Thread.sleep(2000);

                currPayedAmt = currPayedAmt.add(info.getBorrowAmt());
                if (currPayedAmt.compareTo(leshiDayMaxAmt) > 0) {
                    logger.error("乐视放款当日金额超限");
                    break;
                }

                int payChannel = info.getPayChannel();
                // 乐视放款
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_LESHI.getValue()) {
                    updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
                    contractService.processLeshiLendPay(info.getApplyId(), new Date());
                    success++;
                } else {
                    updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue());
                    logger.error("乐视放款失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue());
                logger.error("乐视放款失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            }
        }

        long endTime = System.currentTimeMillis();
        logger.info("乐视放款结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    /**
     * 通联放款
     *
     * @return
     */
    public TaskResult pushToTonglian() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("通联放款开始。");
        // 放款类型
        // 1=全部，2=单期，3=多期
        int leshiPayType = Integer.parseInt(configService.getValue("tonglian_pay_type"));
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Date pushEndTime = new Date(new Date().getTime() - 10 * 60 * 1000);// 精确到分钟
        Criteria criteria = new Criteria();
        criteria.add(Criterion.eq("push_status", ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(pushEndTime, "yyyy-MM-dd HH:mm:ss")));
        if (leshiPayType == 3) {// 多期
            criteria.and(Criterion.eq("repay_method", RepayMethodEnum.PRINCIPAL_INTEREST_DAY.getValue()));
        }
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("通联放款结束，暂无待放款数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }

        BigDecimal tonglianDayMaxAmt = new BigDecimal(configService.getValue("tonglian_day_max_amt"));
        BigDecimal currPayedAmt = borrowInfoManager.sumLeshiPayedAmt();
        logger.info("通联每日放款限额:" + tonglianDayMaxAmt.toString());
        logger.info("通联今日已放款:" + currPayedAmt.toString());

        wirteLog(list);
        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            String lockKey = Global.BORROW_INFO_LOCK + info.getApplyId();
            String requestId = String.valueOf(System.nanoTime());// 请求标识
            try {
                Thread.sleep(2000);

                boolean lock = JedisUtils.setLock(lockKey, requestId, 5 * 60);
                if (!lock) {
                    logger.error("当前贷款编号{},已被锁定.", info.getApplyId());
                    continue;
                }

                currPayedAmt = currPayedAmt.add(info.getBorrowAmt());
                if (currPayedAmt.compareTo(tonglianDayMaxAmt) > 0) {
                    logger.error("通联放款当日金额超限");
                    break;
                }

                int payChannel = info.getPayChannel();
                // 通联放款
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_TONGLIAN.getValue()) {
                    updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
                    contractService.processTltLendPay(info.getApplyId(), new Date());
                    success++;
                } else {
                    updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue());
                    logger.error("通联放款失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                updateBorrowStatus(info.getApplyId(), ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue());
                logger.error("通联放款失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            } finally {
                // 解除orderNo并发锁
                JedisUtils.releaseLock(lockKey, requestId);
            }
        }

        long endTime = System.currentTimeMillis();
        logger.info("通联放款结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    @Transactional
    boolean processAdminSendBorrow(BorrowInfo borrowInfo) {
        String applyId = borrowInfo.getApplyId();
        String userId = borrowInfo.getUserId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        CustUser user = userManager.getById(userId);
        if (user == null || StringUtils.isBlank(user.getAccountId())) {
            logger.error("accountId不存在！对应userId={}", userId);
            throw new RuntimeException("accountId不存在, userId = " + userId);
        }
        // 修复borrow_info标accountId为空情况
        if (StringUtils.isBlank(borrowInfo.getAccountId())) {
            borrowInfo.setAccountId(user.getAccountId());
            borrowInfoManager.updateByIdSelective(borrowInfo);
        }
        CustUserInfo userInfo = userInfoManager.getById(userId);

        // 更新用户贷款资料
        boolean flag = updateInfoReq(Integer.parseInt(user.getAccountId()), user, userInfo, borrowInfo.getPartnerId());
        if (flag) {
            /** 组装参数 */
            TFLSmartAssetDTO tflSmartAssetDTO = getSmartAssetDTO(borrowInfo, loanApply);
            /** 推送资产 */
            String url = Global.getConfig("deposit.saveAsset.url");
            String param = JsonMapper.getInstance().toJson(tflSmartAssetDTO);
            logger.info("推标http请求:{}", param);
            String result = RestTemplateUtils.getInstance().postForJsonRaw(url, param);
            logger.info("推标http响应:{}", result);
            TFLAssetResponseDTO response = JsonMapper.getInstance().fromJson(result, TFLAssetResponseDTO.class);
            if (response != null && StringUtils.equals(response.getStatus(), Global.BANKDEPOSIT_SUCCSS_OK)) {
                /** 更新推送状态、修改申请单状态、新增操作日志 */
                update(response, borrowInfo);
                /** 上传图片 */
                List<FileInfo> fileInfoList = fileInfoManager.getLastFileByUserId(borrowInfo.getUserId(),
                        Arrays.asList(FileBizCode.FRONT_IDCARD.getBizCode(), FileBizCode.BACK_IDCARD.getBizCode()));
                /** 上传到阿里云OSS,推送到投复利 */
                uploadXJDPhoto(fileInfoList, response.getLid(), applyId, borrowInfo.getAccountId());
            } else {
                Date now = new Date();
                borrowInfo.setPushStatus(ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue());
                borrowInfo.setPushTime(now);
                borrowInfo.setUpdateTime(now);
                borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
                borrowInfoManager.updatePushStatus(borrowInfo);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 发送上传图片信息
     *
     * @param response
     * @param fileInfo
     * @param info
     */
    private void sendUploadInfo(int lid, String fileUrl, String remark) {
        UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();
        uploadPhotoDTO.setLid(lid);
        uploadPhotoDTO.setUrl(fileUrl);// url地址：文件夹名/文件名，用分号分隔
        uploadPhotoDTO.setRemark(remark);// 多个备注用;分隔
        String uploadPhotoParam = JsonMapper.getInstance().toJson(uploadPhotoDTO);
        logger.info("推送上传图片，标ID = {}, param = {}", lid, uploadPhotoParam);
        // 只推送信息，不做成功判断
        RestTemplateUtils.getInstance().postForJsonRaw(Global.getConfig("deposit.uploadPhoto.url"), uploadPhotoParam);
    }

    private void wirteLog(List<BorrowInfo> list) {
        StringBuffer sb = new StringBuffer("当前推送资产数据：");
        for (BorrowInfo info : list) {
            sb.append("【applyId = ").append(info.getApplyId()).append(", pushStatus = ").append(info.getPushStatus())
                    .append("】 ");
        }
        logger.info(sb.toString());
    }

    /*
     * public boolean queryUploadInfo(int userId){ UpdateLoanInfoDTO updateDto =
     * new UpdateLoanInfoDTO(); updateDto.setUid(userId); String param =
     * JsonMapper.getInstance().toJson(updateDto); String result =
     * RestTemplateUtils.getInstance().postForJsonRaw(
     * "https://al.toufuli.com/PaydayLoan/QueryLoanInfo", param);
     * TFLAssetResponseDTO response = JsonMapper.getInstance().fromJson(result,
     * TFLAssetResponseDTO.class); if(response != null &&
     * StringUtils.equals(response.getStatus(), Global.BANKDEPOSIT_SUCCSS_OK)){
     * return true; } return false; }
     */

    /**
     * 更新用户贷款资料
     *
     * @param userId
     * @return
     */
    private boolean updateInfoReq(int accountId, CustUser user, CustUserInfo userInfo, String productId) {
        Map<String, String> extInfo = (Map<String, String>) JsonMapper.fromJsonString(userInfo.getRemark(), Map.class);
        if (extInfo == null && !LoanProductEnum.XJDFQ.getId().equals(productId)
                && !LoanProductEnum.XJD.getId().equals(productId)) {
            logger.error("推标时更新贷款资料extInfo不存在,accountId：{}", accountId);
            return false;
        }
        int uid = accountId; // 用户ID
        String job_company_type = null;// 公司类型ID
        String job_company_scale = null;// 公司规模ID
        String have_house = null;// 是否有房ID
        String house_loan = null;// 有无房贷ID
        String have_car = null;// 是否有车ID
        String car_loan = null;// 有无车贷ID
        String job_title = null;// 职位文本
        String job_industry = null;// 工作行业ID
        Integer job_salary = null;// 月收入ID
        String job_years = null;// 工作年数ID
        String education = null; // 学历ID
        int age = IdcardUtils.getAgeByIdCard(user.getIdNo());
        if (age <= 22) {
            education = "1";// 高中
            job_years = "2"; // 工作1-3年
        } else if (age > 22 && age <= 27) {
            education = "2";// 大专
            job_years = "2"; // 工作1-3年
        } else {
            education = userInfo.getDegree();
            job_years = StringUtils.isNotBlank(userInfo.getWorkYear()) ? userInfo.getWorkYear() : "3";
        }
        if (LoanProductEnum.XJDFQ.getId().equals(productId) || LoanProductEnum.XJD.getId().equals(productId)) {
            job_company_type = "8";
            job_company_scale = "3";
            have_house = "2";
            house_loan = "2";
            have_car = "2";
            car_loan = "2";
            job_title = "职员";
            job_industry = "19";
            job_salary = 5;
        } else {
            job_company_type = extInfo.get("workCategory");// 公司类型ID
            job_company_scale = extInfo.get("workSize");// 公司规模ID
            have_house = extInfo.get("house");// 是否有房ID
            house_loan = extInfo.get("houseLoan");// 有无房贷ID
            have_car = extInfo.get("car");// 是否有车ID
            car_loan = extInfo.get("carLoan");// 有无车贷ID
            job_title = userInfo.getWorkPosition();// 职位文本
            job_industry = userInfo.getIndustry();// 工作行业ID
            job_salary = userInfo.getIndivMonthIncome();// 月收入ID
        }
        String job_province = userInfo.getWorkProvince() != null ? userInfo.getWorkProvince()
                : userInfo.getRegProvince();// 工作省份ID
        String job_city = userInfo.getWorkCity() != null ? userInfo.getWorkCity() : userInfo.getRegCity();// 工作城市ID
        Integer marriage = userInfo.getMarital() != null ? userInfo.getMarital() : MaritalTFLEnum.UNMARRIED.getValue();// 是否结婚ID
        Integer gender = user.getSex();// 性别ID

        Map<String, String> areaMap = areaManager.getAreaCodeAndName();
        String job_province_name = areaMap.get(job_province);
        String job_city_name = areaMap.get(job_city);
        if (StringUtils.isBlank(job_city_name)) {
            job_province_name = "湖北省";
            job_city_name = "武汉市";
        }

        UpdateLoanInfoDTO updateDto = new UpdateLoanInfoDTO();
        updateDto.setUid(uid);
        updateDto.setEducation(Integer.parseInt(education));
        updateDto.setJob_province(P2PAreaUtils.getProvince(job_province_name));
        updateDto.setJob_city(P2PAreaUtils.getCity(job_province_name, job_city_name));
        updateDto.setJob_industry(Integer.parseInt(job_industry));
        updateDto.setJob_company_type(Integer.parseInt(job_company_type));
        updateDto.setJob_company_scale(Integer.parseInt(job_company_scale));
        updateDto.setJob_title(job_title);
        updateDto.setJob_salary(job_salary);
        updateDto.setJob_years(Integer.parseInt(job_years));
        updateDto.setHave_house(Integer.parseInt(have_house));
        updateDto.setHouse_loan(Integer.parseInt(house_loan));
        updateDto.setHave_car(Integer.parseInt(have_car));
        updateDto.setCar_loan(Integer.parseInt(car_loan));
        updateDto.setMarriage(1 == marriage ? marriage : MaritalTFLEnum.UNMARRIED.getValue());
        updateDto.setGender(gender);

        String url = Global.getConfig("deposit.updateInfo.url");
        String param = JsonMapper.getInstance().toJson(updateDto);
        logger.info("推标时更新贷款资料http请求：{}", JsonMapper.toJsonString(param));
        String result = RestTemplateUtils.getInstance().postForJsonRaw(url, param);
        TFLAssetResponseDTO response = JsonMapper.getInstance().fromJson(result, TFLAssetResponseDTO.class);
        logger.info("推标时更新贷款资料http响应：{}", JsonMapper.toJsonString(response));
        boolean flag = false;
        if (response != null && StringUtils.equals(response.getStatus(), Global.BANKDEPOSIT_SUCCSS_OK)) {
            flag = true;
        }
        if (flag) {
            flag = changeUserVersion(accountId, user.getRegisterTime(), productId);
        }
        return flag;
    }

    /**
     * 更新现金贷存量用户类型
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean changeUserVersion(Integer accountId, Date registerDate, String productId) {
        try {
            if (DateUtils.isBefore(registerDate, DateUtils.parseDate("2018-06-01 23:59:59"))
                    && LoanProductEnum.XJDFQ.getId().equals(productId)) {
                Map<String, String> param = new HashMap<String, String>();
                param.put("uid", accountId.toString());
                String url = Global.getConfig("deposit.changeVersion.url");
                logger.info("更新现金贷存量用户类型http请求：[{}]", JsonMapper.toJsonString(param));
                Map<String, Object> result = (Map<String, Object>) RestTemplateUtils.getInstance().postForObject(url,
                        param, Map.class);
                logger.info("更新现金贷存量用户类型http响应：[{}]", JsonMapper.toJsonString(result));
                if (null != result && StringUtils.equals((String) result.get("status"), Global.BANKDEPOSIT_SUCCSS_OK)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("更新现金贷存量用户类型失败", e);
        }
        return true;
    }

    private TFLSmartAssetDTO getSmartAssetDTO(BorrowInfo info, LoanApply loanApply) {
        String repayMethodStr = "m";
        RepayMethodEnum repayMethod = RepayMethodEnum.get(info.getRepayMethod());
        switch (repayMethod) {
            case INTEREST:
                repayMethodStr = "m";
                break;
            case EXPIRE:
                repayMethodStr = "i";
                break;
            case INTEREST_DAY:
                repayMethodStr = "m";
            case PRINCIPAL_INTEREST:
                repayMethodStr = "m";
                break;
            case PRINCIPAL_INTEREST_DAY:
                repayMethodStr = "m";
                break;
            default:
                repayMethodStr = "m";
                break;
        }
        TFLSmartAssetDTO tflSmartAssetDTO = new TFLSmartAssetDTO();
        tflSmartAssetDTO.setTitle(info.getTitle() + info.getBorrowAmt().doubleValue() / 10000 + "万");// 贷款标题
        tflSmartAssetDTO.setAmount(info.getBorrowAmt().doubleValue());// 贷款金额
        tflSmartAssetDTO.setApr(info.getActualRate());// 利率
        tflSmartAssetDTO.setDeadline_type(info.getPeriodUnit());// 分期类型,M=月，D=天
        int deadline = 3;// 分期期数
        tflSmartAssetDTO.setDeadline(deadline);
        tflSmartAssetDTO.setChargefortrouble(info.getServFeeRate());// 服务费率
        tflSmartAssetDTO.setRepay_method(repayMethodStr);// 贷款方式
        tflSmartAssetDTO.setPurpose(getPurpose());
        String companyName = getCompanyName();
        String description = String.format(ShortMsgTemplate.DESCRIPTION, companyName, info.getBorrowAmt(), deadline,
                info.getBorrowAmt(), info.getBorrowAmt(), info.getBorrowAmt());
        tflSmartAssetDTO.setDescription(description);

        tflSmartAssetDTO.setUid(Integer.parseInt(info.getAccountId()));// 注册用户编号
        tflSmartAssetDTO.setDays(30);// 贷款期限
        tflSmartAssetDTO.setType(info.getBorrowType());// 标的类型
        // 标的产品类型
        if (LoanProductEnum.XJDFQ.getId().equals(info.getPartnerId())) {
            tflSmartAssetDTO.setV("7");
        } else {
            tflSmartAssetDTO.setV("8");
        }
        return tflSmartAssetDTO;
    }

    /**
     * 更新借款信息
     *
     * @param response
     * @return
     */
    private int update(TFLAssetResponseDTO response, BorrowInfo info) {
        Date now = new Date();
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(info.getApplyId());
        if (response.getLid() != 0) {
            borrowInfo.setOutsideSerialNo("" + response.getLid());
        }
        borrowInfo.setPushTime(now);
        borrowInfo.setUpdateTime(now);
        borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        borrowInfo.setRemark(response.getMessage());
        Integer pushStatus = StringUtils.equals(response.getStatus(), "OK")
                ? ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()
                : ApplyStatusLifeCycleEnum.PUSH_FAIL.getValue();
        borrowInfo.setPushStatus(pushStatus);
        int num = borrowInfoManager.updatePushStatus(borrowInfo);
        if (num > 0) {
            updateLoanApplyInfo(borrowInfo);
            savePushLog(borrowInfo, info);
        }
        return num;
    }

    private int updateLoanApplyInfo(BorrowInfo borrowInfo) {
        LoanApply loanApply = new LoanApply();
        loanApply.setId(borrowInfo.getApplyId());
        loanApply.setStage(ApplyStatusLifeCycleEnum.getStage(borrowInfo.getPushStatus()));
        loanApply.setStatus(borrowInfo.getPushStatus());
        loanApply.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        loanApply.setUpdateTime(new Date());
        return loanApplyManager.updateLoanApplyInfo(loanApply);
    }

    private int updateBorrowStatus(String applyId, int status) {
        Date now = new Date();
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(applyId);
        borrowInfo.setPushTime(now);
        borrowInfo.setUpdateTime(now);
        borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        borrowInfo.setPushStatus(status);
        return borrowInfoManager.updatePushStatus(borrowInfo);
    }

    private void savePushLog(BorrowInfo after, BorrowInfo before) {
        logger.info("推标日志：{},{},{},{},{}", after.getApplyId(),
                ApplyStatusLifeCycleEnum.getStage(before.getPushStatus()), before.getPushStatus(),
                ApplyStatusLifeCycleEnum.getStage(after.getPushStatus()), after.getPushStatus());
        /*
         * try { OperationLog operationLog = new OperationLog();
         * operationLog.preInsert(); operationLog.setUserId(before.getUserId());
         * operationLog.setPreviousStage(ApplyStatusLifeCycleEnum
         * .getStage(before.getPushStatus()));
         * operationLog.setPreviousStatus(before.getPushStatus());
         * operationLog.setApplyId(after.getApplyId());
         * operationLog.setStatus(after.getPushStatus());
         * operationLog.setStage(ApplyStatusLifeCycleEnum.getStage(after
         * .getPushStatus())); operationLog.setSource(Global.SOURCE_SYSTEM);
         * operationLog.setTime(new Date());
         * operationLog.setOperatorId(Global.DEFAULT_OPERATOR_ID);
         * operationLog.setOperatorName(Global.DEFAULT_OPERATOR_NAME);
         * operationLog.setRemark("定时任务推送资产");
         * operationLogManager.saveOperationLog(operationLog); } catch
         * (Exception e) { logger.error( "保存标的推送操作日志失败，参数：" +
         * JsonMapper.getInstance().toJson(after), e); }
         */
    }

    private void uploadPhoto(int lid, String applyId) {
        String photoCacheKey = "BORROW_FILE_CACHE_KEY_" + applyId;
        String url = JedisUtils.get(photoCacheKey);
        if (url != null) {
            UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();
            uploadPhotoDTO.setLid(lid);
            uploadPhotoDTO.setUrl(url);// url地址：文件夹名/文件名，用分号分隔
            uploadPhotoDTO.setRemark("客户资料");// 多个备注用;分隔
            String uploadPhotoParam = JsonMapper.getInstance().toJson(uploadPhotoDTO);
            logger.info("推标上传图片http请求:{}", uploadPhotoParam);
            // 只推送信息，不做成功判断
            String response = RestTemplateUtils.getInstance()
                    .postForJsonRaw(Global.getConfig("deposit.uploadPhoto.url"), uploadPhotoParam);
            logger.info("推标上传图片http响应:{}", response);
        }
    }

    /**
     * 上传身份证正反面到ftp服务器，并推送上传信息到投复利
     *
     * @param lid
     * @param fileInfoList
     */
    public void uploadImgListToFTPAndPush(int lid, List<FileInfo> fileInfoList) {

        FTPUtil ftpUtil = new FTPUtil();
        // 设置ftp连接参数
        ftpUtil.setConfig(Global.getFTPServer(), Global.getFTPPort(), Global.getFTPUsername(), Global.getFTPPwd());
        // 连接服务器
        ftpUtil.connectServer();
        List<String> uploadFTPUrl = new ArrayList<String>();
        List<String> fileRemark = new ArrayList<String>();
        // 上传多个图片，并记录上传成功的图片相对路径和图片描述信息
        for (FileInfo fileInfo : fileInfoList) {
            if (!StringUtils.isBlank(fileInfo.getUrl()) && !StringUtils.isBlank(fileInfo.getRelativePath())) {
                boolean uploadFlag = ftpUtil.uploadServerImageByFtp(fileInfo.getUrl(),
                        Global.getConfig("ftp.rootPath") + fileInfo.getRelativePath());
                if (uploadFlag) {
                    uploadFTPUrl
                            .add(fileInfo.getRelativePath().substring(0, fileInfo.getRelativePath().lastIndexOf("/"))
                                    + fileInfo.getUrl().substring(fileInfo.getUrl().lastIndexOf("/")));
                    fileRemark.add(StringUtils.isBlank(fileInfo.getBizName())
                            ? (fileInfo.getBizCode().equals("front_idcard") ? "身份证正面" : "身份证反面")
                            : fileInfo.getBizName());
                }
            }
        }
        // 关闭连接
        ftpUtil.closeConnect();
        // 推送上传图片信息
        if (uploadFTPUrl.size() > 0) {
            String uploadUrl = StringUtils.join(uploadFTPUrl.toArray(new String[uploadFTPUrl.size()]), ";");
            String uploadRemark = StringUtils.join(fileRemark.toArray(new String[fileRemark.size()]), ";");
            sendUploadInfo(lid, uploadUrl, uploadRemark);
        }
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getCarInfo(String CardCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String year = CardCode.substring(6).substring(0, 4);// 得到年份
        String yue = CardCode.substring(10).substring(0, 2);// 得到月份
        // String day=CardCode.substring(12).substring(0,2);//得到日
        int sex;
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = 2;
        } else {
            sex = 1;
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    /**
     * 上传图片
     *
     * @param fileInfoList
     * @param lid
     */
    private void uploadXJDPhoto(List<FileInfo> fileInfoList, int lid, String applyId, String uid) {
        List<String> uploadUrls = new ArrayList<String>();
        String dirs = "upload/loan/xjdapp/";
        String uploadUrl = null;
        try {
            // 上传到阿里云
            for (FileInfo fileInfo : fileInfoList) {
                InputStream inputStream = new URL(fileInfo.getUrl()).openStream();
                String key = dirs + DateUtils.getDate("yyyy/MM/dd") + "/" + fileInfo.getFileName();
                OOSUtils.uploadCardFile(uid, applyId, inputStream, key);
                uploadUrls.add(key);
            }
            if (uploadUrls.size() > 0) {
                uploadUrl = StringUtils.join(uploadUrls.toArray(new String[uploadUrls.size()]), ";");
            }
            // 推送到投复利
            if (null != uploadUrl) {
                UploadPhotoDTO uploadPhotoDTO = new UploadPhotoDTO();
                uploadPhotoDTO.setLid(lid);
                uploadPhotoDTO.setUrl(uploadUrl);// url地址：文件夹名/文件名，用分号分隔
                uploadPhotoDTO.setRemark("客户资料");// 多个备注用;分隔
                String uploadPhotoParam = JsonMapper.getInstance().toJson(uploadPhotoDTO);
                logger.info("推标上传图片http请求:{}", uploadPhotoParam);
                // 只推送信息，不做成功判断
                String response = RestTemplateUtils.getInstance()
                        .postForJsonRaw(Global.getConfig("deposit.uploadPhoto.url"), uploadPhotoParam);
                logger.info("推标上传图片http响应:{}", response);
            }
        } catch (Exception e) {
            logger.error("上传失败", e);
        }
    }

    private String getCompanyName() {
        String[] company = {"某科技公司", "某金融公司", "某房产公司", "某服务公司", "某文化公司", "某某公司"};
        return company[(int) (Math.random() * company.length)];
    }

    private String getPurpose() {
        String[] purpose = {"电器", "住宿房租", "旅游度假", "家具家纺", "家居百货", "电子数码", "婚庆", "服饰鞋包", "运动健身", "生活费", "化妆护肤", "整形美容",
                "医疗", "技能培训", "保险"};
        return purpose[(int) (Math.random() * purpose.length)];
    }

    /**
     * 代扣服务费成功通知到p2p平台
     *
     * @param applyId
     */
    public void whithholdServFeeSuccessNotify(String applyId) {
        // 598代扣成功后不通知投复利
        // String lid = borrowInfoManager.getOutSideNumByApplyId(applyId);
        // if (StringUtils.isBlank(lid)) {
        // logger.error("标的标号不存在,applyId={}", applyId);
        // }
        // String url =
        // Global.getConfig("deposit.whithholdServFeeSuccessNotify.url");
        // Map<String, Object> m = new HashMap<String, Object>();
        // m.put("lid", lid);
        // String param = JsonMapper.getInstance().toJson(m);
        // logger.info("满标代扣服务费成功通知http请求:{}", param);
        // String result = RestTemplateUtils.getInstance().postForJsonRaw(url,
        // param);
        // logger.info("满标代扣服务费成功通知http响应:{},{}", lid, result);
    }

    /**
     * 取消借款通知到p2p平台
     *
     * @param applyId
     */
    @SuppressWarnings("unchecked")
    public boolean cancelLoanNotify(String applyId) {
        String lid = borrowInfoManager.getOutSideNumByApplyId(applyId);
        if (StringUtils.isBlank(lid)) {
            logger.error("标的标号不存在,applyId={}", applyId);
        }
        String url = Global.getConfig("deposit.cancelLoanNotify.url");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("lid", lid);
        m.put("isCancel", 1);// 0=不取消,1=取消
        boolean ret = false;
        try {
            String param = JsonMapper.getInstance().toJson(m);
            logger.info("取消借款通知http请求:{}", param);
            String result = RestTemplateUtils.getInstance().postForJsonRaw(url, param);
            logger.info("取消借款通知http响应:{},{}", lid, result);
            if (StringUtils.isNotBlank(result)) {
                Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(result, Map.class);
                String status = map.get("status");
                ret = "OK".equals(status) ? true : false;
            }
        } catch (Exception e) {
            logger.info("取消借款通知异常:{}", applyId);
        }
        return ret;
    }

    @Override
    public BorrowerInfoVO getByCriteria(Criteria criteria) {
        BorrowInfo borrowInfo = borrowInfoManager.getByCriteria(criteria);
        if (borrowInfo == null) {
            return null;
        }
        return BeanMapper.map(borrowInfo, BorrowerInfoVO.class);
    }

    /**
     * 口袋放款
     *
     * @param borrowInfo
     * @return
     */
    @Transactional
    void processKoudaiPay(BorrowInfo info) {
        String applyId = info.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        Date now = new Date();
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(info.getApplyId());
        borrowInfo.setPushTime(now);
        borrowInfo.setUpdateTime(now);
        borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        Integer pushStatus = ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue();
        borrowInfo.setPushStatus(pushStatus);
        int num = borrowInfoManager.updatePushStatus(borrowInfo);
        if (num > 0) {
            updateLoanApplyInfo(borrowInfo);
            // 先放款，成功后生成还款计划
            kdPayService.pay(applyId);
        }
    }

    @Override
    public TaskResult pushToTongrong() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("通融放款开始。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Date pushEndTime = new Date(new Date().getTime() - 10 * 60 * 1000);// 精确到分钟
        Criteria criteria = new Criteria();
        criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(pushEndTime, "yyyy-MM-dd HH:mm:ss")));
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("通融放款结束，暂无待放款数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        wirteLog(list);

        BigDecimal tongrongDayMaxAmt = new BigDecimal(configService.getValue("tongrong_day_max_amt"));
        BigDecimal currPayedAmt = tongrongPayLogManager.sumCurrPayedAmt();
        logger.info("通融每日放款限额:" + tongrongDayMaxAmt.toString());
        logger.info("通融今日已放款:" + currPayedAmt.toString());
        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            try {
                Thread.sleep(2000);

                /*
                 * BigDecimal servAmt =
                 * info.getBorrowAmt().multiply(info.getServFeeRate());
                 * BigDecimal payAmt = info.getBorrowAmt().subtract(servAmt);
                 * currPayedAmt = currPayedAmt.add(payAmt); if
                 * (currPayedAmt.compareTo(koudaiDayMaxAmt) > 0) {
                 * logger.error("口袋放款当日金额超限"); break; }
                 */
                int payChannel = info.getPayChannel();
                // 通融放款
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_TONGRONG.getValue()) {
                    processTongrongPay(info);
                    success++;
                } else {
                    logger.error("通融放款失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                logger.error("通融放款失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            }

        }

        long endTime = System.currentTimeMillis();
        logger.info("通融放款结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    @Transactional
    void processTongrongPay(BorrowInfo info) {
        String applyId = info.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        Date now = new Date();
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(info.getApplyId());
        borrowInfo.setPushTime(now);
        borrowInfo.setUpdateTime(now);
        borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        Integer pushStatus = ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue();
        borrowInfo.setPushStatus(pushStatus);
        int num = borrowInfoManager.updatePushStatus(borrowInfo);
        if (num > 0) {
            updateLoanApplyInfo(borrowInfo);
            // 先放款，成功后生成还款计划
            TRPayVO result = tRPayService.pay(applyId);
            if ("200".equals(result.getCode())) {
                contractService.processTongRongLendPay(applyId, new Date());
            }
        }
    }

    @Override
    public TaskResult pushToHanJS() {
        if (STOP_PUSH) {
            logger.info("暂停推标");
            return new TaskResult(0, 0);
        }
        logger.info("汉金所放款开始。");
        long starTime = System.currentTimeMillis();
        int success = 0;
        int fail = 0;
        Date pushEndTime = new Date(new Date().getTime() - 10 * 60 * 1000);// 精确到分钟
        Criteria criteria = new Criteria();
        criteria.add(Criterion.ne("push_status", ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue()));
        criteria.and(Criterion.eq("pay_channel", WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()));
        criteria.and(Criterion.lt("create_time", DateUtils.formatDate(pushEndTime, "yyyy-MM-dd HH:mm:ss")));
        List<BorrowInfo> list = borrowInfoManager.findAllByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            long endTime = System.currentTimeMillis();
            logger.info("汉金所放款结束，暂无待放款数据。执行耗时{}", endTime - starTime);
            return new TaskResult(success, fail);
        }
        wirteLog(list);

        BigDecimal hanjsDayMaxAmt = new BigDecimal(configService.getValue("hanjs_day_max_amt"));
        BigDecimal hanjscurrPayedAmt = borrowInfoManager.getHanjscurrPayedAmt();
        logger.info("汉金所每日放款限额:" + hanjsDayMaxAmt.toString());
        logger.info("汉金所今日已放款:" + hanjscurrPayedAmt.toString());
        for (BorrowInfo info : list) {
            if (success >= 50) {
                break;
            }
            try {
                Thread.sleep(2000);
                BigDecimal servAmt = info.getBorrowAmt().multiply(info.getServFeeRate());
                BigDecimal payAmt = info.getBorrowAmt().subtract(servAmt);
                hanjscurrPayedAmt = hanjscurrPayedAmt.add(payAmt);
                if (hanjscurrPayedAmt.compareTo(hanjsDayMaxAmt) > 0) {
                    logger.error("汉金所放款当日金额超限");
                    break;
                }
                int payChannel = info.getPayChannel();
                if (payChannel == WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()) {
                    processHanJSPay(info);
                    success++;
                } else {
                    logger.error("汉金所放款失败,未知的放款渠道,info = " + JsonMapper.toJsonString(info));
                    fail++;
                }
            } catch (Exception e) {
                logger.error("汉金所放款失败,info = " + JsonMapper.toJsonString(info), e);
                fail++;
            }

        }

        long endTime = System.currentTimeMillis();
        logger.info("汉金所放款结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
        return new TaskResult(success, fail);
    }

    @Transactional
    protected void processHanJSPay(BorrowInfo info) {
        String applyId = info.getApplyId();
        LoanApply loanApply = loanApplyManager.getLoanApplyById(applyId);
        if (loanApply == null) {
            logger.error("贷款申请单不存在，applyId = {}", applyId);
            throw new RuntimeException("贷款申请单不存在,applyId : " + applyId);
        }
        if (!ApplyStatusLifeCycleEnum.WAITING_PUSH.getValue().equals(loanApply.getStatus())) {
            logger.error("贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
            throw new RuntimeException(
                    "贷款申请单的状态不正确, applyId = " + applyId + ", status = " + getDesc(loanApply.getStatus()));
        }
        Date now = new Date();
        BorrowInfo borrowInfo = new BorrowInfo();
        borrowInfo.setApplyId(info.getApplyId());
        borrowInfo.setPushTime(now);
        borrowInfo.setUpdateTime(now);
        borrowInfo.setUpdateBy(Global.DEFAULT_OPERATOR_NAME);
        Integer pushStatus = ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue();
        borrowInfo.setPushStatus(pushStatus);
        int num = borrowInfoManager.updatePushStatus(borrowInfo);
        if (num > 0) {
            updateLoanApplyInfo(borrowInfo);
            CustUserInfo userInfo = userInfoManager.getById(loanApply.getUserId());
            CustUser custUser = userManager.getById(loanApply.getUserId());
            // 先放款，成功后生成还款计划
            HanJSOrderOP op = new HanJSOrderOP();
            op.setAmount(info.getBorrowAmt().subtract(CostUtils.calServFee(info.getServFeeRate(), info.getBorrowAmt()))
                    .toString());
            op.setOrderId(borrowInfo.getApplyId());
            op.setBorrowerMobile(loanApply.getMobile());
            op.setBorrowerName(loanApply.getUserName());
            op.setBorrowerIdCard(loanApply.getIdNo());
            op.setBorrowerAge(String.valueOf(IdcardUtils.getAgeByIdCard(loanApply.getIdNo())));
            String degree = userInfo.getDegree();
            if (null != degree) {
                degree = DegreeEnum.getDesc(Integer.valueOf(degree));
            } else {
                degree = "本科";
            }
            op.setBorrowerEdu(degree);
            Integer marital = userInfo.getMarital();
            String maritalDesc = null;
            if (null != marital) {
                maritalDesc = MaritalEnum.getDesc(Integer.valueOf(marital));
            } else {
                maritalDesc = "未婚";
            }
            String email = custUser.getEmail();
            if (StringUtils.isBlank(email)) {
                email = custUser.getQq() + "@qq.com";
            }
            op.setBorrowerMail(email);
            op.setBorrowerMarry(maritalDesc);
            op.setBorrowerAddr(userInfo.getResideAddr());
            op.setBorrowerUse("个人购物");
            op.setDays(loanApply.getApproveTerm().toString());
            hanJSUserService.pushBid(op);
        }
    }
}