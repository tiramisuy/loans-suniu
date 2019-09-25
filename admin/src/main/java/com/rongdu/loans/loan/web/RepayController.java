package com.rongdu.loans.loan.web;

import com.google.common.collect.Maps;
import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.RoleControl;
import com.rongdu.loans.common.RoleControlParam;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.PayTypesEnum;
import com.rongdu.loans.enums.WhetherEnum;
import com.rongdu.loans.loan.op.FlowOP;
import com.rongdu.loans.loan.op.RepayOP;
import com.rongdu.loans.loan.option.FlowListOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.RepayWarnOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.pay.exception.OrderProcessingException;
import com.rongdu.loans.pay.exception.WithholdUpdateException;
import com.rongdu.loans.pay.service.WithholdService;
import com.rongdu.loans.pay.vo.WithholdResultVO;
import com.rongdu.loans.sys.entity.Office;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.OfficeService;
import com.rongdu.loans.sys.service.SystemService;
import com.rongdu.loans.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/repay")
public class RepayController extends BaseController {

    @Autowired
    private RepayPlanItemService repayPlanItemService;
    @Autowired
    private RepayLogService repayLogService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private RepayWarnService repayWarnService;

    @Autowired
    private CustUserService custUserService;
    @Autowired
    private WithholdService withholdService;
    @Autowired
    private ChannelService channelService;

    /**
     * 还款列表
     *
     * @param repayOP
     * @param model
     * @return
     */
    @RequestMapping(value = "detailList")
    public String detailList(RepayOP repayOP, Boolean first,
                             @RequestParam(value = "current", required = false) String current, Model model) {
        User user = UserUtils.getUser();
        logger.info("还款列表查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(repayOP));

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/loan/detailList";
        }
        model.addAttribute("current", 2);
        // 用于当天还款查询 current为1
        if ("1".equals(current)) {
            dealRepayOP(repayOP, model);
            model.addAttribute("current", 1);
        }

        //List<User> list = systemService.findUserWithRole("xinshen");
        List<User> list = UserUtils.getUserByRole("xinshen");
        if (null != list) {
            model.addAttribute("auditor", list);
        }
        model.addAttribute("repayOP", repayOP);

        RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
        model.addAttribute("page", repayPlanItemService.repayDetailList(op));
        if (repayOP.getStatus()!=null&&repayOP.getStatus() == 1){
            model.addAttribute("chlOrderNoShow", true);
        }
        return "modules/loan/detailList";
    }

    private void dealRepayOP(RepayOP repayOP, Model model) {
        Date now = new Date();
        String nowStr = new SimpleDateFormat("yyyy-MM-dd").format(now);
        String nowStart = nowStr + " 00:00:00";
        String nowEnd = nowStr + " 23:59:59";

        repayOP.setExpectStart(nowStart);
        repayOP.setExpectEnd(nowEnd);
        model.addAttribute("expectStart", nowStart);
        model.addAttribute("expectEnd", nowStart);

    }

