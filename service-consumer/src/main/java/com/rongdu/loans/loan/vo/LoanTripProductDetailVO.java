package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rongdu.common.utils.StringUtils;



/**
 * 用户旅游产品VO
 * @author likang
 *
 */
public class LoanTripProductDetailVO implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2943308870425637382L;
	
	private String productId;          // 产品代码
	//private String custId;          // 客户id
	private String userId;  // 客户id
	private String applyId;          // 借款id
	private String cardNo;  //旅游券卡号
	private String overdueTime;          // 过期时间
	private String name;		// 产品名称
	private String description;		// 产品描述	
	private String imgUrl; //图片路径		
	private String remark; //备注
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOverdueTime() {
		
		try {
			if(StringUtils.isNoneBlank(overdueTime)){//格式化日期
				overdueTime = overdueTime.substring(0,10).replace("-","/");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return overdueTime;
	}
	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
		
		
	
}
