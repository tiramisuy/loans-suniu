package com.rongdu.loans.basic.manager;

import com.rongdu.loans.basic.dao.NotificationDAO;
import com.rongdu.loans.basic.vo.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告本地服务接口
 * @author likang
 *
 */
@Service("notificationManager")
public class NotificationManager {

	@Autowired
	private NotificationDAO notificationDao;

	/**
	 * 根据产品Id统计有效公告条数
	 * @param productId
	 * @return
	 */
	public int countValidNotification(String productId) {
		return notificationDao.countValidNotification(productId);
	}

	/**
	 * 获取有效公告
	 * @return
	 */
	public List<NotificationVO> getValidNotifications() {
		return notificationDao.getValidNotifications();
	}

	/**
	 * 获取公告明细
	 * @param id
	 * @return
	 */
	public NotificationVO getNotificationDetail(String id) {
		return notificationDao.getNotificationDetail(id);
	}
	
	/**
	* @Title: getValidNotificationByProductId  
	* @Description: 根据产品Id获取公告信息 
	* @param @param productId
	* @param @return    参数  
	* @return List<NotificationVO>    返回类型  
	 */
	public List<NotificationVO> getValidNotificationByProductId(String productId) {
		return notificationDao.getValidNotificationByProductId(productId);
	}
}
