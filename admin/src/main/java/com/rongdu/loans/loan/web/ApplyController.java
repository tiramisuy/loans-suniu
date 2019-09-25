package com.rongdu.loans.loan.web;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileServerClient;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.*;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.utils.oss.OOSUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.anrong.vo.MSPReprtVO;
import com.rongdu.loans.basic.service.ConfigService;
import com.rongdu.loans.cms.service.UserApplyCountService;
import com.rongdu.loans.common.*;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.credit.moxie.vo.CreditcardReportVO;
import com.rongdu.loans.cust.option.QueryUserOP;
import com.rongdu.loans.cust.service.CustCouponService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.*;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.WhetherEnum;
import com.rongdu.loans.koudai.op.pay.KDPayCountOP;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.op.CheckOP;
import com.rongdu.loans.loan.option.*;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.Calincntlistv;
import com.rongdu.loans.loan.option.jdq.report.Calloutcntlistv;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.rongTJreportv1.RongContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.sys.entity.Area;
import com.rongdu.loans.sys.entity.Office;
import com.rongdu.loans.sys.entity.Role;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.AreaService;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.OfficeService;
import com.rongdu.loans.sys.service.SystemService;
import com.rongdu.loans.sys.utils.UserUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.*;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/apply")
public class ApplyController extends BaseController {
    private static final String APPROVE_LOCK_KEY_PREFIX = "rengong_approve_lock_";
    private static final String APPROVE_COUNT_KEY_PREFIX = "rengong_approve_count_";
    private static final String APPROVE_SPEED_KEY_PREFIX = "rengong_approve_speed_";
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private RefuseReasonService refuseReasonService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private UserApplyCountService userApplyCountService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private ContactHistoryService contactHistoryService;
    @Autowired
    private ApplyTripartiteService applyTripartiteService;
    @Autowired
    private ApplyTripartiteRong360Service applyTripartiteRong360Service;
    @Autowired
    private OverdueCountService overdueCountService;
    @Autowired
    private ApplyAllotService applyAllotService;
    @Autowired
    private CustCouponService custCouponService;
    @Autowired
    private JDQService jdqService;
    @Autowired
    private DWDService dwdService;
    @Autowired
    private KDPayService kdPayService;
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private SLLService sllService;
    @Autowired
    private XianJinBaiKaService xianJinBaiKaService;
    @Autowired
    private ApplyTripartiteAnrongService applyTripartiteAnrongService;
    @Autowired
    private ConfigService configService;

    /**
     * 定义已过审页面订单状态下拉框枚举
     */
    private static List<ApplyStatusLifeCycleEnum> pageViewList = Arrays.asList(WAITING_PUSH, PUSH_SUCCESS,
            WAITING_REPAY, REPAY, CANCAL, WAITING_VERIFICATION, VERIFICATION);

    /**
     * 定义已过审页面订单状态下拉框与实际数据库状态的一对多映射关系
     */
    private static Map<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>> relationMap = new HashMap<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>>();
    private List<String> asList;

    static {
        relationMap.put(WAITING_PUSH, Arrays.asList(WAITING_PUSH, PUSH_FAIL));
        relationMap.put(PUSH_SUCCESS, Arrays.asList(PUSH_SUCCESS));

        relationMap.put(WAITING_REPAY, Arrays.asList(HAS_BEEN_LENDING, WITHDRAWAL_SUCCESS, WITHDRAWAL_FAIL,
                OVERDUE_WAITING_REPAY, WAITING_REPAY));
        relationMap.put(REPAY, Arrays.asList(BRING_FORWARD_REPAY, REPAY, OVERDUE_REPAY));
        relationMap.put(CANCAL, Arrays.asList(CANCAL));
        relationMap.put(WAITING_VERIFICATION, Arrays.asList(WAITING_VERIFICATION));
        relationMap.put(VERIFICATION, Arrays.asList(VERIFICATION));
    }

