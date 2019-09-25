/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.web;

import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.AOTUCHECK_NO_PASS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.AOTUCHECK_PASS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.APPLY_SUCCESS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.BRING_FORWARD_REPAY;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.CANCAL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.CASH_WITHDRAWAL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.HAS_BEEN_LENDING;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.MANUALCHECK_NO_PASS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.MANUALCHECK_PASS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.MANUAL_CHECK;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.MANUAL_RECHECK;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.OVERDUE_REPAY;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.OVERDUE_WAITING_REPAY;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.PUSH_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.PUSH_SUCCESS;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.REPAY;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.SIGNED;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.VERIFICATION;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_AOTUCHECK;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_LENDING;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_MANUALCHECK;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_PUSH;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_REPAY;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_SIGN;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_VERIFICATION;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WAITING_WITHDRAWAL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL;
import static com.rongdu.loans.enums.ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.RoleControl;
import com.rongdu.loans.common.RoleControlParam;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.cust.option.QueryUserOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.QueryUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.WhetherEnum;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.op.CheckOP;
import com.rongdu.loans.loan.option.ApplyAllotOP;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.LoanCheckOP;
import com.rongdu.loans.loan.service.ApplyAllotService;
import com.rongdu.loans.loan.service.RefuseReasonService;
import com.rongdu.loans.loan.vo.ApplyAllotVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * 贷款订单分配表Controller
 * @author liuliang
 * @version 2018-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/applyAllot")
public class ApplyAllotController extends BaseController {

	private static String CONTRACT_PREFIX = "HT";
	private static final String APPROVE_LOCK_KEY_PREFIX = "rengong_allot_lock_";
	private static final int APPROVE_LOCK_CACHESECONDS = 60 * 60 * 24;
	
	@Autowired
	private ApplyAllotService applyAllotService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private RefuseReasonService refuseReasonService;
	
	
	/**
	 * 定义已过审页面订单状态下拉框枚举
	 */
	private static List<ApplyStatusLifeCycleEnum> pageViewList = Arrays.asList(WAITING_PUSH, PUSH_SUCCESS,
			WAITING_REPAY, REPAY, CANCAL, WAITING_VERIFICATION, VERIFICATION);

	/**
	 * 定义已过审页面订单状态下拉框与实际数据库状态的一对多映射关系
	 */
	private static Map<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>> relationMap = new HashMap<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>>();

