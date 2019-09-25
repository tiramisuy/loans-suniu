package com.rongdu.loans.pay.baofoo.demo;

import com.rongdu.loans.pay.baofoo.demo.base.TransContent;
import com.rongdu.loans.pay.baofoo.demo.base.request.TransReqBF0040002;
import com.rongdu.loans.pay.baofoo.demo.base.response.TransRespBF0040002;
import com.rongdu.loans.pay.baofoo.domain.RequestParams;
import com.rongdu.loans.pay.baofoo.http.SimpleHttpResponse;
import com.rongdu.loans.pay.baofoo.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.util.BaofooClient;
import com.rongdu.loans.pay.baofoo.util.SecurityUtil;
import com.rongdu.loans.pay.baofoo.util.TransConstant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BF0040002SDKDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Double txAmt = Double.valueOf("1234.000")*0.01;
		BigDecimal   decimal   =   new  BigDecimal(txAmt);  
		txAmt   =   decimal.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(txAmt);

		TransContent<TransReqBF0040002> transContent = new TransContent<TransReqBF0040002>(
				TransConstant.data_type_xml);

		List<TransReqBF0040002> trans_reqDatas = new ArrayList<TransReqBF0040002>();

		TransReqBF0040002 transReqData = new TransReqBF0040002();
//		transReqData.setTrans_batchid("20620975");
		transReqData.setTrans_no("HXBWD91707513163720");

		trans_reqDatas.add(transReqData);

		transContent.setTrans_reqDatas(trans_reqDatas);

		String bean2XmlString = transContent.obj2Str(transContent);
		System.out.println("报文：" + bean2XmlString);

		String keyStorePath = "C:/Users/MARK/Desktop/聚宝钱包二期/产品管理/0-账户与支付/宝付/接口文档/宝付代付API接口文档/demo/JAVA DEMO/cer/m_pri.pfx";
		String keyStorePassword = "123456";
		String pub_key = "C:/Users/MARK/Desktop/聚宝钱包二期/产品管理/0-账户与支付/宝付/接口文档/宝付代付API接口文档/demo/JAVA DEMO/cer/baofoo_pub.cer";
		String origData = bean2XmlString;
		//origData = Base64.encode(origData);
		/**
		 * 加密规则：项目编码UTF-8 
		 * 第一步：BASE64 加密
		 * 第二步：商户私钥加密
		 */
		origData =  new String(SecurityUtil.Base64Encode(origData));//Base64.encode(origData);
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
				keyStorePath, keyStorePassword);

		System.out.println("----------->【私钥加密-结果】" + encryptData);

		// 发送请求
		String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040002.do";
		String memberId = "100000178"; // 商户号
		String terminalId = "100000859"; // 终端号
		String dataType = TransConstant.data_type_xml; // 数据类型 xml/json

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(dataType);
		params.setDataContent(encryptData);// 加密后数据
		params.setVersion("4.0.0");
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		System.out.println("宝付请求返回结果：" + response.getEntityString());

		TransContent<TransRespBF0040002> str2Obj = new TransContent<TransRespBF0040002>(dataType);

		String reslut = response.getEntityString();
		
		/**
		 * 在商户终端正常的情况下宝付同步返回会以密文形式返回,如下：
		 * 
		 * 此时要先宝付提供的公钥解密：RsaCodingUtil.decryptByPubCerFile(reslut, pub_key)
		 * 
		 * 再次通过BASE64解密：new String(new Base64().decode(reslut))
		 * 
		 * 在商户终端不正常或宝付代付系统异常的情况下宝付同步返回会以明文形式返回
		 */
		System.out.println(reslut);
		if (reslut.contains("trans_content")) {
			// 我报文错误处理
			str2Obj = (TransContent<TransRespBF0040002>) str2Obj.str2Obj(reslut,TransRespBF0040002.class);
			//业务逻辑判断
		} else {
			reslut = RsaCodingUtil.decryptByPubCerFile(reslut, pub_key);
			reslut = SecurityUtil.Base64Decode(reslut);
			System.out.println(reslut);
			str2Obj = (TransContent<TransRespBF0040002>) str2Obj.str2Obj(reslut,TransRespBF0040002.class);
			//业务逻辑判断
		}
	}

}
