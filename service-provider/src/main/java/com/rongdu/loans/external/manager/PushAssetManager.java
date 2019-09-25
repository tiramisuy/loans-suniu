package com.rongdu.loans.external.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.config.ShortMsgTemplate;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.task.TaskResult;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.option.SendShortMsgOP;
import com.rongdu.loans.basic.service.ShortMsgService;
import com.rongdu.loans.external.vo.PushRespVO;
import com.rongdu.loans.loan.entity.RepayPush;
import com.rongdu.loans.loan.manager.RepayPushManager;
import com.rongdu.loans.loan.vo.OverdueRepayNoticeVO;
import com.rongdu.loans.loan.vo.RepayNoticeVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 向理财端推送资产信息-实体管理接口
 * @author likang
 * @version 2017-08-21
 */
@Service("pushAssetManager")
public class PushAssetManager extends BaseService{

	@Autowired
	private RepayPushManager repayPushManager;

	@Autowired
	private ShortMsgService shortMsgService;

	// 初次保存推送结果
	private static final int SAVE_PUSH = 1;
	// 更新推送结果
	private static final int UPDATE_PUSH = 2;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String PUSH_FAIL_SUFFIX = "_repay_push_fail_count";

	private static final String TASK_FAIL_CNT_SUFFIX = "_repay_push_task_fail_count";

	// 短信验证码缓存时间
	private static final int CACHESECONDS = 24*60*60;

