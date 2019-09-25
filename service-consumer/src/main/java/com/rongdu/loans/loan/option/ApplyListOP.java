package com.rongdu.loans.loan.option;


import com.rongdu.common.persistence.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class ApplyListOP extends BaseEntity<ApplyListOP> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String userName;		// 真实姓名
    private String idNo;		// 用户证件号码
    private String mobile;		// 手机号码
    private Date applyTimeStart;		// 申请开始时间
    private Date applyTimeEnd;		// 申请结束时间
    private Date checkTimeStart;		// 审核开始时间
    private Date checkTimeEnd;		// 审核结束时间
    private String autoCheck;		//是否自动审核
    private Integer status;		//订单状态
    private String checkStatus;    //审核状态
    private String orderBy;
    private String channel;//新增渠道码
    private String auditor;     //审核人
    private List<String> auditorList; // 审核人列表
    private String productId; //产品id{XJD:现金贷,LYD:零用贷}
    private String companyId; //商户id
    private String loanSucess;//是否复贷
    private String termType;	//申请期限类型 1:14天；2:90天；3：184天；4：其他
    private String source; //进件来源（1-ios,2-android,3-h5,4-api）
    private String payChannel; //放款渠道
    private String tripartiteNo;//第三方订单号
    private String isCall; //拨打次数
    private List<CallCountSize> callCounts;      //拨打次数枚举
    private List<String> channelList; // 融360 和 融聚花花 专用字段
    private String compositeScore;

    public String getLoanSucess() {
		return loanSucess;
	}

	public void setLoanSucess(String loanSucess) {
		this.loanSucess = loanSucess;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
    
    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	private Integer stage = 1;		//前端页面： 1待审核 , 2已过审， 3已否决 ，4办理中
    private List<Integer> statusList;   //贷款单状态
    private List<Integer> checkStatusList;

    private Integer blacklist;  //是否黑名单

    private Integer pageNo = 1;
    private Integer pageSize = 10;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public Date getCheckTimeStart() {
        return checkTimeStart;
    }

    public void setCheckTimeStart(Date checkTimeStart) {
        this.checkTimeStart = checkTimeStart;
    }

    public Date getCheckTimeEnd() {
        return checkTimeEnd;
    }

    public void setCheckTimeEnd(Date checkTimeEnd) {
        this.checkTimeEnd = checkTimeEnd;
    }

    public String getAutoCheck() {
        return autoCheck;
    }

    public void setAutoCheck(String autoCheck) {
        this.autoCheck = autoCheck;
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

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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

	public List<Integer> getCheckStatusList() {
		return checkStatusList;
	}

	public void setCheckStatusList(List<Integer> checkStatusList) {
		this.checkStatusList = checkStatusList;
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

    public List<CallCountSize> getCallCounts() {
        return callCounts;
    }

    public void setCallCounts(List<CallCountSize> callCounts) {
        this.callCounts = callCounts;
    }

    public class CallCountSize implements Serializable{
        /**
         * 未接次数
         */
        private Integer min;
        private Integer max;

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
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
}
