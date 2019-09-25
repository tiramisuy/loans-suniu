package com.rongdu.loans.koudai.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.koudai.api.service.KDCreateApiService;
import com.rongdu.loans.koudai.entity.PayLog;
import com.rongdu.loans.koudai.manager.PayLogManager;
import com.rongdu.loans.koudai.service.KDCreateService;
import com.rongdu.loans.koudai.vo.create.KDCreateResultVO;

@Service("kDCreateService")
public class KDCreateServiceImpl implements KDCreateService {
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private KDCreateApiService kdCreateApiService;
	@Autowired
	private PayLogManager payLogManager;

	@Override
	public TaskResult processCreateOrderTask() {
		logger.info("开始口袋创建订单处理中订单任务。");
		long starTime = System.currentTimeMillis();
		/** 查询所有处理中的数据 */
		Map<String, Object> param = new HashMap();
		param.put("pay_status", 0);
		param.put("pay_succ_time_begin", DateUtils.formatDateTime(DateUtils.addDay(new Date(), -3)));//开始时间
		param.put("pay_succ_time_end", DateUtils.formatDateTime(DateUtils.addMinutes(new Date(), -30)));//结束时间

		List<PayLog> list = payLogManager.findCreatingList(param);
		int success = 0;
		int fail = 0;
		for (PayLog l : list) {
			try {
				Thread.sleep(2000);

				// 创建订单
				KDCreateResultVO vo = kdCreateApiService.createOrder(l.getApplyId());

				l.setKdCreateCode(Integer.parseInt(vo.getCode()));
				if (vo.isSuccess()) {
					l.setKdCreateOrderId(vo.getOrder_id());
				} else {
					l.setKdCreateMsg(vo.getMessage());
				}
				payLogManager.update(l);
				success++;
			} catch (Exception e) {
				fail++;
				logger.error("创建订单处理失败，参数： " + JsonMapper.getInstance().toJson(l), e);
			}
		}

		long endTime = System.currentTimeMillis();
		logger.info("口袋创建订单处理中订单任务结束,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

}
