package com.allinpay.demo.trx.ver;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.ver.ValidR;
import com.allinpay.demo.xstruct.ver.ValidRet;

/**
 * @Description 账户实名签约(三要素)
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx211008 {

	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("211008", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		ValidR valid=new ValidR();
		valid.setACCOUNT_NAME("朱瑾春");
		valid.setACCOUNT_NO("6220025378707780542");
		valid.setACCOUNT_PROP("0");
		valid.setACCOUNT_TYPE("00");
		valid.setBANK_CODE("0102");
		valid.setID("420101198101012286");
		valid.setID_TYPE("0");
		valid.setMERCHANT_ID(DemoConfig.merchantid);
		valid.setTEL("13600913244");
		valid.setSUBMIT_TIME(DemoUtil.getNow());
		valid.setREMARK("单笔实时身份验证-备注字段");
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(valid);
		
		try{
			//step1 对象转xml
			String xml = XmlParser.toXml(req);
			//step2 加签
			String signedXml = DemoUtil.buildSignedXml(xml,DemoConfig.pfxpass);
			//step3 发往通联
			String url = DemoConfig.url+"?MERCHANT_ID="+DemoConfig.merchantid+"&REQ_SN="+infoReq.getREQ_SN();
			System.out.println("============================请求报文============================");
			System.out.println(signedXml);
			String respText = HttpUtil.post(signedXml, url);
			System.out.println("============================响应报文============================");
			System.out.println(respText);
			//step4 验签
			if(!DemoUtil.verifyXml(respText)){
				System.out.println("====================================================>验签失败");
				return;
			}
			System.out.println("====================================================>验签成功");
			//step5 xml转对象
			AipgRsp rsp = XmlParser.parseRsp(respText);
			InfoRsp infoRsp = rsp.getINFO();
			System.out.println(infoRsp.getRET_CODE());
			System.out.println(infoRsp.getERR_MSG());
			if("0000".equals(infoRsp.getRET_CODE())){
				ValidRet ret = (ValidRet)rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	
}
