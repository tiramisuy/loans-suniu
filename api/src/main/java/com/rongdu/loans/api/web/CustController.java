package com.rongdu.loans.api.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.FileVO;
import com.rongdu.loans.api.vo.SendMessageSafeOP;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.app.service.AppUpgradeService;
import com.rongdu.loans.app.vo.AppBanksVO;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.common.HandlerTypeEnum;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.cust.option.*;
import com.rongdu.loans.cust.service.CustCouponService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.service.MessageService;
import com.rongdu.loans.cust.vo.*;
import com.rongdu.loans.enums.*;
import com.rongdu.loans.loan.option.*;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import com.rongdu.loans.sys.web.ApiResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * 客户安全Controller
 *
 * @author sunda
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/cust")
public class CustController extends BaseController {

    @Autowired
    private CustUserService custUserService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ShortMsgService shortMsgService;

    @Autowired
    private AppUpgradeService appUpgradeService;

    @Autowired
    private RepayPlanItemService repayPlanItemService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private TltAgreementPayService tltAgreementPayService;

    @Autowired
    private AppBankLimitService appBankLimitService;

    @Autowired
    private CustCouponService custCouponService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private LoanTripProductService loanTripProductService;

    @Autowired
    private RiskBlacklistService riskBlacklistService;

    /**
     * 发送短信验证码(走验签)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendMsgVerCode", method = RequestMethod.POST, name = "发送短信验证码(走验签)")
    @ResponseBody
    public ApiResult sendMsgVerCode(@Valid SendMessageSafeOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 参数验证结果判断
        String userId = request.getHeader("userId");
        // 返回参数构建
        ApiResult result = new ApiResult();
        int msgType;
        try {
            msgType = Integer.parseInt(param.getMsgType());
        } catch (NumberFormatException e) {
            logger.warn("请输入有效的短信验证类型");
            return result.set(ErrInfo.MSG_TYPE_ERROR);
        }
        // 截取ip
        String ip = Servlets.getIpAddress(request);

        // 渠道
        String channel = param.getChannel();
        if (StringUtils.isBlank(channel)) {
            channel = ChannelEnum.JUQIANBAO.getCode();
            param.setChannel(channel);
        }

        // 发送验证码
        // 调短息验证码服务获取短信验证码
        String msgVerCode = "";
         if (StringUtils.equals(param.getMsgType(), ShortMsgTemplate.MSG_TYPE_TL_AGREEMENT)) {
            //通联协议支付确认签约银行验证码
            // 验证银行是否开通
            boolean isOpen = appBankLimitService.isOpen(param.getBankCode());
            if (!isOpen) {
                logger.error("银行卡暂不支持绑定:{},{}", userId, param.getBankCode());
                result.setCode("FAIL");
                result.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
                return result;
            }
            // 从缓存中获取用户
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
            if (null == custUserVO) {
                // 从数据库获取
                custUserVO = custUserService.getCustUserById(userId);
            }

            AppBanksVO bankNameAndNoByCode = appBankLimitService.getBankNameAndNoByCode(param.getBankCode());
            if (bankNameAndNoByCode == null) {
                logger.error("银行卡没有相应配置:{},{}", userId, param.getBankCode());
                result.setCode("FAIL");
                result.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
                return result;
            }
            DirectBindCardOP bindCardOp = new DirectBindCardOP();
            bindCardOp.setCardNo(param.getCardNo());
            bindCardOp.setIdNo(param.getIdCard());
            bindCardOp.setMobile(param.getAccount());
            bindCardOp.setRealName(custUserVO.getRealName());
            bindCardOp.setUserId(userId);
            bindCardOp.setBankNo(bankNameAndNoByCode.getBankNo());
            bindCardOp.setBankCode(param.getBankCode());
            bindCardOp.setSource(param.getSource());
            bindCardOp.setIpAddr(Servlets.getIpAddress(request));

            // 通联协议支付预绑卡银行发验证码
            BindCardResultVO bindCardResult = tltAgreementPayService.agreementPayMsgSend(bindCardOp, HandlerTypeEnum.SANS_HANDLER);
            if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getOrderNo())) {
                logger.error("[{}]协议支付预绑卡发短信失败", param.getAccount());
                result.setCode("FAIL");
                result.setMsg(bindCardResult.getMsg());
                return result;
            }
            result.setData(bindCardResult);
            return result;

        } else {
            msgVerCode = CommonUtils.sendMessage(param.getAccount(), msgType, ip, param.getSource(), userId, channel,
                    shortMsgService);
            if (StringUtils.equals(msgVerCode, Global.FALSE)) {
                return result.set(ErrInfo.MSG_SEND_ERROR);
            }
        }
        return result;
    }

    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePwdFromSafe", method = RequestMethod.POST, name = "修改密码 ")
    @ResponseBody
    public ApiResult updatePwdFromSafe(@Valid UpdatePwdOP param, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 账户是否被锁定 判断
        if (LoginUtils.isLockedAccount(param.getAccount())) {
            result.set(ErrInfo.PWDERROR_FREQUENTLY);
            return result;
        }
        // 原密码判断
        if (StringUtils.isBlank(param.getOldPwd())) {
            logger.error("原密码不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 密码加密
        param.setPassword(LoginUtils.pwdToSHA1(param.getPassword()));
        // 原密码加密
        param.setOldPwd(LoginUtils.pwdToSHA1(param.getOldPwd()));
        int updateRz = custUserService.updatePwd(param);
        if (updateRz < 1) {
            LoginUtils.countPwdError(param.getAccount());
            result.set(ErrInfo.PWD_UPDATE_FAIL);
            return result;
        }
        result.setData(updateRz);
        // 密码修改成功短信提醒 TODO
        return result;
    }

    /**
     * 获取未结清贷款信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUnFinishLoanApply", method = RequestMethod.POST, name = "获取未结清贷款信息")
    @ResponseBody
    public ApiResult getUnFinishLoanApply(String userId) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 根据用户信息获取贷款信息
        LoanApplySimpleVO vo = loanApplyService.getUnFinishLoanApplyInfo(userId);
        if (null == vo || !vo.getLoanApplyStatus().equals(LoanApplySimpleVO.APPLY_STATUS_PASS)) {
            result.set(ErrInfo.NOTFIND_UNFINISH_LOAN_RECORDS);
            return result;
        }

        // 暂不支持延期
        vo.setDelayLimit("0");

        result.setData(vo);
        return result;
    }

    /**
     * 获取当前贷款信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCurrentLoanApply", method = RequestMethod.POST, name = "获取当前贷款信息")
    @ResponseBody
    public ApiResult getCurrentLoanApply(HttpServletRequest request,
                                         @RequestParam(value = "source", required = false) String source) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        String userId = request.getHeader("userId");

        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        String version = request.getParameter("version");

        CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        if (null == custUserVO) {
            // 从数据库获取
            custUserVO = custUserService.getCustUserById(userId);
            if (null == custUserVO) {
                result.set(ErrInfo.UNFIND_CUST);
                return result;
            }
            if (Global.LOCK_USER_FLAG.equals(custUserVO.getStatus())) {
                result.set(ErrInfo.USER_LOCK);
                return result;
            }
            // 更新缓存中的用户信息
            LoginUtils.cacheCustUserInfo(custUserVO);
        }

        LoanApplySimpleVO vo = loanApplyService.getCurrentLoanApplyInfo(userId);
        if (null == vo) {
            result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
            return result;
        }
        // 暂不支持延期
        vo.setDelayLimit("0");

        // 口袋存管渠道是否需要开户
        if (StringUtils.isNotBlank(vo.getPayChannel())
                && Integer.parseInt(vo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()) {
            vo.setNeedOpenAccount("1");
            JedisUtils.set("koudai_cg_pay_flag_" + userId, "1", 1 * 24 * 60 * 60);
        }

        // 汉金所渠道是否需要开户
        if (StringUtils.isNotBlank(vo.getPayChannel())
                && Integer.parseInt(vo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()) {
            if ((null == custUserVO.getHjsAccountId() || 0 == custUserVO.getHjsAccountId().intValue())) {
                vo.setNeedOpenAccount("1");
                JedisUtils.set("hanjs_cg_pay_flag_" + userId, "1", 1 * 24 * 60 * 60);
            } else {
                vo.setNeedOpenAccount("0");
            }
        }
        result.setData(vo);
        return result;
    }

    /**
     * 获取当前贷款信息(非现金贷)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCurrentLoanApplyInfo", method = RequestMethod.POST, name = "获取当前贷款信息")
    @ResponseBody
    public ApiResult getCurrentLoanApplyInfo(HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        String userId = request.getHeader("userId");
        String productId = request.getParameter("productId");
        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        Map<String, Object> map = loanApplyService.getCurrentLoanStatusByPid(userId, productId);
        result.setData(map);
        return result;
    }

    /**
     * 获取已结清贷款信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFinishedLoanApply", method = RequestMethod.POST, name = "获取已结清贷款信息")
    @ResponseBody
    public ApiResult getFinishedLoanApply(HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        String userId = request.getHeader("userId");
        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 获取登录信息
        Subject subject = SecurityUtils.getSubject();
        CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();

        LoansApplyFinishedVO vo = null;
        if (null == shiroUser || StringUtils.isBlank(shiroUser.getIdNo())
                || StringUtils.isBlank(shiroUser.getIdType())) {
            vo = new LoansApplyFinishedVO();
            // 用户信息不全情况，贷款记录为空
            vo.setTotalCount(0);
            vo.setTotalTerm(0);
            result.setData(vo);
            return result;
        }
        // 根据用户信息获取贷款信息
        LoanApplyCustOP loanApplyCustOP = new LoanApplyCustOP();
        loanApplyCustOP.setAccount(shiroUser.getMobile());
        loanApplyCustOP.setIdNo(shiroUser.getIdNo());
        loanApplyCustOP.setIdType(shiroUser.getIdType());
        loanApplyCustOP.setApplyStatus(ApplyStatusEnum.FINISHED.getValue());
        vo = loanApplyService.getFinishedLoansApplyFromUser(loanApplyCustOP);
        if (null == vo || vo.getTotalCount() == 0) {
            result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
            return result;
        }
        result.setData(vo);
        return result;
    }

    /**
     * 根据申请编号获取贷款信息明细
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLoanApplyDetail", method = RequestMethod.POST, name = "根据申请编号获取贷款信息明细")
    @ResponseBody
    public ApiResult getLoanApplyDetail(String applyId, HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(applyId)) {
            logger.error("申请编号不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 参数验证结果判断
        String userId = request.getHeader("userId");

        // 根据申请编号获取贷款信息
        LoanApplySimpleVO vo = loanApplyService.getLoanApplyById(applyId);
        if (null != vo && null != vo.getUserId() && !StringUtils.equals(vo.getUserId(), userId)) {
            result.setCode("FAIL");
            result.setMsg("非法查询，请输入正确的申请编号！");
            return result;
        }
        result.setData(vo);
        return result;
    }

    /**
     * 基础信息入库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveBaseInfo", method = RequestMethod.POST, name = "基础信息入库")
    @ResponseBody
    public ApiResult saveBaseInfo(@Valid BaseInfoOP param, Errors errors, HttpServletRequest request) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();

        String userSaveBaseInfoLockCacheKey = "user_save_base_info_lock_" + userId;
        synchronized (CustController.class) {
            String userSaveBaseInfoLock = JedisUtils.get(userSaveBaseInfoLockCacheKey);
            if (userSaveBaseInfoLock == null) {
                // 加锁，防止并发
                JedisUtils.set(userSaveBaseInfoLockCacheKey, "locked", 120);
            } else {
                logger.warn("基础信息入库接口调用中，userId= {}", userId);
                // 处理中
                result.set(ErrInfo.WAITING);
                return result;
            }
        }
        try {
            // 保存基本信息
            param.setUserId(userId);
            // 获取用户信息
            // 从缓存中获取
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
            if (null == custUserVO) {
                // 从数据库获取
                custUserVO = custUserService.getCustUserById(userId);
                if (null == custUserVO) {
                    result.set(ErrInfo.UNFIND_CUST);
                    return result;
                }
                // 更新缓存中的用户信息
                LoginUtils.cacheCustUserInfo(custUserVO);
            }
            // 真实姓名
            param.setUserName(custUserVO.getRealName());
            // 手机号
            param.setMobile(custUserVO.getMobile());
            // 证件号
            param.setIdNo(custUserVO.getIdNo());
            // 证件号类型
            param.setIdType(custUserVO.getIdType());
            // // 获取缓存中的申请编号
            // param.setApplyId(
            // CommonUtils.getApplyNofromCache(userId));
            int saveRz = custUserService.saveBaseInfo(param);
            if (saveRz < 1) {
                return result.set(ErrInfo.BASIC_SAVE_FAIL);
            }
            // 上传联系人
            int reportResult = reportService.reportContact(userId);
            logger.info("上传联系人结果:{},{},{}", userId, custUserVO.getRealName(), reportResult);
            // 清理用户信息缓存，以便更新
            LoginUtils.cleanCustUserInfoCache(userId);
            result.setData(saveRz);
        } finally {
            // 移除锁
            JedisUtils.del(userSaveBaseInfoLockCacheKey);
        }
        return result;
    }

    /**
     * 投复利客户基础信息入库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tFLSaveBaseInfo", method = RequestMethod.POST, name = "投复利基础信息入库")
    @ResponseBody
    public ApiResult tFLSaveBaseInfo(@Valid TFLBaseInfoOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();

        String userSaveBaseInfoLockCacheKey = "TFLuser_save_base_info_lock_" + userId;
        synchronized (CustController.class) {
            String userSaveBaseInfoLock = JedisUtils.get(userSaveBaseInfoLockCacheKey);
            if (userSaveBaseInfoLock == null) {
                // 加锁，防止并发
                JedisUtils.set(userSaveBaseInfoLockCacheKey, "locked", 120);
            } else {
                logger.warn("基础信息入库接口调用中，userId= {}", userId);
                // 处理中
                result.set(ErrInfo.WAITING);
                return result;
            }
        }
        try {
            // 保存基本信息
            param.setUserId(userId);
            // 获取用户信息
            // 从缓存中获取
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
            if (null == custUserVO) {
                // 从数据库获取
                custUserVO = custUserService.getCustUserById(userId);
                if (null == custUserVO) {
                    result.set(ErrInfo.UNFIND_CUST);
                    return result;
                }
                // 更新缓存中的用户信息
                LoginUtils.cacheCustUserInfo(custUserVO);
            }
            // 真实姓名
            param.setUserName(custUserVO.getRealName());
            // 手机号
            param.setMobile(custUserVO.getMobile());
            // 证件号
            param.setIdNo(custUserVO.getIdNo());
            // 证件号类型
            param.setIdType(custUserVO.getIdType());
            int saveRz = custUserService.saveTFLBaseInfo(param);
            if (saveRz < 1) {
                return result.set(ErrInfo.BASIC_SAVE_FAIL);
            }
            // 上传联系人
            int reportResult = reportService.reportContact(userId);
            logger.info("上传联系人结果:{},{},{}", userId, custUserVO.getRealName(), reportResult);
            // 清理用户信息缓存，以便更新
            LoginUtils.cleanCustUserInfoCache(userId);
            result.setData(saveRz);
        } finally {
            // 移除锁
            JedisUtils.del(userSaveBaseInfoLockCacheKey);
        }
        return result;
    }

    /**
     * 申请信息入库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveApplyInfo", method = RequestMethod.POST, name = "申请信息入库")
    @ResponseBody
    public ApiResult saveApplyInfo(@Valid LoanApplyOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        String productId = param.getProductId();
        if (LoanProductEnum.CCD.getId().equals(productId)) {
            param.setApplyAmt(new BigDecimal(10000));
            param.setApplyTerm(90);
            result.setCode("FAIL");
            result.setMsg("请使用复利普惠APP下单");
            return result;
        }
        // 保存基本信息
        String userId = request.getHeader("userId");
        param.setUserId(userId);

        String lockKey = "user_save_apply_info_lock_" + userId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        boolean lock = JedisUtils.setLock(lockKey, requestId, 60 * 5);
        if (!lock) {
            logger.warn("申请信息入库接口调用中，userId= {}", userId);
            result.set(ErrInfo.WAITING);
            return result;
        }

        boolean hasError = false;
        try {
            if (!"3".equals(param.getSource())) {
                if (operationLogService.getAuthStatus(userId, param.getProductId()) == AuthenticationStatusVO.NO) {
                    hasError = true;
                    logger.error("用户[{}]未完全认证就下单", userId);
                    return result.set(ErrInfo.NOAUTH_FAIL);
                }
            }
            if (LoanProductEnum.XJDFQ.getId().equals(productId)) {
                Integer moreAuth = operationLogService.getMoreAuthStatus(userId, productId);
                if (AuthenticationStatusVO.NO.equals(moreAuth) || AuthenticationStatusVO.ING.equals(moreAuth)) {
                    hasError = true;
                    logger.error("现金贷分期用户[{}]未认证信用卡", userId);
                    result.setCode("FAIL");
                    result.setMsg("信用卡未认证");
                    return result;
                }
            }

            param.setUserId(userId);
            if (loanApplyService.isExistUnFinishLoanApply(userId)) {
                hasError = true;
                logger.error("用户[{}]存在审核中或待还款申请单", userId);
                return result.set(ErrInfo.REPEAT_APPLY);
            }

            // 申请限制判断
            if (loanApplyService.isApplyLimit(userId)) {
                hasError = true;
                logger.error("用户[{}]申请次数受限制", userId);
                result.setCode("FAIL");
                result.setMsg("申请次数超限");
                return result;
            }
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
            if (custUserVO == null) {
                custUserVO = custUserService.getCustUserById(userId);
            }
            if (custUserVO != null && JedisUtils.exists("ORDER:Locked_" + custUserVO.getMobile()) == true) {
                hasError = true;
                logger.error("用户[{}]已通过导流平台生成订单", userId);
                return result.set(ErrInfo.REPEAT_APPLY);
            }
            // 黑名单限制
            long blackCount = riskBlacklistService.countInBlacklist(userId);
            if (blackCount > 0) {
                hasError = true;
                logger.error("用户[{}]已在黑名单", userId);
                result.setCode("FAIL");
                result.setMsg("您的账户已被禁止申请");
                return result;
            }

            // 缓存白骑士设备唯一标识
            CommonUtils.cacheBqs(userId, param.getBqsTokenKey());
            // 缓存同盾设备唯一标识
            // CommonUtils.cacheTd(userId, param.getTdBlackBox());
            // 截取ip
            String ip = Servlets.getIpAddress(request);
            param.setIp(ip);
            // 渠道码
            param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
            // 保存申请信息
            SaveApplyResultVO rz = loanApplyService.saveLoanApply(param);
            if (!rz.isSuccess()) {
                hasError = true;
                logger.error(rz.getMessage());
                if (rz.getMessage().contains("无产品信息")) {
                    result.setCode("FAIL");
                    result.setMsg("暂无该借款期限产品信息，请更新app版本");
                    return result;
                }
                return result.set(ErrInfo.APPLY_SAVE_FAIL);
            }
            // 申请成功后清除申请编号缓存
            CommonUtils.cleanApplyNofromCache(userId);
            result.setData(rz);
            logger.debug("saveApplyInfo result:[{}]", JsonMapper.toJsonString(rz));
        } finally {
            // 移除锁
            if (hasError)
                JedisUtils.releaseLock(lockKey, requestId);
        }
        return result;
    }

    /**
     * 验证申请信息(投复利)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vaildTFLApplyInfo", method = RequestMethod.POST, name = "验证申请信息")
    @ResponseBody
    public ApiResult vaildTFLApplyInfo(@Valid LoanApplyTFLOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        param.setUserId(userId);
        // 初始化返回结果
        ApiResult result = new ApiResult();
        boolean flag = vaildTFLApply(param, result);
        if (!flag)
            return result;
        return result;
    }

    /**
     * 申请信息入库(投复利)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTFLApplyInfo", method = RequestMethod.POST, name = "申请信息入库")
    @ResponseBody
    public ApiResult saveTFLApplyInfo(@Valid LoanApplyTFLOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }

        if (LoanProductEnum.LYFQ.getId().equals(param.getProductId())
                || LoanProductEnum.CCD.getId().equals(param.getProductId())
                || LoanProductEnum.TFL.getId().equals(param.getProductId())) {
            result.setCode("FAIL");
            result.setMsg("请使用复利普惠APP下单");
            return result;
        }

        String userId = request.getHeader("userId");
        param.setUserId(userId);
        // 保存基本信息
        String userSaveApplyInfoLockCacheKey = "user_save_apply_info_lock_" + userId;
        synchronized (CustController.class) {
            String userSaveApplyInfoLock = JedisUtils.get(userSaveApplyInfoLockCacheKey);
            if (userSaveApplyInfoLock == null) {
                // 加锁，防止并发
                JedisUtils.set(userSaveApplyInfoLockCacheKey, "locked", 120);
            } else {
                logger.warn("申请信息入库接口调用中，userId= {}", userId);
                // 处理中
                result.set(ErrInfo.WAITING);
                return result;
            }
        }
        try {
            boolean flag = vaildTFLApply(param, result);
            if (!flag)
                return result;
            // 缓存白骑士设备唯一标识
            CommonUtils.cacheBqs(userId, param.getBqsTokenKey());
            // 缓存同盾设备唯一标识
            CommonUtils.cacheTd(userId, param.getTdBlackBox());
            // 截取ip
            String ip = Servlets.getIpAddress(request);
            param.setIp(ip);

            // 渠道码
            param.setChannelId(ChannelEnum.TOUFULI.getCode());
            // 保存申请信息
            LoanApplyOP loanApplyOP = BeanMapper.map(param, LoanApplyOP.class);
            SaveApplyResultVO rz = loanApplyService.saveLoanApply(loanApplyOP);
            if (!rz.isSuccess()) {
                logger.error(rz.getMessage());
                if (rz.getMessage().contains("无产品信息")) {
                    result.setCode("FAIL");
                    result.setMsg("暂无该借款期限产品信息，请更新app版本");
                    return result;
                }
                return result.set(ErrInfo.APPLY_SAVE_FAIL);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) result.getData();
            data.put("applyId", rz.getApplyId());
            logger.debug("saveTFLApplyInfo result:[{}]", JsonMapper.toJsonString(result));
        } finally {
            // 移除锁
            JedisUtils.del(userSaveApplyInfoLockCacheKey);
        }
        return result;
    }

    /**
     * 获取message站内信
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMsg", name = "获取message站内信 ")
    @ResponseBody
    public ApiResult getMsg(HttpServletRequest request) throws Exception {

        // 截取userId
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }
        // 出现站内信
        List<MessageVO> list = messageService.getMsgByUserId(userId);
        result.setData(list);
        return result;
    }

    /**
     * 统计未阅读的message站内信
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/countUnReadMsg", name = "统计未阅读的message站内信")
    @ResponseBody
    public ApiResult countUnReadMsg(HttpServletRequest request) throws Exception {

        // 截取userId
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }
        // 出现站内信
        int rz = messageService.countUnReadMsg(userId);
        result.setData(rz);
        return result;
    }

    /**
     * 阅读message站内信
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/readMsg", name = "阅读message站内信 ")
    @ResponseBody
    public ApiResult readMsg(@Valid MessageOP param, Errors errors, HttpServletRequest request) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 截取userId
        String userId = request.getHeader("userId");
        param.setUserId(userId);
        // 截取ip
        String ip = Servlets.getIpAddress(request);
        param.setIp(ip);
        // 查看状态 标记为已读
        param.setViewStatus(MsgViewStatus.VIEWED.getValue());
        // 初始化返回结果
        ApiResult result = new ApiResult();

        // 修改信息查看状态
        int rz = messageService.updateMsgViewStatus(param);

        result.setData(rz);
        return result;
    }

    /**
     * 认证流程结果查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAuthenticationResult", method = RequestMethod.POST, name = "认证流程结果查询")
    @ResponseBody
    public ApiResult getAuthenticationResult(HttpServletRequest request) throws Exception {

        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 截取userId
        String userId = request.getHeader("userId");
        String productId = request.getParameter("productId");
        // 查询认证流程结果
        AuthenticationStatusVO vo = operationLogService.getAuthenticationStatus(userId, productId);
        if (null == vo) {
            result.set(ErrInfo.NOTFIND_AUTH_RECORDS);
            return result;
        }
        result.setData(vo);
        return result;
    }

    /**
     * 统计用户是否进入借点钱绑卡页面
     *
     * @param request
     * @param source
     * @return
     */
    @RequestMapping(value = "/countUserPv", name = "获取绑定银行卡等个人信息")
    @ResponseBody
    public ApiResult countUserPv(HttpServletRequest request,
                                 @RequestParam(value = "source", required = false) String source) {
        // 截取userId
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }

