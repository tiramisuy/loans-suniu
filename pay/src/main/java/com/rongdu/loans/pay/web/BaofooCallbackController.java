/**
 * Copyright &copy; 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MoneyUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.loan.service.PayLogService;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.baofoo.demo.base.TransContent;
import com.rongdu.loans.pay.baofoo.demo.base.response.TransRespBF0040010;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.util.SecurityUtil;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.EAccoutSyncUtils;
import com.rongdu.loans.pay.utils.WithdrawErrInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *宝付支付-支付结果回调
 * @author sunda
 * @version 2017-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/")
public class BaofooCallbackController extends BaseController {
    
	@Autowired
	private PayLogService payLogService;
	@Autowired
	private RepayLogService repayLogService;
	@Autowired
	private BaofooWithdrawService baofooWithdrawService;
	@Autowired
	private CustUserService userService;
	
	private static List<String> ipWhiteList = null;
	
	/**
	 * 代付-异步回调地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/withraw/return-url.html")
	public String withrawReturnUrl(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		checkIpAddress("宝付支付-代付-异步回调",request,response);
//		String memberId = request.getParameter("member_id");//商户流水号
//		String terminalId = request.getParameter("terminal_id");//支付结果
//		String dataType = request.getParameter("data_type");//支付结果描述
//		String result = request.getParameter("data_content");//实际成功金额
//		if (StringUtils.isAnyBlank(memberId,terminalId,result)) {
//			Servlets.writeString(response,"ERROR");
//			return null;
//		}
//		String pubKeyPath = this.getClass().getResource(BaofooConfig.pubkey_path).getPath();
//		TransContent<TransRespBF0040010> resp = new TransContent<TransRespBF0040010>(dataType);
//		/**
//		 * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
//		 *
//		 * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
//		 *
//		 * 再次通过BASE64解密：new String(new Base64().decode(reslut))
//		 *
//		 * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
//		 */
//		// 明文返回处理可能是报文头参数不正确、或其他的异常导致；
//		if (result.contains("trans_content")) {
//			// 明文返回
//			// 我报文错误处理
//			resp = (TransContent<TransRespBF0040010>) resp.str2Obj(result, TransRespBF0040010.class);
//			// 业务逻辑判断
//		} else {
//			// 密文返回
//			// 第一步：公钥解密
//			result = RsaCodingUtil.decryptByPubCerFile(result, pubKeyPath);
//			// 第二步BASE64解密
//			result = SecurityUtil.Base64Decode(result);
//			resp = (TransContent<TransRespBF0040010>) resp.str2Obj(result, TransRespBF0040010.class);
//		}
//		logger.info("宝付-代付异步回调-应答结果：{}" , result);
//		List<TransRespBF0040010> datas =  resp.getTrans_reqDatas();
//		if (datas!=null&&!datas.isEmpty()) {
//			TransRespBF0040010 ts =datas.get(0);
//			PayLogVO pw = payLogService.get(ts.getTrans_no());
//			String succCode = ErrInfo.SUCCESS.getCode();
//			//悲观锁，只有未成功的订单，才能继续处理
//			if (pw!=null&&!succCode.equals(pw.getStatus())) {
//				pw.setChlOrderNo(ts.getTrans_orderid());
//				pw.setUpdateTime(new Date());
//				pw.setTxFee(MoneyUtils.toDecimal(ts.getTrans_fee()));
//				pw.setId(ts.getTrans_no());
//				pw.setSuccTime(DateUtils.parseDate(ts.getTrans_endtime(), "yyyyMMddHHmmss"));
//				pw.setSuccAmt(MoneyUtils.toDecimal(ts.getTrans_money()));
//				pw.setStatus(ts.getState());
//				pw.setRemark(getRemarks(ts.getState(),ts.getTrans_remark()));
//				if (WithdrawErrInfo.E1.getCode().equals(ts.getState())) {
//					pw.setStatus(WithdrawErrInfo.S.getCode());
//				}
//				//异步通知代付成功，并且更新本地订单状态
//				int updateRows = payLogService.updatePayResult(pw);
//				if(1==updateRows){
//					//输出OK，告知宝付，我们已经收到付款成功通知
//					Servlets.writeString(response,"OK");
//				}else{
//					logger.info("宝付支付-代付-异步回调，收到通知，但未更新本地订单状态");
//				}
//			}
//		}
		Servlets.writeString(response,"ERROR");	
		return null;
	}
	
	/**
	 * 只能接受白名单IP的通知
	 * @param msg
	 * @param request
	 */
	private void checkIpAddress(String msg, HttpServletRequest request,HttpServletResponse response) {
		String ip = Servlets.getIpAddress(request);
		logger.info("{}：{}",msg,ip);
		Servlets.printAllParameters(request);
		if (!getIpWhiteList().contains(ip)) {
			logger.info("{}：非法的IP访问-{}",msg,ip);
			//throw new BizException("禁止访问");
		}
	}

	/**
	 * 获取IP白名单
	 * 
	 * @return
	 */
	private static List<String> getIpWhiteList() {
		if (null == ipWhiteList) {
			String[] temp = StringUtils.split(BaofooConfig.baofoo_ip_whitelist, ',');
			ipWhiteList=  Arrays.asList(temp);
		}
		return ipWhiteList;
	}

	/**
	 * 认证支付-异步回调地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auth-pay/return-url.html")
	public void authPayReturnUrl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		checkIpAddress("宝付-认证支付-异步通知",request,response);
		String dataContent = request.getParameter("data_content");	
		if (StringUtils.isNotBlank(dataContent)) {
	       //宝付公钥
	        String  cerpath = this.getClass().getResource(BaofooConfig.pubkey_path).getPath();
	        dataContent = RsaCodingUtil.decryptByPubCerFile(dataContent,cerpath);
			//判断解密是否正确，如果为空则宝付公钥不正确
			if(dataContent.isEmpty()){
				logger.debug("宝付-认证支付-异步通知：dataContent为空，检查解密公钥是否正确！");  
			}	
			dataContent = SecurityUtil.Base64Decode(dataContent);
			logger.debug("宝付-认证支付-异步通知：应答结果（解密）：{}",dataContent);  
			//将JSON转化为Map对象
			Map<String,String> resp = (Map<String,String>)JsonMapper.fromJsonString(dataContent, HashMap.class);
			if (isRespSuccess(resp)) {
				//商户订单号
				String orderNo  = resp.get("trans_id");
				RepayLogVO order = repayLogService.get(orderNo);
				String succCode = ErrInfo.SUCCESS.getCode();
				//悲观锁，只有未成功的订单，才能继续处理
				if (order!=null) {
					if (succCode.equals(order.getStatus())) {
						logger.info("宝付支付-认证支付-异步通知，该笔支付订单已经完成，无需重复处理");
					}else {				
						//宝付交易号
						String payOrderNo = resp.get("trans_no");
						String terminalId = String.valueOf(resp.get("terminal_id"));
						String memberId = String.valueOf(resp.get("member_id"));
						String succAmtFen = resp.get("succ_amt");
						String respMsg = resp.get("resp_msg");		
						order.setId(orderNo);
						String succAmtYuan = MoneyUtils.fen2yuan(succAmtFen);
						order.setSuccAmt(MoneyUtils.toDecimal(succAmtYuan));
						order.setRemark(respMsg);
						order.setStatus(succCode);
						order.setTerminalId(terminalId);
						order.setSuccTime(new Date());	
						order.setChlOrderNo(payOrderNo);
						int updateRows = repayLogService.updateRepayResult(order);
						if(1==updateRows){
//							baofooWithdrawService.withraw(order);
						}else {
							logger.info("宝付-认证支付-异步通知:支付成功，收到通知，但未更新本地订单状态");
						}
						//同步电子账户余额及线下充值流水
						new EAccoutSyncUtils().syncEAccoutRecord(order.getUserId());
						//输出OK，告知宝付，不要重复通知
						Servlets.writeString(response,"OK");							
					}
				}else {
					logger.info("宝付支付-认证支付-异步通知，该笔交易掉单，商户订单号为：{}",orderNo);
				}
			}else {
				logger.info("宝付支付-认证支付-异步通知，该笔支付订单未成功，{}：{}",resp.get("resp_code"),resp.get("resp_msg"));
			}
		}else {
			logger.info("宝付-认证支付-异步通知：dataContent为空" );
		}
		Servlets.writeString(response,"ERROR");	
	}
	
	private boolean isRespSuccess(Map<String, String> resp) {
		return StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000");
	}

	private String getRemarks(String state, String defaultValue) {
		if (StringUtils.isNotBlank(defaultValue)) {
			return defaultValue;
		}else {
			for (WithdrawErrInfo i:WithdrawErrInfo.values()) {
				if (i.getCode().equals(state)) {
					return i.getMsg();
				}
			}	
			return null;
		}
	}
	
}