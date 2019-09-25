package com.rongdu.loans.tencent.test;

import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.tencent.common.SignUtils;
import com.rongdu.loans.tencent.common.TencentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignTest {
	
	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(SignTest.class);
	
	public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        String appId = TencentConfig.appid;
        String userId = IdGen.uuid();
        String nonceStr = IdGen.uuid();
        String version = TencentConfig.appid; 
        String ticket = "KmRvYuuiW0prGLseV6dpa7TxJSwtbX2FPsTyZPzZR4X8I5YCTXGImcpE2pLlUoNc";
		params.put("app_id", TencentConfig.appid);
		params.put("user_id", IdGen.uuid());  
		params.put("nonceStr",IdGen.uuid());
		params.put("version", "1.0.0"); 
		params.put("ticket", ticket);
		
		appId = "TIDA0001";
		userId = "userID19959248596551";
		nonceStr = "kHoSxvLZGxSoFsjxlbzEoUzh5PAnTU7T";
		version = "1.0.0";
		ticket = "XO99Qfxlti9iTVgHAjwvJdAZKN3nMuUhrsPdPlPVKlcyS50N6tlLnfuFBPIucaMS";
		//4AE72E6FBC2E9E1282922B013D1B4C2CBD38C4BD
		
		List<String> values = new ArrayList<>();
		values.add(appId);
		values.add(userId);
		values.add(nonceStr);
		values.add(version);
		values.add(ticket);
		String sign = SignUtils.sign(values);

		System.out.println(sign);
		System.out.println(SignUtils.sha1Sign(values));

	}
	
}
