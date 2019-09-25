package com.rongdu.loans.api.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.common.config.Global;
import com.rongdu.common.mq.QueueConfig;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.jdq.JdqUtil;
import com.rongdu.loans.api.common.AuthenticationType;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.option.jdq.*;
import com.rongdu.loans.loan.service.JDQService;
import com.rongdu.loans.loan.service.JDQStatusFeedBackService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.jdq.*;
import com.rongdu.loans.pay.exception.OrderProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2018/10/11.
 */
@Slf4j
@Controller
@RequestMapping(value = "jdq")
public class JDQController {

    Logger logger = LoggerFactory.getLogger(JDQController.class);

    @Autowired
    private JDQService jdqService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private JDQStatusFeedBackService jdqStatusFeedBackService;
    @Autowired
    private LoanApplyService loanApplyService;

    private static final long TIME = 15;// 时间段，单位秒
    private static final long COUNT = 5;// 允许访问的次数
    private static long firstTime = 0;
    private static long accessCount = 0;

    private static synchronized JDQResp accessLock() {// 并发控制
        if (System.currentTimeMillis() - firstTime <= TIME * 1000L) {
            if (accessCount < COUNT) {
                accessCount++;
            } else {
                JDQResp resp = new JDQResp();
                resp.setCode(JDQResp.FAILURE);
                resp.setMsg("系统繁忙，请稍后重试");
                return resp;
            }
        } else {
            firstTime = System.currentTimeMillis();
            accessCount = 1;
        }
        return null;
    }

