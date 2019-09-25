/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.web;

import com.rongdu.common.exception.BizException;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.pay.common.RespInfo;
import com.rongdu.loans.pay.service.QueryCardBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 银行卡bin查询接口
 * 为商户提供根据银行卡 bin 查询银行卡信息，通过银行卡号判断该卡所属的银行名称和卡类型。
 * 注：银行卡 BIN 查询基本支持国内所有银行卡
 * @author sunda
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/pay/")
public class QueryCardBinController extends BaseController {
	@Autowired
	private QueryCardBinService queryCardBinService;
	
	/**
 	* 银行卡bin查询接口
 	* @author sunda
 	*/
	@RequestMapping(value = "query-card-bin")
	@ResponseBody
	public RespInfo queryCardBin(HttpServletRequest request, HttpServletResponse response) {
		logger.info("========银行卡bin查询========");
		String bankCode = request.getParameter("bankCode");
		String cardNo = request.getParameter("cardNo");
		if (StringUtils.isBlank(cardNo)) {
			throw new BizException("银行卡号不能为空");
		}
		RespInfo respInfo = queryCardBinService.queryCardBin(bankCode,cardNo);
		return respInfo;
	}
	

}