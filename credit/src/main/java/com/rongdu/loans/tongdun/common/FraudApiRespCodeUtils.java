package com.rongdu.loans.tongdun.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 百融金服-API-响应代码
 */
public class FraudApiRespCodeUtils {

	private static Map<String, FraudApiRespCode> apiRespCodes = new HashMap<String, FraudApiRespCode>();


	
	/**
	 * 一般API接口-响应信息
	 * @param code
	 * @return
	 */
	public static String getApiMsg(String code){
		FraudApiRespCode resp = apiRespCodes.get(code);
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
		FraudApiRespCode resp = apiRespCodes.get(code);
		if (null != resp) {
			return resp.getSolution();
		}
		return null;
	}
	
	
	static{
		for (FraudApiRespCode e : FraudApiRespCode.values()) {
			apiRespCodes.put(e.getCode(), e);
        }

	}
	
}
