package com.allinpay.demo.trx.filedownload;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import com.allinpay.demo.AIPGException;
import com.allinpay.demo.DemoConfig;
import com.allinpay.demo.util.DemoUtil;
import com.allinpay.demo.util.HttpUtil;
import com.allinpay.demo.xml.XmlParser;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.trans.qry.TransQueryReq;
public class FilePost {
	public static void main(String args[]){
		AipgReq req = new AipgReq();
		InfoReq inforeq = DemoUtil.makeReq("200002", DemoConfig.merchantid, DemoConfig.username, DemoConfig.userpass);
		TransQueryReq dqr=new TransQueryReq();
		req.setINFO(inforeq);
		req.addTrx(dqr);
		dqr.setSTATUS(2);
		dqr.setMERCHANT_ID(DemoConfig.merchantid );
		dqr.setTYPE(1) ;
		dqr.setSTART_DAY("20180511000000");
		dqr.setEND_DAY("20180511235959");
		dqr.setCONTFEE("1") ;
		
		String xml = XmlParser.toXml(req);
		//step2 加签
		try {
		String signedXml = DemoUtil.buildSignedXml(xml, DemoConfig.pfxpass);
		String url = DemoConfig.url;
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
			writeBill(respText);
			System.out.println("====================================================>验签成功");
		} catch (AIPGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
private static void writeBill(String resp) throws Exception {
		
		int iStart = resp.indexOf("<CONTENT>");
		if(iStart==-1) throw new Exception("XML报文中不存在<CONTENT>");
		int end = resp.indexOf("</CONTENT>");
		if(end==-1) throw new Exception("XML报文中不存在</CONTENT>");	
		String billContext = resp.substring(iStart + 9, end);
		
		//写文件
		FileOutputStream sos=null;
		sos=new FileOutputStream(new File("bills/bill.gz"));
		Base64InputStream b64is=new Base64InputStream(IOUtils.toInputStream(billContext),false);
		IOUtils.copy(b64is, sos);
		IOUtils.closeQuietly(b64is);
		//解压
		ZipInputStream zin=new ZipInputStream(new FileInputStream(new File("bills/bill.gz")));
		ZipEntry zipEntry=null;
		 while ((zipEntry = zin.getNextEntry()) != null) {  
			 String entryName = zipEntry.getName().toLowerCase(); 
			 FileOutputStream os = new FileOutputStream("bills/"+entryName);  
             // Transfer bytes from the ZIP file to the output file  
             byte[] buf = new byte[1024];  
             int len;  
             while ((len = zin.read(buf)) > 0) {  
                 os.write(buf, 0, len);  
             }  
             os.close();  
             zin.closeEntry();
		 }
	}
}
