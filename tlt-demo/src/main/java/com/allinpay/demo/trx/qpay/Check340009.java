package com.allinpay.demo.trx.qpay;

import java.util.List;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.stdagr.QAGRDETAIL;
import com.allinpay.demo.xstruct.stdagr.QAGRINFO;
import com.allinpay.demo.xstruct.stdagr.QAGRRSP;
import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.allinpay.demo.xstruct.trans.qry.TransQueryReq;

public class Check340009 {

	public static void main(String[] args) {
		InfoReq infoReq = DemoUtil.makeReq("340009",DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		QAGRINFO QAGRINFO = new QAGRINFO();
		QAGRINFO.setMERCHANT_ID(DemoConfig.merchantid);
		QAGRINFO.setAGRTYPE("01");
		QAGRINFO.setQUERY_MODE("3");
		QAGRINFO.setACCOUNT_NO("6217000340012565987");
		///查询交易的文件名
		/*queryReq.setSTART_DAY("20180613000000");
		queryReq.setEND_DAY("20180613235959");*/
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(QAGRINFO);
		
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
				QAGRRSP ret = (QAGRRSP)rsp.trxObj() ;
				@SuppressWarnings("unchecked")
				List<QAGRDETAIL> list = ret.getDetails();
				for(QAGRDETAIL dt : list){
					System.out.println(dt.getAGRMNO());
					
				}
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}

}
