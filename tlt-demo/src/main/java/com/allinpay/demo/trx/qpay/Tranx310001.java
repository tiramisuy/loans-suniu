package com.allinpay.demo.trx.qpay;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.quickpay.FAGRA;
import com.allinpay.demo.xstruct.quickpay.FAGRARET;

/**
 * @Description  快捷签约短信发起
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class Tranx310001 {

	public static void main(String args[]){
		InfoReq inforeq = DemoUtil.makeReq("310001", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		FAGRA fagra = new FAGRA();
		fagra.setACCOUNT_NAME("买单宝专用四");//
		fagra.setACCOUNT_NO("6217000340012565987");//
		fagra.setACCOUNT_PROP("0");
		fagra.setTEL("15601257600");//
		fagra.setID("310106198707030063");//
		fagra.setID_TYPE("0");
		fagra.setMERCHANT_ID(DemoConfig.merchantid);
		fagra.setACCOUNT_TYPE("00");
		fagra.setCVV2("");
		fagra.setVALIDDATE("");
		fagra.setMERREM("123");
		fagra.setREMARK("123123123");
		
		AipgReq req = new AipgReq();
		req.setINFO(inforeq);
		req.addTrx(fagra);
		try{
			//step1 对象转xml
			String xml = XmlParser.toXml(req);
			//step2 加签
			String signedXml = DemoUtil.buildSignedXml(xml, DemoConfig.pfxpass);
			//step3 发往通联
			String url = DemoConfig.url+"?MERCHANT_ID="+DemoConfig.merchantid+"&REQ_SN="+inforeq.getREQ_SN();
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
			System.out.println(infoRsp.getREQ_SN());
			if("0000".equals(infoRsp.getRET_CODE())){
				FAGRARET fr = (FAGRARET)rsp.trxObj();
				System.out.println(fr.getRET_CODE());
				System.out.println(fr.getERR_MSG());
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
}
