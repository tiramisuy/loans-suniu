package com.rongdu.loans.api.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.basic.service.CityService;
import com.rongdu.loans.basic.service.NotificationService;
import com.rongdu.loans.basic.service.ProvinceService;
import com.rongdu.loans.basic.service.StoreService;
import com.rongdu.loans.basic.vo.NotificationDetailVO;
import com.rongdu.loans.basic.vo.NotificationVO;
import com.rongdu.loans.basic.vo.StoreVO;
import com.rongdu.loans.common.Dict;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.option.PromotionCaseOP;
import com.rongdu.loans.loan.option.RepayPlanOP;
import com.rongdu.loans.loan.service.ContractService;
import com.rongdu.loans.loan.service.LoanRepayPlanService;
import com.rongdu.loans.loan.service.PromotionCaseService;
import com.rongdu.loans.loan.vo.CostingResultVO;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 基础服务Controller
 * 
 * @author sunda
 * @version 2017-06-19
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/basic")
public class BasicController extends BaseController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private PromotionCaseService promotionCaseService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private CityService cityService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private StoreService storeService;
	@Autowired
	private LoanRepayPlanService loanRepayPlanService;

	// @Autowired
	// private SchedulerService schedulerService;

	/**
	 * 获取公告信息 code y0511
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotifications", name = "获取公告信息")
	@ResponseBody
	public ApiResult getNotificationByProductId(@RequestParam(value = "productId", required = false) String productId)
			throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 获取公告信息
		NotificationDetailVO vo = notificationService.getValidNotificationByProductId(productId);
		result.setData(vo);
		return result;
	}

	/**
	 * 获取单个公告明细
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNotificationDetail", name = "获取单个公告明细")
	@ResponseBody
	public ApiResult getNotificationDetail(String notificationId) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		if (StringUtils.isBlank(notificationId)) {
			logger.error("公告id不能为空！");
			return result.set(ErrInfo.BAD_REQUEST);
		}
		// 获取公告信息
		NotificationVO vo = notificationService.getNotificationDetail(notificationId);
		result.setData(vo);
		return result;
	}

	/**
	 * 费率试算
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/costing", name = "费率试算")
	@ResponseBody
	public ApiResult costing(@Valid PromotionCaseOP param, Errors errors) throws Exception {
		// 参数异常处理
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 参数验证
		if (null == param.getApplyAmt()) {
			result.setCode("400");
			result.setMsg("申请金额不能为空");
			return result;
		}
		if (null == param.getApplyTerm()) {
			result.setCode("400");
			result.setMsg("申请期限不能为空");
			return result;
		}
		param.setChannelId(ChannelEnum.JUQIANBAO.getCode());
		// 获取公告信息
		CostingResultVO vo = promotionCaseService.Costing(param);
		if (null == vo || vo.getTotalInterest() == null) {
			result.setCode("FAIL");
			result.setMsg("申请金额或期限暂不支持");
			return result;
		}
		result.setData(vo);
		return result;
	}

	/**
	 * 获取放款成功记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMakeLoanRecords", name = "获取最近三天放款成功记录")
	@ResponseBody
	public ApiResult getMakeLoanRecords() throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 获取放款成功记录
		// List<String> list = contractService.getRecentThreeDaysRecordsStr();
		// if (null == list || list.size() == 0) {
		// result.set(ErrInfo.NOTFIND_MAKELOAN_RECORDS);
		// return result;
		// }
		// result.setData(list);
		return result;
	}

	/**
	 * 缓存清理
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cleanCache", name = "缓存清理")
	@ResponseBody
	public ApiResult cleanCache(String key) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		long rz = JedisUtils.del(key);
		result.setData(rz);
		return result;
	}

	/**
	 * 模糊清理缓存
	 * 
	 * @param key
	 * @param type
	 *            （1-前模糊匹配， 2-后模糊匹配， 3-前后模糊匹配）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDelCache")
	@ResponseBody
	public ApiResult batchDelCache(String key, Integer type) throws Exception {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		List<String> list = JedisUtils.batchDel(key, type);
		if (null == list || list.size() == 0) {
			result.set(ErrInfo.DEL_CACHE_FAIL);
			return result;
		}
		result.setData(list);
		return result;
	}

	/**
	 * 获取市、县列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectAddress", name = "获取市列表")
	@ResponseBody
	public ApiResult selectAddress(Integer pid) {
		// 初始化返回对象
		ApiResult result = new ApiResult();
		List list = new ArrayList();
		if (pid == null || pid < 0) {
			list = provinceService.getAllList();
			if (null == list || list.size() == 0) {
				result.set(ErrInfo.DEL_CACHE_FAIL);
				return result;
			}
		} else {
			list = cityService.getAllCityByPid(pid);
			if (null == list || list.size() == 0) {
				result.set(ErrInfo.DEL_CACHE_FAIL);
				return result;
			}
		}
		result.setData(list);
		return result;
	}

	/**
	 * 获取区域、门店列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectStore", name = "获取门店列表")
	@ResponseBody
	public ApiResult getStores(@RequestParam(value = "productId", required = false) String productId) {
		ApiResult result = new ApiResult();
		List<StoreVO> areaList = storeService.getAllArea();
		if (null == productId) {
			if (areaList != null) {
				for (StoreVO areaVO : areaList) {
					List<StoreVO> storeList = storeService.getAllStore(areaVO.getId(), LoanProductEnum.CCD.getId());
					for (StoreVO storeVO : storeList) {
						storeVO.setChildren(storeService.getAllGroup(storeVO.getId()));
					}
					areaVO.setChildren(storeList);
				}
			} else {
				result.setCode("FAIL");
				result.setMsg("暂无门店");
			}
		} else if (LoanProductEnum.LYFQ.getId().equals(productId)) {
			if (areaList != null) {
				for (StoreVO areaVO : areaList) {
					List<StoreVO> storeList = storeService.getAllStore(areaVO.getId(), LoanProductEnum.LYFQ.getId());
					areaVO.setChildren(storeList);
				}
			} else {
				result.setCode("FAIL");
				result.setMsg("暂无门店");
			}
		}
		result.setData(areaList);
		return result;
	}

	/**
	 * 获取基本信息数据字典
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectDict", name = "获取基本信息数据字典")
	@ResponseBody
	public ApiResult selectDict() {
		ApiResult result = new ApiResult();
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("marital", Dict.marriage);
		map.put("degree", Dict.education);
		map.put("indivMonthIncome", Dict.jobSalary);
		map.put("workCategory", Dict.jobCompanyType);
		map.put("workSize", Dict.jobCompanyScale);
		map.put("industry", Dict.jobIndustry);
		map.put("workYear", Dict.jobYears);
		map.put("house", Dict.haveHouse);
		map.put("houseLoan", Dict.houseLoan);
		map.put("car", Dict.haveCar);
		map.put("carLoan", Dict.carLoan);
		result.setData(map);
		return result;
	}

	//
	//
	// /**
	// * 逾期罚息费用计算
	// * @return
	// * @throws Exception
	// */
	// @RequestMapping(value = "/overdueCalcExec", name="逾期更新计算")
	// @ResponseBody
	// public ApiResult overdueDataCalcExec(@Valid AgreementOP param,
	// Errors errors, HttpServletRequest request) throws Exception {
	// // 初始化返回结果
	// ApiResult result = new ApiResult();
	// TaskResult exeResult = schedulerService.overdueDataCalc();
	// result.setMsg("成功更新："+exeResult.getSuccNum());
	// return result;
	// }

	/**
	 * 获取还款计划
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRepayPlan")
	@ResponseBody
	public ApiResult getRepayPlan(@Valid RepayPlanOP param, Errors errors) throws Exception {
		// 参数异常处理
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 参数验证
//		if (param.getRepayMethod() != 1 && param.getRepayMethod() != 4 && param.getRepayMethod() != 6) {
//			result.set(ErrInfo.BAD_REQUEST);
//			return result;
//		}
		Map<String, Object> repayPlan = loanRepayPlanService.getRepayPlan(param);
		result.setData(repayPlan);
		return result;
	}
	
	/**
	 * 获取所有一级公司
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllTopCompany")
	@ResponseBody
	public ApiResult getAllTopCompany(){
		// 初始化返回结果
		ApiResult result = new ApiResult();
		List<StoreVO> companyList = storeService.getAllTopCompany();
		result.setData(companyList);
		return result;
	}
	
	/**
	 * 根据公司id获取区域、门店列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getStoreByCompanyId", name = "获取门店列表")
	@ResponseBody
	public ApiResult getStoreByCompanyId(@RequestParam(value = "companyId", required = false) String companyId) {
		ApiResult result = new ApiResult();
		List<StoreVO> areaList = storeService.getAllArea();
		if (areaList != null) {
			for (StoreVO areaVO : areaList) {
				List<StoreVO> storeList = storeService.getStoreByAreaAndCompany(areaVO.getId(), companyId);
				for (StoreVO storeVO : storeList) {
					storeVO.setChildren(storeService.getAllGroup(storeVO.getId()));
				}
				areaVO.setChildren(storeList);
			}
		} else {
			result.setCode("FAIL");
			result.setMsg("暂无门店");
		}
		result.setData(areaList);
		return result;
	}
}
