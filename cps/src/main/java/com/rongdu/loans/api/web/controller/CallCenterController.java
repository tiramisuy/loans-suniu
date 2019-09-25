package com.rongdu.loans.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.web.option.CallCenterCustOP;
import com.rongdu.loans.api.web.option.CallCenterOP;
import com.rongdu.loans.api.web.option.CheckOP;
import com.rongdu.loans.api.web.vo.RemoveDuplicateQueryVO;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.cust.option.QueryUserOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.QueryUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.LoanPurposeEnum;
import com.rongdu.loans.enums.SourceEnum;
import com.rongdu.loans.loan.option.ContactHistoryOP;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calincntlistv;
import com.rongdu.loans.loan.option.jdq.report.Calloutcntlistv;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.rongTJreportv1.RongContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.LoanRepayPlanVO;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.service.ApplyTripartiteRong360Service;
import com.rongdu.loans.loan.service.ApplyTripartiteService;
import com.rongdu.loans.loan.service.ContactHistoryService;
import com.rongdu.loans.loan.service.DWDService;
import com.rongdu.loans.loan.service.JDQService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.PromotionCaseService;
import com.rongdu.loans.loan.service.RefuseReasonService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.SLLService;
import com.rongdu.loans.loan.service.XianJinBaiKaService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.loan.vo.CallConnectVO;
import com.rongdu.loans.loan.vo.ContactHistoryVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.sys.service.UserService;
import com.rongdu.loans.sys.vo.UserVO;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * @Description: 呼叫中心
 * @author: RaoWenbiao
 * @date 2019年1月16日
 */
