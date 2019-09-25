package com.rongdu.loans.api.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.UploadParams;
import com.rongdu.common.http.RestTemplateUtils;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.api.common.BadRequestException;
import com.rongdu.loans.api.common.CommonUtils;
import com.rongdu.loans.api.common.ExtInterfaceType;
import com.rongdu.loans.api.common.LoginUtils;
import com.rongdu.loans.api.vo.FaceVerifyVO;
import com.rongdu.loans.api.vo.FileVO;
import com.rongdu.loans.api.vo.OcrVO;
import com.rongdu.loans.api.vo.TelOperatorLogOP;
import com.rongdu.loans.api.vo.TencentTicketVO;
import com.rongdu.loans.credit.baiqishi.service.ZhimaService;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultOP;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeResultVO;
import com.rongdu.loans.credit.baiqishi.vo.AuthorizeVO;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreOP;
import com.rongdu.loans.credit.baiqishi.vo.ZmScoreVO;
import com.rongdu.loans.credit.tencent.service.FaceVerifyService;
import com.rongdu.loans.credit.tencent.service.OcrService;
import com.rongdu.loans.credit.tencent.service.TicketService;
import com.rongdu.loans.credit.tencent.vo.FaceVerifyVo;
import com.rongdu.loans.credit.tencent.vo.OcrResultVo;
import com.rongdu.loans.credit.tencent.vo.TicketVo;
import com.rongdu.loans.cust.option.FaceRecogOP;
import com.rongdu.loans.cust.option.OcrOP;
import com.rongdu.loans.cust.option.SesameCreditOP;
import com.rongdu.loans.cust.option.TaoBaoAndXuexinOP;
import com.rongdu.loans.cust.option.TelOperatorOP;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.OperationLogService;
import com.rongdu.loans.loan.vo.ApplyIdVO;
import com.rongdu.loans.loan.vo.AuthenticationStatusVO;
import com.rongdu.loans.sys.web.ApiResult;

/**
 * 第三方相关处理Controller
 * 
 * @author likang
 * @version 2017-7-11
 */
