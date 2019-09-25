package com.rongdu.loans.koudai.op.pay;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/6/26.
 */
@Data
public class KDPayCountOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6217468386813584730L;

	private String applyStart; // 申请开始时间
	private String applyEnd; // 申请结束时间
	private String status; // 订单状态
	private String checkStart; // 审核开始时间
	private String channelId;
	private Integer stage = 1; // 前端页面： 1待审核 , 2已过审， 3已否决 ，4办理中 5,待复审

	private Integer pageNo = 1;
	private Integer pageSize = 10;

}
