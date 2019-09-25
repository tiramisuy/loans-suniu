package com.rongdu.loans.loan.web;

import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.RoleControl;
import com.rongdu.loans.common.RoleControlParam;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.credit.baiqishi.service.ReportService;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.ChannelVO;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.ReturnTypeEnum;
import com.rongdu.loans.enums.UrgentRecallResult;
import com.rongdu.loans.loan.op.ApplyOP;
import com.rongdu.loans.loan.op.CollectionAssignmentOP;
import com.rongdu.loans.loan.op.PressOP;
import com.rongdu.loans.loan.option.CollectionRecordOP;
import com.rongdu.loans.loan.option.OverdueOP;
import com.rongdu.loans.loan.option.OverduePushBackOP;
import com.rongdu.loans.loan.option.RepayDetailListOP;
import com.rongdu.loans.loan.service.*;
import com.rongdu.loans.loan.vo.*;
import com.rongdu.loans.risk.option.BlacklistOP;
import com.rongdu.loans.risk.service.RiskBlacklistService;
import com.rongdu.loans.risk.vo.BlacklistVO;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.ChannelService;
import com.rongdu.loans.sys.service.SystemService;
import com.rongdu.loans.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by zhangxiaolong on 2017/9/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/collection/")
public class CollectionAssignmentController extends BaseController {

	@Autowired
	private SysCollectionAssignmentService sysCollectionAssignmentService;

	@Autowired
	private CollectionAssignmentService collectionAssignmentService;
	@Autowired
	private CollectionRecordService collectionRecordService;
	@Autowired
	private RepayPlanItemService repayPlanItemService;
	@Autowired
	private CustUserService custUserService;
	@Autowired
	private OverdueService overdueService;

	@Autowired
	private RiskBlacklistService riskBlacklistService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private ApplyTripartiteService applyTripartiteService;
	@Autowired
	private ApplyTripartiteRong360Service applyTripartiteRong360Service;
	@Autowired
	private JDQService jdqService;
	@Autowired
	private DWDService dwdService;
	@Autowired
	private RepayWarnService repayWarnService;
	@Autowired
	private SLLService sllService;
	@Autowired
	private LoanApplyService loanApplyService;

