package com.rongdu.loans.api.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.IdcardUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.BankCode;
import com.rongdu.loans.api.vo.EnsureAgreementOP;
import com.rongdu.loans.api.vo.OpenAccountVO;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.bankDeposit.option.OpenAccountOP;
import com.rongdu.loans.bankDeposit.service.BankDepositService;
import com.rongdu.loans.bankDeposit.vo.OpenAccountResultVO;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.hanjs.op.HanJSUserOP;
import com.rongdu.loans.hanjs.op.HanJSWithdrawOP;
import com.rongdu.loans.hanjs.service.HanJSUserService;
import com.rongdu.loans.hanjs.vo.HanJSResultVO;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.vo.KDwithdrawRecodeVO;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.option.LoanTripProductDetailOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.LoansApplySummaryVO;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.sys.web.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口还款相关Controller
 *
 * @author zcb
 * @version 2018-05-30
 */
@Controller
@RequestMapping(value = "${adminPath}/loan")
public class LoanController extends BaseController {

    @Autowired
    private CustUserService custUserService;

    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private RepayPlanItemService repayPlanItemService;

    @Autowired
    private BankDepositService bankDepositService;

    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;

    @Autowired
    private AppBankLimitService appBankLimitService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private CityService cityService;

    @Autowired
    private LoanTripProductService loanTripProductService;

    @Autowired
    private RongPointCutService rongPointCutService;

    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;

    @Autowired
    private RongService rongService;

    @Autowired
    private KDDepositService kdDepositService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private HanJSUserService hanJSUserService;

    @Autowired
    private PayLogService payLogService;
    @Autowired
    private SLLService sllService;

