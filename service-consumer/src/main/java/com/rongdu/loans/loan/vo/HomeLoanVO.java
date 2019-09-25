package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

import com.rongdu.common.config.Global;

/**
 * 首页页面类型
 * @author zcb
 * @date 2018年8月30日
 */
public class HomeLoanVO implements Serializable {

	private static final long serialVersionUID = 1766742571891270383L;

	/**
	 * 首页页面类型(1：APP原生态 ，2：H5)
	 */
	private Integer homePageType;
	
	/**
	 * 首页H5链接 homePageType=2时有值
	 */
	private String homePageUrl;
	
	/**
	 *	还款页面类型 (1：APP原生态 ，2：H5)
	 */
	private Integer repayPageType;
	
	/**
	 * 还款页面H5链接
	 */
	private String repayPageUrl;
	
	/**
	 * 个人中心页面类型(1：APP原生态 ，2：H5)
	 */
	private Integer memberPageType; 
	
	/**
	 * 个人中心H5链接
	 */
	private String memberPageUrl; 
	
	/**
	 * 首页产品信息
	 */
	private List<LoanProductVO> productList;
	
	public HomeLoanVO() {
		homePageType = 1;
		repayPageType = 1;
		memberPageType = 1;
		homePageUrl = Global.getConfig("homePage.url");
		repayPageUrl = Global.getConfig("repayPage.url");
		memberPageUrl = Global.getConfig("memberPage.url");
	}

	public Integer getHomePageType() {
		return homePageType;
	}

	public void setHomePageType(Integer homePageType) {
		this.homePageType = homePageType;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public Integer getRepayPageType() {
		return repayPageType;
	}

	public void setRepayPageType(Integer repayPageType) {
		this.repayPageType = repayPageType;
	}

	public String getRepayPageUrl() {
		return repayPageUrl;
	}

	public void setRepayPageUrl(String repayPageUrl) {
		this.repayPageUrl = repayPageUrl;
	}

	public Integer getMemberPageType() {
		return memberPageType;
	}

	public void setMemberPageType(Integer memberPageType) {
		this.memberPageType = memberPageType;
	}

	public String getMemberPageUrl() {
		return memberPageUrl;
	}

	public void setMemberPageUrl(String memberPageUrl) {
		this.memberPageUrl = memberPageUrl;
	}

	public List<LoanProductVO> getProductList() {
		return productList;
	}

	public void setProductList(List<LoanProductVO> productList) {
		this.productList = productList;
	}
}
