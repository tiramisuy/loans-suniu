package com.allinpay.demo.trx.filedownload;

import java.security.Provider;

import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.AIPGSignature;
import com.allinpay.demo.util.FileUtil;
import com.allinpay.demo.util.HttpUtil;

public class FileGet {
	private static Provider prvd = null;
	public static void main(String args[]){
		String url=DemoConfig.urlFileGet;
		String MERID =DemoConfig.merchantid;
		String SETTDAY ="20180905";
		String REQTIME ="20180629143211";//df.format(new Date());
		String CONTFEE ="1";
		String SIGN ="";
		CONTFEE=SETTDAY+"|"+REQTIME+"|"+MERID;
		System.out.println(CONTFEE);
		try {
			AIPGSignature signature = new AIPGSignature(prvd);
			SIGN = signature.signMsg(CONTFEE, DemoConfig.pathpfx, DemoConfig.pfxpass);
		url=url.replaceAll("@xxx", SETTDAY).replaceAll("@yyy", REQTIME).replaceAll("@zzz", MERID).replaceAll("@sss", SIGN);
		System.out.println(url);
		CONTFEE=HttpUtil.post("", url);
		} catch (AIPGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileUtil.saveToFile(CONTFEE, "bill.txt", "");//默认编码GBK
	}
}
