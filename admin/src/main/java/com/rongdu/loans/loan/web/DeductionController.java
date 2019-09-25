package com.rongdu.loans.loan.web;

import com.rongdu.loans.common.WebResult;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.loan.op.DeductionFromOP;
import com.rongdu.loans.loan.option.DeductionApproveOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.service.DeductionLogService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.option.DeductionApplyOP;
import com.rongdu.loans.loan.vo.DeductionFromVO;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.sys.entity.Log;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.LogService;
import com.rongdu.loans.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by zhangxiaolong on 2017/7/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/deduction")
public class DeductionController extends BaseController {

	@Autowired
	private RepayPlanItemService repayPlanItemService;
	@Autowired
	private DeductionLogService deductionLogService;
	@Autowired
	private LogService logService;

	/**
	 * 减免申请
	 * 
	 * @param deductionApplyOP
	 * @return
	 */
	@RequestMapping(value = "apply", method = RequestMethod.POST)
	@ResponseBody
	public WebResult apply(@Valid DeductionApplyOP deductionApplyOP) {
		deductionApplyOP.setSource(5);
		User user = UserUtils.getUser();
		deductionApplyOP.setCreateBy(user.getName());
		try {
			deductionLogService.apply(deductionApplyOP);
		} catch (RuntimeException e) {
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			return new WebResult("99", "系统异常");
		}
		return new WebResult("1", "提交成功");
	}

	/**
	 * 审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "approve", method = RequestMethod.POST)
	@ResponseBody
	public WebResult approve(@Valid DeductionApproveOP deductionApproveOP) {
		User user = UserUtils.getUser();
		deductionApproveOP.setApproverId(user.getId());
		deductionApproveOP.setApproverName(user.getName());
		try {
			deductionLogService.approve(deductionApproveOP);
		} catch (RuntimeException e) {
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			return new WebResult("99", "系统异常");
		}
		return new WebResult("1", "提交成功");
	}

	/**
	 * 减免页面
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deductionFrom")
	public WebResult deductionFrom(@Valid DeductionFromOP deductionFromOP) {
		try {
			Integer status = deductionLogService.applyCheck(deductionFromOP.getId());
			// 减免审核状态 0待审核，1通过，2不通过, -1没有申请
			if ("1".equals(deductionFromOP.getSource())) {
				// 申请减免
				if (status == Global.DEDUCTION_INIT) {
					logger.error("申请减免失败，该笔还款计划已经提交减免申请。repayPlanItemId = " + deductionFromOP.getId());
					return new WebResult("99","申请减免失败，该笔还款计划已经提交减免申请");
				}
			} else {
				// 审核减免
				if (status != Global.DEDUCTION_INIT) {
					logger.error("审核减免失败，该笔还款计划没有正在审核的减免申请。repayPlanItemId = " + deductionFromOP.getId());
					return new WebResult("99","该笔还款计划没有正在审核的减免申请");
				}
			}
			DeductionFromVO deductionFromVO = new DeductionFromVO();
			deductionFromVO.setCtx(deductionFromOP.getCtx());
			deductionFromVO.setSource(deductionFromOP.getSource());
			RepayDetailListOP op = new RepayDetailListOP();
			op.setPageSize(Integer.MAX_VALUE);
			op.setStage(1);
			op.setId(deductionFromOP.getId());
			Page<RepayDetailListVO> page = repayPlanItemService.repayDetailList(op);
			if (CollectionUtils.isNotEmpty(page.getList())) {
				deductionFromVO.setRepayDetailListVO(page.getList().get(0));
			}
			deductionFromVO.setList(deductionLogService.deductionFrom(deductionFromOP.getId()));

			return new WebResult("1", "提交成功", deductionFromVO);
		} catch (RuntimeException e) {
			logger.error("查询减免异常：deductionFromOP = " + JsonMapper.getInstance().toJson(deductionFromOP), e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询减免异常：deductionFromOP = " + JsonMapper.getInstance().toJson(deductionFromOP), e);
			return new WebResult("99", "系统异常");
		}
	}
	
	/**
	 * 减免还款明细金额
	 * 
	 * @param deductionApplyOP
	 * @return
	 */
	@RequestMapping(value = "deductionRepayDetailAmount", method = RequestMethod.POST)
	@ResponseBody
	public WebResult deductionRepayDetailAmount(@RequestParam(value = "repayPlanItemId") String repayPlanItemId,
			@RequestParam(value = "deductionDetailAmt") String deductionDetailAmt,
			@RequestParam(value = "deductionReason") String deductionReason) {
		User user = UserUtils.getUser();
		logger.info("减免还款明细金额--->{}--->{}--->{}", user.getId(), user.getName(), repayPlanItemId);
		// 插入日志
		Log entity = new Log();
		entity.setTitle("还款明细-减免金额");
		entity.setCreateBy(user);
		entity.setParams("repayPlanItemId="+repayPlanItemId);
		logService.save(entity);
		try {
			synchronized (DeductionController.class) {
				if (StringUtils.isBlank(deductionDetailAmt)) {
					deductionDetailAmt = "0";
				}
				int num = repayPlanItemService.deductionRepayDetail(repayPlanItemId,deductionDetailAmt,deductionReason,user.getId(),user.getName());
				if (num > 0) {
					return new WebResult("1", "处理成功");
				}
			}
		} catch (Exception e) {
			return new WebResult("99", "系统异常");
		}
		return new WebResult("99", "系统异常");
	}
	
	/**
	 * 验证减免申请
	 * @return
	 */
	@RequestMapping(value = "checkDeduction", method = RequestMethod.POST)
	@ResponseBody
	public WebResult checkDeduction(@RequestParam(value = "repayPlanDetailItemId") String repayPlanDetailItemId) {		
		Integer status = deductionLogService.applyCheck(repayPlanDetailItemId);
		// 减免审核状态 0待审核，1通过，2不通过, -1没有申请
		if (status != Global.DEDUCTION_INIT) {
			return new WebResult("1", "处理成功");
		}
		logger.error("申请减免失败，该笔还款计划已经提交减免申请。repayPlanItemId = " + repayPlanDetailItemId);
		return new WebResult("99", "申请减免失败，该笔还款计划有正在申请的减免");
	}
}
