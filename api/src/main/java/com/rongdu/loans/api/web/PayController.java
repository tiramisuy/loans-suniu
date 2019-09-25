package com.rongdu.loans.api.web;

import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.PinYinUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.BankCode;
import com.rongdu.loans.api.vo.OpenAccountVO;
import com.rongdu.loans.app.service.AppBankLimitService;
import com.rongdu.loans.bankDeposit.option.OpenAccountOP;
import com.rongdu.loans.bankDeposit.option.TermsAuthOP;
import com.rongdu.loans.bankDeposit.service.BankDepositService;
import com.rongdu.loans.bankDeposit.vo.OpenAccountResultVO;
import com.rongdu.loans.bankDeposit.vo.TermsAuthVO;
import com.rongdu.loans.bankDeposit.vo.TransactionRecordVO;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.cust.option.IdentityInfoOP;
import com.rongdu.loans.cust.option.RebindCardInfoOP;
import com.rongdu.loans.cust.option.UpdatePwdOP;
import com.rongdu.loans.cust.service.BindCardService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.BindCardVO;
import com.rongdu.loans.cust.vo.BindInfoVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.option.RePayOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.ConfirmPayResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.pay.op.ConfirmAuthPayOP;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.service.BaofooAgreementPayService;
import com.rongdu.loans.pay.service.BaofooAuthPayService;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.ConfirmAuthPayVO;
import com.rongdu.loans.sys.web.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付相关处理Controller
 *
 * @author likang
 * @version 2017-7-18
 */
@Controller
@RequestMapping(value = "${adminPath}/pay")
public class PayController extends BaseController {

    @Autowired
    private CustUserService custUserService;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private BaofooAuthPayService baofooAuthPayService;

    @Autowired
    private BankDepositService bankDepositService;

    @Autowired
    private AppBankLimitService appBankLimitService;

    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private BaofooAgreementPayService baofooAgreementPayService;

    @Autowired
    private TltAgreementPayService tltAgreementPayService;

    @Autowired
    private RongPointCutService rongPointCutService;

    @Autowired
    private JDQStatusFeedBackService jdqStatusFeedBackService;

    @Autowired
    private JDQService jdqService;

