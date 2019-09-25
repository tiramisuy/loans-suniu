package com.allinpay.demo.trx.qpay;

import java.util.ArrayList;
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
import com.allinpay.demo.xstruct.quickpay.FASTTRX;
import com.allinpay.demo.xstruct.quickpay.FASTTRXRET;
import com.allinpay.demo.xstruct.quickpay.FasttrxDetail;
import com.allinpay.demo.xstruct.quickpay.RET_DETAIL;
import com.allinpay.demo.xstruct.quickpay.RET_DETAILS;
import com.allinpay.demo.xstruct.trans.LedgerDtl;
import com.allinpay.demo.xstruct.trans.breq.Body;
import com.allinpay.demo.xstruct.trans.breq.Trans_Sum;
import com.allinpay.demo.xstruct.trans.brsp.Ret_Detail;

public class Tranx310016 {


	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("310016", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		Body reqBody = new Body();
		Trans_Sum trans_sum = new Trans_Sum();
		trans_sum.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		trans_sum.setMERCHANT_ID(DemoConfig.merchantid) ;
		trans_sum.setTOTAL_ITEM("1") ;
		trans_sum.setTOTAL_SUM("1000000") ;
		reqBody.setTRANS_SUM(trans_sum);
		List<FasttrxDetail> fasttrxDetails=new ArrayList<FasttrxDetail>();
		FasttrxDetail fasttrxDetail=new FasttrxDetail(); 
	
		fasttrxDetail.setSN("00001");
		fasttrxDetail.setAGRMNO("AIP5988180829000002067");
		fasttrxDetail.setACCOUNT_NO("6217000340012565987");
		fasttrxDetail.setACCOUNT_NAME("买单宝专用四");
		fasttrxDetail.setAMOUNT("1000000");
		fasttrxDetail.setCUST_USERID("哈哈哈哈");
		fasttrxDetail.setREMARK("a发送到发斯蒂芬");
		fasttrxDetail.setSUMMARY("asjdfasdfkasdf");
		fasttrxDetails.add(fasttrxDetail);
		reqBody.setDetails(fasttrxDetails);
		LedgerDtl dtl1 = new LedgerDtl();
		dtl1.setAMOUNT("1");
		dtl1.setMERCHANT_ID(DemoConfig.merchantid);
		dtl1.setSN("0");
		dtl1.setTYPE("0");
		
		/*Ledgers ledgers = new Ledgers();
		ledgers.addTrx(dtl1);*/
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(reqBody);
		
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
			com.allinpay.demo.xstruct.trans.brsp.Body rspBody = (com.allinpay.demo.xstruct.trans.brsp.Body)rsp.trxObj();
			System.out.println(infoRsp.getRET_CODE());
			System.out.println(infoRsp.getERR_MSG());
			if("0000".equals(infoRsp.getRET_CODE())){
				@SuppressWarnings("unchecked")
				List<Ret_Detail> rspList = rspBody.getDetails();
				for(Ret_Detail ret_DETAIL:rspList){
					System.out.println(ret_DETAIL.getSN());
					System.out.println(ret_DETAIL.getRET_CODE());
					System.out.println(ret_DETAIL.getERR_MSG());
				}
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	

}
