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
import com.allinpay.demo.xstruct.ver.RNPR;
import com.allinpay.demo.xstruct.ver.RNPRRET;


/**
 * @Description 实名付重发
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx211006R {
	
	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("211006R", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		RNPR rnpr = new RNPR();
		rnpr.setMERCHANT_ID(DemoConfig.merchantid);
		rnpr.setSRCREQSN("200604000001591-0001527687288109");
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(rnpr);
		
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
				RNPRRET ret = (RNPRRET) rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	
}
