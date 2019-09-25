package com.rongdu.loans.basic.service;

import org.springframework.stereotype.Service;

import com.rongdu.loans.basic.vo.NotificationDetailVO;
import com.rongdu.loans.basic.vo.NotificationVO;


/**
 * 公告Service
 * @author likang
 * @version 2017-06-30
 */
@Service
public interface NotificationService {

	/**
	 * 获取公告信息列表
	 * @param productId
	 * @return
	 */
    NotificationDetailVO getNotifications(String productId);
    
	/**
	 * 获取单个公告明细
	 * @param id
	 * @return
	 */
	NotificationVO getNotificationDetail(String id);
	
	/**code y0511
	* @Title: getValidNotificationByProductId  
	* @Description: 根据产品Id获取公告信息 
	* @param @param productId
	* @param @return    参数  
	* @return NotificationDetailVO    返回类型  
	 */
	NotificationDetailVO getValidNotificationByProductId(String productId);
}