    /**
     * 开通恒丰银行电子账户，身份信息入库
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/creatJxbAccount", method = RequestMethod.POST, name = "开户")
    @ResponseBody
    public ApiResult creatJxbAccount(@Valid IdentityInfoOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 验证银行是否开通
        if (LoanProductEnum.XJD.getId().equals(param.getProductId())) {
            boolean isOpen = appBankLimitService.isOpen(param.getBankCode());
            if (!isOpen) {
                logger.error("银行卡暂不支持绑定:{},{}", userId, param.getBankCode());
                result.setCode("FAIL");
                result.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
                return result;
            }
        }
        // 现金贷ios验证码
        if (LoanProductEnum.XJD.getId().equals(param.getProductId()) && "1".equals(param.getSource())) {
            // 短信验证码认证
            String msgVerCode = param.getMsgVerCode();
            String cache = JedisUtils.get(param.getAccount() + Global.BANKDEPOSIT_SRVAUTHCODE_SUFFIX);
            if (!StringUtils.equals(msgVerCode, cache)) {
                result.set(ErrInfo.MSG_ERROR);
                return result;
            }
        }
        // 截取ip
        String ip = Servlets.getIpAddress(request);
        // 宝付支付四要素验证（绑卡）流程
        // 宝付接口参数对象设置
        DirectBindCardOP bindCardOP = new DirectBindCardOP();
        // 银行卡号
        bindCardOP.setCardNo(param.getCardNo());
        // 银行编号
        bindCardOP.setBankCode(param.getBankCode());
        // 姓名
        bindCardOP.setRealName(param.getTrueName());
        // 身份证号
        bindCardOP.setIdNo(param.getIdNo());
        // 手机号
        bindCardOP.setMobile(param.getAccount());
        // ip
        bindCardOP.setIpAddr(ip);
        // 用户id
        bindCardOP.setUserId(userId);
        // 渠道
        bindCardOP.setSource(param.getSource());
        // 开户行地址
        bindCardOP.setCityId(param.getCityId());
        BindCardResultVO bindCardResult = baofooAuthPayService.directBindCard(bindCardOP);
        logger.info("宝付四要素验证响应:{},{}", param.getTrueName(), JsonMapper.toJsonString(bindCardResult));
        if (!bindCardResult.isSuccess()) {
            result.setCode("FAIL");
            result.setMsg(bindCardResult.getMsg());
            if ("BF00101".equals(bindCardResult.getCode()) || "BF00102".equals(bindCardResult.getCode())
                    || "BF00127".equals(bindCardResult.getCode())) {
                result.setMsg(bindCardResult.getMsg() + ", 请更换绑卡");
            }
            return result;
        }
        if (StringUtils.isBlank(bindCardResult.getBindId())) {
            result.set(ErrInfo.CARD4_VERIFY_FAIL);
            return result;
        }
        param.setBindId(bindCardResult.getBindId());

        // 初始化电子账户
        String accountId = null;
        Integer isNewUser = 0;
        // 现金贷不开通投复利账户
        if (LoanProductEnum.CCD.getId().equals(param.getProductId())
                || LoanProductEnum.TFL.getId().equals(param.getProductId())
                || LoanProductEnum.LYFQ.getId().equals(param.getProductId())) {
            // 恒丰银行开户流程
            // 调恒丰银行开户接口
            OpenAccountOP openAccountOP = new OpenAccountOP();
            for (BankCode bankCode : BankCode.values()) {
                if (param.getBankCode().equals(bankCode.getbName())) {
                    // 银行行别号
                    openAccountOP.setParent_bank_id(bankCode.getBcode());
                }
            }
            // 用户名
            String uname = PinYinUtils.toPinyin(param.getTrueName())
                    + param.getIdNo().substring(param.getIdNo().length() - 6, param.getIdNo().length());
            openAccountOP.setUname(uname);
            // 银行卡开户行所在的城市编号
            openAccountOP.setCity_id(param.getCityId());
            // 银行卡号
            openAccountOP.setCapAcntNo(param.getCardNo());
            // 身份证
            openAccountOP.setIdCard(param.getIdNo());
            // 真实姓名
            openAccountOP.setRealName(param.getTrueName());
            // 手机号
            openAccountOP.setMobile(param.getAccount());
            // 从缓存中获取用户
            CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
            if (null == custUserVO) {
                // 从数据库获取
                custUserVO = custUserService.getCustUserById(userId);
            }
            // 邮箱
            openAccountOP.setEmail(custUserVO.getEmail());
            // 调用投复利接口开户
            OpenAccountResultVO openRz = bankDepositService.openBankDepositAccount(openAccountOP, param.getProductId());
            logger.info("投复利开户响应:{},{}", param.getTrueName(), JsonMapper.toJsonString(openRz));
            if (null == openRz || !openRz.isSuccess()) {
                result.setCode(openRz.getCode());
                result.setMsg(openRz.getMsg());
                logger.debug("账号[{}]开通恒丰银行电子账户开户失败[{}],错误信息[{}]", param.getAccount(), param.getCardNo(),
                        openRz.getMsg());
                return result;
            }
            isNewUser = openRz.getIsNewUser();
            accountId = openRz.getAccountId();
            if (StringUtils.isBlank(accountId)) {
                result.set(ErrInfo.CG_OPEN_FAIL);
                return result;
            }
            // 诚诚贷天威开户
            /*
             * if (LoanProductEnum.CCD.getId().equals(param.getProductId())) {
             * createCCDAccount(openRz,openAccountOP); }
             */
            param.setUname(uname);
            // 设置投复利返回恒丰银行电子账户UID
            param.setAccountId(accountId);
        }

        // 保存身份信息流程
        // 用户id
        param.setUserId(userId);
        // 申请编号
        // String applyId = CommonUtils.getApplyNofromCache(userId);
        // param.setApplyId(applyId);
        // 保存开户信息
        int saveRz = custUserService.saveIdentityInfo(param);
        if (saveRz == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }
        logger.info("账号[{}]来自产品[{}],开户成功,电子账户ID[{}]", param.getAccount(), param.getProductId(), accountId);
        // 清除用户信息缓存以便重新加载
        LoginUtils.cleanCustUserInfoCache(userId);
        // 返回结果处理
        OpenAccountVO openVo = new OpenAccountVO();
        // 电子账户
        openVo.setAccountId(accountId);
        // 快捷支付绑定id
        openVo.setBindId(param.getBindId());
        // 保存开户信息结果
        openVo.setSaveResult(saveRz);
        openVo.setIsNewUser(isNewUser);
        result.setData(openVo);
        return result;
    }

    /**
     * 认证第四步开户-宝付协议支付绑卡确认
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/baofooBind", method = RequestMethod.POST, name = "绑卡开户")
    @ResponseBody
    public ApiResult baofooBind(@Valid ConfirmBindCardOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 从缓存中获取用户
        CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        if (null == custUserVO) {
            // 从数据库获取
            custUserVO = custUserService.getCustUserById(userId);
        }
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

        IdentityInfoOP identityInfoOP = new IdentityInfoOP();
        identityInfoOP.setTrueName(bindInfo.getName());
        identityInfoOP.setCardNo(bindInfo.getCardNo());
        identityInfoOP.setIdNo(bindInfo.getIdNo());
        identityInfoOP.setProtocolNo(bindCardResult.getBindId());
        identityInfoOP.setUserId(userId);
        identityInfoOP.setSource(param.getSource());
        identityInfoOP.setBankMobile(custUserVO.getMobile());
        identityInfoOP.setBankCode(bindCardResult.getBankCode());
        identityInfoOP.setProductId(param.getProductId());
        identityInfoOP.setCityId(param.getCityId());
        identityInfoOP.setAccount(bindInfo.getMobile());

        bindInfo.setBindId(bindCardResult.getBindId());
        bindInfo.setStatus(bindCardResult.getCode());
        bindInfo.setRemark(bindCardResult.getMsg());
        bindInfo.setBankCode(bindCardResult.getBankCode());
        bindInfo.setBankName(bindCardResult.getBankName());
        bindInfo.setChlName("宝付确认绑卡");
        bindInfo.setSource(param.getSource());
        bindInfo.setBankCity(param.getCityId());
        bindInfo.setIp(Servlets.getIpAddress(request));

        int saveBc = bindCardService.update(bindInfo);
        if (saveBc == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        // 初始化电子账户
        String accountId = null;
        Integer isNewUser = 0;
        // 现金贷不开通投复利账户
        if (LoanProductEnum.CCD.getId().equals(param.getProductId())
                || LoanProductEnum.TFL.getId().equals(param.getProductId())
                || LoanProductEnum.LYFQ.getId().equals(param.getProductId())) {
            // 恒丰银行开户流程
            // 调恒丰银行开户接口
            OpenAccountOP openAccountOP = new OpenAccountOP();
            for (BankCode bankCode : BankCode.values()) {
                if (bindInfo.getBankCode().equals(bankCode.getbName())) {
                    // 银行行别号
                    openAccountOP.setParent_bank_id(bankCode.getBcode());
                }
            }
            // 用户名
            String uname = PinYinUtils.toPinyin(bindInfo.getName())
                    + bindInfo.getIdNo().substring(bindInfo.getIdNo().length() - 6, bindInfo.getIdNo().length());
            openAccountOP.setUname(uname);
            // 银行卡开户行所在的城市编号
            openAccountOP.setCity_id(param.getCityId());
            // 银行卡号
            openAccountOP.setCapAcntNo(bindInfo.getCardNo());
            // 身份证
            openAccountOP.setIdCard(bindInfo.getIdNo());
            // 真实姓名
            openAccountOP.setRealName(bindInfo.getName());
            // 手机号
            openAccountOP.setMobile(bindInfo.getMobile());

            // 调用投复利接口开户
            OpenAccountResultVO openRz = bankDepositService.openBankDepositAccount(openAccountOP, param.getProductId());
            logger.info("投复利开户响应:{},{}", bindInfo.getName(), JsonMapper.toJsonString(openRz));
            if (null == openRz || !openRz.isSuccess()) {
                result.setCode(openRz.getCode());
                result.setMsg(openRz.getMsg());
                logger.debug("账号[{}]开通恒丰银行电子账户开户失败[{}],错误信息[{}]", bindInfo.getMobile(), bindInfo.getCardNo(),
                        openRz.getMsg());
                return result;
            }
            isNewUser = openRz.getIsNewUser();
            accountId = openRz.getAccountId();
            if (StringUtils.isBlank(accountId)) {
                result.set(ErrInfo.CG_OPEN_FAIL);
                return result;
            }
            identityInfoOP.setAccountId(accountId);
        }

        int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
        if (saveRz == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        // 换卡时要解绑借卡
        if (param.getType() == 2 && StringUtils.isNotBlank(custUserVO.getProtocolNo())) {
            try {
                baofooAgreementPayService.unBind(userId, custUserVO.getProtocolNo());
            } catch (Exception e) {
                logger.error("解绑卡异常", e);
            }
        }

        // 清除用户信息缓存以便重新加载
        LoginUtils.cleanCustUserInfoCache(userId);
        // 返回结果处理
        OpenAccountVO openVo = new OpenAccountVO();
        // 电子账户
        openVo.setAccountId(accountId);
        // 快捷支付绑定id
        openVo.setBindId(bindCardResult.getBindId());
        // 保存开户信息结果
        openVo.setSaveResult(saveRz);
        openVo.setIsNewUser(isNewUser);
        result.setData(openVo);
        return result;
    }

    /**
     * 通联协议支付签约
     *
     * @param param
     * @param errors
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/tonglianBind", method = RequestMethod.POST, name = "协议支付签约")
    @ResponseBody
    public ApiResult tonglianBind(@Valid ConfirmBindCardOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 从缓存中获取用户
        CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        if (null == custUserVO) {
            // 从数据库获取
            custUserVO = custUserService.getCustUserById(userId);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        param.setUserId(userId);
//        BindCardResultVO bindCardResult = baofooAgreementPayService.agreementConfirmBind(param);

        BindCardResultVO bindCardResult = tltAgreementPayService.agreementPaySign(param);
        logger.info("通联协议确认确认签约响应:{},{}", param.getUserId(), JsonMapper.toJsonString(bindCardResult));
        if (!bindCardResult.isSuccess()) {
            result.setCode("FAIL");
            result.setMsg(bindCardResult.getMsg());
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

        IdentityInfoOP identityInfoOP = new IdentityInfoOP();
        identityInfoOP.setTrueName(bindInfo.getName());
        identityInfoOP.setCardNo(bindInfo.getCardNo());
        identityInfoOP.setIdNo(bindInfo.getIdNo());
        identityInfoOP.setProtocolNo(bindCardResult.getBindId());
        identityInfoOP.setUserId(userId);
        identityInfoOP.setSource(param.getSource());
        identityInfoOP.setBankMobile(custUserVO.getMobile());
        identityInfoOP.setBankCode(bindInfo.getBankCode());
        identityInfoOP.setProductId(param.getProductId());
        identityInfoOP.setCityId(param.getCityId());
        identityInfoOP.setAccount(bindInfo.getMobile());
        identityInfoOP.setBindId(bindCardResult.getBindId());

        bindInfo.setBindId(bindCardResult.getBindId());
        bindInfo.setStatus(bindCardResult.getCode());
        bindInfo.setRemark(bindCardResult.getMsg());
        bindInfo.setBankCode(bindInfo.getBankCode());
        bindInfo.setBankName(bindInfo.getBankName());
        bindInfo.setChlName("通联确认协议支付绑卡");
        bindInfo.setSource(param.getSource());
        bindInfo.setBankCity(param.getCityId());
        bindInfo.setIp(Servlets.getIpAddress(request));

        int saveBc = bindCardService.update(bindInfo);
        if (saveBc == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        // 初始化电子账户
        String accountId = null;
        Integer isNewUser = 0;

        int saveRz = custUserService.saveIdentityInfo(identityInfoOP);
        if (saveRz == 0) {
            result.set(ErrInfo.ERROR);
            return result;
        }

        if (ChannelEnum.JIEDIANQIAN.getCode().equals(custUserVO.getChannel())
        || ChannelEnum.JIEDIANQIAN2.getCode().equals(custUserVO.getChannel())){
            LoanApplySimpleVO vo = loanApplyService.getUnFinishLoanApplyInfo(custUserVO.getId());
            // 没有未完结的订单 插入待生成订单key
            if (StringUtils.isBlank(vo.getApplyId())){
                FileInfoVO fileInfo = custUserService.getLastJDQBaseByUserId(custUserVO.getId());
                if (null != fileInfo){
                    String applyId = jdqService.getApplyId(fileInfo.getApplyId());
                    if (StringUtils.isBlank(applyId)){
                        Map<String, String> map = Maps.newHashMap();
                        map.put(fileInfo.getApplyId(), String.valueOf(System.currentTimeMillis()));
                        JedisUtils.mapPut(Global.JDQ_THIRD_KEY, map);
                    }
                }

            }
            // 借点钱换卡成功后 推送状态
            if (null != vo && StringUtils.isNotBlank(vo.getApplyId())){
                jdqStatusFeedBackService.orderStatusFeedBack(vo.getApplyId());
            }

        }

        // 清除用户信息缓存以便重新加载
        LoginUtils.cleanCustUserInfoCache(userId);
        // 返回结果处理
        OpenAccountVO openVo = new OpenAccountVO();
        // 电子账户
        openVo.setAccountId(accountId);
        // 快捷支付绑定id
        openVo.setBindId(bindCardResult.getBindId());
        // 保存开户信息结果
        openVo.setSaveResult(saveRz);
        openVo.setIsNewUser(isNewUser);
        result.setData(openVo);
        return result;
    }


    /**
     * 快捷支付-预支付
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/prePay", method = RequestMethod.POST, name = "快捷支付-预支付")
    @ResponseBody
    public ApiResult prePay(@Valid RePayOP param, Errors errors, HttpServletRequest request) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 用户id
        String userId = request.getHeader("userId");
        logger.error("预支付，请升级app版本，userId= {}", userId);
        result.setCode("FAIL");
        result.setMsg("请升级app版本");
        return result;

        // // 截取ip
        // String ip = Servlets.getIpAddress(request);
        // // 申请编号
        // String applyId = null;
        // // 合同编号
        // String contractId = null;
        // // 支付类型，1=还款，2=延期，3=加急,5旅游券，兼容之前APP版本默认为1
        // if (param.getPayType() == null) {
        // param.setPayType(1);
        // }
        // // 支付类型错误
        // if (!PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())
        // && !PayTypeEnum.DELAY.getId().equals(param.getPayType())
        // && !PayTypeEnum.URGENT.getId().equals(param.getPayType())
        // && !PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
        // logger.error("预支付，支付类型错误，userId= {},payType={}", userId,
        // param.getPayType());
        // result.setCode("FAIL");
        // result.setMsg("支付失败");
        // return result;
        // }
        // if (PayTypeEnum.TRIP.getId().equals(param.getPayType()) &&
        // StringUtils.isBlank(param.getTripProductId())) {
        // logger.error("协议支付，旅游产品id为空，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("请选择旅游产品");
        // return result;
        // }
        // if (!PayTypeEnum.URGENT.getId().equals(param.getPayType())
        // && !PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
        // // 还款计划编号非空判断
        // if (StringUtils.isBlank(param.getRepayPlanItemId())) {
        // logger.error("预支付，还款计划编号为空，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("还款计划编号为空");
        // return result;
        // }
        // // 还款计划状态判断
        // StatementVO statementVO =
        // settlementService.getStatementByItemId(param.getRepayPlanItemId());
        // if (null == statementVO || statementVO.getCurRepayStatus() == 1) {
        // logger.error("预支付，还款计划为空或已结清，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("支付成功,请刷新页面");
        // return result;
        // }
        // // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        // Long payCount =
        // repayLogService.countPayingByRepayPlanItemId(statementVO.getCurRepayItemId());
        // if (payCount != 0) {
        // logger.error("预支付，重复支付，userId= {},repayPlanItemId={}", userId,
        // statementVO.getCurRepayItemId());
        // result.setCode("FAIL");
        // result.setMsg("交易处理中,请稍后查询");
        // return result;
        // }
        // applyId = statementVO.getApplyId();
        // contractId = statementVO.getContNo();
        // } else {
        // // 申请订单号非空判断
        // if (StringUtils.isBlank(param.getApplyId())) {
        // logger.error("预支付，申请订单号为空，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("申请订单号为空");
        // return result;
        // }
        // // 申请订单号状态判断
        // LoanApplySimpleVO applyVO =
        // loanApplyService.getLoanApplyById(param.getApplyId());
        // if (applyVO == null || XjdLifeCycle.LC_RAISE_0 !=
        // applyVO.getProcessStatus().intValue()) {
        // logger.error("预支付，申请订单号为空或状态错误,status={}",
        // applyVO.getProcessStatus());
        // result.setCode("FAIL");
        // result.setMsg("支付成功,请刷新页面");
        // return result;
        // }
        // // 统计已经提交支付，正在处理中的订单，如果有，就不重复提交支付
        // Long payCount =
        // repayLogService.countPayingByApplyId(param.getApplyId());
        // if (payCount != 0) {
        // logger.error("预支付，重复支付，userId= {},applyId={}", userId,
        // param.getApplyId());
        // result.setCode("FAIL");
        // result.setMsg("交易处理中,请稍后查询");
        // return result;
        // }
        // applyId = param.getApplyId();
        // contractId = "";
        // param.setRepayPlanItemId("");
        // }
        // // 验证金额
        // boolean txAmtError = false;
        // LoanApplySimpleVO currApplyVO =
        // loanApplyService.getUnFinishLoanApplyInfo(userId);
        // if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())) {
        // if (currApplyVO.getCurToltalRepayAmt().compareTo(new
        // BigDecimal(param.getTxAmt())) != 0) {
        // txAmtError = true;
        // logger.error("预支付，还款金额错误,userId={},applyId={},currAmt={},txAmt={}",
        // userId, applyId,
        // currApplyVO.getCurToltalRepayAmt(), param.getTxAmt());
        // }
        // } else if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
        // if (currApplyVO.getCurDelayRepayAmt().compareTo(new
        // BigDecimal(param.getTxAmt())) != 0) {
        // txAmtError = true;
        // logger.error("预支付，延期金额错误,userId={},applyId={},currAmt={},txAmt={}",
        // userId, applyId,
        // currApplyVO.getCurDelayRepayAmt(), param.getTxAmt());
        // }
        // } else if (PayTypeEnum.URGENT.getId().equals(param.getPayType())) {
        // if (currApplyVO.getUrgentFee().compareTo(new
        // BigDecimal(param.getTxAmt())) != 0) {
        // txAmtError = true;
        // logger.error("预支付，加急金额错误,userId={},applyId={},currAmt={},txAmt={}",
        // userId, applyId,
        // currApplyVO.getUrgentFee(), param.getTxAmt());
        // }
        // } else if (PayTypeEnum.TRIP.getId().equals(param.getPayType())) {
        // if (currApplyVO.getUrgentFee().compareTo(new
        // BigDecimal(param.getTxAmt())) != 0) {
        // txAmtError = true;
        // logger.error("预支付，旅游券金额错误,userId={},applyId={},currAmt={},txAmt={}",
        // userId, applyId,
        // currApplyVO.getUrgentFee(), param.getTxAmt());
        // }
        // }
        // if (txAmtError || new
        // BigDecimal(param.getTxAmt()).compareTo(BigDecimal.ZERO) <= 0) {
        // result.setCode("FAIL");
        // result.setMsg("支付金额错误,请刷新页面");
        // return result;
        // }
        // // 7天的不还款
        // LoanApplySimpleVO applyVO =
        // loanApplyService.getLoanApplyById(applyId);
        // if (applyVO.getApplyTerm() == 7) {
        // logger.error("预支付，7天暂时无法线上还款，userId= {}", userId);
        // result.set(ErrInfo.LIMIT_PAY);
        // return result;
        // }
        // // 现金贷分期产品不能延期
        // boolean isDelay = true;
        // if (LoanProductEnum.XJDFQ.getId().equals(applyVO.getProductId())) {
        // isDelay = false;
        // } else if (LoanProductEnum.XJD.getId().equals(applyVO.getProductId())
        // && applyVO.getTerm().intValue() > 1) {
        // isDelay = false;
        // }
        // if (!isDelay && PayTypeEnum.DELAY.getId().equals(param.getPayType()))
        // {
        // logger.error("预支付，现金贷分期产品不能延期，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("暂不支持展期");
        // return result;
        // }
        //
        // // 银行卡绑定验证
        // CustUserVO custUserVO = LoginUtils.getCustUserInfo(userId);
        // if (null == custUserVO) {
        // // 从数据库获取
        // custUserVO = custUserService.getCustUserById(userId);
        // if (null == custUserVO) {
        // logger.error("预支付，未查询到用户信息，userId= {}", userId);
        // result.set(ErrInfo.UNFIND_CUST);
        // return result;
        // }
        // // 更新缓存中的用户信息
        // LoginUtils.cacheCustUserInfo(custUserVO);
        // }
        // // 绑卡验证
        // if (StringUtils.isBlank(custUserVO.getCardNo()) ||
        // StringUtils.isBlank(custUserVO.getBindId())) {
        // logger.error("预支付，未绑定银行卡，请前往个人中心绑卡，userId= {}", userId);
        // result.setCode("FAIL");
        // result.setMsg("未绑定银行卡，请前往个人中心绑卡");
        // return result;
        // }
        //
        // // 构造接口参数对象
        // PreAuthPayOP preAuthPayOP = new PreAuthPayOP();
        // preAuthPayOP.setContractId(contractId);
        // preAuthPayOP.setApplyId(applyId);
        // preAuthPayOP.setUserId(userId);
        // preAuthPayOP.setTxAmt(param.getTxAmt());
        // preAuthPayOP.setRepayPlanItemId(param.getRepayPlanItemId());
        // preAuthPayOP.setIpAddr(ip);
        // preAuthPayOP.setSource(param.getSource());
        // preAuthPayOP.setPayType(param.getPayType());
        // // 接口调用
        // PreAuthPayVO preAuthPayVO =
        // baofooAuthPayService.preAuthPay(preAuthPayOP);
        // if (!preAuthPayVO.isSuccess()) {
        // logger.error("预支付，{}，userId= {}", preAuthPayVO.getMsg(), userId);
        // result.setCode("FAIL");
        // result.setMsg(preAuthPayVO.getMsg());
        // if ("BF00127".equals(preAuthPayVO.getCode()) ||
        // "BF00342".equals(preAuthPayVO.getCode())) {
        // result.setMsg(preAuthPayVO.getMsg() + ", 请更换绑卡");
        // }
        // return result;
        // }
        // if (StringUtils.isBlank(preAuthPayVO.getChlOrderNo())) {
        // logger.error("预支付，交易流水号为空，userId= {}", userId);
        // result.set(ErrInfo.PREPAY_FAIL);
        // return result;
        // }
        // logger.info("预支付，成功，userId= {},chlOrderNo={}", userId,
        // preAuthPayVO.getChlOrderNo());
        // // 获取预支付信息
        // param.setBindId(custUserVO.getBindId());
        // param.setFullName(custUserVO.getRealName());
        // param.setBankCode(custUserVO.getBankCode());
        // param.setCardNo(custUserVO.getCardNo());
        // param.setPayComOrderNo(preAuthPayVO.getChlOrderNo());
        // param.setTxType(Global.REPAY_TYPE_MANUAL);
        // param.setUserId(userId);
        // CommonUtils.cachePreAuthPayResult(param);
        // // 返回值
        // result.setData(preAuthPayVO);
        // return result;
    }

    /**
     * 快捷支付-确认支付
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/confirmPay", method = RequestMethod.POST, name = "快捷支付-确认支付")
    @ResponseBody
    public ApiResult confirmPay(@Valid ConfirmAuthPayOP param, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 缓存中获取支付信息
        RePayOP rePayOP = CommonUtils.getPreAuthPayResult(param.getChlOrderNo());
        if (null == rePayOP) {
            logger.error("确认支付，超时，chlOrderNo= {}", param.getChlOrderNo());
            result.set(ErrInfo.PAY_TIMEOUT);
            return result;
        }
        // 支付类型，1=还款，2=延期，3=加急，兼容之前APP版本默认为1
        if (param.getPayType() == null) {
            param.setPayType(1);
        }
        // 预支付和确认支付类型不一致
        if (!rePayOP.getPayType().equals(param.getPayType())) {
            logger.error("确认支付，预支付和确认支付类型不一致，chlOrderNo= {},{},{}", param.getChlOrderNo(), rePayOP.getPayType(),
                    param.getPayType());
            result.set(ErrInfo.PAY_TIMEOUT);
            return result;
        }

        String userConfirmPayLockCacheKey = "user_confirmPay_lock_" + param.getChlOrderNo();
        synchronized (PayController.class) {
            String userConfirmPayLock = JedisUtils.get(userConfirmPayLockCacheKey);
            if (userConfirmPayLock == null) {
                // 加锁，防止并发
                JedisUtils.set(userConfirmPayLockCacheKey, "locked", 120);
            } else {
                logger.warn("确认支付接口调用中，chlOrderNo= {}", param.getChlOrderNo());
                // 处理中
                result.set(ErrInfo.WAITING);
                return result;
            }
        }

        try {
            // 调用确认支付接口
            ConfirmAuthPayVO confirmAuthPayVO = baofooAuthPayService.confirmAuthPay(param);
            // 保存支付结果
            rePayOP.setPayStatus(confirmAuthPayVO.getCode());
            rePayOP.setOrderInfo(confirmAuthPayVO.getMsg());
            rePayOP.setPaySuccAmt(confirmAuthPayVO.getSuccAmt());
            rePayOP.setPaySuccTime(confirmAuthPayVO.getSuccTime());
            // 支付结果返回前段
            if (!confirmAuthPayVO.isSuccess()) {
                rongPointCutService.settlementPoint(rePayOP.getRepayPlanItemId(), false);// 用作Rong360订单还款时，切面通知的切入点标记
                logger.error("确认支付，{}，chlOrderNo= {}", confirmAuthPayVO.getMsg(), param.getChlOrderNo());
                result.setCode("FAIL");
                result.setMsg(confirmAuthPayVO.getMsg());
                if ("BF00232".equals(confirmAuthPayVO.getCode())) {
                    result.setMsg(confirmAuthPayVO.getMsg() + ", 请更换绑卡");
                }
                return result;
            }
            logger.info("确认支付，成功，chlOrderNo={}", param.getChlOrderNo());
            // 支付成功后更新还款计划项下信息
            ConfirmPayResultVO rz = null;
            if (PayTypeEnum.SETTLEMENT.getId().equals(param.getPayType())) {
                rz = settlementService.settlement(rePayOP);
            } else if (PayTypeEnum.DELAY.getId().equals(param.getPayType())) {
                rz = settlementService.delay(rePayOP);
            } else if (PayTypeEnum.URGENT.getId().equals(param.getPayType())) {
                rz = settlementService.urgent(rePayOP);
            } else {
                rz = settlementService.trip(rePayOP);
            }
            if (null == rz || rz.getResult() == 0) {
                // if(StringUtils.isNotBlank(rz.getMessage())) {
                // result.setCode("FAIL");
                // result.setMsg(rz.getMessage());
                // } else {
                // result.set(ErrInfo.PAY_SETTLEMENT_FAIL);
                // }
                result.set(ErrInfo.PAY_SETTLEMENT_FAIL);
                logger.error("[{}], 还款结算结果:[{}]", rePayOP.getApplyId(), rz != null ? rz.getMessage() : "");
                return result;
            }
            result.setData(confirmAuthPayVO);
        } finally {
            // 移除锁
            JedisUtils.del(userConfirmPayLockCacheKey);
        }
        return result;
    }

    /**
     * 交易记录查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTransactionRecord", method = RequestMethod.POST, name = "交易记录查询")
    @ResponseBody
    public ApiResult getTransactionRecord(String mobile, HttpServletRequest request) throws Exception {
        // 初始化返回结果
        ApiResult result = new ApiResult();
        if (StringUtils.isBlank(mobile)) {
            result.setCode("FAIL");
            result.setMsg("手机号不能为空");
            return result;
        }
        // 查询结果
        List<TransactionRecordVO> list = bankDepositService.getTransactionRecordS(mobile);
        // TODO
        // if(null == list || list.size() == 0) {
        // result.set(ErrInfo.UNFIND_TX_RECORD);
        // return result;
        // }
        result.setData(list);
        return result;
    }

    /**
     * 四合一授权
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/termsAuth", method = RequestMethod.POST, name = "四合一授权")
    @ResponseBody
    public ApiResult termsAuth(@Valid TermsAuthOP param, Errors errors, HttpServletRequest request) throws Exception {

        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 用户id
        String userId = request.getHeader("userId");
        param.setUserId(userId);
        // 获取用户信息
        CustUserVO shiroUser = custUserService.getCustUserById(userId);
        if (null != shiroUser) {
            if (StringUtils.isBlank(shiroUser.getAccountId())) {
                logger.error("电子账户获取失败，确认是否完成开户！");
                return result.set(ErrInfo.ACCOUNTID_ERROR);
            }
            // 电子账户
            param.setAccountId(shiroUser.getAccountId());
            // 手机号
            param.setMobile(shiroUser.getMobile());
            // 获取缓存的短信序列号
            String smsSeq = CommonUtils.getBDSmsSeqFormCache(shiroUser.getMobile());
            if (null == smsSeq) {
                logger.error("四合一授权短信序列号为空！");
                return result.set(ErrInfo.TERMSAUTH_SMSCODE_TIMEOUT);
            } else {
                param.setSmsSeq(smsSeq);
            }
        }

        // 唯一订单号
        StringBuilder bf = new StringBuilder();
        bf.append(shiroUser.getMobile()).append(System.currentTimeMillis());
        param.setOrderId(bf.toString());

        // 查询结果
        TermsAuthVO termsAuthVO = bankDepositService.termsAuth(param);

        if (null == termsAuthVO) {
            logger.error("四合一授权失败！");
            return result.set(ErrInfo.TERMSAUTH_FAIL);
        } else {
            if (!StringUtils.equals(termsAuthVO.getCode(), Global.BANKDEPOSIT_SUCCSS_4)) {
                logger.error("四合一授权失败！[{}]", termsAuthVO.getMessage());
                result.setCode("FAIL");
                result.setMsg(termsAuthVO.getMessage());
                return result;
            }
        }
        result.setData(termsAuthVO);
        return result;
    }

    /**
     * 修改交易密码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST, name = "修改交易密码")
    @ResponseBody
    public ApiResult updatePayPwd(@Valid UpdatePwdOP param, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 原密码判断
        if (StringUtils.isBlank(param.getOldPwd())) {
            logger.error("原密码不能为空！");
            return result.set(ErrInfo.BAD_REQUEST);
        }
        // 调恒丰银行修改交易密码接口 TODO
        int updateRz = custUserService.updatePwd(param);
        result.setData(updateRz);
        return result;
    }

    /**
     * 重新绑卡
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reBindCard", method = RequestMethod.POST, name = "重新绑卡")
    @ResponseBody
    public ApiResult reBindCard(@Valid RebindCardInfoOP param, Errors errors, HttpServletRequest request)
            throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        String userId = request.getHeader("userId");
        // 初始化返回结果
        ApiResult result = new ApiResult();
        // 验证银行是否开通
        boolean isOpen = appBankLimitService.isOpen(param.getBankCode());
        if (!isOpen) {
            logger.error("银行卡暂不支持绑定:{},{}", userId, param.getBankCode());
            result.setCode("FAIL");
            result.setMsg("银行卡暂不支持绑定，请更换其他银行卡");
            return result;
        }
        // 截取ip
        String ip = Servlets.getIpAddress(request);
        // 宝付支付四要素验证（绑卡）流程
        // 宝付接口参数对象设置
        DirectBindCardOP bindCardOP = new DirectBindCardOP();
        // 银行卡号
        bindCardOP.setCardNo(param.getCardNo());
        // 银行编号
        bindCardOP.setBankCode(param.getBankCode());
        // 姓名
        bindCardOP.setRealName(param.getTrueName());
        // 身份证号
        bindCardOP.setIdNo(param.getIdNo());
        // 手机号
        bindCardOP.setMobile(param.getAccount());
        // ip
        bindCardOP.setIpAddr(ip);
        // 用户id
        bindCardOP.setUserId(userId);
        // 渠道
        bindCardOP.setSource(param.getSource());
        BindCardResultVO bindCardResult = baofooAuthPayService.reBindCard(bindCardOP);
        logger.info("宝付四要素验证响应:{},{}", param.getTrueName(), JsonMapper.toJsonString(bindCardResult));
        if (!bindCardResult.isSuccess()) {
            result.setCode("FAIL");
            result.setMsg(bindCardResult.getMsg());
            return result;
        }
        if (StringUtils.isBlank(bindCardResult.getBindId())) {
            result.set(ErrInfo.CARD4_VERIFY_FAIL);
            return result;
        }
        return result;
    }

    /**
     * 获取绑定银行卡
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBindBankCard", name = "获取绑定银行卡信息")
    @ResponseBody
    public ApiResult getBindBankCard(HttpServletRequest request) throws Exception {
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
        BindInfoVO vo = custUserService.getBindInfoById(userId);
        if (null == vo) {
            result.set(ErrInfo.NOTFIND_CUST_USER);
            return result;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cardNo", vo.getCardNo());
        map.put("bankName", vo.getBankName());
        map.put("bankLogo", "http://api.jubaoqiandai.com/img/bank/logo/" + vo.getBankCode() + ".png");
        map.put("bankBack", "http://api.jubaoqiandai.com/img/bank/back/" + vo.getBankCode() + ".png");
        result.setData(map);
        return result;
    }

    /**
     * 诚诚贷天威开户
     */
    @SuppressWarnings("unchecked")
    private void createCCDAccount(OpenAccountResultVO openAccount, OpenAccountOP userInfo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", openAccount.getAccountId());
        params.put("realName", userInfo.getRealName());
        params.put("idCard", userInfo.getIdCard());
        params.put("mobile", userInfo.getMobile());
        // rest模式调用接口
        logger.info("诚诚贷天威开户http请求：[{}]", JsonMapper.toJsonString(params));
        Map<String, String> result = (Map<String, String>) RestTemplateUtils.getInstance()
                .postForObject(Global.getConfig("ccd_twOpenAccount_url"), params, Map.class);
        logger.info("诚诚贷天威开户http响应：[{}],[{}]", userInfo.getMobile(), JsonMapper.toJsonString(result));
    }
}
