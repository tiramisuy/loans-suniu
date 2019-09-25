package com.allinpay.demo.trx;

import java.util.ArrayList;
import java.util.List;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.trans.breq.Body;
import com.allinpay.demo.xstruct.trans.breq.Trans_Detail;
import com.allinpay.demo.xstruct.trans.breq.Trans_Sum;
import com.allinpay.demo.xstruct.trans.brsp.Ret_Detail;

/**
 * @Description 批量代付
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx100002 {

	public static void main(String args[]){
		InfoReq inforeq = DemoUtil.makeReq("100002",DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		Body reqBody = new Body();
		
		Trans_Sum trans_sum = new Trans_Sum();
		trans_sum.setBUSINESS_CODE("09900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		trans_sum.setMERCHANT_ID(DemoConfig.merchantid) ;
		trans_sum.setTOTAL_ITEM("2") ;
		trans_sum.setTOTAL_SUM("4") ;
		reqBody.setTRANS_SUM(trans_sum);
		
		List<Trans_Detail> transList = new ArrayList<Trans_Detail>();
		Trans_Detail trans_detail = new Trans_Detail() ;
		trans_detail.setSN("1") ;
    	trans_detail.setACCOUNT_NAME("测试1") ;
 		trans_detail.setACCOUNT_PROP("0") ;
		trans_detail.setACCOUNT_NO("3228481200290317805") ;
		trans_detail.setBANK_CODE("0103") ;
		trans_detail.setAMOUNT("2") ;
		trans_detail.setCURRENCY("CNY");
		transList.add(trans_detail) ;
		
		Trans_Detail trans_detail2 = new Trans_Detail() ;
		trans_detail2.setSN("2") ;
		trans_detail2.setACCOUNT_NAME("测试2") ;
		trans_detail2.setACCOUNT_NO("3228481200290317809") ;
		trans_detail2.setBANK_CODE("0103") ;
		trans_detail2.setAMOUNT("2") ;
		trans_detail2.setCURRENCY("CNY");
		transList.add(trans_detail2);
		reqBody.setDetails(transList);
		
		AipgReq req = new AipgReq();
		req.setINFO(inforeq);
		req.addTrx(reqBody);
		try{
			//step1 对象转xml
			String xml = XmlParser.toXml(req);
			//step2 加签
			String signedXml = DemoUtil.buildSignedXml(xml,DemoConfig.pfxpass);
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
			if("0000".equals(infoRsp.getRET_CODE())){
				com.allinpay.demo.xstruct.trans.brsp.Body rspBody = (com.allinpay.demo.xstruct.trans.brsp.Body)rsp.trxObj();
				@SuppressWarnings("unchecked")
				List<Ret_Detail> rspList = rspBody.getDetails();
				for(Ret_Detail rd : rspList){
					System.out.println(rd.getSN());
					System.out.println(rd.getRET_CODE());
					System.out.println(rd.getERR_MSG());
				}
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}

}
