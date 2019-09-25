package com.rongdu.loans.cust.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.rongdu.loans.loan.option.jdq.Calls;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.Telecom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.http.ClientResult;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.Dict;
import com.rongdu.loans.common.RoleControlUtils;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.credit.baiqishi.vo.CuishouOP;
import com.rongdu.loans.credit.baiqishi.vo.CuishouVO;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageOP;
import com.rongdu.loans.credit.baiqishi.vo.ReportPageVO;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.op.RemoveDuplicateQueryOP;
import com.rongdu.loans.cust.option.BorrowerOP;
import com.rongdu.loans.cust.option.CustUserStatusOP;
import com.rongdu.loans.cust.option.QuerySmsOP;
import com.rongdu.loans.cust.option.QueryUserOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.service.SmsCodeService;
import com.rongdu.loans.cust.service.SysCustUserService;
import com.rongdu.loans.cust.vo.AccountVO;
import com.rongdu.loans.cust.vo.BorrowerVO;
import com.rongdu.loans.cust.vo.BorrowlistVO;
import com.rongdu.loans.cust.vo.CardVO;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.QueryUserVO;
import com.rongdu.loans.cust.vo.RemoveDuplicateQueryVO;
import com.rongdu.loans.cust.vo.RepayItemDetailVO;
import com.rongdu.loans.cust.vo.SmsCodeVo;
import com.rongdu.loans.cust.vo.UserFileInfoVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.IdTypeEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.OpenAccountEnum;
import com.rongdu.loans.enums.RelationshipEnum;
import com.rongdu.loans.loan.op.SaveContactOP;
import com.rongdu.loans.loan.option.ContactHistoryOP;
import com.rongdu.loans.loan.option.ContactHistorySaveOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.option.dwd.charge.DWDReport;
import com.rongdu.loans.loan.option.jdq.report.JDQReport;
import com.rongdu.loans.loan.option.xjbk.ContactCheck;
import com.rongdu.loans.loan.option.xjbk.ContactList;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.service.ContactHistoryService;
import com.rongdu.loans.loan.service.DWDService;
import com.rongdu.loans.loan.service.JDQService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.OperationLogService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.SLLService;
import com.rongdu.loans.loan.service.ShopWithholdService;
import com.rongdu.loans.loan.service.XianJinBaiKaService;
import com.rongdu.loans.loan.vo.ContactHistoryVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.OperationLogVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.option.WhitelistOP;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import com.rongdu.loans.risk.service.RiskWhitelistService;
import com.rongdu.loans.sys.entity.Office;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.OfficeService;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/custUser/")
public class CustUserController extends BaseController {

    @Autowired
    private SysCustUserService sysCustUserService;
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private LoanRepayPlanService loanRepayPlanService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ContactHistoryService contactHistoryService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private LoanApplyService loanApplyService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private XianJinBaiKaService xianJinBaiKaService;
    @Autowired
    private RiskBlacklistService riskBlacklistService;
    @Autowired
    private ShopWithholdService shopWithholdService;
    @Autowired
    private JDQService jdqService;
    @Autowired
    private DWDService dwdService;
    @Autowired
    private SLLService sllService;
    @Autowired
    private RiskWhitelistService riskWhitelistService;
    
    @Autowired
    private RepayPlanItemService repayPlanItemService;

    /**
     * 借款人列表
     *
     * @param borrowerOP
     * @param model
     * @return
     */
    @RequestMapping(value = "custuserList")
    public String custuserList(BorrowerOP borrowerOP, Boolean first, Model model) {
    	User user = UserUtils.getUser();
		logger.info("借款人列表查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(borrowerOP));

        List<ChannelVO> chList = channelService.findAllChannel();
        StringBuffer chAllStr = new StringBuffer();
        for (ChannelVO ch : chList) {
            chAllStr.append("'").append(ch.getCid()).append("',");
        }
        chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
        model.addAttribute("channels", chList);
        model.addAttribute("allChannel", chAllStr);

        model.addAttribute("enums", OpenAccountEnum.values());
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/cust/custUserList";
        }
        model.addAttribute("borrowerOP", borrowerOP);
        model.addAttribute("storeId", UserUtils.getUser().getCompany().getId());
        model.addAttribute("page",
                borrowerOP == null ? Collections.emptyList() : custUserService.custUserList(borrowerOP));
        return "modules/cust/custUserList";
    }


    /**
     * 加载拉黑原因页面
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "toInsert")
    public WebResult toInsert(@RequestParam(value = "custUserId") String custUserId) {
        try {
            return new WebResult("1", "提交成功", custUserId);
        } catch (Exception e) {
            logger.error("加载拉黑页面异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }


    /**
     * 借款人分类列表
     *
     * @param borrowerOP
     * @param model
     * @return
     */
    @RequestMapping(value = "channelCustUser")
    public String channelCustUser(BorrowerOP borrowerOP, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/cust/channelCustUser";
        }
        User user = UserUtils.getUser();
        if (user.getChannel() != null) {
            borrowerOP.setChannel(user.getChannel());
        }

