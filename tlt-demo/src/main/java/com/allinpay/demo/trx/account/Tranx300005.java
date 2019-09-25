package com.allinpay.demo.trx.account;

import java.util.List;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.acquery.AcNode;
import com.allinpay.demo.xstruct.acquery.AcQueryRep;
import com.allinpay.demo.xstruct.acquery.AcQueryReq;
import com.allinpay.demo.xstruct.ahquery.AHQueryRep;
import com.allinpay.demo.xstruct.ahquery.AHQueryReq;
import com.allinpay.demo.xstruct.ahquery.BalNode;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.etdtlquery.EtDtl;
import com.allinpay.demo.xstruct.etdtlquery.EtQReq;
import com.allinpay.demo.xstruct.etdtlquery.EtQRsp;
import com.allinpay.demo.xstruct.etquery.EtNode;
import com.allinpay.demo.xstruct.etquery.EtQueryRep;
import com.allinpay.demo.xstruct.etquery.EtQueryReq;
import com.allinpay.demo.xstruct.trans.CashRep;
import com.allinpay.demo.xstruct.trans.CashReq;

public class Tranx300005 {


	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("300005", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		EtQReq  cashReq = new EtQReq();
		cashReq.setQ_DATE("20180907");
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(cashReq);
		
		try{
			//step1 对象转xml
			String xml = XmlParser.toXml(req);
			//step2 加签
			String signedXml = DemoUtil.buildSignedXml(xml, DemoConfig.pfxpass);
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
				EtQRsp cr = (EtQRsp)rsp.trxObj();
				List<EtDtl> cr1=cr.getDetails();
				for(EtDtl ad:cr1){
					System.out.println(ad.getAMOUNT());
				}
				/**/
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	

}
