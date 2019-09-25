package com.rongdu.loans.loan.option.rong360Model;

import java.io.Serializable;

import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;

import lombok.Data;

/**  
* @Title: Rong360CommonOP.java  
* @Package com.rongdu.loans.loan.option.rong360  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年6月29日  
* @version V1.0  
*/
@Data
public class RongCommonOP implements Serializable {

	private static final long serialVersionUID = -8956152034279997476L;
	
	//订单推送
	private OrderBaseInfo orderBaseInfo;//订单基本信息
	private OrderAppendInfo orderAppendInfo;//订单补充信息
	
	//融天机运营商报告信息
	private TianjiReportDetailResp tianjiReportDetail;

}