    /**
     * 还款提醒列表
     *
     * @param warnOP
     * @param model
     * @return
     */
    @RequestMapping(value = "warnList")
    public String warnList(RepayWarnOP warnOP, Model model) {
        User user = UserUtils.getUser(); // 获取当前登录用户信息，用于查询被分配还款提醒
        logger.info("还款预提醒列表查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(warnOP));
        List<User> list = systemService.getUserByRoleAndDept("csy", "");
        if (null != list) {
            model.addAttribute("auditor", list);
        }
        if (UserUtils.haveRole(user, "csy") && !UserUtils.haveRole(user, "system")) {
            // 非系统管理员之鞥你看当前用户
            warnOP.setSysUserId(user.getId());
        }
        model.addAttribute("warnOP", warnOP);
        model.addAttribute("page", repayWarnService.getRepayWarnList(warnOP));
        return "modules/loan/warnList";
    }

    /**
     * 查询待分配的催收员
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllCsUser")
    public WebResult getAllCsUser() {
        try {
            return new WebResult("1", "提交成功", UserUtils.getUserByRole("kefu_xjd"));
        } catch (Exception e) {
            logger.error("查询催收员信息异常", e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 提交还款提醒内容
     *
     * @param content    (本次) 还款提醒内容
     * @param id
     * @param oldContent 之前提醒内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "warnConfirm")
    public WebResult warnConfirm(String content, String id, String oldContent) {
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotBlank(oldContent)) {
                sb.append(oldContent).append("&gt;").append(DateUtils.getDateTime()).append(content);
            } else {
                sb.append(DateUtils.getDateTime()).append("&nbsp;").append(content);
            }
            repayWarnService.updateWarn(id, sb.toString());
            return new WebResult("1", "提交成功", null);
        } catch (Exception e) {
            logger.error("提交还款预提醒信息异常！", e);
            return new WebResult("99", "系统异常！");
        }
    }

    /**
     * 导出还款明细数据
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    @ExportLimit()
    public void exportFile(RepayOP repayOP, HttpServletRequest request, HttpServletResponse response,
                           RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出还款明细数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("还款数据", RepayReportVo.class);
            String fileName = "还款明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
            RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
            op.setProductId(roleControlParam.getProductId());
            //op.setCompanyId(roleControlParam.getCompanyId());
            List<RepayDetailListVO> result = repayPlanItemService.repayDetailExportList(op);
            for (RepayDetailListVO repayDetailListVO : result) {
                if (repayDetailListVO.getProductId().equals(LoanProductEnum.XJD.getId())) {
                    repayDetailListVO
                            .setDelayTimes(repayDetailListVO.getApproveTerm() / repayDetailListVO.getApplyTerm() - 1);
                }
                if (repayDetailListVO.getLoanStatus() == 1) {
                    repayDetailListVO.setLoanStatusStr("取消放款");
                }

                repayDetailListVO
                        .setEgTisTerm(repayDetailListVO.getThisTerm() + "/" + repayDetailListVO.getTotalTerm());
            }
            excel.setDataList(result).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /*	*//**
     * 导出当天还款数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    /*
     * @RequestMapping(value = "exportToday", method = RequestMethod.POST)
     *
     * @ExportLimit() public void exportToday(RepayOP repayOP,
     * HttpServletRequest request, HttpServletResponse response,
     * RedirectAttributes redirectAttributes) throws IOException { User user =
     * UserUtils.getUser(); logger.info("导出当天还款明细数据--->{}--->{}", user.getId(),
     * user.getName()); ExportExcel excel = null; try { excel = new
     * ExportExcel("当天还款数据", RepayReportTodayVo.class); String fileName =
     * "当天还款明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
     * RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
     * RoleControlParam roleControlParam = RoleControl.get(op.getProductId(),
     * op.getCompanyId()); op.setProductId(roleControlParam.getProductId());
     * op.setCompanyId(roleControlParam.getCompanyId()); List<RepayDetailListVO>
     * result = repayPlanItemService.repayDetailExportList(op); for
     * (RepayDetailListVO repayDetailListVO : result) { if
     * (repayDetailListVO.getProductId().equals(LoanProductEnum.XJD.getId())) {
     * repayDetailListVO.setDelayTimes(repayDetailListVO.getApproveTerm() /
     * repayDetailListVO.getApplyTerm() - 1); }
     *
     * repayDetailListVO .setEgTisTerm(repayDetailListVO.getThisTerm() + "/" +
     * repayDetailListVO.getTotalTerm()); }
     * excel.setDataList(result).write(response, fileName); } finally { if
     * (excel != null) excel.dispose(); } }
     */

    /**
     * 导出还款表数据
     */
    @RequestMapping(value = "repayTotalexport", method = RequestMethod.POST)
    @ExportLimit()
    public void repayTotalexport(RepayOP repayOP, HttpServletRequest request, HttpServletResponse response,
                                 RedirectAttributes redirectAttributes) throws IOException {

        User user = UserUtils.getUser();
        logger.info("导出还款总账数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("还款表数据", RepayTotalReportVo.class);
            String fileName = "还款数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
            RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
            op.setProductId(roleControlParam.getProductId());
            op.setCompanyId(roleControlParam.getCompanyId());
            List<RepayTotalListVO> result = loanRepayPlanService.repayTotalExportList(op);
            List<Office> companyList = officeService.getAllCompany(null);
            if (null != companyList) {
                for (Office office : companyList) {
                    for (RepayTotalListVO vo : result) {
                        if ("CCD".equals(vo.getProductId())) {
                            vo.setMouthRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(12), 6)
                                    .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            vo.setTermRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(24), 6)
                                    .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");

                            vo.setContractTerm(vo.getThisTerm() / 2); // 设置合同期数
                        } else if ("TFL".equals(vo.getProductId())) {
                            vo.setMouthRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(12), 6)
                                    .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            vo.setTermRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(12), 6)
                                    .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            vo.setContractTerm(vo.getThisTerm()); // 设置合同期数
                        } else {
                            if ("XJD".equals(vo.getProductId())) {
                                vo.setCompanyName("急速钱包");
                                vo.setTermRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                        .divide(BigDecimal.valueOf(12), 6)
                                        .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            } else if ("XJDFQ".equals(vo.getProductId())) {
                                vo.setCompanyName("现金分期");
                                vo.setTermRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                        .divide(BigDecimal.valueOf(365), 6)
                                        .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            } else {
                                vo.setTermRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                        .divide(BigDecimal.valueOf(365), 6)
                                        .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");
                            }
                            vo.setMouthRateStr(vo.getBasicRate().multiply(BigDecimal.valueOf(100))
                                    .divide(BigDecimal.valueOf(12), 6)
                                    .setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString() + "%");

                            vo.setContractTerm(vo.getThisTerm()); // 设置合同期数
                        }
                        if (vo.getCompanyId() != null) {
                            if (vo.getCompanyId().equals(office.getCompanyId())) {
                                vo.setCompanyName(office.getName());
                            }
                        }
                    }
                }
            }
            excel.setDataList(result).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    private RepayDetailListOP repayOP2RepayDetailListOP(RepayOP repayOP) {
        RepayDetailListOP op = BeanMapper.map(repayOP, RepayDetailListOP.class);
        if (StringUtils.isNotBlank(repayOP.getBorrowStart())) {
            op.setBorrowTimeStart(DateUtils.parse(repayOP.getBorrowStart()));
        }
        if (StringUtils.isNotBlank(repayOP.getBorrowEnd())) {
            op.setBorrowTimeEnd(DateUtils.parse(repayOP.getBorrowEnd()));
        }
        if (StringUtils.isNotBlank(repayOP.getExpectStart())) {
            op.setExpectTimeStart(DateUtils.parse(repayOP.getExpectStart()));
        }
        if (StringUtils.isNotBlank(repayOP.getExpectEnd())) {
            op.setExpectTimeEnd(DateUtils.parse(repayOP.getExpectEnd()));
        }
        if (StringUtils.isNotBlank(repayOP.getActualEnd())) {
            op.setActualTimeEnd(DateUtils.parse(repayOP.getActualEnd()));
        }
        if (StringUtils.isNotBlank(repayOP.getActualStart())) {
            op.setActualTimeStart(DateUtils.parse(repayOP.getActualStart()));
        }
        if (StringUtils.isNotBlank(repayOP.getApplyCreateEnd())) {
            op.setApplyCreateTimeEnd(DateUtils.parse(repayOP.getApplyCreateEnd()));
        }
        if (StringUtils.isNotBlank(repayOP.getApplyCreateStart())) {
            op.setApplyCreateTimeStart(DateUtils.parse(repayOP.getApplyCreateStart()));
        }
        return op;
    }

    /**
     * 流水列表
     *
     * @param flowOP
     * @param model
     * @return
     */
    @RequestMapping(value = "flowList")
    public String flowList(FlowOP flowOP, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/loan/flowList";
        }
        model.addAttribute("enums", WhetherEnum.values());
        model.addAttribute("flowOP", flowOP);
        FlowListOP op = flowOP2FlowListOP(flowOP);
        model.addAttribute("page", repayLogService.getRepayLogList(op));
        return "modules/loan/flowList";
    }

    @RequestMapping(value = "exportFlowList", method = RequestMethod.POST)
    @ExportLimit()
    public void exportFlowList(FlowOP flowOP, HttpServletRequest request, HttpServletResponse response,
                               RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出还款交易流水--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("还款流水", RepayLogReportVO.class);
            String fileName = "还款流水" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            FlowListOP op = flowOP2FlowListOP(flowOP);
            List<RepayLogListVO> result = repayLogService.getRepayLogListForExport(op);
            excel.setDataList(result).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    private FlowListOP flowOP2FlowListOP(FlowOP flowOP) {
        FlowListOP op = BeanMapper.map(flowOP, FlowListOP.class);
        if (StringUtils.isNotBlank(flowOP.getAmount())) {
            op.setTxAmt(BigDecimal.valueOf(Double.valueOf(flowOP.getAmount())));
        }
        if (StringUtils.isNotBlank(flowOP.getRepayStart())) {
            op.setRepayTimeStart(DateUtils.parse(flowOP.getRepayStart()));
        }
        if (StringUtils.isNotBlank(flowOP.getRepayEnd())) {
            op.setRepayTimeEnd(DateUtils.parse(flowOP.getRepayEnd()));
        }
        return op;
    }

    /**
     * 还款计划列表
     *
     * @param contNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "repayPlanItemList", method = RequestMethod.POST)
    public WebResult repayPlanItemList(@RequestParam(value = "contNo") String contNo) {
        try {
            RepayPlanItemListVO vo = repayPlanItemService.repayPlanItemService(contNo);

            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询还款计划列表异常：contNo = " + contNo, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询还款计划列表异常：contNo = " + contNo, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 手动延期
     */
    @ResponseBody
    @RequestMapping(value = "processAdminDelay")
    public WebResult processAdminDelay(@RequestParam(value = "repayPlanItemId") String repayPlanItemId) {
        try {
            synchronized (RepayController.class) {
                contractService.processAdminDelay(repayPlanItemId);
            }
            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("手动延期异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动延期异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 还款总账列表
     *
     * @param repayOP
     * @param model
     * @return
     */
    @RequestMapping(value = "repayTotalList")
    public String repayTotalList(RepayOP repayOP, Boolean first, Model model) {
        User user = UserUtils.getUser();
        logger.info("还款总账列表查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(repayOP));
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/loan/repayTotalList";
        }
        model.addAttribute("repayOP", repayOP);
        RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
/*        RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
        op.setProductId(roleControlParam.getProductId());
        op.setCompanyId(roleControlParam.getCompanyId());*/

        model.addAttribute("page", loanRepayPlanService.repayTotalList(op));
        return "modules/loan/repayTotalList";
    }

    /**
     * 手动延期金额查询
     */
    @ResponseBody
    @RequestMapping(value = "getDelayAmount")
    public WebResult getDelayAmount(@RequestParam(value = "repayPlanItemId") String repayPlanItemId,
                                    @RequestParam(value = "delayDate") String delayDate) {
        try {
            Map<String, Object> data = Maps.newHashMap();
            synchronized (RepayController.class) {
                if (StringUtils.isBlank(delayDate)) {
                    delayDate = DateUtils.getDateTime();
                }
                data = contractService.getDelayAmount(repayPlanItemId, delayDate);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("手动延期查询异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动延期查询异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 手动延期
     */
    @ResponseBody
    @RequestMapping(value = "delayDeal")
    public WebResult delayDeal(@RequestParam(value = "repayPlanItemId") String repayPlanItemId,
                               @RequestParam(value = "delayDate") String delayDate, @RequestParam(value = "repayType") String repayType,
                               @RequestParam(value = "repayTypeName") String repayTypeName) {
        User user = UserUtils.getUser();
        logger.info("延期--->{}--->{}--->{}", user.getId(), user.getName(), repayPlanItemId);

        // 口袋放款只能延期一次
        Date applyDate = DateUtils.parse(repayPlanItemId.substring(2, 10), "yyyyMMdd");
        Date startDate = DateUtils.parse("20180926", "yyyyMMdd");
        int term = Integer.parseInt(repayPlanItemId.substring(repayPlanItemId.length() - 2, repayPlanItemId.length()));
        if (DateUtils.daysBetween(startDate, applyDate) >= 0 && term > 1) {
            return new WebResult("99", "延期次数超限", null);
        }
        try {
            synchronized (RepayController.class) {
                contractService.delayDeal(repayPlanItemId, 1, delayDate, repayType, repayTypeName);
            }
            return new WebResult("1", "提交成功", null);
        } catch (OrderProcessingException e) {
            return new WebResult(e.getCode(), e.getMsg());
        } catch (RuntimeException e) {
            logger.error("手动延期异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("手动延期异常：repayPlanItemId = " + repayPlanItemId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * code y0524
     *
     * @param @param  itemId
     * @param @param  delayAmt
     * @param @return 参数
     * @return WebResult 返回类型
     * @Title: delayDealWithhold
     * @Description: 延期代扣
     */
    @ResponseBody
    @RequestMapping(value = "/delayDealWithhold")
    public WebResult delayDealWithhold(@RequestParam(value = "repayPlanItemId") String itemId,
                                       @RequestParam(value = "delayAmt") String delayAmt) {
        User user = UserUtils.getUser();
        logger.info("延期代扣--->{}--->{}--->{}", user.getId(), user.getName(), itemId);
        // 口袋放款只能延期一次
        Date applyDate = DateUtils.parse(itemId.substring(2, 10), "yyyyMMdd");
        Date startDate = DateUtils.parse("20180926", "yyyyMMdd");
        int term = Integer.parseInt(itemId.substring(itemId.length() - 2, itemId.length()));
        if (DateUtils.daysBetween(startDate, applyDate) >= 0 && term > 1) {
            return new WebResult("99", "延期次数超限", null);
        }
        try {
            synchronized (RepayController.class) {
                WithholdResultVO vo = withholdService.delayDealWithhold(itemId, delayAmt, PayTypesEnum.TONGLIAN);
                if (vo.getSuccess()) {
                    return new WebResult("S000", "延期代扣成功", null);
                } else if (vo.getUnsolved()) {
                    return new WebResult("I000", "延期代扣处理中，请稍后再试", null);
                }
                return new WebResult("F001", vo.getMsg(), null);
            }
        } catch (WithholdUpdateException e) {
            return new WebResult(e.getCode(), e.getMsg());
        } catch (Exception e) {
            logger.error("系统异常", e);
            return new WebResult("F000", "系统异常");
        }
    }

    /**
     * 将交易流水设置为失败状态
     */
    @ResponseBody
    @RequestMapping(value = "ing2fail")
    public WebResult ing2fail(@RequestParam(value = "id") String id) {
        User user = UserUtils.getUser();
        logger.info("将交易流水设置为失败状态--->{}--->{}--->{}", user.getId(), user.getName(), id);
        try {
            synchronized (RepayController.class) {
                RepayLogVO log = repayLogService.get(id);
                if (log != null && !"SUCCESS".equals(log.getStatus())) {
                    log.setStatus("F");
                    log.setRemark("交易超时");
                    repayLogService.update(log);
                }
            }
            return new WebResult("1", "提交成功", null);
        } catch (OrderProcessingException e) {
            return new WebResult(e.getCode(), e.getMsg());
        } catch (RuntimeException e) {
            logger.error("交易流水设置为失败状态：交易流水号 = " + id, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("交易流水设置为失败状态：交易流水号 = " + id, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 今日还款列表
     *
     * @param repayOP
     * @param model
     * @return
     */
    @RequestMapping(value = "todayRepayList")
    public String todayRepayList(RepayOP repayOP, Model model) {

        List<User> list = systemService.findUserByOffice();
        if (null != list) {
            model.addAttribute("auditor", list);
        }
        model.addAttribute("repayOP", repayOP);

        RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);

        RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
        op.setProductId(roleControlParam.getProductId());
        op.setCompanyId(roleControlParam.getCompanyId());

        model.addAttribute("page", repayPlanItemService.todayRepayList(op));
        return "modules/loan/todayRepayList";
    }

    /**
     * 导出当天还款数据
     *
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "exportToday", method = RequestMethod.POST)
    @ExportLimit()
    public void exportToday(RepayOP repayOP, HttpServletRequest request, HttpServletResponse response,
                            RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出当天还款明细数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("当天还款数据", TodayRepayListVO.class);
            String fileName = "当天还款明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            RepayDetailListOP op = repayOP2RepayDetailListOP(repayOP);
            RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
            op.setProductId(roleControlParam.getProductId());
            op.setCompanyId(roleControlParam.getCompanyId());
            op.setPageSize(-1);
            Page<TodayRepayListVO> voPage = repayPlanItemService.todayRepayList(op);
            List<TodayRepayListVO> result = voPage.getList();
            for (TodayRepayListVO repayDetailListVO : result) {
                repayDetailListVO
                        .setContractTerm(repayDetailListVO.getThisTerm() + "/" + repayDetailListVO.getTotalTerm());

                if (repayDetailListVO.getStatus() == 0) {
                    repayDetailListVO.setStatusStr("待还款");
                } else if (repayDetailListVO.getStatus() == 1) {
                    repayDetailListVO.setStatusStr("已还款");
                } else {
                    repayDetailListVO.setStatusStr("处理中");
                }
            }
            excel.setDataList(result).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

}
