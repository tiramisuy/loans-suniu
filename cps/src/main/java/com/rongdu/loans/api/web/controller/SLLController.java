package com.rongdu.loans.api.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.security.SignUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.common.sll.SLLUtil;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.WithdrawalSourceEnum;
import com.rongdu.loans.loan.option.SLL.*;
import com.rongdu.loans.loan.option.jdq.JDQResp;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.service.SLLService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.sll.*;
import com.rongdu.loans.mq.MessageProductorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2018/10/29.
 */
@Slf4j
@Controller
@RequestMapping(value = "ssl")
public class SLLController {

    @Autowired
    private SLLService sllService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private MessageProductorService messageProductorService;
    @Autowired
    private RongPointCutService rongPointCutService;

    private static final long TIME = 15;// 时间段，单位秒
    private static final long COUNT = 30;// 允许访问的次数
    private static long firstTime = 0;
    private static long accessCount = 0;

    private static final long TIME_2 = 15;// 时间段，单位秒
    private static final long COUNT_2 = 45;// 允许访问的次数
    private static long firstTime_2 = 0;
    private static long accessCount_2 = 0;

    private static synchronized SLLResp accessLock() {// 并发控制
        if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
            if (accessCount < COUNT) {
                accessCount++;
            } else {
                SLLResp resp = new SLLResp();
                resp.setCode(SLLResp.FAILURE);
                resp.setMsg("系统繁忙，请稍后重试");
                return resp;
            }
        } else {
            firstTime = System.currentTimeMillis();
            accessCount = 1;
        }
        return null;
    }

    private static synchronized SLLResp accessLock_2() {// 并发控制
        if (System.currentTimeMillis() - firstTime_2 <= TIME_2 * 1000L) {
            if (accessCount_2 < COUNT_2) {
                accessCount_2++;
            } else {
                SLLResp resp = new SLLResp();
                resp.setCode(SLLResp.FAILURE);
                resp.setMsg("系统繁忙，请稍后重试");
                return resp;
            }
        } else {
            firstTime_2 = System.currentTimeMillis();
            accessCount_2 = 1;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "sslGateWay", method = RequestMethod.POST)
    public SLLResp sllGateWay(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        Map<String, Object> map = Maps.newHashMap();
        map.put("is_reloan", "0");
        sllResp.setCode("200");
        sllResp.setData(map);
        return sllResp;
    }

    @ResponseBody
    @RequestMapping(value = "baseData", method = RequestMethod.POST)
    public SLLResp baseData(@RequestBody String body) {
        // log.info("baseData================" + body);
        SLLResp sllResp = new SLLResp();
        try {
            SLLReq sllReq = JSONObject.parseObject(body, SLLReq.class);
            String biz_data = sllReq.getBizData();
            BaseData baseData = JSONObject.parseObject(biz_data, BaseData.class);

            String userPhone = null;
            userPhone = baseData.getOrderinfo().getUserMobile().trim();
            String mobile = userPhone;
            String idNo = baseData.getApplydetail().getUserId().trim().toUpperCase();
            String mobileAndIdNo = mobile + idNo;
            String md5 = MD5Util.string2MD5(mobileAndIdNo);
            JedisUtils.set("SLL:APPLY_LOCK_" + md5, "lock", Global.ONE_DAY_CACHESECONDS);
            if (userPhone == null || userPhone.length() > 11) {
                sllResp.setCode("102");
                sllResp.setMsg("数据缺失");
                return sllResp;
            }

            messageProductorService.sendBaseInfo(baseData);
            JedisUtils.set("ORDER:Locked_" + baseData.getOrderinfo().getUserMobile().trim(), "", 60 * 60 * 4);
            sllResp.setCode("200");
            return sllResp;
        } catch (Exception e) {
            log.error("==========奇虎360基础数据pushBaseInfo异常==========", e);
            sllResp.setCode("400");
            sllResp.setMsg("数据异常");
            return sllResp;
        }
    }

    @ResponseBody
    @RequestMapping(value = "addData", method = RequestMethod.POST)
    public SLLResp addData(@RequestBody String body) {
        // log.info("addData================" + body);
        SLLResp sllResp = new SLLResp();
        try {
            SLLReq sllReq = JSONObject.parseObject(body, SLLReq.class);
            String biz_data = sllReq.getBizData();
            AddData addData = JSONObject.parseObject(biz_data, AddData.class);
            messageProductorService.sendAdditionInfo(addData);
            sllResp.setCode("200");
            return sllResp;
        } catch (Exception e) {
            e.printStackTrace();
            sllResp.setCode("400");
            sllResp.setMsg("数据异常");
            return sllResp;
        }
    }

    /**
     * 查询复贷和黑名单信息
     */
    @ResponseBody
    @RequestMapping(value = "/orderQuickLoan", method = RequestMethod.POST)
    public SLLResp orderQuickLoan(@RequestBody String body) {
        SLLResp lockResp = accessLock_2();
        if (lockResp != null) {
            log.info("访问频率限制--->奇虎360--->查询复贷和黑名单信息");
            return lockResp;
        }
        SLLResp sllResp = new SLLResp();
        String idCard = "";
        String userMobile = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "orderQuickLoan");
            QuickLoanOP op = JSONObject.parseObject(bizData, QuickLoanOP.class);
            idCard = op.getIdCard();
            userMobile = op.getUserMobile();

            // 业务服务
            sllResp = sllService.orderQuickLoan(op);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询复贷接口】idCard={},userMobile={}", idCard, userMobile, e);
        }
        log.debug("【奇虎360-查询复贷接口】idCard={},userMobile={},响应结果={}", idCard, userMobile,
                JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 推送用户绑定银行卡
     */
    @ResponseBody
    @RequestMapping(value = "/bankBind", method = RequestMethod.POST)
    public SLLResp bankBind(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "bankBind");
            CardBindOP op = JSONObject.parseObject(bizData, CardBindOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            sllResp = sllService.cardBind(op);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-用户绑定银行卡接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-用户绑定银行卡接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 推送用户验证银行卡
     */
    @ResponseBody
    @RequestMapping(value = "/cardBindConfirm", method = RequestMethod.POST)
    public SLLResp cardBindConfirm(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "cardBindConfirm");
            CardBindOP op = JSONObject.parseObject(bizData, CardBindOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            sllResp = sllService.cardBindConfirm(op);
            rongPointCutService.sllCardBindConfirm(op, sllResp);// 用作奇虎360绑卡成功时，切面通知的切入点标记
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-用户验证银行卡接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-用户验证银行卡接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 查询审批结论
     */
    @ResponseBody
    @RequestMapping(value = "/conclusionPull", method = RequestMethod.POST)
    public SLLResp conclusionPull(@RequestBody String body) {
        SLLResp lockResp = accessLock();
        if (lockResp != null) {
            log.info("访问频率限制--->奇虎360--->查询审批结论");
            return lockResp;
        }
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "conclusionPull");
            JSONObject object = JSONObject.parseObject(bizData);
            orderNo = object.getString("order_no");

            // 业务服务
            ConclusionPullVO vo = sllService.conclusionPull(orderNo);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询审批结论接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-查询审批结论接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 试算接口
     */
    @ResponseBody
    @RequestMapping(value = "/orderTrial", method = RequestMethod.POST)
    public SLLResp orderTrial(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "orderTrial");
            OrderTrialOP op = JSONObject.parseObject(bizData, OrderTrialOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            OrderTrialVO vo = sllService.orderTrial(op);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-试算接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-试算接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 查询还款计划
     */
    @ResponseBody
    @RequestMapping(value = "/repaymentPlanPull", method = RequestMethod.POST)
    public SLLResp repaymentPlanPull(@RequestBody String body) {
        SLLResp lockResp = accessLock();
        if (lockResp != null) {
            log.info("访问频率限制--->奇虎360--->查询还款计划");
            return lockResp;
        }
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "repaymentPlanPull");
            JSONObject object = JSONObject.parseObject(bizData);
            orderNo = object.getString("order_no");

            // 业务服务
            RepaymentPlanPullVO vo = sllService.repaymentPlanPull(orderNo);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询还款计划接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-查询还款计划接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 查询还款详情
     */
    @ResponseBody
    @RequestMapping(value = "/repayplanDetail", method = RequestMethod.POST)
    public SLLResp repayplanDetail(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "repayplanDetail");
            RepayplanDetailOP op = JSONObject.parseObject(bizData, RepayplanDetailOP.class);
            orderNo = op.getOrderNO();

            // 业务服务
            List<OrderRepayplanDetailVO> vo = sllService.repayplanDetail(op);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询还款详情接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-查询还款详情接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 推送用户还款信息
     */
    @ResponseBody
    @RequestMapping(value = "/orderRepay", method = RequestMethod.POST)
    public SLLResp orderRepay(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "orderRepay");
            OrderRepayOP op = JSONObject.parseObject(bizData, OrderRepayOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            sllResp = sllService.orderRepay(op);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-用户还款接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-用户还款接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 查询订单状态
     */
    @ResponseBody
    @RequestMapping(value = "/orderStatusPull", method = RequestMethod.POST)
    public SLLResp orderStatusPull(@RequestBody String body) {
        SLLResp lockResp = accessLock();
        if (lockResp != null) {
            log.info("访问频率限制--->奇虎360--->查询订单状态");
            return lockResp;
        }
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "orderStatusPull");
            JSONObject object = JSONObject.parseObject(bizData);
            orderNo = object.getString("order_no");

            // 业务服务
            OrderStatusPullVO vo = sllService.orderStatusPull(orderNo);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询订单状态接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-查询订单状态接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 推送用户确认收款信息-跳开户再放款
     */
    @ResponseBody
    @RequestMapping(value = "/conclusionConfirm", method = RequestMethod.POST)
    public SLLResp conclusionConfirm(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "conclusionConfirm");
            ConclusionConfirmOP op = JSONObject.parseObject(bizData, ConclusionConfirmOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            String applyId = sllService.getApplyId(orderNo);
            if (StringUtils.isBlank(applyId)) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("数据异常，用户工单尚未生成！");
                log.error("【奇虎360-确认收款接口】异常-用户工单尚未生成！orderNo={}", orderNo);
                return sllResp;
            }
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            if (custUserVO == null) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("数据异常，用户尚未注册！");
                log.error("【奇虎360-确认收款接口】异常-用户尚未注册orderNo={}", orderNo);
                return sllResp;
            }
            String servFee = String.valueOf(applyAllotVO.getServFee());
            String approveAmt = String.valueOf(applyAllotVO.getApproveAmt());
            String approveTerm = String.valueOf(applyAllotVO.getApproveTerm());
            // 模拟用户登陆
            Map<String, String> map = LoginUtils.loginMock(custUserVO);
            String returnUrl = op.getConfirmReturnUrl();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(map);
            params.add("servFee", servFee);
            params.add("approveAmt", approveAmt);
            params.add("approveTerm", approveTerm);
            params.add("returnUrl", returnUrl);
            String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/#")
                    .path("/loanInfo").queryParams(params).build().toString();
            /*String url =
                    UriComponentsBuilder.newInstance().scheme("http").host(
                            "47.100.113.196").path("/#")
                            .path("/loanInfo").queryParams(params).build().toString();*/
            ConclusionConfirmVO vo = new ConclusionConfirmVO();
            vo.setConfirmUrl(url);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-确认收款接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-确认收款接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    /**
     * 推送用户确认收款信息
     */
    /*
     * @ResponseBody
	 * 
	 * @RequestMapping(value = "/conclusionConfirm", method =
	 * RequestMethod.POST) public SLLResp conclusionConfirm(@RequestBody String
	 * body) { SLLResp sllResp = new SLLResp(); String orderNo = ""; try { //
	 * 业务数据解密 String bizData = SLLUtil.getBizData(body,"conclusionConfirm");
	 * ConclusionConfirmOP op = JSONObject.parseObject(bizData,
	 * ConclusionConfirmOP.class); orderNo = op.getOrderNo();
	 * 
	 * // 业务服务 sllResp = sllService.conclusionConfirm(op); } catch (Exception e)
	 * { sllResp.setCode(SLLResp.FAILURE); sllResp.setMsg("系统异常");
	 * log.error("【奇虎360-推送用户确认收款信息】异常orderNo={}", orderNo, e); }
	 * log.debug("【奇虎360-推送用户确认收款信息】orderNo={},响应结果={}", orderNo,
	 * JSONObject.toJSONString(sllResp)); return sllResp; }
	 */

    /**
     * 查询借款合同
     */
    @ResponseBody
    @RequestMapping(value = "/contractGet", method = RequestMethod.POST)
    public SLLResp contractGet(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        String orderNo = "";
        ContractGetVO vo = new ContractGetVO();
        try {
            // 业务数据解密
            String bizData = SLLUtil.getBizData(body, "contractGet");
            ContractGetOP op = JSONObject.parseObject(bizData, ContractGetOP.class);
            orderNo = op.getOrderNo();

            // 业务服务
            String applyId = sllService.getApplyId(orderNo);
            if (StringUtils.isBlank(applyId)) {
                sllResp.setCode(SLLResp.FAILURE);
                sllResp.setMsg("数据异常，用户工单尚未生成！");
                log.error("【奇虎360-查询借款合同接口】异常-用户工单尚未生成！orderNo={}", orderNo);
                return sllResp;
            }
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            if (custUserVO == null) {
                sllResp.setCode(JDQResp.FAILURE);
                sllResp.setMsg("数据异常，用户尚未注册！");
                log.error("【奇虎360-查询借款合同接口】异常-用户尚未注册orderNo={}", orderNo);
                return sllResp;
            }
            // 模拟用户登陆
            Map<String, String> map = LoginUtils.loginMock(custUserVO);
            String returnUrl = op.getContractReturnUrl();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(map);
            params.add("returnUrl", returnUrl);
            String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/#")
                    .path("/agreement14").queryParams(params).build().toString();
            /*String url =
                    UriComponentsBuilder.newInstance().scheme("http").host(
                            "47.100.113.196").path("/#")
                            .path("/agreement14").queryParams(params).build().toString();*/
            vo.setContractUrl(url);
            sllResp.setCode(SLLResp.SUCCESS);
            sllResp.setMsg("SUCCESS");
            sllResp.setData(vo);
        } catch (Exception e) {
            sllResp.setCode(SLLResp.FAILURE);
            sllResp.setMsg("系统异常");
            log.error("【奇虎360-查询借款合同接口】异常orderNo={}", orderNo, e);
        }
        log.debug("【奇虎360-查询借款合同接口】orderNo={},响应结果={}", orderNo, JSONObject.toJSONString(sllResp));
        return sllResp;
    }

    @RequestMapping(value = "/resetImage")
    @ResponseBody
    public String resetImage(String orderNo, String key) throws Exception {
        if (!"ssl".equals(key)) {
            return "错误";
        }
        String result = sllService.resetImage(orderNo);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "createAccount", method = RequestMethod.POST)
    public Map<String, Object> createAccount(@RequestBody String body) {
        // log.info("baseData================" + body);
        SLLResp sllResp = new SLLResp();
        try {
            SLLReq sllReq = JSONObject.parseObject(body, SLLReq.class);
            String bizData = sllReq.getBizData();
            CreateAccountOP op = JSONObject.parseObject(bizData, CreateAccountOP.class);
            String order = op.getOrderNo();
            String url = op.getReturnUrl();
            String applyId = sllService.getApplyId(order);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            String result = null;
            if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().equals(Integer.parseInt(applyAllotVO.getPayChannel()))) {
                CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
                Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
                Map<String, String> params = new HashMap<>();
                params.put("userId", headerMap.get("userId"));
                params.put("cityId", "1");
                params.put("email", "1");
                params.put("source", "4");// 来源api

                // api接口签名
                Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
                        new TypeReference<Map<String, Object>>() {
                        });
                String openReturnUrl = URLEncoder.encode("http://api.jubaoqiandai.com/#/successPage", "UTF-8");
                signMap.put("appKey", headerMap.remove("appKey"));
                signMap.put("orderNo", order);
                signMap.put("openReturnUrl", openReturnUrl);
                headerMap.put("sign", SignUtils.createSignNew(signMap, true));

                MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                queryParams.add("orderNo", order);
                queryParams.add("openReturnUrl", openReturnUrl);
                String requestUrl = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
                        .path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();

            //String requestUrl =
            //        UriComponentsBuilder.newInstance().scheme("http").host("47.100.113.196").port(8080).path("/api").path(
            //                "/api").path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();
                result = sllService.createAccount(requestUrl, params, headerMap,applyAllotVO.getPayChannel());
                log.info("SLL口袋开户地址2:" + result);
            }
            if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().equals(Integer.parseInt(applyAllotVO.getPayChannel()))) {
                //result = "http://47.100.113.196:8080/cps/ssl/openUrl?orderNo=" + order + "&returnUrl=" + url;
                result = "http://cps.jubaoqiandai.com/ssl/openUrl?orderNo=" + order + "&returnUrl=" + url;
                log.info("SLL汉金所开户地址1:" + result);
            }
            Map<String, Object> map = Maps.newHashMap();
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("open_url", result);
            map.put("data", map1);
            map.put("code", 200);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/openUrl")
    public void openUrl(HttpServletResponse response, String orderNo, String returnUrl) {
        try {
            String applyId = sllService.getApplyId(orderNo);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
            Map<String, String> params = new HashMap<>();
            params.put("userId", headerMap.get("userId"));
            params.put("cityId", "1");
            params.put("email", "1");
            params.put("source", "4");// 来源api

            // api接口签名
            Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
                    new TypeReference<Map<String, Object>>() {
                    });
            String openReturnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            signMap.put("appKey", headerMap.remove("appKey"));
            signMap.put("orderNo", orderNo);
            signMap.put("openReturnUrl", openReturnUrl);
            headerMap.put("sign", SignUtils.createSignNew(signMap, true));

            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.add("orderNo", orderNo);
            queryParams.add("openReturnUrl", openReturnUrl);
            String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
                    .path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();

            //String url =
            //        UriComponentsBuilder.newInstance().scheme("http").host("47.100.113.196").port(8080).path("/api").path(
            //                "/api").path("/loan").path("/creatJxbAccount").queryParams(queryParams).build().toString();
            log.info("SLL汉金所开户地址2:" + url);
            String result = sllService.createAccount(url, params, headerMap,applyAllotVO.getPayChannel());
            response.setContentType("text/html;");
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ResponseBody
    @RequestMapping(value = "confirmWithdraw", method = RequestMethod.POST)
    public Map<String, Object> confirmWithdraw(@RequestBody String body) {
        SLLResp sllResp = new SLLResp();
        try {
            SLLReq sllReq = JSONObject.parseObject(body, SLLReq.class);
            String bizData = sllReq.getBizData();
            CreateAccountOP op = JSONObject.parseObject(bizData, CreateAccountOP.class);
            String order = op.getOrderNo();
            String url = op.getReturnUrl();
            String result = null;
            String applyId = sllService.getApplyId(order);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            if (WithdrawalSourceEnum.WITHDRAWAL_KOUDAI_CG.getValue().equals(Integer.parseInt(applyAllotVO.getPayChannel()))) {
                CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
                Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
                Map<String, String> params = new HashMap<>();
                params.put("applyId", applyId);

                // api接口签名
                Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
                        new TypeReference<Map<String, Object>>() {
                        });
                String openReturnUrl = URLEncoder.encode(url, "UTF-8");
                signMap.put("appKey", headerMap.remove("appKey"));
                signMap.put("orderNo", order);
                signMap.put("openReturnUrl", openReturnUrl);
                headerMap.put("sign", SignUtils.createSignNew(signMap, true));

                MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                queryParams.add("orderNo", order);
                queryParams.add("openReturnUrl", openReturnUrl);

                String returnUrl = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
                        .path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();

            //String returnUrl =
            //        UriComponentsBuilder.newInstance().scheme("http").host("47.100.113.196").port(8080).path("/api").path(
            //                "/api").path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();
                result = sllService.withdraw(returnUrl, params, headerMap,applyAllotVO.getPayChannel());

            }
            if (WithdrawalSourceEnum.WITHDRAWAL_HJS.getValue().equals(Integer.parseInt(applyAllotVO.getPayChannel()))) {
                //result = "http://47.100.113.196:8080/cps/ssl/withdraw?orderNo=" + order + "&returnUrl=" + url;
                result = "http://cps.jubaoqiandai.com/ssl/withdraw?orderNo=" + order + "&returnUrl=" + url;
                log.info("SLL汉金所提现地址1:" + result);
            }
            Map<String, Object> map = Maps.newHashMap();
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("withdraw_url", result);
            map.put("data", map1);
            map.put("code", 200);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/withdraw")
    public void withdraw(HttpServletResponse response, String orderNo, String returnUrl) {
        try {
            String applyId = sllService.getApplyId(orderNo);
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            Map<String, String> headerMap = LoginUtils.loginMock(custUserVO);
            Map<String, String> params = new HashMap<>();
            params.put("applyId", applyId);

            // api接口签名
            Map<String, Object> signMap = JSONObject.parseObject(JSONObject.toJSONString(params),
                    new TypeReference<Map<String, Object>>() {
                    });
            String openReturnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            signMap.put("appKey", headerMap.remove("appKey"));
            signMap.put("orderNo", orderNo);
            signMap.put("openReturnUrl", openReturnUrl);
            headerMap.put("sign", SignUtils.createSignNew(signMap, true));

            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.add("orderNo", orderNo);
            queryParams.add("openReturnUrl", openReturnUrl);

            String url = UriComponentsBuilder.newInstance().scheme("https").host("api.jubaoqiandai.com").path("/api")
                    .path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();

            //String url =
            //        UriComponentsBuilder.newInstance().scheme("http").host("47.100.113.196").port(8080).path("/api").path(
            //                "/api").path("/loan").path("/enSureKdWithdraw").queryParams(queryParams).build().toString();
            log.info("SLL汉金所提现地址2:" + url);

            String result = sllService.withdraw(url, params, headerMap,applyAllotVO.getPayChannel());
            response.setContentType("text/html;");
            PrintWriter out = response.getWriter();
            out.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