	static {
		relationMap.put(WAITING_PUSH, Arrays.asList(WAITING_PUSH, PUSH_FAIL));
		relationMap.put(PUSH_SUCCESS, Arrays.asList(PUSH_SUCCESS));

		relationMap.put(WAITING_REPAY, Arrays.asList(HAS_BEEN_LENDING, WITHDRAWAL_SUCCESS, WITHDRAWAL_FAIL,OVERDUE_WAITING_REPAY,WAITING_REPAY));
		relationMap.put(REPAY, Arrays.asList(BRING_FORWARD_REPAY, REPAY, OVERDUE_REPAY));
		relationMap.put(CANCAL, Arrays.asList(CANCAL));
		relationMap.put(WAITING_VERIFICATION, Arrays.asList(WAITING_VERIFICATION));
		relationMap.put(VERIFICATION, Arrays.asList(VERIFICATION));
	}

	
	 /**
	  * 贷款订单列表
	  * @param applyOP
	  * @param model
	  * @return
	  */
		@RequestMapping(value = "list")
		public String list(ApplyOP applyOP, Model model) {
			List<User> list = UserUtils.getUserByRole("xinshen");
			if (null != list) {
				List<User> auditorList = new ArrayList<User>();
				for (User user : list) {
					if (UserUtils.haveRole(UserUtils.getUser(), "ccd")
							&& UserUtils.haveRole(UserUtils.get(user.getId()), "ccd")) {
						auditorList.add(user);
					} else if ((UserUtils.haveRole(UserUtils.getUser(), "jbqb") || (UserUtils.haveRole(UserUtils.getUser(),
							"xjdfq")))
							&& (UserUtils.haveRole(UserUtils.get(user.getId()), "jbqb") || UserUtils.haveRole(
									UserUtils.get(user.getId()), "xjdfq"))) {
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
			getStatusList(applyListOP, page);
			Page<ApplyAllotVO> voPage = null;

			RoleControlParam roleControlParam = RoleControl.get(applyListOP.getProductId(), applyOP.getCompanyId());
			// applyListOP.setProductId(roleControlParam.getProductId());
			applyListOP.setCompanyId(roleControlParam.getCompanyId());

			voPage = applyAllotService.getAllotApplyList(page, applyListOP);

			getStatusNameForPage(voPage);
			if (applyOP.getStage() == 1 || applyOP.getStage() == 5) {
				for (ApplyAllotVO applyVO : voPage.getList()) {
					Map<String, Object> approveLockMap = getApproveLockMap(applyVO.getId());
					if (approveLockMap != null) {
						applyVO.setStatusStr("[" + approveLockMap.get("userName") + "]审核中");
					}
				}
			}

			model.addAttribute("page", voPage);
			return "modules/loan/allotList";
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
				return pageViewList;
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
						PUSH_FAIL.getValue(), CANCAL.getValue(), WAITING_LENDING.getValue(), HAS_BEEN_LENDING.getValue(),
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

				checkStatus = StringUtils.isBlank(autoCheck) ? "" : StringUtils.equals(autoCheck,
						WhetherEnum.YES.getValue()) ? "1" : "3";
				applyListOP.setCheckStatus(checkStatus);
				applyListOP.setStatus(null);// 清空
				page.setOrderBy("time desc");
				break;
			case 3:
				applyListOP.setStatusList(Arrays.asList(AOTUCHECK_NO_PASS.getValue(), MANUALCHECK_NO_PASS.getValue()));
				checkStatus = StringUtils.isBlank(autoCheck) ? "" : StringUtils.equals(autoCheck,
						WhetherEnum.YES.getValue()) ? "2" : "4";
				applyListOP.setCheckStatus(checkStatus);
				page.setOrderBy("time desc");
				break;
			case 4:
				applyListOP.setStatusList(Arrays.asList(WAITING_PUSH.getValue(), PUSH_SUCCESS.getValue(),
						PUSH_FAIL.getValue(), WAITING_LENDING.getValue()));
				checkStatus = StringUtils.isBlank(autoCheck) ? "" : StringUtils.equals(autoCheck,
						WhetherEnum.YES.getValue()) ? "1" : "3";
				applyListOP.setCheckStatus(checkStatus);
				page.setOrderBy("time desc");
				break;
			case 5:
				applyListOP.setStatusList(Arrays.asList(MANUAL_RECHECK.getValue()));
				page.setOrderBy("applyTime");
				break;
			default:
				logger.warn("stage参数错误,stage={}", applyListOP.getStage());
				break;
			}
		}
		
		
		/**
		 * 根据预先定义的状态映射关系，将数据库中的状态转换成页面上输出的状态
		 * 
		 * @param voPage
		 */
		private void getStatusNameForPage(Page<ApplyAllotVO> voPage) {
			List<ApplyAllotVO> list = voPage.getList();
			if (CollectionUtils.isNotEmpty(list)) {
				for (ApplyAllotVO vo : list) {
					for (Map.Entry<ApplyStatusLifeCycleEnum, List<ApplyStatusLifeCycleEnum>> entry : relationMap.entrySet()) {
						List<ApplyStatusLifeCycleEnum> value = entry.getValue();
						if (value.contains(ApplyStatusLifeCycleEnum.get(vo.getStatus()))) {
							vo.setStatus(entry.getKey().getValue());
							continue;
						}
					}
				}
			}

		}
		
		
		

		@SuppressWarnings("unchecked")
		private Map<String, Object> getApproveLockMap(String applyId) {
			Map<String, Object> approveLockMap = null;
			String approveLockKey = APPROVE_LOCK_KEY_PREFIX + applyId;
			String approveLockJson = JedisUtils.get(approveLockKey);
			if (StringUtils.isNotBlank(approveLockJson)) {
				approveLockMap = (Map<String, Object>) JsonMapper.fromJsonString(approveLockJson, Map.class);
			}
			return approveLockMap;
		}

		
		
		/**
		 * 审核/详情 页面
		 * 
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "checkAllotFrom")
		public String checkAllotFrom(@RequestParam(value = "id") String id, @RequestParam(value = "sign") String sign,
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

			ApplyAllotVO applySimpleVO = applyAllotService.getById(applyId);
			model.addAttribute("productId", applySimpleVO.getProductId());
			model.addAttribute("applyInfo", applySimpleVO);

			if (StringUtils.equals(sign, "check")) {
				if (UserUtils.haveRole("superRole")) {
					model.addAttribute("options", refuseReasonService.findAll());
				} else {
					if (isApproveLocked(applyId)) {
						addMessage(redirectAttributes, "此订单正在审核中...");
						return "redirect:" + adminPath + "/loan/applyAllot/list";
					} else {
						model.addAttribute("options", refuseReasonService.findAll());
						addApproveLock(applyId);
					}
				}
			}
				return "modules/cust/allotUserView";
		}

		
		
		/**
		 * 审核
		 * 
		 * @return
		 */
		@RequestMapping(value = "checkAllot")
		public String check(@Valid CheckOP checkOP, HttpServletRequest request, RedirectAttributes redirectAttributes) {
			if (isApproveLocked(checkOP.getApplyId())) {
				addMessage(redirectAttributes, "此订单正在审核中...");
				return "redirect:" + adminPath + "/loan/applyAllot/list";
			}
			ApplyAllotVO applySimpleVO = applyAllotService.getById(checkOP.getApplyId());
			if (applySimpleVO == null) {
				throw new RuntimeException("审核订单异常 : apply not find");
			}
			
			LoanCheckOP opc = BeanMapper.map(checkOP, LoanCheckOP.class);
			String operatorId = StringUtils.isNotBlank(opc.getOperatorId()) ? opc.getOperatorId()
					: Global.DEFAULT_OPERATOR_ID;
			String operatorName = StringUtils.isNotBlank(opc.getOperatorName()) ? opc.getOperatorName()
					: Global.DEFAULT_OPERATOR_NAME;
			
			String remark = StringUtils.isNotBlank(applySimpleVO.getRemark()) ? applySimpleVO.getRemark() : "";
			remark = StringUtils.isNotBlank(checkOP.getRemark()) ? remark + ";" + checkOP.getRemark() : remark;

			synchronized (ApplyAllotController.class) {
				ApplyAllotOP updateEntity = new ApplyAllotOP();
				updateEntity.setId(checkOP.getApplyId());
				updateEntity.setApproveResult(checkOP.getCheckResult().equals(1) ? 3 : 4);

				Integer	status = checkOP.getCheckResult().equals(1) ? WAITING_PUSH.getValue() : MANUALCHECK_NO_PASS.getValue();
				updateEntity.setApproverId(operatorId);
				updateEntity.setApproverName(operatorName);

				updateEntity.setStatus(status);
				updateEntity.setRemark(remark);
				updateEntity.setUpdateBy(operatorName);
				updateEntity.setApproveTime(new Date());
				updateEntity.setStage(ApplyStatusLifeCycleEnum.getStage(status));
				
				String contractNo = CONTRACT_PREFIX + updateEntity.getId();
				updateEntity.setContNo(contractNo);
				applyAllotService.updateAllot(updateEntity);
			}
			return "redirect:" + adminPath + "/loan/applyAllot/list";
		}

		
		
		
		@SuppressWarnings("unchecked")
		private boolean isApproveLocked(String applyId) {
			User user = UserUtils.getUser();
			String approveLockKey = APPROVE_LOCK_KEY_PREFIX + applyId;
			String approveLockJson = JedisUtils.get(approveLockKey);
			if (StringUtils.isNotBlank(approveLockJson)) {
				Map<String, Object> approveLockMap = (Map<String, Object>) JsonMapper.fromJsonString(approveLockJson,
						Map.class);
				String cacheUserId = String.valueOf(approveLockMap.get("userId"));
				if (!user.getId().equals(cacheUserId)) {
					return true;
				}
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		private void addApproveLock(String applyId) {
			User user = UserUtils.getUser();
			String approveLockKey = APPROVE_LOCK_KEY_PREFIX + applyId;
			String approveLockJson = JedisUtils.get(approveLockKey);
			if (StringUtils.isNotBlank(approveLockJson)) {
				Map<String, Object> approveLockMap = (Map<String, Object>) JsonMapper.fromJsonString(approveLockJson,
						Map.class);
				String cacheUserId = String.valueOf(approveLockMap.get("userId"));
				if (user.getId().equals(cacheUserId)) {
					approveLockMap.put("userId", user.getId());
					approveLockMap.put("userName", user.getName());
					JedisUtils.set(approveLockKey, JsonMapper.toJsonString(approveLockMap), APPROVE_LOCK_CACHESECONDS);
				}
			} else {
				Map<String, Object> approveLockMap = new HashMap<String, Object>();
				approveLockMap.put("userId", user.getId());
				approveLockMap.put("userName", user.getName());
				JedisUtils.set(approveLockKey, JsonMapper.toJsonString(approveLockMap), APPROVE_LOCK_CACHESECONDS);
			}
		}
		
		
		/**
		 * 取消
		 */
		@ResponseBody
		@RequestMapping(value = "cancel")
		public WebResult cancel(@RequestParam(value = "applyId") String applyId) {
			try {
				synchronized (ApplyController.class) {
					applyAllotService.updateCancel(applyId);
				}
				return new WebResult("1", "提交成功", null);
			} catch (Exception e) {
				logger.error("取消异常：applyId = " + applyId, e);
				return new WebResult("99", "取消异常");
			}
		}
		

		/**
		 * 撤销-重新审核
		 */
		@ResponseBody
		@RequestMapping(value = "resetcheck")
		public WebResult resetcheck(@RequestParam(value = "applyId") String applyId) {
			try {
				synchronized (ApplyController.class) {
					applyAllotService.updateResetCheck(applyId);
					// 释放订单
					JedisUtils.del(APPROVE_LOCK_KEY_PREFIX + applyId);
				}
				return new WebResult("1", "提交成功", null);
			} catch (Exception e) {
				logger.error("重新审核异常：applyId = " + applyId, e);
				return new WebResult("99", "系统异常");
			}
		}

}