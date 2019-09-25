package com.rongdu.common.file;

/**
 * 文件业务类型
 * @author sunda
 * @version 2017-06-27
 */
public enum FileBizCode {
	
	FRONT_IDCARD("front_idcard","身份证正面","apply_loan","贷款申请材料"),
	BACK_IDCARD("back_idcard","身份证反面","apply_loan","贷款申请材料"),
	FACE_VERIFY("face_verify","人脸识别","apply_loan","贷款申请材料"),
	//Mobile Network Operator 移动网络运营商
	MNO_CONTACT("mno_contact","运营商通讯信息","contact","通讯信息"),
	DEVICE_CONTACT("device_contact","设备通讯信息","contact","通讯信息"),
	ANDROID_DEVICE("android_device","Android设备信息","mobile_device","移动设备信息"),
	IOS_DEVICE("ios_device","iOS设备信息","mobile_device","移动设备信息"),
	BAIQISHI_REPORT("baiqishi_report","白骑士征信报告","credit_report","征信报告"),
	ZHIMA_PUSH("zhima_push","芝麻信用数据上传","zhima_push","数据上传"),
	ENTERPRISE_LICENSE("enterprise_license","企业营业执照上传","apply_loan","数据上传"),
	CONTACT_FILE("contact_file","通讯录附件","cust_contact_file","客户通讯录文件"),
	XINYAN_BLACK("xinyan_black","新颜负面拉黑","xinyan_black","新颜负面拉黑"),
	MOXIE_EMAIL_REPORT("moxie_email_report","魔蝎信用卡邮箱报告","moxie_email_report","魔蝎信用卡邮箱报告"),
	MOXIE_BANK_REPORT("moxie_bank_report","魔蝎网银报告","moxie_bank_report","魔蝎网银报告"),

	XJBK_BASE_DATA("xjbk_base_data","现金白卡基础数据","xjbk_base_data","现金白卡基础数据"),
	XJBK_ADDITIONAL_DATA("xjbk_addition_data","现金白卡补充数据","xjbk_addition_data","现金白卡补充数据"),
	
	RONG_BASE_DATA("rong_base_data","融360基础数据","rong_base_data","融360基础数据"),
	RONG_ADDITIONAL_DATA("rong_addition_data","融360补充数据","rong_addition_data","融360补充数据"),
	RONGTJ_REPORT_DETAIL("rongtj_report_detail","融天机互联网报告详情","rongtj_report_detail","融天机互联网报告详情"),

	DWD_BASE_DATA("dwd_base_data","大王贷基础数据","dwd_base_data","大王贷基础数据"),
	DWD_ADDITIONAL_DATA("dwd_addition_data","大王贷补充数据","dwd_addition_data","大王贷补充数据"),

	JDQ_BASE_DATA("jdq_base_data","借点钱基础数据","jdq_base_data","借点钱基础数据"),
	JDQ_BASE_DATA_ADD("jdq_base_data_add","借点钱补充数据","jdq_base_data_add","借点钱补充数据"),
	JDQ_REPORT_DATA("jdq_report_data","借点钱报告数据","jdq_report_data","借点钱报告数据"),
	DWD_REPORT_DATA("dwd_report_data","大王贷报告数据","dwd_report_data","大王贷报告数据"),
	DWD_CHARGE_DATA("dwd_charge_data","大王贷运营商数据","dwd_charge_data","大王贷运营商数据"),

	SLL_BASE_DATA("sll_base_data","SLL基础数据","sll_base_data","SLL基础数据"),
	SLL_REPORT_DATA("sll_report_data","SLL报告数据","sll_report_data","SLL报告数据"),
	SLL_ADDITIONAL_DATA("sll_addition_data","SLL补充数据","sll_addition_data","SLL补充数据");

	private String bizCode = null;
	private String bizName = null;
	private String parentBizCode = null;
	private String parentBizName = null;
	
	FileBizCode(){		
	}
	
	private FileBizCode(String bizCode, String bizName, String parentBizCode,String parentBizName) {
		this.bizCode = bizCode;
		this.bizName = bizName;
		this.parentBizCode = parentBizCode;
		this.parentBizName = parentBizName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getParentBizCode() {
		return parentBizCode;
	}

	public void setParentBizCode(String parentBizCode) {
		this.parentBizCode = parentBizCode;
	}

	public String getParentBizName() {
		return parentBizName;
	}

	public void setParentBizName(String parentBizName) {
		this.parentBizName = parentBizName;
	}
	


}
