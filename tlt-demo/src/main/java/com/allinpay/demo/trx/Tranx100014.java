package com.allinpay.demo.trx;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.common.InfoRsp;
import com.allinpay.demo.xstruct.trans.TransExt;
import com.allinpay.demo.xstruct.trans.TransRet;

/**
 * @Description 单笔实时代付
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx100014 {

	public static void main(String[] args){
		
		InfoReq infoReq = DemoUtil.makeReq("100014", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		TransExt trans = new TransExt();
		trans.setBUSINESS_CODE("09900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		trans.setMERCHANT_ID(DemoConfig.merchantid);
		trans.setSUBMIT_TIME(DemoUtil.getNow());
		trans.setACCOUNT_NAME("银联000000");
		trans.setACCOUNT_NO("6224243000000038");
		trans.setACCOUNT_PROP("0");
		trans.setAMOUNT("100");
		trans.setBANK_CODE("0102");
		trans.setCURRENCY("CNY");
		trans.setTEL("");
		trans.setCUST_USERID("410421197808247033");
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(trans);
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
				TransRet ret = (TransRet) rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());
				System.out.println(ret.getSETTLE_DAY());
				
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}

}
