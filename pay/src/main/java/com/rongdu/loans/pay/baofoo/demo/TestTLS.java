/**
 * Company: www.baofu.com
 * @author dasheng(大圣)
 * @date 2018年1月26日
 */
package com.rongdu.loans.pay.baofoo.demo;

import java.util.Map;
import java.util.TreeMap;

import com.rongdu.loans.pay.baofoo.domain.RequestParams;
import com.rongdu.loans.pay.baofoo.http.SimpleHttpResponse;
import com.rongdu.loans.pay.baofoo.util.BaofooClient;
import com.rongdu.loans.pay.baofoo.util.HttpUtil;
import com.rongdu.loans.pay.utils.BaofooConfig;

/**
 * 
 * 升级后我司只支持TLSv1.1，TLSv1.2
 * 运行后有返回值（biz_resp_code=BF00436&biz_resp_msg=交易类型不存在&resp_code=F）说明请求正常
 * 
 * SSLContext ctx = SSLContext.getInstance("TLSv1.2");或SSLContext ctx =
 * SSLContext.getInstance("TLSv1.1"); 在SimpleHttpClient类中的enableSSL方法内
 */

public class TestTLS {
	public static void main(String[] args) throws Exception {

		Map<String, String> DateArry = new TreeMap<String, String>();
		DateArry.put("terminal_id", "100000917");
		DateArry.put("member_id", "100000178");

		// String PostString =
		// HttpUtil.doRequest("https://vgw.baofoo.com/cutpayment/protocol/backTransRequest",
		// DateArry);
		//
		// System.out.println(PostString);

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt("100000917"));
		params.setTerminalId(Integer.parseInt("100000178"));
		params.setRequestUrl("https://vgw.baofoo.com/cutpayment/protocol/backTransRequest");
		SimpleHttpResponse response = BaofooClient.doRequest(params);
		System.out.println(response.getEntityString());
	}
}