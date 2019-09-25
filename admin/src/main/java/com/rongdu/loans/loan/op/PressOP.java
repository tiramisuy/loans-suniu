package com.rongdu.loans.loan.op;


import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
public class PressOP implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6217468386813584730L;

    private String id;          //待还款ID
    private String userName;        // 真实姓名
    private String idNo;        // 用户证件号码
    private String mobile;        // 手机号码
    private Integer status;     //订单状态  0待还款，1已还款
    private List<Integer> overdue;      //逾期天数枚举

    private String type;//
    private String operatorId;//催收人员
    private Integer result;//催收结果
    private String content;//催收内容


    private String borrowStart;		// 借款时间
    private String borrowEnd;
    private String expectStart;		// 应该还款时间
    private String expectEnd;
    private String actualStart;		// 实际还款时间
    private String actualEnd;


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

    public enum OverdueEnum{
    	Overdue_0(0,1,1,"1"),
        Overdue_1(1,2,7,"2~7"),
        Overdue_2(2,8,15,"8~15"),
        Overdue_3(3,16,30,"16~30"),
        Overdue_4(4,31,60,"31~60"),
        Overdue_5(5,61,90,"61~90"),
        Overdue_6(6,91,Integer.MAX_VALUE,"90以上");

        private Integer value;
        private Integer min;
        private Integer max;
        private String desc;

        OverdueEnum(Integer value,Integer min, Integer max, String desc) {
            this.value = value;
            this.min = min;
            this.max = max;
            this.desc = desc;
        }

        public static OverdueEnum get(Integer id) {
            for (OverdueEnum overdueEnum : OverdueEnum.values()) {
                if (overdueEnum.getValue().equals(id)) {
                    return overdueEnum;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Integer> getOverdue() {
        return overdue;
    }

    public void setOverdue(List<Integer> overdue) {
        this.overdue = overdue;
    }

    public String getBorrowStart() {
        return borrowStart;
    }

    public void setBorrowStart(String borrowStart) {
        this.borrowStart = borrowStart;
    }

    public String getBorrowEnd() {
        return borrowEnd;
    }

    public void setBorrowEnd(String borrowEnd) {
        this.borrowEnd = borrowEnd;
    }

    public String getExpectStart() {
        return expectStart;
    }

    public void setExpectStart(String expectStart) {
        this.expectStart = expectStart;
    }

    public String getExpectEnd() {
        return expectEnd;
    }

    public void setExpectEnd(String expectEnd) {
        this.expectEnd = expectEnd;
    }

    public String getActualStart() {
        return actualStart;
    }

    public void setActualStart(String actualStart) {
        this.actualStart = actualStart;
    }

    public String getActualEnd() {
        return actualEnd;
    }

    public void setActualEnd(String actualEnd) {
        this.actualEnd = actualEnd;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
