package com.rongdu.loans.pay.service;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.common.RespInfo;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryCardBinService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 查询银行卡bin信息
	 * @param bankCode
	 * @param cardNo
	 * @return
	 */
	public RespInfo queryCardBin(String bankCode, String cardNo) {
		long start = System.currentTimeMillis();
		String respString = sendRequest( cardNo);
		long end = System.currentTimeMillis();
		logger.info("银行卡bin查询-宝付处理耗时：{}毫秒",(end-start));
		//宝付公钥
		String  cerpath = this.getClass().getResource(BaofooConfig.pubkey_path).getPath();
		respString = RsaCodingUtil.decryptByPubCerFile(respString,cerpath);
		try {
			respString = SecurityUtil.Base64Decode(respString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> result = (Map<String, String>) JsonMapper.fromJsonString(respString, HashMap.class);
		RespInfo respInfo = new RespInfo();
		if (result==null||result.get("resp_code")==null) {
			respInfo.setCode(ErrInfo.ERROR.getCode());
			respInfo.setMsg(ErrInfo.ERROR.getMsg());
		}else {
			if (isRespSuccess(result)) {
				String bankAbbr = result.get("pay_code");
				String actualBankCode = bankAbbr;
				respInfo.put("bankAbbr", bankAbbr);
				respInfo.put("bankName", result.get("bank_description"));
				respInfo.put("cardType", result.get("card_type"));
				respInfo.put("cardDesc", result.get("card_description"));
				respInfo.put("cardBin", result.get("card_bin"));
				respInfo.put("actualBankCode", actualBankCode);
				respInfo.put("cardNo", cardNo);
				respInfo.put("bankCode", bankCode);
				if (StringUtils.isNotBlank(bankCode)) {
					respInfo.put("match", bankCode.equals(actualBankCode));
				}		
				respInfo.setMsg(result.get("resp_msg"));
			}else {
				respInfo.setCode(result.get("resp_code"));
				respInfo.setMsg(result.get("resp_msg"));
			}
		}
		logger.info("银行卡bin查询-应答结果：{}",respInfo);
		return respInfo;
	}
	
	private String sendRequest(String cardNo) {
		//版本号
		String version = "4.0.0.0";
		//交易类型
		String txnType = "3001";
		//交易子类
		String txnSubType = "01361";
		//商户号
		String memberId = BaofooConfig.member_id;
		//终端号
		String terminalId = BaofooConfig.authpay_terminal_id;
		//加密数据类型
		String dataType = "json";
		//加密数据
		String dataContent = null;
		//商户流水号
		String transSerialNo = "CARDBIN"+System.nanoTime();
		//订单日期
		String tradeDate = DateUtils.getDate("yyyyMMddHHmmss");
		//银行卡号
		String bankCardNo = cardNo;
		//请求地址
		String url= BaofooConfig.card_bin_url;
	      //商户私钥
		String  pfxpath = this.getClass().getResource(BaofooConfig.keystore_path).getPath();
        Map<String,Object> encryptParams = new HashMap<String,Object>();        
        encryptParams.put("txn_type", txnType);
        encryptParams.put("txn_sub_type", txnSubType);
        encryptParams.put("terminal_id", terminalId);
        encryptParams.put("member_id", memberId);
        encryptParams.put("trans_serial_no", transSerialNo);
        encryptParams.put("trade_date", tradeDate);
        encryptParams.put("bank_card_no", bankCardNo);
        String encryptRequest = JsonMapper.toJsonString(encryptParams);
        logger.info("银行卡bin查询-加密参数：{}",encryptRequest);
		try {
			encryptRequest = SecurityUtil.Base64Encode(encryptRequest);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		dataContent = RsaCodingUtil.encryptByPriPfxFile(encryptRequest,pfxpath,BaofooConfig.keystore_password);	
		Map<String, String> params = new HashMap<String, String>();
		params.put("version", version);
		params.put("txn_type", txnType);
		params.put("txn_sub_type", txnSubType);
		params.put("member_id", memberId);
		params.put("terminal_id", terminalId);
		params.put("data_type", dataType);
		params.put("data_content", dataContent);
		url = url+"?version={version}&txn_type={txn_type}&txn_sub_type={txn_sub_type}&member_id={member_id}&terminal_id={terminal_id}&data_type={data_type}&data_content={data_content}";
		RestTemplate client = createRestTemplate();
		String respString = client.getForObject(url, String.class, params);	
		return respString;
	}

	private boolean isRespSuccess(Map<String, String> resp) {
		return StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000");
	}
	
	private  RestTemplate createRestTemplate() {
		RestTemplate client = new RestTemplate();
		StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
		List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
		mcList.add(mc);
		client.setMessageConverters(mcList);
		return client;
	}

}
