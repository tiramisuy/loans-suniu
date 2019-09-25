package com.rongdu.loans.pay.kjtpay.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import com.kjtpay.gateway.common.util.JsonMapUtil;
import com.kjtpay.gateway.common.util.security.SecurityService;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;

/**
 * 
* @Description: 加解密签名验签 
* @author: RaoWenbiao
* @date 2018年11月16日
 */
public class KjtpayUtil {

	public static Logger logger = LoggerFactory.getLogger(KjtpayUtil.class);
	
	private static SecurityService securityService;
	private static SecurityService rsaSecurityService;
	public static SecurityService rtnSecurityService;

	public static String privateKey;
	public static String publicKey;

	public static String partnerId;
	public static String url;

	static {
		privateKey = Global.getConfig("kjtpay.privateKey");
		publicKey = Global.getConfig("kjtpay.publicKey");
		rsaSecurityService = new SecurityService(privateKey, publicKey);

		rtnSecurityService = new SecurityService("", Global.getConfig("kjtpay.rtn.publicKey"));

		partnerId = Global.getConfig("kjtpay.partnerId");
		url = Global.getConfig("kjtpay.url");
	}

	/**
	 * 加密
	 * @param charset
	 * @param service
	 * @param signType
	 * @param req
	 * @return
	 */
	public String encrypt(String charset, String service, String signType, String req) {

		System.out.println("转换前json：" + req);
		if (StringUtils.isNoneBlank(req)) {

			JSONObject bizReq = null;
			// 因js转出的嵌套json有\，使用gson转成请求类会报错，故需要转换一下
			bizReq = convertParm(service, req);

			if (bizReq != null) {
				System.out.println("转换后json：" + bizReq.toString());

				return encrypt(signType, bizReq.toString(), charset);
			}

		}

		return "请求参数填写出错或必填参数未填写";
	}

	/**
	 * 使用json字符串形式加密
	 * @param signType
	 * @param oriText
	 * @param charset
	 * @return
	 */
	private String encrypt(String signType, String oriText, String charset) {
		// 演示使用字符串形式加密
		if ("RSA".equals(signType)) {
			// RSA加密
			return rsaSecurityService.encrypt(oriText, charset);
		} else if ("ITRUS".equals(signType)) {
			return securityService.encrypt(oriText, charset);
		} else {
			return "加密出错";
		}

	}

	/**
	 * 商户签名
	 * @param signData
	 * @return
	 */
	public static String sign(String signData) {

		if (StringUtils.isNoneBlank(signData)) {

			@SuppressWarnings("unchecked")
			Map<String, String> req = JSON.parseObject(signData, HashMap.class);

			String charset = req.get("charset");
			String signType = req.get("sign_type");
			if (StringUtils.isNoneBlank(charset) && StringUtils.isNoneBlank(signType)) {

				String service = req.get("service");

				if ("instant_trade".equals(service) || "ensure_trade".equals(service)) {
					// 演示使用请求map方式签名
					if ("RSA".equals(signType)) {
						// RSA签名
						return rsaSecurityService.sign(req, charset);
					} else if ("ITRUS".equals(signType)) {
						return securityService.sign(req, charset);
					}

				} else {

					// 演示使用请求对象签名
					RequestBase requestBase = convertRequestBaseParm(req);

					if ("RSA".equals(signType)) {
						// RSA签名
						return rsaSecurityService.sign(requestBase, charset);
					} else if ("ITRUS".equals(signType)) {
						return securityService.sign(requestBase, charset);
					}

				}

			}
		}

		return "请求参数不正确！";
	}

	/**
	 * 商户验签
	 * @param responseParameter
	 * @return
	 */
	public static VerifyResult verify(ResponseParameter responseParameter) {

		if (null == responseParameter || StringUtils.isBlank(responseParameter.getSignType())
				|| StringUtils.isBlank(responseParameter.getCharset())) {
			return null;
		}

		VerifyResult result = null;

		if ("RSA".equals(responseParameter.getSignType())) {
			// RSA验签
			result = rtnSecurityService.verify(responseParameter, responseParameter.getSign(),
					responseParameter.getCharset());
		} else if ("ITRUS".equals(responseParameter.getSignType())) {
			// result = securityService.verify(responseParameter,
			// responseParameter.getSign(),
			// responseParameter.getCharset());
		}

		return result;
	}
	