@Controller
@RequestMapping(value = "call/center")
public class CallCenterController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ContactHistoryService contactHistoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RefuseReasonService refuseReasonService;

    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;

    @Autowired
    private ApplyTripartiteService applyTripartiteService;
    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;

    @Autowired
    private JDQService jdqService;
    @Autowired
    private DWDService dwdService;
    @Autowired
    private XianJinBaiKaService xianJinBaiKaService;
    @Autowired
    private PromotionCaseService promotionCaseService;

    /**
     * 定义已过审页面订单状态下拉框枚举
     */
    @Autowired
    private SLLService sllService;

    private static final String errorPage = "/WEB-INF/views/error/errorPage.jsp";

    /**
     * 用户信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfo")
    public String userInfo(CallCenterOP op, HttpServletRequest request) throws Exception {

        // String retPage2 = "/WEB-INF/views/modules/callCenter/userInfo2.jsp";
        CallCenterCustOP callCenterCustOP = (CallCenterCustOP) JsonMapper.fromJsonString(op.getCust(),
                CallCenterCustOP.class);

        logger.info("呼叫系统请求参数：" + JsonMapper.toJsonString(op));

        if (op == null || StringUtils.isBlank(op.getCalled()) || StringUtils.isBlank(op.getOperator_id())
                || callCenterCustOP == null || StringUtils.isBlank(callCenterCustOP.getUuid1())) {
            request.setAttribute("msg", "参数异常，请联系管理员");
            return errorPage;

        }

        if ("1".equals(callCenterCustOP.getUuid1())) {//预提醒
        	userInfoYtx(op, request, callCenterCustOP.getUuid2());
            return "/WEB-INF/views/modules/callCenter/userInfo_ytx.jsp";
        }else if ("2".equals(callCenterCustOP.getUuid1())) {
            userInfo2(op, request, callCenterCustOP.getUuid2());

            if (request.getAttribute("msg") != null) {
                return errorPage;
            }

            return "/WEB-INF/views/modules/callCenter/userInfo2.jsp";
        } else {
            userInfo1(op, request, callCenterCustOP.getUuid2());
            return "/WEB-INF/views/modules/callCenter/userInfo.jsp";
        }

    }
    //预提醒
    private void userInfoYtx(CallCenterOP op, HttpServletRequest request, String uuid2) {
        UserInfoVO userInfo = custUserService.getUserInfoByMobile(op.getCalled());

        if (userInfo != null) {
            request.setAttribute("custUser", userInfo);

            ApplyAllotVO applyAllotVO = loanApplyService.getApplyById(uuid2);
    		if (applyAllotVO != null) {
    			applyAllotVO.setPurpose(LoanPurposeEnum.getDesc(applyAllotVO.getPurpose()));
    			applyAllotVO.setStatusStr(ApplyStatusLifeCycleEnum.getDesc(applyAllotVO.getStatus()));

    			applyAllotVO.setChannelId(getChannelNameById(applyAllotVO.getChannelId()));
    			applyAllotVO.setSource(SourceEnum.getDesc(applyAllotVO.getSource()));
    			
    			request.setAttribute("apply", applyAllotVO);
    		}
    		
    		RepayDetailListOP repayDetailListOP = new RepayDetailListOP();
    		repayDetailListOP.setUserId(userInfo.getId());
    		repayDetailListOP.setApplyId(uuid2);
    		
    		List<RepayDetailListVO> repayPlanItemList = repayPlanItemService.repayDetailExportList(repayDetailListOP);
    		
    		
            request.setAttribute("repayPlanItemList", repayPlanItemList);
            
            
            
        }
        request.setAttribute("applyId", uuid2);
        
        

    }
    
    
    private String getChannelNameById(String cId){
    	String cache = "CPS_CACHE_LIST_BY_CHANNEl_MAP";
		Map<String,Map<String, String>> channelMap = (Map<String,Map<String, String>>) JedisUtils.getObject(cache);
		if (channelMap == null) {
			channelMap = new HashMap();
			List<Map<String, String>> channelList = promotionCaseService.findAllChannel();
			
			if(CollectionUtils.isNotEmpty(channelList)){
    			for (Map<String, String> channel :channelList) {
					channelMap.put(channel.get("cid"),channel);						
				}
    		}			
			JedisUtils.setObject(cache, channelMap, 60 * 60 * 24);
		}
		
		return channelMap.get(cId) == null?"":channelMap.get(cId).get("cName");
    }
    
    private void userInfo1(CallCenterOP op, HttpServletRequest request, String uuid2) {
        UserInfoVO userInfo = custUserService.getUserInfoByMobile(op.getCalled());

        if (userInfo != null) {
            request.setAttribute("custUser", userInfo);

            LoanRepayPlanVO loanRepayPlan = loanRepayPlanService.getByApplyId(uuid2);
            request.setAttribute("loanRepayPlan", loanRepayPlan);

            request.setAttribute("applyList", loanApplyService.getLoanApplyByUserId(userInfo.getId()));
        }
        request.setAttribute("applyId", uuid2);
        // ApplyAllotVO applyAllotVO =
        // loanApplyService.getApplyById(callCenterCustOP.getUuid2());
        // if (applyAllotVO != null) {
        // applyAllotVO.setPurpose(LoanPurposeEnum.getDesc(applyAllotVO.getPurpose()));
        // applyAllotVO.setStatusStr(ApplyStatusLifeCycleEnum.getDesc(applyAllotVO.getStatus()));
        //
        // request.setAttribute("apply", applyAllotVO);
        // }

    }

    // private String userInfo2(CallCenterOP op, HttpServletRequest request,
    // String uuid2) {
    // UserInfoVO userInfo =
    // custUserService.getUserInfoByMobile(op.getCalled());
    // String redirectUrl = null;
    // String url = null;
    //
    // if (userInfo == null) {
    // request.setAttribute("msg", "用户不存在，手机号："+op.getCalled()+"，请联系管理员");
    // return errorPage;
    // }
    //
    // if (StringUtils.isNotBlank(uuid2)) {
    // request.setAttribute("applyId", uuid2);
    // UserVO user = userService.getUserByCallId(op.getOperator_id());
    //
    // if (user == null) {
    // request.setAttribute("msg", "坐席工号异常，请联系管理员");
    // return errorPage;
    // }
    //
    // redirectUrl = "/a/loan/apply/checkFrom?id=" + userInfo.getId() +
    // "&sign=check&sourse=callCenter&applyId=" + uuid2;
    // redirectUrl = URLEncoder.encode(redirectUrl);
    // url = Global.getConfig("admin.server.url") + "/sso/login?username=" +
    // user.getLoginName() + "&redirectUrl="
    // + redirectUrl;
    // }
    //
    // return url;
    //
    // }

    private void userInfo2(CallCenterOP op, HttpServletRequest request, String uuid2) {
        UserInfoVO userInfo = custUserService.getUserInfoByMobile(op.getCalled());

        if (userInfo == null || userInfo.getId() == null) {
            request.setAttribute("msg", "坐席工号异常，请联系管理员");
            return;
        }

        if (StringUtils.isNotBlank(op.getCust())) {
            CallCenterCustOP callCenterCustOP = (CallCenterCustOP) JsonMapper.fromJsonString(op.getCust(),
                    CallCenterCustOP.class);
            // LoanApplySimpleVO applyAllotVO =
            // loanApplyService.getLoanApplyById(callCenterCustOP.getUuid2());
            if (StringUtils.isNotBlank(callCenterCustOP.getUuid2())) {
                // ApplyAllotVO applyAllotVO =
                loanApplyService.getApplyById(callCenterCustOP.getUuid2());
                request.setAttribute("applyId", callCenterCustOP.getUuid2());

                // if (applyAllotVO != null) {
                // applyAllotVO.setPurpose(LoanPurposeEnum.getDesc(applyAllotVO.getPurpose()));
                // applyAllotVO.setStatusStr(ApplyStatusLifeCycleEnum.getDesc(applyAllotVO.getStatus()));
                //
                // request.setAttribute("apply", applyAllotVO);
                //
                // }
                LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(callCenterCustOP.getUuid2());
                request.setAttribute("applyInfo", applySimpleVO);

                request.setAttribute("productId", applySimpleVO.getProductId());

                QueryUserOP queryUserOP = new QueryUserOP();
                queryUserOP.setId(userInfo.getId());
                queryUserOP.setApplyId(callCenterCustOP.getUuid2());
                queryUserOP.setSnapshot(true);
                QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
                request.setAttribute("vo", vo);

            }

        }
        request.setAttribute("options", refuseReasonService.findAll());

        request.setAttribute("userId", userInfo.getId());
        request.setAttribute("operatorId", op.getOperator_id());
        request.setAttribute("sign", "check");
        request.setAttribute("mobile", op.getCalled());

    }

    /**
     * 借款人详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userDetail", method = RequestMethod.POST)
    public ApiResult userDetail(@RequestParam(value = "userId") String userId,
                                @RequestParam(value = "applyId", required = false) String applyId,
                                @RequestParam(value = "sign", required = false) String sign) {
        try {
            String cacheKey = "apply_user_detail_" + userId + "_" + applyId;
            UserInfoVO vo = (UserInfoVO) JedisUtils.getObject(cacheKey);
            if (vo == null) {
                boolean snapshot = !StringUtils.equals(sign, "user");
                vo = custUserService.getUserInfo(userId, applyId, snapshot);
                if (snapshot) {
                    vo.setContactList(reportService.contactMatch(vo.getId(), applyId, vo.getContactList()));
                }

                vo.setSign(sign);

                JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
            }

            ContactHistoryOP contactHistoryOP = new ContactHistoryOP();
            contactHistoryOP.setUserId(userId);
            contactHistoryOP.setApplyId(applyId);
            contactHistoryOP.setMobile(vo.getMobile());
            contactHistoryOP.setUserName("test");

            cacheKey = "apply_remove_duplicate_query_" + contactHistoryOP.getUserId() + "_"
                    + contactHistoryOP.getApplyId();

            RemoveDuplicateQueryVO removeDuplicateQueryVO = (RemoveDuplicateQueryVO) JedisUtils.getObject(cacheKey);
            if (removeDuplicateQueryVO == null) {

                List<ContactHistoryVO> list = contactHistoryService.removeDuplicateQuery(contactHistoryOP);
                removeDuplicateQueryVO = new RemoveDuplicateQueryVO();
                removeDuplicateQueryVO.setList(list);

                JedisUtils.setObject(cacheKey, removeDuplicateQueryVO, Global.TWO_HOURS_CACHESECONDS);
            }

            ApiResult result = new ApiResult("1", "提交成功", vo);
            result.put("removeDuplicateQueryVO", removeDuplicateQueryVO);

            return result;
        } catch (RuntimeException e) {
            logger.error("查询借款人详情异常：id = " + userId + " , " + "applyId =" + applyId + ", sign = " + sign, e);
            return new ApiResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询借款人详情异常：id = " + userId + " , applyId =" + applyId + ",sign = " + sign, e);
            return new ApiResult("99", "系统异常");
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "getContactConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getContactConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                           @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "approvalContactConnectInfo_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            //data = null;//临时移除缓存
            if (data == null) {
                data = new HashMap();
                LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);

                List<CallConnectVO> callInCntList = new ArrayList();
                List<CallConnectVO> callOutCntList = new ArrayList();

                List<ContactCheck> contactChecks = new ArrayList<ContactCheck>();

                if (applyTripartiteService.isExistApplyId(applyId)) {
                    //return "modules/cust/XianJinCardUserView";
                    XianJinBaiKaCommonOP xianJinBaiKaAdditional = xianJinBaiKaService.getXianJinBaiKaAdditional(userId);
                    XianJinBaiKaCommonOP xianJinBaiKaBase = xianJinBaiKaService.getXianJinBaiKaBase(userId);
                    List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook().getPhoneList();
                    List<ContactList> contactList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify()
                            .getContactList();

                    for (String phone : phoneList) {
                        ContactCheck contactCheck = new ContactCheck();
                        String contactMobile = phone.substring(phone.lastIndexOf("_") + 1);
                        String contactName = phone.substring(0, (phone.lastIndexOf("_")));
                        contactCheck.setMobile(contactMobile);
                        contactCheck.setName(contactName);
                        contactCheck.setCallCnt(0);
                        contactCheck.setCallLen(0);
                        for (ContactList contactList1 : contactList) {
                            if (contactMobile.equals(contactList1.getPhoneNum())) {
                                contactCheck.setCallCnt(contactList1.getCallCnt());
                                contactCheck.setCallLen(new Double(contactList1.getCallLen() * 60).intValue());
                            }
                        }
                        contactChecks.add(contactCheck);
                    }
                    Collections.sort(contactChecks);


                } else if ((("RONG".equals(applySimpleVO.getChannelId()) || "RONGJHH".equals(applySimpleVO.getChannelId())) && "4".equals(applySimpleVO.getSource()))
                        || applyTripartiteRong360Service.isExistApplyId(applyId)) {

                    Map ConnectInfoMap = (Map) reportService.getRongConnectInfo(applyId, userId);

                    callInCntList = BeanMapper.mapList((List) ConnectInfoMap.get("callInCntList"), CallConnectVO.class);
                    callOutCntList = BeanMapper.mapList((List) ConnectInfoMap.get("callOutCntList"),
                            CallConnectVO.class);

                    List<RongContactCheck> rongContactCheckList = (List<RongContactCheck>) ConnectInfoMap.get("data2");

                    ContactCheck contactCheck = null;
                    for (RongContactCheck iteam : rongContactCheckList) {
                        contactCheck = new ContactCheck();
                        contactCheck.setName((String) iteam.getName());
                        contactCheck.setMobile((String) iteam.getPhone());
                        contactCheck.setCallCnt((Integer) iteam.getTalkCnt());
                        contactCheck.setCallLen(iteam.getTalkSeconds());

                        contactChecks.add(contactCheck);
                    }

                    //return "modules/cust/rongUserView";
                } else if (jdqService.isExistApplyId(applyId)) {

                    String orderNo = jdqService.getOrderNo(applyId);
                    JDQReport jdqReport = jdqService.getReportData(orderNo);
                    contactChecks = jdqReport.getContactCheckList();

                    CallConnectVO callInCnt = null;
                    for (Calincntlistv iteam : jdqReport.getCalincntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCalledCnt(iteam.getTerminatingcallcount());
                        callInCnt.setCalledSeconds(iteam.getTerminatingtime());
                        callInCntList.add(callInCnt);
                    }

                    for (Calloutcntlistv iteam : jdqReport.getCalloutcntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCallCnt(iteam.getOriginatingcallcount());
                        callInCnt.setCallSeconds(iteam.getOriginatingtime());
                        callOutCntList.add(callInCnt);
                    }

                    // return "modules/cust/jdqUserView";
                } else if (dwdService.isExistApplyId(applyId)) {

                    String orderNo = dwdService.getOrderNo(applyId);
                    DWDReport jdqReport = dwdService.getReportData(orderNo);
                    contactChecks = jdqReport.getContactCheckList();

                    CallConnectVO callInCnt = null;
                    for (Calincntlistv iteam : jdqReport.getCalincntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCalledCnt(iteam.getTerminatingcallcount());
                        callInCnt.setCalledSeconds(iteam.getTerminatingtime());
                        callInCntList.add(callInCnt);
                    }

                    for (Calloutcntlistv iteam : jdqReport.getCalloutcntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCallCnt(iteam.getOriginatingcallcount());
                        callInCnt.setCallSeconds(iteam.getOriginatingtime());
                        callOutCntList.add(callInCnt);
                    }

                    // return "modules/cust/dwdUserView";
                } else if (sllService.isExistApplyId(applyId)) {

                    String orderNo = sllService.getOrderNo(applyId);
                    JDQReport jdqReport = sllService.getReportData(orderNo);
                    contactChecks = jdqReport.getContactCheckList();

                    CallConnectVO callInCnt = null;
                    for (Calincntlistv iteam : jdqReport.getCalincntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCalledCnt(iteam.getTerminatingcallcount());
                        callInCnt.setCalledSeconds(iteam.getTerminatingtime());
                        callInCntList.add(callInCnt);
                    }

                    for (Calloutcntlistv iteam : jdqReport.getCalloutcntlistv()) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName(iteam.getContactname());
                        callInCnt.setPhone(iteam.getMobile());
                        callInCnt.setPhoneLocation(iteam.getBelongto());
                        callInCnt.setCallCnt(iteam.getOriginatingcallcount());
                        callInCnt.setCallSeconds(iteam.getOriginatingtime());
                        callOutCntList.add(callInCnt);
                    }
                    // return "modules/cust/sllUserView";
                } else {

                    int type = 1;
                    String report_cacheKey = "apply_baiqishi_report_" + applyId + "_" + type;
                    String result = JedisUtils.get(report_cacheKey);
                    if (result == null) {
                        result = reportService.getReportData(applyId, type);
                        JedisUtils.set(report_cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
                    }

                    Map reportData = (Map) JsonMapper.fromJsonString(result, Map.class);

                    List<Map<String, Object>> ccmList = (List) reportData.get("ccmList1");// 呼入
                    CallConnectVO callInCnt = null;
                    for (Map<String, Object> ccmIteam : ccmList) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName((String) ccmIteam.get("contactName"));
                        callInCnt.setPhone((String) ccmIteam.get("mobile"));
                        callInCnt.setPhoneLocation((String) ccmIteam.get("belongTo"));
                        callInCnt.setCalledCnt((Integer) ccmIteam.get("terminatingCallCount"));
                        callInCnt.setCalledSeconds((Integer) ccmIteam.get("terminatingTime"));
                        callInCntList.add(callInCnt);
                    }

                    ccmList = (List) reportData.get("ccmList2");// 呼出

                    for (Map<String, Object> ccmIteam : ccmList) {
                        callInCnt = new CallConnectVO();
                        callInCnt.setContactName((String) ccmIteam.get("contactName"));
                        callInCnt.setPhone((String) ccmIteam.get("mobile"));
                        callInCnt.setPhoneLocation((String) ccmIteam.get("belongTo"));
                        callInCnt.setCallCnt((Integer) ccmIteam.get("originatingCallCount"));
                        callInCnt.setCallSeconds((Integer) ccmIteam.get("originatingTime"));
                        callOutCntList.add(callInCnt);
                    }

                    List<Map<String, Object>> contactConnectInfoList = reportService.getContactConnectInfo(applyId);
                    ContactCheck contactCheck = null;
                    for (Map<String, Object> iteam : contactConnectInfoList) {
                        contactCheck = new ContactCheck();
                        contactCheck.setName((String) iteam.get("name"));
                        contactCheck.setMobile((String) iteam.get("mobile"));
                        contactCheck.setCallCnt((Integer) iteam.get("connectCount"));
                        if (iteam.get("connectTime") != null)
                            contactCheck.setCallLen((Integer) iteam.get("connectTime"));

                        contactChecks.add(contactCheck);
                    }

                    //return "modules/cust/userView";

                }
                data.put("contactChecks", contactChecks);

                data.put("callInCntList", callInCntList);
                data.put("callOutCntList", callOutCntList);


                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }

            return new ApiResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new ApiResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new ApiResult("99", "系统异常");
        }

    }
//	
//	private void setCallInCntList(List<CallConnectVO> callInCntList,List<ContactCheck> contactChecks){
//		CallConnectVO callInCnt = null;
//		for (ContactCheck ccmIteam : contactChecks) {
//			callInCnt = new CallConnectVO();
//			callInCnt.setContactName(ccmIteam.getName());
//			callInCnt.setPhone(ccmIteam.getMobile());
//			callInCnt.setPhoneLocation("");
//			callInCnt.setCalledCnt(ccmIteam.getCallCnt());
//			callInCnt.setCalledSeconds(new Double(ccmIteam.getCallLen()).intValue());
//			callInCntList.add(callInCnt);
//		}
//	}

    /**
     * 审核
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "approve")
    public ApiResult approve(@Valid CheckOP checkOP, HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        UserVO user = userService.getUserByCallId(checkOP.getOperatorId());
        checkOP.setOperatorId(user.getId());
        checkOP.setOperatorName(user.getName());

        if (isApproveLocked(checkOP.getApplyId(), user.getId())) {
            return new ApiResult("99", "系统异常");
        }
        LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(checkOP.getApplyId());
        if (applySimpleVO == null || applySimpleVO.getIsHaveLoan() == LoanApplySimpleVO.NO) {
            throw new RuntimeException("审核订单异常 : apply not find");
        }
        if (checkOP.getCheckResult() == 3) {
            throw new RuntimeException("请点击保存按钮");
        }
        LoanCheckOP op = BeanMapper.map(checkOP, LoanCheckOP.class);
        if (op.getCheckResult() == 1) {
            if (LoanProductEnum.XJD.getId().equals(applySimpleVO.getProductId())) {
                if (op.getApproveTerm().intValue() != Global.XJD_AUTO_FQ_DAY_90
                        && op.getApproveTerm().intValue() != Global.XJD_AUTO_FQ_DAY_28
                        && op.getApproveTerm().intValue() != Global.XJD_DQ_DAY_14
                        && op.getApproveTerm().intValue() != Global.XJD_DQ_DAY_15) {
                    throw new RuntimeException("未知的审批期限");
                }
                if (op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                        && op.getApproveAmt().compareTo(new BigDecimal(2000)) != 0) {
                    throw new RuntimeException("90天产品审批金额只能为2000");
                } else if (op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                        && op.getApproveAmt().compareTo(new BigDecimal(3000)) != 0) {
                    throw new RuntimeException("28天产品审批金额只能为3000");
                } else if ((op.getApproveTerm().intValue() == Global.XJD_DQ_DAY_14
                        || op.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15)
                        && op.getApproveAmt().compareTo(new BigDecimal(1500)) != 0) {
                    throw new RuntimeException("14/15天产品审批金额只能为1500");
                }

                op.setRepayTerm(applySimpleVO.getTerm());
                op.setProductId(applySimpleVO.getProductId());
                if (op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                        || op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                        || op.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
                    op.setBorrowType(13);
                } else {
                    op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
                }
            } else if (LoanProductEnum.XJDFQ.getId().equals(applySimpleVO.getProductId())) {
                // op.setApproveAmt(applySimpleVO.getApproveAmt());
                op.setApproveTerm(applySimpleVO.getApproveTerm());
                op.setRepayTerm(applySimpleVO.getTerm());
                op.setBorrowType(8);// 现金分期
                op.setProductId(applySimpleVO.getProductId());
            } else {
                throw new RuntimeException("审核订单异常 : product not find");
            }
        } else {
            op.setApproveAmt(applySimpleVO.getApproveAmt());
            op.setApproveTerm(applySimpleVO.getApproveTerm());
            op.setRepayTerm(applySimpleVO.getTerm());
            op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
            op.setServFeeRate(applySimpleVO.getServFeeRate());
            op.setProductId(applySimpleVO.getProductId());
        }
        // if (UserUtils.haveRole("primary")) {
        // op.setLevel(true); // 初级信审
        // }

        op.setOperatorId(user.getId());
        op.setOperatorName(user.getName());
        op.setIp(Servlets.getIpAddress(request));
        synchronized (CallCenterController.class) {
            loanApplyService.approve(op);
            removeApproveCount(op.getApplyId(), user.getId());
            removeApproveSpeedLock(user.getId());
            JedisUtils.set(APPROVE_LOCK_KEY_PREFIX + op.getApplyId(), "1", 30);
        }
        return new ApiResult("1", "操作成功", null);
    }

    /**
     * 审核页面的保存按钮
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCheck")
    public ApiResult saveCheck(@Valid CheckOP checkOP, HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        UserVO user = userService.getUserByCallId(checkOP.getOperatorId());
        checkOP.setOperatorId(user.getId());
        checkOP.setOperatorName(user.getName());
        if (isApproveLocked(checkOP.getApplyId(), user.getId())) {
            return new ApiResult("99", "此订单正在审核中...");
        }
        LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(checkOP.getApplyId());
        int callCount = 1;
        if (applySimpleVO.getCallCount() != null) {
            callCount = applySimpleVO.getCallCount() + 1;
        }
        loanApplyService.updateNoAnswer(checkOP.getApplyId(), callCount, checkOP.getRemark());

        if (applySimpleVO == null || applySimpleVO.getIsHaveLoan() == LoanApplySimpleVO.NO) {
            throw new RuntimeException("审核订单异常 : apply not find");
        }
        LoanCheckOP op = BeanMapper.map(checkOP, LoanCheckOP.class);
        if (LoanProductEnum.XJD.getId().equals(applySimpleVO.getProductId())) {
            op.setRepayTerm(applySimpleVO.getTerm());
            op.setProductId(applySimpleVO.getProductId());
            if (op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_90
                    || op.getApproveTerm().intValue() == Global.XJD_AUTO_FQ_DAY_28
                    || op.getApproveTerm().intValue() == Global.XJD_DQ_DAY_15) {
                op.setBorrowType(13);
            } else {
                op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
            }
        } else if (LoanProductEnum.XJDFQ.getId().equals(applySimpleVO.getProductId())) {
            // op.setApproveAmt(applySimpleVO.getApproveAmt());
            op.setApproveTerm(applySimpleVO.getApproveTerm());
            op.setRepayTerm(applySimpleVO.getTerm());
            op.setBorrowType(8);// 现金分期
            op.setProductId(applySimpleVO.getProductId());
        } else {
            throw new RuntimeException("审核订单异常 : product not find");
        }

        Date now = new Date();

        // 插入审核日志
        loanApplyService.saveApproveLogLatter(op, now, false, user.getId(), user.getName(), checkOP.getApplyId());

        // 点击保存后立刻可以点击新单,删除锁
        removeApproveSpeedLock(user.getId());
        return new ApiResult("1", "操作成功", null);
    }

    private static final String APPROVE_LOCK_KEY_PREFIX = "rengong_approve_lock_";
    private static final String APPROVE_COUNT_KEY_PREFIX = "rengong_approve_count_";
    private static final String APPROVE_SPEED_KEY_PREFIX = "rengong_approve_speed_";

    private boolean isApproveLocked(String applyId, String userId) {
        String lockCacheKey = APPROVE_LOCK_KEY_PREFIX + applyId;
        String value = JedisUtils.get(lockCacheKey);
        if (StringUtils.isNotBlank(value)) {
            if (!value.contains(userId)) {
                return true;
            }
        } else {
            String speedCacheKey = APPROVE_SPEED_KEY_PREFIX + userId;
            if (JedisUtils.get(speedCacheKey) != null) {
                return true;
            }
        }
        return false;
    }

    private void removeApproveCount(String applyId, String userId) {
        String cacheKey = APPROVE_COUNT_KEY_PREFIX + userId + "_" + applyId;
        JedisUtils.del(cacheKey);
    }

    private void removeApproveSpeedLock(String userId) {
        String speedCacheKey = APPROVE_SPEED_KEY_PREFIX + userId;
        JedisUtils.del(speedCacheKey);
    }

}
