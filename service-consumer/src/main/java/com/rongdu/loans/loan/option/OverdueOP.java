package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class OverdueOP implements Serializable{

    private static final long serialVersionUID = -6219847033510500874L;
    /**
     * 待还款ID
     */
    private String id;
    /**
     *合同编号
     */
    private String contNo;
    /**
     *客户名称
     */
    private String userName;
    /**
     * 客户ID
     */
    private String userId;
    /**
     * 证件号码
     */
    private String idNo;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 催收员
     */
    private String operatorId;
    /**
     * 催收结果
     */
    private String result;
    /**
     * 催收内容
     */
    private String content;
    /**
     *是否已经结清（0-否，1-是）
     */
    private Integer status;
    /**
     * 借款时间
     */
    private Date borrowTimeStart;
    private Date borrowTimeEnd;
    /**
     * 应该还款时间
     */
    private Date expectTimeStart;
    private Date expectTimeEnd;
    /**
     * 实际还款时间
     */
    private Date actualTimeStart;
    private Date actualTimeEnd;
    /**
     * 复选逾期时间段
     */
    private List<OverdueTime> overdueList;
    
    private String companyId;  //商户Id

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    
    private String productId; //产品id
    
    /**
   	 *渠道 
   	 */
   	private String channelId;
    
    private String termType;	//申请期限类型 1:14天；2:90天；3：184天；4：其他

    /**
     * 是否为复贷
     */
    private String loanSucess;
    
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

    public class OverdueTime implements Serializable{
        /**
         * 逾期时间
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBorrowTimeStart() {
        return borrowTimeStart;
    }

    public void setBorrowTimeStart(Date borrowTimeStart) {
        this.borrowTimeStart = borrowTimeStart;
    }

    public Date getBorrowTimeEnd() {
        return borrowTimeEnd;
    }

    public void setBorrowTimeEnd(Date borrowTimeEnd) {
        this.borrowTimeEnd = borrowTimeEnd;
    }

    public Date getExpectTimeStart() {
        return expectTimeStart;
    }

    public void setExpectTimeStart(Date expectTimeStart) {
        this.expectTimeStart = expectTimeStart;
    }

    public Date getExpectTimeEnd() {
        return expectTimeEnd;
    }

    public void setExpectTimeEnd(Date expectTimeEnd) {
        this.expectTimeEnd = expectTimeEnd;
    }

    public Date getActualTimeStart() {
        return actualTimeStart;
    }

    public void setActualTimeStart(Date actualTimeStart) {
        this.actualTimeStart = actualTimeStart;
    }

    public Date getActualTimeEnd() {
        return actualTimeEnd;
    }

    public void setActualTimeEnd(Date actualTimeEnd) {
        this.actualTimeEnd = actualTimeEnd;
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

    public List<OverdueTime> getOverdueList() {
        return overdueList;
    }

    public void setOverdueList(List<OverdueTime> overdueList) {
        this.overdueList = overdueList;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

    public String getLoanSucess() {
        return loanSucess;
    }

    public void setLoanSucess(String loanSucess) {
        this.loanSucess = loanSucess;
    }
}
