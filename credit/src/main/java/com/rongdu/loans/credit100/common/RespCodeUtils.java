package com.rongdu.loans.credit100.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 百融金服-API-响应代码
 */
public class RespCodeUtils {
	
	private static Map<String, LoginRespCode> loginRespCodes = new HashMap<String, LoginRespCode>();
	private static Map<String, ApiRespCode> apiRespCodes = new HashMap<String, ApiRespCode>();
	
	/**
	 * 登录-响应信息
	 * @param code
	 * @return
	 */
	public static String getLoginMsg(String code){
		LoginRespCode resp = loginRespCodes.get(code);
		if (null != resp) {
			return resp.getMsg();
		}
		return null;
	}
	
	/**
	 * 登录-遇到错误的处理措施
	 * @param code
	 * @return
	 */
	public static String getApiResp(String code){
		LoginRespCode resp = loginRespCodes.get(code);
		if (null != resp) {
			return resp.getSolution();
		}
		return null;
	}
	
	/**
	 * 一般API接口-响应信息
	 * @param code
	 * @return
	 */
	public static String getApiMsg(String code){
		ApiRespCode resp = apiRespCodes.get(code);
		if (null != resp) {
			return resp.getMsg();
		}
		return null;
	}
	
	/**
	 * 一般API接口-遇到错误的处理措施
	 * @param code
	 * @return
	 */
	public static String getApiSolution(String code){
		ApiRespCode resp = apiRespCodes.get(code);
		if (null != resp) {
			return resp.getSolution();
		}
		return null;
	}
	
	
	static{
		for (ApiRespCode e : ApiRespCode.values()) {
			apiRespCodes.put(e.getCode(), e);
        }
		
		for (LoginRespCode e : LoginRespCode.values()) {
			loginRespCodes.put(e.getCode(), e);
        }
	}
	
}
