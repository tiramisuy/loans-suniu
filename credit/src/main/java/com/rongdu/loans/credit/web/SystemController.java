package com.rongdu.loans.credit.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rongdu.common.config.XjdLifeCycle;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.mq.CommonMessage;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.credit.http.HttpUtils;
import com.rongdu.loans.credit.tencent.service.AccessTokenService;
import com.rongdu.loans.loan.option.ApplyListOP;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.vo.ApplyListVO;
import com.rongdu.loans.mq.MessageProductor;
import com.rongdu.loans.risk.entity.AutoApprove;
import com.rongdu.loans.risk.service.AutoApproveService;
import com.rongdu.loans.risk.service.CreditDataPersistenceService;
import com.rongdu.loans.risk.vo.AutoApproveVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 登录Controller
 * @author sunda
 * @version 2013-5-31
 */
@Controller
public class SystemController extends BaseController{
		
	

	@RequestMapping(value = "api/anon/test")
	public String test(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AccessTokenService accessTokenService = SpringContextHolder.getBean("accessTokenService");
//		AccessTokenVo vo = accessTokenService.getAccessToken();
		
//		TicketService service = SpringContextHolder.getBean("ticketService");
//		TicketVo vo = service.getNonceTicket("123456");		
//		
//		FaceVerifyService service = SpringContextHolder.getBean("faceVerifyService");
//		FaceVerifyVo vo =  service.getFaceVerifyResult("123456");
		
//		OcrService service = SpringContextHolder.getBean("ocrService");
//		OcrResultVo vo =  service.getOcrResult(request.getParameter("orderNo"));
//		String str = JsonMapper.toJsonString(vo);
//		
//		str = str.replaceAll("\"([^\"]{500,})\"", "\"\"");
//		System.out.println(str);
//		DeviceFingerprintServiceImpl service = new DeviceFingerprintServiceImpl();
//		DeviceInfoOP op = new DeviceInfoOP();
//		op.setSource("2");
//		op.setTokenKey("2dd38343-d4d1-4877-846e-f3117e48b776");
//		DeviceInfoVO vo = service.getDeviceInfo(op);

//		DeviceFingerprintServiceImpl service = new DeviceFingerprintServiceImpl();
//		DeviceContactOP op = new DeviceContactOP();
//		op.setSource("2");
//		op.setTokenKey("2dd38343-d4d1-4877-846e-f3117e48b776");
//		DeviceContactVO vo = service.getContactInfo(op);
//		BaiqishiServiceImpl service = new BaiqishiServiceImpl();
//		ReportDataOP op = new ReportDataOP();
//		op.setName("谭康升");
//		op.setMobile("13592778678");
//		op.setCertNo("452502197304125230");
//		ReportDataVO vo = service.getReportData(op);
//		renderJson(response, vo);
		CreditDataPersistenceService creditDataPersistenceService = SpringContextHolder.getBean("creditDataPersistenceService");
//		creditDataPersistenceService.saveBaiqishiReportFile("1020170815230645412744");
//		creditDataPersistenceService.saveBaiqishiDeviceInfoFile("1020170815230645412744");
//		creditDataPersistenceService.saveBaiqishiDeviceContactFile("1020170815230645412744");
		creditDataPersistenceService.saveBaiqishiMnoContactFile("1020170815230645412744");
		return null;
	}
	
	@RequestMapping(value = "api/anon/approve")
	public String approve(HttpServletRequest request,HttpServletResponse response) throws Exception {
		MessageProductor messageProductor = SpringContextHolder.getBean("messageProductor");
		String applyIds = request.getParameter("applyIds");
		if (StringUtils.isBlank(applyIds)){
			applyIds = "1020170815144700192395";
		}
		List<String> applyIdList = Arrays.asList(applyIds.split(","));
		AutoApproveVO vo = null;
		for (String applyId:applyIdList){
//			messageProductor.sendToPreAutoApproveQueue(applyId);
			vo = ((AutoApproveService)SpringContextHolder.getBean("autoApproveService")).approveXjd(applyId);
		}
		String msg = "已将"+applyIdList.size()+"消息推送到队列";
		renderJson(response, msg);
		return null;
	}

	@RequestMapping(value = "api/anon/getApplyList")
	public String getApplyList(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String statusString = request.getParameter("status");
		Page<ApplyListVO> page = new Page<ApplyListVO>();
		if (StringUtils.isNoneBlank(startTime,endTime,statusString)){
			page.setPageSize(2000);
			page.setPageNo(1);
			LoanApplyService loanApplyService = SpringContextHolder.getBean("loanApplyService");
			ApplyListOP applyListOp = new ApplyListOP();
			applyListOp.setApplyTimeStart(DateUtils.parseDate(startTime));
			applyListOp.setApplyTimeEnd(DateUtils.parseDate(endTime));
//			applyListOp.setCheckTimeStart(DateUtils.parseDate(startTime));
//			applyListOp.setCheckTimeEnd(DateUtils.parseDate(endTime));
			applyListOp.setStatus(Integer.parseInt(statusString));
			page = loanApplyService.getLoanApplyList(page,applyListOp);


		}
		renderJson(response, page);
		return null;
	}

	@RequestMapping(value = "api/anon/approveBatch")
	public String approveBatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AutoApproveService autoApproveService = SpringContextHolder.getBean("autoApproveService");
		Page<ApplyListVO> page = new Page<ApplyListVO>();
		String host = "http://loans-credit.rongdu.com";
//		String host = "http://localhost:8080";
		String reponseString = null;
		String startTime = "2017-09-26 00:00:00";
		String endTime = "2017-09-27 10:59:59";
		String statusString = String.valueOf(XjdLifeCycle.LC_APPLY_1);
//		String statusString = String.valueOf(XjdLifeCycle.LC_ARTIFICIAL_AUDIT_0);


		HttpUtils httpUtils = new HttpUtils();
		Map<String,String> params = new HashMap();
		params.put("startTime",startTime);
		params.put("endTime",endTime);
		params.put("status",statusString);
		reponseString = httpUtils.getForJson(host+"/api/anon/getApplyList",params);
		TypeReference<Page<ApplyListVO>> reference = new TypeReference<Page<ApplyListVO>>(){};
		page = (Page<ApplyListVO>) JsonMapper.fromJsonString(reponseString,reference);

		Map<String,ApplyListVO> map =  new LinkedHashMap<>();
		for (ApplyListVO vo:page.getList()){
			map.put(vo.getId(),vo);
		}

		AutoApprove vo = null;


		int tatol = map.size();
		int count = 0;
		logger.info("待审批的贷款申请总数：{}",tatol);
		for (String applyId:map.keySet()){
			count++;
			logger.info("正在自动审核：{}-{},{}，{},{}",tatol,count,applyId,map.get(applyId).getUserName(),map.get(applyId).getIdNo());
			reponseString = httpUtils.getForJson(host+"/api/anon/approve?applyIds="+applyId,null);
			logger.info("========自动审核结果：{}",reponseString);
			Thread.sleep(1000);
//			break;
		}
		renderJson(response, tatol);
		return null;
	}
	

}
