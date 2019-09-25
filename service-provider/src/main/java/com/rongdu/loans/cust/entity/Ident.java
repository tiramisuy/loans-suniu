package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class Ident extends BaseEntity<Ident> {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1240867141246490988L;
    
	/**
	 * 证件有效期-是长期
	 */
	public static final String ID_TERM_LONG_YES = "1";
	
	/**
	 * 证件有效期-不是长期
	 */
	public static final String ID_TERM_LONG_NO = "0";
	
    private String userId;		// 客户编号
    private Integer idType; // 证件类型
    private String idNo;		// 证件号码
    private String idCtry;		// 证件发证国家
    private String idTermBegin;		// 证件有效期(开始)
    private String idTermEnd;		// 证件有效期(结束)
    private String idTermLong;		// 证件是否长期有效
    private String idRegOrg;		// 证件登记机关

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdCtry() {
        return idCtry;
    }

    public void setIdCtry(String idCtry) {
        this.idCtry = idCtry;
    }

    public String getIdTermBegin() {
        return idTermBegin;
    }

    public void setIdTermBegin(String idTermBegin) {
        this.idTermBegin = idTermBegin;
    }

    public String getIdTermEnd() {
        return idTermEnd;
    }

    public void setIdTermEnd(String idTermEnd) {
        this.idTermEnd = idTermEnd;
    }

    public String getIdTermLong() {
        return idTermLong;
    }

    public void setIdTermLong(String idTermLong) {
        this.idTermLong = idTermLong;
    }

    public String getIdRegOrg() {
        return idRegOrg;
    }

    public void setIdRegOrg(String idRegOrg) {
        this.idRegOrg = idRegOrg;
    }
}
