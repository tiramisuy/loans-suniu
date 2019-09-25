package com.rongdu.loans.loan.op;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class ApplyOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386813584730L;

	private String userName; // 真实姓名
	private String idNo; // 用户证件号码
	private String mobile; // 手机号码
	private String applyStart; // 申请开始时间
	private String applyEnd; // 申请结束时间
	private String checkStart; // 审核开始时间
	private String checkEnd; // 审核结束时间
	private String autoCheck; // 是否自动审核
	private String status; // 订单状态
	private String channel; // 渠道码
	private String auditor; // 审核人
	private List<String> auditorList; // 审核人列表
	private String companyId; // 商户id
	private String areaId; // 区域id
	private String officeId; // 门店id
	private String office; // 门店名称
	private String areaName; // 区域名称
	private String productId; // 产品ide
	private String loanSucess;// 是否复贷
	private String groupId;// 组Id
	private String typeCheck; // allGroup为查看所有组 allOffice为查看所有门店
	private String allotFlag;// 催收任务分配开关 0不可分配 1可分配
	private String termType; // 申请期限类型 1:14天；2:90天；3：184天；4：其他
	private String source; //进件来源（1-ios,2-android,3-h5,4-api）
	private String payChannel; //放款渠道
	private String isCall; //是否拨打
	private List<Integer> callCount;
    private String tripartiteNo;//第三方订单号//拨打次数枚举
	private List<String> channelList; // 融360 和 融聚花花 专用字段
	// 宜信决策分
	private String compositeScore;
	// 是否通过二次机审 0否 1是
	private String userCreditLine;
	public enum CallCountEnum{
		callCount_0(0,1,1,"1"),
		callCount_1(1,2,2,"2"),
		callCount_2(2,3,3,"3"),
		callCount_3(3,4,6,"4~6"),
		callCount_4(4,7,9,"7~9"),
		callCount_5(6,10,Integer.MAX_VALUE,"10以上");

		private Integer value;
		private Integer min;
		private Integer max;
		private String desc;
		
		CallCountEnum(Integer value,Integer min, Integer max, String desc) {
			this.value = value;
			this.min = min;
			this.max = max;
			this.desc = desc;
		}

		public static CallCountEnum get(Integer id) {
			for (CallCountEnum callCountEnum : CallCountEnum.values()) {
				if (callCountEnum.getValue().equals(id)) {
					return callCountEnum;
				}
			}
			return null;
		}

		public Integer getValue() {
			return value;
		}
		public Integer getMin() {
			return min;
		}
		public Integer getMax() {
			return max;
		}

		public String getDesc() {
			return desc;
		}

	}
	public String getLoanSucess() {
		return loanSucess;
	}

	public void setLoanSucess(String loanSucess) {
		this.loanSucess = loanSucess;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	private Integer stage = 1; // 前端页面： 1待审核 , 2已过审， 3已否决 ，4办理中 5,待复审

	private Integer pageNo = 1;
	private Integer pageSize = 10;

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApplyStart() {
		return applyStart;
	}

	public void setApplyStart(String applyStart) {
		this.applyStart = applyStart;
	}

	public String getApplyEnd() {
		return applyEnd;
	}

	public void setApplyEnd(String applyEnd) {
		this.applyEnd = applyEnd;
	}

	public String getCheckStart() {
		return checkStart;
	}

	public void setCheckStart(String checkStart) {
		this.checkStart = checkStart;
	}

	public String getCheckEnd() {
		return checkEnd;
	}

	public void setCheckEnd(String checkEnd) {
		this.checkEnd = checkEnd;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getAutoCheck() {
		return autoCheck;
	}

	public void setAutoCheck(String autoCheck) {
		this.autoCheck = autoCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTypeCheck() {
		return typeCheck;
	}

	public void setTypeCheck(String typeCheck) {
		this.typeCheck = typeCheck;
	}

	public String getAllotFlag() {
		return allotFlag;
	}

	public void setAllotFlag(String allotFlag) {
		this.allotFlag = allotFlag;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public List<String> getAuditorList() {
		return auditorList;
	}

	public void setAuditorList(List<String> auditorList) {
		this.auditorList = auditorList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getTripartiteNo() {
		return tripartiteNo;
	}

	public void setTripartiteNo(String tripartiteNo) {
		this.tripartiteNo = tripartiteNo;
	}
	public String getIsCall() {
		return isCall;
	}

	public void setIsCall(String isCall) {
		this.isCall = isCall;
	}

	public List<Integer> getCallCount() {
		return callCount;
	}

	public void setCallCount(List<Integer> callCount) {
		this.callCount = callCount;
	}

	public List<String> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<String> channelList) {
		this.channelList = channelList;
	}

	public String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(String compositeScore) {
		this.compositeScore = compositeScore;
	}

	public String getUserCreditLine() {
		return userCreditLine;
	}

	public void setUserCreditLine(String userCreditLine) {
		this.userCreditLine = userCreditLine;
	}
}
