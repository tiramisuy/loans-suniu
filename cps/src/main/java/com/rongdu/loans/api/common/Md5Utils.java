package com.rongdu.loans.api.common;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {

	private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

	public static boolean checkMD5(HttpServletRequest request, String privateKey){
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		//获取传入的参数，并将参数放入到TreeMap中(sign除外)
		for(String key :map.keySet()){
			if("sign".equals(key) || "datetime".equals(key)){
				continue;
			}
			String[] values = map.get(key);
			String logValue = null;
			for(int i = 0 ;i < values.length ; i ++){
				if(logValue == null){
					logValue = values[i] ;
				}else{
					logValue = logValue + "," +values[i];	
				}
			}
			treeMap.put(key, logValue);
		}
		String[] signs = map.get("sign");
		String sign = "";
		if(signs != null && signs.length > 0){
			for(int i=0;i<signs.length;i++){
				sign+= signs[i];
			}
		}
		logger.info("sign:"+sign);
		String signature = "";
		for(String key:treeMap.keySet()){
			signature += String.valueOf(treeMap.get(key));
		}
		logger.info("signature:"+signature);
		logger.info("MD5:"+DigestUtils.md5Hex(signature+privateKey));
		if(sign.equals(DigestUtils.md5Hex(signature+privateKey))){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkStrMD5(String sign, String paramStr, String privateKey){
		//logger.info("sign:" + sign);
		String signature = DigestUtils.md5Hex(paramStr+privateKey); 
		//logger.info("MD5:" + signature);
		if(sign.equals(signature)){
			return true;
		}else{
			return false;
		}
	}
    
}
