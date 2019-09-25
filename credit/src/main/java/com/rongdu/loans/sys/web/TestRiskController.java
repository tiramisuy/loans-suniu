package com.rongdu.loans.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.cust.vo.UserInfoVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.executor.R10000001Executor;
import com.rongdu.loans.risk.executor.R10030028Executor;
import com.rongdu.loans.risk.executor.R10030031Executor;
import com.rongdu.loans.risk.executor.R10030035Executor;
import com.rongdu.loans.risk.executor.R10030054Executor;
import com.rongdu.loans.risk.executor.R10030056Executor;
import com.rongdu.loans.risk.executor.R10040001Executor;
import com.rongdu.loans.risk.executor.R10040004Executor;
import com.rongdu.loans.risk.executor.R1005Executor;
import com.rongdu.loans.risk.executor.R1006Executor;
import com.rongdu.loans.risk.executor.R1007Executor;
import com.rongdu.loans.risk.executor.R1008Executor;
import com.rongdu.loans.risk.executor.R1013Executor;
import com.rongdu.loans.risk.executor.R10150001Executor;
import com.rongdu.loans.risk.executor.R10170001Executor;
import com.rongdu.loans.risk.executor.R10170002Executor;
import com.rongdu.loans.risk.executor.R10170004Executor;
import com.rongdu.loans.risk.executor.R10170009Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030002Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030044Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030052Executor;
import com.rongdu.loans.risk.executor.xjbk.R10030059Executor;
import com.rongdu.loans.risk.service.AutoApproveService;
import com.rongdu.loans.risk.service.CreditDataInvokeService;

/**
 * 测试风控Controller
 * 
 * @author liuzhuang
 * @version 2017-10-18
 */
@Controller
public class TestRiskController extends BaseController {
	@Autowired
	private CreditDataInvokeService creditDataInvokeService;
	@Autowired
	private AutoApproveService autoApproveService;

	@RequestMapping(value = "credit/anon/test")
	@ResponseBody
	public Object test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// execRisk();
		// 白骑士
		// execBaoQiShi();
		// 宜信致诚
		// execZhiCheng();
		// 腾讯云
		// execTencent();
		// 百融
		// execCredit100();
		// 同盾
		// execTongDun();

		// execZhima();
		// execKD();
		// execOverdue();
		// exec1003();

		// execXinyanBlack();
		// execXinyanTotaldebt();
		// execXinyanOverdue();

