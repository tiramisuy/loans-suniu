package com.rongdu.loans.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.loans.koudai.service.KDPayService;
import com.rongdu.loans.scheduler.service.SchedulerService;

public class KDPayTest extends SpringTransactionalContextTests {

	@Autowired
	private KDPayService kdPayService;
	@Autowired
	private SchedulerService schedulerService;

	// @Rollback(false)
	// @Test
	// public void payTest() throws Exception {
	// KDPayVO vo = kdPayService.pay("1020180705161034972242");
	// }

	// @Rollback(false)
	// @Test
	// public void processPayingTask() throws Exception {
	// TaskResult result = schedulerService.processKdPaying();
	// System.out.println(JsonMapper.toJsonString(result));
	// }
}
