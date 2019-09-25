package com.rongdu.loans.cust.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.cust.vo.CustUserExportVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.CustUserExportService;
import com.rongdu.loans.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/sys/expCustUser/")
public class CustUserExportController extends BaseController {
	@Autowired
	private CustUserExportService expCustService;
	
	@Autowired
	private ChannelService channelService;

	@RequestMapping(value = "expLoanApplyList")
	public String expLoanApplyList(CustUserExportVO op, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("expCustuserOP", op);
		
		 List<ChannelVO> channel = channelService.findAllChannel();	//渠道下拉框
	        if (null != channel) {
	            model.addAttribute("channel", channel);
	        }
	        
		Page<CustUserExportVO> page = new Page<CustUserExportVO>();
		if (op.getApproveResult() != null) {
			int approveResult = op.getApproveResult();
			switch (approveResult) {
			case 0:// 未买加急券 loan_apply中 status =410
				op.setApproveResult(0);
				op.setStatus(410);
				page = expCustService.findAllLoanApply(
						new Page<CustUserExportVO>(request, response), op);
				break;
			case 1: // 注册未申请 cust_user 中存在 ，loan_apply 中不存在
				op.setApproveResult(1);
				page = expCustService.findAllLoanApply(
						new Page<CustUserExportVO>(request, response), op);
				break;
			case 2: // 被拒的 loan_apply中 approve_result (2,4)
				op.setApproveResult(2);
				page = expCustService.findAllLoanApply(
						new Page<CustUserExportVO>(request, response), op);
				break;
			case 3: // 未绑卡的 cust_user 中card_no 为空的数据
				op.setApproveResult(3);
				page = expCustService.findAllLoanApply(
						new Page<CustUserExportVO>(request, response), op);
				break;
			case 4: // 应还未复贷
				op.setApproveResult(4);
				page = expCustService.findAllLoanApply(
						new Page<CustUserExportVO>(request, response), op);
				break;
			default:
				page = new Page<CustUserExportVO>();
				break;
			}
		}
		model.addAttribute("appesult",op.getApproveResult());
		model.addAttribute("page", page);

		return "modules/cust/expCustUserList";
	}

	@RequestMapping(value = "exportApply")
	@ExportLimit
	public String exportFile(CustUserExportVO op, HttpServletRequest request,
			Model model, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		logger.info("导出客户数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("客户数据", CustUserExportVO.class);
			String fileName = "客户信息报表" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";

			if (op.getApproveResult() != null) {
				int approveResult = op.getApproveResult();
				switch (approveResult) {
				case 0:// 未买加急券 loan_apply中 status =410
					op.setApproveResult(0);
					op.setStatus(410);
					break;
				case 1: // 注册未申请 cust_user 中存在 ，loan_apply 中不存在
					op.setApproveResult(1);
					break;
				case 2: // 被拒的 loan_apply中 approve_result (2,4)
					op.setApproveResult(2);
					break;
				case 3: // 未绑卡的 cust_user 中card_no 为空的数据
					op.setApproveResult(3);
					break;
				case 4: // 应还未复贷
					op.setApproveResult(4);
					break;
				default:
					break;
				}
			}

			List<CustUserExportVO> volist = expCustService.findExpCustUser(op);

			excel.setDataList(volist).write(response, fileName);
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出客户数据失败！失败信息：" + e.getMessage());
		} finally {
			if (excel != null)
				excel.dispose();
		}
		return "modules/cust/expCustUserList";
	}

}