    /**
     * @Title: checkUser
     * @Description: 借点钱-用户检测接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "checkUser", method = RequestMethod.POST)
    public JDQResp checkUser(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        log.debug("check-user入参：{}", jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }

        CheckUserOP checkUserOP = JSONObject.parseObject(partnerDecode, CheckUserOP.class);
        //log.debug("check-user入参：{}", checkUserOP);
        jdqResp = new JDQResp();
        try {
            CheckUserVO checkUserVO = jdqService.checkUser(checkUserOP);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(checkUserVO);
        } catch (Exception e) {
            log.error("【借点钱】用户检测接口异常phone={},idNumber={}", checkUserOP.getPhone(), checkUserOP.getIdNumber(), e);
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
        }
        log.debug("【借点钱】用户检测接口phone={},idNumber={},响应结果={}", checkUserOP.getPhone(), checkUserOP.getIdNumber(),
                JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: intoOrder
     * @Description: 借点钱-进件接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "pushPhaseOne", method = RequestMethod.POST)
    public JDQResp intoOrder(@RequestBody JSONObject jsonObject) {
        //log.debug("pushPhaseOne.into-order入参：{}",jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }
        String channelCode = JdqUtil.ChannelParse.getCode(jsonObject.getString("channel_code"));

        jdqResp = jdqService.intoOrder(partnerDecode, QueueConfig.PUSH_JDQ.getType(),channelCode);
        return jdqResp;
    }

    /**
     * @Title: intoOrder
     * @Description: 借点钱-推送补充信息接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "pushPhaseTwo", method = RequestMethod.POST)
    public JDQResp pushPhaseTwo(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        //log.debug("pushPhaseTwo.into-order入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }
        String channelCode = JdqUtil.ChannelParse.getCode(jsonObject.getString("channel_code"));

        jdqResp = jdqService.intoOrder(partnerDecode, QueueConfig.PUSH_JDQ_ADDITION.getType(),channelCode);
        return jdqResp;
    }

    /**
     * @Title: calculate
     * @Description: 借点钱-提现试算接口
     * @return JDQResp 返回类型 s
     */
    @ResponseBody
    @RequestMapping(value = "/withdrawTryCalculate", method = RequestMethod.POST)
    public JDQResp calculate(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);

        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("withdrawTryCalculate入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }

        CalculateOP calculateOP = JSONObject.parseObject(partnerDecode, CalculateOP.class);
        //log.debug("calculate入参：{}", calculateOP);
        jdqResp = new JDQResp();
        JDQCalculateInfoVO jdqCalculateInfo = null;
        try {
            jdqCalculateInfo = jdqService.calculate(calculateOP);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(jdqCalculateInfo);
        } catch (Exception e) {
            log.error("【借点钱】提现试算接口异常jdqOrderId={}", calculateOP.getJdqOrderId(), e);
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
        }
        log.debug("【借点钱】提现试算接口jdqOrderId={},响应结果={}", calculateOP.getJdqOrderId(), JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: cardInfo
     * @Description: 借点钱-查询绑卡信息接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/cardList", method = RequestMethod.POST)
    public JDQResp cardInfo(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("cardList入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }


        CardInfoOP cardInfoOP = JSONObject.parseObject(partnerDecode, CardInfoOP.class);
        //log.debug("cardInfo入参：{}", cardInfoOP);
        jdqResp = new JDQResp();
        try {
            CardInfoVO cardInfo = jdqService.cardInfo(cardInfoOP);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(cardInfo);
            if (cardInfo == null) {
                jdqResp.setMsg("未查到用户的相关信息");
            }
        } catch (Exception e) {
            log.error("【借点钱】查询绑卡信息接口异常idNumber={},phone={}", cardInfoOP.getIdNumber(), cardInfoOP.getPhone(), e);
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
        }
        log.debug("【借点钱】查询绑卡信息接口idNumber={},phone={},响应结果={}", cardInfoOP.getIdNumber(), cardInfoOP.getPhone(),
                JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: bindCardUrl
     * @Description: 借点钱- 跳转页面绑卡接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/bindCardExt", method = RequestMethod.POST)
    public JDQResp bindCardUrl(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("bindCardExt-入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }


        BindCardOP bindCardOP = JSONObject.parseObject(partnerDecode, BindCardOP.class);
        //log.debug("bindCardUrl入参：{}", bindCardOP);
        BindCardVO bindCardVO = new BindCardVO();
        jdqResp = new JDQResp();
        try {
            String phone = bindCardOP.getPhone();
            CustUserVO cust = custUserService.getCustUserByMobile(phone);
            if (cust == null) {
                jdqResp.setCode(JDQResp.FAILURE);
                jdqResp.setMsg("数据异常，用户尚未注册!");
                log.error("【借点钱】跳转页面绑卡接口异常-用户尚未注册！phone={},idNumber={}", bindCardOP.getPhone(),
                        bindCardOP.getIdNumber());
                return jdqResp;
            }
            // 模拟用户登陆
            Map<String, String> map = loginMock(cust);

            String returnUrl = bindCardOP.getSuccessReturnUrl();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(map);
            params.add("flag", "1");// 绑卡
            if (StringUtils.isNotBlank(cust.getCardNo())) {
                params.set("flag", "2");// 换卡
            }
            params.add("returnUrl", returnUrl);
            String h5 = Global.getConfig("h5.server.url");
            log.info("h5:{}", h5);
            String bindCardUrl = UriComponentsBuilder.newInstance().scheme("http").host(h5)
                    .path("/#").path("/jdqRepayInfo").queryParams(params).build().toString();

            JSONObject data = new JSONObject();
            //bindType绑卡类型：1-绑定新卡，2-选定旧卡
            int bindStatus = "2".equals(bindCardOP.getBindType()) ? 1 : 2;//绑卡状态： 1-老卡确认成功，无需再重新跳转合作机构绑卡页面。 2-跳转合作机构绑卡页面进行绑卡。
            data.put("bind_status", bindStatus);
            data.put("url", bindCardUrl);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(data);
        } catch (Exception e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
            log.error("【借点钱】跳转页面绑卡接口异常phone={},idNumber={}", bindCardOP.getPhone(), bindCardOP.getIdNumber(), e);
        }
        log.debug("【借点钱】跳转页面绑卡接口phone={},idNumber={},响应结果={}", bindCardOP.getPhone(), bindCardOP.getIdNumber(),
                JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: loanContract
     * @Description: 借点钱-合同接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/loanContractExt", method = RequestMethod.POST)
    public JDQResp loanContract(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("loanContractExt-入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }



        LoanContractOP loanContractOP = JSONObject.parseObject(partnerDecode, LoanContractOP.class);
        //log.debug("loan_contract入参：{}", loanContractOP);
        jdqResp = new JDQResp();
        try {
            String applyId = jdqService.getApplyId(loanContractOP.getJdqOrderId());
            if (StringUtils.isBlank(applyId)) {
                jdqResp.setCode(JDQResp.FAILURE);
                jdqResp.setMsg("数据异常，用户工单尚未生成！");
                log.error("【借点钱】合同接口异常-用户工单尚未生成！jdqOrderId={}", loanContractOP.getJdqOrderId());
                return jdqResp;
            }
            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(applyId);
            CustUserVO custUserVO = custUserService.getCustUserByMobile(applyAllotVO.getMobile());
            if (custUserVO == null) {
                jdqResp.setCode(JDQResp.FAILURE);
                jdqResp.setMsg("数据异常，用户尚未注册！");
                log.error("【借点钱】合同接口异常-用户尚未注册jdqOrderId={}", loanContractOP.getJdqOrderId());
                return jdqResp;
            }
            // 模拟用户登陆
            Map<String, String> map = loginMock(custUserVO);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(map);
            String h5 = Global.getConfig("h5.server.url");
            log.info("h5:{}", h5);
            String url = UriComponentsBuilder.newInstance().scheme("http").host(h5).path("/#")
                    .path("/agreement3").queryParams(params).build().toString();
            /*
             * String url =
             * UriComponentsBuilder.newInstance().scheme("http").host
             * ("47.100.113.196").path("/#")
             * .path("/agreement3").queryParams(params).build().toString();
             */
            LoanContractVO vo = new LoanContractVO();
            vo.setContractName("聚财协议");
            vo.setUrl(url);
            List<LoanContractVO> list = new ArrayList<>();
            list.add(vo);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(list);
        } catch (Exception e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常！");
            log.error("【借点钱】合同接口异常jdqOrderId={}", loanContractOP.getJdqOrderId(), e);
        }
        log.debug("【借点钱】合同接口jdqOrderId={},响应结果={}", loanContractOP.getJdqOrderId(), JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: withdraw
     * @Description: 借点钱-确认提现接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/confirmLoan", method = RequestMethod.POST)
    public JDQResp withdraw(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("confirmLoan-入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }


        WithdrawOP withdrawOP = JSONObject.parseObject(partnerDecode, WithdrawOP.class);
        //log.debug("withdraw入参：{}", withdrawOP);
        jdqResp = new JDQResp();
        try {
            jdqService.withdraw(withdrawOP);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
        } catch (OrderProcessingException e) {
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg(e.getMsg());
            log.error("【借点钱】确认提现接口异常jdqOrderId={}", withdrawOP.getJdqOrderId(), e);
        } catch (RuntimeException e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg(e.getMessage());
            log.error("【借点钱】确认提现接口异常jdqOrderId={}", withdrawOP.getJdqOrderId(), e);
        } catch (Exception e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常");
            log.error("【借点钱】确认提现接口异常jdqOrderId={}", withdrawOP.getJdqOrderId(), e);
        }
        log.debug("【借点钱】确认提现接口jdqOrderId={},响应结果={}", withdrawOP.getJdqOrderId(), JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: repayment
     * @Description: 借点钱-主动还款接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/repayment", method = RequestMethod.POST)
        public JDQResp repayment(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("repayment-入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }


        RepaymentOP repaymentOP = JSONObject.parseObject(partnerDecode, RepaymentOP.class);
        //log.debug("repayment入参：{}", repaymentOP);
        jdqResp = jdqService.repayment(repaymentOP);
        log.debug("【借点钱】主动还款接口jdqOrderId={},响应结果={}", repaymentOP.getJdqOrderId(), JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    /**
     * @Title: orderInfo
     * @Description: 借点钱-订单状态查询接口
     * @return JDQResp 返回类型
     */
    @ResponseBody
    @RequestMapping(value = "/pullOrderStatus", method = RequestMethod.POST)
    public JDQResp orderInfo(@RequestBody JSONObject jsonObject) {
        //String partnerDecode = JdqUtil.partnerDecode(jsonObject);
        String partnerDecode = null;
        JDQResp jdqResp = null;
        log.debug("pullOrderStatus-入参：{}",jsonObject);

        try {
            partnerDecode = JdqUtil.partnerDecode(jsonObject);
        } catch (Exception e) {
            logger.error( "数据解密异常", e);
            jdqResp = new JDQResp();
            jdqResp.setCode("-1");
            jdqResp.setMsg(e.getMessage());
            return jdqResp;
        }


        JSONObject object = JSONObject.parseObject(partnerDecode);
        String orderId = object.getString("jdq_order_id");
        //log.debug("order-info入参：{}", orderId);

        JDQResp lockResp = accessLock();
        if (lockResp != null) {
            log.info("访问频率限制--->借点钱--->订单状态查询--->{}", orderId);
            return lockResp;
        }

        jdqResp = new JDQResp();
        try {
            JDQOrderStatusFeedBackVO vo = jdqStatusFeedBackService.pullOrderStatus(orderId);
            jdqResp.setCode(JDQResp.SUCCESS);
            jdqResp.setMsg("SUCCESS");
            jdqResp.setData(vo);
        } catch (Exception e) {
            jdqResp.setCode(JDQResp.FAILURE);
            jdqResp.setMsg("系统异常");
            log.error("【借点钱】订单状态查询接口异常jdqOrderId={}", orderId, e);
        }
        log.debug("【借点钱】订单状态查询接口jdqOrderId={},响应结果={}", orderId, JSONObject.toJSONString(jdqResp));
        return jdqResp;
    }

    private Map<String, String> loginMock(CustUserVO custUserVO) {
        Map<String, String> map = new HashMap<>();
        UsernamePasswordToken token = new UsernamePasswordToken(custUserVO.getMobile(), custUserVO.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        SecurityUtils.getSubject().getSession().setTimeout(3600000L);
        AuthenticationType authenticationType = AuthenticationType.LOGIN;
        LoginUtils.authenticationType.set(authenticationType);
        subject.login(token);
        CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
        String userId = shiroUser.getId();
        String tokenId = LoginUtils.generateTokenId(userId);
        String appKey = LoginUtils.generateAppKey(userId);
        LoginUtils.cleanCustUserInfoCache(userId);
        JedisUtils.delObject(Global.USER_AUTH_PREFIX + userId);

        map.put("userId", userId);
        map.put("appKey", appKey);
        map.put("tokenId", tokenId);
        return map;
    }
}
