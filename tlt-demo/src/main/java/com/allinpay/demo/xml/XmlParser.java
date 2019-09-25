package com.allinpay.demo.xml;

import com.allinpay.demo.xstruct.accttrans.AcctTransferReq;
import com.allinpay.demo.xstruct.accttransfer.BSum;
import com.allinpay.demo.xstruct.accttransfer.BacctTransferReq;
import com.allinpay.demo.xstruct.accttransfer.Dtl;
import com.allinpay.demo.xstruct.acquery.AcNode;
import com.allinpay.demo.xstruct.acquery.AcQueryRep;
import com.allinpay.demo.xstruct.acquery.AcQueryReq;
import com.allinpay.demo.xstruct.ahquery.AHQueryRep;
import com.allinpay.demo.xstruct.ahquery.AHQueryReq;
import com.allinpay.demo.xstruct.ahquery.BalNode;
import com.allinpay.demo.xstruct.cardbin.QCardBinReq;
import com.allinpay.demo.xstruct.cardbin.QCardBinRsp;
import com.allinpay.demo.xstruct.common.AipgReq;
import com.allinpay.demo.xstruct.common.AipgRsp;
import com.allinpay.demo.xstruct.common.InfoReq;
import com.allinpay.demo.xstruct.etdtlquery.EtDtl;
import com.allinpay.demo.xstruct.etdtlquery.EtQReq;
import com.allinpay.demo.xstruct.etdtlquery.EtQRsp;
import com.allinpay.demo.xstruct.etdtlquery.EtSum;
import com.allinpay.demo.xstruct.etquery.EtNode;
import com.allinpay.demo.xstruct.etquery.EtQueryRep;
import com.allinpay.demo.xstruct.etquery.EtQueryReq;
import com.allinpay.demo.xstruct.netbank.NetBankReq;
import com.allinpay.demo.xstruct.netbank.NetBankRsp;
import com.allinpay.demo.xstruct.quickpay.*;
import com.allinpay.demo.xstruct.stdagr.QAGRDETAIL;
import com.allinpay.demo.xstruct.stdagr.QAGRINFO;
import com.allinpay.demo.xstruct.stdagr.QAGRRSP;
import com.allinpay.demo.xstruct.trans.CashRep;
import com.allinpay.demo.xstruct.trans.CashReq;
import com.allinpay.demo.xstruct.trans.ChargeReq;
import com.allinpay.demo.xstruct.trans.ELE_BILL;
import com.allinpay.demo.xstruct.trans.LedgerDtl;
import com.allinpay.demo.xstruct.trans.Ledgers;
import com.allinpay.demo.xstruct.trans.Refund;
import com.allinpay.demo.xstruct.trans.TransExt;
import com.allinpay.demo.xstruct.trans.TransRet;
import com.allinpay.demo.xstruct.trans.breq.Trans_Detail;
import com.allinpay.demo.xstruct.trans.brsp.Body;
import com.allinpay.demo.xstruct.trans.brsp.Ret_Detail;
import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.allinpay.demo.xstruct.trans.qry.TransQueryReq;
import com.allinpay.demo.xstruct.transfer.Transfer;
import com.allinpay.demo.xstruct.tunotify.TUNotifyRep;
import com.allinpay.demo.xstruct.tunotify.TUNotifyReq;
import com.allinpay.demo.xstruct.ver.RNP;
import com.allinpay.demo.xstruct.ver.RNPA;
import com.allinpay.demo.xstruct.ver.RNPARET;
import com.allinpay.demo.xstruct.ver.RNPC;
import com.allinpay.demo.xstruct.ver.RNPCRET;
import com.allinpay.demo.xstruct.ver.RNPR;
import com.allinpay.demo.xstruct.ver.RNPRRET;
import com.allinpay.demo.xstruct.ver.ValbSum;
import com.allinpay.demo.xstruct.ver.ValidBD;
import com.allinpay.demo.xstruct.ver.ValidBReq;
import com.allinpay.demo.xstruct.ver.ValidR;
import com.allinpay.demo.xstruct.ver.ValidRet;
import com.allinpay.demo.xstruct.ver.ValidTR;
import com.allinpay.demo.xstruct.ver.VbDetail;
import com.allinpay.demo.xstruct.ver.idv.IdVer;
import com.allinpay.demo.xstruct.ver.idv.VALIDRETDTL;
import com.thoughtworks.xstream.XStream;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class XmlParser {
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	private static final XStream xsreq = initXStream(new XStreamEx(), true);
	private static final XStream xsrsp = initXStream(new XStreamEx(), false);

	public static AipgReq parseReq(String xml) {
		return (AipgReq) xsreq.fromXML(xml);
	}

	public static AipgRsp parseRsp(String xml) {
		return (AipgRsp) xsrsp.fromXML(xml);
	}

	public static String toXml(Object o) {
		return (o instanceof AipgReq) ?  XmlParser.toXml(xsreq, o) : XmlParser.toXml(xsrsp, o);

	}

	public static String toXml(XStream xs, Object o) {
		String xml;
		xml = xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		xml = HEAD + xml;
		return xml;
	}

	public static AipgReq xmlReq(String xmlMsg) {
		AipgReq req = (AipgReq) xsreq.fromXML(xmlMsg);
		return req;
	}

	public static AipgRsp xmlRsp(String xmlMsg) {
		AipgRsp rsp = (AipgRsp) xsrsp.fromXML(xmlMsg);
		return rsp;
	}

	public static String reqXml(AipgReq req) {
		String xml = HEAD + xsreq.toXML(req);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static String rspXml(AipgRsp rsp) {
		String xml = HEAD + xsrsp.toXML(rsp);
		xml = xml.replace("__", "_");
		return xml;
	}

	public static XStream initXStream(XStream xs, boolean isreq) {
		if (isreq) {
			xs.alias("AIPG", AipgReq.class);
			xs.alias("BODY", com.allinpay.demo.xstruct.trans.breq.Body.class);
			xs.alias("TRANS_DETAIL", Trans_Detail.class);
			xs.aliasField("TRANS_DETAILS", com.allinpay.demo.xstruct.trans.breq.Body.class,
					"details");
		} else {
			xs.alias("AIPG", AipgRsp.class);
			xs.alias("BODY", Body.class);
		}
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("TRANS", TransExt.class);
		xs.alias("LEDGERS", Ledgers.class);
		xs.addImplicitCollection(Ledgers.class, "list");
		xs.alias("LEDGERDTL", LedgerDtl.class);
		xs.alias("TRANSRET", TransRet.class);
		xs.alias("TRANSRET", TransRet.class);
		xs.alias("VALIDR", ValidR.class);
		xs.alias("VALIDTR", ValidTR.class);
		xs.alias("QAGRINFO", QAGRINFO.class);
		xs.alias("TRANSFER", Transfer.class);
		
		xs.alias("IDVER", IdVer.class);
	
		
		xs.alias("FASTTRXRET", FASTTRXRET.class);
		xs.alias("FASTTRX_DETAIL", FasttrxDetail.class);
		xs.alias("VALIDRET", ValidRet.class);
		xs.alias("VALIDBREQ", ValidBReq.class);
		xs.alias("VALBSUM", ValbSum.class);
		xs.alias("VALIDBD", ValidBD.class);
		xs.alias("VBDETAIL", VbDetail.class);
		xs.addImplicitCollection(ValidBD.class, "details");
		xs.addImplicitCollection(QTransRsp.class, "details");
		xs.addImplicitCollection(QAGRRSP.class, "details");
		xs.addImplicitCollection(AcQueryRep.class, "details");
		xs.addImplicitCollection(AHQueryRep.class, "details");
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
		xs.alias("QAGRRSP", QAGRRSP.class);
		xs.alias("ACQUERYREP", AcQueryRep.class);
		xs.alias("QAGRDETAIL", QAGRDETAIL.class);
		
		xs.aliasField("RET_DETAILS", Body.class, "details");
		xs.alias("RET_DETAIL", Ret_Detail.class);
		xs.alias("REFUND", Refund.class);
		
		xs.alias("CHARGEREQ", ChargeReq.class);
		xs.alias("VALIDRETDTL", VALIDRETDTL.class);
		
		xs.alias("QCARDBINREQ", QCardBinReq.class);
		xs.alias("QCARDBINRSP", QCardBinRsp.class);
		xs.alias("ACQUERYREQ", AcQueryReq.class);
		xs.alias("ACNODE", AcNode.class);
		xs.alias("FASTTRXRETC", FASTTRXRETC.class);
		xs.alias("RNP", RNP.class);
		xs.alias("RNPA", RNPA.class);
		xs.alias("RNPARET", RNPARET.class);
		xs.alias("RNPR", RNPR.class);
		xs.alias("RNPRRET", RNPRRET.class);
		xs.alias("RNPC", RNPC.class);
		xs.alias("RNPCRET", RNPCRET.class);

		xs.alias("FAGRA", FAGRA.class);
		xs.alias("FAGRARET", FAGRARET.class);
//		xs.alias("FAGRC", FAGRCEXT.class);
		xs.alias("FAGRC", FAGRC.class);
		xs.alias("FAGRCRET", FAGRCRET.class);
		xs.alias("FAGRCNL", FAGRCNL.class);
		xs.alias("FAGRCNLRET", FAGRCNLRET.class);
		xs.alias("FASTTRX", FASTTRX.class);
		xs.alias("FASTTRXRET", FASTTRXRET.class);
		xs.alias("ELE_BILL", ELE_BILL.class);
		
		xs.alias("AHQUERYREQ", AHQueryReq.class);
		xs.alias("AHQUERYREP", AHQueryRep.class);
		xs.alias("BALNODE", BalNode.class);
		xs.alias("TUQNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("ETQREQ", EtQReq.class);
		xs.alias("ETQRSP", EtQRsp.class);
		xs.addImplicitCollection(EtQRsp.class, "details");
		xs.alias("ETSUM", EtSum.class);
		xs.alias("ETDTL", EtDtl.class);
		xs.aliasField("ETDTLS", com.allinpay.demo.xstruct.trans.breq.Body.class,
				"ETDTLS");
		xs.alias("ACCTTRANSFERREQ", AcctTransferReq.class);
		
		xs.alias("BACCTTRANSFERREQ", BacctTransferReq.class);
		xs.aliasField("DTLS", Body.class, "details");
		xs.alias("BSUM", BSum.class);
		
		xs.alias("DTL", Dtl.class);
		xs.alias("NETBANKREQ", NetBankReq.class);
		xs.alias("NETBANKRSP", NetBankRsp.class);
		return xs;
	}


}