    /**
     * 贷前、贷中列表
     *
     * @param applyOP
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    public String list(ApplyOP applyOP, Boolean first, Model model) {
        User sysUser = UserUtils.getUser();
        logger.info("贷前、贷中列表查询--->{}--->{}-->{}", sysUser.getId(), sysUser.getName(), JsonMapper.toJsonString(applyOP));

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);
        if (null != first && first) {
            model.addAttribute("page", new Page());
            return "modules/loan/applyList";
        }

        model.addAttribute("auditor", UserUtils.getUserByRole("xinshen"));
        model.addAttribute("enums", ApplyOP.CallCountEnum.values());
        model.addAttribute("autoCheck", WhetherEnum.values());
        model.addAttribute("statusList", getStatusEnumList(applyOP.getStage()));
        model.addAttribute("applyOP", applyOP);

        Page page = new Page();
        ApplyListOP applyListOP = assemble(applyOP);
        // 查询页面默认的多种状态
        getStatusList(applyListOP, page);
        Page<ApplyListVO> voPage = null;

        voPage = loanApplyService.getLoanApplyList(page, applyListOP);

        getStatusNameForPage(voPage);
        if (applyOP.getStage() == 1 || applyOP.getStage() == 5) {
            for (ApplyListVO applyVO : voPage.getList()) {
                String lockName = getApproveLockName(applyVO.getId());
                if (lockName != null) {
                    applyVO.setStatusStr("[" + lockName + "]审核中");
                }
            }
        }

        model.addAttribute("page", voPage);
        return "modules/loan/applyList";
    }

    /**
     * 加载更改放款渠道页面
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "toUpdatePayChannel")
    public WebResult toUpdatePayChannel(@RequestParam(value = "applyId") String applyId) {
        try {
            return new WebResult("1", "提交成功", applyId);
        } catch (Exception e) {
            logger.error("加载更改放款渠道页面异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 添加黑名单: cust_blacklist
     *
     * @return
     */
    @RequestMapping(value = "updatePayChannel")
    @ResponseBody
    public WebResult updatePayChannel(String applyId, Integer payChannel) {
        try {
            int flag = loanApplyService.updatePChannel(applyId, payChannel);
            if (flag > 0) {
                if (flag == 500) {
                    return new WebResult("200", "该笔订单已确认借款,不能修改放款渠道.");
                } else {
                    return new WebResult("1", "提交成功！");
                }
            }
            return new WebResult("200", "系统异常");

        } catch (Exception e) {
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 申请列表
     *
     * @param applyOP
     * @param model
     * @return
     */
    @RequestMapping(value = "applyForList")
    public String applyForList(ApplyOP applyOP, Model model) {
        model.addAttribute("applyOP", applyOP);

        Page page = new Page();
        ApplyListOP applyListOP = assemble(applyOP);
        // 查询页面默认的多种状态
        getStatusList(applyListOP, page);
        Page<ApplyListVO> voPage = null;

        // RoleControlParam roleControlParam =
        // RoleControl.get(applyListOP.getProductId(), applyOP.getCompanyId());
        // applyListOP.setProductId(roleControlParam.getProductId());
        applyListOP.setCompanyId(UserUtils.getUser().getCompany().getId());

        voPage = loanApplyService.getLoanApplyList(page, applyListOP);

        getStatusNameForPage(voPage);

        model.addAttribute("page", voPage);
        return "modules/loan/applyForList";
    }

    /**
     * 订单申请详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "checkApplyFrom")
    public String checkApplyFrom(@RequestParam(value = "id") String id, @RequestParam(value = "sign") String sign,
                                 @RequestParam(value = "applyId") String applyId,
                                 @RequestParam(value = "flag", required = false) String flag, Model model,
                                 RedirectAttributes redirectAttributes) {
        QueryUserOP queryUserOP = new QueryUserOP();
        queryUserOP.setId(id);
        queryUserOP.setApplyId(applyId);
        queryUserOP.setSnapshot(true);
        QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
        model.addAttribute("vo", vo);
        model.addAttribute("flag", flag);
        model.addAttribute("applyId", applyId);
        model.addAttribute("sign", sign);

        LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);
        model.addAttribute("productId", applySimpleVO.getProductId());
        model.addAttribute("applyInfo", applySimpleVO);

        return "modules/cust/userApplyView";
    }

    /**
     * 审核/详情 页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "checkFrom")
    public String checkFrom(@RequestParam(value = "id") String id, @RequestParam(value = "sign") String sign,
                            @RequestParam(value = "applyId") String applyId,
                            @RequestParam(value = "flag", required = false) String flag,
                            @RequestParam(value = "sourse", required = false) String sourse, // 来源
                            Model model, RedirectAttributes redirectAttributes) {

        User user = UserUtils.getUser();
        logger.info("审核/详情 页面查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(id));

        String lockKey = "loan_apply_checkform_lock_" + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        if (StringUtils.equals(sign, "check") && StringUtils.isBlank(sourse)) {
            boolean lock = JedisUtils.setLock(lockKey, requestId, 30);
            if (!lock) {
                addMessage(redirectAttributes, "此订单正在审核中...");
                return "redirect:" + adminPath + "/loan/apply/list";
            }
        }
        try {
            if (StringUtils.equals(sign, "check")) {
                if (UserUtils.haveRole("system")) {
                    model.addAttribute("options", refuseReasonService.findAll());
                } else {
                    if (isApproveLocked(applyId)) {
                        addMessage(redirectAttributes, "此订单正在审核中/点击审核按钮频繁...");
                        return "redirect:" + adminPath + "/loan/apply/list";
                    } else {
                        // 审核订单数量锁
                        String numKey = "user_approve_apply_num_lock_" + user.getId();
                        String maxNum = configService.getValue("user_approve_apply_num_lock");
                        List<String> applys = (List<String>) JedisUtils.getObjectList(numKey);
                        if (null != applys){
                            boolean containFlag = applys.contains(applyId);
                            if (!containFlag){
                                if (applys.size() >= Integer.valueOf(maxNum)){
                                    addMessage(redirectAttributes, "当前审核中订单总数超限...");
                                    return "redirect:" + adminPath + "/loan/apply/list";
                                } else {
                                    applys.add(applyId);
                                    JedisUtils.setObjectList(numKey, applys, DateUtils.getMiao());
                                }
                            }
                        } else {
                            applys = new ArrayList<>();
                            applys.add(applyId);
                            JedisUtils.setObjectList(numKey, applys, DateUtils.getMiao());
                        }
                        model.addAttribute("options", refuseReasonService.findAll());
                        addApproveLock(applyId);
                        setApproveCount(applyId);
                    }
                }
            }
            QueryUserOP queryUserOP = new QueryUserOP();
            queryUserOP.setId(id);
            queryUserOP.setApplyId(applyId);
            queryUserOP.setSnapshot(true);
            QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
            model.addAttribute("vo", vo);
            model.addAttribute("flag", flag);
            model.addAttribute("applyId", applyId);
            model.addAttribute("sign", sign);
            model.addAttribute("sourse", sourse);

            LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);
            model.addAttribute("productId", applySimpleVO.getProductId());
            model.addAttribute("applyInfo", applySimpleVO);
            model.addAttribute("callCount", applySimpleVO.getCallCount());

            List<ChannelVO> chList = channelService.findAllChannel();
            Map<String, String> channelMap = new HashMap<>();
            chList.forEach(channelVO -> channelMap.put(channelVO.getCid(),channelVO.getcName()));
            model.addAttribute("channelMap", channelMap);

            if (jdqService.isExistApplyId(applyId)) {
                return "modules/cust/jdqUserView";
            } else if (dwdService.isExistApplyId(applyId)) {
                return "modules/cust/dwdUserView";
            } else {
                return "modules/cust/userView";
            }
        } finally {
            // 解除orderNo并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
    }

    /**
     * 审核
     *
     * @return
     */
    @RequestMapping(value = "check")
    public String check(@Valid CheckOP checkOP, @RequestParam(value = "sourse", required = false) String sourse, // 来源
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (isApproveLocked(checkOP.getApplyId())) {
            addMessage(redirectAttributes, "此订单正在审核中...");
            return "redirect:" + adminPath + "/loan/apply/list";
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
            if (op.getApproveTerm().intValue() != Global.XJD_AUTO_FQ_DAY_28 && op.getApproveTerm().intValue() != Global.XJD_DQ_DAY_8) {
                throw new RuntimeException("未知的审批期限");
            }
            op.setRepayTerm(applySimpleVO.getTerm());
            op.setProductId(applySimpleVO.getProductId());
            op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
        } else {
            op.setApproveAmt(applySimpleVO.getApproveAmt());
            op.setApproveTerm(applySimpleVO.getApproveTerm());
            op.setRepayTerm(applySimpleVO.getTerm());
            op.setBorrowType(Global.DEFAULT_BORROW_TYPE);
            op.setServFeeRate(applySimpleVO.getServFeeRate());
            op.setProductId(applySimpleVO.getProductId());
        }
/*		if (UserUtils.haveRole("primary")) {
			op.setLevel(true); // 初级信审
		}*/
        User user = UserUtils.getUser();
        op.setOperatorId(user.getId());
        op.setOperatorName(user.getName());
        op.setIp(Servlets.getIpAddress(request));
        synchronized (ApplyController.class) {
            loanApplyService.approve(op);
            removeApproveCount(op.getApplyId());
            removeApproveSpeedLock();
            JedisUtils.set(APPROVE_LOCK_KEY_PREFIX + op.getApplyId(), "1", 30);
            JedisUtils.del("approve_apply_" + op.getApplyId());// 删除审批队列订单
            // 删除审核订单集数量
            String numKey = "user_approve_apply_num_lock_" + user.getId();
            List<String> applys = (List<String>) JedisUtils.getObjectList(numKey);
            applys.remove(checkOP.getApplyId());
            JedisUtils.setObjectList(numKey, applys, DateUtils.getMiao());
        }

        if (StringUtils.isNotBlank(sourse)) {
            return "redirect:" + adminPath + "/loan/apply/checkFrom?id=" + applySimpleVO.getUserId()
                    + "&sign=detail&applyId=" + checkOP.getApplyId();
        }

        return "redirect:" + adminPath + "/loan/apply/list";
    }

    /**
     * 还款计划列表
     */
    @ResponseBody
    @RequestMapping(value = "contentTable")
    public WebResult contentTable(@Valid ContentTableOP op) {
        try {
            ContentTableVO vo = loanApplyService.contentTable(op);
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询还款计划列表异常：op = " + JsonMapper.getInstance().toJson(op), e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询还款计划列表异常：op = " + JsonMapper.getInstance().toJson(op), e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 根据预先定义的状态映射关系，将数据库中的状态转换成页面上输出的状态
     *
     * @param voPage
     */
    private void getStatusNameForPage(Page<ApplyListVO> voPage) {
        List<ApplyListVO> list = voPage.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ApplyListVO vo : list) {
                for (Map.Entry<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>> entry : relationMap
                        .entrySet()) {
                    List<ApplyStatusLifeCycleEnum> value = entry.getValue();
                    if (value.contains(ApplyStatusLifeCycleEnum.get(vo.getStatus()))) {
                        vo.setStatus(entry.getKey().getValue());
                        continue;
                    }
                }
            }
        }

    }

    /**
     * 组装参数
     *
     * @param applyOP
     * @return
     */
    private ApplyListOP assemble(ApplyOP applyOP) {
        ApplyListOP applyListOP = BeanMapper.map(applyOP, ApplyListOP.class);
        if (StringUtils.isNotBlank(applyOP.getApplyStart())) {
            applyListOP.setApplyTimeStart(DateUtils.parse(applyOP.getApplyStart()));
        }
        if (StringUtils.isNotBlank(applyOP.getApplyEnd())) {
            applyListOP.setApplyTimeEnd(DateUtils.parse(applyOP.getApplyEnd()));
        }
        if (StringUtils.isNotBlank(applyOP.getCheckStart())) {
            applyListOP.setCheckTimeStart(DateUtils.parse(applyOP.getCheckStart()));
        }
        if (StringUtils.isNotBlank(applyOP.getCheckEnd())) {
            applyListOP.setCheckTimeEnd(DateUtils.parse(applyOP.getCheckEnd()));
        }
        if (applyOP.getStage() != null && applyOP.getStage().intValue() == 1
                && StringUtils.isNotBlank(applyOP.getAuditor())) {
            List<String> myApplyIds = getApprovingListByAuditor(applyOP.getAuditor());
            if (myApplyIds.isEmpty()) {
                myApplyIds.add("-1");
            }
            applyListOP.setAuditorList(myApplyIds);
        }
        if (CollectionUtils.isNotEmpty(applyOP.getCallCount())) {
            List<ApplyListOP.CallCountSize> callCounts = new ArrayList<ApplyListOP.CallCountSize>();
            for (Integer value : applyOP.getCallCount()) {
                ApplyOP.CallCountEnum callCountEnum = ApplyOP.CallCountEnum.get(value);
                ApplyListOP.CallCountSize callCountSize = applyListOP.new CallCountSize();
                callCountSize.setMin(callCountEnum.getMin());
                callCountSize.setMax(callCountEnum.getMax());
                callCounts.add(callCountSize);
            }
            applyListOP.setCallCounts(callCounts);
        }
        return applyListOP;
    }

    /**
     * 获取查询时的状态和page参数
     *
     * @param applyListOP
     * @return
     */
    private void getStatusList(ApplyListOP applyListOP, Page page) {
        page.setPageNo(applyListOP.getPageNo());
        page.setPageSize(applyListOP.getPageSize());
        String autoCheck = applyListOP.getAutoCheck();
        String checkStatus;
        // 前端页面： 1待审核 , 2已过审， 3已否决 ，4办理中
        switch (applyListOP.getStage()) {
            case 0:
                applyListOP.setStatusList(Arrays.asList(APPLY_SUCCESS.getValue(), WAITING_AOTUCHECK.getValue()));
                page.setOrderBy("applyTime");
                break;
            case 1:
                applyListOP.setStatusList(Arrays.asList(WAITING_MANUALCHECK.getValue()));
                page.setOrderBy("applyTime");
                break;
            case 2:
                // 已过审页面默认显示待推送以后的所有状态
                applyListOP.setStatusList(Arrays.asList(AOTUCHECK_PASS.getValue(), MANUALCHECK_PASS.getValue(),
                        WAITING_SIGN.getValue(), SIGNED.getValue(), WAITING_PUSH.getValue(), PUSH_SUCCESS.getValue(),
                        PUSH_FAIL.getValue(), WAITING_LENDING.getValue(), HAS_BEEN_LENDING.getValue(),
                        WAITING_WITHDRAWAL.getValue(), CASH_WITHDRAWAL.getValue(), WITHDRAWAL_SUCCESS.getValue(),
                        WITHDRAWAL_FAIL.getValue(), WAITING_REPAY.getValue(), BRING_FORWARD_REPAY.getValue(),
                        REPAY.getValue(), OVERDUE_WAITING_REPAY.getValue(), OVERDUE_REPAY.getValue(),
                        WAITING_VERIFICATION.getValue(), VERIFICATION.getValue()));
                // 如果页面选择了一个状态，则根据预先定义的关系映射，查询实际对应状态的数据
                ApplyStatusLifeCycleEnum option = ApplyStatusLifeCycleEnum.get(applyListOP.getStatus());
                List<ApplyStatusLifeCycleEnum> enums = relationMap.get(option);
                if (CollectionUtils.isNotEmpty(enums)) {
                    List<Integer> statusList = new ArrayList<Integer>();
                    for (ApplyStatusLifeCycleEnum status : enums) {
                        statusList.add(status.getValue());
                    }
                    applyListOP.setStatusList(statusList);
                }

                checkStatus = StringUtils.isBlank(autoCheck) ? ""
                        : StringUtils.equals(autoCheck, WhetherEnum.YES.getValue()) ? "1" : "3";
                applyListOP.setCheckStatus(checkStatus);
                applyListOP.setStatus(null);// 清空
                page.setOrderBy("applyTime desc");
                break;
            case 3:
                applyListOP.setStatusList(Arrays.asList(AOTUCHECK_NO_PASS.getValue(), MANUALCHECK_NO_PASS.getValue()));
                checkStatus = StringUtils.isBlank(autoCheck) ? ""
                        : StringUtils.equals(autoCheck, WhetherEnum.YES.getValue()) ? "2" : "4";
                applyListOP.setCheckStatus(checkStatus);
                page.setOrderBy("applyTime desc");
                break;
            case 4:
                applyListOP.setStatusList(Arrays.asList(WAITING_PUSH.getValue(), PUSH_SUCCESS.getValue(),
                        PUSH_FAIL.getValue(), WAITING_LENDING.getValue()));
                checkStatus = StringUtils.isBlank(autoCheck) ? ""
                        : StringUtils.equals(autoCheck, WhetherEnum.YES.getValue()) ? "1" : "3";
                applyListOP.setCheckStatus(checkStatus);
                page.setOrderBy("applyTime desc");
                break;
            case 5:
                applyListOP.setStatusList(Arrays.asList(MANUAL_RECHECK.getValue()));
                page.setOrderBy("applyTime");
                break;
            case 6:
                applyListOP.setStatusList(Arrays.asList(CANCAL.getValue()));
                // checkStatus = StringUtils.isBlank(autoCheck) ? "" :
                // StringUtils.equals(autoCheck,
                // WhetherEnum.YES.getValue()) ? "1" : "3";
                Integer[] checkStatusArray = null;
                checkStatusArray = StringUtils.isBlank(autoCheck) ? null
                        : StringUtils.equals(autoCheck, WhetherEnum.YES.getValue()) ? new Integer[]{1, 2}
                        : new Integer[]{3, 4};
                if (checkStatusArray != null) {
                    applyListOP.setCheckStatusList(Arrays.asList(checkStatusArray));
                }
                applyListOP.setStatus(null);// 清空
                page.setOrderBy("applyTime desc");
            default:
                logger.warn("stage参数错误,stage={}", applyListOP.getStage());
                break;
        }
    }

    private void getStatusNameForAPiPage(Page<ApplyListVO> voPage) {
        List<ApplyListVO> list = voPage.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ApplyListVO vo : list) {
                for (Map.Entry<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>> entry : relationMap
                        .entrySet()) {
                    List<ApplyStatusLifeCycleEnum> value = entry.getValue();
                    if (value.contains(ApplyStatusLifeCycleEnum.get(vo.getStatus()))) {
                        vo.setStatus(entry.getKey().getValue());
                        continue;
                    }
                }
            }
        }

    }

    /**
     * 封装页面上订单状态下拉框列表
     *
     * @param stage
     * @return
     */
    private List<ApplyStatusLifeCycleEnum> getStatusEnumList(Integer stage) {
        // 前端页面： 1待审核 , 2已过审， 3已否决 ，4办理中
        switch (stage) {
            case 1:
                return Arrays.asList(MANUAL_CHECK, WAITING_MANUALCHECK);
            case 2:
                return Arrays.asList(WAITING_PUSH, PUSH_SUCCESS, WAITING_REPAY, REPAY, WAITING_VERIFICATION, VERIFICATION);
            case 3:
                return Arrays.asList(AOTUCHECK_NO_PASS, MANUALCHECK_NO_PASS);
            case 4:
                return Arrays.asList(WAITING_PUSH, PUSH_SUCCESS, PUSH_FAIL, WAITING_LENDING);
            case 5:
                return Arrays.asList(MANUAL_CHECK, WAITING_MANUALCHECK, MANUAL_RECHECK);
            default:
                logger.warn("stage参数错误,stage={}", stage);
                return null;
        }
    }

