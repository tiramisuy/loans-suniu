/**
 * @author dasheng(大圣)
 * 
 */
package com.rongdu.loans.pay.baofoo.agreement.Demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.rongdu.loans.pay.baofoo.agreement.rsa.RsaCodingUtil;
import com.rongdu.loans.pay.baofoo.agreement.rsa.SignatureUtils;
import com.rongdu.loans.pay.baofoo.agreement.util.FormatUtil;
import com.rongdu.loans.pay.baofoo.agreement.util.HttpUtil;
import com.rongdu.loans.pay.baofoo.agreement.util.Log;
import com.rongdu.loans.pay.baofoo.agreement.util.SecurityUtil;


public class ReadySign {
	/**
	 * 预绑卡（协议支付）
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {		
		String send_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//报文发送日期时间		
		String  pfxpath ="D:\\CER_EN_DECODE\\AgreementPay\\bfkey_100025773@@200001173.pfx";//商户私钥        
        String  cerpath = "D:\\CER_EN_DECODE\\AgreementPay\\bfkey_100025773@@200001173.cer";//宝付公钥
		
        String AesKey = "4f66405c4f66405c";//商户自定义（可随机生成  商户自定义(AES key长度为=16位)）
		String dgtl_envlp = "01|"+AesKey;//使用接收方的公钥加密后的对称密钥，并做Base64转码，明文01|对称密钥，01代表AES[密码商户自定义]
		Log.Write("密码dgtl_envlp："+dgtl_envlp);
		dgtl_envlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtl_envlp), cerpath);//公钥加密	

		String Cardinfo = "6212262309006279753|何豪雨|310115199007129776|15823781632||";//账户信息[银行卡号|持卡人姓名|证件号|手机号|银行卡安全码|银行卡有效期]
		
		Log.Write("SHA-1摘要[Cardinfo]结果："+SecurityUtil.sha1X16(Cardinfo, "UTF-8"));
		
		Log.Write("卡信息："+Cardinfo+",长度："+Cardinfo.length());		
		Cardinfo = SecurityUtil.AesEncrypt(SecurityUtil.Base64Encode(Cardinfo), AesKey);//先BASE64后进行AES加密
		Log.Write("AES结果:"+Cardinfo);
		Map<String,String> DateArry = new TreeMap<String,String>();
		DateArry.put("send_time", send_time);
		DateArry.put("msg_id", "TISN"+System.currentTimeMillis());//报文流水号
		DateArry.put("version", "4.0.0.0");
		DateArry.put("terminal_id", "200001173");
		DateArry.put("txn_type", "01");//交易类型
		DateArry.put("member_id", "100025773");
		DateArry.put("dgtl_envlp", dgtl_envlp);
		DateArry.put("user_id", "T117300110134");//用户在商户平台唯一ID
		DateArry.put("card_type", "101");//卡类型  101	借记卡，102 信用卡
		DateArry.put("id_card_type", "01");//证件类型
		DateArry.put("acc_info",Cardinfo);
				
		String SignVStr = FormatUtil.coverMap2String(DateArry);
		Log.Write("SHA-1摘要字串："+SignVStr);
		String signature = SecurityUtil.sha1X16(SignVStr, "UTF-8");//签名
				
		Log.Write("SHA-1摘要结果："+signature);		
				
		String Sign = SignatureUtils.encryptByRSA(signature, pfxpath, "100025773_286941");
		Log.Write("RSA签名结果："+Sign);		
		DateArry.put("signature", Sign);//签名域
		
		String PostString  = HttpUtil.RequestForm("https://vgw.baofoo.com/cutpayment/protocol/backTransRequest", DateArry);	
		Log.Write("请求返回:"+PostString);
		
		Map<String, String> ReturnData = FormatUtil.getParm(PostString);
		
		if(!ReturnData.containsKey("signature")){
			throw new Exception("缺少验签参数！");
		}
		
		String RSign=ReturnData.get("signature");
		Log.Write("返回的验签值："+RSign);
		ReturnData.remove("signature");//需要删除签名字段		
		String RSignVStr = FormatUtil.coverMap2String(ReturnData);
		Log.Write("返回SHA-1摘要字串："+RSignVStr);
		String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");//签名
		Log.Write("返回SHA-1摘要结果："+RSignature);
		
		if(SignatureUtils.verifySignature(cerpath,RSignature,RSign)){
			Log.Write("Yes");//验签成功
		}
		
		if(!ReturnData.containsKey("resp_code")){
			throw new Exception("缺少resp_code参数！");
		}		
		
		if(ReturnData.get("resp_code").toString().equals("S")){	
			if(!ReturnData.containsKey("dgtl_envlp")){
				throw new Exception("缺少dgtl_envlp参数！");
			}
			String RDgtlEnvlp = SecurityUtil.Base64Decode(RsaCodingUtil.decryptByPriPfxFile(ReturnData.get("dgtl_envlp"), pfxpath, "100025773_286941"));		
			Log.Write("返回数字信封："+RDgtlEnvlp);
			String RAesKey=FormatUtil.getAesKey(RDgtlEnvlp);//获取返回的AESkey
			Log.Write("返回的AESkey:"+RAesKey);
			Log.Write("唯一码:"+SecurityUtil.Base64Decode(SecurityUtil.AesDecrypt(ReturnData.get("unique_code"),RAesKey)));
		}
	}

}
