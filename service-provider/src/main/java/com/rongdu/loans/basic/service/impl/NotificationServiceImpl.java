package com.rongdu.loans.basic.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.manager.NotificationManager;
import com.rongdu.loans.basic.service.NotificationService;
import com.rongdu.loans.basic.vo.NotificationDetailVO;
import com.rongdu.loans.basic.vo.NotificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告Service实现
 * 
 * @author likang
 * @version 2017-06-30
 */
@Service("notificationService")
public class NotificationServiceImpl extends BaseService implements NotificationService {

	@Autowired
	private NotificationManager notificationManager;

	public NotificationDetailVO getNotifications(String productId) {
		// 初始化返回值
		NotificationDetailVO rz = new NotificationDetailVO();
		// 统计公告有效信息条数
		int count = notificationManager.countValidNotification(productId);
		rz.setValidCount(count);
		// 获取公告列表
		List<NotificationVO> list = notificationManager.getValidNotifications();
		rz.setNotificationList(list);
		return rz;
	}

	@Override
	public NotificationVO getNotificationDetail(String id) {
		// 构造返回对象
		NotificationVO vo = new NotificationVO();
		if (StringUtils.isBlank(id)) {
			logger.error("param is error!");
			return vo;
		}
		return notificationManager.getNotificationDetail(id);
	}

	@Override
	public NotificationDetailVO getValidNotificationByProductId(String productId) {
		// 初始化返回值
		NotificationDetailVO vo = new NotificationDetailVO();
		if (StringUtils.isBlank(productId)) {
			logger.warn("产品Id为空！获取所有产品公告");
		}
		// 统计公告有效信息条数
		int count = notificationManager.countValidNotification(productId);
		vo.setValidCount(count);
		List<NotificationVO> list = notificationManager.getValidNotificationByProductId(productId);
		vo.setNotificationList(list);
		return vo;
	}
}
