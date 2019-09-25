package com.rongdu.loans.loan.option;

import java.io.Serializable;

public class PromotionOP implements Serializable{

	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private String productId;
	    
	    /**
	     * 渠道码
	     */
	    private String channelId;
	    
	    private Integer pageNo = 1;
	    private Integer pageSize = 10;
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getChannelId() {
			return channelId;
		}
		public void setChannelId(String channelId) {
			this.channelId = channelId;
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
	    
}
