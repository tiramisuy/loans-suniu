package com.rongdu.loans.test.credit100;

import com.bfd.facade.MerchantServer;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit100.common.ApiRespCode;
import com.rongdu.loans.credit100.common.Credit100Config;
import com.rongdu.loans.credit100.common.LoginRespCode;
import com.rongdu.loans.credit100.common.RespCodeUtils;
import com.rongdu.loans.credit100.message.Credit100LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
public class Credit100BrcreditpointTest {
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(Credit100BrcreditpointTest.class);
	//Token
	private static String tokenid = null;
	//商户客户端
	private static MerchantServer ms=new MerchantServer();
	
	public static void main(String[] args) throws Exception {
		
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,String> reqData = new HashMap<String,String>();
		//请求参数
		String id = "420521197901260467";
		String cell = "13227278857";
		String name = "袁海蓉";
		
		reqData.put("id", id);
		reqData.put("cell", cell);
		reqData.put("name", name);		
		reqData.put("meal", Credit100Config.brcreditpoint_biz_code);
		
		param.put("apiName", Credit100Config.api_name);
		param.put("tokenid", getTokenid());
		param.put("reqData", reqData);
					
//		reqData.put("af_swift_number",afSwiftNumber);
//		reqData.put("event", event);
		
		String requestJson = JsonMapper.toJsonString(param);
		logger.debug("{}-{}-请求报文：{}",Credit100Config.partner_name,Credit100Config.brcreditpoint_biz_name,requestJson);
	    String responseJson=ms.getApiData(requestJson,Credit100Config.api_code);	
		logger.debug("{}-{}-应答结果：{}",Credit100Config.partner_name,Credit100Config.brcreditpoint_biz_name,responseJson);
		
		Map<String,String> result = (Map<String, String>) JsonMapper.fromJsonString(responseJson, HashMap.class);
		String code = result.get("code");
		String succCode = ApiRespCode.SUCCESS.getCode();
		if (StringUtils.isNotBlank(code)&&succCode.equals(code)) {
			
		}else{
			logger.debug("{}-{}-调用失败：{}，{}，{}",Credit100Config.partner_name,Credit100Config.brcreditpoint_biz_name,
					code,RespCodeUtils.getApiMsg(code),RespCodeUtils.getApiSolution(code));
			String c100007 = ApiRespCode.C_100007.getCode();
			//tokenid有效期是1个小时，如果1个小时没有用，就会过期。建议通过过期返回的错误码100007判断是否需要重新登录。
			if (c100007.equals(code)) {
				login();
				//递归调用
				//to do...
			}
		}
		
	}

	
	/**
	 * 登录百融服务器
	 */
	private static void login() {
		logger.debug("百融金服-登录认证：进行中...");
		String result = null;
		try {
			result = ms.login(Credit100Config.api_username,Credit100Config.api_password,"LoginApi",Credit100Config.api_code);
		} catch (Exception e) {
			logger.debug("百融金服-登录认证：认证失败，{}",e.getMessage());
			e.printStackTrace();
		}
		Credit100LoginResponse loginResponse = (Credit100LoginResponse)JsonMapper.fromJsonString(result, Credit100LoginResponse.class);
		String succCode = LoginRespCode.SUCCESS.getCode();
		if (loginResponse!=null&&succCode.equals(loginResponse.getCode())) {
			setTokenid(loginResponse.getTokenid());;
			logger.debug("百融金服-登录认证：认证成功，tokenid为：{}",tokenid);
		}else {
			logger.debug("百融金服-登录认证：认证失败，{}",result);
		}
	}
	
	/**
	 * 获取令牌；如果令牌为空，就进行登录
	 * @return
	 */
	public static String getTokenid() {
		if (StringUtils.isBlank(tokenid)) {
			login();
		}
		return tokenid;
	}
	
	/**
	 * 设置令牌
	 * @param tokenid
	 */
	public static void setTokenid(String tokenid) {
		Credit100BrcreditpointTest.tokenid = tokenid;
	}
	
}
