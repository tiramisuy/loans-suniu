package com.rongdu.loans.koudai.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class KDHttpUtils {
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param requestUrl
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostRequestBody(String requestUrl, Map<String, String> reqMap) {
		String param = reqStrUtil(reqMap);
		// logger.info("=======调用口袋理财请求参数：" + param);
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		String result = "";
		try {
			URL url = new URL(requestUrl);
			URLConnection urlConnection = url.openConnection();
			// 设置doOutput属性为true表示将使用此urlConnection写入数据
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			// 得到请求的输出流对象
			out = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
			// 把数据写入请求的Body
			out.write(param);
			out.flush();
			// 从服务器读取响应
			inputStream = urlConnection.getInputStream();
			result = IOUtils.toString(inputStream, "utf-8");
			// logger.info("=======口袋理财返回结果：" + result);
		} catch (Exception e) {
			// logger.error("=======调用口袋理财出现异常：" + e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(inputStream);
		}
		return result;
	}

	/**
	 * 对请求参数进行处理
	 * 
	 * @param reqMap
	 * @return
	 */
	public static String reqStrUtil(Map<String, String> reqMap) {
		String reqStr = "";
		for (Map.Entry<String, String> entry : reqMap.entrySet()) {
			reqStr += entry.getKey() + "=" + entry.getValue() + "&";
		}
		return reqStr.substring(0, reqStr.length() - 1);
	}
}
