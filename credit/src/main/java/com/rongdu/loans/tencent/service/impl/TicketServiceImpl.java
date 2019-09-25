package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.tencent.service.AccessTokenService;
import com.rongdu.loans.credit.tencent.service.TicketService;
import com.rongdu.loans.credit.tencent.vo.AccessTokenVo;
import com.rongdu.loans.credit.tencent.vo.TicketVo;
import com.rongdu.loans.tencent.common.TencentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取腾讯Ticket-服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("ticketService")
public class TicketServiceImpl extends PartnerApiService implements TicketService{
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Override
	public TicketVo getSignTicket() {
		
		//配置参数
		String partnerId = TencentConfig.partner_id;
		String partnerName = TencentConfig.partner_name;
		String bizCode = TencentConfig.sign_ticket_biz_code;
		String bizName = TencentConfig.sign_ticket_biz_name;
		String url = TencentConfig.api_ticket_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		TicketVo vo = null;
		vo = (TicketVo) JedisUtils.getObject("tencentSignTicket");
		if (vo == null) {
			AccessTokenVo token = accessTokenService.getAccessToken();
			Map<String, String> params = new HashMap<String, String>();
			//公共参数
			params.put("app_id", TencentConfig.appid);
			params.put("access_token", token.getAccessToken());
			params.put("type", "SIGN");
			params.put("version", "1.0.0");   
			//拼接请求字符串
//			url = HttpUtils.makeQueryString(url, params);
			//发送请求
			vo = (TicketVo) getForJson(url, params,TicketVo.class,log);
			//将tickets数组中的值取出，设置到ticket，expireTime，expireIn
			List<Map<String, String>> tickets = vo.getTickets();
			if (tickets!=null&&!tickets.isEmpty()) {
				Map<String, String> map = tickets.get(0);
				vo.setTicket(map.get("value"));
				vo.setExpireIn(Integer.parseInt(map.get("expire_in")));
				vo.setExpireTime(map.get("expire_time"));
				vo.setTickets(null);
			}
			//应答成功时，缓存Token
			if (vo!=null&&vo.isSuccess()) {
				if(vo.getExpireIn()>600){
					JedisUtils.setObject("tencentSignTicket", vo , vo.getExpireIn()-600);	
				}
				//JedisUtils.setObject("tencentSignTicket", vo , 3000);
			}		
			return vo;
		}else {
			return vo;
		}

	}

	@Override
	public TicketVo getNonceTicket(String userId) {
		//配置参数
		String partnerId = TencentConfig.partner_id;
		String partnerName = TencentConfig.partner_name;
		String bizCode = TencentConfig.nonce_ticket_biz_code;
		String bizName = TencentConfig.nonce_ticket_biz_code;
		String url = TencentConfig.api_ticket_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		TicketVo vo = null;
		AccessTokenVo token = accessTokenService.getAccessToken();
		Map<String, String> params = new HashMap<String, String>();
		
		//公共参数
		params.put("app_id", TencentConfig.appid);
		params.put("access_token", token.getAccessToken());
		params.put("type", "NONCE");
		params.put("version", "1.0.0"); 
		params.put("user_id", userId); 
		
		//发送请求
		vo = (TicketVo) getForJson(url, params,TicketVo.class,log);
		//将tickets数组中的值取出，设置到ticket，expireTime，expireIn
		List<Map<String, String>> tickets = vo.getTickets();
		if (tickets!=null&&!tickets.isEmpty()) {
			Map<String, String> map = tickets.get(0);
			vo.setTicket(map.get("value"));
			vo.setExpireIn(Integer.parseInt(map.get("expire_in")));
			vo.setExpireTime(map.get("expire_time"));
			vo.setTickets(null);
		}
		vo.setAppId(TencentConfig.appid);
		vo.setOrderNo(IdGen.uuid());
		return vo;

	}
	
}
