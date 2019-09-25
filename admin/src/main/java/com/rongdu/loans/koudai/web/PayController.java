package com.rongdu.loans.koudai.web;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.koudai.op.PayLogListOP;
import com.rongdu.loans.koudai.service.KDDepositService;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.koudai.service.PayLogService;
import com.rongdu.loans.koudai.vo.PayLogListVO;
import com.rongdu.loans.loan.option.ApiResultVO;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.vo.AdminWebResult;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.sys.entity.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/koudai")
public class PayController extends BaseController {

	@Autowired
	private PayLogService kdPayLogService;
	@Autowired
	private KDPayService kdPayService;
	@Autowired
	private LoanApplyService loanApplyService;
	@Autowired
	private KDDepositService kdDepositService;

	@RequestMapping(value = "/payLog/list")
	public String list(PayLogListOP payLogListOP,Boolean first, Model model) {
    	if (null != first && first) {
            model.addAttribute("page", new Page(1,10));
            return "modules/koudai/payLogList";
        }
    	Page page = new Page();
		page.setPageNo(payLogListOP.getPageNo());
		page.setPageSize(payLogListOP.getPageSize());
		page.setOrderBy("pay_time desc");
		Page voPage = kdPayLogService.findList(page, payLogListOP);
		model.addAttribute("page", voPage);
		return "modules/koudai/payLogList";
	}

	
	
