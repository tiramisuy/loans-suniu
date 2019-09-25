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
import com.allinpay.demo.xstruct.ver.ValbSum;
import com.allinpay.demo.xstruct.ver.ValidBD;
import com.allinpay.demo.xstruct.ver.ValidBReq;
import com.allinpay.demo.xstruct.ver.VbDetail;


/**
 * @Description 批量实名验证（三要素）
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx211000 {

	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("211000", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		
		ValidBReq vbreq=new ValidBReq();
		ValbSum VALBSUM =new ValbSum();
		VALBSUM.setMERCHANT_ID(DemoConfig.merchantid);
		VALBSUM.setSUBMIT_TIME(DemoUtil.getNow());
		VALBSUM.setTOTAL_ITEM("12");
		
		ValidBD VALIDBD=new ValidBD();
		VbDetail vbdetail=null;
		for(int i=0;i<12;i++){
			vbdetail=new VbDetail();
			vbdetail.setACCOUNT_NAME("银联" + i);
			vbdetail.setACCOUNT_NO("6224243000000038" + i);
			vbdetail.setACCOUNT_PROP("0");
			vbdetail.setACCOUNT_TYPE("00");
			vbdetail.setSN("00"+i);
			vbdetail.setOPTYPE("01");//01—新增；02—解除；03—更改
			vbdetail.setID_TYPE("0");//证件类型：0 身份证
			vbdetail.setID("44201010423543543543");//身份证号
			VALIDBD.addDTL(vbdetail);
		}
		vbreq.setVALBSUM(VALBSUM);
		vbreq.setVALIDBD(VALIDBD);
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(vbreq);
		
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
				
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
		
	}
}
