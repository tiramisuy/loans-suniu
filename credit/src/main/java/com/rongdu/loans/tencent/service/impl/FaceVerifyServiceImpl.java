/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.tencent.service.FaceVerifyService;
import com.rongdu.loans.credit.tencent.service.TicketService;
import com.rongdu.loans.credit.tencent.vo.FaceVerifyVo;
import com.rongdu.loans.credit.tencent.vo.TicketVo;
import com.rongdu.loans.tencent.common.SignUtils;
import com.rongdu.loans.tencent.common.TencentConfig;
import com.rongdu.loans.tencent.manager.FaceVerifyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 腾讯人脸验证-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("faceVerifyService")
public class FaceVerifyServiceImpl  extends PartnerApiService implements FaceVerifyService{
	
	/**
 	* 腾讯人脸验证-实体管理接口
 	*/
	@Autowired
	private FaceVerifyManager faceVerifyManager;
	
	@Autowired
	private TicketService ticketService;
	
	@Override
	public FaceVerifyVo getFaceVerifyResult(String orderNo) {
		
		//配置参数
		String partnerId = TencentConfig.partner_id;
		String partnerName = TencentConfig.partner_name;
		String bizCode = TencentConfig.face_verify_biz_code;
		String bizName = TencentConfig.face_verify_biz_name;
		String url = TencentConfig.face_verify_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		FaceVerifyVo vo = null;
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
		String sign = SignUtils.sign(values);
		params.put("sign", sign); 
		
		//拼接请求字符串
//		url = HttpUtils.makeQueryString(url, params);
		//发送请求
		vo = (FaceVerifyVo) getForJson(url, params,FaceVerifyVo.class,log);

		Map<String, String> result = vo.getResult();
		if (result!=null&&!result.isEmpty()) {
			//应答成功时
			if (vo!=null&&vo.isSuccess()) {
				vo.setIdNo(result.get("idNo"));
				vo.setIdType(result.get("idType"));
				vo.setName(result.get("name"));
				vo.setPhoto(result.get("photo"));
				vo.setVideo(result.get("video"));			
			}		
			vo.setOrderNo(result.get("orderNo"));
		}
		vo.setResult(null);
		return vo;
	}
	
}