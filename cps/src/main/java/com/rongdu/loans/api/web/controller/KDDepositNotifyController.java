package com.rongdu.loans.api.web.controller;

import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.web.option.KDComplianceBorrowPageOP;
import com.rongdu.loans.api.web.option.KDDepositNotifyLoansOP;
import com.rongdu.loans.api.web.option.KDDepositNotifyOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.service.PayLogService;
import com.rongdu.loans.koudai.vo.PayLogListVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDNotifyResponseVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountNotifyDataVO;
import com.rongdu.loans.koudai.vo.deposit.user.KDOpenAccountNotifyVO;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.RongPointCutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

/**
 * Created by zcb on 2018/11/12. liuzhuang
 */
@Controller
@RequestMapping(value = "kdai/deposit/notify")
public class KDDepositNotifyController extends BaseController {

	@Autowired
	private CustUserService custUserService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private KDDepositService kdDepositService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             accountOpenRetNotify
	 * @Description: 开户同步页面
	 */
	@RequestMapping(value = "accountOpenRet")
	public String accountOpenRetNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("{}-{}:{}", "口袋", "存管开户同步回跳", JsonMapper.toJsonString(request.getParameterMap()));
		String returnUrl = request.getParameter("returnUrl");
		if (returnUrl != null) {
			return "redirect:" + returnUrl;
		}
		return "redirect:http://api.jubaoqiandai.com/#/errorPage";
		// KDOpenAccountNotifyVO vo = (KDOpenAccountNotifyVO)
		// JsonMapper.fromJsonString(json, KDOpenAccountNotifyVO.class);
		// KDNotifyResponseVO ret = new KDNotifyResponseVO();
		// ret.setData("");
		// if ("0".equals(vo.getRetCode())) {
		// return "redirect:http://api.jubaoqiandai.com/#/successPage";
		// } else {
		//
		// }
	}

	/**
	 * @return KDNotifyResponseVO 返回类型
	 * @throws @Title:
	 *             accountOpenCallbackNotify
	 * @Description: 开户异步页面
	 */
	// {"retCode":"0","txCode":"accountOpenEncryptPage","retData":{"accountId":"6212461620000000101","idNum":"421003199210134023","cardNo":"6236682870008974276","mobile":"13277091298","openMobile":"13277091298"}}
	@RequestMapping(value = "accountOpenCallback")
	@ResponseBody
	public KDNotifyResponseVO accountOpenCallbackNotify(@RequestBody String json, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		json = decodeReqParam(json);
		logger.info("{}-{}:{}", "口袋", "存管开户异步回调", json);

		KDOpenAccountNotifyVO vo = (KDOpenAccountNotifyVO) JsonMapper.fromJsonString(json, KDOpenAccountNotifyVO.class);
		KDNotifyResponseVO ret = new KDNotifyResponseVO();
		ret.setData("");
		if ("0".equals(vo.getRetCode())) {// 开户成功

			KDOpenAccountNotifyDataVO retData = vo.getRetData();

			CustUserVO custUserVO = custUserService.getCustUserByMobile(vo.getRetData().getMobile());

			// if(retData != null){
			// if(StringUtils.isNotBlank(retData.getCardNo())){//开户卡号
			// custUserVO.setCardNo(retData.getCardNo());
			// }
			// if(StringUtils.isNotBlank(retData.getOpenMobile())){//开户手机号
			// custUserVO.setBankMobile(retData.getOpenMobile());
			// }
			// }

			custUserVO.setKdAccountId(vo.getRetData().getAccountId());// 更新口袋存管账户id
			int result = custUserService.updateCustUser(custUserVO);
			if (result > 0) {
				ret.setCode("0");
				ret.setMessage("\u5f00\u6237\u56de\u8c03\u6210\u529f");
			} else {
				ret.setCode("1");
				ret.setMessage("\u5f00\u6237\u56de\u8c03\u5931\u8d25");
			}
		} else {
			ret.setCode("1");
			ret.setMessage("\u5f00\u6237\u56de\u8c03\u5931\u8d25");
		}
		return ret;
	}

	@RequestMapping(value = "pwdResetRet")
	@ResponseBody
	public String pwdResetRetNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("{}-{}:{}", "口袋", "存管重置密码同步回跳");
		return "交易完成";
	}

	/**
	 * 合规借款用户信息 同步页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrowRet")
	public String borrowRet(HttpServletRequest request, HttpServletResponse response) {
		logger.info("{}-{}:{}", "口袋", "合规借款用户信息 同步页面", JsonMapper.toJsonString(request.getParameterMap()));
		String returnUrl = request.getParameter("returnUrl");
		String applyId = request.getParameter("applyId");
		if (returnUrl != null) {
            JedisUtils.set(Global.KD_OPEN3+ applyId, "lock", Global.THREE_DAY_CACHESECONDS);// SLL口袋存管开户成功标识
            //rongPointCutService.sllLendPoint(applyId, 1);
			rongPointCutService.creatAccount(applyId, "绑卡成功", true);// RONG,SLL绑卡成功口袋存管开户时，切面通知的切入点标记
			return "redirect:" + returnUrl;
		}
		// KDDepositRetVO vo = (KDDepositRetVO) JsonMapper.fromJsonString(json,
		// KDDepositRetVO.class);
		//
		// if ("0".equals(vo.getRetCode())) {
		// return "redirect:http://api.jubaoqiandai.com/#/successPage";
		// }
		// return "redirect:http://api.jubaoqiandai.com/#/errorPage";
		return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

	/**
	 * 确认借款(异步回调)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "borrowNotify")
	@ResponseBody
	public KDNotifyResponseVO borrowNotify(@RequestBody String reqString) {
		reqString = decodeReqParam(reqString);
		logger.info("口袋确认借款接收参数：" + reqString);
		KDNotifyResponseVO ret = new KDNotifyResponseVO();
		ret.setCode("0");
		try {
			KDDepositNotifyOP op = (KDDepositNotifyOP) JsonMapper.fromJsonString(reqString, KDDepositNotifyOP.class);
			KDComplianceBorrowPageOP complianceBorrowPageOP = BeanMapper.map(op.getRetData(),
					KDComplianceBorrowPageOP.class);
			String applyId = complianceBorrowPageOP.getOrderId();
			if (!"0".equals(op.getRetCode())) {// 非成功状态
				ret.setCode("500");
				return ret;
			}

			String lockKey = "kdcg_notify_apply_lock_" + applyId;
			boolean lock = JedisUtils.setLock(lockKey, String.valueOf(System.nanoTime()), 5);
			if (!lock) {
				logger.error("口袋确认借款处理失败,订单被锁定，单号：" + applyId);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// ret.setCode("500");
				// return ret;
			}

			// 开户确认回调 更新放款渠道,只更新状态为410的订单(410更新为411)
			loanApplyService.updateKDPayChannel(applyId);
			logger.info("口袋确认借款处理成功，applyId：" + applyId);
		} catch (Exception e) {
			logger.error("口袋确认借款处理失败", e);
			ret.setCode("500");
			return ret;
		}

		return ret;
	}

	/**
	 * 口袋放款、提现 异步通知
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "loansCallback")
	@ResponseBody
	public KDNotifyResponseVO loansCallback(@RequestBody String reqString) {
		reqString = decodeReqParam(reqString);
		logger.info("口袋放款、提现 异步通知参数：" + reqString);
		KDNotifyResponseVO ret = new KDNotifyResponseVO();
		ret.setCode("0");
		KDDepositNotifyLoansOP op = (KDDepositNotifyLoansOP) JsonMapper.fromJsonString(reqString,
				KDDepositNotifyLoansOP.class);

		String applyId = op.getOriginalData().getOrderBase().getOutTradeNo();

		String lockKey = "kdcg_notify_apply_lock_" + applyId;
		boolean lock = JedisUtils.setLock(lockKey, String.valueOf(System.nanoTime()), 5);
		if (!lock) {
			logger.error("口袋订单被锁定，单号：" + applyId);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// ret.setCode("500");
			// return ret;
		}

		try {
			if ("30004".equals(op.getRetCode())) {// 放款成功

				logger.info("口袋放款通知参数：" + reqString);

				String order_id = null;
				if (op.getRetData() != null) {
					Map retData = (Map) op.getRetData();
					order_id = retData.get("order_id") == null ? "" : retData.get("order_id").toString();
				}

				// 放款操作
				contractService.processKDDepositLendPay(applyId,
						new Date(op.getRequestContent().getLoanTime() * 1000L));
				// 保存paylog
				kdDepositService.saveOrUpdatePayLogStatus(applyId, order_id, 0);

				try {
					// 放款后 上传还款计划
					kdDepositService.pushAssetRepaymentPeriod(applyId);
				} catch (Exception e) {
					logger.error("口袋上传还款计划失败，applyId：" + applyId, e);
				}
				logger.info("口袋放款处理成功，applyId：" + applyId);
				// ytodo 0217 待提现
				//rongPointCutService.sllLendPoint(applyId, 1);// Rong360放款时，切面通知的切入点标记
				rongPointCutService.lendPoint(applyId, 1);// Rong，SLL口袋存管放款时，切面通知的切入点标记
			} else if ("30001".equals(op.getRetCode())) {// 提现成功
				logger.info("口袋提现 异步通知参数：" + reqString);
				// String applyId =
				// op.getOriginalData().getOrderBase().getOutTradeNo();
				// 提现操作 更新为提现成功
				loanApplyService.updateApplyStatus(applyId, ApplyStatusLifeCycleEnum.WITHDRAWAL_SUCCESS.getValue());

				// 更新paylog 为已提现
				kdDepositService.updatePayLogStatus(applyId, 1);
				logger.info("口袋提现处理成功，applyId：" + applyId);
				// ytodo 0217 提现成功
				rongPointCutService.lendPoint(applyId, 2);// Rong360口袋存管提现时，切面通知的切入点标记
			} else if ("30002".equals(op.getRetCode())) {// 提现失败
				logger.info("口袋提现失败 异步通知参数：" + reqString);
				// String applyId =
				// op.getOriginalData().getOrderBase().getOutTradeNo();
				// 提现操作 更新为提现失败
				// loanApplyService.updateApplyStatus(applyId,
				// ApplyStatusLifeCycleEnum.WITHDRAWAL_FAIL.getValue());
				logger.info("口袋提现处理失败，applyId：" + applyId);
			} else if ("30003".equals(op.getRetCode())) {// 提现冲正
				logger.info("口袋提现冲正 异步通知参数：" + reqString);
				// String applyId =
				// op.getOriginalData().getOrderBase().getOutTradeNo();
				// 提现操作 更新为募集中
				loanApplyService.updateApplyStatus(applyId, ApplyStatusLifeCycleEnum.PUSH_SUCCESS.getValue());
				// 更新paylog 为未提现
				// kdDepositService.updatePayLogStatus(applyId,0);
				PayLogListVO payLogListVO = new PayLogListVO();
				payLogListVO.setApplyId(applyId);
				payLogListVO.setWithdrawStatus(0);
				payLogListVO.setPayStatus(1);
				payLogListVO.setKdPayMsg("提现冲正");
				payLogService.updateByApplyId(payLogListVO);
				logger.info("口袋提现冲正，applyId：" + applyId);
			}
		} catch (Exception e) {
			logger.error("口袋放款、提现 处理失败,业务类型码:" + op.getTxCode(), e);
			ret.setCode("500");
			return ret;
		}

		return ret;
	}

	/**
	 * 授权信息 同步页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "repayAuthRet")
	public String repayAuthRet(HttpServletRequest request, HttpServletResponse response) {
		logger.info("{}-{}:{}", "口袋", "授权信息 同步页面", JsonMapper.toJsonString(request.getParameterMap()));
		String returnUrl = request.getParameter("returnUrl");
		if (returnUrl != null) {
			return "redirect:" + returnUrl;
		}
		// KDDepositRetVO vo = (KDDepositRetVO) JsonMapper.fromJsonString(json,
		// KDDepositRetVO.class);
		//
		// if ("0".equals(vo.getRetCode())) {
		// return "redirect:http://api.jubaoqiandai.com/#/successPage";
		// }
		return "redirect:http://api.jubaoqiandai.com/#/errorPage";
	}

	/**
	 * 提现同步页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "withdrawRet")
	public String withdrawRet(HttpServletRequest request, @RequestParam(value = "applyId") String applyId) {
		logger.info("{}-{}:{}", "口袋", "提现同步页面", JsonMapper.toJsonString(request.getParameterMap()));
		String returnUrl = request.getParameter("returnUrl");
		if (returnUrl != null) {
			return "redirect:" + returnUrl;
		}
		// return "redirect:http://api.jubaoqiandai.com/#/errorPage";
		// if(StringUtils.isNotBlank(applyId)){
		// //更新paylog 为提现中
		// //kdDepositService.updatePayLogStatus(applyId,2);
		// }
		return "redirect:http://api.jubaoqiandai.com/#/successPage";
	}

	/**
	 * @return String 返回类型
	 * @throws @Title:
	 *             decodeReqParam
	 * @Description: 对请求进行编码处理
	 */
	@SuppressWarnings("deprecation")
	private String decodeReqParam(String json) {

		json = URLDecoder.decode(json);
		if (json.indexOf("=") > 0) {// 口袋json参数末尾有=
			json = json.substring(0, json.indexOf("="));
		}
		return json;
	}

}
