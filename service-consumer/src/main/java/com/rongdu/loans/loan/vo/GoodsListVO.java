/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rongdu.common.persistence.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表Entity
 * @author Lee
 * @version 2018-08-28
 */
@Data
public class GoodsListVO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 *商品名称
	 */
	private String goodsName;
	/**
	 *商品单价
	 */
	private BigDecimal goodsPrice;
	/**
	 *市场价
	 */
	private BigDecimal marketPrice;
	/**
	 *商品图片url
	 */
	private String goodsPic;
	/**
	 *简称
	 */
	private String simpleName;
	/**
	 *销量
	 */
	private String salesVolume;
	/**
	 *展示图片
	 */
	private String picBanner;
	/**
	 *详情
	 */
	private String picDetail;
	private BigDecimal maxCoupon;

	private String status; /**商品上下架状态  0:下架 1：上架*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;

	private String createBy;
	private String updateBy;
	private int del = BaseEntity.DEL_NORMAL;

}