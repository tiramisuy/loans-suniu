package com.rongdu.loans.constant;

import java.util.HashMap;
import java.util.Map;

public class Dict {
	public static Map<String,String> indivSex = new HashMap<String,String>();
	public static Map<String,String> indivMarital = new HashMap<String,String>();
	public static Map<String,String> idType = new HashMap<String,String>();
	public static Map<String,String> idTermLong = new HashMap<String,String>();	
	public static Map<String,String> indivDegree = new HashMap<String,String>();
	public static Map<String,String> indivLiveEqReg = new HashMap<String,String>();
	public static Map<String,String> indivLive = new HashMap<String,String>();
	public static Map<String,String> indivEmpType = new HashMap<String,String>();
	public static Map<String,String> indivEmpNum = new HashMap<String,String>();
	public static Map<String,String> indivIndtryPaper = new HashMap<String,String>();
	public static Map<String,String> indivPosition = new HashMap<String,String>();
	public static Map<String,String> indivCardInd = new HashMap<String,String>();
	public static Map<String,String> purpose = new HashMap<String,String>();
	public static Map<String,String> applyTnr = new HashMap<String,String>();
	public static Map<String,String> loanMtd = new HashMap<String,String>();
	public static Map<String,String> mailAddr = new HashMap<String,String>();
	public static Map<String,String> smsNotice = new HashMap<String,String>();
	public static Map<String,String> indivRelation = new HashMap<String,String>();
	public static Map<String,String> bankRel = new HashMap<String,String>();
	public static Map<String,String> spreadTyp = new HashMap<String,String>();
	public static Map<String,String> applSrc = new HashMap<String,String>();
	public static Map<String,String> assetKind = new HashMap<String,String>();
	public static Map<String,String> assetTyp = new HashMap<String,String>();
	
