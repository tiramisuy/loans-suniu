package com.rongdu.loans.qhzx.demo;

import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求示例参考
 * 
 * @author 唐应泉
 * @support by Tel:0755-22625539
 * @company 深圳前海征信中心股份有限公司
 * 
 */
public class DemoMain
{
    public static void main(String[] args) throws Exception
    {
//        // 发送HTTP请求
//        postHttpRequest();
        // 发送HTTPS请求
        postHttpsRequest();
    }

    /**
     * 发送HTTP请求
     * 
     * @throws Exception
     */
    private static void postHttpRequest() throws Exception
    {
        String sfUrl = "https://test-qhzx.pingan.com.cn:5443/dcs-dmz/do/dmz/query/blacklist/v1/MSC8004";
        String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
        String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
                "SK803@!QLF-D25WEDA5E52DA");
        String busiData = "\"busiData\":\"" + encBusiData + "\"";
        String sigValue = DataSecurityUtil.signData(encBusiData);
        String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
        String securityInfo = "\"securityInfo\":" + MessageUtil.getSecurityInfo(sigValue, pwd);
        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
        System.out.println("请求：" + message);

        String res = post(sfUrl, message);

        System.out.println("响应Message：" + res);
        JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
        System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));

        // 一定要验证签名的合法性！！！！！！！！
        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
                "signatureValue"));

        System.out.println("响应BusiData明文："
                + DataSecurityUtil.decrypt(msgJSON.getString("busiData"), "SK803@!QLF-D25WEDA5E52DA"));
    }

    /**
     * 发送HTTPs请求,注意这里我们信任了任何服务器证书
     * 
     * @throws Exception
     */
    private static void postHttpsRequest() throws Exception
    {
        String sfUrl = "https://test-qhzx.pingan.com.cn:5443/do/dmz/query/blacklist/v1/MSC8004";
        String header = "\"header\":" + MessageUtil.getMHeader_DMZ();
        String encBusiData = DataSecurityUtil.encrypt(MessageUtil.getBusiData_MSC8004().getBytes(),
                "SK803@!QLF-D25WEDA5E52DA");
        String busiData = "\"busiData\":\"" + encBusiData + "\"";
        String sigValue = DataSecurityUtil.signData(encBusiData);
        String pwd = DataSecurityUtil.digest("weblogic1".getBytes());
        String securityInfo = "\"securityInfo\":" + MessageUtil.getSecurityInfo(sigValue, pwd);
        String message = "{" + header + "," + busiData + "," + securityInfo + "}";
        System.out.println("请求：" + message);

        String res = post(sfUrl, message);

        System.out.println("响应Message：" + res);
        JSONObject msgJSON = net.sf.json.JSONObject.fromObject(res);
        System.out.println("响应BusiData密文：" + msgJSON.getString("busiData"));

        // 一定要验证签名的合法性！！！！！！！！
        DataSecurityUtil.verifyData(msgJSON.getString("busiData"), msgJSON.getJSONObject("securityInfo").getString(
                "signatureValue"));

        System.out.println("响应BusiData明文："
                + DataSecurityUtil.decrypt(msgJSON.getString("busiData"), "SK803@!QLF-D25WEDA5E52DA"));
    }
    
	/**
	 * 发送POST请求
	 * @param url
	 * @param reqString
	 * @return
	 */
	public static String post(String url,String jsonString) {
		RestTemplate client = new RestTemplate();
		List<HttpMessageConverter<?>> mcList = new ArrayList<HttpMessageConverter<?>>();
		StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));  
				
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=utf-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.setContentLength(jsonString.getBytes().length);
		
		mcList.add(mc);
		client.setMessageConverters(mcList);
		HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
		String respString = client.postForObject(url, entity, String.class);
		return respString;
	}
}