	/**
	 * 提前还款推送
	 * @param repayNoticeVO 推送信息
	 * @param userId 用户id
	 * @param applyId 申请编号
	 * @param payComOrderNo 支付公司订单号
	 * @return
	 */
	public String preRepayPush(RepayNoticeVO repayNoticeVO,
							   String userId, String applyId, String payComOrderNo) {
		// 参数判断
		if(null == repayNoticeVO
				|| StringUtils.isBlank(userId)
				|| StringUtils.isBlank(applyId)
				|| StringUtils.isBlank(payComOrderNo)) {
			logger.error("the param repayNoticeVO is null");
			return null;
		}
		// 参数转换
		Map<String, String> paramsMap = null;
		try {
			paramsMap = BeanUtils.describe(repayNoticeVO);
		} catch (Exception e) {
			logger.error("object to map error:[{}]", e);
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("batchservlet.advancePayment.url");
		if(logger.isDebugEnabled()) {
			logger.debug("url:[{}], params:[{}]",
					url, JsonMapper.toJsonString(paramsMap));
		}

		// 还款推送结果对象初始化
		RepayPush repayPush = new RepayPush();
		repayPush.preInsert();
		repayPush.setApplyId(applyId);
		repayPush.setUserId(userId);
		repayPush.setPushType(RepayPush.TYPE_PRE);
		repayPush.setChlOrderNo(payComOrderNo);
		repayPush.setAccountId(repayNoticeVO.getAccountId());
		repayPush.setAssetId(repayNoticeVO.getAssetId());
		repayPush.setInterestStartDate(repayNoticeVO.getInterestStartDate());
		repayPush.setInterestEndDate(repayNoticeVO.getInterestEndDate());
		if(null != repayNoticeVO.getPrepayFee()) {
			repayPush.setPrepayFee(
					new BigDecimal(repayNoticeVO.getPrepayFee()));
		}
		if(null != repayNoticeVO.getRepayAmount()) {
			repayPush.setTxAmount(
					new BigDecimal(repayNoticeVO.getRepayAmount()));
		}
		// 调用理财端接口
		return Adapter(url, paramsMap, repayPush, SAVE_PUSH, payComOrderNo);
	}

	/**
	 * 逾期还款推送
	 * @param repayNoticeVO 推送信息
	 * @param userId 用户id
	 * @param applyId 申请编号
	 * @param payComOrderNo 支付公司订单号
	 * @return
	 */
	public String OverdueRepayPush(OverdueRepayNoticeVO overdueRepayNoticeVO,
								   String userId, String applyId, String payComOrderNo) {
		// 参数判断
		if(null == overdueRepayNoticeVO
				|| StringUtils.isBlank(userId)
				|| StringUtils.isBlank(applyId)) {
			logger.error("the param overdueRepayNoticeVO is null");
			return null;
		}
		// 参数转换
		Map<String, String> paramsMap = null;
		try {
			paramsMap = BeanUtils.describe(overdueRepayNoticeVO);
		} catch (Exception e) {
			logger.error("object to map error:[{}]", e);
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("batchservlet.overdueRepayment.url");
		if(logger.isDebugEnabled()) {
			logger.debug("url:[{}], params:[{}]",
					url, JsonMapper.toJsonString(paramsMap));
		}

		// 还款推送结果对象初始化
		RepayPush repayPush = new RepayPush();
		repayPush.preInsert();
		repayPush.setApplyId(applyId);
		repayPush.setUserId(userId);
		repayPush.setPushType(RepayPush.TYPE_OVERDUE);
		repayPush.setChlOrderNo(payComOrderNo);
		repayPush.setAssetId(overdueRepayNoticeVO.getAssetId());
		repayPush.setAccountId(overdueRepayNoticeVO.getAccountId());
		repayPush.setRepayDate(overdueRepayNoticeVO.getRepayDate());
		if(null != overdueRepayNoticeVO.getInterest()) {
			repayPush.setInterest(new BigDecimal(
					overdueRepayNoticeVO.getInterest()));
		}
		if(null != overdueRepayNoticeVO.getOverdueFee()) {
			repayPush.setOverdueFee(new BigDecimal(
					overdueRepayNoticeVO.getOverdueFee()));
		}
		if(null != overdueRepayNoticeVO.getOverdueInterest()) {
			repayPush.setOverdueInterest(new BigDecimal(
					overdueRepayNoticeVO.getOverdueInterest()));
		}
		if(null != overdueRepayNoticeVO.getPrincipal()) {
			repayPush.setPrincipal(new BigDecimal(
					overdueRepayNoticeVO.getPrincipal()));
		}
		if(null != overdueRepayNoticeVO.getReduceFee()) {
			repayPush.setReduceFee(new BigDecimal(
					overdueRepayNoticeVO.getReduceFee()));
		}
		if(StringUtils.isBlank(payComOrderNo)) {
			// 调用理财端接口
			return restDo(url, paramsMap, repayPush, SAVE_PUSH);
		}
		// 调用理财端接口
		return Adapter(url, paramsMap, repayPush, SAVE_PUSH, payComOrderNo);
	}

	/**
	 * restful方法调用理财端接口
	 * @param url
	 * @param paramsMap
	 * @param logType 1
	 * @return
	 */
	private String restDo(String url, Map<String, String> paramsMap, RepayPush repayPush, int logType) {
		// 接口远程调用
		String respString =
				RestTemplateUtils.getInstance().postForJson(
						url, paramsMap);
		if(StringUtils.isNotBlank(respString)) {
			if(logger.isDebugEnabled()) {
				logger.debug("assetId:[{}], respString:[{}]",
						paramsMap.get("assetId"), respString);
			}
			// 返回结果解析
			PushRespVO rzMap = (PushRespVO)JsonMapper.fromJsonString(
					respString, PushRespVO.class);
			// 返回结果判断
			if(null != rzMap) {
				if(StringUtils.equals(
						Global.BANKDEPOSIT_SUCCSS_8, rzMap.getCode())){

					/** 保存推送结果 **/
					repayPush.setPushResult(RepayPush.RESULT_SUCCESS);
					repayPush.setPushDescribe(RepayPush.SUCCESS);
					saveOrUpdatePush(repayPush, logType);
					return Global.BANKDEPOSIT_SUCCSS_8;
				} else {
					/** 失败后重新发送 **/
					if(countCache(paramsMap.get("assetId"), PUSH_FAIL_SUFFIX)
							<= Global.REPAY_PUSH_REPEAT_LIMIT) {
						return restDo(url, paramsMap, repayPush, logType);
					}

					/** 保存推送结果 **/
					repayPush.setPushResult(RepayPush.RESULT_FAIL);
					repayPush.setPushDescribe(rzMap.getMessage());
					saveOrUpdatePush(repayPush, logType);

					// 清除还款推送次数缓存
					JedisUtils.del(paramsMap.get("assetId")+PUSH_FAIL_SUFFIX);

					logger.error("assetId:[{}], 推送失败信息：[{}]",
							paramsMap.get("assetId"), rzMap.getMessage());
					return rzMap.getMessage();
				}
			}
		}
		return null;
	}

	/**
	 * 接口调用适配器
	 * @param url
	 * @param paramsMap
	 * @param repayPush
	 * @param logType
	 * @param payComOrderNo
	 * @return
	 */
	private String Adapter(String url, Map<String, String> paramsMap,
						   RepayPush repayPush, int logType, String payComOrderNo) {
		// 查询还款到账情况
		String payStatus = repayPushManager.getPayStatus(payComOrderNo);
		if(StringUtils.equals(payStatus, RepayPush.SUCCESS)) {
			// 调用理财端接口
			return restDo(url, paramsMap, repayPush, logType);
		} else {
			if(SAVE_PUSH == logType) {
				repayPush.setPushResult(RepayPush.RESULT_FAIL);
				repayPush.setPushDescribe("还款未到账，暂存数据");
				repayPushManager.saveRepayPush(repayPush);
				return Global.BANKDEPOSIT_SUCCSS_8;
			}
		}
		return null;
	}

	/**
	 * 发送失败预警短信
	 * @param assetId
	 */
	private void sendFailSmg(String assetId) {
		// 24小时定时任务失败次数
		Integer batchFalicnt = (Integer) JedisUtils.getObject(
				assetId+TASK_FAIL_CNT_SUFFIX);
		// 定时任务失败次数超限，短信预警
		if(null != batchFalicnt
				&& batchFalicnt >= Global.REPAY_PUSH_TASK_LIMIT) {
			// 短信预警
			SendShortMsgOP msgOP = new SendShortMsgOP();
			// 通知人手机号
			String mobs = Global.getConfig("push.fail.warn.mobs");
			msgOP.setMobile(mobs);
			// 短信内容
			String message = String.format(
					ShortMsgTemplate.MSG_TEMP_REPAYPUSH_FAIL, assetId);
			msgOP.setMessage(message);
			shortMsgService.sendMsg(msgOP);
		}
	}

	/**
	 * 保存或更新推送结果
	 * @param repayPush
	 * @param tpye
	 */
	private void saveOrUpdatePush(RepayPush repayPush, int tpye) {
		if(SAVE_PUSH == tpye) {
			repayPushManager.saveRepayPush(repayPush);
		} else if(UPDATE_PUSH == tpye) {
			repayPushManager.updatePushResultByAssetId(repayPush);
		}
	}


	/**
	 * 次数缓存
	 * @param assetId
	 * @param suffix 后缀列席
	 * @return
	 */
	private int countCache(String assetId, String suffix) {
		String cacheKey = assetId+suffix;
		Integer cnt = (Integer) JedisUtils.getObject(cacheKey);
		if(null == cnt) {
			cnt = 1;
		} else {
			cnt++;
		}
		JedisUtils.setObject(cacheKey, cnt, CACHESECONDS);
		return cnt;
	}

	/**
	 * 提前还款推送资产端（聚宝钱包）定时任务
	 * @return
	 */
	public TaskResult preRepayPushTask() {
		// 任务结果统计变量
		int success = 0;
		int fail = 0;
		// 查询推送失败的数据
		logger.info("提前还款信息推送资产端----开始。");
		long starTime = System.currentTimeMillis();
		List<RepayPush> list = getPushFailAsset(RepayPush.TYPE_PRE);
		if (CollectionUtils.isEmpty(list)) {
			long endTime = System.currentTimeMillis();
			logger.info("提前还款信息推送资产端结束，暂无待推送数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 处理推送失败的数据
		for(RepayPush info : list) {
			if(null != info) {
				try {
					/** 推送接口参数设置 **/
					RepayNoticeVO repayNoticeVO = new RepayNoticeVO();
					repayNoticeVO.setAccountId(info.getAccountId());
					repayNoticeVO.setAssetId(info.getAssetId());
					repayNoticeVO.setInterestStartDate(info.getInterestStartDate());
					repayNoticeVO.setInterestEndDate(info.getInterestEndDate());
					repayNoticeVO.setRepayAmount(
							String.valueOf(info.getTxAmount()));
					repayNoticeVO.setPrepayFee(String.valueOf(info.getPrepayFee()));
					// 参数转换
					Map<String, String> paramsMap = BeanUtils.describe(repayNoticeVO);
					// 获取配置文件中配置的调用url
					String url = Global.getConfig("batchservlet.advancePayment.url");
					logger.debug("url:[{}], params:[{}]", url, JsonMapper.toJsonString(paramsMap));

					/** 还款推送结果对象初始化 **/
					RepayPush repayPush = new RepayPush();
					repayPush.preUpdate();
					repayPush.setAssetId(info.getAssetId());

					// 调用推送接口
					if(StringUtils.isNotBlank(info.getChlOrderNo())) {
						String rz = Adapter(url, paramsMap, repayPush, UPDATE_PUSH, info.getChlOrderNo());
						// 记录成功失败次数
						if(null == rz) {
							// 不记录成功或者失败
						}
						else if(StringUtils.equals(rz, Global.BANKDEPOSIT_SUCCSS_8)) {
							success++;
						} else {
							countCache(info.getAssetId(), TASK_FAIL_CNT_SUFFIX);
							fail++;
							// 发送预警短信
							sendFailSmg(info.getAssetId());
						}
					}
				} catch (Exception e) {
					logger.error("提前还款信息推送资产端,AssetId:[{}],[{}] ",
							info.getAssetId(), e);
					fail++;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("提前还款信息推送资产端,成功{}笔,失败{}笔,执行耗时{}",
				success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	/**
	 * 逾期还款推送资产端（聚宝钱包）定时任务
	 * @return
	 */
	public TaskResult overdueRepayPushTask() {
		// 任务结果统计变量
		int success = 0;
		int fail = 0;
		// 查询推送失败的数据
		logger.info("逾期还款信息推送资产端----开始。");
		long starTime = System.currentTimeMillis();
		List<RepayPush> list = getPushFailAsset(RepayPush.TYPE_OVERDUE);
		if (CollectionUtils.isEmpty(list)) {
			long endTime = System.currentTimeMillis();
			logger.info("逾期还款信息推送资产端结束，暂无待推送数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 处理推送失败的数据
		for(RepayPush info : list) {
			if(null != info) {
				try {
					/** 推送接口参数设置 **/
					OverdueRepayNoticeVO overdueRepayNoticeVO = new OverdueRepayNoticeVO();
					overdueRepayNoticeVO.setAccountId(info.getAccountId());
					overdueRepayNoticeVO.setAssetId(info.getAssetId());
					overdueRepayNoticeVO.setRepayDate(info.getRepayDate());
					overdueRepayNoticeVO.setInterest(
							String.valueOf(info.getInterest()));
					overdueRepayNoticeVO.setOverdueFee(
							String.valueOf(info.getOverdueFee()));
					overdueRepayNoticeVO.setOverdueInterest(
							String.valueOf(info.getOverdueInterest()));
					overdueRepayNoticeVO.setPrincipal(
							String.valueOf(info.getPrincipal()));
					overdueRepayNoticeVO.setReduceFee(
							String.valueOf(info.getReduceFee()));
					// 参数转换
					Map<String, String> paramsMap = BeanUtils.describe(overdueRepayNoticeVO);
					// 获取配置文件中配置的调用url
					String url = Global.getConfig("batchservlet.overdueRepayment.url");
					if(logger.isDebugEnabled()) {
						logger.debug("url:[{}], params:[{}]",
								url, JsonMapper.toJsonString(paramsMap));
					}

					/** 还款推送结果对象初始化 **/
					RepayPush repayPush = new RepayPush();
					repayPush.preUpdate();
					repayPush.setAssetId(info.getAssetId());

					// 调用推送接口
					String rz = null;
					if(StringUtils.isNotBlank(info.getChlOrderNo())) {
						// 逾期主动还款情况
						rz = Adapter(url, paramsMap, repayPush, UPDATE_PUSH, info.getChlOrderNo());
					} else {
						// 逾期代扣还款情况
						rz = restDo(url, paramsMap, repayPush, UPDATE_PUSH);
					}
					// 记录成功失败次数
					if(null == rz) {
						// 不记录成功或者失败
					}
					else if(StringUtils.equals(rz, Global.BANKDEPOSIT_SUCCSS_8)) {
						success++;
					} else {
						countCache(info.getAssetId(), TASK_FAIL_CNT_SUFFIX);
						fail++;
						// 发送预警短信
						sendFailSmg(info.getAssetId());
					}

				} catch (Exception e) {
					logger.error("逾期还款信息推送资产端,AssetId:[{}],[{}] ", info.getAssetId(), e);
					fail++;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("逾期还款信息推送资产端,成功{}笔,失败{}笔,执行耗时{}", success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}

	/**
	 * 根据推送类型查询推送失败信息列表
	 * @param pushType
	 * @return
	 */
	private List<RepayPush> getPushFailAsset(Integer pushType) {
		if(pushType != null) {
			return repayPushManager.getPushFailByType(pushType);
		}
		return null;
	}

	/**
	 * 正常还款推送
	 * @param repayNoticeVO 推送信息
	 * @param userId 用户id
	 * @param applyId 申请编号
	 * @param payComOrderNo 支付公司订单号
	 * @return
	 */
	public String onTimeRepayPush(RepayNoticeVO repayNoticeVO, String userId,
								  String applyId, String payComOrderNo) {
		// 参数判断
		if(null == repayNoticeVO
				|| StringUtils.isBlank(userId)
				|| StringUtils.isBlank(applyId)
				|| StringUtils.isBlank(payComOrderNo)) {
			logger.error("the param repayNoticeVO is null");
			return null;
		}
		// 参数转换
		Map<String, String> paramsMap = null;
		try {
			paramsMap = BeanUtils.describe(repayNoticeVO);
			paramsMap.remove("prepayFee");
		} catch (Exception e) {
			logger.error("object to map error:[{}]", e);
		}
		// 获取配置文件中配置的调用url
		String url = Global.getConfig("batchservlet.repay.url");
		if(logger.isDebugEnabled()) {
			logger.debug("url:[{}], params:[{}]",
					url, JsonMapper.toJsonString(paramsMap));
		}

		// 还款推送结果对象初始化
		RepayPush repayPush = new RepayPush();
		repayPush.preInsert();
		repayPush.setApplyId(applyId);
		repayPush.setUserId(userId);
		repayPush.setPushType(RepayPush.TYPE_ONTIME);
		repayPush.setChlOrderNo(payComOrderNo);
		repayPush.setAccountId(repayNoticeVO.getAccountId());
		repayPush.setAssetId(repayNoticeVO.getAssetId());
		repayPush.setInterestStartDate(repayNoticeVO.getInterestStartDate());
		repayPush.setInterestEndDate(repayNoticeVO.getInterestEndDate());
		if(null != repayNoticeVO.getRepayAmount()) {
			repayPush.setTxAmount(
					new BigDecimal(repayNoticeVO.getRepayAmount()));
		}
		// 调用理财端接口
		return Adapter(url, paramsMap, repayPush, SAVE_PUSH, payComOrderNo);
	}


	/**
	 * 正常还款推送资产端（聚宝钱包）定时任务
	 * @return
	 */
	public TaskResult onTimeRepayPushTask() {
		// 任务结果统计变量
		int success = 0;
		int fail = 0;
		// 查询推送失败的数据
		logger.info("按期还款信息推送资产端----开始。");
		long starTime = System.currentTimeMillis();
		List<RepayPush> list = getPushFailAsset(RepayPush.TYPE_ONTIME);
		if (CollectionUtils.isEmpty(list)) {
			long endTime = System.currentTimeMillis();
			logger.info("按期还款信息推送资产端结束，暂无待推送数据。执行耗时{}", endTime - starTime);
			return new TaskResult(success, fail);
		}
		// 处理推送失败的数据
		for (RepayPush info : list) {
			if (null != info) {
				try {
					/** 推送接口参数设置 **/
					RepayNoticeVO repayNoticeVO = new RepayNoticeVO();
					repayNoticeVO.setAccountId(info.getAccountId());
					repayNoticeVO.setAssetId(info.getAssetId());
					repayNoticeVO.setInterestStartDate(info.getInterestStartDate());
					repayNoticeVO.setInterestEndDate(info.getInterestEndDate());
					repayNoticeVO.setRepayAmount(
							String.valueOf(info.getTxAmount()));
					// 参数转换
					Map<String, String> paramsMap = BeanUtils.describe(repayNoticeVO);
					paramsMap.remove("prepayFee");
					// 获取配置文件中配置的调用url
					String url = Global.getConfig("batchservlet.repay.url");
					logger.debug("url:[{}], params:[{}]", url, JsonMapper.toJsonString(paramsMap));

					/** 还款推送结果对象初始化 **/
					RepayPush repayPush = new RepayPush();
					repayPush.preUpdate();
					repayPush.setAssetId(info.getAssetId());

					// 调用推送接口
					if (StringUtils.isNotBlank(info.getChlOrderNo())) {
						String rz = Adapter(url, paramsMap, repayPush, UPDATE_PUSH, info.getChlOrderNo());
						// 记录成功失败次数
						if (null == rz) {
							// 不记录成功或者失败
						} else if (StringUtils.equals(rz, Global.BANKDEPOSIT_SUCCSS_8)) {
							success++;
						} else {
							countCache(info.getAssetId(), TASK_FAIL_CNT_SUFFIX);
							fail++;
							// 发送预警短信
							sendFailSmg(info.getAssetId());
						}
					}
				} catch (Exception e) {
					logger.error("按期还款信息推送资产端,AssetId:[{}],[{}] ",
							info.getAssetId(), e);
					fail++;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("按期还款信息推送资产端,成功{}笔,失败{}笔,执行耗时{}",
				success, fail, endTime - starTime);
		return new TaskResult(success, fail);
	}
}
