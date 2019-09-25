package com.rongdu.loans.pay.baofoo.demo;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.util.HttpUtil;
import com.rongdu.loans.pay.utils.BaofooConfig;
import com.rongdu.loans.pay.utils.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AuthPayDemo {
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(AuthPayDemo.class);
	
	public static void main(String[] args) throws Exception {
        //银行卡编码
        String pay_code = "ICBC";
        //银行卡卡号
        String acc_no = "6222020111122220000";
        //身份证号码
        String id_card = "320301198502169142";
        //姓名
        String id_holder = "张宝";
        //银行预留手机号
        String mobile = "13412346137";
        Map<String, String>  result = null;
//        result = preBindCard(pay_code, acc_no, id_card, id_holder, mobile);	
//        result = confirmBindCard("123456", result.get("trans_id"));
//        cancelBind(result.get("bind_id"));
        
//        result = directBindCard(pay_code, acc_no, id_card, id_holder, mobile);
//        cancelBind(result.get("bind_id"));
        
//        result = directBindCard(pay_code, acc_no, id_card, id_holder, mobile);
//        queryBind(acc_no);
        
        result = directBindCard(pay_code, acc_no, id_card, id_holder, mobile);
        result = preAuthPay("201604271949318660", "1234.56", "127.0.0.1");
        result = confirmAuthPay("123456", result.get("business_no"));
        queryAuthPayStatus( result.get("trans_id"));
	}


	/**
	 * 11-预绑卡交易
	 * @param payCode
	 * @param accNo
	 * @param idCard
	 * @param idHolder
	 * @param mobile
	 */
	private static Map<String,String> preBindCard(String payCode, String accNo,
			String idCard, String idHolder, String mobile) {
		logger.debug("========11-预绑卡类交易===========");
		//交易子类
        String txn_sub_type = "11";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();       
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
        encryptParams.put("acc_no", accNo);//银行卡号
        encryptParams.put("trans_id", transId);//商户订单号
        encryptParams.put("id_card_type", "01");//证件类型固定01（身份证）
        encryptParams.put("id_card", idCard);
        encryptParams.put("id_holder", idHolder);
        encryptParams.put("mobile", mobile);
        encryptParams.put("acc_pwd", "");
        encryptParams.put("valid_date", "");
        encryptParams.put("valid_no", "");
        encryptParams.put("pay_code", payCode);	
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		return resp;
	}
	
	private static Map<String,String> confirmBindCard(String smsCode,String transId) {
		logger.debug("========12-确认绑卡交易===========");
		//交易子类
        String txn_sub_type = "12";  
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params); 
        //短信验证码
        encryptParams.put("sms_code", smsCode);
        //预绑卡订单号
        encryptParams.put("trans_id", transId);
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		return resp;
	}
	
	private static Map<String,String> directBindCard(String payCode, String accNo,
			String idCard, String idHolder, String mobile) {
		logger.debug("========01-直接绑卡交易===========");
		//交易子类
        String txn_sub_type = "01";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();       
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
        encryptParams.put("acc_no", accNo);//银行卡号
        encryptParams.put("trans_id", transId);//商户订单号
        encryptParams.put("id_card_type", "01");//证件类型固定01（身份证）
        encryptParams.put("id_card", idCard);
        encryptParams.put("id_holder", idHolder);
        encryptParams.put("mobile", mobile);
        encryptParams.put("acc_pwd", "");
        encryptParams.put("valid_date", "");
        encryptParams.put("valid_no", "");
        encryptParams.put("pay_code", payCode);	
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		return resp;
	}
	
	private static void cancelBind(String bindId) {
		logger.debug("========02-解除绑定关系交易===========");
        String txn_sub_type = "02";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();      
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
        //获取绑定标识	 
		encryptParams.put("bind_id",bindId); 
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
     
	}
	
	private static void queryBind(String accNo) {
		logger.debug("========03-查询绑定关系交易===========");
        String txn_sub_type = "03";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();        
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
        //银行卡号
		encryptParams.put("acc_no",accNo) ;
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
	}
	
	private static Map<String,String> preAuthPay(String bindId,String txnAmt,String ipAddr) {
		logger.debug("========15-认证支付类预支付交易===========");
        String txn_sub_type = "15";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();        
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params); 
      //金额转换成分
		BigDecimal  txn_amt_num = new BigDecimal(txnAmt).multiply(BigDecimal.valueOf(100));
		//支付金额
		txnAmt = String.valueOf(txn_amt_num.setScale(0));	
		//获取绑定标识 
        Map<String,String> ClientIp = new HashMap<String,String>();         
        ClientIp.put("client_ip", ipAddr);
        encryptParams.put("bind_id",bindId);           
        encryptParams.put("trans_id", transId);           
        encryptParams.put("risk_content", ClientIp); 
      //金额以分为单位(整型数据)并把元转换成分
        encryptParams.put("txn_amt", txnAmt); 
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		return resp;
	}
	
	private static Map<String,String> confirmAuthPay(String smsCode,String businessNo) {
		logger.debug("========16-认证支付类支付确认交易===========");
        String txn_sub_type = "16";
        //商户订单号
        String transId = "AP"+txn_sub_type+System.nanoTime();      
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
    	//支付短信验证码
        encryptParams.put("sms_code", smsCode);
        //宝付业务流水号
        encryptParams.put("business_no", businessNo);    
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		return resp;
	}
	
	private static void queryAuthPayStatus(String origTransId) {
		logger.debug("========06/31-交易状态查询交易===========");
        String txn_sub_type = "06";
        //商户订单号
        String trans_id = "AP06"+System.nanoTime();        
		Map<String, String> params = createParams(txn_sub_type);       
        Map<String, Object> encryptParams = createEncryptParams(params);           
        //原始商户订单号
        encryptParams.put("orig_trans_id", origTransId);    
		Map<String,String> resp = sendRequest(params, encryptParams);	
		if(StringUtils.isNotBlank(resp.get("resp_code"))&&resp.get("resp_code").equals("0000")){	
			
		}
		
	}
	
	private static Map<String, String> sendRequest(Map<String, String> params,Map<String,Object> encryptParams) {
        //测试地址
        String request_url = BaofooConfig.auth_pay_url;
       //商户私钥
        String  pfxpath = "C:/Users/MARK/Desktop/聚宝钱包二期/产品管理/0-账户与支付/宝付/接口文档/宝付认证支付API接口文档/Demo/JAVA/PreAuthentication/PreAuthentication/WebContent/CER/bfkey_100000276@@100000990.pfx";
       //宝付公钥
        String  cerpath = "C:/Users/MARK/Desktop/聚宝钱包二期/产品管理/0-账户与支付/宝付/接口文档/宝付认证支付API接口文档/Demo/JAVA/PreAuthentication/PreAuthentication/WebContent/CER/bfkey_100000276@@100000990.cer";
		String reqString = "";
		reqString = JsonMapper.toJsonString(encryptParams);
		logger.debug("认证支付-{}-请求参数：{}",getSubTypeMap().get(params.get("txn_sub_type")),reqString);        
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(reqString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxpath,"123456");	        
		params.put("data_content", data_content);       
		String respString  = HttpUtil.RequestForm(request_url, params);	
		logger.debug("认证支付-{}-应答结果：{}",getSubTypeMap().get(params.get("txn_sub_type")),respString);  
		respString = RsaCodingUtil.decryptByPubCerFile(respString,cerpath);
		//判断解密是否正确。如果为空则宝付公钥不正确
		if(respString.isEmpty()){
			logger.debug("认证支付-{}-检查解密公钥是否正确！",getSubTypeMap().get(params.get("txn_sub_type")));  
		}	
		try {
			respString = SecurityUtil.Base64Decode(respString);
			logger.debug("认证支付-{}-应答结果（解密）：{}",getSubTypeMap().get(params.get("txn_sub_type")),respString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//将JSON转化为Map对象
		Map<String,String> resp = null;
		 	resp = (Map<String,String>)JsonMapper.fromJsonString(respString, HashMap.class);
		logger.debug("认证支付-{}-应答代码：{}，应答消息:{}",getSubTypeMap().get(params.get("txn_sub_type")),resp.get("resp_code"),resp.get("resp_msg"));
		return resp;
	}

	private static Map<String, Object> createEncryptParams(Map<String, String> params) {
		/**
         * 报文体
         */
        Map<String,Object> encryptParams = new HashMap<String,Object>();        

        /**
         * 公共参数
         */
        String trade_date=DateUtils.getDate("yyyyMMddHHmmss");//交易日期        
        encryptParams.put("txn_sub_type", params.get("txn_sub_type"));
        encryptParams.put("biz_type", "0000");
        encryptParams.put("terminal_id", params.get("terminal_id"));
        encryptParams.put("member_id", params.get("member_id"));
        encryptParams.put("trans_serial_no", "SN"+System.nanoTime());
        encryptParams.put("trade_date", trade_date);
        encryptParams.put("additional_info", "附加信息");
        encryptParams.put("req_reserved", "保留");
		return encryptParams;
	}

	private static Map<String, String> createParams(String txnSubType) {
		Map<String,String> params = new HashMap<String,String>();      
        params.put("version", "4.0.0.0");
        params.put("member_id", "100000276");
        params.put("terminal_id", "100000990");
        params.put("txn_type", "0431");
        params.put("txn_sub_type", txnSubType);
        params.put("data_type", "json");
		return params;
	}
	
	private static Map<String, String> getSubTypeMap() {
		Map<String,String> map = new HashMap<String,String>();      
		map.put("11", "预绑卡交易");
		map.put("12", "确认绑卡交易");
		map.put("01", "直接绑卡交易");
		map.put("02", "解除绑定关系交易");
		map.put("03", "查询绑定关系交易");
		map.put("15", "认证支付类预支付交易");
		map.put("16", "认证支付类支付确认交易");
		map.put("31", "交易状态查询交易");
		return map;
	}
	
	
}