	/**
	 * 异步通知验签
	 * @param responseParameter
	 * @return
	 */
	public static VerifyResult verify(Map<String,String> responseParameter) {

		if (null == responseParameter || StringUtils.isBlank(responseParameter.get("sign_type"))) {
			return null;
		}

		VerifyResult result = null;

		if ("RSA".equals(responseParameter.get("sign_type"))) {
			// RSA验签
			result = rtnSecurityService.verify(responseParameter, responseParameter.get("sign"),"UTF-8");
		} else if ("ITRUS".equals(responseParameter.get("sign_type"))) {
			// result = securityService.verify(responseParameter,
			// responseParameter.getSign(),
			// responseParameter.getCharset());
		}

		return result;
	}

	/**
	 * RSA密钥校验
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static String verifyKeyPair(String privateKey, String publicKey) {
		try {

			String sign = RSA.sign("test", privateKey, "utf-8");
			boolean isOk = RSA.verify("test", sign, publicKey, "utf-8");

			if (isOk) {
				return "校验成功";
			} else {
				return "校验失败";
			}

		} catch (Exception e) {
			return "校验失败";
		}
	}

	/**
	 * 商户验签
	 * @param signData
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static VerifyResult verify(String verifyData) {

		if (StringUtils.isNoneBlank(verifyData)) {

			VerifyResult result = null;

			Map<String, String> responseParameter = new HashMap<String, String>();
			responseParameter = JsonMapUtil.gsonToObj(verifyData, responseParameter.getClass());

			String bizContent = responseParameter.get("biz_content") == null ? null
					: JSON.toJSONString(responseParameter.get("biz_content"));
			responseParameter.remove("biz_content");
			responseParameter.put("biz_content", bizContent);

			String signType = responseParameter.get("sign_type");
			String charset = responseParameter.get("charset");
			String sign = responseParameter.get("sign");

			if ("RSA".equals(signType)) {
				// RSA验签
				result = rtnSecurityService.verify(responseParameter, sign, charset);
			} else if ("ITRUS".equals(signType)) {
				result = securityService.verify(responseParameter, sign, charset);
			}
			
			return result;
		}

		return new VerifyResult(false);
	}
	
	/**
	 * 商户验签
	 * @param signData
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static VerifyResult verify(String verifyData,Class type) {

		if (StringUtils.isNoneBlank(verifyData)) {

			VerifyResult result = null;

			Map<String, String> responseParameter = new HashMap<String, String>();
			responseParameter = JsonMapUtil.gsonToObj(verifyData, responseParameter.getClass());

			String bizContent = responseParameter.get("biz_content") == null ? null
					: JSON.toJSONString(responseParameter.get("biz_content"));
			responseParameter.remove("biz_content");
			
			if(bizContent == null){
				responseParameter.put("biz_content", bizContent);
			}else {
				responseParameter.put("biz_content", JsonMapUtil.gsonToJson(JsonMapper.fromJsonString(bizContent,type)));
			}
			
			

			String signType = responseParameter.get("sign_type");
			String charset = responseParameter.get("charset");
			String sign = responseParameter.get("sign");

			if ("RSA".equals(signType)) {
				// RSA验签
				result = rtnSecurityService.verify(responseParameter, sign, charset);
			} else if ("ITRUS".equals(signType)) {
				result = securityService.verify(responseParameter, sign, charset);
			}
			
			return result;
		}

		return new VerifyResult(false);
	}

	/**
	 * 转换参数
	 * @param service
	 * @param reqParm
	 * @return
	 */
	public JSONObject convertParm(String service, String reqParm) {

		if ("instant_trade".equals(service)) {
			return convertInstantTradeParm(reqParm);

		} else if ("ensure_trade".equals(service)) {
			return convertInstantTradeParm(reqParm);

		} else if ("trade_settle".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);

		} else if ("entry_account_offline".equals(service)) {
			return convertWithSpecialParm(reqParm, null);

		} else if ("batch_bank_witholding".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("withholding_list");
			return convertWithSpecialParm(reqParm, fieldNameList);

		} else if ("trade_bank_witholding".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);

		} else if ("trade_close".equals(service)) {
			return convertWithSpecialParm(reqParm, null);

		} else if ("trade_query".equals(service)) {
			return convertWithSpecialParm(reqParm, null);

		} else if ("trade_refund".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);

		} else if ("batch_transfer_account".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("transfer_list");
			return convertWithSpecialParm(reqParm, fieldNameList);

		} else if ("transfer_to_account".equals(service)) {
			return convertWithSpecialParm(reqParm, null);

		} else if ("transfer_to_card".equals(service)) {
			return convertWithSpecialParm(reqParm, null);

		} else if ("batch_transfer_card".equals(service)) {
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("transfer_list");
			return convertWithSpecialParm(reqParm, fieldNameList);
		}

		return null;

	}

	/**
	 * 转换给定参数名的参数，将给定参数的JSON字符串格式转换成JSON对象数组格式
	 * @param reqParm
	 * @param fieldNameList
	 * @return
	 */
	public JSONObject convertWithSpecialParm(String reqParm, List<String> fieldNameList) {

		JSONObject reqJson = JSONObject.parseObject(reqParm);

		if (!CollectionUtils.isEmpty(fieldNameList)) {

			for (String fieldName : fieldNameList) {
				String fieldValue = reqJson.getString(fieldName);
				if (StringUtils.isNoneBlank(fieldValue)) {
					JSONArray fieldValueJSONArray = JSONArray.parseArray(fieldValue);
					reqJson.put(fieldName, fieldValueJSONArray);
				}
			}

		}

		return reqJson;
	}

	/**
	 * 转换即时到账参数
	 * @param req
	 * @return
	 */
	public JSONObject convertInstantTradeParm(String req) {
		JSONObject tradeReq = JSONObject.parseObject(req);
		// 设置复杂属性
		// 转换支付方式pay_method
		String pay_method = tradeReq.getString("pay_method");
		JSONObject payMethod = JSONObject.parseObject(pay_method);
		tradeReq.put("pay_method", payMethod);

		// 转换终端信息域terminal_info
		String terminal_info = tradeReq.getString("terminal_info");
		JSONObject terminalInfo = JSONObject.parseObject(terminal_info);
		tradeReq.put("terminal_info", terminalInfo);
		// 转换商户自定义域merchant_custom
		String merchant_custom = tradeReq.getString("merchant_custom");
		JSONObject merchantCustom = JSONObject.parseObject(merchant_custom);
		tradeReq.put("merchant_custom", merchantCustom);

		// 转换交易信息trade_info
		String trade_info = tradeReq.getString("trade_info");

		if (StringUtils.isNoneBlank(trade_info)) {
			JSONObject tradeInfo = JSONObject.parseObject(trade_info);

			// //转换交易扩展参数trade_ext
			// String trade_ext = tradeInfo.getString("trade_ext");
			// if(StringUtils.isNoneBlank(trade_ext)){
			// JSONObject tradeExt = JSONObject.parseObject(trade_ext);
			// tradeInfo.put("trade_ext", tradeExt);
			// }

			// 转换分账列表royalty_info
			String royalty_info = tradeInfo.getString("royalty_info");
			if (StringUtils.isNoneBlank(royalty_info)) {
				JSONArray royaltyInfos = JSONArray.parseArray(royalty_info);
				tradeInfo.put("royalty_info", royaltyInfos);
			}

			tradeReq.put("trade_info", tradeInfo);

			return tradeReq;
		}

		return null;
	}

	/**
	 * RSA密钥生成
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/genKeyPair.do", method = RequestMethod.POST)
	public String genKeyPair(@RequestParam(value = "keyType", required = false) String keyType,
			@RequestParam(value = "length", required = false) Integer length) throws Exception {

		if (!("PKCS#1".equalsIgnoreCase(keyType) || "PKCS#8".equalsIgnoreCase(keyType))) {
			return "密钥格式错误";
		}

		if (!(1024 == length || 2048 == length)) {
			return "密钥长度错误";
		}

		Map<String, Object> keyMap = RSA.genKeyPair(length);

		if (keyMap != null) {
			String publicKey = RSA.getPublicKey(keyMap);
			String privateKey = RSA.getPrivateKey(keyMap, keyType);

			System.out.println("publicKey:" + publicKey);
			System.out.println("privateKey:" + privateKey);

			JSONObject jSONObject = new JSONObject();
			jSONObject.put("publicKey", publicKey);
			jSONObject.put("privateKey", privateKey);
			return jSONObject.toJSONString();
		}

		return null;
	}

	/**
	 * RSA密钥生成
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static String genKeyPair() {

		try {
			Map<String, Object> keyMap = RSA.genKeyPair(2048);// 2048

			if (keyMap != null) {
				String publicKey = RSA.getPublicKey(keyMap);
				String privateKey = RSA.getPrivateKey(keyMap, "PKCS#8");

				System.out.println("publicKey:" + publicKey);
				System.out.println("privateKey:" + privateKey);

				JSONObject jSONObject = new JSONObject();
				jSONObject.put("publicKey", publicKey);
				jSONObject.put("privateKey", privateKey);
				return jSONObject.toJSONString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * RSA密钥校验
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static String verifyKeyPair(String keyPair) {
		try {
			System.out.println("keyPair：" + keyPair);
			if (StringUtils.isNoneBlank(keyPair)) {

				JSONObject keyPairJson = JSONObject.parseObject(keyPair);
				String sign = RSA.sign("test", keyPairJson.getString("privateKey"), "utf-8");
				boolean isOk = RSA.verify("test", sign, keyPairJson.getString("publicKey"), "utf-8");

				if (isOk) {
					return "校验成功";
				} else {
					return "校验失败";
				}
			}
		} catch (Exception e) {
			return "校验失败";
		}

		return "请正确输入公钥和私钥";
	}

	/**
	 * 转换公共请求参数
	 * @param req
	 * @return
	 */
	public static RequestBase convertRequestBaseParm(Map<String, String> req) {
		if (req != null) {
			RequestBase requestBase = new RequestBase();
			requestBase.setRequestNo(req.get("request_no"));
			requestBase.setService(req.get("service"));
			requestBase.setVersion(req.get("version"));
			requestBase.setPartnerId(req.get("partner_id"));
			requestBase.setCharset(req.get("charset"));
			requestBase.setSignType(req.get("sign_type"));
			requestBase.setSign(req.get("sign"));
			requestBase.setTimestamp(req.get("timestamp"));
			requestBase.setFormat(req.get("format"));
			requestBase.setBizContent(req.get("biz_content"));

			return requestBase;
		}

		return null;
	}

	// private static Gson gson= new Gson();
	// /**
	// *
	// * @Title: toJsonString
	// * @Description: Object 转json
	// * @return String 返回类型
	// * @throws
	// */
	// public static String toJsonString(Object object){
	// return gson.toJson(object);
	// }
	// /**
	// *
	// * @Title: fromJson
	// * @Description: json 转Object
	// * @return T 返回类型
	// * @throws
	// */
	// public static <T> T fromJson(String json,Class<T> classOfT){
	// return gson.fromJson(json,classOfT);
	// }

	/**
	 * 
	* @Title: genRequestBase
	* @Description: 生成请求参数
	* @return RequestBase    返回类型
	* @throws
	 */
	public static RequestBase genRequestBase(String service, Object bizContent) {
		RequestBase requestBase = new RequestBase();

		requestBase.setRequestNo(IdGen.uuid());
		requestBase.setService(service);
		requestBase.setPartnerId(partnerId);
		requestBase.setFormat("JSON");
		requestBase.setCharset("UTF-8");
		requestBase.setSignType("RSA");

		// 2018-11-19%2011%3A43%3A35
		// requestBase.setTimestamp("2018-11-19%2011%3A43%3A35");
		requestBase.setTimestamp(DateUtils.formatDate(new Date(), DateUtils.FORMAT_LONG));
		requestBase.setVersion("1.0");
		requestBase.setBizContent(rsaSecurityService.encrypt(bizContent, requestBase.getCharset()));

		requestBase.setSign(sign(JsonMapUtil.gsonToJson(requestBase)));
		return requestBase;
	}

	public static void main(String[] args) {
		// genKeyPair();
		//transferToCard();
		// System.out.println(verifyKeyPair(privateKey,publicKey));

		// try {
		// System.out.println(RSA.verify("biz_content=VKTW0YrmWgU0cHgUC+gANG2wFXAHvCr9eh4+KgHx6vTX/PxKK/RSKOqLfPHy6PXdRBGsZNNb3AA2fHbmeBUB/gj6/LTdWwMUnkCNaJvJcLm6WCkgheCJrKtd0Hzj2pz4jFQB51nOeKeQeK5iaPD1lUUKU/GHPuPNCcIdWP37KoxVHfDNziyjtOGLgcMV24CinPcY3lWfRoX+O1tfkrkiZKw15aEMAb5htlHQR3fF261xSysqnaNqHxRE9FgRmsi8glIhAWHJY9AFg8PfEWpJPkpjSSKie2XykeLwTrcwP2AKl23Lata5oZHbse3Q6FzEAcq0IHOfJOXPzzZimWGbzgaPJhzsKJNkQLDKTKbVZs+bI82dgYjVyeIl6CjONv0YNKqTi3fAV+9IBZGicpww1oruCvhST7blWWfFsiZ4ef0UVtB1LvQty8Z12qyoKtJCPtzNwXdSiP7rFVwkJ4EDePhtUABwUOQAfs5WSMrJj6tZVUQXiNZEtW824JzuFvEF&charset=UTF-8&format=JSON&partner_id=200000151638&request_no=2017101909425756398174056404&service=transfer_to_card&timestamp=2018-11-19
		// 11:53:38&version=1.0",
		// "Gu+1myoWv5OQusalA4i3VXZ47tTEPzS95QJ5XmxVoEcOFJ+KwmiNaQq9eeSrjYO/HoeqoxQXTU2bXitFmV2tvJBzteuM6/3hJKPoBImXQCkj9WpZs0liIAc54VGq1C3aWTfUxGh7rLcPb5JKFq3beYiLAtL6SJayh3GsjDu7HOM=",
		// publicKey, "utf-8"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