@Controller
@RequestMapping(value = "${adminPath}/third")
public class ThirdPartyController extends BaseController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private OcrService OcrService;

	@Autowired
	private FaceVerifyService faceVerifyService;

	@Autowired
	private ZhimaService zhimaService;

	@Autowired
	private CustUserService custUserService;

	@Autowired
	private LoanApplyService loanApplyService;

	@Autowired
	private OperationLogService operationLogService;

	/**
	 * 获取腾讯NONCE ticket
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTencentTicket", name = "获取腾讯NONCE ticket")
	@ResponseBody
	public ApiResult getTencentTicket(String doType, HttpServletRequest request) throws Exception {

		// 截取userId
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 调用接口类型
		if (StringUtils.isBlank(doType)) {
			logger.error("调用接口类型不能为空！");
			return result.set(ErrInfo.BAD_REQUEST);
		}
		// 获取缓存中接口调用次数
		if (StringUtils.equals(doType, Global.TENCENT_OCR)) {
			Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.OCR.getValue());
			if (null != count && count > Global.OCR_DAY_LIMIT) {
				result.set(ErrInfo.DO_OVERRUN);
				return result;
			}
		} else if (StringUtils.equals(doType, Global.TENCENT_FACE)) {
			Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.FACE.getValue());
			if (null != count && count > Global.FACE_DAY_LIMIT) {
				result.set(ErrInfo.DO_OVERRUN);
				return result;
			}
		}
		// 获取Nonce ticket
		TicketVo ticketVo = ticketService.getNonceTicket(userId);
		if (null == ticketVo) {
			result.set(ErrInfo.TENCENT_GET_TICKET_FAIL);
			return result;
		} else if (ticketVo.isSuccess()) {
			// 设置返回对象
			TencentTicketVO tencentTicketVO = new TencentTicketVO();
			// appId
			tencentTicketVO.setAppId(ticketVo.getAppId());
			// 腾讯企业流水号
			tencentTicketVO.setBizSeqNo(ticketVo.getBizSeqNo());
			// 聚宝钱包业务流水号
			tencentTicketVO.setOrderNo(ticketVo.getOrderNo());
			// Ticket
			tencentTicketVO.setTicket(ticketVo.getTicket());
			result.setData(tencentTicketVO);
		} else {
			result.setCode("FAIL");
			result.setMsg(ticketVo.getMsg());
		}
		return result;
	}

	/**
	 * 腾讯ocr接口调用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doOcr", method = RequestMethod.POST, name = "ocr接口调用")
	@ResponseBody
	public ApiResult doOcr(@Valid OcrOP param, Errors errors, HttpServletRequest request) throws Exception {
		// 参数验证结果判断
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();

		/*
		 * if(StringUtils.equals(param.getSource(), "1")) {
		 * result.setCode("FAIL"); result.setMsg("升级维护中，请一周后再试"); return result;
		 * }
		 */

		// 调用ocr
		// 构造返回对象
		OcrVO ocrVO = new OcrVO();
		// 获取缓存中接口调用次数
		Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.OCR.getValue());
		if (null == count) {
			count = 1;
		} else {
			count++;
		}
		// 更新缓存中接口调用次数
		CommonUtils.setCountCache(userId, ExtInterfaceType.OCR.getValue(), count);
		// 用户id
		param.setUserId(userId);
		// 调用次数
		param.setCount(count);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);

		// TODO
		// 调用腾讯接口获取外部数据
		// OcrResultVo ocrResultVO =
		// OcrService.getOcrResult(param.getOrderNo());
		OcrResultVo ocrResultVO = null;
		if (null != param.getOrderNo()) {
			ocrResultVO = OcrService.getOcrResult(param.getOrderNo());
		} else if (null != param.getFrontPhotoOrderNo() && null != param.getBackPhotoOrderNo()) {
			ocrResultVO = OcrService.getOcrResult(param.getFrontPhotoOrderNo(), param.getBackPhotoOrderNo());
		}
		if (null == ocrResultVO) {
			result.set(ErrInfo.TENCENT_DO_FAIL);
			return result;
		} else if (!ocrResultVO.isSuccess()) {
			result.setCode("FAIL");
			result.setMsg(ocrResultVO.getMsg());
			return result;
		} else if (null == ocrResultVO.getFrontCode()
				|| !StringUtils.equals(Global.TENCENT_OCR_SCAN_OK, ocrResultVO.getFrontCode())) {
			result.set(ErrInfo.OCR_IDCARD_FRONT_FAIL);
			return result;
		} else if (null == ocrResultVO.getBackCode()
				|| !StringUtils.equals(Global.TENCENT_OCR_SCAN_OK, ocrResultVO.getBackCode())) {
			result.set(ErrInfo.OCR_IDCARD_BACK_FAIL);
			return result;
		}
		// 身份证姓名
		param.setName(StringUtils.isNotBlank(param.getName()) ? param.getName() : ocrResultVO.getName());
		// 身份证姓名
		param.setIdcard(ocrResultVO.getIdcard());
		// 民族
		param.setNation(ocrResultVO.getNation());
		// 性别
		param.setSex(ocrResultVO.getSex());
		// 户籍地址
		param.setAddress(ocrResultVO.getAddress());
		// 身份证有效期
		param.setValidDate(ocrResultVO.getValidDate());
		// 发证机关
		param.setAuthority(ocrResultVO.getAuthority());
		// 出生日期
		param.setBirth(ocrResultVO.getBirth());

		// 后台相关处理
		int saveRz = custUserService.saveDoOcr(param);
		if (saveRz == 1) {
			ocrVO.setDoResult(saveRz);
		} else {
			result.set(ErrInfo.OCR_STA_UPDATE_FAIL);
			return result;
		}
		long t1 = System.currentTimeMillis();
		// 身份证正面上传
		UploadParams frontIdcard = new UploadParams();
		frontIdcard.setUserId(userId);
		frontIdcard.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		frontIdcard.setIp(ip);
		frontIdcard.setSource(param.getSource());
		FileVO front = CommonUtils.uploadBase64Image(ocrResultVO.getFrontPhoto(), frontIdcard);
		// 身份证反面上传
		UploadParams backIdcard = new UploadParams();
		backIdcard.setUserId(userId);
		backIdcard.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
		backIdcard.setIp(ip);
		backIdcard.setSource(param.getSource());
		FileVO back = CommonUtils.uploadBase64Image(ocrResultVO.getBackPhoto(), backIdcard);
		logger.debug("uploadImage cost time:[{}]", (System.currentTimeMillis() - t1));
		if (null != front && null != front.getUrl()) {
			ocrVO.setFrontIDUrl(front.getUrl());
		} else {
			ocrVO.setFrontIDUrl("");
		}
		if (null != back && null != back.getUrl()) {
			ocrVO.setBackIDUrl(back.getUrl());
		} else {
			ocrVO.setFrontIDUrl("");
		}
		// 清理用户信息缓存，以便更新
		LoginUtils.cleanCustUserInfoCache(userId);
		result.setData(ocrVO);
		return result;
	}

	/**
	 * 腾讯人脸识别接口调用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doFaceRecognition", method = RequestMethod.POST, name = "人脸识别接口调用")
	@ResponseBody
	public ApiResult doFaceRecognition(@Valid FaceRecogOP param, Errors errors, HttpServletRequest request)
			throws Exception {

		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 调用人脸识别
		// 获取缓存中接口调用次数
		Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.FACE.getValue());
		if (null == count) {
			count = 1;
		} else {
			count++;
		}
		// 更新缓存中接口调用次数
		CommonUtils.setCountCache(userId, ExtInterfaceType.FACE.getValue(), count);
		param.setUserId(userId);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);
		// TODO
		int saveRz = custUserService.saveDoFaceRecognition(param);

		long t1 = System.currentTimeMillis();
		FaceVerifyVo faceVerifyVo = faceVerifyService.getFaceVerifyResult(param.getOrderNo());
		// 人脸识别视频上传
		UploadParams faceVerify = new UploadParams();
		faceVerify.setUserId(userId);
		faceVerify.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		faceVerify.setIp(ip);
		faceVerify.setSource(param.getSource());
		FileVO video = CommonUtils.uploadBase64Video(faceVerifyVo.getVideo(), faceVerify);
		logger.debug("uploadVideo cost time:[{}]", (System.currentTimeMillis() - t1));

		// 人脸识别照片
		UploadParams faceImage = new UploadParams();
		faceImage.setUserId(userId);
		faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		faceImage.setIp(ip);
		faceImage.setSource(param.getSource());
		FileVO image = CommonUtils.uploadBase64Image(faceVerifyVo.getPhoto(), faceImage);

		// 返回对象设置
		FaceVerifyVO faceVo = new FaceVerifyVO();
		faceVo.setDoResult(saveRz);
		if (null != image && null != image.getUrl()) {
			faceVo.setImageUrl(image.getUrl());
		} else {
			faceVo.setImageUrl("");
		}
		if (null != video && null != video.getUrl()) {
			faceVo.setVideoUrl(video.getUrl());
		} else {
			faceVo.setVideoUrl("");
		}
		result.setData(faceVo);
		return result;
	}

	/**
	 * 电信运营商认证
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doTelOperator", method = RequestMethod.POST, name = "电信运营商认证")
	@ResponseBody
	public ApiResult doTelOperator(@Valid TelOperatorOP param, Errors errors, HttpServletRequest request)
			throws Exception {

		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 保存电信运营商认证结果
		// // 获取缓存中接口调用次数
		// Integer count = CommonUtils.getCountCache(
		// userId, ExtInterfaceType.TEL_OPERATOR.getValue());
		// if(null == count) {
		// count = 1;
		// } else if(count > Global.OPERATOR_DAY_LIMIT){
		// result.set(ErrInfo.DO_OVERRUN);
		// return result;
		// } else {
		// count++;
		// }
		// // 更新缓存中接口调用次数
		// CommonUtils.setCountCache(
		// userId, ExtInterfaceType.TEL_OPERATOR.getValue(), count);
		param.setUserId(userId);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);

		// 保存认证状态
		int saveRz = custUserService.saveDoTelOperator(param);
		if (saveRz < 1) {
			return result.set(ErrInfo.OPERATOR_STA_UPDATE_FAIL);
		}
		result.setData(saveRz);
		return result;
	}

	/**
	 * 芝麻信用认证授权
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doSesameCredit", method = RequestMethod.POST, name = "芝麻信用认证授权")
	@ResponseBody
	public ApiResult doSesameCredit(@Valid AuthorizeOP param, Errors errors, HttpServletRequest request)
			throws Exception {

		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 调用芝麻信息授权接口
		// 获取缓存中接口调用次数
		Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.SESAME_CREDIT.getValue());
		if (null == count) {
			count = 1;
		} else if (count > Global.SESAME_DAY_LIMIT) {
			result.set(ErrInfo.DO_OVERRUN);
			return result;
		} else {
			count++;
		}
		// 更新缓存中接口调用次数
		CommonUtils.setCountCache(userId, ExtInterfaceType.SESAME_CREDIT.getValue(), count);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);
		// 获取用户信息
		CustUserVO cust = LoginUtils.getCustUserInfo(userId);
		if (null != cust) {
			param.setIdNo(cust.getIdNo());
			param.setName(cust.getRealName());
		} else {
			CustUserVO custUserVO = custUserService.getCustUserById(userId);
			if (null != custUserVO && null != custUserVO.getIdNo() && null != custUserVO.getRealName()) {
				param.setIdNo(custUserVO.getIdNo());
				param.setName(custUserVO.getRealName());
				// 更新缓存中的用户信息
				LoginUtils.cacheCustUserInfo(custUserVO);
			} else {
				result.set(ErrInfo.USERINFO_INCOMPLETE);
				return result;
			}
		}
		// 芝麻信用授权
		AuthorizeVO authorizeVO = zhimaService.authorize(param);
		if (null == authorizeVO || null == authorizeVO.getAuthInfoUrl()) {
			result.set(ErrInfo.SESAME_DO_FAIL);
			return result;
		}
		result.setData(authorizeVO);
		return result;
	}

	/**
	 * 更新芝麻信用认证状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refreshSesameCreditStatus", method = RequestMethod.POST, name = "更新芝麻信用认证状态")
	@ResponseBody
	public ApiResult refreshSesameCreditStatus(@Valid AuthorizeResultOP param, Errors errors,
			HttpServletRequest request) throws Exception {

		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);
		if (null == param.getOpenId()) {
			// 获取用户信息
			CustUserVO cust = LoginUtils.getCustUserInfo(userId);
			if (null != cust) {
				param.setIdNo(cust.getIdNo());
				param.setName(cust.getRealName());
			}
		}

		// 调用记录参数对象
		SesameCreditOP sesameCreditOP = new SesameCreditOP();
		sesameCreditOP.setUserId(userId);
		sesameCreditOP.setIp(ip);
		sesameCreditOP.setSource(param.getSource());

		// 查询芝麻信用授权结果
		AuthorizeResultVO rzVO = zhimaService.getAuthorizeResult(param);
		if (null == rzVO || !rzVO.isAuthorized()) {
			sesameCreditOP.setAuthorized(rzVO.isAuthorized());
			custUserService.saveDoSesameCredit(sesameCreditOP);
			result.set(ErrInfo.SESAME_AUTH_FAIL);
			return result;
		}
		// 查询芝麻信用分
		ZmScoreOP zmScoreOP = new ZmScoreOP();
		zmScoreOP.setIp(ip);
		zmScoreOP.setUserId(userId);
		zmScoreOP.setSource(param.getSource());
		zmScoreOP.setOpenId(rzVO.getOpenId());
		ZmScoreVO zmScoreVO = zhimaService.getZmScore(zmScoreOP);
		if (null != zmScoreVO) {
			sesameCreditOP.setZmScore(zmScoreVO.getZmScore());
			sesameCreditOP.setFlowNo(zmScoreVO.getFlowNo());
			sesameCreditOP.setBizNo(zmScoreVO.getBizNo());
		}
		sesameCreditOP.setAuthorized(rzVO.isAuthorized());
		int rz = custUserService.saveDoSesameCredit(sesameCreditOP);
		if (rz == 0) {
			result.set(ErrInfo.SESAME_STA_UPDATE_FAIL);
			return result;
		}
		// 补充查询日期
		zmScoreVO.setQueryDate(DateUtils.getDate());
		result.setData(zmScoreVO);
		return result;
	}

	/**
	 * 获取申请编号
	 * 
	 * @param userId
	 * @return
	 */
	private String getApplyId(String userId) {
		// 生成申请编号
		ApplyIdVO applyIdVO = loanApplyService.getApplyId(userId);
		if (null != applyIdVO) {
			return applyIdVO.getApplyId();
		}
		return null;
	}

	/**
	 * 商汤ocr接口调用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doLinkFaceOcr", method = RequestMethod.POST, name = "linkFaceOcr接口调用")
	@ResponseBody
	public ApiResult doLinkFaceOcr(@Valid OcrOP param, Errors errors, HttpServletRequest request) throws Exception {
		// 参数验证结果判断
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		// 参数验证结果判断
		String userId = request.getHeader("userId");
		// 初始化返回结果
		ApiResult result = new ApiResult();

		// 调用ocr
		// 构造返回对象
		OcrVO ocrVO = new OcrVO();
		// 获取缓存中接口调用次数
		Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.OCR.getValue());

		if (null != count && count > Global.OCR_DAY_LIMIT) {
			result.set(ErrInfo.DO_OVERRUN);
			return result;
		}

		if (null == count) {
			count = 1;
		} else {
			count++;
		}

		// 更新缓存中接口调用次数
		CommonUtils.setCountCache(userId, ExtInterfaceType.OCR.getValue(), count);
		// 用户id
		param.setUserId(userId);
		// 调用次数
		param.setCount(count);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);

		// 后台相关处理
		// 检查身份证重复绑定code y0508
		boolean isRepeat = custUserService.isRepeatByIdNo(param.getIdcard());
		if (isRepeat) {
			result.set(ErrInfo.OCR_IDCARD_EXISTS);
			return result;
		}
		int saveRz = custUserService.saveDoOcr(param);
		if (saveRz == 1) {
			ocrVO.setDoResult(saveRz);

			// 人脸无法使用时放开，可用时屏蔽
			// if("1".equals(param.getSource())) {
			// FaceRecogOP param1 = new FaceRecogOP();
			// param1.setUserId(userId);
			// param1.setIp("20180914");
			// param1.setSource("1");
			// custUserService.saveDoFaceRecognition(param1);
			// }
		} else {
			result.set(ErrInfo.OCR_STA_UPDATE_FAIL);
			return result;
		}
		long t1 = System.currentTimeMillis();
		// 身份证正面上传
		UploadParams frontIdcard = new UploadParams();
		frontIdcard.setUserId(userId);
		frontIdcard.setBizCode(FileBizCode.FRONT_IDCARD.getBizCode());
		frontIdcard.setIp(ip);
		frontIdcard.setSource(param.getSource());
		FileVO front = CommonUtils.uploadBase64Image(param.getLinkFaceFrontPhoto(), frontIdcard);
		// 身份证反面上传
		UploadParams backIdcard = new UploadParams();
		backIdcard.setUserId(userId);
		backIdcard.setBizCode(FileBizCode.BACK_IDCARD.getBizCode());
		backIdcard.setIp(ip);
		backIdcard.setSource(param.getSource());
		FileVO back = CommonUtils.uploadBase64Image(param.getLinkFaceBackPhoto(), backIdcard);
		logger.debug("uploadImage cost time:[{}]", (System.currentTimeMillis() - t1));
		if (null != front && null != front.getUrl()) {
			ocrVO.setFrontIDUrl(front.getUrl());
		} else {
			ocrVO.setFrontIDUrl("");
		}
		if (null != back && null != back.getUrl()) {
			ocrVO.setBackIDUrl(back.getUrl());
		} else {
			ocrVO.setFrontIDUrl("");
		}
		// 清理用户信息缓存，以便更新
		LoginUtils.cleanCustUserInfoCache(userId);
		result.setData(ocrVO);
		return result;
	}

	/**
	 * 商汤人脸识别接口调用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doLinkFaceLive", method = RequestMethod.POST, name = "商汤人脸识别接口调用")
	@ResponseBody
	public ApiResult doLinkFaceLive(@RequestParam Map<String, String> linkFace, HttpServletRequest request)
			throws Exception {
		String userId = request.getHeader("userId");
		String requestId = linkFace.get("requestId");
		String status = linkFace.get("status");
		float hackScore = StringUtils.isNotBlank(linkFace.get("hackScore"))
				? Float.parseFloat(linkFace.get("hackScore"))
				: 0;
		float verifyScore = StringUtils.isNotBlank(linkFace.get("verifyScore"))
				? Float.parseFloat(linkFace.get("verifyScore"))
				: 0;
		String livenessDataId = linkFace.get("livenessDataId");
		String linkFaceLivePhoto = linkFace.get("linkFaceLivePhoto");
		String source = linkFace.get("source");

		logger.info("商汤人脸识别:userId:{},requestId:{},status:{},livenessDataId:{},hackScore:{},verifyScore:{}", userId,
				requestId, status, livenessDataId, hackScore, verifyScore);

		// 初始化返回结果
		ApiResult result = new ApiResult();
		if (!"OK".equals(status)) {
			result.set(ErrInfo.FACE_PHOTO_UPLOAD_FAIL);
			return result;
		}
		// Hackhack_score阈值为0.98，大于0.98是hack行为，小于等于0.98是正常活人。人脸置信度，一般是0.7
		if (hackScore > Global.HACK_SCORE || verifyScore < Global.VERIFY_SCORE) {
			result.set(ErrInfo.FACE_PHOTO_UPLOAD_FAIL);
			return result;
		}
		// 获取缓存中接口调用次数
		Integer count = CommonUtils.getCountCache(userId, ExtInterfaceType.FACE.getValue());
		if (null == count) {
			count = 1;
		} else if (count > Global.FACE_DAY_LIMIT) {
			result.set(ErrInfo.DO_OVERRUN);
			return result;
		} else {
			count++;
		}
		// 更新缓存中接口调用次数
		CommonUtils.setCountCache(userId, ExtInterfaceType.FACE.getValue(), count);

		FaceRecogOP param = new FaceRecogOP();
		param.setUserId(userId);
		// 截取ip
		String ip = Servlets.getIpAddress(request);
		param.setIp(ip);
		param.setSource(source);
		// TODO
		int saveRz = custUserService.saveDoFaceRecognition(param);

		// 人脸识别照片
		UploadParams faceImage = new UploadParams();
		faceImage.setUserId(userId);
		faceImage.setBizCode(FileBizCode.FACE_VERIFY.getBizCode());
		faceImage.setIp(ip);
		faceImage.setSource(param.getSource());
		FileVO image = CommonUtils.uploadBase64Image(linkFaceLivePhoto, faceImage);
		logger.debug("uploadImg cost time:[{}]", (System.currentTimeMillis() - System.currentTimeMillis()));
		// 返回对象设置
		FaceVerifyVO faceVo = new FaceVerifyVO();
		faceVo.setDoResult(saveRz);
		if (null != image && null != image.getUrl()) {
			faceVo.setImageUrl(image.getUrl());
		} else {
			faceVo.setImageUrl("");
		}
		result.setData(faceVo);
		return result;
	}

	/**
	 * 获取签约url
	 */
	@RequestMapping(value = "/getSignUrl", name = "获取签约url")
	@ResponseBody
	public ApiResult getSignUrl(HttpServletRequest request) throws Exception {
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");
		if (StringUtils.isBlank(userId)) {
			result.set(ErrInfo.BAD_REQUEST);
			return result;
		}
		String productId = request.getParameter("productId");
		// 查询认证流程结果
		AuthenticationStatusVO vo = operationLogService.getAuthenticationStatus(userId, productId);
		if (null != vo && vo.getSignStatus() == AuthenticationStatusVO.YES) {
			result.setCode("FAIL");
			result.setMsg("已签约成功");
			return result;
		}

		Map<String, String> map = new HashMap<String, String>();
		// 查询用户信息
		CustUserVO custUser = custUserService.getCustUserById(userId);
		if (null != custUser && StringUtils.isNotBlank(custUser.getAccountId())) {
			map.put("signUrl", Global.getConfig("toufuli_sign_url") + "?Uid=" + custUser.getAccountId());
		} else {
			result.setCode("FAIL");
			result.setMsg("未开户绑卡");
			return result;
		}
		result.setData(map);
		return result;
	}

	/**
	 * 获取投复利懒猫开户链接
	 * 
	 * @param isNewUser
	 *            是否是开存管新客户
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDepositUrl", name = "获取签约url")
	@ResponseBody
	public ApiResult getDepositUrl(Integer isNewUser, HttpServletRequest request) throws Exception {
		ApiResult result = new ApiResult();
		String userId = request.getHeader("userId");
		if (StringUtils.isBlank(userId)) {
			result.set(ErrInfo.BAD_REQUEST);
			return result;
		}
		String productId = request.getParameter("productId");
		// 查询认证流程结果
		AuthenticationStatusVO vo = operationLogService.getAuthenticationStatus(userId, productId);
		if (null != vo && vo.getSignStatus() == AuthenticationStatusVO.YES) {
			result.setCode("FAIL");
			result.setMsg("开通存管账户成功");
			return result;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "0");
		// 查询用户信息
		CustUserVO custUser = custUserService.getCustUserById(userId);
		if (null != custUser && StringUtils.isNotBlank(custUser.getAccountId())) {
			if (null != isNewUser && 0 == isNewUser) { // 新客户
				map.put("depositUrl", Global.getConfig("toufuli_deposit_url") + "?uid=" + custUser.getAccountId());
			} else {
				Map<String, String> params = new HashMap<String, String>();
				params.put("uid", custUser.getAccountId());
				logger.info("获取投复利存管url接口：uid={}", custUser.getAccountId());
				String json = RestTemplateUtils.getInstance().postForJson(Global.getConfig("toufuli_isActive_url"),
						params);
				logger.info("获取投复利存管url接口返回结果:result={}", json);
				Map<String, String> mapData = (Map<String, String>) JsonMapper.fromJsonString(json, Map.class);
				if (null != mapData) {
					if ("0".equals(mapData.get("openAccount"))) {// 新客户开户
						map.put("depositUrl",
								Global.getConfig("toufuli_deposit_url") + "?uid=" + custUser.getAccountId());
					} else if ("20".equals(mapData.get("openAccount"))) {// 激活
						map.put("depositUrl",
								Global.getConfig("toufuli_acive_url") + "?uid=" + custUser.getAccountId());
					} else if ("1".equals(mapData.get("openAccount"))) {
						map.put("status", "1");
						custUserService.saveDoDepositOperator(custUser.getAccountId());
					}
				} else {
					map.put("depositUrl", Global.getConfig("toufuli_deposit_url") + "?uid=" + custUser.getAccountId());
				}
			}
		} else {
			result.setCode("FAIL");
			result.setMsg("未绑卡");
			return result;
		}
		result.setData(map);
		return result;
	}

	/**
	 * 更新信用卡认证信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveCreditStatus", method = RequestMethod.POST, name = "更新信用卡认证状态信息")
	@ResponseBody
	public ApiResult saveCreditStatus(@Valid TaoBaoAndXuexinOP param, HttpServletRequest request) {
		// 初始化返回结果
		ApiResult result = new ApiResult();
		String userId = param.getUserId();
		if (param == null || StringUtils.isBlank(userId)) {
			result.setCode("400");
			result.setMsg("用户id不能为空");
			return result;
		}
		// // 获取缓存中接口调用次数
		// Integer count = CommonUtils.getCountCache(userId,
		// ExtInterfaceType.CREDIT_CARD.getValue());
		// if (null == count) {
		// count = 1;
		// } else if (count > Global.CREDIT_CARD_DAY_LIMIT) {
		// result.set(ErrInfo.DO_OVERRUN);
		// return result;
		// } else {
		// count++;
		// }
		// // 更新缓存中接口调用次数
		// CommonUtils.setCountCache(userId,
		// ExtInterfaceType.CREDIT_CARD.getValue(), count);

		if (!StringUtils.equals(TaoBaoAndXuexinOP.DO_OPERATOR_SUCC, param.getDoOperatorResult())) {
			result.setCode("FAIL");
			result.setMsg("信用卡认证失败");
			return result;
		}

		// 缓存认证信息30秒，待后台魔蝎回调更新认证状态，回调前为认证中状态
		// String cacheKey = "CREDIT_CARD_OPERATION_" + userId;
		// JedisUtils.set(cacheKey, "ing", 30);

		// // 更新信用卡认证状态
		// int logRz = loanApplyService.updateAuthStatus(param.getUserId(),
		// status);
		// if (logRz == 0) {
		// result.setCode("FAIL");
		// result.setMsg("更新信用卡认证状态失败");
		// }
		return result;
	}

	/**
	 * 电信运营商认证记录日志
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doTelOperatorLog", method = RequestMethod.POST, name = "电信运营商认证记录日志")
	@ResponseBody
	public ApiResult doTelOperatorLog(@Valid TelOperatorLogOP param, Errors errors, HttpServletRequest request)
			throws Exception {

		// 参数验证结果判断
		String userId = request.getHeader("userId");
		logger.info("{}-{}-{}", "运营商认证日志", userId, JsonMapper.toJsonString(param));
		// 初始化返回结果
		ApiResult result = new ApiResult();
		// 记录日志
		return result;
	}
}
