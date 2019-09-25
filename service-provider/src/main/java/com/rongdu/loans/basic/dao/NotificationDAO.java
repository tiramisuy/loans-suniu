package com.rongdu.loans.basic.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.vo.NotificationVO;
import com.rongdu.loans.cust.entity.Message;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 公告DAO接口
 * 
 * @author likang
 * @version 2017-06-30
 */
@MyBatisDao
public interface NotificationDAO extends BaseDao<Message, String> {

	/**
	 * 根据产品Id统计有效公告条数
	 * 
	 * @param productId
	 * @return
	 */
	int countValidNotification(@Param("productId") String productId);

	/**
	 * 获取有效公告
	 * 
	 * @return
	 */
	List<NotificationVO> getValidNotifications();

	/**
	 * 获取公告明细
	 * 
	 * @param id
	 * @return
	 */
	NotificationVO getNotificationDetail(String id);

	/**
	 * @Title: getValidNotificationByProductId
	 * @Description: 根据产品Id获取公告信息
	 * @param @param
	 *            productId
	 * @param @return
	 *            参数
	 * @return List<NotificationVO> 返回类型
	 */
	List<NotificationVO> getValidNotificationByProductId(@Param("productId") String productId);
}