    /**
     * 命中规则列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "hitRule")
    public WebResult hitRule(@RequestParam(value = "id") String id) {
        try {
            QueryUserOP queryUserOP = new QueryUserOP();
            queryUserOP.setId(id);
            queryUserOP.setSnapshot(true);
            QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
            return new WebResult("1", "查询成功", vo);
        } catch (Exception e) {
            logger.error("系统异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    private boolean isApproveLocked(String applyId) {
        User user = UserUtils.getUser();
        String lockCacheKey = APPROVE_LOCK_KEY_PREFIX + applyId;
        String value = JedisUtils.get(lockCacheKey);
        if (StringUtils.isNotBlank(value)) {
            if (!value.contains(user.getId())) {
                return true;
            }
        } else {
            String speedCacheKey = APPROVE_SPEED_KEY_PREFIX + user.getId();
            if (JedisUtils.get(speedCacheKey) != null) {
                return true;
            }
        }
        return false;
    }

    private void addApproveLock(String applyId) {
        User user = UserUtils.getUser();
        String lockCacheKey = APPROVE_LOCK_KEY_PREFIX + applyId;
        String value = JedisUtils.get(lockCacheKey);
        if (StringUtils.isBlank(value)) {
            value = user.getId() + "," + user.getName();
            int cacheSeconds = DateUtils.getSecondsOfTwoDate(new Date(), DateUtils.getDayEnd(new Date()));
            JedisUtils.set(lockCacheKey, value, cacheSeconds);

            setApproveSpeedLock();
        }
    }

    private String getApproveLockName(String applyId) {
        String name = null;
        String lockCacheKey = APPROVE_LOCK_KEY_PREFIX + applyId;
        String value = JedisUtils.get(lockCacheKey);
        if (value != null) {
            String[] arr = value.split(",");
            name = arr.length > 1 ? arr[1] : "";
        }
        return name;
    }

    private void setApproveCount(String applyId) {
        User user = UserUtils.getUser();
        String cacheKey = APPROVE_COUNT_KEY_PREFIX + user.getId() + "_" + applyId;
        if (JedisUtils.exists(cacheKey)) {
            return;
        }
        int cacheSeconds = DateUtils.getSecondsOfTwoDate(new Date(), DateUtils.getDayEnd(new Date()));
        JedisUtils.set(cacheKey, "0", cacheSeconds);
    }

    private List<String> getApprovingListByAuditor(String userId) {
        List<String> list = new ArrayList<String>();
        String cacheKey = APPROVE_COUNT_KEY_PREFIX;
        Set<String> keys = JedisUtils.getKeys(cacheKey, 2);
        if (keys != null) {
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key.indexOf(userId) != -1) {
                    String applyId = key.replace(APPROVE_COUNT_KEY_PREFIX + userId + "_", "");
                    list.add(applyId);
                }
            }
        }
        return list;
    }

    private void removeApproveCount(String applyId) {
        User user = UserUtils.getUser();
        String cacheKey = APPROVE_COUNT_KEY_PREFIX + user.getId() + "_" + applyId;
        JedisUtils.del(cacheKey);
    }

    private void setApproveSpeedLock() {
        User user = UserUtils.getUser();
        String speedCacheKey = APPROVE_SPEED_KEY_PREFIX + user.getId();
        JedisUtils.set(speedCacheKey, "1", 10);
    }

    private void removeApproveSpeedLock() {
        User user = UserUtils.getUser();
        String speedCacheKey = APPROVE_SPEED_KEY_PREFIX + user.getId();
        JedisUtils.del(speedCacheKey);
    }

    /**
     * 运营统计
     * @param applyOP
     * @param model
     * @return
     */
    @RequestMapping(value = "/operationalStatistics")
    public String operationalStatistics(ApplyOP applyOP, Model model) {
        if (StringUtils.isNotBlank(applyOP.getCheckStart())){
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
        }else {
            if (StringUtils.isBlank(applyOP.getApplyStart())) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
        }
        List<ChannelVO> chList = channelService.findAllChannel();
        String chAllStr = chList.stream().map(ChannelVO::getCid).collect(Collectors.joining("','","'","'"));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);
        OperationalStatisticsVO vo = userApplyCountService.getOperationalStatistics(applyOP);
        BigDecimal wirhdrawAmt = vo.getWithdrawAmt() == null ? BigDecimal.ZERO : vo.getWithdrawAmt();
        BigDecimal wirhdrawAmtAuto2 = vo.getWithdrawAmtAuto2() == null ? BigDecimal.ZERO : vo.getWithdrawAmtAuto2();
        vo.setWithdrawAmt(wirhdrawAmt.divide(new BigDecimal(10000),Global.FOUR_SCALE,BigDecimal.ROUND_HALF_UP));
        vo.setWithdrawAmtAuto2(wirhdrawAmtAuto2.divide(new BigDecimal(10000),Global.FOUR_SCALE,BigDecimal.ROUND_HALF_UP));
        vo.setAuto1PassApplyRate(CostUtils.divide(new BigDecimal(vo.getApplyAuto1Pass()),
                new BigDecimal(vo.getTotalApply())));    // 机审通过数/生成订单数
        vo.setManPassAuto1Rate(CostUtils.divide(new BigDecimal(vo.getApplyPass()),
                new BigDecimal(vo.getApplyAuto1Pass())));  // 人审通过数/机审通过数
        vo.setBindBankApplyPassRate(CostUtils.divide(new BigDecimal(vo.getBindBank()),
                new BigDecimal(vo.getApplyPass()))); // 绑卡数/人审通过数
        vo.setAuto1PassRegRate(CostUtils.divide(new BigDecimal(vo.getApplyPass()),
                new BigDecimal(vo.getTotalReg())));  // 机审通过数/用户注册数
        vo.setWithdrawCountApplyPassRate(CostUtils.divide(new BigDecimal(vo.getWithdrawCount()),
                new BigDecimal(vo.getApplyPass())));    // 放款数/人审通过数
        vo.setWithdrawCountApplyRate(CostUtils.divide(new BigDecimal(vo.getWithdrawCount()),
                new BigDecimal(vo.getTotalApply())));   // 放款数/生成订单数
        model.addAttribute("vo", vo);
        model.addAttribute("applyOP", applyOP);
        return "modules/loan/operationalStatistics";
    }

    /**
     * 现金贷统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "applyCount")
    public String applyCount(ApplyOP applyOP, HttpServletRequest request, Model model) {
        User user = UserUtils.getUser();
        // 定义常量用于区分口袋
        String typeCheck = null;
        if ("KOUDAI".equals(applyOP.getProductId())) {
            typeCheck = "KOUDAI";
            applyOP.setTermType("1");
            applyOP.setProductId(LoanProductEnum.XJD.getId());
            applyOP.setTypeCheck(typeCheck);
        }
        // 设置页码>=30
        if (applyOP != null && applyOP.getPageSize() < 30) {
            applyOP.setPageSize(30);
        }

        if (!StringUtils.isBlank(applyOP.getCheckStart())) {
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));

            // 口袋时间限制 2018-09-26 以后的
            if ("KOUDAI".equals(typeCheck)) {
                applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
                if (DateUtils.isAfter(DateUtils.parseDate("2018-09-26"),
                        DateUtils.parseDate(applyOP.getApplyStart()))) {
                    applyOP.setApplyStart("2018-09-26");
                }
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
            }
        } else {
            if (StringUtils.isBlank(applyOP.getApplyStart())) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
            // 口袋时间限制 2018-09-26 以后的
            if ("KOUDAI".equals(typeCheck) && DateUtils.isAfter(DateUtils.parseDate("2018-09-26"),
                    DateUtils.parseDate(applyOP.getApplyStart()))) {
                applyOP.setApplyStart("2018-09-26");
            }
        }
        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        if (StringUtils.isBlank(applyOP.getChannel())) {
            // applyOP.setChannel(chAllStr.toString());
        }
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);

        String cacheKey1 = "applyCount_1_" + JsonMapper.toJsonString(applyOP);
        String cacheKey2 = "applyCount_2_" + JsonMapper.toJsonString(applyOP);
        String cacheKey3 = "applyCount_3_" + JsonMapper.toJsonString(applyOP);
        if ("15926324431".equals(user.getLoginName())) {
            cacheKey3 = "applyCount_3_" + JsonMapper.toJsonString(applyOP) + "_tushu_account";
        }
        String cacheKey4 = "applyCount_4_" + JsonMapper.toJsonString(applyOP);
        String cacheKey5 = "applyCount_5_" + JsonMapper.toJsonString(applyOP);

        UserApplyCountVO vo = (UserApplyCountVO) JedisUtils.getObject(cacheKey1);
        Double delaySum = (Double) JedisUtils.getObject(cacheKey2);
        Page<UserApplyRepayCountVO> repayCountPage = (Page<UserApplyRepayCountVO>) JedisUtils.getObject(cacheKey3);
        UserApplyCountVO xjbkVO = (UserApplyCountVO) JedisUtils.getObject(cacheKey4);
        Double payAmt = (Double) JedisUtils.getObject(cacheKey5);

        // data1
        if (vo == null) {
            vo = userApplyCountService.getUserApplyCount(applyOP.getApplyStart() + " 00:00:00",
                    applyOP.getApplyEnd() + " 23:59:59", applyOP.getChannel(), applyOP.getProductId(),
                    applyOP.getTermType());
            JedisUtils.setObject(cacheKey1, vo, 10 * 60);
        }
        // data2
        if (delaySum == null) {
            delaySum = userApplyCountService.getDelaySum(applyOP);
            delaySum = (delaySum == null) ? 0 : delaySum;
            JedisUtils.setObject(cacheKey2, delaySum, 10 * 60);
        }
        // data3
        if (repayCountPage == null) {
            // 还款统计区分口袋
            if ("KOUDAI".equals(typeCheck)) {
                repayCountPage = userApplyCountService.getOtherRepayStat(applyOP);
            } else {
                repayCountPage = userApplyCountService.getRepayCount(applyOP);
            }
            JedisUtils.setObject(cacheKey3, repayCountPage, 10 * 60);
        }
        // data4
        if (null == xjbkVO) { // 现金白卡
            xjbkVO = userApplyCountService.getXjbkUserApplyCount(applyOP.getApplyStart() + " 00:00:00",
                    applyOP.getApplyEnd() + " 23:59:59", applyOP.getProductId());
            JedisUtils.setObject(cacheKey4, xjbkVO, 10 * 60);
        }
        // data5
        if (payAmt == null && "KOUDAI".equals(typeCheck)) {
            payAmt = userApplyCountService.getKDPayAmt(applyOP.getApplyStart() + " 00:00:00",
                    applyOP.getApplyEnd() + " 23:59:59");
            payAmt = (payAmt == null) ? 0 : payAmt;
            JedisUtils.setObject(cacheKey5, payAmt, 10 * 60);
        }

        // 处理数据
        if ("15926324431".equals(user.getLoginName())) {
            if (vo != null) {
                vo.setTotalReg(vo.getTotalReg() * 2);
                vo.setTotalApply(vo.getTotalApply() * 2);
                vo.setApplyPass(vo.getApplyPass() * 2);
                vo.setLoanPass(vo.getLoanPass() * 2);
                vo.setLoanPassNew(vo.getLoanPassNew() * 2);
                vo.setLoanPassOld(vo.getLoanPassOld() * 2);
                vo.setLoanReady(vo.getLoanReady() * 2);
                vo.setRaiseCount(vo.getRaiseCount() * 2);
                vo.setRaiseAmt(vo.getRaiseAmt() * 2);
                vo.setWithdrawAmt(vo.getWithdrawAmt() * 2);
                vo.setTotalApplyAmt(vo.getTotalApplyAmt() * 2);
                vo.setTotalLoan(vo.getTotalLoan() * 2);
                vo.setTotalLoanReady(vo.getTotalLoanReady() * 2);
                vo.setTotalRepay(StringUtils.isNotBlank(vo.getTotalRepay())
                        ? String.valueOf(Double.parseDouble(vo.getTotalRepay()) * 2)
                        : "0");
                vo.setTotalApplyAccess(vo.getTotalApplyAccess() * 2);
                vo.setTotalApplyMoney(vo.getTotalApplyMoney() * 2);
            }

            if (repayCountPage != null && repayCountPage.getList() != null) {
                for (UserApplyRepayCountVO countVO : repayCountPage.getList()) {
                    countVO.setTotalNum(StringUtils.isNotBlank(countVO.getTotalNum())
                            ? String.valueOf(Integer.parseInt(countVO.getTotalNum()) * 2)
                            : "0");
                    countVO.setTotalAmt(StringUtils.isNotBlank(countVO.getTotalAmt())
                            ? String.valueOf(Double.parseDouble(countVO.getTotalAmt()) * 2)
                            : "0");
                    countVO.setPayedNum(StringUtils.isNotBlank(countVO.getPayedNum())
                            ? String.valueOf(Integer.parseInt(countVO.getPayedNum()) * 2)
                            : "0");
                    countVO.setPayedAmt(StringUtils.isNotBlank(countVO.getPayedAmt())
                            ? String.valueOf(Double.parseDouble(countVO.getPayedAmt()) * 2)
                            : "0");
                    countVO.setUnpayNum(StringUtils.isNotBlank(countVO.getUnpayNum())
                            ? String.valueOf(Integer.parseInt(countVO.getUnpayNum()) * 2)
                            : "0");
                    countVO.setUnpayAmt(StringUtils.isNotBlank(countVO.getUnpayAmt())
                            ? String.valueOf(Double.parseDouble(countVO.getUnpayAmt()) * 2)
                            : "0");
                    countVO.setDelayNum(StringUtils.isNotBlank(countVO.getDelayNum())
                            ? String.valueOf(Integer.parseInt(countVO.getDelayNum()) * 2)
                            : "0");
                    countVO.setDelayAmt(StringUtils.isNotBlank(countVO.getDelayAmt())
                            ? String.valueOf(Double.parseDouble(countVO.getDelayAmt()) * 2)
                            : "0");
                    countVO.setPrincipal(StringUtils.isNotBlank(countVO.getPrincipal())
                            ? String.valueOf(Double.parseDouble(countVO.getPrincipal()) * 2)
                            : "0");
                    countVO.setPayedPrincipal(StringUtils.isNotBlank(countVO.getPayedPrincipal())
                            ? String.valueOf(Double.parseDouble(countVO.getPayedPrincipal()) * 2)
                            : "0");
                    countVO.setDelayFee(StringUtils.isNotBlank(countVO.getDelayFee())
                            ? String.valueOf(Double.parseDouble(countVO.getDelayFee()) * 2)
                            : "0");
                    countVO.setPartPayAmt(StringUtils.isNotBlank(countVO.getPartPayAmt())
                            ? String.valueOf(Double.parseDouble(countVO.getPartPayAmt()) * 2)
                            : "0");
                }
            }
        }

        model.addAttribute("model", vo);
        model.addAttribute("xmodel", xjbkVO);
        model.addAttribute("delaySum", delaySum);
        model.addAttribute("repayCountPage", repayCountPage);
        model.addAttribute("payAmt", payAmt);
        if ("KOUDAI".equals(typeCheck)) {
            applyOP.setProductId("KOUDAI");
        }
        model.addAttribute("applyOP", applyOP);
        return "modules/loan/applyCount";
    }

    /**
     * 诚诚贷统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "officeCount")
    public String listByOffice(ApplyOP applyOP, HttpServletRequest request, Model model) {

        if (!StringUtils.isBlank(applyOP.getCheckStart())) {
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
        } else {
            if (StringUtils.isBlank(applyOP.getApplyStart())) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
        }

        List<Area> areaList = new ArrayList<Area>();
        List<Area> allList = areaService.getAll();
        if (StringUtils.isBlank(applyOP.getAreaId())) {
            areaList = allList;
        } else {
            if (areaService.getById(applyOP.getAreaId()) != null) {
                areaList.add(areaService.getById(applyOP.getAreaId()));
            }
        }
        // 结果列表
        List<Map<String, Object>> areaResultList = new ArrayList<Map<String, Object>>();
        for (Area area : areaList) {
            Integer count = 0;
            Map<String, Object> areaResultMap = new HashMap<String, Object>();
            List<Map<String, Object>> officeList = userApplyCountService.getOfficeListByArea(area.getId());
            if (null != officeList && officeList.size() > 0) {
                // 放入区域名
                areaResultMap.put("area", area.getName());
                List<Map> officeResultList = new ArrayList<Map>();
                if ("allOffice".equals(applyOP.getTypeCheck())) {

                    if (StringUtils.isBlank(applyOP.getOfficeId())) {
                        // 查询所有区域的所有门店
                        for (Map<String, Object> office : officeList) {
                            Map<String, Object> officeResultMap = new HashMap<String, Object>();
                            // 放入门店名
                            officeResultMap.put("office", office.get("office"));
                            UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                    applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                    "'" + (String) office.get("id") + "'", LoanProductEnum.CCD.getId(), null);
                            officeResultMap.put("officeResult", vo);
                            officeResultList.add(officeResultMap);
                        }
                        areaResultMap.put("areaResult", officeResultList);
                    } else if (StringUtils.isNotBlank(applyOP.getOfficeId())) {
                        // 查询指定区域下指定门店的信息
                        Office office = officeService.get(applyOP.getOfficeId());
                        Map<String, Object> officeResultMap = new HashMap<String, Object>();
                        // 放入门店名
                        officeResultMap.put("office", office.getName());
                        UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                "'" + applyOP.getOfficeId() + "'", LoanProductEnum.CCD.getId(), null);
                        officeResultMap.put("officeResult", vo);
                        officeResultList.add(officeResultMap);
                        areaResultMap.put("areaResult", officeResultList);
                    }

                } else {
                    if (StringUtils.isBlank(applyOP.getOfficeId()) && StringUtils.isBlank(applyOP.getGroupId())) {
                        // 查询所有区域下所有门店下所有组
                        for (Map<String, Object> office : officeList) {
                            Map<String, Object> officeResultMap = new HashMap<String, Object>();
                            // 放入门店名
                            officeResultMap.put("office", office.get("office"));
                            List<Map<String, Object>> groupList = userApplyCountService
                                    .getGroupByOffice(office.get("id").toString());
                            if (null != groupList && groupList.size() > 0) {
                                count += (groupList.size() + 1);
                                List<Map> groupResultList = new ArrayList<Map>();
                                for (Map<String, Object> group : groupList) {
                                    Map<String, Object> groupResultMap = new HashMap<String, Object>();
                                    // 放入组名
                                    groupResultMap.put("groupName", group.get("groupName"));
                                    UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                            applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                            "'" + (String) office.get("id") + "'", LoanProductEnum.CCD.getId(),
                                            "'" + (String) group.get("id") + "'");
                                    groupResultMap.put("groupResult", vo);
                                    groupResultList.add(groupResultMap);
                                }
                                officeResultMap.put("officeResult", groupResultList);
                                officeResultList.add(officeResultMap);
                            }
                        }
                        if (count > 0) {
                            areaResultMap.put("size", count);
                            areaResultMap.put("areaResult", officeResultList);
                        } else {
                            areaResultMap.remove("area");
                        }
                    } else if (StringUtils.isNotBlank(applyOP.getOfficeId())
                            && StringUtils.isBlank(applyOP.getGroupId())) {
                        // 查询指定区域下指定门店所有组
                        Office office = officeService.get(applyOP.getOfficeId());
                        Map<String, Object> officeResultMap = new HashMap<String, Object>();
                        // 放入门店名
                        officeResultMap.put("office", office.getName());
                        List<Map<String, Object>> groupList = userApplyCountService
                                .getGroupByOffice(applyOP.getOfficeId());
                        if (null != groupList && groupList.size() > 0) {
                            count += (groupList.size() + 1);
                            List<Map> groupResultList = new ArrayList<Map>();
                            for (Map<String, Object> group : groupList) {
                                Map<String, Object> groupResultMap = new HashMap<String, Object>();
                                // 放入组名
                                groupResultMap.put("groupName", group.get("groupName"));
                                UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                        applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                        "'" + applyOP.getOfficeId() + "'", LoanProductEnum.CCD.getId(),
                                        "'" + (String) group.get("id") + "'");
                                groupResultMap.put("groupResult", vo);
                                groupResultList.add(groupResultMap);
                            }
                            officeResultMap.put("officeResult", groupResultList);
                            officeResultList.add(officeResultMap);
                        }
                        if (count > 0) {
                            areaResultMap.put("size", count);
                            areaResultMap.put("areaResult", officeResultList);
                        } else {
                            areaResultMap.remove("area");
                        }
                    } else {
                        // 查询指定区域下指定门店指定组
                        Office office = officeService.get(applyOP.getOfficeId());
                        Map<String, Object> officeResultMap = new HashMap<String, Object>();
                        // 放入门店名
                        officeResultMap.put("office", office.getName());

                        Office group = officeService.get(applyOP.getGroupId());
                        count = 2;
                        List<Map> groupResultList = new ArrayList<Map>();
                        Map<String, Object> groupResultMap = new HashMap<String, Object>();
                        // 放入组名
                        groupResultMap.put("groupName", group.getName());
                        UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                "'" + applyOP.getOfficeId() + "'", LoanProductEnum.CCD.getId(),
                                "'" + applyOP.getGroupId() + "'");
                        groupResultMap.put("groupResult", vo);
                        groupResultList.add(groupResultMap);
                        officeResultMap.put("officeResult", groupResultList);
                        officeResultList.add(officeResultMap);
                        areaResultMap.put("size", count);
                        areaResultMap.put("areaResult", officeResultList);
                    }
                }
                areaResultList.add(areaResultMap);
            }
        }
        model.addAttribute("allArea", allList);
        model.addAttribute("list", areaResultList);
        model.addAttribute("applyOP", applyOP);
        return "modules/loan/officeApplyCount";
    }

    /**
     * 导出现金贷统计数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export")
    @ExportLimit
    public String exportFile(ApplyOP applyOP, HttpServletRequest request, Model model, HttpServletResponse response,
                             RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        logger.info("导出运营统计报表数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("还款数据", ApplyCountReportVO.class);
            String fileName = "运营统计报表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            if (!StringUtils.isBlank(applyOP.getCheckStart())) {
                int start = Integer.parseInt(applyOP.getCheckStart());
                applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
            } else {
                if (StringUtils.isBlank(applyOP.getApplyStart())) {
                    applyOP.setApplyStart(DateUtils.getDate());
                }
                if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                    applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
                }
            }
            List<ChannelVO> chList = channelService.findAllChannel();
            StringBuffer chAllStr = new StringBuffer();
            for (ChannelVO ch : chList) {
                chAllStr.append("'").append(ch.getCid()).append("',");
            }
            chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
            if (StringUtils.isBlank(applyOP.getChannel())) {
                applyOP.setChannel(chAllStr.toString());
            }
            model.addAttribute("channels", chList);
            model.addAttribute("allChannel", chAllStr);
            UserApplyCountVO vo = userApplyCountService.getUserApplyCount(applyOP.getApplyStart() + " 00:00:00",
                    applyOP.getApplyEnd() + " 23:59:59", applyOP.getChannel(), LoanProductEnum.XJD.getId(),
                    applyOP.getTermType());

            StringBuffer chStr = new StringBuffer();
            for (ChannelVO ch : chList) {
                if (StringUtils.isBlank(applyOP.getChannel()) || applyOP.getChannel().indexOf(ch.getCid()) >= 0) {
                    chStr.append(ch.getcName()).append(",");
                }
            }
            vo.setChannelStr(chStr.substring(0, chStr.length() - 1));
            excel.setDataList(Arrays.asList(vo)).write(response, fileName);
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出现金贷统计数据失败！失败信息：" + e.getMessage());
        } finally {
            if (excel != null)
                excel.dispose();
        }
        return "modules/loan/applyCount";
    }

    /**
     * 查询区域列表
     */
    @ResponseBody
    @RequestMapping(value = "getAllArea")
    public WebResult getAllArea() {
        try {
            return new WebResult("1", "提交成功", areaService.getAll());
        } catch (Exception e) {
            logger.error("查询区域列表异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 查询门店列表
     */
    @ResponseBody
    @RequestMapping(value = "getOfficeByArea")
    public WebResult getOfficeByArea(String areaId) {
        try {
            return new WebResult("1", "提交成功", userApplyCountService.getOfficeListByArea(areaId));
        } catch (Exception e) {
            logger.error("查询门店列表异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 查询商户列表
     */
    @ResponseBody
    @RequestMapping(value = "getAllCompany")
    public WebResult getAllCompany() {
        try {
            return new WebResult("1", "提交成功", officeService.getAllCompany(null));
        } catch (Exception e) {
            logger.error("查询商户列表异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    /*
     * 业务分配
     */
    @ResponseBody
    @RequestMapping(value = "doAllotment")
    public WebResult doAllotment(@Valid ApplyAssignmentVO vo) {
        User user = UserUtils.getUser();
        logger.info("分配待审核订单--->{}--->{}", user.getId(), user.getName());
        try {
            List<String> list = Arrays.asList(StringUtils.split(vo.getIds(), "|"));
            for (String id : list) {

                loanApplyService.updateCompanyId(vo.getCompanyId(), id);

                ApplyAllotVO apply = loanApplyService.getApplyById(id);
                apply.setStatus(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());// 将状态改为待审核
                apply.setCompanyId(vo.getCompanyId());// 设置门店名称
                ApplyAllotVO allot = applyAllotService.getById(id);
                int flag = 0;
                if (allot != null && allot.getId() != null) {
                    ApplyAllotOP op = new ApplyAllotOP();
                    op.setId(id);
                    op.setStatus(ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK.getValue());// 将状态改为待审核
                    op.setCompanyId(vo.getCompanyId());
                    op.setDel(0);
                    op.setUpdateBy(user.getName());
                    op.setUpdateTime(new Date());
                    flag = applyAllotService.updateAllot(op);

                } else {
                    apply.setDel(0);
                    apply.preUpdate();
                    flag = applyAllotService.insertAllot(apply);
                }
                if (flag < 1) {
                    return new WebResult("99", "系统异常!");
                }

            }
            return new WebResult("1", "提交成功", null);
        } catch (Exception e) {
            logger.error("商户分配异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }

    /*
     * 文件上传
     */
    @ResponseBody
    @RequestMapping(value = "uploadFile/{applyId}")
    public WebResult uploadFile(@PathVariable("applyId") String applyId, HttpServletRequest request,
                                HttpServletResponse response) {
        LoanApplySimpleVO loanvo = loanApplyService.getLoanApplyById(applyId);
        String userId = loanvo.getUserId();
        CustUserVO custUservo = custUserService.getCustUserById(userId);
        String uid = custUservo.getAccountId();
        List<Office> companyList = officeService.getAllCompany(null);
        String companyName = null;
        if (null != companyList) {
            for (Office office : companyList) {
                if (custUservo.getRemark().equals(office.getCompanyId())) {
                    companyName = office.getName();
                }
            }
        }
        Integer borrowType = loanApplyService.getBorrowType(applyId);
        String borrowTypeName = null;
        switch (borrowType) {
            case 1:
                borrowTypeName = "信用";
                break;
            case 2:
                borrowTypeName = "抵押";
                break;
            case 3:
                borrowTypeName = "担保";
                break;
            case 5:
                borrowTypeName = "质押";
                break;
            case 6:
                borrowTypeName = "车保宝";
                break;
            default:
                borrowTypeName = "信贷";
                break;
        }
        String key = null;
        // 进行转换
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
        Iterator<String> it = multiRequest.getFileNames();
        while (it.hasNext()) {
            // 根据文件名称取文件
            MultipartFile file = multiRequest.getFile(it.next());
            if (LoanProductEnum.CCD.getId().equals(loanvo.getProductId())) {
                key = companyName + "+" + custUservo.getRealName() + "+" + loanvo.getApproveAmt() + "+" + borrowTypeName
                        + "+" + loanvo.getTerm() / 2 + "月"
                        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            } else {
                key = companyName + "+" + custUservo.getRealName() + "+" + loanvo.getApproveAmt() + "+" + borrowTypeName
                        + "+" + loanvo.getTerm() + "月"
                        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            try {
                InputStream inputStream = file.getInputStream();
                boolean flag = OOSUtils.uploadBorrowFile(uid, applyId, inputStream, key);
                if (!flag) {
                    return new WebResult("NOK", "上传失败");
                }
            } catch (Exception e) {
                logger.error("上传失败", e);
                return new WebResult("NOK", "上传失败");
            }
            break;
        }
        return new WebResult("OK", "上传成功");
    }

    /*
     * 通讯录文件上传
     */
    @ResponseBody
    @RequestMapping(value = "uploadContactFile/{userId}")
    public WebResult uploadContactFile(@PathVariable("userId") String userId, HttpServletRequest request,
                                       HttpServletResponse response) {
        // 进行转换
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) (request);
        Iterator<String> it = multiRequest.getFileNames();
        while (it.hasNext()) {
            // 根据文件名称取文件
            MultipartFile file = multiRequest.getFile(it.next());
            String tmpdir = System.getProperty("java.io.tmpdir");
            File file1 = new File(tmpdir + "/" + file.getOriginalFilename());
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), file1);
                // 通讯录文件上传
                UploadParams contactFileParams = new UploadParams();
                contactFileParams.setUserId(userId);
                contactFileParams.setOrigName(file.getOriginalFilename());
                contactFileParams.setBizCode(FileBizCode.CONTACT_FILE.getBizCode());
                contactFileParams.setIp(Servlets.getIpAddress(request));
                contactFileParams.setSource("5");
                FileServerClient fileServerClient = new FileServerClient();
                if ("xlsx".equals(ext) || "xls".equals(ext) || "csv".equals(ext) || "pdf".equals(ext)) {
                    fileServerClient.uploadDocument(file1, contactFileParams);
                } else if ("jpeg".equals(ext) || "jpg".equals(ext) || "png".equals(ext) || "bmp".equals(ext)) {
                    fileServerClient.uploadImage(file1, contactFileParams);
                } else {
                    return new WebResult("NOK", "文件类型错误");
                }
            } catch (Exception e) {
                logger.error("上传失败", e);
                return new WebResult("NOK", "上传失败");
            } finally {
                FileUtils.deleteFile(file1.getAbsolutePath());
            }
        }
        return new WebResult("OK", "上传成功");
    }

    /**
     * 删除通讯录附件
     */
    @ResponseBody
    @RequestMapping(value = "deleteContactFile")
    public WebResult deleteContactFile(@RequestParam("id") String id) {
        try {
            int count = custUserService.deleteTruely(id);
            if (count == 0) {
                new WebResult("NOK", "删除失败");
            }
        } catch (Exception e) {
            logger.error("删除通讯录附件异常", e);
            return new WebResult("NOK", "系统异常");
        }
        return new WebResult("OK", "删除成功");
    }

    /**
     * 更改标的附件
     */
    @ResponseBody
    @RequestMapping(value = "enSureUpload")
    public WebResult enSureUpload(String applyId) {
        String photoCacheKey = "BORROW_FILE_CACHE_KEY_" + applyId;
        String url = JedisUtils.get(photoCacheKey);
        String lid = loanApplyService.getOutSideNumByApplyId(applyId);
        if (url != null && lid != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("lid", lid);
            map.put("url", url);
            map.put("remark", "客户资料");
            String uploadPhotoParam = JsonMapper.getInstance().toJson(map);
            logger.info("推标上传图片http请求:{}", uploadPhotoParam);
            // 只推送信息，不做成功判断
            String response = RestTemplateUtils.getInstance()
                    .postForJsonRaw(Global.getConfig("deposit.uploadPhoto.url"), uploadPhotoParam);
            logger.info("推标上传图片http响应:{}", response);

            return new WebResult("OK", "更改附件成功");
        }
        return new WebResult("NOK", "更改附件失败");
    }

    /**
     * 检查标状态
     */
    @ResponseBody
    @RequestMapping(value = "checkLoanStatus")
    public WebResult checkLoanStatus(String applyId) {
        String lid = loanApplyService.getOutSideNumByApplyId(applyId);
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isBlank(lid)) {
            return new WebResult("NOK", "未推标");
        }
        param.put("lid", lid);
        logger.info("查询标状态http请求:{}", applyId);
        Map<String, Object> result = (Map<String, Object>) RestTemplateUtils.getInstance()
                .postForObject(Global.getConfig("deposit.checkBidStatus.url"), param, Map.class);
        logger.info("查询标状态http请求:{}", result);
        if (null != result && result.get("status").equals("OK") && (Boolean) result.get("isCan") == true) {
            return new WebResult("OK", "可以更改附件");
        }
        return new WebResult("NOK", "已推标或标不存在");
    }

    /**
     * 新审核统计 code y0516
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "newAllotCount")
    public String newAllotCount(ApplyOP applyOP, HttpServletRequest request, Model model) {
        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);

        List<User> list = UserUtils.getUserByRole("xinshen");
        if (null != list) {
            model.addAttribute("auditor", list);
        }
        if (StringUtils.isBlank(applyOP.getApplyStart())) {
            applyOP.setApplyStart(DateUtils.getDate());
        }
        if (StringUtils.isBlank(applyOP.getApplyEnd())) {
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
        }

        List<String> userList = new ArrayList<String>();

        List<Map<String, Object>> resultList = null;
        Map<String, Object> resultMap = null;
        if (StringUtils.isBlank(applyOP.getAuditor())) {
            for (User user : list) {
                userList.add(user.getId());
            }
            resultMap = getCheckAllotList(applyOP, userList, list, "ALL");
        } else {
            userList.add(applyOP.getAuditor());
            resultMap = getCheckAllotList(applyOP, userList, list, null);
        }
        resultList = (List<Map<String, Object>>) resultMap.get("officeCountList");
        model.addAttribute("primaryResultTotal", resultMap.get("primaryResultTotal"));
        model.addAttribute("list", resultList);
        model.addAttribute("applyOP", applyOP);
        return "modules/loan/newAllotCount";
    }

    /**
     * 导出已过审数据
     */
    @RequestMapping(value = "exportAuditPass")
    @ExportLimit
    public void exportAuditPass(ApplyOP applyOP, HttpServletRequest request, Model model, HttpServletResponse response,
                                RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        logger.info("导出已过审数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("订单表已过审数据", ApplyAuditPassReportVO.class);
            String fileName = "订单表已过审数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page page = new Page();
            ApplyListOP applyListOP = assemble(applyOP);
            // 查询页面默认的多种状态
            getStatusList(applyListOP, page);
            RoleControlParam roleControlParam = RoleControl.get(applyListOP.getProductId(), applyOP.getCompanyId());
            applyListOP.setProductId(roleControlParam.getProductId());
            applyListOP.setCompanyId(roleControlParam.getCompanyId());
            List<ApplyListVO> result = loanApplyService.getLoanApplyListExport(null, applyListOP);
            List<Office> companyList = officeService.getAllCompany(null);
            for (Office office : companyList) {
                for (ApplyListVO vo : result) {
                    if (vo.getCompanyId() != null) {
                        if (vo.getCompanyId().equals(office.getCompanyId())) {
                            vo.setCompanyName(office.getName());
                        }
                    }
                }
            }
            excel.setDataList(result).write(response, fileName);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出订单表已过审数据失败！失败信息：" + e.getMessage());
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /**
     * 信审员统计 code y0516
     *
     * @param applyOP
     * @param user
     * @return
     */
    private Map<String, Object> getCheckAllotList(ApplyOP applyOP, List<String> userList, List<User> list, String type) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> allUserResult = new ArrayList<Map<String, Object>>();
        if (null == userList || userList.size() == 0) {
            result.put("officeCountList", allUserResult);
            return result;
        }
        // 处理时间
        applyOP.setApplyStart(applyOP.getApplyStart() + " 00:00:00");
        applyOP.setApplyEnd(applyOP.getApplyEnd() + " 23:59:59");
        // 统计结果并处理
        List<Map<String, Object>> userAllotResult = userApplyCountService.getCheckAllotList(userList, applyOP, type);

        // 统计初审员的回款率
//        List<Map<String, Object>> returnRate =
//                loanApplyService.getReturnRate(applyOP.getApplyStart() + " 00:00:00",
//                        applyOP.getApplyEnd() + " 23:59:59", "ALL".equals(type) ? null :
//                                userList.get(0), channel, productId, termType);

        Map<String, Object> userResult = null;
        Map<String, Object> countResult = new HashMap<String, Object>();
        // 合计数据
        BigDecimal apprvTotal = new BigDecimal(0);
        BigDecimal notapprvTotal = new BigDecimal(0);
        BigDecimal sumTotal = new BigDecimal(0);
        BigDecimal withdrawrv = new BigDecimal(0);

        /*
         * BigDecimal backNumALL = new BigDecimal(0); BigDecimal newTotalAll =
         * new BigDecimal(0);
         */
        for (Map<String, Object> map : userAllotResult) {
            apprvTotal = apprvTotal.add(new BigDecimal(map.get("apprv").toString()));
            notapprvTotal = notapprvTotal.add(new BigDecimal(map.get("notapprv").toString()));
            sumTotal = sumTotal.add(new BigDecimal(map.get("total").toString()));
            withdrawrv = withdrawrv.add(new BigDecimal(map.get("withdrawrv").toString()));
        }
        /*
         * for (Map<String, Object> map : returnRate) { for (String user :
         * userList) { if (user.equals((String) map.get("approverId"))) {
         * backNumALL = backNumALL.add((BigDecimal) map.get("backNum"));
         * newTotalAll = newTotalAll.add(BigDecimal.valueOf((Long)
         * map.get("total"))); } } }
         */
        countResult.put("sumTotal", sumTotal);
        countResult.put("withdrawrv", withdrawrv);
        countResult.put("apprvTotal", apprvTotal);
        countResult.put("notapprvTotal", notapprvTotal);
        countResult.put("accessRateTotal", sumTotal.compareTo(BigDecimal.ZERO) == 0 ? "0.00%"
                : apprvTotal.divide(sumTotal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
        countResult.put("withdrawrvTotal", withdrawrv.compareTo(BigDecimal.ZERO) == 0 ? "0.00%"
                : withdrawrv.divide(apprvTotal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
        /*
         * countResult.put( "returnRateTotal",
         * newTotalAll.compareTo(BigDecimal.ZERO) == 0 ? "0.00%" :
         * backNumALL.divide(newTotalAll, 2,
         * BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
         */
        // 合计
        result.put("primaryResultTotal", countResult);
        for (String user : userList) {
            userResult = new HashMap<String, Object>();
            BigDecimal apprv = new BigDecimal(0);
            BigDecimal notapprv = new BigDecimal(0);
            BigDecimal total = new BigDecimal(0);
            BigDecimal withdrawrvCount = new BigDecimal(0);

            /*
             * BigDecimal backNum = new BigDecimal(0); BigDecimal newTotal = new
             * BigDecimal(0);
             */
            String officeName = "";
            String userName = "";
            for (Map<String, Object> map : userAllotResult) {
                if (user.equals((String) map.get("operator_id"))) {
                    apprv = apprv.add((BigDecimal) map.get("apprv"));
                    notapprv = notapprv.add((BigDecimal) map.get("notapprv"));
                    total = total.add(new BigDecimal(map.get("total").toString()));
                    withdrawrvCount = withdrawrvCount.add((BigDecimal) map.get("withdrawrv"));
                }
                if (user.equals((String) map.get("approver_id"))) {
                    apprv = apprv.add((BigDecimal) map.get("apprv"));
                    notapprv = notapprv.add((BigDecimal) map.get("notapprv"));
                    total = total.add(new BigDecimal(map.get("total").toString()));
                    withdrawrvCount = withdrawrvCount.add((BigDecimal) map.get("withdrawrv"));
                }
                if (user.equals((String) map.get("approver_id")) && user.equals((String) map.get("operator_id"))) {
                    apprv = apprv.subtract((BigDecimal) map.get("apprv"));
                    notapprv = notapprv.subtract((BigDecimal) map.get("notapprv"));
                    total = total.subtract(new BigDecimal(map.get("total").toString()));
                    withdrawrvCount = withdrawrvCount.subtract((BigDecimal) map.get("withdrawrv"));
                }
            }
            /*
             * for (Map<String, Object> map : returnRate) { if
             * (user.equals((String) map.get("approverId"))) { backNum =
             * backNum.add((BigDecimal) map.get("backNum")); newTotal =
             * newTotal.add(BigDecimal.valueOf((Long) map.get("total"))); } }
             */
            for (User users : list) {
                if (user.equals(users.getId())) {
                    officeName = users.getCompanyId();
                    userName = users.getName();
                }
            }
            userResult.put("AuditorName", userName);
            userResult.put("officeName", officeName);
            userResult.put("total", total);
            userResult.put("apprv", apprv);
            userResult.put("notapprv", notapprv);
            userResult.put("withdrawrvCount", withdrawrvCount);
            userResult.put("withdrawrvCountTotal", withdrawrvCount.compareTo(BigDecimal.ZERO) == 0 ? "0.00%"
                    : withdrawrvCount.divide(apprv, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
            userResult.put("accessRate", total.compareTo(BigDecimal.ZERO) == 0 ? "0.00%"
                    : apprv.divide(total, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
            /*
             * userResult.put( "returnRate", newTotal.compareTo(BigDecimal.ZERO)
             * == 0 ? "0.00%" : backNum.divide(newTotal, 2,
             * BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) + "%");
             */
            allUserResult.add(userResult);
        }
        result.put("officeCountList", allUserResult);
        return result;
    }

    /**
     * 重新审核
     */
    @ResponseBody
    @RequestMapping(value = "resetcheck")
    public WebResult resetcheck(@RequestParam(value = "applyId") String applyId) {
        try {
            synchronized (ApplyController.class) {
                loanApplyService.updateResetCheck(applyId);
                // 释放订单
                JedisUtils.del(APPROVE_LOCK_KEY_PREFIX + applyId);

                JedisUtils.set("approve_apply_" + applyId, loanApplyService.getBaseLoanApplyById(applyId).getUserId(),
                        Global.THREE_DAY_CACHESECONDS);// 加入审批列表
            }
            return new WebResult("1", "提交成功", null);
        } catch (Exception e) {
            logger.error("重新审核异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 取消
     */
    @ResponseBody
    @RequestMapping(value = "cancel")
    public WebResult cancel(@RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("取消待推送订单--->{}--->{}", user.getId(), user.getName());
        try {
            synchronized (ApplyController.class) {
                loanApplyService.updateCancel(applyId, user.getName());
            }
            return new WebResult("1", "提交成功", null);
        } catch (Exception e) {
            logger.error("取消异常：applyId = " + applyId, e);
            return new WebResult("99", "取消异常");
        }
    }

    /**
     * 手动放款--往borrow_info 中插入数据
     */
    @ResponseBody
    @RequestMapping(value = "handelExtend")
    public WebResult handelExtend(@RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("手动放款--->{}--->{}", user.getId(), user.getName());
        try {
            synchronized (ApplyController.class) {
                int flag = loanApplyService.handelExtend(applyId);
                if (flag == 10) {
                    return new WebResult("10", "borrow_info中已经存在", null);
                } else if (flag == 11) {
                    return new WebResult("11", "贷款申请订单不存在", null);
                } else if (flag == 12) {
                    return new WebResult("12", "用户未绑卡", null);
                } else if (flag == 13) {
                    return new WebResult("13", "订单状态不正确(不等于410)", null);
                }
            }
            return new WebResult("1", "提交成功", null);
        } catch (Exception e) {
            logger.error("取消异常：applyId = " + applyId, e);
            return new WebResult("99", "取消异常");
        }
    }

    /**
     * 资信云报告
     */
    @ResponseBody
    @RequestMapping(value = "reportData")
    public WebResult reportData(@RequestParam(value = "applyId") String applyId,
                                @RequestParam(value = "type") Integer type) {
        try {
            String cacheKey = "apply_baiqishi_report_" + applyId + "_" + type;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getReportData(applyId, type);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", JsonMapper.fromJsonString(result, Map.class));
        } catch (Exception e) {
            logger.error("资信云报告异常：applyId = " + applyId, e);
            return new WebResult("99", "资信云报告异常");
        }
    }

    /**
     * 审批预览
     */
    @ResponseBody
    @RequestMapping(value = "approveView")
    public WebResult approveView(@RequestParam(value = "applyId") String applyId,
                                 @RequestParam(value = "userId") String userId, @RequestParam(value = "userName") String userName,
                                 @RequestParam(value = "mobile") String mobile) {
        try {

            String cacheKey = "apply_approve_view_" + userId + "_" + applyId;
            Map<String, Object> result = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (result == null) {
                result = new HashMap<String, Object>();
                List<RepayItemDetailVO> repayItemDetailList = loanRepayPlanService.findUserRepayList(userId);
                ContactHistoryOP contactHistoryOP = new ContactHistoryOP();
                contactHistoryOP.setApplyId(applyId);
                contactHistoryOP.setUserId(userId);
                contactHistoryOP.setUserName(userName);
                contactHistoryOP.setMobile(mobile);
                List<ContactHistoryVO> contactHistoryList = contactHistoryService
                        .removeDuplicateQuery(contactHistoryOP);
                result.put("repayItemDetailList", repayItemDetailList);
                result.put("contactHistoryList", contactHistoryList);
                result.put("adminPath", adminPath);

                JedisUtils.setObject(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", result);
        } catch (Exception e) {
            logger.error("审批预览异常：userId = " + userId, e);
            return new WebResult("99", "审批预览异常");
        }
    }

    /**
     * 导出合同
     *
     * @throws IOException
     */
    @RequestMapping("/exportContractWord")
    public void exportContractWord(String contNo, String type, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> dataMap = repayPlanItemService.getRepayDetailByContNo(contNo);
        Configuration configuration = new Configuration();
        String templateFolder = this.getClass().getClassLoader().getResource("../../").getPath()
                + "WEB-INF/classes/templates/modules/ftl/";
        configuration.setDefaultEncoding("utf-8");
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        Template template = null;
        String fileName = null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
            if ("1".equals(type)) {
                template = configuration.getTemplate("contract.ftl");
                fileName = "借款合同" + contNo + ".doc";
            } else {
                template = configuration.getTemplate("agreement.ftl");
                fileName = "借款协议" + contNo + ".doc";
            }
            // 生成Word文档
            file = createDoc(dataMap, template, contNo);
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-msdown");
            response.setHeader("Content-Disposition",
                    "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete();
        }
    }

    private File createDoc(Map<String, Object> dataMap, Template template, String contNo) {
        String tmpdir = System.getProperty("java.io.tmpdir") + "/" + contNo + ".doc";
        File f = new File(tmpdir);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    /**
     * 查询门店下组列表
     */
    @ResponseBody
    @RequestMapping(value = "getGroupByOffice")
    public WebResult getGroupByOffice(String officeId) {
        try {
            return new WebResult("1", "提交成功", userApplyCountService.getGroupByOffice(officeId));
        } catch (Exception e) {
            logger.error("查询门店列表异常", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * code y0516
     *
     * @param @param applyOP
     * @param @param model
     * @return String 返回类型
     * @Title: newAllotManage
     * @Description: 信审人员列表
     */
    @RequestMapping(value = "/newAllotManage")
    public String newAllotManage(@ModelAttribute(value = "applyOP") ApplyOP applyOP, Model model) {
        if (StringUtils.isBlank(applyOP.getCompanyId())) {
            applyOP.setCompanyId("XJD");// 只显示聚宝钱包信审人员
        }
        // 获取信审员
        List<User> list = UserUtils.getUserByRole("xinshen");
        List<String> userList = null;
        // 根据查询条件applyOP组合userList
        if (StringUtils.isBlank(applyOP.getCompanyId()) && StringUtils.isBlank(applyOP.getAuditor())) {
            userList = new ArrayList<String>();
            for (User user : list) {
                userList.add(user.getId());
            }
        } else if (StringUtils.isNotBlank(applyOP.getCompanyId()) && StringUtils.isBlank(applyOP.getAuditor())) {
            userList = new ArrayList<String>();
            for (User user : list) {
                if (null != user.getCompanyId() && user.getCompanyId().equals(applyOP.getCompanyId())) {
                    userList.add(user.getId());
                    if (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                            || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq")) {
                        userList.remove(user.getId());
                    }
                } else if (applyOP.getCompanyId().equals(LoanProductEnum.XJD.getId())) {
                    if (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                            || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq")) {
                        userList.add(user.getId());
                    }
                }
            }
        } else if (StringUtils.isNotBlank(applyOP.getCompanyId()) && StringUtils.isNotBlank(applyOP.getAuditor())) {
            User user = systemService.getUser(applyOP.getAuditor());
            userList = new ArrayList<String>();
            if (user.getCompany().getId().equals(applyOP.getCompanyId())) {
                userList.add(user.getId());
            } else if (applyOP.getCompanyId().equals(LoanProductEnum.XJD.getId())
                    && (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                    || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq"))) {
                userList.add(user.getId());
            }
        } else {
            userList = new ArrayList<String>();
            userList.add(applyOP.getAuditor());
        }
        List<String> primaryUserList = new ArrayList<String>();// 初审权限信审员
        List<String> finalUserList = new ArrayList<String>();// 终审权限信审员
        for (String userId : userList) {
            if (UserUtils.haveRole(UserUtils.get(userId), "primary")) {
                primaryUserList.add(userId);
            } else {
                finalUserList.add(userId);
            }
        }
        List<User> primaryUsers = systemService.getUserListByUserIds(primaryUserList);
        for (User user : primaryUsers) {
            if (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                    || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq")) {
                user.setCompanyId(null);
            }
        }
        List<User> finalUsers = systemService.getUserListByUserIds(finalUserList);
        for (User user : finalUsers) {
            if (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                    || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq")) {
                user.setCompanyId(null);
            }
        }
        model.addAttribute("primaryUsers", primaryUsers);
        model.addAttribute("finalUsers", finalUsers);
        return "modules/loan/newAllotManage";
    }

    /**
     * code y0516
     *
     * @param @param userId
     * @return WebResult 返回类型
     * @Title: allotPrimaryAuthorization
     * @Description: 信审人员初审权限管理
     */
    @RequestMapping(value = "/switchAllotManage")
    @ResponseBody
    public WebResult allotPrimaryAuthorization(@RequestParam(value = "userId") String userId) {
        User user = UserUtils.get(userId);
        try {
            List<Role> roleList = systemService.getRoleListByUserId(userId);
            Role role = systemService.getRoleByEnname("primary");
            if (UserUtils.haveRole(user, "primary")) {
                roleList.remove(role);
            } else {
                roleList.add(role);
            }
            user.setRoleList(roleList);
            systemService.saveUser(user);
            return new WebResult("1", "操作成功");
        } catch (Exception e) {
            logger.error("初审权限操作异常userId=" + userId, e);
            return new WebResult("99", "操作异常");
        }
    }

    /**
     * code y0516
     *
     * @param @param  companyId
     * @param @return 参数
     * @return WebResult 返回类型
     * @Title: getAuditorByCompanyId
     * @Description: 根据companyId获取信审人员
     */
    @ResponseBody
    @RequestMapping(value = "/getAuditorByCompanyId")
    public WebResult getAuditorByCompanyId(@RequestParam(value = "companyId") String companyId) {
        List<User> auditor = new ArrayList<User>();
        List<User> list = UserUtils.getUserByRole("xinshen");
        // 全部信审人员
        if (StringUtils.isBlank(companyId)) {
            return new WebResult("1", "提交成功", list);
        }
        // 聚宝钱包信审人员
        if ("XJD".equals(companyId)) {
            for (User user : list) {
                if (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                        || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq")) {
                    auditor.add(user);
                }
            }
            return new WebResult("1", "提交成功", auditor);
        }
        // 其他
        for (User user : list) {
            if (companyId.equals(user.getCompanyId())) {
                auditor.add(user);
            }
        }
        return new WebResult("1", "提交成功", auditor);
    }

    /**
     * 魔蝎信用卡报告
     */
    @ResponseBody
    @RequestMapping(value = "getCreditcardReport")
    public WebResult getCreditcardReport(@RequestParam(value = "userId") String userId) {
        try {
            CreditcardReportVO vo = reportService.getMoxieCreditcardReport(userId);
            return new WebResult("1", "提交成功", vo);
        } catch (Exception e) {
            logger.error("信用卡报告异常：userId = " + userId, e);
            return new WebResult("99", "信用卡报告异常");
        }
    }

    /**
     * 逾期统计
     *
     * @param op
     * @param model
     * @return
     */
    @RequestMapping(value = "overdueCount")
    public String overdueCount(OverdueCountOP op, Model model) {
        model.addAttribute("overdueCountOP", op);
        model.addAttribute("page", overdueCountService.overdueCountList(op));
        return "modules/loan/overdueCount";
    }

    /**
     * 产品统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "officeProductCount")
    public String officeProductCount(ApplyOP applyOP, HttpServletRequest request, Model model) {
        Integer sumTotalReg = 0;
        Integer sumTotalApplyAccess = 0;
        double sumTotalApplyMoney = 0;
        Integer sumLoanPass = 0;
        double sumTotalLoan = 0;
        double sumTotalRepay = 0;
        double sumOverDueAmt = 0;
        List<Map<String, Object>> areaResultList = new ArrayList<Map<String, Object>>();

        if (!(StringUtils.isBlank(applyOP.getProductId()) && StringUtils.isBlank(applyOP.getApplyStart())
                && StringUtils.isBlank(applyOP.getApplyEnd()))) {
            if (!StringUtils.isBlank(applyOP.getCheckStart())) {
                int start = Integer.parseInt(applyOP.getCheckStart());
                applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
            } else {
                if (StringUtils.isBlank(applyOP.getApplyStart())) {
                    applyOP.setApplyStart(DateUtils.getDate());
                }
                if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                    applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
                }
            }
            LoanProductEnum[] pro = null;
            LoanProductEnum[] proo = RoleControlUtils.getProductList(); // 通过枚举取产品信息
            LoanProductEnum[] pro1 = {LoanProductEnum.get(applyOP.getProductId())};

            if (StringUtils.isBlank(applyOP.getProductId())) {
                pro = proo;
            } else {
                pro = pro1;
            }

            // 结果列表

            for (LoanProductEnum product : pro) {
                Integer count = 0;
                Map<String, Object> areaResultMap = new HashMap<String, Object>();
                List<Map<String, Object>> officeList = userApplyCountService.getOfficeListByProductId(product.getId());
                if (officeList == null || officeList.size() == 0) { // 如果没有门店就默认将产品名称放入门店
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("office", product.getName());
                    map.put("id", product.getId());
                    officeList.add(map);
                }

                if (null != officeList && officeList.size() > 0) {
                    // 放入区域名
                    areaResultMap.put("product", product.getName());
                    List<Map> officeResultList = new ArrayList<Map>();

                    if (StringUtils.isBlank(applyOP.getOfficeId())) {
                        // 查询所有产品的所有门店
                        for (Map<String, Object> office : officeList) {
                            Map<String, Object> officeResultMap = new HashMap<String, Object>();
                            // 放入门店名
                            officeResultMap.put("office", office.get("office"));

                            String offid = (String) office.get("id");
                            UserApplyCountVO vo = new UserApplyCountVO();
                            if (offid.equals(product.getId())) { // 产品ID等于门店ID说明本产品没有门店根据产品ID查询
                                vo = userApplyCountService.getProductCountByOffice(
                                        applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                        null, product.getId(), null);
                            } else {
                                vo = userApplyCountService.getProductCountByOffice(
                                        applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                        "'" + (String) office.get("id") + "'", product.getId(), null);
                            }

                            // 在此处做合计
                            if (!StringUtils.isBlank(applyOP.getProductId())) { // 如果是根据产品查询的注册人数不做处理
                                sumTotalReg += vo.getTotalReg();
                            } else { // 如果不是做查询现金带分期跟聚宝只合计一个。
                                if (!product.getId().equals("XJD")) {
                                    sumTotalReg += vo.getTotalReg();
                                } else {
                                    sumTotalReg += 0;
                                }
                            }
                            sumTotalApplyAccess += vo.getTotalApplyAccess();
                            sumTotalApplyMoney += vo.getTotalApplyMoney();
                            sumLoanPass += vo.getLoanPass();
                            sumTotalLoan += vo.getTotalLoan();
                            sumTotalRepay += Double.valueOf(vo.getTotalRepay());
                            sumOverDueAmt += Double.valueOf(vo.getOverDueAmt());

                            officeResultMap.put("officeResult", vo);
                            officeResultList.add(officeResultMap);
                        }
                        areaResultMap.put("areaResult", officeResultList);
                    } else if (StringUtils.isNotBlank(applyOP.getOfficeId())) {
                        // 查询指定区域下指定门店的信息
                        Office office = officeService.get(applyOP.getOfficeId());
                        Map<String, Object> officeResultMap = new HashMap<String, Object>();
                        // 放入门店名
                        officeResultMap.put("office", office.getName());
                        UserApplyCountVO vo = userApplyCountService.getUserCountByOffice(
                                applyOP.getApplyStart() + " 00:00:00", applyOP.getApplyEnd() + " 23:59:59",
                                "'" + applyOP.getOfficeId() + "'", applyOP.getProductId(), null);
                        officeResultMap.put("officeResult", vo);
                        officeResultList.add(officeResultMap);
                        areaResultMap.put("areaResult", officeResultList);
                    }

                    areaResultList.add(areaResultMap);
                }
            }

        } else {
            if (!StringUtils.isBlank(applyOP.getCheckStart())) {
                int start = Integer.parseInt(applyOP.getCheckStart());
                applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
            } else {
                if (StringUtils.isBlank(applyOP.getApplyStart())) {
                    applyOP.setApplyStart(DateUtils.getDate());
                }
                if (StringUtils.isBlank(applyOP.getApplyEnd())) {
                    applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
                }
            }
        }
        model.addAttribute("list", areaResultList);
        model.addAttribute("applyOP", applyOP);

        model.addAttribute("sumTotalReg", sumTotalReg);
        model.addAttribute("sumTotalApplyAccess", sumTotalApplyAccess);
        model.addAttribute("sumTotalApplyMoney", sumTotalApplyMoney);
        model.addAttribute("sumLoanPass", sumLoanPass);
        model.addAttribute("sumTotalLoan", sumTotalLoan);
        model.addAttribute("sumTotalRepay", sumTotalRepay);
        model.addAttribute("sumOverDueAmt", sumOverDueAmt);

        return "modules/loan/officeProductCount";
    }

    /**
     * 根据产品查询门店列表
     */
    @ResponseBody
    @RequestMapping(value = "getOfficeByProduct")
    public WebResult getOfficeByProduct(String productId) {
        if (StringUtils.isNotBlank(productId)) {
            return new WebResult("1", "提交成功", userApplyCountService.getOfficeListByProductId(productId));
        } else {
            return new WebResult("1", "提交成功");
        }
    }

    /**
     * 现金白卡报告
     */
    @ResponseBody
    @RequestMapping(value = "xianJinCardData")
    public WebResult xianJinCardData(@RequestParam(value = "applyId") String applyId) {
        try {
            String cacheKey = "xjbk_apply_report_" + applyId;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getXianJinCardData(applyId);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", JsonMapper.fromJsonString(result, Map.class));
        } catch (Exception e) {
            logger.error("现金卡报告异常：applyId = " + applyId, e);
            e.printStackTrace();
            return new WebResult("99", "现金卡报告异常");
        }
    }

    /**
     * @param @param applyId
     * @Title: rong360Data
     * @Description: 融360运营商报告
     */
    @ResponseBody
    @RequestMapping(value = "/rongData")
    public WebResult rong360Data(@RequestParam(value = "applyId") String applyId) {
        try {
            String cacheKey = "rong_apply_report_" + applyId;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getRong360Data(applyId);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", result);
        } catch (Exception e) {
            logger.error("融天机运营商报告异常：applyId = " + applyId, e);
            return new WebResult("99", "融天机运营商报告异常");
        }
    }

    /**
     * 30产品还款统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "xjdThirtyCount")
    public String xjdThirtyCount(ApplyOP applyOP, HttpServletRequest request, Model model) {

        // 设置页码>=30
        if (applyOP != null && applyOP.getPageSize() < 30) {
            applyOP.setPageSize(30);
        }
        if (StringUtils.isBlank(applyOP.getTermType())){
            applyOP.setTermType("one");
        }
        if (StringUtils.isNotBlank(applyOP.getCheckStart())) {
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
        } else {
            if (StringUtils.isBlank(applyOP.getApplyStart())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyStart() + " 00:00:00"))) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyEnd() + " 23:59:59"))) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
        }

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);

        Page thirtyRepayCount = userApplyCountService.getThirtyRepayCount(applyOP);
        model.addAttribute("thirtyRepayCount", thirtyRepayCount);
        return "modules/loan/xjdThirtyCount";
    }

    /**
     * 信审30产品还款统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "approverRepayCount")
    public String approverRepayCount(ApplyOP applyOP, Model model) {
        List<User> list = UserUtils.getUserByRole("xinshen");
        if (null != list) {
            model.addAttribute("auditor", list);
        }

        // 设置页码>=30
        if (applyOP != null && applyOP.getPageSize() < 30) {
            applyOP.setPageSize(30);
        }
        if (StringUtils.isNotBlank(applyOP.getCheckStart())) {
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
        } else {
            if (StringUtils.isBlank(applyOP.getApplyStart())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyStart() + " 00:00:00"))) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyEnd() + " 23:59:59"))) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
        }

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);
        model.addAttribute("applyOP", applyOP);
        Page approverRepayCount = userApplyCountService.approverRepayCount(applyOP);
        model.addAttribute("approverRepayCount", approverRepayCount);
        return "modules/loan/approverRepayCount";
    }

    /**
     * @Title: cancelCoupon @Description: 取消卡券 @param applyId @return
     * 设定文件 @return String 返回类型 @throws
     */
    @ResponseBody
    @RequestMapping(value = "cancelCoupon")
    public WebResult cancelCoupon(@RequestParam(value = "applyId") String applyId) {
        User user = UserUtils.getUser();
        logger.info("取消卡券--->{}--->{}", user.getId(), user.getName());
        try {
            custCouponService.cancelCoupon(applyId);
            return new WebResult("1", "取消成功", null);
        } catch (Exception e) {
            logger.error("取消异常：applyId = " + applyId, e);
            return new WebResult("99", "取消异常");
        }
    }

    /**
     * 审核页面的保存按钮
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCheck")
    public WebResult saveCheck(@Valid CheckOP checkOP, @RequestParam(value = "sourse", required = false) String sourse, // 来源
                               HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (isApproveLocked(checkOP.getApplyId())) {
            addMessage(redirectAttributes, "此订单正在审核中...");
            return new WebResult("99", "此订单正在审核中...");
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
        op.setApproveTerm(applySimpleVO.getApproveTerm());
        op.setRepayTerm(applySimpleVO.getTerm());
        op.setBorrowType(8);// 现金分期
        op.setProductId(applySimpleVO.getProductId());

        Date now = new Date();
        User user = UserUtils.getUser();
        // 插入审核日志
        loanApplyService.saveApproveLogLatter(op, now, false, user.getId(), user.getName(), checkOP.getApplyId());

        // 点击保存后立刻可以点击新单,删除锁
        removeApproveSpeedLock();

        if (StringUtils.isNotBlank(sourse)) {
            return new WebResult("1", "操作成功", adminPath + "/loan/apply/checkFrom?id=" + applySimpleVO.getUserId()
                    + "&sign=detail&applyId=" + checkOP.getApplyId());
        }

        return new WebResult("1", "操作成功", null);
    }

    @ResponseBody
    @RequestMapping(value = "jdqData")
    public WebResult jdqData(@RequestParam(value = "applyId") String applyId) {
        try {
            String cacheKey = "apply_jdq_report_" + applyId;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getjdqData(applyId);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", JsonMapper.fromJsonString(result, Map.class));
        } catch (Exception e) {
            logger.error("借点钱报告异常：applyId = " + applyId, e);
            e.printStackTrace();
            return new WebResult("99", "借点钱报告异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "dwdData")
    public WebResult dwdData(@RequestParam(value = "applyId") String applyId) {
        try {
            String cacheKey = "apply_dwd_report_" + applyId;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getdwdData(applyId);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", JsonMapper.fromJsonString(result, Map.class));
        } catch (Exception e) {
            logger.error("大王贷报告异常：applyId = " + applyId, e);
            e.printStackTrace();
            return new WebResult("99", "大王贷报告异常");
        }
    }

    /**
     * 放款统计
     *
     * @param applyOP
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getPayCount")
    public String getPayCount(KDPayCountOP applyOP, HttpServletRequest request, Model model) {

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);
        // 设置页码>=30
        if (applyOP != null && applyOP.getPageSize() < 30) {
            applyOP.setPageSize(30);
        }
        if (StringUtils.isNotBlank(applyOP.getCheckStart())) {
            int start = Integer.parseInt(applyOP.getCheckStart());
            applyOP.setApplyStart(DateUtils.formatDate(DateUtils.addDay(new Date(), start)));
            applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), start == -1 ? -1 : 0)));
        } else {
            if (StringUtils.isBlank(applyOP.getApplyStart())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyStart() + " 00:00:00"))) {
                applyOP.setApplyStart(DateUtils.getDate());
            }
            if (StringUtils.isBlank(applyOP.getApplyEnd())
                    || DateUtils.isBefore(new Date(), DateUtils.parse(applyOP.getApplyEnd() + " 23:59:59"))) {
                applyOP.setApplyEnd(DateUtils.formatDate(DateUtils.addDay(new Date(), 0)));
            }
        }
        if (StringUtils.isBlank(applyOP.getStatus())) {
            applyOP.setStatus("1");
        }
        Page thirtyRepayCount = new Page();

        if (StringUtils.isNotBlank(applyOP.getStatus()) && (!applyOP.getStatus().equals("1"))) {
            thirtyRepayCount = payLogService.getBFPayCount(applyOP);
        } else {
            thirtyRepayCount = kdPayService.getPayCount(applyOP);

        }

        model.addAttribute("applyOP", applyOP);
        model.addAttribute("thirtyRepayCount", thirtyRepayCount);
        return "modules/loan/payLogCount";
    }

    @ResponseBody
    @RequestMapping(value = "sllData")
    public WebResult sllData(@RequestParam(value = "applyId") String applyId) {
        try {
            String cacheKey = "apply_sll_report_" + applyId;
            String result = JedisUtils.get(cacheKey);
            if (result == null) {
                result = reportService.getsllData(applyId);
                JedisUtils.set(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", JsonMapper.fromJsonString(result, Map.class));
        } catch (Exception e) {
            logger.error("360报告异常：applyId = " + applyId, e);
            e.printStackTrace();
            return new WebResult("99", "360报告异常");
        }
    }

    /**
     * 审核 页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "approval")
    public String approval(@RequestParam(value = "id") String id, @RequestParam(value = "sign") String sign,
                           @RequestParam(value = "applyId") String applyId,
                           @RequestParam(value = "flag", required = false) String flag,
                           @RequestParam(value = "sourse", required = false) String sourse, // 来源
                           Model model, RedirectAttributes redirectAttributes) {
        String lockKey = "loan_apply_checkform_lock_" + applyId;
        String requestId = String.valueOf(System.nanoTime());// 请求标识
        if (StringUtils.equals(sign, "check") && StringUtils.isBlank(sourse)) {
            boolean lock = JedisUtils.setLock(lockKey, requestId, 5);
            if (!lock) {
                addMessage(redirectAttributes, "此订单正在审核中...");
                return "redirect:" + adminPath + "/loan/apply/list";
            }
        }
        try {
            QueryUserOP queryUserOP = new QueryUserOP();
            queryUserOP.setId(id);
            queryUserOP.setApplyId(applyId);
            queryUserOP.setSnapshot(true);
            QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
            model.addAttribute("vo", vo);
            model.addAttribute("flag", flag);
            model.addAttribute("applyId", applyId);
            model.addAttribute("sign", sign);
            model.addAttribute("sourse", sourse);

            LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);
            model.addAttribute("productId", applySimpleVO.getProductId());
            model.addAttribute("applyInfo", applySimpleVO);
            model.addAttribute("callCount", applySimpleVO.getCallCount());
            model.addAttribute("processStatus", applySimpleVO.getProcessStatus());

            if (applySimpleVO.getProcessStatus() != 220) {// 非待审核状态，移除审批队列，跳转到下一单
                JedisUtils.del("approve_apply_" + applyId);// 删除审批队列订单
                return approvalNext(model, redirectAttributes);
            }

            if (StringUtils.equals(sign, "check")) {
                if (UserUtils.haveRole("superRole")) {
                    model.addAttribute("options", refuseReasonService.findAll());
                } else {
                    if (isApproveLocked(applyId)) {
                        addMessage(redirectAttributes, "此订单正在审核中...");
                        return "redirect:" + adminPath + "/loan/apply/list";
                    } else {
                        model.addAttribute("options", refuseReasonService.findAll());
                        addApproveLock(applyId);
                        setApproveCount(applyId);
                    }
                }
            }

            model.addAttribute("userId", id);

            return "/modules/cust/approval/userView";
        } finally {
            // 解除orderNo并发锁
            JedisUtils.releaseLock(lockKey, requestId);
        }
    }

    /**
     * 借款人详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "approvalUserDetail", method = RequestMethod.POST)
    public ApiResult approvalUserDetail(@RequestParam(value = "userId") String userId,
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
    @RequestMapping(value = "approvalContactConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult approvalContactConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                                @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "approvalContactConnectInfo_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            // data = null;//临时移除缓存
            if (data == null) {
                data = new HashMap();
                LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);

                List<CallConnectVO> callInCntList = new ArrayList();
                List<CallConnectVO> callOutCntList = new ArrayList();

                List<ContactCheck> contactChecks = new ArrayList<ContactCheck>();

                if (applyTripartiteService.isExistApplyId(applyId)) {
                    // return "modules/cust/XianJinCardUserView";
                    XianJinBaiKaCommonOP xianJinBaiKaAdditional = xianJinBaiKaService.getXianJinBaiKaAdditional(userId);
                    XianJinBaiKaCommonOP xianJinBaiKaBase = xianJinBaiKaService.getXianJinBaiKaBase(userId);
                    List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook()
                            .getPhoneList();
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

                } else if (("RONG".equals(applySimpleVO.getChannelId()) && "4".equals(applySimpleVO.getSource()))
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

                    // return "modules/cust/rongUserView";
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
                    if (ccmList != null) {
                        for (Map<String, Object> ccmIteam : ccmList) {
                            callInCnt = new CallConnectVO();
                            callInCnt.setContactName((String) ccmIteam.get("contactName"));
                            callInCnt.setPhone((String) ccmIteam.get("mobile"));
                            callInCnt.setPhoneLocation((String) ccmIteam.get("belongTo"));
                            callInCnt.setCalledCnt((Integer) ccmIteam.get("terminatingCallCount"));
                            callInCnt.setCalledSeconds((Integer) ccmIteam.get("terminatingTime"));
                            callInCntList.add(callInCnt);
                        }
                    }

                    ccmList = (List) reportData.get("ccmList2");// 呼出
                    if (ccmList != null) {
                        for (Map<String, Object> ccmIteam : ccmList) {
                            callInCnt = new CallConnectVO();
                            callInCnt.setContactName((String) ccmIteam.get("contactName"));
                            callInCnt.setPhone((String) ccmIteam.get("mobile"));
                            callInCnt.setPhoneLocation((String) ccmIteam.get("belongTo"));
                            callInCnt.setCallCnt((Integer) ccmIteam.get("originatingCallCount"));
                            callInCnt.setCallSeconds((Integer) ccmIteam.get("originatingTime"));
                            callOutCntList.add(callInCnt);
                        }
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

                    // return "modules/cust/userView";

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

    /**
     * 审核
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "approve")
    public ApiResult approve(@Valid CheckOP checkOP, HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        User user = UserUtils.getUser();
        // checkOP.setOperatorId(user.getId());
        // checkOP.setOperatorName(user.getName());

        if (isApproveLocked(checkOP.getApplyId())) {
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
        if (UserUtils.haveRole("primary")) {
            op.setLevel(true); // 初级信审
        }

        op.setOperatorId(user.getId());
        op.setOperatorName(user.getName());
        op.setIp(Servlets.getIpAddress(request));
        synchronized (ApplyController.class) {
            loanApplyService.approve(op);
            removeApproveCount(op.getApplyId());
            removeApproveSpeedLock();
            JedisUtils.set(APPROVE_LOCK_KEY_PREFIX + op.getApplyId(), "1", 30);

            JedisUtils.del("approve_apply_" + op.getApplyId());// 删除审批队列订单
        }

        return new ApiResult("1", "提交成功", null);
    }

    /**
     * 下一个审核 页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "approvalNext")
    public String approvalNext(Model model, RedirectAttributes redirectAttributes) {

        Set<String> approveSet = JedisUtils.getKeys("approve_apply_", 2);
        if (approveSet == null || approveSet.size() == 0) {
            addMessage(redirectAttributes, "无审核中订单...");
            return "redirect:" + adminPath + "/loan/apply/list";
        }
        String nextId = null;
        String nextUserId = null;
        for (String key : approveSet) {
            if (!isApproveLocked(key)) {// 订单被锁定
                nextId = key.replace("approve_apply_", "");
                nextUserId = JedisUtils.get(key);
                break;
            }
        }
        if (nextUserId == null) {
            addMessage(redirectAttributes, "无审核中订单...");
            return "redirect:" + adminPath + "/loan/apply/list";
        }
        return approval(nextUserId, "detail", nextId, "", "", // 来源
                model, redirectAttributes);

    }

    /**
     * 融360api引流过来订单
     *
     * @param applyOP
     * @param first
     * @param model
     * @return
     */
    @RequestMapping(value = "applyByRong")
    public String applyByRong(ApplyOP applyOP, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page());
            return "modules/loan/applyByRong";
        }
        List<User> list = UserUtils.getUserByRole("xinshen");
        if (null != list) {
            List<User> auditorList = new ArrayList<User>();
            for (User user : list) {
                if (UserUtils.haveRole(UserUtils.getUser(), "ccd")
                        && UserUtils.haveRole(UserUtils.get(user.getId()), "ccd")) {
                    auditorList.add(user);
                } else if ((UserUtils.haveRole(UserUtils.getUser(), "jbqb")
                        || (UserUtils.haveRole(UserUtils.getUser(), "xjdfq")))
                        && (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb")
                        || UserUtils.haveRole(UserUtils.get(user.getId()), "xjdfq"))) {
                    auditorList.add(user);
                } else if (UserUtils.haveRole(UserUtils.getUser(), "tfl")
                        && UserUtils.haveRole(UserUtils.get(user.getId()), "tfl")) {
                    auditorList.add(user);
                } else if (UserUtils.haveRole(UserUtils.getUser(), "lyfq")
                        && UserUtils.haveRole(UserUtils.get(user.getId()), "lyfq")) {
                    auditorList.add(user);
                } else if (UserUtils.haveRole(UserUtils.getUser(), "superRole")) {
                    auditorList.add(user);
                }
            }
            model.addAttribute("auditor", auditorList);
        }
        model.addAttribute("autoCheck", WhetherEnum.values());
        model.addAttribute("statusList", getStatusEnumList(applyOP.getStage()));
        model.addAttribute("applyOP", applyOP);

        Page page = new Page();
        ApplyListOP applyListOP = assemble(applyOP);
        // 查询页面默认的多种状态
        // getStatusList(applyListOP, page);
        page.setPageNo(applyListOP.getPageNo());
        page.setPageSize(applyListOP.getPageSize());
        Page<ApplyListVO> voPage = null;

        RoleControlParam roleControlParam = RoleControl.get(applyListOP.getProductId(), applyOP.getCompanyId());
        applyListOP.setProductId(roleControlParam.getProductId());
        applyListOP.setCompanyId(roleControlParam.getCompanyId());
        voPage = loanApplyService.getLoanApplyByApi(page, applyListOP);
        getStatusNameForAPiPage(voPage);
        if (applyOP.getStage() == 1 || applyOP.getStage() == 5) {
            for (ApplyListVO applyVO : voPage.getList()) {
                String lockName = getApproveLockName(applyVO.getId());
                if (lockName != null) {
                    applyVO.setStatusStr("[" + lockName + "]审核中");
                }
            }
        }

        model.addAttribute("page", voPage);
        return "modules/loan/applyByRong";
    }

    @ResponseBody
    @RequestMapping(value = "anRongData")
    public WebResult anRongData(@RequestParam(value = "applyId") String applyId) {
        try {
            if (StringUtils.isBlank(applyId)) {
                return new WebResult("99", "客户订单号为空");
            }
            String cacheKey = "apply_anRongreport_" + applyId;
            MSPReprtVO result = (MSPReprtVO) JedisUtils.getObject(cacheKey);
            if (result == null) {
                result = reportService.getAnRongData(applyId);
                if (null != result){
                    if (null != result.getErrors()){
                        return new WebResult("99", result.getErrors().toString());
                    } else {
                        applyTripartiteAnrongService.save(applyId);
                        JedisUtils.setObject(cacheKey, result, Global.TWO_HOURS_CACHESECONDS);
                    }
                }
            }
            return new WebResult("1", "提交成功", result);
        } catch (Exception e) {
            logger.error("安融MSP报告获取异常：applyId = " + applyId, e);
            e.printStackTrace();
            return new WebResult("99", "安融MSP报告获取异常");
        }
    }



}
