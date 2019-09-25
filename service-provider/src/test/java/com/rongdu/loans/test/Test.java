package com.rongdu.loans.test;

import com.rongdu.common.config.Global;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.security.Digests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.HttpUtils;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.hanjs.op.HanJSApiOrderOP;
import com.rongdu.loans.hanjs.util.MD5Util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws Exception {
		String url = "http://www.baidu.com/";
		String json = "{\"name\":\"yxc\"}";

		/*for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(()->{
				System.out.println(Thread.currentThread().getName()+":start");
				String s1 = HttpClientUtils.postForJson(url, null, json);
				//System.out.println("*************************************************");
				System.out.println(Thread.currentThread().getName()+":s1111111111111111------------------------"+s1.length());
				//System.out.println("*************************************************");
				//System.out.println("");
			});
			thread.start();
		}*/

		String s1 = HttpClientUtils.postForJson(url, null, json);
		System.out.println("*************************************************");
		System.out.println("s1111111111111111------------------------"+s1.length());
		System.out.println("*************************************************");
		System.out.println("");

		String s2 = HttpClientUtils.postForJson(url, null, json);
		System.out.println("*************************************************");
		System.out.println("s2222222222222222------------------------"+s2.length());
		System.out.println("*************************************************");
		System.out.println("");

		String s3 = HttpClientUtils.postForJson(url, null, json);
		System.out.println("*************************************************");
		System.out.println("s3333333333333333------------------------"+s3.length());
		System.out.println("*************************************************");
	}



	private static String sendSms111(String strMobiles, String strMessage) throws Exception {
		String result = "";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", Global.MONGATE_USER_ID);
		params.put("seed", DateUtils.getHHmmss());
		params.put("key", getMD5(getMD5(Global.MONGATE_PWD).toLowerCase()+DateUtils.getHHmmss()).toLowerCase());
		params.put("dest", strMobiles);
		params.put("content", URLEncoder.encode(strMessage, "UTF-8"));
		params.put("ext", "");
		params.put("reference", "");
		String param = JsonMapper.getInstance().toJson(params);
		result = RestTemplateUtils.getInstance().postForJsonRaw(Global.MONGATE_SEND_SUBMIT_URL, param);
		return result;
	}


	private static String sendSms(String strMobiles, String strMessage) throws Exception {
		String result = "";// 定义返回值变量
//      SmsParams p = new SmsParams();
//      p.setPszMobis(strMobiles);// 设置手机号码
//      p.setPszMsg(strMessage);// 设置短信内容
//      p.setIMobiCount(String.valueOf(strMobiles.split(",").length));// 设置手机号码个数
//      p.setPszSubPort(strSubPort);// 设置扩展子号
//      p.setMsgId(strUserMsgId);// 设置流水号
//      p.setPassword(Digests.md5(Digests.md5(p.getPassword())+DateUtils.getHHmmss()));

		String seed = DateUtils.getHHmmss();
		StringBuffer sb = new StringBuffer();
		sb.append("name=" + Global.MONGATE_USER_ID);
		sb.append("&seed=" + seed);
		sb.append("&key=" + Digests.md5(Digests.md5(Global.MONGATE_PWD).toLowerCase()+seed).toLowerCase());////md5( md5(password)  +  seed) )  
		sb.append("&dest=" + strMobiles);
		sb.append("&content=" + "【聚宝钱包】" + URLEncoder.encode(strMessage, "UTF-8") + "。退订回T");//注意编码，字段编码要和接口所用编码一致，有可能出现汉字之类的记得转换编码
		sb.append("&ext=" + "");
		sb.append("&reference=" + "");

//		Map<String, String> params = new HashMap<String, String>();
//		params.put("name", Global.MONGATE_USER_ID);
//		params.put("seed", seed);
//		params.put("key", getMD5(getMD5(Global.MONGATE_PWD).toLowerCase()+seed).toLowerCase());
//		params.put("dest", strMobiles);
//		params.put("content", URLEncoder.encode(strMessage, "UTF-8"));
//		params.put("ext", "");
//		params.put("reference", "");
//		result = RestTemplateUtils.getInstance().postForJsonRaw(Global.MONGATE_SEND_SUBMIT_URL, params);

		HttpURLConnection connection = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			// 创建url对象
			URL url = new URL(Global.MONGATE_SEND_SUBMIT_URL);
			// 打开url连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置url请求方式 ‘GET’ 或者 ‘POST’
			connection.setRequestMethod("POST");
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			printWriter.write(sb.toString());//post的参数 xx=xx&yy=yy
			// flush输出流的缓冲
			printWriter.flush();
			//开始获取数据
			bis = new BufferedInputStream(connection.getInputStream());
			bos = new ByteArrayOutputStream();
			int len;
			byte[] arr = new byte[1024];
			while((len=bis.read(arr))!= -1){
				bos.write(arr,0,len);
				bos.flush();
			}

			result =  bos.toString();
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常!" + e);
			e.printStackTrace();
		} finally {
			try {
				try {
					if(bos != null){
						bos.close();
					}
					if(connection != null){
						connection.disconnect();
					}
					if(bis != null){
						bis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(result);
		return result;
	}



	/**
	 * 对字符串md5加密
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			// 计算md5函数
			md.update(str.getBytes());
			byte encrypt[];
			encrypt = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte t : encrypt) {
				String s = Integer.toHexString(t & 0xFF);
				if (s.length() == 1) {
					s = "0" + s;
				}
				sb.append(s);
			}
			return sb.toString();
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//            return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			System.out.println("MD5加密出现错误");
		}
		return null;
	}
}