	/**
	 * 逾期催收列表
	 *
	 * @param pressOP
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pressList")
	public String pressList(PressOP pressOP, Boolean first,
			@RequestParam(value = "userNameEncode", required = false) String userNameEncode,
			@RequestParam(value = "contentEncode", required = false) String contentEncode, Model model) {
		User user = UserUtils.getUser();
		logger.info("逾期催收列表查询--->{}--->{}-->{}", user.getId(), user.getName(), JsonMapper.toJsonString(pressOP));

		List<ChannelVO> chList = channelService.findAllChannel();
		StringBuffer chAllStr = new StringBuffer();
		for (ChannelVO ch : chList) {
			chAllStr.append("'").append(ch.getCid()).append("',");
		}
		chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
		model.addAttribute("channels", chList);
		model.addAttribute("allChannel", chAllStr);
		if (null != first && first) {
			model.addAttribute("page", new Page(1, 10));
			return "modules/loan/pressList";
		}
		// 页面间get请求避免乱码问题 页面端进行转码 controller端进行解码 详情返回时会使用到
		if (StringUtils.isNoneBlank(userNameEncode)) {
			try {
				userNameEncode = URLDecoder.decode(userNameEncode.trim(), "UTF-8");
				pressOP.setUserName(userNameEncode);
			} catch (UnsupportedEncodingException e) {

			}
		}
		// 页面间get请求避免乱码问题 页面端进行转码 controller端进行解码 详情返回时会使用到
		if (StringUtils.isNoneBlank(contentEncode)) {
			try {
				contentEncode = URLDecoder.decode(contentEncode.trim(), "UTF-8");
				pressOP.setContent(contentEncode);
			} catch (UnsupportedEncodingException e) {

			}
		}
		Boolean isCsy = (UserUtils.haveRole(user, "csy") || UserUtils.haveRole(user, "csy_m2"));
		// 系统管理员可以看到所有
		if (UserUtils.haveRole(user,"system")){
			isCsy = false;
		}
		model.addAttribute("isCsy", isCsy);
		if (isCsy) {
			pressOP.setOperatorId(user.getId());
		}
		model.addAttribute("enums", PressOP.OverdueEnum.values());
		model.addAttribute("csy", UserUtils.getUserByRole("csy"));
		model.addAttribute("pressOP", pressOP);
		model.addAttribute("resultList", UrgentRecallResult.values());
		OverdueOP op = pressOP2OverdueOP(pressOP);
		Page<OverdueVO> overdueList = null;
		RoleControlParam roleControlParam = RoleControl.get(pressOP.getProductId(), op.getCompanyId());
		op.setCompanyId(roleControlParam.getCompanyId());
		op.setProductId(roleControlParam.getProductId());
		// 逾期催收 设置分页最小设置为30，默认30
		if (op.getStatus() == 0 && op.getPageSize() <= 30) {
			op.setPageSize(30);
		}
		overdueList = overdueService.overdueList(op);

		Map<String, String> map = getChannelName(); // 渠道信息
		for (OverdueVO vo : overdueList.getList()) {
			vo.setChannelId(map.get(vo.getChannelId()));
			if (StringUtils.isNotBlank(vo.getResult())) {
				if (vo.getResult().equals("暂停催收")) {
					vo.setIsPushOverdue(true);
				} else {
					vo.setIsPushOverdue(false);
				}
			} else {
				vo.setIsPushOverdue(false);
			}

		}

		model.addAttribute("page", overdueList);

		return "modules/loan/pressList";
	}

	public Map<String, String> getChannelName() {
		List<ChannelVO> list = channelService.findAllChannel();
		Map<String, String> map = new HashMap<String, String>();
		for (ChannelVO vo : list) {
			map.put(vo.getCid(), vo.getcName());
		}
		return map;
	}

	/**
	 * 导出已还明细数据
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	@ExportLimit()
	public void exportFile(PressOP pressOP, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		User user = UserUtils.getUser();
		logger.info("导出催收已还数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("催收已还数据", OverdueVO.class);
			String fileName = "催收已还明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			OverdueOP op = pressOP2OverdueOP(pressOP);
			op.setPageSize(-1);
			RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
			op.setCompanyId(roleControlParam.getCompanyId());
			op.setProductId(roleControlParam.getProductId());
			Page<OverdueVO> page = overdueService.overdueList(op);
			if (page != null) {
				List<OverdueVO> result = page.getList();
				excel.setDataList(result).write(response, fileName);
			}
		} finally {
			if (excel != null)
				excel.dispose();
		}
	}

	/**
	 * 导出逾期催收数据
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "OverDueDataExport", method = RequestMethod.POST)
	@ExportLimit()
	public void OverDueDataExport(PressOP pressOP, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		User user = UserUtils.getUser();
		logger.info("导出逾期数据--->{}--->{}", user.getId(), user.getName());
		ExportExcel excel = null;
		try {
			excel = new ExportExcel("逾期数据", OverdueDataExportVo.class);
			String fileName = "逾期数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			OverdueOP op = pressOP2OverdueOP(pressOP);
			op.setPageSize(-1);
			RoleControlParam roleControlParam = RoleControl.get(op.getProductId(), op.getCompanyId());
			op.setCompanyId(roleControlParam.getCompanyId());
			op.setProductId(roleControlParam.getProductId());
			Page<OverdueVO> page = overdueService.overdueList(op);
			if (page != null) {
				List<OverdueVO> result = page.getList();
				excel.setDataList(result).write(response, fileName);
			} else {
				Page<OverdueDataExportVo> pages = new Page<OverdueDataExportVo>();
			}
		} finally {
			if (excel != null)
				excel.dispose();
		}
	}

	private OverdueOP pressOP2OverdueOP(PressOP pressOP) {
		OverdueOP op = BeanMapper.map(pressOP, OverdueOP.class);
		if (StringUtils.isNotBlank(pressOP.getBorrowStart())) {
			op.setBorrowTimeStart(DateUtils.parse(pressOP.getBorrowStart()));
		}
		if (StringUtils.isNotBlank(pressOP.getBorrowEnd())) {
			op.setBorrowTimeEnd(DateUtils.parse(pressOP.getBorrowEnd()));
		}
		if (StringUtils.isNotBlank(pressOP.getExpectStart())) {
			op.setExpectTimeStart(DateUtils.parse(pressOP.getExpectStart()));
		}
		if (StringUtils.isNotBlank(pressOP.getExpectEnd())) {
			op.setExpectTimeEnd(DateUtils.parse(pressOP.getExpectEnd()));
		}
		if (StringUtils.isNotBlank(pressOP.getActualEnd())) {
			op.setActualTimeEnd(DateUtils.parse(pressOP.getActualEnd()));
		}
		if (StringUtils.isNotBlank(pressOP.getActualStart())) {
			op.setActualTimeStart(DateUtils.parse(pressOP.getActualStart()));
		}
		if (CollectionUtils.isNotEmpty(pressOP.getOverdue())) {
			List<OverdueOP.OverdueTime> overdueTimeList = new ArrayList<OverdueOP.OverdueTime>();
			for (Integer value : pressOP.getOverdue()) {
				PressOP.OverdueEnum overdueEnum = PressOP.OverdueEnum.get(value);
				OverdueOP.OverdueTime overdueTime = op.new OverdueTime();
				overdueTime.setMin(overdueEnum.getMin());
				overdueTime.setMax(overdueEnum.getMax());
				overdueTimeList.add(overdueTime);
			}
			op.setOverdueList(overdueTimeList);
		}
		return op;
	}

	/**
	 * 催收详情页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pressFrom")
	public String pressFrom(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "itemId") String itemId, String applyId, String page, Model model, PressOP pressOP,
			@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "userNameEncode", required = false) String userNameEncode,
			@RequestParam(value = "contentEncode", required = false) String contentEncode,
			@RequestParam(value = "index", required = false) String index,
			@RequestParam(value = "beforeOrAfter", required = false) String beforeOrAfter,
			@RequestParam(value = "overday") String overday) {
		CustUserVO vo = custUserService.getCustUserById(userId);
		model.addAttribute("userIdNo", vo.getIdNo());
		model.addAttribute("userRealName", vo.getRealName());
		model.addAttribute("userMobile", vo.getMobile());
		model.addAttribute("userId", userId);
		model.addAttribute("applyId", applyId);
		model.addAttribute("itemId", itemId);
		model.addAttribute("page", page);
		// 根据逾期时间判断相关界面是否显示
		model.addAttribute("overday", overday);

		// 页面间get请求避免乱码问题 页面端进行转码 controller端进行解码 详情返回时会使用到
		if (StringUtils.isNoneBlank(userNameEncode)) {
			try {
				userNameEncode = URLDecoder.decode(userNameEncode.trim(), "UTF-8");
				pressOP.setUserName(userNameEncode);
			} catch (UnsupportedEncodingException e) {

			}
		}
		// 页面间get请求避免乱码问题 页面端进行转码 controller端进行解码 详情返回时会使用到
		if (StringUtils.isNoneBlank(contentEncode)) {
			try {
				contentEncode = URLDecoder.decode(contentEncode.trim(), "UTF-8");
				pressOP.setContent(contentEncode);
			} catch (UnsupportedEncodingException e) {

			}
		}
		User user = UserUtils.getUser();
		Boolean isCsy = UserUtils.haveRole(user, "csy");
		model.addAttribute("isCsy", isCsy);
		if (isCsy) {
			pressOP.setOperatorId(user.getId());
		}
		model.addAttribute("csy", UserUtils.getUserByRole("csy"));
		OverdueOP op = pressOP2OverdueOP(pressOP);
		Page<OverdueVO> overdueList = null;
		RoleControlParam roleControlParam = RoleControl.get(pressOP.getProductId(), op.getCompanyId());
		op.setCompanyId(roleControlParam.getCompanyId());
		op.setProductId(roleControlParam.getProductId());

		Map<String, Object> resBefore = new HashMap<String, Object>(); // 存放上一个的信息（仅保存id即可）
		Map<String, Object> resAfter = new HashMap<String, Object>(); // 存放下一个的信息（仅保存id即可）
		String hasBefore = "1"; // 用于控制是否上一个按钮 1有 0没有
		String hasAfter = "1"; // 用于控制是否下一个按钮 1有 0没有
		// overdueList = overdueService.overdueList(op);
		// 逾期催收 设置分页最小设置为30，默认30
		if (op.getStatus() == 0 && op.getPageSize() <= 30) {
			op.setPageSize(30);
			pressOP.setPageSize(30);
		}
		int pageNo = pressOP.getPageNo();
		int pageSize = pressOP.getPageSize();
		Page voPage = new Page(op.getPageNo(), op.getPageSize());
		if (index != null && index.trim().length() != 0) {
			int indexInt = Integer.valueOf(index);
			// 判断是否有上一个
			hasBefore = dealBefore(pressOP, beforeOrAfter, op, resBefore, hasBefore, pageNo, pageSize, voPage,
					indexInt);
			// 判断是否有下一个
			hasAfter = dealAfter(pressOP, beforeOrAfter, op, resAfter, pageNo, pageSize, voPage, hasAfter, indexInt);
		}
		model.addAttribute("pressOP", pressOP);
		model.addAttribute("parentId", parentId);
		// 处理点击上一个时候 userId applyId itemId 的变化 （用于用户信息等的查询）
		if ("-1".equals(beforeOrAfter)) {
			String beforeItemId = resBefore.get("beforeItemId") + "";
			String beforeApplyId = resBefore.get("beforeApplyId") + "";
			String beforeUserId = resBefore.get("beforeUserId") + "";
			if (StringUtils.isNoneBlank(beforeItemId) && StringUtils.isNoneBlank(beforeApplyId)
					&& StringUtils.isNoneBlank(beforeUserId)) {
				model.addAttribute("userId", beforeUserId);
				model.addAttribute("applyId", beforeApplyId);
				model.addAttribute("itemId", beforeItemId);
			}
		}
		// 处理点击下一个时候 userId applyId itemId 的变化 （用于用户信息等的查询）
		if ("1".equals(beforeOrAfter)) {
			String afterItemId = resAfter.get("afterItemId") + "";
			String afterApplyId = resAfter.get("afterApplyId") + "";
			String afterUserId = resAfter.get("afterUserId") + "";
			if (StringUtils.isNoneBlank(afterItemId) && StringUtils.isNoneBlank(afterApplyId)
					&& StringUtils.isNotBlank(afterUserId)) {
				model.addAttribute("userId", afterUserId);
				model.addAttribute("applyId", afterApplyId);
				model.addAttribute("itemId", afterItemId);
			}
		}
		// 处理点击上一个时候 页面index的变化
		if ("-1".equals(beforeOrAfter)) {
			index = dealBeforeIndex(pageNo, pageSize, index);
		}
		// 处理点击下一个时候 页面index的变化
		if ("1".equals(beforeOrAfter)) {
			index = dealAfterIndex(op, voPage, pageNo, pageSize, index);
		}
		model.addAttribute("index", index);// 当前页面索引 第几个
		model.addAttribute("hasBefore", hasBefore); // 是否有上一个
		model.addAttribute("resBefore", resBefore); // 上一个的信息
		model.addAttribute("hasAfter", hasAfter); // 是否有下一个
		model.addAttribute("resAfter", resAfter); // 下一个的信息
		model.addAttribute("beforeOrAfter", beforeOrAfter); // 操作标识 -1上一个 1下一个

		// RepayWarnVO warnvo = repayWarnService.getByRepayId(itemId);
		// model.addAttribute("warnInfo", warnvo);
		LoanApplySimpleVO applySimpleVO = loanApplyService.getLoanApplyById(applyId);
		model.addAttribute("succCount", applySimpleVO.getLoanSuccCount());
		if (applyTripartiteService.isExistApplyId(applyId)) {
			return "modules/loan/XianJinCardPressForm";
		} else if ((("RONG".equals(applySimpleVO.getChannelId()) || "RONGJHH".equals(applySimpleVO.getChannelId()))
				&& "4".equals(applySimpleVO.getSource())) || applyTripartiteRong360Service.isExistApplyId(applyId)) {
			return "modules/loan/rongPressForm";
		} else if (jdqService.isExistApplyId(applyId)) {
			return "modules/loan/jdqPressForm";
		} else if (dwdService.isExistApplyId(applyId)) {
			return "modules/loan/dwdPressForm";
		} /*else if (sllService.isExistApplyId(applyId)) {
			return "modules/loan/sllPressForm";
		}*/ else {
			return "modules/loan/pressForm";
		}
	}

	// 计算点击下一个时候的页面索引的变化
	private String dealAfterIndex(OverdueOP op, Page page, int pageNo, int pageSize, String index) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("op", op);
		condition.put("page", page);
		int index_int = Integer.valueOf(index);
		List<Map<String, Object>> totalList = sysCollectionAssignmentService.findOverdueListByCondition(condition);
		if (totalList != null && totalList.size() != 0) {
			// 获取最大页面数
			int maxPage = (totalList.size() % pageSize == 0) ? totalList.size() / pageSize
					: totalList.size() / pageSize + 1;
			// 计算出当前索引值
			int countIndex = (pageNo - 1) * pageSize + index_int - 1;
			// 如果是最后一个，没有下一个
			if (countIndex == totalList.size()) {
				index = "";
			} else {
				// 如果当前不是最大页 并且当前是当前页最后一个 进行翻页 索引值变为1 ，如果不是最后一个 index+1
				if (pageNo < maxPage && index_int == pageSize) {
					// 下一页首位
					index = "1";
				} else {
					index = (index_int + 1) + "";
				}
			}
		} else {
			index = "";
		}
		return index;
	}

	// 计算点击上一个时候的页面索引变化
	private String dealBeforeIndex(int pageNo, int pageSize, String index) {
		int index_int = Integer.valueOf(index);
		if (pageNo == 1 && index_int == 1) {
			// 如果是第一页的第一个，也就是没有上一个
			index = "";
		} else if (index_int == 1) {
			// 上一个为前一页末尾
			index = pageSize + "";
		} else {
			// 其他情况index-1
			index = (index_int - 1) + "";
		}
		return index;
	}

	// 判断是否有下一个
	private String dealAfter(PressOP pressOP, String beforeOrAfter, OverdueOP op, Map<String, Object> resAfter,
			int pageNo, int pageSize, Page voPage, String hasAfter, int indexInt) {
		// 计算出下一个的索引值
		int afterIndex = (pageNo - 1) * pageSize + indexInt - 1 + 1;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("op", op);
		condition.put("page", voPage);
		// 查出全部
		List<Map<String, Object>> totalList = sysCollectionAssignmentService.findOverdueListByCondition(condition);
		if (totalList != null && totalList.size() != 0) {
			int totalSize = totalList.size();
			// 如果最后一个点击上一个 最后一个上一个的下一个存在就是最后一个
			if (totalSize == afterIndex && "-1".equals(beforeOrAfter)) {
				afterIndex = afterIndex - 1;
			}
			condition.put("afterIndex", afterIndex);

			// 查出下一个
			List<Map<String, Object>> afterList = sysCollectionAssignmentService.findOverdueListByCondition(condition);
			// 如果存在下一个，就获取下一个的信息，填充给resAfter
			if (afterList != null && afterList.size() == 1) {
				Map<String, Object> after = afterList.get(0);
				String afterItemId = after.get("id") + "";
				String afterUserId = after.get("userId") + "";
				String afterApplyId = after.get("applyId") + "";
				if (StringUtils.isNoneBlank(afterItemId) && StringUtils.isNoneBlank(afterUserId)
						&& StringUtils.isNoneBlank(afterApplyId)) {
					resAfter.put("afterItemId", afterItemId);
					resAfter.put("afterUserId", afterUserId);
					resAfter.put("afterApplyId", afterApplyId);
					// 如果是某一页的最后1条 点击下一个 那么pageNo+1
					if (indexInt == pageSize && "1".equals(beforeOrAfter)) {
						// 查询当前查询的最大数据 获取最大页码数
						// 比较当前页码数和最大之间的关系，如果相同则不变，如果当前小于最大，则当前页码加1
						pageNo = (pageNo < totalSize) ? (pageNo + 1) : totalSize;
						pressOP.setPageNo(pageNo);
					}
				}
			} else {
				if (!"-1".equals(beforeOrAfter)) {
					hasAfter = "0";
				}
			}
			if ("1".equals(beforeOrAfter)) {
				if (afterIndex + 1 == totalSize) {
					hasAfter = "0";
				}
			}
		} else {
			hasAfter = "0";
		}
		return hasAfter;
	}

	// 判断是否有上一个
	private String dealBefore(PressOP pressOP, String beforeOrAfter, OverdueOP op, Map<String, Object> resBefore,
			String hasBefore, int pageNo, int pageSize, Page voPage, int indexInt) {
		if (pageNo == 1 && indexInt == 1) {
			// 那么此时当前是第一个数据，没有上一个
			if (!"1".equals(beforeOrAfter)) {
				hasBefore = "0";
			} else if ("1".equals(beforeOrAfter)) {
				// 第一个数据的点击下一个是有数据的，进行填充
				Map<String, Object> condition = new HashMap<String, Object>();
				int beforeIndex = (pageNo - 1) * pageSize + indexInt - 1 - 1;
				condition.put("op", op);
				condition.put("page", voPage);
				// 避免错误，进行处理
				beforeIndex = (beforeIndex < 0) ? 0 : beforeIndex;
				if (beforeIndex == 0) {
					if ("-1".equals(beforeOrAfter)) {
						hasBefore = "0";
					}
					condition.put("first", 1);
				} else {
					condition.put("beforeIndex", beforeIndex);
				}
				// 查出上一个
				List<Map<String, Object>> list = sysCollectionAssignmentService.findOverdueListByCondition(condition);
				if (list != null && list.size() == 1) {
					Map<String, Object> before = list.get(0);
					String beforeItemId = before.get("id") + "";
					String beforeUserId = before.get("userId") + "";
					String beforeApplyId = before.get("applyId") + "";
					if (StringUtils.isNoneBlank(beforeItemId) && StringUtils.isNoneBlank(beforeUserId)
							&& StringUtils.isNoneBlank(beforeApplyId)) {
						resBefore.put("beforeItemId", beforeItemId);
						resBefore.put("beforeUserId", beforeUserId);
						resBefore.put("beforeApplyId", beforeApplyId);
						// 如果是某一页（非首页）的第一条 点击上一个 那么pageNo-1
						if (indexInt == 1 && pageNo > 1 && "-1".equals(beforeOrAfter)) {
							pressOP.setPageNo(pageNo - 1);
						}
					}
				}
			}
		} else {
			// 不是第一个的情况下，就肯定有上一个
			Map<String, Object> condition = new HashMap<String, Object>();
			int beforeIndex = (pageNo - 1) * pageSize + indexInt - 1 - 1;
			condition.put("op", op);
			condition.put("page", voPage);
			// 避免错误，进行处理
			beforeIndex = (beforeIndex < 0) ? 0 : beforeIndex;
			if (beforeIndex == 0) {
				if ("-1".equals(beforeOrAfter)) {
					hasBefore = "0";
				}
				condition.put("first", 1);
			} else {
				condition.put("beforeIndex", beforeIndex);
			}
			// 查出上一个
			List<Map<String, Object>> list = sysCollectionAssignmentService.findOverdueListByCondition(condition);
			if (list != null && list.size() == 1) {
				Map<String, Object> before = list.get(0);
				String beforeItemId = before.get("id") + "";
				String beforeUserId = before.get("userId") + "";
				String beforeApplyId = before.get("applyId") + "";
				if (StringUtils.isNoneBlank(beforeItemId) && StringUtils.isNoneBlank(beforeUserId)
						&& StringUtils.isNoneBlank(beforeApplyId)) {
					resBefore.put("beforeItemId", beforeItemId);
					resBefore.put("beforeUserId", beforeUserId);
					resBefore.put("beforeApplyId", beforeApplyId);
					// 如果是某一页（非首页）的第一条 点击上一个 那么pageNo-1
					if (indexInt == 1 && pageNo > 1 && "-1".equals(beforeOrAfter)) {
						pressOP.setPageNo(pageNo - 1);
					}
				}

			} else {
				hasBefore = "0";
			}
		}
		return hasBefore;
	}

	/**
	 * 添加黑名单详情
	 *
	 * @param userId
	 * @param itemId
	 * @param applyId
	 * @param page
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pressBlackFrom")
	public WebResult pressBlackFrom(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "itemId") String itemId, String applyId, Model model) {
		try {
			CustUserVO vo = custUserService.getCustUserById(userId);
			model.addAttribute("userIdNo", vo.getIdNo());
			model.addAttribute("userRealName", vo.getRealName());
			model.addAttribute("userMobile", vo.getMobile());

			model.addAttribute("userId", userId);
			model.addAttribute("applyId", applyId);
			model.addAttribute("itemId", itemId);

			RepayDetailListOP op = new RepayDetailListOP();
			op.setUserId(userId);
			op.setStatus(0);
			op.setPageSize(Integer.MAX_VALUE);
			op.setId(itemId);

			model.addAttribute("collectionDetailVo", repayPlanItemService.repayDetailList(op).getList());
			return new WebResult("1", "提交成功", model);

		} catch (RuntimeException e) {
			logger.error("查询黑名单详情异常：custId = " + userId + " applyId = " + applyId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询黑名单详情异常：custId = " + userId + " applyId = " + applyId, e);
			return new WebResult("99", "系统异常");
		}

	}

	/**
	 * 申请加入黑名单
	 *
	 * @param userId
	 * @param remark
	 * @return
	 */
	@RequestMapping(value = "saveBlackForm", method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveBlackForm(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "remark") String remark, @RequestParam(value = "applyId") String applyId,
			@RequestParam(value = "itemId") String itemId) {
		try {
			long checkInsert = riskBlacklistService.countInBlacklist(userId);
			if (checkInsert > 0) {
				return new WebResult("200", "该用户，已经在黑名单中。");
			} else {
				User user = UserUtils.getUser();
				int flag = riskBlacklistService.insertBlacklist(userId, remark, applyId, 0, user.getName());
				if (flag > 0) {
					return new WebResult("1", "提交成功！");
				}
				return new WebResult("200", "系统异常");
			}

		} catch (RuntimeException e) {
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 黑名单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "blackList")
	public String blackList(BlacklistOP op, Model model) {
		op.setStatus(0);
		model.addAttribute("BlacklistOP", op);
		model.addAttribute("page", riskBlacklistService.selectBlackList(op));
		return "modules/loan/blackList";
	}

	/**
	 * 黑名单审核页面
	 *
	 * @param id
	 * @param userId
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "blackListReview")
	public WebResult blackListReview(@RequestParam(value = "id") String id,
			@RequestParam(value = "userId") String userId, Model model) {
		try {

			BlacklistVO entity = riskBlacklistService.getById(id);
			model.addAttribute("reason", entity.getReason());

			CustUserVO vo = custUserService.getCustUserById(userId);
			model.addAttribute("userIdNo", vo.getIdNo());
			model.addAttribute("userRealName", vo.getRealName());
			model.addAttribute("userMobile", vo.getMobile());
			model.addAttribute("id", id);
			return new WebResult("1", "提交成功", model);

		} catch (RuntimeException e) {
			logger.error("查询黑名单详情异常：custId = " + userId + " userId = " + userId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询黑名单详情异常：custId = " + userId + " userId = " + userId, e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 黑名单审核
	 *
	 * @return
	 */
	@RequestMapping(value = "checkBlackList", method = RequestMethod.POST)
	@ResponseBody
	public WebResult checkBlackList(@RequestParam(value = "id") String id, @RequestParam(value = "status") int status) {
		try {
			long flag = 0;
			if (status == 2) { // 如果审核不通过就做删除操作
				flag = riskBlacklistService.deleteBlickList(id);
			} else {
				User user = UserUtils.getUser();
				flag = riskBlacklistService.updateBlackById(id, status, user.getName());
			}
			if (flag > 0) {
				return new WebResult("1", "审核成功！");
			} else {
				return new WebResult("200", "系统异常");
			}

		} catch (RuntimeException e) {
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 分配详情
	 */
	@ResponseBody
	@RequestMapping(value = "assignmentDetail")
	public WebResult allotmentDetail(@RequestParam(value = "itemId") String itemId) {
		try {
			List<CollectionAssignmentVO> list = collectionAssignmentService.getAllByRepayPlanItemId(itemId);
			AllotmentLogListVO vo = new AllotmentLogListVO();
			vo.setList(list);
			return new WebResult("1", "提交成功", vo);
		} catch (RuntimeException e) {
			logger.error("查询分配详情异常：itemId = " + itemId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询分配详情异常：itemId = " + itemId, e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 分配详情
	 */
	@ResponseBody
	@RequestMapping(value = "getOperator")
	public WebResult getOperator() {
		try {
			List<User> userList = UserUtils.getUserByRole("csy");
			return new WebResult("1", "提交成功", userList);
		} catch (RuntimeException e) {
			logger.error("查询催收员列表异常", e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询催收员列表异常", e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 催收分配
	 */
	@ResponseBody
	@RequestMapping(value = "doAllotment")
	public WebResult doAllotment(@Valid CollectionAssignmentOP op) {
		try {
			com.rongdu.loans.loan.option.CollectionAssignmentOP collectionAssignmentOP = createOP(op);
			collectionAssignmentService.doAllotment(collectionAssignmentOP);
			return new WebResult("1", "提交成功", null);
		} catch (RuntimeException e) {
			logger.error("催收分配异常：op = " + JsonMapper.getInstance().toJson(op), e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("催收分配异常：op = " + JsonMapper.getInstance().toJson(op), e);
			return new WebResult("99", "系统异常");
		}
	}

	private com.rongdu.loans.loan.option.CollectionAssignmentOP createOP(CollectionAssignmentOP op) {
		Date today = new Date();
		/*
		 * User user = UserUtils.get(op.getOperatorId()); if (user == null) {
		 * throw new RuntimeException("选择的催收员不存在"); }
		 */
		User loginUser = UserUtils.getUser();
		com.rongdu.loans.loan.option.CollectionAssignmentOP collectionAssignmentOP = new com.rongdu.loans.loan.option.CollectionAssignmentOP();
		collectionAssignmentOP.setId(op.getOperatorId());
		// collectionAssignmentOP.setName(user.getName());
		collectionAssignmentOP.setOperatorId(loginUser.getId());
		collectionAssignmentOP.setOperatorName(loginUser.getName());
		collectionAssignmentOP.setIdList(Arrays.asList(StringUtils.split(op.getIds(), "|")));

		// 今天24点
		if (StringUtils.equals(op.getTime(), "1")) {
			collectionAssignmentOP.setReturnType(ReturnTypeEnum.TIMING.getValue());
			collectionAssignmentOP.setReturnTime(DateUtils.getDayEnd(today));
		}
		// 明天24点
		if (StringUtils.equals(op.getTime(), "2")) {
			collectionAssignmentOP.setReturnType(ReturnTypeEnum.TIMING.getValue());
			collectionAssignmentOP.setReturnTime(DateUtils.getDayEnd(DateUtils.addDay(today, 1)));
		}
		// 永久分配
		if (StringUtils.equals(op.getTime(), "3")) {
			collectionAssignmentOP.setReturnType(ReturnTypeEnum.NOT.getValue());
		}
		return collectionAssignmentOP;
	}

	/**
	 * 保存催收记录
	 */
	@ResponseBody
	@RequestMapping(value = "saveRecord")
	public WebResult saveRecord(CollectionRecordOP op) {
		try {
			User loginUser = UserUtils.getUser();
			op.setOperatorId(loginUser.getId());
			op.setOperatorName(loginUser.getName());
			int result = collectionRecordService.save(op);

			/**
			 * 将催收记录重新放入缓存
			 */
			String cacheKey = "cuishou_detail_info_" + op.getItemId();
			CollectionDetailVO vo1 = (CollectionDetailVO) JedisUtils.getObject(cacheKey);
			List<CollectionRecordVO> list = collectionRecordService.list(op.getItemId());
			vo1.setRecordList(list);
			JedisUtils.setObject(cacheKey, vo1, Global.TWO_HOURS_CACHESECONDS);

			if (result == 0) {
				logger.error("保存催收记录异常：op = " + JsonMapper.getInstance().toJson(op));
				return new WebResult("99", "保存失败");
			}
			return new WebResult("1", "提交成功", null);
		} catch (RuntimeException e) {
			logger.error("保存催收记录异常：op = " + JsonMapper.getInstance().toJson(op), e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("保存催收记录异常：op = " + JsonMapper.getInstance().toJson(op), e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 催收记录
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public WebResult list(@RequestParam(value = "itemId") String itemId) {
		try {
			List<CollectionRecordVO> list = collectionRecordService.list(itemId);
			CollectionRecordListVO vo = new CollectionRecordListVO();
			vo.setList(list);
			return new WebResult("1", "提交成功", vo);
		} catch (RuntimeException e) {
			logger.error("查询催收记录异常：itemId = " + itemId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询催收记录异常：itemId = " + itemId, e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 催收详情
	 */
	@ResponseBody
	@RequestMapping(value = "detail")
	public WebResult detail(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "applyId") String applyId, @RequestParam(value = "thisTermId") String thisTermId) {
		try {
			String cacheKey = "cuishou_detail_info_" + thisTermId;
			CollectionDetailVO vo = (CollectionDetailVO) JedisUtils.getObject(cacheKey);
			if (vo == null) {
				RepayDetailListOP op = new RepayDetailListOP();
				op.setId(thisTermId);
				op.setUserId(userId);
				op.setStatus(0);
				op.setPageSize(Integer.MAX_VALUE);
				List<RepayDetailListVO> list = repayPlanItemService.repayDetailList(op).getList();

				List<ContactToCollectionVO> contactList = custUserService.getContactHisByApplyNo(applyId);

				vo = new CollectionDetailVO();
				vo.setResultList(getUrgentRecallResultVOList());
				vo.setContactList(contactList);
				vo.setDetailList(list);

				// 获取催收记录
				List<CollectionRecordVO> recordList = collectionRecordService.list(thisTermId);
				vo.setRecordList(recordList);

				// 通讯录信息--------begin--------------
				if (DateUtils.daysBetween(list.get(0).getRepayDate(), new Date()) > 1) {
					List<Map<String, Object>> contactConnectInfoList = reportService.getContactConnectInfo(applyId);
					vo.setContactConnect(contactConnectInfoList);
				}
				// List<FileInfoVO> contactFileList =
				// custUserService.getCustContactFile(userId);
				// vo.setFileInfoList(contactFileList);
				// 通讯录信息--------end--------------

				LoanApplySimpleVO loanApplyVo = loanApplyService.getLoanApplyById(applyId);

				boolean showContact = isShowContact(loanApplyVo.getLoanSuccCount(), list.get(0).getOverdue());
				vo.setShowContact(showContact);

				// 获取提醒
/*				RepayWarnVO warnvo = repayWarnService.getByRepayId(thisTermId);
				if (warnvo == null) {
					warnvo = new RepayWarnVO();
				}
				vo.setWarnInfo(warnvo);*/

				JedisUtils.setObject(cacheKey, vo, Global.TWO_HOURS_CACHESECONDS);
			}
			return new WebResult("1", "提交成功", vo);
		} catch (RuntimeException e) {
			logger.error("查询催收详情异常：custId = " + userId + " applyId = " + applyId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("查询催收详情异常：custId = " + userId + " applyId = " + applyId, e);
			return new WebResult("99", "系统异常");
		}
	}

	/**
	 * 新客户(succCont =0)逾期超过2天开始显示通讯录 老客户(succCont>0) 逾期超过14天开始显示通讯录
	 * 
	 * @param succCont
	 * @param overdue
	 * @return
	 */
	private boolean isShowContact(Integer succCont, Integer overdue) {
		if (succCont == null) {
			succCont = 0;
		}
		boolean flag = false;
		if (succCont == 0) {
			if (overdue > 2) {
				flag = true;
			}
		} else {
			if (overdue > 14) {
				flag = true;
			}
		}
		return flag;
	}

	private List<UrgentRecallResultVO> getUrgentRecallResultVOList() {
		List<UrgentRecallResultVO> list = new ArrayList<UrgentRecallResultVO>();
		for (UrgentRecallResult item : UrgentRecallResult.values()) {
			UrgentRecallResultVO vo = new UrgentRecallResultVO();
			vo.setValue(item.getValue());
			vo.setDesc(item.getDesc());
			list.add(vo);
		}
		return list;
	}

	/**
	 * @param @param
	 *            applyOP
	 * @param @param
	 *            model
	 * @return String 返回类型
	 * @Title: allotCollectionManage
	 * @Description: 分配催收人员列表
	 */
	@RequestMapping(value = "/allotCollectionManage")
	public String allotCollectionManage(@ModelAttribute(value = "applyOP") ApplyOP applyOP, Model model) {

		Map<String, Object> condition = new HashMap<String, Object>();

		String companyId = applyOP.getCompanyId();
		String userName = applyOP.getUserName();
		String allotFlag = applyOP.getAllotFlag();

		if (!StringUtils.isEmpty(companyId)) {
			condition.put("companyId", companyId.trim());
		}
		if (!StringUtils.isEmpty(userName)) {
			condition.put("userName", userName.trim());
		}
		if (!StringUtils.isEmpty(allotFlag)) {
			condition.put("allotFlag", allotFlag.trim());
		}
		List<String> inRole = new ArrayList<String>();
		inRole.add("jbcsy");
		inRole.add("csy_m2");
		condition.put("role", inRole);
		condition.put("cx", "催收");

		// 根据不同的条件查询不同公司催收员角色的人员列表
		List<Map<String, Object>> userList = systemService.getUserListByCondition(condition);

		// 根据不同条件查出拥有催收部门的催收公司
		List<Map<String, Object>> csCompanyList = systemService.getCSCompanyListByCondition(condition);

		model.addAttribute("userList", userList);
		model.addAttribute("csCompanyList", csCompanyList);
		return "modules/loan/allotCollectionManage";
	}

	/**
	 * @param @param
	 *            userId
	 * @return WebResult 返回类型
	 * @Title: allotCollectionAuthorization
	 * @Description: 催收分配开关权限管理
	 */
	@RequestMapping(value = "/allotCollectionAuthorization")
	@ResponseBody
	public WebResult allotCollectionAuthorization(@RequestParam(value = "userId") String userId) {
		User user = UserUtils.get(userId);
		try {

			if (user != null) {
				String allotFlag = user.getAllotFlag();

				if (!StringUtils.isEmpty(allotFlag)) {

					// allotFlag 0不可分配 1可分配 该方法是进行两者切换 其余状态认定是异常
					if (allotFlag.trim().equals("0")) {
						user.setAllotFlag("1");
						systemService.updateUserInfo(user);
						return new WebResult("1", "操作成功");
					} else if (allotFlag.trim().equals("1")) {
						user.setAllotFlag("0");
						systemService.updateUserInfo(user);
						return new WebResult("1", "操作成功");
					} else {
						logger.error("催收分配开关权限管理userId=" + userId, "开关状态不对");
						return new WebResult("99", "操作异常");
					}
				} else {
					logger.error("催收分配开关权限管理userId=" + userId, "开关状态不对");
					return new WebResult("99", "操作异常");
				}
			} else {
				logger.error("催收分配开关权限管理userId=" + userId, "该用户不存在");
				return new WebResult("99", "操作异常");
			}

		} catch (Exception e) {
			logger.error("催收分配开关权限管理userId=" + userId, e);
			return new WebResult("99", "操作异常");
		}
	}

	/**
	 * 暂停催收
	 */
	@ResponseBody
	@RequestMapping(value = "updateOverdue")
	public WebResult updateOverdue(@RequestParam(value = "overdueId") String overdueId,
			@RequestParam(value = "resultType") Integer resultType) {
		User user = UserUtils.getUser();
		logger.info("暂停催收--->{}--->{}--->{}", user.getId(), user.getName(), overdueId);
		try {

			int flag = overdueService.updateStopOverdue(overdueId, resultType, user.getName());
			if (flag > 0) {
				return new WebResult("1", "操作成功");
			} else {
				return new WebResult("99", "操作失败");
			}
		} catch (RuntimeException e) {
			logger.error("停催操作异常：overdueId = " + overdueId, e);
			return new WebResult("99", e.getMessage());
		} catch (Exception e) {
			logger.error("停催操作异常：overdueId = " + overdueId, e);
			return new WebResult("99", "系统异常");
		}
	}

	@RequestMapping(value = "/pushBackCount")
	public String pushBackCount(@ModelAttribute(value = "op") OverduePushBackOP op, Model model) {
		List<ChannelVO> chList = channelService.findAllChannel();
		StringBuffer chAllStr = new StringBuffer();
		for (ChannelVO ch : chList) {
			chAllStr.append("'").append(ch.getCid()).append("',");
		}
		chAllStr = new StringBuffer(chAllStr.substring(0, chAllStr.length() - 1));
		model.addAttribute("channels", chList);
		model.addAttribute("allChannel", chAllStr);
		model.addAttribute("op", op);
		if (StringUtils.isBlank(op.getEndTime())) {
			String dateStr = DateUtils.getDate();
			op.setEndTime(dateStr);
		}
		List<OverduePushBackVO> list = overdueService.getPushBackOverdue(op);
		model.addAttribute("list", list);
		return "modules/loan/pushBackOverdueCount";
	}

}