    /**
     * code y0601 获取借款记录
     */
    @RequestMapping(value = "/getLoanApplyRecord", method = RequestMethod.POST, name = "获取借款记录")
    @ResponseBody
    public ApiResult getLoanApplyRecord(@RequestParam(value = "userId") String userId) throws Exception {
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(userId)) {
            logger.error("用户id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        String cacheKey = "app_sel_loan_apply_record_" + userId;
        result = (ApiResult) JedisUtils.getObject(cacheKey);
        if (result == null) {
            result = new ApiResult();
            // 根据用户信息获取贷款信息
            List<LoansApplySummaryVO> list = loanApplyService.getLoanApplyListByUserId(userId);
            if (null == list) {
                result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
                return result;
            }
            List<Map<String, Object>> listTemp = new ArrayList<Map<String, Object>>();
            String loanTerm = null;
            for (LoansApplySummaryVO vo : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("applyId", vo.getApplyId());
                map.put("applyTime", vo.getApplyTime());
                map.put("approveAmt", vo.getApproveAmt());
                map.put("approveTerm", vo.getApproveTerm());
                map.put("repayMethod", RepayMethodEnum.getDesc(vo.getRepayMethod()));
                map.put("productName", vo.getProductName());
                map.put("term", vo.getTerm());
                map.put("applyTerm", vo.getApplyTerm());
                if (vo.getRepayMethod().intValue() == RepayMethodEnum.ONE_TIME.getValue().intValue()) {
                    loanTerm = vo.getApproveTerm() + "天";
                } else {
                    loanTerm = vo.getTerm() + "期";
                }
                map.put("loanTerm", loanTerm);
                Integer status = vo.getOverType();
                if (status == XjdLifeCycle.LC_AUTO_AUDIT_2 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2) {
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_REJECT); // 审核未通过4
                } else if (status == XjdLifeCycle.LC_APPLY_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_0
                        || status == XjdLifeCycle.LC_AUTO_AUDIT_3 || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0
                        || status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3) {
                    // 真正审核中
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
                } else if (status == XjdLifeCycle.LC_RAISE_0 || status == XjdLifeCycle.LC_RAISE_1
                        || status == XjdLifeCycle.LC_LENDERS_0 || status == XjdLifeCycle.LC_CASH_2) {
                    // 放款中
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_PAY); // 放款中5
                } else if (ApplyStatusEnum.FINISHED.getValue().equals(vo.getApplyStatus())) {
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_FINISHED); // 已结清
                    // 6
                } else if (status == XjdLifeCycle.LC_CASH_3 || status == XjdLifeCycle.LC_CASH_4
                        || status == XjdLifeCycle.LC_REPAY_0) {
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_PASS); // 还款中3
                } else if (status == XjdLifeCycle.LC_OVERDUE_0) {
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_OVERDUE); // 已逾期9
                } else {
                    map.put("status", LoanApplySimpleVO.APPLY_STATUS_AUDITING); // 审核中2
                }
                listTemp.add(map);
            }

            result.setData(listTemp);

            JedisUtils.setObject(cacheKey, result, 5 * 60);
        }

        return result;
    }

    /**
     * code y0601 获取订单详情
     */
    @RequestMapping(value = "/getDetailByApplyId", method = RequestMethod.POST, name = "获取订单详情")
    @ResponseBody
    public ApiResult getDetailByApplyId(@RequestParam(value = "applyId") String applyId) throws Exception {
        // 初始化返回结果
        ApiResult result = null;
        String cacheKey = "app_sel_loan_apply_detail_" + applyId;
        result = (ApiResult) JedisUtils.getObject(cacheKey);
        if (result == null) {
            result = new ApiResult();

            Map<String, Object> map = repayPlanItemService.getRepayDetailByApplyId(applyId);
            if (null == map) {
                result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
                return result;
            }
            result.setData(map);

            JedisUtils.setObject(cacheKey, result, 5 * 60);
        }
        return result;
    }

    private OpenAccountResultVO creatJxbAccount(String userId, String cityId, String email, String productId,
                                                String source, String returnUrl) throws Exception {
        // 初始化电子账户
        String accountId = null;

        OpenAccountResultVO openRz = null;
        // 恒丰银行开户流程
        // 调恒丰银行开户接口
        OpenAccountOP openAccountOP = new OpenAccountOP();
        // 从缓存中获取用户
        CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        if (null == custUserVO) {
            // 从数据库获取
            custUserVO = custUserService.getCustUserById(userId);
        }
        for (BankCode bankCode : BankCode.values()) {
            if (bankCode.getbName().equals(custUserVO.getBankCode())) {
                // 银行行别号
                openAccountOP.setParent_bank_id(bankCode.getBcode());
            }
        }
        // 用户名PinYinUtils.toPinyin(custUserVO.getRealName())
        String uname = custUserVO.getIdNo().substring(custUserVO.getIdNo().length() - 6, custUserVO.getIdNo().length());
        openAccountOP.setUname(uname);
        // 银行卡开户行所在的城市编号
        /*
         * if (!StringUtils.isNumeric(cityId) &&
		 * StringUtils.isNotBlank(custUserVO.getBankCityId())) { cityId =
		 * custUserVO.getBankCityId(); } else if
		 * (!StringUtils.isNumeric(cityId)) { cityId =
		 * String.valueOf(cityService.getIdByName(cityId)); }
		 */

        if (!StringUtils.isNumeric(cityId)) {
            cityId = String.valueOf(cityService.getIdByName(cityId));
        }
        openAccountOP.setCity_id(StringUtils.isNotBlank(cityId) ? cityId : custUserVO.getBankCityId());
        // 银行卡号
        openAccountOP.setCapAcntNo(custUserVO.getCardNo());
        // 身份证
        openAccountOP.setIdCard(custUserVO.getIdNo());
        // 真实姓名
        openAccountOP.setRealName(custUserVO.getRealName());
        // 手机号
        openAccountOP.setMobile(custUserVO.getMobile());
        // 邮箱
        openAccountOP.setEmail(StringUtils.isNotBlank(email) ? email : custUserVO.getQq() + "@qq.com");

        LoanApplySimpleVO applyVo = loanApplyService.getUnFinishLoanApplyInfo(userId);
        if (null == applyVo || StringUtils.isBlank(applyVo.getApplyId())) {
            logger.error("该用户未进件,userId=" + userId);
            openRz = new OpenAccountResultVO();
            openRz.setSuccess(false);
            openRz.setCode(ErrInfo.NOTFIND_LOAN_RECORDS.getCode());
            openRz.setMsg(ErrInfo.NOTFIND_LOAN_RECORDS.getMsg());
            return openRz;
        }
        // ytodo 0303 加急券
        if (LoanApplySimpleVO.APPLY_STATUS_URGENT_S.intValue() != applyVo.getLoanApplyStatus().intValue()
                && LoanApplySimpleVO.APPLY_STATUS_SHOPPING.intValue() != applyVo.getLoanApplyStatus().intValue()) {
            logger.error("已提交成功，请刷新页面,applyId=" + applyVo.getApplyId());
            openRz = new OpenAccountResultVO();
            openRz.setSuccess(false);
            openRz.setCode("FAIL");
            openRz.setMsg("已提交成功，请刷新页面");
            return openRz;
        }
        // 1-ios,2-android,3-h5,4-api
        if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
                && !loanApplyService.isBuyShoppingGold(applyVo.getApplyId())) {// 口袋存管放款
            openRz = new OpenAccountResultVO();
            ApiResultVO apiResultVO = kdDepositService.comprehensive(applyVo.getApplyId(), returnUrl);
            if (ErrInfo.SUCCESS.getCode().equals(apiResultVO.getCode())) {// 请求成功
                openRz.setSuccess(true);
                if (apiResultVO.get("url") != null) {// 口袋下发页面
                    openRz.setUrl(apiResultVO.get("url").toString());
                }
            } else {
                openRz.setSuccess(false);
                openRz.setCode("FAIL");
                openRz.setMsg("code:" + apiResultVO.getCode() + ";" + apiResultVO.getMsg());
                return openRz;
            }
        } else if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()
                && !loanApplyService.isBuyShoppingGold(applyVo.getApplyId())) {
            // 调用汉金所接口开户
            openRz = new OpenAccountResultVO();
            HanJSUserOP op = new HanJSUserOP();
            op.setGender(IdcardUtils.getGenderByIdCard(openAccountOP.getIdCard()));
            op.setMobile(openAccountOP.getMobile());
            op.setName(openAccountOP.getRealName());
            HanJSResultVO result = hanJSUserService.openAccount(op);
            if ("SUCCESS".equals(result.getCode())) {
                logger.info("汉金所存管开户接口调用成功,手机号[{}]", openAccountOP.getMobile());
                openRz.setSuccess(true);
                openRz.setUrl(result.getData().toString());
                return openRz;
            } else {
                logger.info("汉金所存管开户接口调用失败,手机号[{}],失败原因[{}]", openAccountOP.getMobile(), result.getMessage());
                openRz.setSuccess(false);
                openRz.setCode("FAIL");
                openRz.setMsg(result.getMessage());
                return openRz;
            }
        } else {
            // 不调用投复利开户，直接返回成功
            openRz = new OpenAccountResultVO();
            openRz.setSuccess(true);
            openRz.setCode("SUCCESS");
            openRz.setMsg("开户成功");
            // 调用投复利接口开户
            // openRz = bankDepositService.openBankDepositAccount(openAccountOP,
            // productId);
            // logger.info("投复利开户响应:{},{}", custUserVO.getRealName(),
            // JsonMapper.toJsonString(openRz));
            // if (!openRz.isSuccess()) {
            // return openRz;
            // }
            // accountId = openRz.getAccountId();
            // if (StringUtils.isBlank(accountId)) {
            // openRz.setSuccess(false);
            // openRz.setCode("FAIL");
            // openRz.setMsg("存管账号为空");
            // return openRz;
            // }
        }

        // 保存开户信息
        IdentityInfoOP identityInfoOP = new IdentityInfoOP();
        identityInfoOP.setTrueName(custUserVO.getRealName());
        identityInfoOP.setCardNo(custUserVO.getCardNo());
        identityInfoOP.setIdNo(custUserVO.getIdNo());
        identityInfoOP.setBankCode(custUserVO.getBankCode());
        identityInfoOP.setAccount(custUserVO.getMobile());
        identityInfoOP.setUserId(userId);
        identityInfoOP.setSource(custUserVO.getSource().toString());
        identityInfoOP.setProductId(LoanProductEnum.XJDFQ.getId());
        identityInfoOP.setCityId(cityId);
        identityInfoOP.setUname(uname);
        identityInfoOP.setEmail(email);
        // 设置投复利返回恒丰银行电子账户UID
        // 懒猫回调有url参数
        if (StringUtils.isBlank(openRz.getUrl())) {
            identityInfoOP.setAccountId(accountId);
        }

        // 保存身份信息流程
        // 保存开户信息
        int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
        if (saveRz == 0) {
            openRz.setSuccess(false);
            openRz.setCode("FAIL");
            openRz.setMsg("保存开户信息失败");
            return openRz;
        }
        logger.info("账号[{}]来自产品[{}],开户成功,电子账户ID[{}]", identityInfoOP.getAccount(), identityInfoOP.getProductId(),
                accountId);
        // 清除用户信息缓存以便重新加载
        LoginUtils.cleanCustUserInfoCache(userId);
        return openRz;
    }

    /**
     * 借款确认并开通存管账户接口
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/enSureAgreement", method = RequestMethod.POST, name = "协议确认记录接口")
    @ResponseBody
    public ApiResult enSureAgreement(@Valid EnsureAgreementOP param, HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 参数验证结果判断
        if (StringUtils.isBlank(param.getUserId())) {
            logger.error("用户Id不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        String enSureAgreementLockCacheKey = "enSureAgreement_lock_" + param.getUserId();
        synchronized (AgreementPayController.class) {
            String enSureAgreementLock = JedisUtils.get(enSureAgreementLockCacheKey);
            if (enSureAgreementLock == null) {
                // 加锁，防止并发
                JedisUtils.set(enSureAgreementLockCacheKey, "locked", 120);
            } else {
                logger.warn("借款确认接口调用中，userId= {}", param.getUserId());
                // 处理中
                result.setCode("FAIL");
                result.setMsg("处理中,请稍后查询");
                return result;
            }
        }
        CustUserVO custUser = LoginUtils.getCustUserInfo(param.getUserId());
        if (null == custUser) {
            // 从数据库获取
            custUser = custUserService.getCustUserById(param.getUserId());
        }
        // 绑卡验证
        if (StringUtils.isBlank(custUser.getCardNo())) {
            logger.error("确认借款，未绑定银行卡，请前往个人中心绑卡，userId= {}", param.getUserId());
            result.setCode("FAIL");
            result.setMsg("未绑定银行卡，请前往个人中心绑卡");
            return result;
        }
        LoanApplySimpleVO applyVo = loanApplyService.getUnFinishLoanApplyInfo(param.getUserId());
        if (null == applyVo || StringUtils.isBlank(applyVo.getApplyId())) {
            logger.error("该用户未进件,userId=" + param.getUserId());
            return result.set(ErrInfo.NOTFIND_LOAN_RECORDS);
        }

        boolean existApplyId = sllService.isExistApplyId(applyVo.getApplyId());
        if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()
                && existApplyId
                && (null == custUser.getHjsAccountId() || 0 == custUser.getHjsAccountId().intValue())) {
            logger.error("汉金所渠道确认借款：:" + param.getUserId());
            JedisUtils.set(Global.HJS_OPEN1 + applyVo.getApplyId(), "lock", Global.THREE_DAY_CACHESECONDS);
            rongPointCutService.enSureAgreement(applyVo.getApplyId());
            return result.set(ErrInfo.SUCCESS);
        }

        if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()
                && existApplyId) {
            logger.error("口袋渠道确认借款：:" + param.getUserId());
            JedisUtils.set(Global.KD_OPEN1 + applyVo.getApplyId(), "lock", Global.THREE_DAY_CACHESECONDS);
            rongPointCutService.enSureAgreement(applyVo.getApplyId());
            return result.set(ErrInfo.SUCCESS);
        }

        if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue()) {
            logger.error("口袋存管放款订单不调用此方法:" + param.getUserId());
            result.setCode("FAIL");
            result.setMsg("放款渠道错误");
            return result;
        }

        if (StringUtils.isNotBlank(applyVo.getPayChannel())
                && Integer.parseInt(applyVo.getPayChannel()) == WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue()
                && (null == custUser.getHjsAccountId() || 0 == custUser.getHjsAccountId().intValue())) {
            logger.error("汉金所放款渠道未开户不能确认借款:" + param.getUserId());
            result.setCode("FAIL");
            result.setMsg("未开户，请刷新页面");
            return result;
        }
        if (LoanApplySimpleVO.APPLY_STATUS_SHOPPING.intValue() != applyVo.getLoanApplyStatus().intValue()) {
            logger.error("已提交成功，请刷新页面,applyId=" + applyVo.getApplyId());
            result.setCode("FAIL");
            result.setMsg("已提交成功，请刷新页面");
            return result;
        }
        // 分期产品，需要开户
        // if ((applyVo.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_28
        // || applyVo.getApproveTerm() == Global.XJD_AUTO_FQ_DAY_90)
        // && StringUtils.isBlank(custUser.getAccountId())) {
        // // 用户未开通存管,需要开户
        // OpenAccountResultVO openResult = creatJxbAccount(param.getUserId(),
        // param.getCityId(), param.getEmail(),
        // null, null);
        // if (!openResult.isSuccess()) {
        // result.setCode("FAIL");
        // result.setMsg(openResult.getMsg());
        // return result;
        // }
        // }
        // 14天口袋放款招行不能放款
        // if (applyVo.getApproveTerm() == Global.XJD_DQ_DAY_14 &&
        // "CMB".equals(custUser.getBankCode())) {
        // logger.error("招行不能放款，请更换绑卡,applyId=" + applyVo.getApplyId());
        // result.setCode("FAIL");
        // result.setMsg("暂不支持招行打款，请更换绑卡");
        // return result;
        // }
        // 15天未开户客户，招行不能放款
        // if (applyVo.getApproveTerm() == Global.XJD_DQ_DAY_15 &&
        // "CMB".equals(custUser.getBankCode())
        // && StringUtils.isBlank(custUser.getAccountId())) {
        // logger.error("招行不能放款，请更换绑卡,applyId=" + applyVo.getApplyId());
        // result.setCode("FAIL");
        // result.setMsg("暂不支持招行打款，请更换绑卡");
        // return result;
        // }
        if (LoanProductEnum.XJD.getId().equals(applyVo.getProductId())
                && LoanApplySimpleVO.APPLY_STATUS_SHOPPING.intValue() == applyVo.getLoanApplyStatus().intValue()
                && LoanApplySimpleVO.APPLY_PAY_TYPE_0.intValue() == applyVo.getLoanApplyPayType().intValue()) {
            logger.error("先支付才能推标！");
            result.setCode("FAIL");
            result.setMsg("暂不支持代扣旅游券,请更新APP");
            return result;
        }
        boolean flag = loanApplyService.saveShopedBorrowInfo(applyVo.getApplyId(), LoanApplySimpleVO.APPLY_PAY_TYPE_1);
        String orderNo = applyTripartiteRong360Service.getThirdIdByApplyId(applyVo.getApplyId());
        if (orderNo != null) {
            rongService.handCancle(orderNo);
            JedisUtils.set("rong360_jubao_borrow_info_" + applyVo.getApplyId(), "1", Global.THREE_DAY_CACHESECONDS);
        }
        if (flag) {
            // 更新用户表
			/*
			 * CustUserVO custUserVO = new CustUserVO();
			 * custUserVO.setId(param.getUserId());
			 * custUserVO.setEmail(param.getEmail());
			 * custUserService.updateCustUser(custUserVO);
			 */
            // 插入收货地址表
			/*
			 * GoodsAddressOP op = new GoodsAddressOP();
			 * op.setUserId(param.getUserId());
			 * op.setApplyId(applyVo.getApplyId());
			 * op.setMobile(applyVo.getMobile());
			 * op.setAddress(param.getGoodsAddress());
			 * op.setCity(param.getGoodsCity());
			 * op.setDistrict(param.getGoodsDistrict());
			 * op.setProvince(param.getGoodsProvince());
			 * op.setSource(applyVo.getSource());
			 * op.setProductId(LoanProductEnum.XJDFQ.getId());
			 * goodsAddressService.save(op);
			 */

            // 保存用户选择旅游产品
            if (StringUtils.isNoneBlank(param.getTripProductId())) {
                LoanTripProductDetailOP productDetailOP = new LoanTripProductDetailOP();
                productDetailOP.setApplyId(applyVo.getApplyId());
                productDetailOP.setCustId(param.getUserId());
                productDetailOP.setProductId(param.getTripProductId());
                loanTripProductService.saveCustProduct(productDetailOP);

            }

            // 清理用户缓存
            LoginUtils.cleanCustUserInfoCache(param.getUserId());
            rongPointCutService.enSureAgreement(applyVo.getApplyId());
            return result.set(ErrInfo.SUCCESS);
        }
        return result.set(ErrInfo.ERROR);
    }

    /**
     * 发送短信验证码(预绑卡)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendBindMsgCode", method = RequestMethod.POST, name = "发送短信验证码(预绑卡)")
    @ResponseBody
    public ApiResult sendBindMsgCode(HttpServletRequest request) throws Exception {
        // 参数验证结果判断
        String userId = request.getHeader("userId");
        // 返回参数构建
        ApiResult result = new ApiResult();
        // 截取ip
        String ip = Servlets.getIpAddress(request);
        // 从缓存中获取用户
        CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        if (null == custUserVO) {
            // 从数据库获取
            custUserVO = custUserService.getCustUserById(userId);
        }
        // 发送验证码
        // 调短息验证码服务获取短信验证码
        // 验证银行是否开通
        boolean isOpen = appBankLimitService.isOpen(custUserVO.getBankCode());
        if (!isOpen) {
            logger.error("银行卡暂不支持绑定:{},{}", userId, custUserVO.getBankCode());
            result.setCode("FAIL");
            result.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
            return result;
        }
        DirectBindCardOP bindCardOp = new DirectBindCardOP();
        bindCardOp.setCardNo(custUserVO.getCardNo());
        bindCardOp.setIdNo(custUserVO.getIdNo());
        bindCardOp.setMobile(custUserVO.getMobile());
        bindCardOp.setRealName(custUserVO.getRealName());
        bindCardOp.setUserId(userId);
        bindCardOp.setBankCode(custUserVO.getBankCode());
        bindCardOp.setSource(custUserVO.getSource().toString());
        bindCardOp.setIpAddr(Servlets.getIpAddress(request));
        // 宝付协议支付预绑卡银行发验证码
        BindCardResultVO bindCardResult = baofooAgreementPayService.agreementPreBind(bindCardOp);
        if (!bindCardResult.isSuccess() || StringUtils.isBlank(bindCardResult.getBindId())) {
            logger.error("[{}]协议支付预绑卡失败", custUserVO.getMobile());
            result.setCode("FAIL");
            result.setMsg(bindCardResult.getMsg());
            if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                    || "BF00127".equals(bindCardResult.getCode())) {
                result.setMsg(bindCardResult.getMsg() + ", 请更换绑卡");
            } else {
                result.setMsg(bindCardResult.getMsg() + ", 可在个人中心里更换绑卡");
            }
            return result;
        }
        result.setData(bindCardResult);
        // 清理用户缓存
        LoginUtils.cleanCustUserInfoCache(userId);
        return result;
    }

    /**
     * 存量客户宝付协议支付绑卡
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/baofooBind", method = RequestMethod.POST, name = "存量客户宝付协议支付绑卡")
    @ResponseBody
    public ApiResult baofooBind(@Valid ConfirmBindCardOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        param.setUserId(userId);
        BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(param);
        logger.info("宝付协议确认绑卡响应:{},{}", param.getUserId(), JsonMapper.toJsonString(bindCardResult));
        if (!bindCardResult.isSuccess()) {
            result.setCode("FAIL");
            result.setMsg(bindCardResult.getMsg());
            if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                    || "BF00127".equals(bindCardResult.getCode())) {
                result.setMsg(bindCardResult.getMsg() + ", 请更换绑卡");
            } else {
                result.setMsg(bindCardResult.getMsg() + ", 可在个人中心里更换绑卡");
            }
            return result;
        }
        if (StringUtils.isBlank(bindCardResult.getBindId())) {
            result.set(ErrInfo.CARD4_VERIFY_FAIL);
            return result;
        }

        BindCardVO bindInfo = bindCardService.findByOrderNo(param.getOrderNo());
        if (bindInfo == null) {
            result.set(ErrInfo.CARD4_VERIFY_FAIL);
            return result;
        }
        bindInfo.setBindId(bindCardResult.getBindId());
        bindInfo.setStatus(bindCardResult.getCode());
        bindInfo.setRemark(bindCardResult.getMsg());
        bindInfo.setChlName("宝付确认绑卡");
        bindInfo.setSource(param.getSource());
        bindInfo.setIp(Servlets.getIpAddress(request));

        int saveBc = bindCardService.update(bindInfo);
        if (saveBc == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        // 更新协议绑卡号
        CustUserVO vo = new CustUserVO();
        vo.setId(userId);
        vo.setProtocolNo(bindCardResult.getBindId());

        int saveRz = custUserService.updateCustUser(vo);
        if (saveRz == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        // 换卡时要解绑借卡
        if (param.getType() == 2 && StringUtils.isNotBlank(vo.getProtocolNo())) {
            try {
                baofooAgreementPayService.unBind(userId, vo.getProtocolNo());
            } catch (Exception e) {
                logger.error("解绑卡异常", e);
            }
        }

        // 清除用户信息缓存以便重新加载
        LoginUtils.cleanCustUserInfoCache(userId);
        // 返回结果处理
        OpenAccountVO openVo = new OpenAccountVO();
        // 快捷支付绑定id
        openVo.setBindId(bindCardResult.getBindId());
        // 保存开户信息结果
        openVo.setSaveResult(saveRz);
        result.setData(openVo);
        return result;
    }

    /**
     * 开通存管账户接口
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creatJxbAccount", method = RequestMethod.POST, name = "开通存管账户")
    @ResponseBody
    public ApiResult openAccount(HttpServletRequest request,
                                 @RequestParam(value = "userId", required = true) String userId,
                                 @RequestParam(value = "cityId", required = true) String cityId,
                                 @RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "source", required = false) String source) throws Exception {
        // 返回参数构建
        ApiResult result = new ApiResult();

        if (StringUtils.isBlank(userId)) {
            logger.error("开通存管账户，userId= {}", userId);
            result.setCode("FAIL");
            result.setMsg("参数错误");
            return result;
        }
        CustUserVO custUser = LoginUtils.getCustUserInfo(userId);
        if (null == custUser) {
            // 从数据库获取
            custUser = custUserService.getCustUserById(userId);
        }
        // 绑卡验证
        if (StringUtils.isBlank(custUser.getCardNo())) {
            logger.error("开通存管账户，未绑定银行卡，请前往个人中心绑卡，userId= {}", userId);
            result.setCode("FAIL");
            result.setMsg("未绑定银行卡，请前往个人中心绑卡");
            return result;
        }

        String productId = request.getParameter("productId");
        String returnUrl = request.getParameter("openReturnUrl");// 三方跳转页面
        OpenAccountResultVO resultVO = creatJxbAccount(userId, cityId, email, productId, source, returnUrl);
        if (resultVO.isSuccess()) {
            result.set(ErrInfo.SUCCESS);
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("url", resultVO.getUrl());

            result.setData(resultData);
            return result;
        } else {
            result.setCode("FAIL");
            result.setMsg(resultVO.getMsg());
            return result;
        }
    }

    /**
     * 口袋提现
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/kdWithdraw", method = RequestMethod.POST, name = "口袋提现")
    @ResponseBody
    public ApiResult kdWithdraw(HttpServletRequest request,
                                @RequestHeader(value = "userId", required = true) String userId) throws Exception {
        // 返回参数构建
        ApiResult result = new ApiResult();

        // LoanApplySimpleVO applyVo =
        // loanApplyService.getUnFinishLoanApplyInfo(userId);
        String payChannel = loanApplyService.getPayChannelByUserId(userId);
        // 查询未提现记录
        if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(payChannel)) {
            // 口袋存管
            List<KDwithdrawRecodeVO> recodeVOList = kdDepositService.findWithdrawRecode(userId, 0);

            if (recodeVOList != null && recodeVOList.size() > 0) {
                if (Float.valueOf(recodeVOList.get(0).getMoney()) > 0) {
                    result.put("cashAmt", recodeVOList.get(0).getMoney());// 可提现金额
                    result.put("applyId", recodeVOList.get(0).getApplyId());// 申请id
                    result.put("url", Global.getConfig("api.server.url") + "api/loan/enSureKdWithdraw");
                } else {
                    result.put("cashAmt", 0);
                }
            } else {
                result.put("cashAmt", 0);
            }
            logger.info("口袋查询可提现记录:用户ID{},查询结果{}", userId, JsonMapper.toJsonString(result));
        } else if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(payChannel)) {
            // 汉金所提现
            // 查询可提现的金额
            PayLogVO vo = payLogService.findWithdrawAmount(userId);
            if (null != vo && vo.getSuccAmt().compareTo(BigDecimal.ZERO) > 0) {
                result.put("cashAmt", vo.getSuccAmt());// 可提现金额
                result.put("applyId", vo.getApplyId());// 申请id
                result.put("url", Global.getConfig("api.server.url") + "api/loan/enSureKdWithdraw");
            } else {
                result.put("cashAmt", 0);
            }
            logger.info("汉金所查询可提现记录:用户ID{},查询结果{}", userId, JsonMapper.toJsonString(result));
        }
        // logger.info("口袋提现result===" + JsonMapper.toJsonString(result));
        return result;

    }

    /**
     * 确认口袋提现
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/enSureKdWithdraw", method = RequestMethod.POST, name = "确认口袋提现")
    @ResponseBody
    public ApiResult enSureKdWithdraw(HttpServletRequest request,
                                      @RequestParam(value = "applyId", required = true) String applyId) throws Exception {
        // 返回参数构建
        ApiResult result = new ApiResult();
        String userId = request.getHeader("userId");
        String payChannel = loanApplyService.getPayChannelByUserId(userId);
        if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().toString().equals(payChannel)) {
            // 口袋存管
            String returnUrl = request.getParameter("cashReturnUrl");// 三方跳转页面
            ApiResultVO apiResultVO = kdDepositService.withdraw(applyId, returnUrl);
            if (ErrInfo.SUCCESS.getCode().equals(apiResultVO.getCode())) {
                result.put("url", apiResultVO.get("url"));// 跳转url
            } else {
                result.setCode(apiResultVO.getCode());
                result.setMsg(apiResultVO.getMsg());
            }
            logger.info("口袋存管确认提现:{},{},确认结果{}", userId, applyId, JsonMapper.toJsonString(result));
        } else if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().toString().equals(payChannel)) {
            // 汉金所提现
            PayLogVO vo = payLogService.findWithdrawAmount(userId);
            if (null != vo) {
                HanJSWithdrawOP op = new HanJSWithdrawOP();
                op.setOrderId(vo.getApplyId());
                op.setAmount(vo.getTxAmt().toString());
                op.setMobile(vo.getToMobile());
                HanJSResultVO hanResult = hanJSUserService.withdraw(op);
                if (ErrInfo.SUCCESS.getCode().equals(hanResult.getCode())) {
                    result.put("url", hanResult.getData());// 跳转url
                } else {
                    result.setCode(hanResult.getCode());
                    result.setMsg(hanResult.getMessage());
                }
            }
            logger.info("汉金所确认提现:{},{},确认结果{}", userId, applyId, JsonMapper.toJsonString(result));
        }
        return result;

    }

    /**
     * 口袋提现记录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/withdrawRecode", method = RequestMethod.POST, name = "口袋提现记录")
    @ResponseBody
    public ApiResult withdrawRecode(HttpServletRequest request,
                                    @RequestHeader(value = "userId", required = true) String userId) throws Exception {
        // 返回参数构建
        ApiResult result = new ApiResult();

        // LoanApplySimpleVO applyVo =
        // loanApplyService.getUnFinishLoanApplyInfo(userId);
        // 口袋提现记录
        List<KDwithdrawRecodeVO> recodeVOList = kdDepositService.findWithdrawRecode(userId, null);
        // 汉金所提现记录
        List<KDwithdrawRecodeVO> recodeVOList1 = payLogService.findHJSWithdrawRecode(userId);
        for (KDwithdrawRecodeVO kDwithdrawRecodeVO : recodeVOList1) {
            recodeVOList.add(kDwithdrawRecodeVO);
        }
        result.setData(recodeVOList);
        return result;

    }

}
