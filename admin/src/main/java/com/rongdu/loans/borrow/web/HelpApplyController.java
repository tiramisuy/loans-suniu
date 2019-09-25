/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.borrow.option.HelpApplyForListOP;
import com.rongdu.loans.borrow.service.HelpApplyService;
import com.rongdu.loans.borrow.service.HelpResultService;
import com.rongdu.loans.borrow.vo.HelpApplyAllotVO;
import com.rongdu.loans.borrow.vo.HelpApplyVO;
import com.rongdu.loans.borrow.vo.HelpResultVO;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 助贷申请表Controller
 * 
 * @author liuliang
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/helpApply")
public class HelpApplyController extends BaseController {

	@Autowired
	private HelpApplyService helpApplyService;
	@Autowired
	private HelpResultService helpResultService;

	@RequestMapping(value = "list")
	public String list(HelpApplyForListOP op, Model model) {
		model.addAttribute("op", op);
		User user = UserUtils.getUser();
		Boolean iskefu = UserUtils.haveRole(user, "help_borrow");
		if (iskefu) {
			op.setUserId(user.getId());
		}
		Page page = new Page();
		page.setPageNo(op.getPageNo());
		page.setPageSize(op.getPageSize());
		Page<HelpApplyVO> voPage = helpApplyService.getBorrowHelpList(page, op);
		model.addAttribute("page", voPage);
		return "modules/borrow/borrowHelpList";
	}

	/**
	 * 加载营销提醒页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toConfimHelp")
	public WebResult toConfimHelp(@RequestParam(value = "id") String id, Model model) {
		try {

			HelpApplyVO applySimpleVO = helpApplyService.getHelpById(id);

			model.addAttribute("userId", applySimpleVO.getUserId());
			model.addAttribute("userName", applySimpleVO.getUserName());
			model.addAttribute("mobile", applySimpleVO.getMobile());
			model.addAttribute("applyTime", applySimpleVO.getApplyTime());
			model.addAttribute("source", applySimpleVO.getSource());
			model.addAttribute("borrowId", id);

			List<HelpResultVO> marketVo = helpResultService.getHelpResultByUserId(id);
			if (CollectionUtils.isNotEmpty(marketVo)) {
				model.addAttribute("resultList", marketVo);
			}
			return new WebResult("1", "提交成功", model);
		} catch (Exception e) {
			logger.error("加载催收页面异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 助贷产品营销
	 * 
	 * @param content
	 * @param marketId
	 * @param oldContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "borrowResultConfirm")
	public WebResult borrowResultConfirm(@RequestParam(value = "company") String company,
			@RequestParam(value = "borrowId") String borrowId, @RequestParam(value = "borrowAmt") BigDecimal borrowAmt,
			@RequestParam(value = "giveAmt") BigDecimal giveAmt, @RequestParam(value = "source") String source,
			@RequestParam(value = "status") String status, @RequestParam(value = "mobile") String mobile) {
		try {
			HelpResultVO vo = new HelpResultVO();
			vo.setId(IdGen.uuid());
			// vo.setUserId(userId);
			vo.setBorrowId(borrowId);
			vo.setLoanCompany(company);
			vo.setMobile(mobile);
			vo.setLoanAmt(borrowAmt);
			vo.setSuccAmt(giveAmt);
			vo.setSource(source);
			vo.setStatus(status);
			vo.setCreateBy(UserUtils.getUser().getName());
			vo.setUpdateBy(UserUtils.getUser().getName());
			helpResultService.insertHelpResult(vo);
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("提交信息异常！", e);
			return new WebResult("99", "系统异常！");
		}
	}

	/**
	 * 查询待分配的助贷营销员
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllCsUser")
	public WebResult getAllCsUser() {
		try {
			return new WebResult("1", "提交成功", UserUtils.getUserByRole("help_borrow"));
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
			String str = vo.getCompanyId();
			int s = str.indexOf("-");
			String uid = str.substring(0, s);
			String uname = str.substring(s + 1);

			for (String id : list) {
				int flag = helpApplyService.updateAllotApply(id, uid, uname,UserUtils.getUser().getName());
				if (flag <= 0) {
					return new WebResult("99", "更新重复分配信息失败！");
				}
			}
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("还款信息分配异常：op = ", e);
			return new WebResult("99", "系统异常");
		}
	}

}