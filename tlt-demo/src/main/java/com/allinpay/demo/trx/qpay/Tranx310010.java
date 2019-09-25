package com.allinpay.demo.trx.qpay;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.quickpay.FASTTRX;
import com.allinpay.demo.xstruct.quickpay.FASTTRXRET;
import com.allinpay.demo.xstruct.quickpay.FASTTRXRETC;
import com.allinpay.demo.xstruct.trans.LedgerDtl;

public class Tranx310010 {


	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("310010", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		FASTTRX ft = new FASTTRX();
		ft.setMERCHANT_ID(DemoConfig.merchantid);
		ft.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		ft.setSUBMIT_TIME(DemoUtil.getNow());
		ft.setAGRMNO("AIP5988180829000002067");
		ft.setACCOUNT_NAME("买单宝专用四");
		ft.setAMOUNT("90");
		ft.setVER_CODE("111111");
		ft.setSRC_REQ_SN("200604000005095-0001536132935606");
		ft.setCUST_USERID("哈哈哈哈");
		ft.setREMARK("a发送到发斯蒂芬");
		ft.setSUMMARY("asjdfasdfkasdf");
		
		LedgerDtl dtl1 = new LedgerDtl();
		dtl1.setAMOUNT("1");
		dtl1.setMERCHANT_ID(DemoConfig.merchantid);
		dtl1.setSN("0");
		dtl1.setTYPE("0");
		
		/*Ledgers ledgers = new Ledgers();
		ledgers.addTrx(dtl1);*/
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(ft);
		/*req.addTrx(ledgers);*/
		
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
				FASTTRXRETC ret = (FASTTRXRETC) rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());
				System.out.println(ret.getSETTLE_DAY());
				
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	

}
