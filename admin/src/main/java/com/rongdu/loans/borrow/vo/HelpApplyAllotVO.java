package com.rongdu.loans.borrow.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangxiaolong on 2017/9/29.
 */
public class HelpApplyAllotVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7464346052255834438L;
	/**
     * 拼装的id
     */
    @NotBlank(message = "参数不能为空")
    private String ids;
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