	static{
		//性别 INDIV_SEX
		indivSex.put("男","1");
		indivSex.put("女","2");
		indivSex.put("未知的性别","3");
		indivSex.put("未说明的性别","4");
		//婚姻状况 INDIV_MARITAL
		indivMarital.put("未婚","10");
		indivMarital.put("已婚","20");
		indivMarital.put("其他","90");								
		//申请人证件类型 ID_TYPE
		idType.put("其他证件","2X");
		idType.put("外国人居留证","28");
		idType.put("身份证","20");
		idType.put("户口本","21");
		idType.put("护照","22");
		idType.put("士兵证","24");
		idType.put("港澳居民来往内地通行证","25");
		idType.put("台湾同胞来往内地通行证","26");
		idType.put("临时身份证","27");
		idType.put("组织部门编号证","1A");
		idType.put("警官证","29");
		idType.put("军官证","23");
		//身份证件是否长期有效 ID_TERM_LONG
		idTermLong.put("是","Y");
		idTermLong.put("否","N");		
		//文化程度 INDIV_DEGREE
		indivDegree.put("硕士","09");
		indivDegree.put("博士及以上","08");
		indivDegree.put("本科","10");
		indivDegree.put("大专","20");
		indivDegree.put("高中或中专","30");
		indivDegree.put("初中及以下","40");
		//现居住地址是否同户籍地址 INDIV_LIVE_EQ_REG
		indivLiveEqReg.put("是","Y");
		indivLiveEqReg.put("否","N");	
		//现居住住房性质 INDIV_LIVE		
		indivLive.put("自购无贷款","1	");
		indivLive.put("自购有贷款","2");	
		indivLive.put("亲属楼宇","3");	
		indivLive.put("集体宿舍","4");	
		indivLive.put("租房","5	");
		indivLive.put("共有住宅","6");	
		indivLive.put("其他","7");	
		indivLive.put("未知","9");
		//单位性质 INDIV_EMP_TYPE
		indivEmpType.put("国家机关、事业单位","A");	
		indivEmpType.put("国有企业","B");
		indivEmpType.put("集体企业","C");
		indivEmpType.put("中外合资/中外合作/外商独资","D");
		indivEmpType.put("股份制企业","E");
		indivEmpType.put("私营企业","F");
		indivEmpType.put("其他","G");
		//单位规模 INDIV_EMP_NUM		
		indivEmpNum.put("50人以下","1");
		indivEmpNum.put("51-100人","2");
		indivEmpNum.put("101-500人","3");
		indivEmpNum.put("500人以上","4");
		//行业性质 INDIV_INDTRY_PAPER
		indivIndtryPaper.put("个体工商户","01");
		indivIndtryPaper.put("批发/零售","02");
		indivIndtryPaper.put("制造业","03");
		indivIndtryPaper.put("房地产","04");
		indivIndtryPaper.put("建筑业","05");
		indivIndtryPaper.put("酒店/旅游/餐饮","06");
		indivIndtryPaper.put("交通运输/仓储/邮政业","07");
		indivIndtryPaper.put("传媒/体育/娱乐","08");
		indivIndtryPaper.put("专业事务所","09");
		indivIndtryPaper.put("信息传输/计算机服务/软件业","10");
		indivIndtryPaper.put("生物/医药","11");
		indivIndtryPaper.put("金融","12");
		indivIndtryPaper.put("公共事业","13");
		indivIndtryPaper.put("石油/石化","14");
		indivIndtryPaper.put("教育","15");
		indivIndtryPaper.put("医疗卫生","16");
		indivIndtryPaper.put("科研院所","17");
		indivIndtryPaper.put("水利/环境/公共设施管理业","18");
		indivIndtryPaper.put("烟草","19");
		indivIndtryPaper.put("军事机构","20");
		indivIndtryPaper.put("社会团体","21");
		indivIndtryPaper.put("政府机构/公检法","22");
		indivIndtryPaper.put("自由职业","23");
		indivIndtryPaper.put("租赁/服务业","24");
		indivIndtryPaper.put("其他","99");
		//现任职位 INDIV_POSITION 
		indivPosition.put("高级管理人员","1");
		indivPosition.put("一般管理人员","2");
		indivPosition.put("一般正式员工","3");
		indivPosition.put("非正式员工","4");
		indivPosition.put("其他","9");
		//是否有银行信用卡 INDIV_CARD_IND 
		indivCardInd.put("是","Y");
		indivCardInd.put("否","N");	
		//贷款用途 PURPOSE 
		purpose.put("保值耐用","STO");
		purpose.put("贬值耐用","FLI");
		purpose.put("旅游","TRA");
		purpose.put("婚庆","MAR");
		purpose.put("教育","EDU");
		purpose.put("装修","DEC");
		purpose.put("其他","OTH");
		//申请贷款期限 APPLY_TNR 
		applyTnr.put("3个月","3");
		applyTnr.put("6个月","6");
		applyTnr.put("9个月","9");
		applyTnr.put("12个月","12");
		applyTnr.put("18个月","18");
		applyTnr.put("24个月","24");
		applyTnr.put("36个月","36");
		applyTnr.put("60个月","60");
		//还款方式 LOAN_MTD
		loanMtd.put("等额本息","1");
		loanMtd.put("按月付息，到期还清","2");	
		//放款通知书、还款计划表寄送地址 MAIL_ADDR 
		mailAddr.put("居住地址","00");
		mailAddr.put("单位地址","01");	
		mailAddr.put("其他地址","09");		
		//是否开通免费短信通知服务 SMS_NOTICE 		
		smsNotice.put("是","Y");
		smsNotice.put("否","N");	
		//与联系人关系 INDIV_RELATION 
		indivRelation.put("同事","06");
		indivRelation.put("合伙人","07");
		indivRelation.put("其他关系","08");
		indivRelation.put("配偶","01");
		indivRelation.put("父母","02");
		indivRelation.put("子女","03");
		indivRelation.put("其他血亲","04");
		indivRelation.put("其他姻亲","05");
		indivRelation.put("同学","10");
		indivRelation.put("朋友","11");
		indivRelation.put("兄弟姐妹","09");
		//与银行往来关系 BANK_REL 
		bankRel.put("贵宾卡客户","01");
		bankRel.put("个人房贷户","02");
		bankRel.put("个人车贷户","03");
		bankRel.put("信用卡客户","04");
		bankRel.put("代发工资户","05");
		bankRel.put("对公贷款户","06");
		bankRel.put("对公存款户","07");
		bankRel.put("推荐客户","08");
		bankRel.put("存款客户","09");
		bankRel.put("行内员工","10");
		//推广方式 SPREAD_TYP 
		spreadTyp.put("亲访单位","10");
		spreadTyp.put("未亲访单位","11");
		spreadTyp.put("亲核原件","20");
		spreadTyp.put("未亲核原件","21");
		spreadTyp.put("亲见签名","30");
		spreadTyp.put("未亲见签名","31");
		//进件来源 APPL_SRC 
		applSrc.put("陌生拜访","10");
		applSrc.put("他人介绍","20");
		applSrc.put("设点营销","30");
		applSrc.put("电话营销","40");
		applSrc.put("自进件","50");
		applSrc.put("他人代交表","60");
		applSrc.put("其他","90");
		//资产性质 ASSET_KIND 
		assetKind.put("资产","ASSET");
		assetKind.put("负债","DEBT");
		//资产类型 ASSET_TYP
		assetTyp.put("房产","PPTY");
		assetTyp.put("汽车","VEC");
		assetTyp.put("其他","OTH");
	}
	
}