        // 通过用户id获取用户贷款申请信息
        LoanApplySimpleVO applySimpleVO = loanApplyService.getUnFinishLoanApplyInfo(userId);
        if (applySimpleVO != null) {
            //修改状态为已访问
            int i = loanApplyService.updateCountUserBindCardPv(applySimpleVO.getApplyId());
            logger.info("修改用户是否进入借点钱绑卡页面状态受影响行数:{}", i);
        }
        logger.info("修改用户是否进入借点钱绑卡页面状态用户为:{}", userId);
        return result;
    }

    /**
     * 获取绑定银行卡等个人信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCustUser", name = "获取绑定银行卡等个人信息")
    @ResponseBody
    public ApiResult getCustUser(HttpServletRequest request,
                                 @RequestParam(value = "source", required = false) String source) throws Exception {

        // 截取userId
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }
        // 通过用户id获取用户信息
        String cacheKey = "api_user_bind_info_" + userId;
        BindInfoVO vo = (BindInfoVO) JedisUtils.getObject(cacheKey);
        if (vo == null) {
            vo = custUserService.getBindInfoById(userId);
            if (null == vo) {
                result.set(ErrInfo.NOTFIND_CUST_USER);
                return result;
            } else {
                if (null == vo.getAccountId()) {
                    vo.setAccountId("");
                }
                if (null == vo.getCardNo()) {
                    vo.setCardNo("");
                    vo.setBankName("");
                    vo.setBankCode("");
                }
                JedisUtils.setObject(cacheKey, vo, 60);
            }
        }
        if (JedisUtils.get("koudai_cg_pay_flag_" + userId) != null) {
            vo.setAccountId("");
        }
        if (JedisUtils.get("hanjs_cg_pay_flag_" + userId) != null) {
            vo.setAccountId("");
        }
        result.setData(vo);
        return result;
    }

    /**
     * 获取用户基本信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCustUserInfo", name = "获取用户基本信息")
    @ResponseBody
    public ApiResult getCustUserInfo(HttpServletRequest request, String productId) throws Exception {
        // 截取userId
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }
        if (LoanProductEnum.TFL.getId().equals(productId) || LoanProductEnum.CCD.getId().equals(productId)
                || LoanProductEnum.LYFQ.getId().equals(productId)) {
            TFLBaseInfoOP vo = custUserService.getTFLUserInfo(userId);
            if (null == vo) {
                result.set(ErrInfo.NOTFIND_CUST_USERINFO);
                return result;
            }
            result.setData(vo);
            return result;
        }
        CustUserInfoVO vo = custUserService.getSimpleUserInfo(userId);
        if (null == vo) {
            result.set(ErrInfo.NOTFIND_CUST_USERINFO);
            return result;
        }
        result.setData(vo);
        return result;
    }

    /**
     * 获取协议要素
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAgreementFactor", name = "获取协议要素")
    @ResponseBody
    public ApiResult getAgreementFactor(@Valid AgreementOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数异常处理
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证
        if (null == param.getApplyAmt()) {
            result.setCode("400");
            result.setMsg("申请金额不能为空");
            return result;
        }
        if (null == param.getApplyTerm()) {
            result.setCode("400");
            result.setMsg("申请期限不能为空");
            return result;
        }
        if (LoanProductEnum.XJDFQ.getId().equals(param.getProductId()) && param.getRepayMethod() == null) {
            result.setCode("400");
            result.setMsg("还款方式不能为空");
            return result;
        }
        // 截取userId
        String userId = request.getHeader("userId");
        // 用户id
        param.setUserId(userId);
        // 渠道号
        // param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
        LoanProductEnum productEnum = LoanProductEnum.get(param.getProductId());
        switch (productEnum) {
            case CCD:
                param.setChannelId(ChannelEnum.CHENGDAI.getCode());
                param.setRepayMethod(RepayMethodEnum.INTEREST_DAY.getValue());
                break;
            case TFL:
                param.setChannelId(ChannelEnum.TOUFULI.getCode());
                break;
            case LYFQ:
                param.setChannelId(ChannelEnum.LYFQAPP.getCode());
                param.setRepayMethod(RepayMethodEnum.PRINCIPAL_INTEREST.getValue());
                break;
            case XJD:
                param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                param.setRepayMethod(RepayMethodEnum.ONE_TIME.getValue());
                break;
            case XJDFQ:
                param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
                break;
            default:
                break;
        }
        // 缓存中获取申请编号
        param.setApplyId(CommonUtils.getApplyNofromCache(userId));
        // 获取协议要素信息
        AgreementVO vo = loanApplyService.getAgreementFactor(param);
        if (null == vo || null == vo.getIdNo()) {
            result.setCode("FAIL");
            result.setMsg("获取协议要素信息失败");
            return result;
        }
        result.setData(vo);
        logger.debug("AgreementVO:[{}]", JsonMapper.toJsonString(vo));
        return result;
    }

    /**
     * 更新学信认证信息
     *
     * @return
     */
    @RequestMapping(value = "/saveXuexinStatus", method = RequestMethod.POST, name = "更新学信认证信息")
    @ResponseBody
    public ApiResult saveXuexinStatus(@Valid TaoBaoAndXuexinOP param, HttpServletRequest request) {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        if (param == null || StringUtils.isBlank(param.getUserId())) {
            result.setCode("400");
            result.setMsg("用户id不能为空");
            return result;
        }

        if (param == null || StringUtils.isBlank(param.getXuexinId())) {
            result.setCode("400");
            result.setMsg("用户学信账号不能为空");
            return result;
        }

        Integer status = null;
        if (StringUtils.equals(TaoBaoAndXuexinOP.DO_OPERATOR_SUCC, param.getDoOperatorResult())) {
            status = XjdLifeCycle.LC_XUEXIN_1;
        } else {
            result.setCode("FAIL");
            result.setMsg("学信网认证失败");
            return result;
        }

        CustUserVO custUser = custUserService.getCustUserById(param.getUserId());
        custUser.setXuexinId(param.getXuexinId());
        int ret = custUserService.updateCustUser(custUser);
        if (ret != 1) {
            result.setCode("FAIL");
            result.setMsg("更新学信账号失败");
            return result;
        }
        // 更新学信认证状态
        int logRz = loanApplyService.updateAuthStatus(param.getUserId(), status);
        if (logRz == 0) {
            result.setCode("FAIL");
            result.setMsg("更新学信认证状态失败");
        }

        return result;
    }

    /**
     * 更新淘宝认证信息
     *
     * @return
     */
    @RequestMapping(value = "/saveTaobaoStatus", method = RequestMethod.POST, name = "更新淘宝认证状态信息")
    @ResponseBody
    public ApiResult saveTaobaoStatus(@Valid TaoBaoAndXuexinOP param, HttpServletRequest request) {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        if (param == null || StringUtils.isBlank(param.getUserId())) {
            result.setCode("400");
            result.setMsg("用户id不能为空");
            return result;
        }

        Integer status = null;
        if (StringUtils.equals(TaoBaoAndXuexinOP.DO_OPERATOR_SUCC, param.getDoOperatorResult())) {
            status = XjdLifeCycle.LC_TAOBAO_1;
        } else {
            result.setCode("FAIL");
            result.setMsg("淘宝认证失败");
            return result;
        }

        // 保存淘宝账号
        CustUserVO custUser = custUserService.getCustUserById(param.getUserId());
        custUser.setAlipayId(param.getAlipayId());
        int ret = custUserService.updateCustUser(custUser);
        if (ret != 1) {
            result.setCode("FAIL");
            result.setMsg("更新淘宝账号失败");
            return result;
        }
        // 更新淘宝认证状态
        int logRz = loanApplyService.updateAuthStatus(param.getUserId(), status);
        if (logRz == 0) {
            result.setCode("FAIL");
            result.setMsg("更新淘宝认证状态失败");
        }
        return result;
    }

    /**
     * 保存经纬度详细地址信息
     *
     * @return
     */
    @RequestMapping(value = "/saveJWDLocation", method = RequestMethod.POST, name = "保存经纬度详细地址信息")
    @ResponseBody
    public ApiResult saveJWDLocation(@Valid LocationOP param, HttpServletRequest request) {
        // 初始化返回对象
        ApiResult result = new ApiResult();
        // 截取userId
        String userId = request.getHeader("userId");

        if (param == null) {
            result.setCode("400");
            result.setMsg("请求数据不能为空");
            return result;
        }
        param.setIp(Servlets.getIpAddress(request));
        // 条用百度地图接口，保存地址信息
        int ret = appUpgradeService.saveJWdLocation(param.getLat(), param.getLng(), param.getIp(), userId);
        if (ret != 1) {
            result.setCode("FAIL");
            result.setMsg("保存经纬度详细地址信息失败");
            return result;
        }

        return result;
    }

    /**
     * 上传企业营业执照
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadEnterpriseLicense", method = RequestMethod.POST, name = "上传企业营业执照")
    @ResponseBody
    public ApiResult uploadEnterpriseLicense(@Valid UploadEnterpriseLicenseOP param, Errors errors,
                                             HttpServletRequest request) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 参数验证结果判断
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();

        // 用户id
        param.setUserId(userId);
        // 截取ip
        String ip = Servlets.getIpAddress(request);
        param.setIp(ip);

        // 企业营业执照上传
        UploadParams enterpriseLicense = new UploadParams();
        enterpriseLicense.setUserId(userId);
        enterpriseLicense.setBizCode(FileBizCode.ENTERPRISE_LICENSE.getBizCode());
        enterpriseLicense.setIp(ip);
        enterpriseLicense.setSource(param.getSource());
        FileVO fileVO = CommonUtils.uploadBase64Image(param.getEnterpriseLicensePhoto(), enterpriseLicense);
        if (fileVO != null && StringUtils.isNotBlank(fileVO.getUrl())) {
            JedisUtils.set(Global.UPLOAD_ENTERPRISE_LICENSE_PREFIX + userId, "1", 60 * 60 * 24 * 3);
            result.setData(fileVO);
        } else {
            result.setCode("FAIL");
            result.setMsg("上传失败");
        }
        return result;
    }

    private boolean vaildTFLApply(LoanApplyTFLOP param, ApiResult result) {
        String userId = param.getUserId();
        // 参数验证
        if (param.getRepayMethod() != 1 && param.getRepayMethod() != 4 && param.getRepayMethod() != 6) {
            result.set(ErrInfo.BAD_REQUEST);
            return false;
        }
        result.set(ErrInfo.SUCCESS);
        Map<String, Object> data = new LinkedHashMap<>();
        result.setData(data);
        if (operationLogService.getAuthStatus(userId, param.getProductId()) == AuthenticationStatusVO.NO) {
            logger.error("用户[{}]未完全认证就下单", userId);
            data.put("status", 1);
            return false;
        }

        int maxApprovingCount = LoanProductEnum.LYFQ.getId().equals(param.getProductId()) ? 2 : 1;
        int approvingCount = loanApplyService.countApprovingByUserId(userId);
        if (approvingCount >= maxApprovingCount) {
            logger.error("用户[{}]上笔申请单正在审核中:{}", userId, approvingCount);
            data.put("status", 2);
            return false;
        }
        int maxUnFinishCount = LoanProductEnum.LYFQ.getId().equals(param.getProductId()) ? 2 : 3;
        int unFinishCount = loanApplyService.countUnFinishLoanApplyByUserId(userId);
        if (unFinishCount >= maxUnFinishCount) {
            logger.error("用户[{}]未结清贷款笔数超过限制:{}", userId, unFinishCount);
            data.put("status", 3);
            return false;
        }
        boolean hasOtherProductUnFinish = loanApplyService.hasOtherProductUnFinishApply(userId, param.getProductId());
        if (hasOtherProductUnFinish) {
            logger.error("用户[{}]有其他产品未结清:{}", userId, hasOtherProductUnFinish);
            data.put("status", 3);
            return false;
        }
        BigDecimal maxLimitAmt = new BigDecimal(1000000);
        BigDecimal minUploadLimitAmt = new BigDecimal(200000);
        // 累计未结清金额
        BigDecimal amt = loanApplyService.getUnFinishLoanAmtByUserId(userId);
        amt = (amt == null) ? new BigDecimal(0) : amt;
        CustUserVO user = custUserService.getCustUserById(userId);
        if (amt.compareTo(maxLimitAmt) >= 0) {
            logger.error("用户[{}]可用额度为0:{}", userId, amt);
            data.put("status", 4);
            return false;
        }
        if (amt.add(param.getApplyAmt()).compareTo(maxLimitAmt) > 0) {
            logger.error("用户[{}]累计未结清借款额度超过100万,请修改您的借款金额:{}", userId, amt);
            data.put("creditAmt", maxLimitAmt.subtract(amt));
            data.put("status", 5);
            return false;
        }
        if (JedisUtils.get(Global.UPLOAD_ENTERPRISE_LICENSE_PREFIX + userId) == null) {
            if ((StringUtils.isBlank(user.getType()) || UserTypeEnum.personal.getId().equals(user.getType()))
                    && amt.add(param.getApplyAmt()).compareTo(minUploadLimitAmt) >= 0) {
                logger.error("用户[{}]累计未结清借款额度超过20万,需上传企业法人营业执照:{}", userId, amt);
                data.put("status", 6);
                return false;
            }
        }
        long lastRejectDays = loanApplyService.getLastRejectDaysByUserId(userId);
        if (lastRejectDays != -1 && lastRejectDays < Global.MIN_AGAIN_APPLY_DAY) {
            logger.error("用户[{}]申请次数超限,上次被拒时间距今{}天", userId, lastRejectDays);
            data.put("status", 7);
            return false;
        }
        int rejectCount = loanApplyService.countRejectByUserid(userId);
        if (rejectCount >= Global.MAX_REJECT_COUNT) {
            logger.error("用户[{}]申请次数超限,累计被拒{}次", userId, rejectCount);
            data.put("status", 7);
            return false;
        }
        data.put("status", 0);
        return true;
    }

    /**
     * 获取借款记录
     */
    @RequestMapping(value = "/getLoanApplyRecord", method = RequestMethod.POST, name = "获取借款记录")
    @ResponseBody
    public ApiResult getLoanApplyRecord(HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        String userId = request.getHeader("userId");
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 根据用户信息获取贷款信息
        List<LoansApplySummaryVO> list = loanApplyService.getLoanApplyListByUserId(userId);
        if (null == list) {
            result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
            return result;
        }
        List<Map<String, Object>> listTemp = new ArrayList<Map<String, Object>>();
        for (LoansApplySummaryVO vo : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("applyId", vo.getApplyId());
            map.put("applyTime", vo.getApplyTime());
            map.put("approveAmt", vo.getApproveAmt());
            map.put("approveTerm", vo.getTerm());
            map.put("repayMethod", RepayMethodEnum.getDesc(vo.getRepayMethod()));
            Integer status = vo.getOverType();
            if (status == XjdLifeCycle.LC_AUTO_AUDIT_2 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2) {
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_REJECT); // 审核未通过4
            } else if (status == XjdLifeCycle.LC_APPLY_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_0
                    || status == XjdLifeCycle.LC_AUTO_AUDIT_3 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0
                    || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3) {
                // 真正审核中
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
            } else if (status == XjdLifeCycle.LC_RAISE_0 || status == XjdLifeCycle.LC_RAISE_1
                    || status == XjdLifeCycle.LC_LENDERS_0) {
                // 放款中
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_PAY); // 放款中5
            } else if (ApplyStatusEnum.FINISHED.getValue().equals(vo.getApplyStatus())) {
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_FINISHED); // 已结清
                // 6
            } else if (status == XjdLifeCycle.LC_CASH_4 || status == XjdLifeCycle.LC_REPAY_0
                    || status == XjdLifeCycle.LC_OVERDUE_0) {
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_PASS); // 还款中3
            } else {
                map.put("status", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
            }
            listTemp.add(map);
        }

        result.setData(listTemp);
        return result;
    }

    /**
     * 获取订单详情
     */
    @RequestMapping(value = "/getDetailByApplyId", method = RequestMethod.POST, name = "获取订单详情")
    @ResponseBody
    public ApiResult getDetailByApplyId(String applyId) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        Map<String, Object> map = repayPlanItemService.getRepayDetailByApplyId(applyId);
        if (null == map) {
            result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
            return result;
        }
        result.setData(map);
        return result;
    }

    /**
     * 获取用户卡券
     */
    @RequestMapping(value = "/findCustCoupon", method = RequestMethod.POST, name = "获取用户卡券")
    @ResponseBody
    public ApiResult findCustCoupon(HttpServletRequest request) throws Exception {
        String userId = request.getHeader("userId");

        List<CustCouponVO> resultList = custCouponService.findCustCouponByUserId(userId);

        Collections.sort(resultList, new Comparator<CustCouponVO>() {
            public int compare(CustCouponVO p1, CustCouponVO p2) {
                return p1.getStatus() - p2.getStatus();
            }
        });

        LoanTripProductDetailOP op = new LoanTripProductDetailOP();
        op.setCustId(userId);
        List<LoanTripProductDetailVO> tripList = loanTripProductService.findCustProduct(op);

        if (tripList != null && tripList.size() > 0) {
            CustCouponVO custCouponVO = null;
            for (LoanTripProductDetailVO tripProductDetailVO : tripList) {
                custCouponVO = new CustCouponVO();
                custCouponVO.setApplyId(tripProductDetailVO.getApplyId());
                custCouponVO.setType(8);
                custCouponVO.setCouponName(tripProductDetailVO.getName());

                if (StringUtils.isNotBlank(tripProductDetailVO.getOverdueTime())) {
                    custCouponVO.setEndTime(DateUtils.parse(tripProductDetailVO.getOverdueTime(), "yyyy/MM/dd"));
                }

                custCouponVO.setImgUrl(tripProductDetailVO.getImgUrl());
                custCouponVO.setCardNo(tripProductDetailVO.getCardNo());
                custCouponVO.setRemark(tripProductDetailVO.getRemark());

                resultList.add(custCouponVO);
            }
        }

        // 初始化返回结果
        ApiResult result = new ApiResult();
        result.setData(resultList);
        return result;
    }

    /**
     * 获取有效用户卡券
     */
    @RequestMapping(value = "/findAvailableCoupon", method = RequestMethod.POST, name = "获取有效用户卡券")
    @ResponseBody
    public ApiResult findAvailableCoupon(HttpServletRequest request) throws Exception {
        String userId = request.getHeader("userId");
        String amount = request.getParameter("amount");

        // 初始化返回结果
        ApiResult result = new ApiResult();

        if (StringUtils.isBlank(amount)) {
            result.set(ErrInfo.BAD_REQUEST);
            return result;
        }

        List<CustCouponVO> resultList = custCouponService.findAvailableCoupon(userId, Float.parseFloat(amount));

        result.setData(resultList);
        return result;
    }

    /**
     * 获取用户购物订单
     */
    @RequestMapping(value = "/findGoodsOrder", method = RequestMethod.POST, name = "获取用户购物订单")
    @ResponseBody
    public ApiResult findShopOrder(GoodsOrderOP op, HttpServletRequest request) throws Exception {
        String userId = request.getHeader("userId");

        op.setAccountId(userId);

        // 初始化返回结果
        ApiResult result = new ApiResult();

        List<GoodsOrderVO> list = shopService.findGoodsOrder(op);
        // 设置银行编码和银行名称
        if (list != null && list.size() > 0) {
            CustUserVO user = custUserService.getCustUserById(userId);
            for (GoodsOrderVO orderVO : list) {
                orderVO.setBankCode(user.getBankCode());
                orderVO.setBankName(BankCodeEnum.getName(user.getBankCode()));
            }
        }

        result.setData(list);
        return result;
    }

}
