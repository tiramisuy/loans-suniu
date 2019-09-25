package com.rongdu.loans.loan.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.borrow.vo.HelpApplyAllotVO;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.cust.vo.CustUserAllotVO;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.option.LoanMarketCountOP;
import com.rongdu.loans.loan.option.LoanMarketManagementOP;
import com.rongdu.loans.loan.option.MarketAllotUserOP;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanMarketManagementService;
import com.rongdu.loans.loan.service.MarketAllotService;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.loan.vo.LoanMarketCountVO;
import com.rongdu.loans.loan.vo.LoanMarketManagementVO;
import com.rongdu.loans.loan.vo.MarketAllotVO;
import com.rongdu.loans.loan.vo.MarketManagementVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.CustUserExportService;
import com.rongdu.loans.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/loan/market")
public class LoanMarketManagementCntroller extends BaseController {

	@Autowired
	private LoanMarketManagementService loanMarketManagementService;

	@Autowired
	private LoanApplyService loanApplyService;

	@Autowired
	private CustUserExportService expCustService;

	@Autowired
	private MarketAllotService marketAllotService;

	@RequestMapping(value = "list")
	public String list(LoanMarketManagementOP op, Model model) {
		model.addAttribute("op", op);
		User user = UserUtils.getUser();
		Boolean iskefu = UserUtils.haveRole(user, "kefu_dianxiao");
		if (iskefu) {
			op.setUserId(user.getId());
		}

		// 获取可分配用户 ，传到页面用于查询 1为可分配；0为不可分配
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("allotFlag", 1);

		List<User> userList = UserUtils.getRoleUserByCondition("kefu_dianxiao", condition);
		model.addAttribute("userList", userList);

		model.addAttribute("loginUser", user.getId());

		Page page = new Page();
		page.setPageNo(op.getPageNo());
		page.setPageSize(op.getPageSize());
		Page<LoanMarketManagementVO> voPage = loanMarketManagementService.getMarketList(page, op);
		model.addAttribute("page", voPage);
		return "modules/loan/marketList";
	}
	
	
	
	/**
	 * 营销统计
	 * @param op
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "countList")
	public String countList(LoanMarketCountOP op, Model model) {
		model.addAttribute("op", op);
		User user = UserUtils.getUser();
		Boolean iskefu = UserUtils.haveRole(user, "kefu_dianxiao");
		if (iskefu) {
			op.setUserName(user.getId());
		}

		// 获取可分配用户 ，传到页面用于查询 1为可分配；0为不可分配
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("allotFlag", 1);

		List<User> userList = UserUtils.getRoleUserByCondition("kefu_dianxiao", condition);
		model.addAttribute("userList", userList);

		/*model.addAttribute("loginUser", user.getId());*/