	@RequestMapping(value = "/payLog/exportList", method = RequestMethod.POST)
	@ExportLimit()
	public void exportList(PayLogListOP payLogListOP, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		User user = UserUtils.getUser();
		logger.info("导出口袋放款明细数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("放款明细数据", PayLogListVO.class);
			String fileName = "放款明细数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			
			Page page = new Page();
			page.setPageSize(50000);
			page.setOrderBy("pay_time desc");
			Page voPage = kdPayLogService.findList(page, payLogListOP);
			if(voPage !=null){
				List<PayLogListVO> list = voPage.getList();
				excel.setDataList(list).write(response, fileName);
			}
		} finally {
			if (excel != null)
				excel.dispose();
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/adminPay")
	public WebResult adminPay(String id) {
		try {
			AdminWebResult result = kdPayService.adminPay(id);
			return new WebResult(result.getCode(), result.getMsg());
		} catch (Exception e) {
			logger.error("手动放款异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/adminCreate")
	public WebResult adminCreate(String id) {
		try {
			AdminWebResult result = kdPayService.adminCreate(id);
			return new WebResult(result.getCode(), result.getMsg());
		} catch (Exception e) {
			logger.error("手动创建订单异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/adminCancel")
	public WebResult adminCancel(String id) {
		try {
			AdminWebResult result = kdPayService.adminCancel(id);
			return new WebResult(result.getCode(), result.getMsg());
		} catch (Exception e) {
			logger.error("取消订单异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getContract")
	public WebResult getContract(String applyId) {
		User user = UserUtils.getUser();
		logger.info("查询口袋合同--->{}--->{}--->{}", user.getId(), user.getName(),applyId);
		try {
			Map<String, Object> result = kdPayService.getConctract(applyId);
			if (0 == (Integer)result.get("retCode")) {
				List<String> contract = (List<String>) result.get("retData");
				return new WebResult("1",(String)result.get("retMsg"),contract);
			}
			return new WebResult("99",(String)result.get("retMsg"));
		} catch (Exception e) {
			logger.error("查询合同异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryStatus")
	public WebResult queryStatus(String applyId) {
		User user = UserUtils.getUser();
		logger.info("查询放款状态--->{}--->{}--->{}", user.getId(), user.getName(),applyId);
		try {
			Map<String, Object> result = kdPayService.queryStatus(applyId);
			if (0 == (Integer)result.get("retCode")) {
				Map<String, Object> data = (Map<String, Object>) result.get("retData");
				String resultMsg = null;
				if (null != data) {
					Integer status = (Integer) data.get("status");
					switch (status) {
					case 1:
						resultMsg = "放款中";
						break;
					case 2:
						resultMsg = "放款成功(钱放到电子账户)";
						break;
					case 4:
						resultMsg = "提现冲正 ";
						break;
					case 5:
						resultMsg = "放款成功(受托支付)";
						break;
					case 6:
						resultMsg = "提现成功(钱到银行卡) ";
						break;
					case 7:
						resultMsg = "提现失败(可以再次发起提现) ";
						break;
					case 8:
						resultMsg = "订单不存在";
						break;
					case 9:
						resultMsg = "提现失败(不需要再次发起提现 风控拒绝订单)";
						break;
					case 10:
						resultMsg = "提现中";
						break;
					case 11:
						resultMsg = "订单取消";
						break;
					default:
						resultMsg = "查询失败";
						break;
					}
				}
				return new WebResult("1",(String)data.get("msg"),resultMsg);
			}
			return new WebResult("99",(String)result.get("retMsg"));
		} catch (Exception e) {
			logger.error("查询合同异常", e);
			return new WebResult("99", "系统异常");
		}
	}
	
	
	/**
	 * 修改放款渠道
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changePaychannel")
	public WebResult changePaychannel(@RequestParam(value = "id") String id,
			@RequestParam(value = "paychannel") String paychannel) {
		User user = UserUtils.getUser();
		logger.info("修改放款渠道--->{}--->{}", user.getId(), user.getName());
		try {
			String lockKey = "changePaychannel_lock_" + id;
			String requestId = String.valueOf(System.nanoTime());// 请求标识
			// 根据id防并发加锁
			boolean lock = JedisUtils.setLock(lockKey, requestId, 60*5);

			if (!lock) {
				logger.warn("修改放款渠道调用中，id= {}", id);
				return new WebResult("99", "操作频繁，请5分钟后再试！", null);
			}

			if (StringUtils.isBlank(id) || paychannel == null) {
				logger.warn("找不到参数，payLog= {}，paychannel= {}", id, paychannel);
				return new WebResult("99", "参数异常");
			}

			synchronized (PayController.class) {

				kdPayService.changePaychannel(id, paychannel);
				return new WebResult("1", "提交成功", null);
			}
		} catch (RuntimeException e) {
			logger.error("修改放款渠道异常：payLog = " + id, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("修改放款渠道异常：payLog = " + id, e);
			return new WebResult("99", "系统异常");
		} finally {
		}
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/queryAccount")
	public WebResult queryAccount(String applyId) {
		User user = UserUtils.getUser();
		logger.info("查询口袋开户信息--->{}--->{}--->{}", user.getId(), user.getName(),applyId);
		try {
			LoanApplySimpleVO apply = loanApplyService.getLoanApplyById(applyId);
			if (null != apply){
				Map<String, Object> result = kdPayService.queryAccount(apply.getIdNo());
				if (0 == (Integer)result.get("retCode")) {
					Map<String, Object> data = (Map<String, Object>) result.get("retData");
					if (null != data) {
						Map<String,String> map = new HashMap<>();
						map.put("bindCard",(String) data.get("bindCard"));
						map.put("mobile",(String) data.get("mobile"));
						map.put("bankName",(String) data.get("bankName"));
						return new WebResult("1","查询成功",map);
					}
					return new WebResult("99","查询失败");
				}
			}
			return new WebResult("99","订单不存在");
		} catch (Exception e) {
			logger.error("查询口袋开户信息", e);
			return new WebResult("99", "系统异常");
		}
	}
	/**
	 * 口袋上传还款计划
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pushAssetRepaymentPeriod")
	public WebResult pushAssetRepaymentPeriod(@RequestParam(value = "applyId") String applyId) {
		User user = UserUtils.getUser();
		logger.info("口袋上传还款计划", user.getId(), user.getName());
		try {			
			// 放款后 上传还款计划
			ApiResultVO resultVO = kdDepositService.pushAssetRepaymentPeriod(applyId);
			if(ErrInfo.SUCCESS.getCode().equals(resultVO.getCode())){
				return new WebResult("1", "提交成功", null);
			}else {
				return new WebResult("500", "上传还款计划失败，状态码："+resultVO.getCode()+",状态信息："+resultVO.getMsg(), null);
			}
			
			
		} catch (RuntimeException e) {
			logger.error("口袋上传还款计划异常：applyId = " + applyId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("口袋上传还款计划异常：applyId = " + applyId, e);
			return new WebResult("99", "系统异常");
		} finally {
		}
	}
	
}