		// execXJBK();
		// execLinkface();
		// test();
		return "success";
	}

	private void test() {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180925200648083072");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10170009Executor());

		CustUserVO userVO = context.getUser();
		userVO.setRealName("陆曙");
		userVO.setIdNo("522627199205170415");

		// userVO.setIdNo("522627199205133333");
		// userVO.setRealName("张三");

		chains.doExecute(context);

	}

	private void exec(Executor executor) {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180705161034972242");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(executor);
		chains.doExecute(context);
	}

	private void execXinyanBlack() {
		// AutoApproveContext context = getAutoApproveContext("1", "1", "杨会会",
		// "370830198811116536", "13986066955");
		AutoApproveContext context = getAutoApproveContext(IdGen.uuid(), "1", "张元泽", "34240119850612652X",
				"13986066955");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10170001Executor());
		chains.doExecute(context);
	}

	public void execXinyanTotaldebt() {
		Executor executor = new R10170002Executor();
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180613184203550480");

		CustUserVO userVO = context.getUser();
		userVO.setRealName("方志刚");
		userVO.setIdNo("142702198911125235");

		// userVO.setRealName("李四");
		// userVO.setIdNo("142702180001062719");

		executor.execute(context);
	}

	public void execXinyanOverdue() {
		Executor executor = new R10170004Executor();
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180613184203550480");

		CustUserVO userVO = context.getUser();
		userVO.setRealName("陆曙");
		userVO.setIdNo("522627199205170415");

		executor.execute(context);
	}

	private void exec1003() {
		AutoApproveContext context = getAutoApproveContext("1", "1", "刘壮", "420114198604291231", "13986066955");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10030056Executor());
		chains.doExecute(context);
	}

	private void execOverdue() {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180607220015010630");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10030028Executor());
		chains.doExecute(context);
	}

	private void execKD() {
		AutoApproveContext context = getAutoApproveContext("1", "1", "牟雪", "350103198705251514", "18895602356");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10150001Executor());
		chains.doExecute(context);
	}

	private void execZhima() {
		AutoApproveContext context = getAutoApproveContext("1", "1", "黎钻娣", "441900199009095185", "13631790994");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R1007Executor());
		chains.doExecute(context);
	}

	private void execRisk() {
		String applyId = "1020171027112448821201";
		autoApproveService.approveXjd(applyId);
	}

	private void execBaoQiShi() {
		// 642221199112190211-万年利-15595402226
		// 642226199008292017-张晓东-15595109295
		// 654222199601011333-贾永志-15900204215
		// AutoApproveContext context = getAutoApproveContext("100", "100",
		// "张晓东",
		// "642226199008292017", "15595109295");

		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020171116144242797845");
		// context.getApplyInfo().setSource("2");// 1=ios,2=android
		// context.getApplyInfo().setExtInfo("{\"bqsTokenKey\":\"D3143028-7633-4AC6-AAFC-217D14C3A9A8\"}");
		ExecutorChain chains = new ExecutorChain();
		// 查询设备信息，需要设备指纹tokenKey，检测Android设备安装是否有可疑的应用程序
		// chains.addExecutor(new R10030030Executor());
		// 查询通讯录，需要设备指纹tokenKey，设备通讯录手机号码较少
		// chains.addExecutor(new R10030004Executor());

		// 白骑士资信云报告，需要用户授权
		// chains.addExecutor(new R10030033Executor());

		// 自有黑名单
		// chains.addExecutor(new R10020001Executor());
		// 白骑士芝麻信用分
		// chains.addExecutor(new R10040002Executor());
		// 白骑士芝麻信用行业关注名单
		// chains.addExecutor(new R1007Executor());
		// 白骑士反欺诈
		// chains.addExecutor(new R10030034Executor());
		// chains.addExecutor(new R10040003Executor());
		// chains.addExecutor(new R1012Executor());

		// chains.addExecutor(new R10030043Executor());
		// chains.addExecutor(new R10030044Executor());
		// chains.addExecutor(new R10030045Executor());
		// chains.addExecutor(new R10030046Executor());
		// chains.addExecutor(new R10030047Executor());
		// chains.addExecutor(new R10030048Executor());
		// chains.addExecutor(new R10030049Executor());

		// chains.addExecutor(new R10030006Executor());
		// chains.addExecutor(new R10030007Executor());
		// chains.addExecutor(new R10030011Executor());
		// chains.addExecutor(new R10030013Executor());
		// chains.addExecutor(new R10030014Executor());
		// chains.addExecutor(new R10030036Executor());
		// chains.addExecutor(new R10030043Executor());
		// chains.addExecutor(new R10030044Executor());

		// chains.addExecutor(new R10030050Executor());
		// chains.addExecutor(new R10030051Executor());
		chains.addExecutor(new R10030054Executor());
		chains.doExecute(context);
	}

	private void execZhiCheng() {
		AutoApproveContext context = getAutoApproveContext("1", "1", "朱培培", "320305198905040963", "13986066955");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10030031Executor());
		chains.addExecutor(new R10040001Executor());
		chains.addExecutor(new R1005Executor());
		chains.addExecutor(new R1006Executor());
		chains.doExecute(context);
	}

	private void execTencent() {
		AutoApproveContext context = getAutoApproveContext("2", "2", "杨二红", "1234567890", "0086-13246208548");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R1008Executor());
		chains.doExecute(context);
	}

	private void execCredit100() {
		AutoApproveContext context = getAutoApproveContext("3", "3", "张飞", "110106199001010000", "13520000000");
		ExecutorChain chains = new ExecutorChain();
		// chains.addExecutor(new R1009Executor());
		// chains.addExecutor(new R1010Executor());已下綫
		// chains.addExecutor(new R1011Executor());
		chains.doExecute(context);
	}

	private void execTongDun() {
		AutoApproveContext context = getAutoApproveContext("4", "4", "张飞", "110106199001010000", "13520000000");
		context.getApplyInfo().setSource("2");// 1=ios,2=android
		context.getApplyInfo().setIp("119.96.134.45:10086");
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new R10030035Executor());
		chains.addExecutor(new R10040004Executor());
		chains.addExecutor(new R1013Executor());
		chains.addExecutor(new R10000001Executor());
		chains.doExecute(context);
	}

	private AutoApproveContext getAutoApproveContext(String applyId, String userId, String userName, String idNo,
			String mobile) {
		AutoApproveContext context = new AutoApproveContext(applyId);
		// 贷款申请信息
		LoanApplySimpleVO applyInfo = null;
		// 用户基本信息
		CustUserVO user = null;
		// 用户扩展信息
		UserInfoVO userInfo = null;
		if (context.getApplyInfo() == null) {
			applyInfo = new LoanApplySimpleVO();
			applyInfo.setApplyId(applyId);
			applyInfo.setSource("1");
			context.setApplyInfo(applyInfo);
			if (applyInfo != null) {
				context.setUserId(userId);
				context.setUserName(userName);
			}
		}

		if (user == null && StringUtils.isNotBlank(context.getUserId())) {
			user = new CustUserVO();
			user.setIdNo(idNo);
			user.setMobile(mobile);
			user.setRealName(userName);
			context.setUser(user);
		}

		if (userInfo == null && StringUtils.isNotBlank(context.getUserId())) {
			userInfo = new UserInfoVO();
			userInfo.setContactList(getContactList());
			// 移除空的联系人
			if (userInfo != null && userInfo.getContactList() != null) {
				List<CustContactVO> contactList = userInfo.getContactList();
				for (int i = contactList.size() - 1; i >= 0; i--) {
					CustContactVO item = contactList.get(i);
					if (StringUtils.isBlank(item.getMobile()) || StringUtils.contains(item.getMobile(), "null")) {
						contactList.remove(item);
					}
				}
			}
			context.setUserInfo(userInfo);
		}
		return context;
	}

	private List<CustContactVO> getContactList() {
		List<CustContactVO> contactList = new ArrayList<CustContactVO>();
		CustContactVO v = new CustContactVO();
		v.setName("张传波");
		v.setMobile("15926324431");
		contactList.add(v);

		CustContactVO v1 = new CustContactVO();
		v1.setName("曹丽萍");
		v1.setMobile("18971564431");
		contactList.add(v1);

		CustContactVO v2 = new CustContactVO();
		v2.setName("陈诚");
		v2.setMobile("13659888291");
		contactList.add(v2);
		return contactList;
	}

	/**
	 * 
	 * @Title: execXJBK
	 * @Description: 现金白卡测试
	 * @return void 返回类型
	 * @throws
	 */
	public void execXJBK() {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180613184203550480");
		context.setUserId("180604000039");
		CustUserVO userVO = context.getUser();
		userVO.setRealName("李萌");
		userVO.setId("180604000039");
		userVO.setIdNo("420281198703230018");

		Executor executor = null;

		executor = new R10030002Executor();
		executor.execute(context);

		executor = new R10030044Executor();
		executor.execute(context);

		executor = new R10030052Executor();
		executor.execute(context);

	}

	/**
	 * 
	 * @Title: 商汤接口测试
	 * @Description: 现金白卡测试
	 * @return void 返回类型
	 * @throws
	 */
	public void execLinkface() {
		AutoApproveContext context = creditDataInvokeService.createAutoApproveContext("1020180613184203550480");
		CustUserVO userVO = context.getUser();
		userVO.setRealName("蒋丹丹");
		userVO.setId("6657e4b0803e4302a22b35a3ee66b690");
		userVO.setIdNo("342626199206100187");

		Executor executor = null;

		executor = new R10030059Executor();
		executor.execute(context);

	}

}