		List<LoanMarketCountVO> voList = loanMarketManagementService.getMarketCountList( op);
		model.addAttribute("voList", voList);
		return "modules/loan/marketCountList";
	}
	
	
	

	@RequestMapping(value = "exportMarketList", method = RequestMethod.POST)
	@ExportLimit
	public void exportMarketList(LoanMarketManagementOP op, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes)throws IOException  {
		User user = UserUtils.getUser();
		logger.info("导出营销列表数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("营销列表数据", LoanMarketManagementVO.class);
			String fileName = "营销列表信息报表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<LoanMarketManagementVO> marketList = new ArrayList<LoanMarketManagementVO>();
			Boolean iskefu = UserUtils.haveRole(user, "kefu_dianxiao");
			if (iskefu) {
				op.setUserId(user.getId());
			}
			op.setPageSize(-1);
			marketList = loanMarketManagementService.findMarketList(op);
			excel.setDataList(marketList).write(response, fileName);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出营销列表数据失败！失败信息：" + e.getMessage());
		} finally {
			if (excel != null)
				excel.dispose();
		}
	}
	
	
	

	/**
	 * 查询待分配的营销员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllCsUser")
	public WebResult getAllCsUser() {
		try {
			// 获取可分配用户 ，传到页面用于查询 1为可分配；0为不可分配
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("allotFlag", 1);
			List<User> userList = UserUtils.getRoleUserByCondition("kefu_dianxiao", condition);

			return new WebResult("1", "提交成功", userList /*
														 * UserUtils.getUserByRole
														 * ("kefu_dianxiao")
														 */);
		} catch (Exception e) {
			logger.error("查询营销员信息异常", e);
			return new WebResult("99", "系统异常");
		}

	}

	/**
	 * 手动分配
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "doHandAllot")
	public WebResult doWarn(@Valid HelpApplyAllotVO vo) {
		try {
			List<String> list = Arrays.asList(StringUtils.split(vo.getIds(), "|"));
			List<String> userStr = Arrays.asList(StringUtils.split(vo.getCompanyId(), "|"));
			List<MarketAllotUserOP> userList = new ArrayList<MarketAllotUserOP>();
			for(String strUser : userStr){
				MarketAllotUserOP uop = new MarketAllotUserOP();
				String str = strUser;
				int s = str.indexOf("-");
				uop.setUserId(str.substring(0, s));
				uop.setUserName(str.substring(s + 1));
				userList.add(uop);
			}
			
			List<String> insertList = new ArrayList<String>();
			User user = UserUtils.getUser();
			int a = 0;
			int total = userList.size();
			if(total>0){
				for (String id : list) {
					MarketManagementVO market = loanMarketManagementService.getMarketByApplyId(id);
					if (market == null) { // 如果对应订单在分配表中没有数据就做插入操作
						insertList.add(id);
					} else { // 如果对应订单在分配表中已经分配过就做修改操作
						MarketAllotUserOP userOp = 	userList.get(a % total);
						
						market.setUserId(userOp.getUserId());
						market.setUserName(userOp.getUserName());
						market.setUpdateTime(new Date());
						market.setAllotDate(new Date());
						market.setUpdateBy(user.getName());
						loanMarketManagementService.updateMarket(market);
					}
					a++;
				}
				loanMarketManagementService.allotInsert(user.getName(),insertList,userList);
				return new WebResult("1", "提交成功", null);
			}else{
				return new WebResult("99", "没有可分配人员");
			}
		} catch (Exception e) {
			logger.error("还款信息分配异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 加载营销提醒页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toConfimMarket")
	public WebResult toConfimMarket(@RequestParam(value = "applyId") String applyId,
			@RequestParam(value = "marketId") String marketId, Model model) {
		try {

			LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);

			model.addAttribute("userName", applySimpleVO.getUserName());
			model.addAttribute("mobile", applySimpleVO.getMobile());
			model.addAttribute("applyTime", applySimpleVO.getApplyTime());
			model.addAttribute("approveAmt", applySimpleVO.getApproveAmt());

			MarketManagementVO marketVo = loanMarketManagementService.getMarketById(marketId);
			model.addAttribute("marketVo", marketVo);
			if (!"".equals(marketVo.getContent()) && marketVo.getContent() != null) {
				String[] conarr = marketVo.getContent().split("&gt;");
				model.addAttribute("conarr", conarr);
			}
			return new WebResult("1", "提交成功", model);
		} catch (Exception e) {
			logger.error("加载催收页面异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 营销提醒
	 * 
	 * @param content
	 * @param marketId
	 * @param oldContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "marketConfirm")
	public WebResult marketConfirm(@RequestParam(value = "content") String content,
			@RequestParam(value = "marketId") String marketId, @RequestParam(value = "oldContent") String oldContent) {
		try {

			if (!"".equals(oldContent)) {
				content = oldContent + "&gt;" + content;
			}
			MarketManagementVO repa = new MarketManagementVO();
			repa.setId(marketId);
			repa.setContent(content);
			repa.setIsWarn(1);
			repa.setWarnTime(DateUtils.getDateTime());
			repa.setUpdateBy(UserUtils.getUser().getName());
			loanMarketManagementService.updateMarket(repa);
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("提交还款提醒信息异常！", e);
			return new WebResult("99", "系统异常！");
		}
	}

	/**
	 * 加载营销结果页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toMarketResult")
	public WebResult toMarketResult(@RequestParam(value = "marketId") String marketId, Model model) {
		try {
			MarketManagementVO marketVo = loanMarketManagementService.getMarketById(marketId);
			model.addAttribute("marketVo", marketVo);
			return new WebResult("1", "提交成功", model);
		} catch (Exception e) {
			logger.error("加载催收页面异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 营销提醒
	 * 
	 * @param content
	 * @param marketId
	 * @param oldContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confimMarketResult")
	public WebResult confimMarketResult(@RequestParam(value = "resultType") Integer resultType,
			@RequestParam(value = "marketId") String marketId) {
		try {
			User user = UserUtils.getUser();
			MarketManagementVO repa = new MarketManagementVO();
			repa.setId(marketId);
			repa.setIsPush(resultType);
			repa.setUpdateTime(new Date());
			repa.setUpdateBy(user.getName());
			repa.setWarnTime(DateUtils.getDateTime());
			loanMarketManagementService.updateMarket(repa);
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("提交还款提醒信息异常！", e);
			return new WebResult("99", "系统异常！");
		}
	}

	/**
	 * @param @param applyOP
	 * @param @param model
	 * @return String 返回类型
	 * @Title: allotCollectionManage
	 * @Description: 分配催收人员列表
	 */
	@RequestMapping(value = "/marketCollectionManage")
	public String marketCollectionManage(@ModelAttribute(value = "applyOP") ApplyOP applyOP, Model model) {

		Map<String, Object> condition = new HashMap<String, Object>();

		String userName = applyOP.getUserName();
		String allotFlag = applyOP.getAllotFlag();

		if (!StringUtils.isEmpty(userName)) {
			condition.put("userName", userName.trim());
		}
		if (!StringUtils.isEmpty(allotFlag)) {
			condition.put("allotFlag", allotFlag.trim());
		}
		condition.put("role", "kefu_dianxiao");
		List<User> userList = UserUtils.getRoleUserByCondition("kefu_dianxiao", condition);
		model.addAttribute("userList", userList);
		return "modules/loan/marketCollectionManage";
	}

	/**
	 * 营销客户== 注册未申请，新用户列表 申请表中订单状态为1 已完结的 用户，老用户
	 * 
	 * @param op
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "marketNewList")
	public String marketNewList(CustUserAllotVO op, Model model,Boolean first, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("expCustuserOP", op);

		User user = UserUtils.getUser();
		Boolean iskefu = UserUtils.haveRole(user, "kefu_dianxiao");
		if (iskefu) {
			op.setAllotUserId(user.getId());
		}

		// 获取可分配用户 ，传到页面用于查询 1为可分配；0为不可分配
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("allotFlag", 1);

		List<User> userList = UserUtils.getRoleUserByCondition("kefu_dianxiao", condition);
		model.addAttribute("userList", userList);

		model.addAttribute("loginUser", user.getId());
		Page<CustUserAllotVO> page = new Page<CustUserAllotVO>();

		if (op.getApproveResult() != null) {
			op.setApproveResult(op.getApproveResult());
		} else {
			op.setApproveResult(1);
		}
		
		
		if (null != first && first) {
            model.addAttribute("page", new Page(1,10));
            return "modules/loan/marketNewList";
        }
		
		page = expCustService.findMarketingUser(new Page<CustUserAllotVO>(request, response), op);
		model.addAttribute("appesult", op.getApproveResult());
		model.addAttribute("page", page);

		return "modules/loan/marketNewList";
	}

	@RequestMapping(value = "exportMarketNewList", method = RequestMethod.POST)
	@ExportLimit
	public void exportMarketNewList(CustUserAllotVO op, HttpServletRequest request, Model model,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		logger.info("导出营销客户数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("营销客户数据", CustUserAllotVO.class);
			String fileName = "营销客户信息报表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<CustUserAllotVO> userList = new ArrayList<CustUserAllotVO>();
			if (op.getApproveResult() != null) {
				op.setApproveResult(op.getApproveResult());
			} else {
				op.setApproveResult(1);
			}
			op.setPageSize(-1);
			userList = expCustService.exportMarketUser(op);
			excel.setDataList(userList).write(response, fileName);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出营销客户数据失败！失败信息：" + e.getMessage());
		} finally {
			if (excel != null)
				excel.dispose();
		}
	}

	/**
	 * 营销客户==营销客户手动分配
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "custMarketAllot")
	public WebResult custMarketAllot(@Valid HelpApplyAllotVO vo) {
		try {
			List<String> list = Arrays.asList(StringUtils.split(vo.getIds(), "|"));
			User user = UserUtils.getUser();
			List<String> userStr = Arrays.asList(StringUtils.split(vo.getCompanyId(), "|"));
			List<MarketAllotUserOP> userList = new ArrayList<MarketAllotUserOP>();
			for(String strUser : userStr){
				MarketAllotUserOP uop = new MarketAllotUserOP();
				String str = strUser;
				int s = str.indexOf("-");
				uop.setUserId(str.substring(0, s));
				uop.setUserName(str.substring(s + 1));
				userList.add(uop);
			}
			List<String> insertList = new ArrayList<String>();

			int a = 0;
			int total = userList.size();
			if(total > 0){
				for (String id : list) {
					
					MarketAllotVO allot = marketAllotService.getMarketByCustUserId(id);
					MarketAllotUserOP userOp = 	userList.get(a % total);
	
					if (allot == null) { // 如果对应订单在分配表中没有数据就做插入操作
						insertList.add(id);
					} else { // 如果对应订单在分配表中已经分配过就做修改操作
						allot.setUserId(userOp.getUserId());
						allot.setUserName(userOp.getUserName());
						allot.setUpdateTime(new Date());
						allot.setUpdateBy(user.getName());
						allot.setAllotDate(new Date());
						marketAllotService.updateMarket(allot);
					}
					
					a++;
					
				}
				marketAllotService.allotInsert(user.getName(),insertList,userList);
			}else{
				return new WebResult("99", "没有可分配人员");
			}
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("营销客户信息分配异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 营销客户==加载营销结果页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toMarketNewResult")
	public WebResult toMarketNewResult(@RequestParam(value = "allotId") String allotId, Model model) {
		try {
			MarketAllotVO marketVo = marketAllotService.getAllotMarketByid(allotId);
			model.addAttribute("marketVo", marketVo);
			return new WebResult("1", "提交成功", model);
		} catch (Exception e) {
			logger.error("加载催收页面异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 营销客户==提交营销结果
	 * 
	 * @param content
	 * @param marketId
	 * @param oldContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confimAllotMarketResult")
	public WebResult confimAllotMarketResult(@RequestParam(value = "resultType") Integer resultType,
			@RequestParam(value = "allotId") String allotId) {
		try {
			User user = UserUtils.getUser();
			MarketAllotVO repa = new MarketAllotVO();
			repa.setId(allotId);
			repa.setIsPush(resultType);
			repa.setUpdateTime(new Date());
			repa.setUpdateBy(user.getName());
			repa.setWarnTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			marketAllotService.updateMarket(repa);
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("提交营销结果信息异常！", e);
			return new WebResult("99", "系统异常！");
		}
	}

}
