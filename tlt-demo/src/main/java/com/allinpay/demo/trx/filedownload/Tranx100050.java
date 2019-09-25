package com.allinpay.demo.trx.filedownload;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.trans.ELE_BILL;



/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年7月16日
 **/
public class Tranx100050 {
	
	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("100050", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		ELE_BILL eb = new ELE_BILL();
		eb.setFILENAME("200604000005095-0001535595283870");
		eb.setFSN("0");
		eb.setMERCHANT_ID("200604000005095");
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(eb);
		try{
			//step1 对象转xml
			final String xml = XmlParser.toXml(req);
			//step2 加签
			final String signedXml = DemoUtil.buildSignedXml(xml, DemoConfig.pfxpass);
			//step3 发往通联
			final String url = DemoConfig.url;//"https://172.16.1.11:8443/aipg/ProcessServlet";
			System.out.println("============================请求报文============================");
			System.out.println(signedXml);
			try{
				System.out.println("请求" + Thread.currentThread().getId());
				String respText = HttpUtil.downloadFile(url, signedXml, "E:/file", "pdf");
				System.out.println("============================响应报文============================");
				System.out.println(respText);
			}catch(Exception e){
				e.printStackTrace();
			}
			//step4 验签
			/*
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

			}
			*/
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
}
