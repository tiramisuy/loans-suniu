package com.rongdu.loans.loan.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/9/29.
 */
public class ApplyAssignmentVO implements Serializable {

    /**
     * 拼装的id
     */
    @NotBlank(message = "参数不能为空")
    private String ids;
    /**
     * 商户id
     */
    @NotBlank(message = "参数不能为空")
    private String companyId;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    
}