        List<ChannelVO> channel = channelService.findAllChannel();
        if (null != channel) {
            model.addAttribute("channel", channel);
        }
        List<Office> companyList = officeService.getAllCompany(null);
        if (null != companyList) {
            model.addAttribute("companyList", companyList);
        }
        if (UserUtils.haveRole(UserUtils.getUser(), "ccd")) {
            borrowerOP.setChannel(ChannelEnum.CHENGDAI.getCode());
        } else if (UserUtils.haveRole(UserUtils.getUser(), "tfl")) {
            borrowerOP.setChannel(ChannelEnum.TOUFULI.getCode());
        } else if (UserUtils.haveRole(UserUtils.getUser(), "lyfq")) {
            borrowerOP.setChannel(ChannelEnum.LYFQAPP.getCode());
        }
        model.addAttribute("enums", OpenAccountEnum.values());
        model.addAttribute("borrowerOP", borrowerOP);
        model.addAttribute("storeId", UserUtils.getUser().getCompany().getId());

        Page<BorrowerVO> pageVo = custUserService.custUserList(borrowerOP);
        for (BorrowerVO vo : pageVo.getList()) {
            String str = vo.getIdNo();
            if (StringUtils.isNotBlank(str)) {
                String str1 = str.substring(6, 12);
                vo.setIdNo(str.replace(str1, "******"));
            }
        }
        model.addAttribute("page", borrowerOP == null ? Collections.emptyList() : pageVo);
        return "modules/cust/channelCustUser";
    }

    /**
     * 添加黑名单: cust_blacklist
     *
     * @return
     */
    @RequestMapping(value = "insertBlickList")
    @ResponseBody
    public WebResult insertBlickList(String userId, String blackReason) {
        try {
            // 插入 cust_blackList
            /*
             * CustUserVO vo = custUserService.getCustUserById(userId);
			 * custUserService.insertBlickList(vo);
			 */

            // 插入risk_blackList
            long checkInsert = riskBlacklistService.countInBlacklist(userId);
            if (checkInsert > 0) {
                return new WebResult("200", "该用户，已经在黑名单中。");
            } else {
                User user = UserUtils.getUser();
                int flag = riskBlacklistService.insertBlacklist(userId, blackReason, null, 1, user.getName());
                if (flag > 0) {
                    return new WebResult("1", "提交成功！");
                }
                return new WebResult("200", "系统异常");
            }

        } catch (Exception e) {
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 黑名单列表 cust_blacklist
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "custBlackList")
    public String custuserList(@ModelAttribute("BlacklistOP") BlacklistOP op, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/cust/custBlackList";
        }
        model.addAttribute("BlacklistOP", op);
        model.addAttribute("page", riskBlacklistService.selectBlackList(op));
        return "modules/cust/custBlackList";
    }

    /**
     * 移除黑名单: risk_blacklist
     *
     * @return
     */
    @RequestMapping(value = "deleteBlickList")
    @ResponseBody
    public WebResult deleteBlickList(String blackId) {
        User user = UserUtils.getUser();
        logger.info("物理移除黑名单--->{}--->{}", user.getId(), user.getName());
        try {
            int flag = riskBlacklistService.deleteBlickList(blackId);
            if (flag > 0) {
                return new WebResult("1", "删除成功！");
            }
            return new WebResult("200", "系统异常");
        } catch (Exception e) {
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 白名单列表 risk_whitelist
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "custWhiteList")
    public String custWhiteList(@ModelAttribute("WhitelistOP") WhitelistOP op, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/cust/custWhiteList";
        }
        model.addAttribute("WhitelistOP", op);
        model.addAttribute("page", riskWhitelistService.selectWhiteList(op));
        return "modules/cust/custWhiteList";
    }

    /**
     * 移除白名单: risk_whitelist
     *
     * @return
     */
    @RequestMapping(value = "deleteWhiteList")
    @ResponseBody
    public WebResult deleteWhiteList(String whiteId) {
        User user = UserUtils.getUser();
        logger.info("物理移除白名单--->{}--->{}", user.getId(), user.getName());
        try {
            int flag = riskWhitelistService.deleteWhiteList(whiteId);
            if (flag > 0) {
                return new WebResult("1", "删除成功！");
            }
            return new WebResult("200", "系统异常");
        } catch (Exception e) {
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 借款人 冻结、解冻
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "updateStatus")
    public String updateStatus(@Valid CustUserStatusOP dto) {
        User user = UserUtils.getUser();
        if (user.getRoleList().size() > 0) {
            custUserService.updateStatus(dto);
        }
        return "redirect:" + adminPath + "/sys/custUser/custuserList";
    }

    /**
     * 借款人详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "custuserDetail")
    public String custuserDetail(@RequestParam(value = "id") String id, Model model) {
    	User user = UserUtils.getUser();
    	logger.info(" 借款人详情查询-->{}-->{}-->{}", user.getId(), user.getName(), id);
        QueryUserOP queryUserOP = new QueryUserOP();
        queryUserOP.setId(id);
        QueryUserVO vo = custUserService.custUserDetail(queryUserOP);
        model.addAttribute("vo", vo);
        model.addAttribute("sign", "user");
        return "modules/cust/userView";
    }

    /**
     * 去修改借款人基本信息页面
     *
     * @param id
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "toUpdateCustuser")
    public String toUpdateCust(@RequestParam(value = "id") String id,
                               @RequestParam(value = "historyId", required = false) String historyId, Model model) {
        CustUser custUser = new CustUser();
        if (StringUtils.isNoneBlank(id)) {
            custUser = sysCustUserService.getCustById(id);
        }
        // 处理remark的部分，转成map结构，回显于页面
        if (StringUtils.isNotBlank(custUser.getRemark())) {
            Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(custUser.getRemark(), Map.class);
            model.addAttribute("workCategory", map.get("workCategory"));
            model.addAttribute("workSize", map.get("workSize"));
            model.addAttribute("house", map.get("house"));
            model.addAttribute("cust_houseLoan", map.get("houseLoan"));
            model.addAttribute("car", map.get("car"));
            model.addAttribute("cust_carLoan", map.get("carLoan"));
        }
        // 加载下拉选内容
        List<Map<String, String>> idTypelist = new ArrayList<Map<String, String>>();
        for (IdTypeEnum idTypeEnum : IdTypeEnum.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", idTypeEnum.getValue());
            map.put("desc", idTypeEnum.getDesc());
            idTypelist.add(map);
        }
        model.addAttribute("historyId", historyId);
        model.addAttribute("idTypelist", idTypelist);
        model.addAttribute("gender", Dict.gender);
        model.addAttribute("haveHouse", Dict.haveHouse);
        model.addAttribute("houseLoan", Dict.houseLoan);
        model.addAttribute("haveCar", Dict.haveCar);
        model.addAttribute("carLoan", Dict.carLoan);
        model.addAttribute("marriage", Dict.marriage);
        model.addAttribute("education", Dict.education);
        model.addAttribute("jobSalary", Dict.jobSalary);
        model.addAttribute("jobCompanyType", Dict.jobCompanyType);
        model.addAttribute("jobCompanyScale", Dict.jobCompanyScale);
        model.addAttribute("jobIndustry", Dict.jobIndustry);
        model.addAttribute("jobYears", Dict.jobYears);
        model.addAttribute("custUser", custUser);
        model.addAttribute("sign", "user");
        return "modules/cust/updateCustuser";
    }

    /**
     * 修改借款人的很基本的基本信息（学历等非全信息）
     *
     * @return
     */
    @RequestMapping(value = "updateCustuser")
    @ResponseBody
    public WebResult updateCustuser(CustUser user) {
        try {
            User currentUser = UserUtils.getUser();
            user.setUpdateBy(currentUser.getName());
            user.setUpdateTime(new Date());
            sysCustUserService.updateCustUser(user);
            if (StringUtils.isNotBlank(user.getApplyId())) {
                sysCustUserService.updateCustUserHistory(user);
            }
            return new WebResult("1", "操作成功");
        } catch (Exception e) {
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 借款人详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userDetail", method = RequestMethod.POST)
    public WebResult userDetail(@RequestParam(value = "id") String id,
                                @RequestParam(value = "applyId", required = false) String applyId,
                                @RequestParam(value = "sign", required = false) String sign) {
        try {
            String cacheKey = "apply_user_detail_" + id + "_" + applyId;
            UserInfoVO vo = (UserInfoVO) JedisUtils.getObject(cacheKey);
            if (vo == null) {
                boolean snapshot = !StringUtils.equals(sign, "user");
                vo = custUserService.getUserInfo(id, applyId, snapshot);
                if (snapshot) {
                    vo.setContactList(reportService.contactMatch(vo.getId(), applyId, vo.getContactList()));
                }
                if (StringUtils.isNotBlank(vo.getChannelName())) {
                    List<ChannelVO> channelList = RoleControlUtils.getChannelList();
                    for (ChannelVO channelVO : channelList) {
                        if (vo.getChannelName().equals(channelVO.getCid())) {
                            vo.setChannelName(channelVO.getcName());
                        }
                    }
                }
                vo.setSign(sign);
                vo.setAdminPath(adminPath);
                JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询借款人详情异常：id = " + id + " , " + "applyId =" + applyId + ", sign = " + sign, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询借款人详情异常：id = " + id + " , applyId =" + applyId + ", sign = " + sign, e);
            return new WebResult("99", "系统异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "rongUserDetail", method = RequestMethod.POST)
    public WebResult rongUserDetail(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "applyId", required = false) String applyId,
                                    @RequestParam(value = "sign", required = false) String sign) {
        try {
            String cacheKey = "rong_apply_user_detail_" + id + "_" + applyId;
            UserInfoVO vo = (UserInfoVO) JedisUtils.getObject(cacheKey);
            if (vo == null) {
                boolean snapshot = !StringUtils.equals(sign, "user");
                vo = custUserService.getUserInfo(id, applyId, snapshot);
                if (snapshot) {
                    vo.setContactList(reportService.rongContactMatch(vo.getId(), applyId, vo.getContactList()));
                }
                if (StringUtils.isNotBlank(vo.getChannelName())) {
                    List<ChannelVO> channelList = RoleControlUtils.getChannelList();
                    for (ChannelVO channelVO : channelList) {
                        if (vo.getChannelName().equals(channelVO.getCid())) {
                            vo.setChannelName(channelVO.getcName());
                        }
                    }
                }
                vo.setSign(sign);
                vo.setAdminPath(adminPath);
                JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询借款人详情异常：id = " + id + " , " + "applyId =" + applyId + ", sign = " + sign, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询借款人详情异常：id = " + id + " , applyId =" + applyId + ", sign = " + sign, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 查询用户影像信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "fileinfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult fileinfo(@RequestParam("userId") String userId, @RequestParam("idNo") String idNo,
                              @RequestParam("realName") String realName) {
        try {
            List<FileInfoVO> list = custUserService.getFileinfo(userId);
            UserFileInfoVO vo = new UserFileInfoVO();
            vo.setList(list);
            vo.setIdNo(idNo);
            vo.setRealName(realName);
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询用户影像信息异常：userId = " + userId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询用户影像信息异常：userId = " + userId, e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 查询用户借款信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "borrowlist", method = RequestMethod.POST)
    @ResponseBody
    public WebResult borrowlist(@RequestParam("userId") String userId, String ctx) {
        try {
            BorrowlistVO vo = new BorrowlistVO();
            List<RepayItemDetailVO> list = loanRepayPlanService.findUserRepayList(userId);
            vo.setCtx(ctx);
            vo.setList(list);
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询用户借款信息异常：userId = " + userId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询用户借款信息异常：userId = " + userId, e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 查询用户资金账户
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "account", method = RequestMethod.POST)
    @ResponseBody
    public WebResult account(@RequestParam("accountId") String accountId) {
        try {
            /** 资金账户 */
            Map map = null;
            /** 资金账户 */
            String url = Global.getConfig("deposit.balanceQuery.url");
            Map<String, String> params = new HashMap<String, String>();
            params.put("accountId", accountId);
            ClientResult clientResult = (ClientResult) RestTemplateUtils.getInstance().postForObject(url, params,
                    ClientResult.class);
            AccountVO accountVO = new AccountVO();
            if (StringUtils.equals(clientResult.getCode(), "0000")) {
                map = (Map) clientResult.getResult();
                if (map != null && map.size() > 0 && StringUtils.equals(map.get("retCode").toString(), "00000000")) {
                    // 总额
                    BigDecimal currBal = new BigDecimal(map.get("currBal").toString());
                    // 可用余额
                    BigDecimal availBal = new BigDecimal(map.get("availBal").toString());
                    // 冻结金额
                    BigDecimal freezeBal = currBal.subtract(availBal).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    accountVO.setAvailBal(availBal);
                    accountVO.setCurrBal(currBal);
                    accountVO.setFreezeBal(freezeBal);
                    return new WebResult("1", "提交成功", accountVO);
                }
            }
            return new WebResult("1", "提交成功", accountVO);
        } catch (RuntimeException e) {
            logger.error("查询资金账户异常：accountId = " + accountId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询资金账户异常：accountId = " + accountId, e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 查询用户银行卡信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "cardinfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult cardinfo(@RequestParam("userId") String userId) {
        try {
            String cacheKey = "apply_user_card_info_" + userId;
            CardVO vo = (CardVO) JedisUtils.getObject(cacheKey);
            if (vo == null) {
                vo = custUserService.getCardinfo(userId);
                JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查询用户银行卡信息异常：userId = " + userId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询用户银行卡信息异常：userId = " + userId, e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 查询用户资信云报告
     *
     * @param reportPageOP
     * @return
     */
    @RequestMapping(value = "getReportPage", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getReportPage(@Valid ReportPageOP reportPageOP) {
        try {
            ReportPageVO vo = reportService.getReportPage(reportPageOP);
            return new WebResult("1", "提交成功", vo.getReportPageUrl());
        } catch (RuntimeException e) {
            logger.error("查询用户资信云报告异常：reportPageOP = " + JsonMapper.getInstance().toJson(reportPageOP), e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询用户资信云报告异常：reportPageOP = " + JsonMapper.getInstance().toJson(reportPageOP), e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 查重信息，查询填写过客户为联系人的记录
     *
     * @param removeDuplicateQueryOP
     * @return
     */
    @RequestMapping(value = "removeDuplicateQuery", method = RequestMethod.POST)
    @ResponseBody
    public WebResult removeDuplicateQuery(@Valid RemoveDuplicateQueryOP removeDuplicateQueryOP) {
        try {

            String cacheKey = "apply_remove_duplicate_query_" + removeDuplicateQueryOP.getUserId() + "_"
                    + removeDuplicateQueryOP.getApplyId();
            RemoveDuplicateQueryVO vo = (RemoveDuplicateQueryVO) JedisUtils.getObject(cacheKey);
            if (vo == null) {
                ContactHistoryOP contactHistoryOP = BeanMapper.map(removeDuplicateQueryOP, ContactHistoryOP.class);

                List<ContactHistoryVO> list = contactHistoryService.removeDuplicateQuery(contactHistoryOP);
                vo = new RemoveDuplicateQueryVO();
                vo.setList(list);
                vo.setFlag(removeDuplicateQueryOP.getFlag());
                vo.setCtx(removeDuplicateQueryOP.getCtx());

                JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
            }

            return new WebResult("1", "提交成功", vo);
        } catch (RuntimeException e) {
            logger.error("查重信息异常：removeDuplicateQueryOP = " + JsonMapper.getInstance().toJson(removeDuplicateQueryOP),
                    e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查重信息异常：removeDuplicateQueryOP = " + JsonMapper.getInstance().toJson(removeDuplicateQueryOP),
                    e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 新增催收联系人
     *
     * @param op
     * @return
     */
    @RequestMapping(value = "saveCollectionContact", method = RequestMethod.POST)
    @ResponseBody
    public WebResult saveCollectionContact(@Valid SaveContactOP op) {
        try {
            ContactHistorySaveOP contactHistoryOP = BeanMapper.map(op, ContactHistorySaveOP.class);
            contactHistoryOP.setRelationship(RelationshipEnum.getValue(op.getRelation()));
            custUserService.saveContactHistory(contactHistoryOP);

            String cacheKey = "apply_user_detail_" + op.getUserId() + "_" + op.getApplyId();
            JedisUtils.del(cacheKey);

            String cacheKey1 = "rong_apply_user_detail_" + op.getUserId() + "_" + op.getApplyId();
            JedisUtils.del(cacheKey1);

            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("新增催收联系人异常：op = " + JsonMapper.getInstance().toJson(op), e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("新增催收联系人异常：op = " + JsonMapper.getInstance().toJson(op), e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 删除催收联系人
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteCollectionContact", method = RequestMethod.POST)
    @ResponseBody
    public WebResult deleteCollectionContact(@RequestParam("id") String id) {
        try {
            custUserService.delContactHisById(id);
            return new WebResult("1", "提交成功", null);
        } catch (RuntimeException e) {
            logger.error("删除催收联系人异常：id = " + id, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("删除催收联系人异常：id = " + id, e);
            return new WebResult("99", "系统异常");
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "getContactConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getContactConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                           @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "apply_contact_connect_info_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (data == null) {
            	
                List<Map<String, Object>> contactConnectInfoList = reportService.getContactConnectInfo(applyId);
                List<FileInfoVO> contactFileList = custUserService.getCustContactFile(userId);
                // 将contactConnectInfoList等分
                List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
                int remaider = contactConnectInfoList.size() % 2; // 余数
                int number = contactConnectInfoList.size() / 2; // 商
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<Map<String, Object>> value = null;
                    if (remaider > 0) {
                        value = contactConnectInfoList.subList(i * number + offset, (i + 1) * number + offset + 1);
                        remaider--;
                        offset++;
                    } else {
                        value = contactConnectInfoList.subList(i * number + offset, (i + 1) * number + offset);
                    }
                    List<Map<String, Object>> value1 = new ArrayList<Map<String, Object>>();
                    value1.addAll(value);
                    result.add(value1);
                }
                int size = result.get(0).size();
                for (Map<String, Object> map : result.get(1)) {
                    map.put("index", size);
                    size++;
                }
                data = new HashMap<String, Object>();
                data.put("data1", result.get(0));
                data.put("data3", result.get(1));
                data.put("data2", contactFileList);
                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 新客户(succCont =0)逾期超过2天开始显示通讯录
     * 老客户(succCont>0) 逾期超过14天开始显示通讯录
     * @param succCont
     * @param overdue
     * @return
     */
    private boolean isShowContact(Integer succCont,Integer overdue){
    	if(succCont==null){
    		succCont = 0;
    	}
    	boolean flag = false;
    	if(succCont==0){
    		if(overdue >2){
    			flag =true;
    		}
    	}else{
    		if(overdue >14){
        		flag =true;
        	}
    	}
    	return flag;
    }
    
    @RequestMapping(value = "uploadCuishou", method = RequestMethod.POST)
    @ResponseBody
    public WebResult uploadCuishou(@Valid CuishouOP cuishouOP) {
        try {
            CuishouVO vo = reportService.uploadCuishou(cuishouOP);
            if (vo.isSuccess())
                return new WebResult("1", "处理成功，请30秒后在资信云报告中查看催收指标", vo);
            else
                return new WebResult("1", vo.getMsg(), vo);
        } catch (RuntimeException e) {
            logger.error("上传查询催收指标用户异常：cuishouOP = " + JsonMapper.getInstance().toJson(cuishouOP), e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("上传查询催收指标用户异常：cuishouOP = " + JsonMapper.getInstance().toJson(cuishouOP), e);
            return new WebResult("99", "系统异常");
        }

    }

    /**
     * 客户管理
     */
    @RequestMapping(value = "findCustUser")
    public String findCustUser(String mobile, Model model) {
        Map<String, String> param = new HashMap<String, String>();
        model.addAttribute("mobile", mobile);
        if (StringUtils.isNotBlank(mobile)) {
            try {
                String url = Global.getConfig("deposit.customerQuery.url");
                param.put("Mobile", mobile);
                logger.info("查询客户信息http请求：[{}]", JsonMapper.toJsonString(param));
                Map<String, Object> map = (Map<String, Object>) RestTemplateUtils.getInstance().postForObject(url,
                        param, Map.class);
                logger.info("查询客户信息http响应：[{}]", JsonMapper.toJsonString(map));
                if (null != map) {
                    if (map.get("status").equals("OK")) {
                        model.addAttribute("status", map.get("status"));
                        model.addAttribute("resultMap", map.get("userinfo"));
                        model.addAttribute("loan", map.get("loan"));
                        model.addAttribute("amount", map.get("amount"));
                        model.addAttribute("loanList", map.get("loanList"));
                        model.addAttribute("isRepaying", map.get("IsRepaying"));
                        model.addAttribute("loanCount", custUserService.getLoanCountByMobile(mobile));
                    } else {
                        CustUserVO vo = custUserService.getCustUserByMobile(mobile);
                        if (null != vo) {
                            model.addAttribute("status", map.get("status"));
                            model.addAttribute("loanCount", custUserService.getLoanCountByMobile(mobile));
                            model.addAttribute("vo", vo);
                        } else {
                            model.addAttribute("none", "none");
                        }
                    }
                    return "modules/cust/queryInfo";
                }
            } catch (Exception e) {
                logger.error("查询客户信息异常：Mobile = " + mobile, e);
                return "error/404";
            }
        }
        return "modules/cust/queryInfo";
    }

    /*
     * 查询客户短信
     */
    @RequestMapping(value = "getCustUserSms")
    public String getCustUserSms(QuerySmsOP querySmsOP, Boolean first, Model model) {
        if (first) {
            model.addAttribute("page", new Page());
            return "modules/cust/queryCustSmsInfo";
        }
        if (StringUtils.isBlank(querySmsOP.getMobile())) {
            model.addAttribute("page", new Page());
            return "modules/cust/queryCustSmsInfo";
        }
        boolean isCaiwu = UserUtils.haveRole(UserUtils.getUser(), "caiwu");
        if (!isCaiwu) {
            querySmsOP.setType("code");
        }
        Page page = new Page();
        page.setPageNo(querySmsOP.getPageNo());
        page.setPageSize(querySmsOP.getPageSize());
        page.setOrderBy("send_time");
        Page<SmsCodeVo> voPage = smsCodeService.getSmsCode(page, querySmsOP);
        model.addAttribute("querySmsOP", querySmsOP);
        model.addAttribute("page", voPage);
        return "modules/cust/queryCustSmsInfo";
    }

    @RequestMapping(value = "exportSms")
    @ExportLimit
    public void exportSms(QuerySmsOP querySmsOP, HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出用户短信数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("用户短信数据", SmsCodeVo.class);
            String fileName = "用户短信数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<SmsCodeVo> voPage = smsCodeService.exportSms(querySmsOP);
            if (voPage != null && voPage.size() > 0) {
                excel.setDataList(voPage).write(response, fileName);
            }
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /**
     * 查询用户认证信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getAuthList", method = RequestMethod.POST)
    @ResponseBody
    public WebResult authList(@RequestParam("userId") String userId) {
        try {
            String cacheKey = "apply_auth_list_" + userId;
            List<OperationLogVO> list = (List<OperationLogVO>) JedisUtils.getObject(cacheKey);
            if (list == null) {
                list = operationLogService.getByUserId(userId);
                JedisUtils.setObject(cacheKey, list, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", list);
        } catch (RuntimeException e) {
            logger.error("查询用户认证信息异常：userId = " + userId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询用户认证信息异常：userId = " + userId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 删除认证信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "delAuthByStatus", method = RequestMethod.POST)
    @ResponseBody
    public WebResult delAuthByStatus(String userId, Integer status) {
        try {
            operationLogService.delAuthByStatus(userId, status);
            return new WebResult("1", "提交成功");
        } catch (RuntimeException e) {
            logger.error("删除认证信息：userId = " + userId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("删除认证信息：userId = " + userId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 根据用户id查询客户订单
     */
    @RequestMapping(value = "custApplyList")
    @ResponseBody
    public WebResult custApplyList(String id) {
        List<Map<String, Object>> list = loanApplyService.custApplyList(id);
        if (null != list) {
            list = setStatus(list);
            return new WebResult("1", "查询成功", list);
        }
        return new WebResult("99", "系统异常");
    }

    /**
     * 根据手机号查询客户订单
     */
    @RequestMapping(value = "applyListByMobile")
    @ResponseBody
    public WebResult applyListByMobile(String mobile) {
        CustUserVO vo = custUserService.getCustUserByMobile(mobile);
        if (null != vo) {
            List<Map<String, Object>> list = loanApplyService.custApplyList(vo.getId());
            if (null != list) {
                list = setStatus(list);
                return new WebResult("1", "查询成功", list);
            }
        }
        return new WebResult("99", "没有进件信息");
    }

    private List<Map<String, Object>> setStatus(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            map.put("productId", LoanProductEnum.getDesc((String) map.get("productId")));
            Integer status = (Integer) map.get("status");
            if (status < XjdLifeCycle.LC_APPLY_0) {
                map.put("status", "未进件");
            } else if ((status <= XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3 && status >= XjdLifeCycle.LC_APPLY_1)
                    && (status != XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2 && status != XjdLifeCycle.LC_AUTO_AUDIT_2)
                    && (status != XjdLifeCycle.LC_ARTIFICIAL_AUDIT_1 && status != XjdLifeCycle.LC_AUTO_AUDIT_1)) {
                map.put("status", "待审核");
            } else if (status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_2 || status == XjdLifeCycle.LC_AUTO_AUDIT_2) {
                map.put("status", "申请被拒绝");
            } else if (status == XjdLifeCycle.LC_ARTIFICIAL_AUDIT_1 || status == XjdLifeCycle.LC_AUTO_AUDIT_1) {
                map.put("status", "申请已通过");
            } else if (status > XjdLifeCycle.LC_ARTIFICIAL_AUDIT_3 && status <= XjdLifeCycle.LC_LENDERS_0
                    && status != XjdLifeCycle.LC_CHANNEL_0) {
                map.put("status", "申请已通过,未放款");
            } else if (status == XjdLifeCycle.LC_CHANNEL_0) {
                map.put("status", "申请已取消");
            } else if (status <= XjdLifeCycle.LC_CASH_4 && status > XjdLifeCycle.LC_LENDERS_0) {
                map.put("status", "已放款");
            } else if (status == XjdLifeCycle.LC_REPAY_0) {
                map.put("status", "未还款");
            } else if (status == XjdLifeCycle.LC_REPAY_1) {
                map.put("status", "已提前还款");
            } else if (status == XjdLifeCycle.LC_REPAY_2) {
                map.put("status", "正常还款");
            } else if (status == XjdLifeCycle.LC_OVERDUE_0) {
                map.put("status", "逾期未还款");
            } else if (status == XjdLifeCycle.LC_OVERDUE_1) {
                map.put("status", "逾期已还款");
            }
        }
        return list;
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "getXianJinCardConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getXianJinCardConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                               @RequestParam(value = "userId", required = false) String userId) {
        try {

            String cacheKey = "xjbk_apply_contact_connect_info_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (data == null) {
                XianJinBaiKaCommonOP xianJinBaiKaAdditional = xianJinBaiKaService.getXianJinBaiKaAdditional(userId);
                XianJinBaiKaCommonOP xianJinBaiKaBase = xianJinBaiKaService.getXianJinBaiKaBase(userId);
                List<String> phoneList = xianJinBaiKaAdditional.getUser_additional().getAddressBook().getPhoneList();
                List<ContactList> contactList = xianJinBaiKaBase.getUser_verify().getOperatorReportVerify()
                        .getContactList();
                
                
                List<ContactCheck> contactChecks = new ArrayList<ContactCheck>();
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
                List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
                int remaider = contactChecks.size() % 2; // 余数
                int number = contactChecks.size() / 2; // 商
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<ContactCheck> value = null;
                    if (remaider > 0) {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
                        remaider--;
                        offset++;
                    } else {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
                    }
                    List<ContactCheck> value1 = new ArrayList<ContactCheck>();
                    value1.addAll(value);
                    result.add(value1);
                }
                int size = result.get(0).size();
                for (ContactCheck contactCheck : result.get(1)) {
                    contactCheck.setIndex(size);
                    size++;
                }
                data = new HashMap<String, Object>();
                data.put("data1", result.get(0));
                data.put("data3", result.get(1));
                data.put("data2", contactChecks);
                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    @RequestMapping(value = "getJDQConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getJDQConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                       @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "jdq_apply_contact_connect_info_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (data == null) {
                String orderNo = jdqService.getOrderNo(applyId);
                JDQReport jdqReport = jdqService.getReportData(orderNo);
                List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
                List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
                int remaider = contactChecks.size() % 2; // 余数
                int number = contactChecks.size() / 2; // 商
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<ContactCheck> value = null;
                    if (remaider > 0) {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
                        remaider--;
                        offset++;
                    } else {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
                    }
                    List<ContactCheck> value1 = new ArrayList<ContactCheck>();
                    value1.addAll(value);
                    result.add(value1);
                }
                int size = result.get(0).size();
                for (ContactCheck contactCheck : result.get(1)) {
                    contactCheck.setIndex(size);
                    size++;
                }
                data = new HashMap<>();
                data.put("data1", result.get(0));
                data.put("data3", result.get(1));
                data.put("data2", contactChecks);
                
                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }


    @RequestMapping(value = "getSLLConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getSLLConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                       @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "sll_apply_contact_connect_info_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (data == null) {
                String orderNo = sllService.getOrderNo(applyId);
                JDQReport jdqReport = sllService.getReportData(orderNo);
                List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
                List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
                int remaider = contactChecks.size() % 2; // 余数
                int number = contactChecks.size() / 2; // 商
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<ContactCheck> value = null;
                    if (remaider > 0) {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
                        remaider--;
                        offset++;
                    } else {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
                    }
                    List<ContactCheck> value1 = new ArrayList<ContactCheck>();
                    value1.addAll(value);
                    result.add(value1);
                }
                int size = result.get(0).size();
                for (ContactCheck contactCheck : result.get(1)) {
                    contactCheck.setIndex(size);
                    size++;
                }
                data = new HashMap<>();
                data.put("data1", result.get(0));
                data.put("data3", result.get(1));
                data.put("data2", contactChecks);

                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    @RequestMapping(value = "getDWDConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getDWDConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                       @RequestParam(value = "userId", required = false) String userId) {
        try {
            String cacheKey = "dwd_apply_contact_connect_info_" + userId + "_" + applyId;
            Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
            if (data == null) {

                String orderNo = dwdService.getOrderNo(applyId);
                DWDReport jdqReport = dwdService.getReportData(orderNo);
                List<ContactCheck> contactChecks = jdqReport.getContactCheckList();
                List<List<ContactCheck>> result = new ArrayList<List<ContactCheck>>();
                int remaider = contactChecks.size() % 2; // 余数
                int number = contactChecks.size() / 2; // 商
                int offset = 0;// 偏移量
                for (int i = 0; i < 2; i++) {
                    List<ContactCheck> value = null;
                    if (remaider > 0) {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset + 1);
                        remaider--;
                        offset++;
                    } else {
                        value = contactChecks.subList(i * number + offset, (i + 1) * number + offset);
                    }
                    List<ContactCheck> value1 = new ArrayList<ContactCheck>();
                    value1.addAll(value);
                    result.add(value1);
                }
                int size = result.get(0).size();
                for (ContactCheck contactCheck : result.get(1)) {
                    contactCheck.setIndex(size);
                    size++;
                }
                data = new HashMap<>();
                data.put("data1", result.get(0));
                data.put("data3", result.get(1));
                data.put("data2", contactChecks);
                
                JedisUtils.setObject(cacheKey, data, Global.TWO_HOURS_CACHESECONDS);
            }
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * @param @param applyId
     * @param @param userId
     * @Title: getRongConnectInfo
     * @Description: 获取融360用户通讯录信息
     */
    @RequestMapping(value = "/getRongConnectInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResult getRongConnectInfo(@RequestParam(value = "applyId", required = false) String applyId,
                                        @RequestParam(value = "userId", required = false) String userId) {
        try {
			
             Object data =  reportService.getRongConnectInfo(applyId, userId);
            return new WebResult("1", "提交成功", data);
        } catch (RuntimeException e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", e.getMessage());
        } catch (Exception e) {
            logger.error("查询通讯录信息异常：applyId = " + applyId, e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 取消客户标的
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "cancelLid", method = RequestMethod.POST)
    @ResponseBody
    public WebResult cancelLid(@RequestParam("lid") String lid) {
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isNotBlank(lid)) {
            try {
                String url = Global.getConfig("deposit.cancelCustBid.url");
                param.put("lid", lid);
                logger.info("取消客户标的http请求：[{}]", JsonMapper.toJsonString(param));
                Map<String, Object> map = (Map<String, Object>) RestTemplateUtils.getInstance().postForObject(url,
                        param, Map.class);
                logger.info("取消客户标的http响应：[{}]", JsonMapper.toJsonString(map));
                if (null != map && Global.BANKDEPOSIT_SUCCSS_OK.equals((String) map.get("status"))) {

                    return new WebResult("OK", (String) map.get("message"));
                } else {
                    return new WebResult("NOK", (String) map.get("message"));
                }
            } catch (Exception e) {
                return new WebResult("NOK", "系统异常");
            }
        }
        return new WebResult("NOK", "标的不存在");
    }

    /**
     * 平台重新放款
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "afreshPay", method = RequestMethod.POST)
    @ResponseBody
    public WebResult afreshPay(@RequestParam("lid") String lid, @RequestParam("type") String type) {
        User user = UserUtils.getUser();
        logger.info("重新放款--->{}--->{}--->{}--->{}", user.getId(), user.getName(), lid, type);
        if ("2".equals(type)) {
            Boolean flag = shopWithholdService.isWithholdSuccess(lid);
            if (!flag) {
                return new WebResult("NOK", "重新放款失败,请检查598是否扣款成功");
            }
        }
        Map<String, String> param = new HashMap<String, String>();
        if (StringUtils.isNotBlank(lid)) {
            try {
                String url = Global.getConfig("deposit.afreshPay.url");
                param.put("lid", lid);
                logger.info("重新放款http请求：[{}]", JsonMapper.toJsonString(param));
                Map<String, Object> map = (Map<String, Object>) RestTemplateUtils.getInstance().postForObject(url,
                        param, Map.class);
                logger.info("重新放款http响应：[{}]", JsonMapper.toJsonString(map));
                if (null != map && Global.BANKDEPOSIT_SUCCSS_OK.equals((String) map.get("status"))) {
                    return new WebResult("OK", (String) map.get("message"));
                } else {
                    return new WebResult("NOK", (String) map.get("message"));
                }
            } catch (Exception e) {
                return new WebResult("NOK", "系统异常");
            }
        }
        return new WebResult("NOK", "标的不存在");
    }


    @RequestMapping(value = "getJDQConnectDetail")
    public String getConnectDetail(String applyId, Model model) {
        String cacheKey = "jdq_original_connect_detail_" + applyId;
        Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
        if (null == data) {
            String orderNo = jdqService.getOrderNo(applyId);
            List<Calls> calls = jdqService.getPushBaseData(orderNo).getTelecom().getCalls();
            int remaider = calls.size() % 2; // 余数
            int number = calls.size() / 2; // 商
            int offset = 0;// 偏移量
            List<List<Calls>> result = new ArrayList<List<Calls>>();
            for (int i = 0; i < 2; i++) {
                List<Calls> value = null;
                if (remaider > 0) {
                    value = calls.subList(i * number + offset, (i + 1) * number + offset + 1);
                    remaider--;
                    offset++;
                } else {
                    value = calls.subList(i * number + offset, (i + 1) * number + offset);
                }
                List<Calls> value1 = new ArrayList<Calls>();
                value1.addAll(value);
                result.add(value1);
            }
            data = new HashMap<>();
            data.put("data1", result.get(0));
            data.put("data2", result.get(1));
            JedisUtils.setObject(cacheKey, data, 24 * 60 * 60);
        }
        model.addAttribute("connectDetail", data);
        return "modules/cust/connectDetail";
    }

    @RequestMapping(value = "getDWDConnectDetail")
    public String getDWDConnectDetail(String applyId, Model model) {
        String cacheKey = "dwd_original_connect_detail_" + applyId;
        Map<String, Object> data = (Map<String, Object>) JedisUtils.getObject(cacheKey);
        if (null == data) {
            String orderNo = dwdService.getOrderNo(applyId);
            List<com.rongdu.loans.loan.option.dwd.charge.Calls> calls = dwdService.getdwdChargeInfo(orderNo).getCharge().getData().getReport().getMembers().getTransactions().get(0).getCalls();
            int remaider = calls.size() % 2; // 余数
            int number = calls.size() / 2; // 商
            int offset = 0;// 偏移量
            List<List<com.rongdu.loans.loan.option.dwd.charge.Calls>> result = new ArrayList<List<com.rongdu.loans.loan.option.dwd.charge.Calls>>();
            for (int i = 0; i < 2; i++) {
                List<com.rongdu.loans.loan.option.dwd.charge.Calls> value = null;
                if (remaider > 0) {
                    value = calls.subList(i * number + offset, (i + 1) * number + offset + 1);
                    remaider--;
                    offset++;
                } else {
                    value = calls.subList(i * number + offset, (i + 1) * number + offset);
                }
                List<com.rongdu.loans.loan.option.dwd.charge.Calls> value1 = new ArrayList<com.rongdu.loans.loan.option.dwd.charge.Calls>();
                value1.addAll(value);
                result.add(value1);
            }
            data = new HashMap<>();
            data.put("data1", result.get(0));
            data.put("data2", result.get(1));
            JedisUtils.setObject(cacheKey, data, 24 * 60 * 60);
        }
        model.addAttribute("connectDetail", data);
        return "modules/cust/connectDetail";
    }
}
