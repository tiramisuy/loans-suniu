package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.tencent.service.OcrService;
import com.rongdu.loans.credit.tencent.service.TicketService;
import com.rongdu.loans.credit.tencent.vo.OcrResultVo;
import com.rongdu.loans.credit.tencent.vo.TicketVo;
import com.rongdu.loans.tencent.common.SignUtils;
import com.rongdu.loans.tencent.common.TencentConfig;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 腾讯身份证OCR识别-服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("ocrService")
public class OcrServiceImpl extends PartnerApiService implements OcrService{
	
	@Autowired
	private TicketService ticketService;
	
	/**
	 * 根据订单号，获取身份证OCR识别的结果
	 */
	@Override
	public OcrResultVo getOcrResult(String orderNo) {
		
		//配置参数
		String partnerId = TencentConfig.partner_id;
		String partnerName = TencentConfig.partner_name;
		String bizCode = TencentConfig.get_ocr_result_biz_code;
		String bizName = TencentConfig.get_ocr_result_biz_name;
		String url = TencentConfig.get_ocr_result_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		OcrResultVo vo = null;
		TicketVo signTicket = ticketService.getSignTicket();
		Map<String, String> params = new HashMap<String, String>();
        String appId = TencentConfig.appid;
        String ticket = signTicket.getTicket(); 
        String version = "1.0.0"; 
        String nonceStr = IdGen.uuid();
		//公共参数
		params.put("app_id", appId);
		params.put("version", version); 
		params.put("nonce", nonceStr);
		params.put("order_no", orderNo);
		params.put("get_file", "1"); 
				
		//生成签名
		List<String> values = new ArrayList<>();
		values.add(appId);
		values.add(orderNo);
		values.add(nonceStr);
		values.add(version);
		values.add(ticket);
		String sign = SignUtils.sha1Sign(values);
		params.put("sign", sign); 
		
		//拼接请求字符串
//		url = HttpUtils.makeQueryString(url, params);
		//发送请求
		vo = (OcrResultVo) getForJson(url, params,OcrResultVo.class,log);
		Map<String, String> result = vo.getResult();
		if (result!=null&&!result.isEmpty()) {
			//应答成功时
			if (vo!=null&&vo.isSuccess()) {	
				try {
					BeanUtils.populate(vo, result);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}		
			vo.setOrderNo(result.get("orderNo"));
			if (StringUtils.isBlank(vo.getFrontCode())&&StringUtils.isNotBlank(vo.getFrontPhoto())) {
				vo.setFrontCode("0");
			}
			if (StringUtils.isBlank(vo.getBackCode())&&StringUtils.isNotBlank(vo.getBackPhoto())) {
				vo.setBackCode("0");
			}
		}
		vo.setResult(null);
		return vo;

	}
	
	/**
	 *  根据订单号，获取身份证OCR识别的结果（增强版）
	 * @param frontPhotoOrderNo 身份证正面订单号
	 * @param backPhotoOrderNo 身份证反面订单号
	 * @return
	 */
	@Override
	public OcrResultVo getOcrResult(String frontPhotoOrderNo,String backPhotoOrderNo) {		
		OcrResultVo frontOcrResultVo = getOcrResult(frontPhotoOrderNo);
		OcrResultVo backOcrResultVo = getOcrResult(backPhotoOrderNo);
		if (frontOcrResultVo==null) {
			frontOcrResultVo = backOcrResultVo;
		}else {
			if (backOcrResultVo!=null) {
				frontOcrResultVo.setBackCode(backOcrResultVo.getBackCode());
				frontOcrResultVo.setBackPhoto(backOcrResultVo.getBackPhoto());
				frontOcrResultVo.setValidDate(backOcrResultVo.getValidDate());
				frontOcrResultVo.setAuthority(backOcrResultVo.getAuthority());
			}
		}
		return frontOcrResultVo;
	}
	
}
