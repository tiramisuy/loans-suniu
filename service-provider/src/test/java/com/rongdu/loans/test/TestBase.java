package com.rongdu.loans.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.common.rong360.HttpClientUtils;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.loan.entity.LoanApply;
import com.rongdu.loans.loan.entity.LoanRepayPlan;
import com.rongdu.loans.loan.entity.RepayPlanItem;
import com.rongdu.loans.loan.entity.User;
import com.rongdu.loans.loan.manager.LoanApplyManager;
import com.rongdu.loans.loan.manager.LoanRepayPlanManager;
import com.rongdu.loans.loan.manager.RepayPlanItemManager;
import com.rongdu.loans.loan.manager.UserManager;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.option.dwd.ChargeInfo;
import com.rongdu.loans.loan.service.JDQStatusFeedBackService;
import com.rongdu.loans.loan.service.RepayPlanItemService;
import com.rongdu.loans.loan.service.RongPointCutService;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.risk.vo.AutoApproveVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br>
 * 〈基础测试类〉
 *
 * @author yuanxianchu
 * @create 2019/5/23
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/spring-**.xml")
@ActiveProfiles(value = "test")
@Slf4j
public class TestBase {

	@Autowired
	private LoanApplyManager loanApplyManager;
	@Autowired
	private ThreadPoolTaskExecutor myexecutor;
	/*@Autowired
    private ThreadPoolTaskExecutor */
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private ShortMsgService shortMsgService;
	@Autowired
	private RongPointCutService rongPointCutService;
	@Autowired
	private JDQStatusFeedBackService jdqStatusFeedBackService;
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	@Autowired
	private RepayPlanItemManager repayPlanItemManager;
	@Autowired
	private LoanRepayPlanManager loanRepayPlanManager;
	@Autowired
	private UserManager userManager;


	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Test
	public void test() {
		/*System.out.println(executorService.);
        System.out.println("poolSize:"+executorService.getPoolSize());
        System.out.println("maxPoolSize:"+executorService.getMaxPoolSize());
        System.out.println("corePoolSize:"+executorService.getCorePoolSize());*/

		/*final AtomicInteger successNum = new AtomicInteger();
		final AtomicInteger failNum = new AtomicInteger();

		long startTime = System.currentTimeMillis();
		try {
			List<String> list = new ArrayList<>();
			for (int i = 0; i < 10000; i++) {
				list.add("number"+i);
			}
			final Queue<String> queue = new ConcurrentLinkedDeque<>(list);

			final CountDownLatch countDownLatch = new CountDownLatch(list.size());
			for (int i = 0; i < list.size() ; i++) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						String poll = queue.poll();
						log.debug(Thread.currentThread().getName() + ":" + poll);
						countDownLatch.countDown();
						successNum.incrementAndGet();
					}
				});
			}
			countDownLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.debug("耗时：{}",endTime-startTime);
		System.out.println("成功："+successNum.intValue());*/
		/*SendShortMsgOP sendShortMsgOP = new SendShortMsgOP();
		sendShortMsgOP.setMsgType(3);// 还款提醒
		sendShortMsgOP.setUserName("袁富贵");
		sendShortMsgOP.setMobile("13129940961");
		sendShortMsgOP.setSource(String.valueOf(Global.SOURCE_WEB));
		sendShortMsgOP.setUserId("");
		sendShortMsgOP.setChannelId("DWDAPI");
		sendShortMsgOP.setProductId("JN");
		sendShortMsgOP.setTerm("");
		sendShortMsgOP.setAmount(new BigDecimal(720));
		shortMsgService.sendShortMsg(sendShortMsgOP);
		List<String> lis = new ArrayList<>();*/
		/*String url = "http://www.baidu.com/";
		String s = HttpClientUtils.postForJson(url, null, null);
		System.out.println(s);*/

	}
	@Test
	public void test1() {
		/*List<String> list = new ArrayList<>();
		list.add("123123");
		rongPointCutService.overduePoint(list);
		System.out.println("123123123123123");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		//jdqStatusFeedBackService.orderStatusFeedBack("1020190513101159649527");
		String applyId = "1020190425103634429161";
		List<Map<String, Object>> repayDetailByContNo = repayPlanItemManager.getRepayDetailByContNo(applyId);
		LoanRepayPlan loanRepayPlan = new LoanRepayPlan();
		LoanRepayPlan byApplyId = loanRepayPlanManager.getByApplyId(applyId);
		byApplyId.setUpdateBy("yxc");
		loanRepayPlanManager.update(byApplyId);
		List<Map<String, Object>> repayDetailByContNo2 = repayPlanItemManager.getRepayDetailByContNo(applyId);

	}

}
