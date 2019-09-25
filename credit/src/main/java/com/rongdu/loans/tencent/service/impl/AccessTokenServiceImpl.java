package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.credit.tencent.service.AccessTokenService;
import com.rongdu.loans.credit.tencent.vo.AccessTokenVo;
import com.rongdu.loans.tencent.common.TencentConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯接口访问令牌-服务实现类
 * @author sunda
 * @version 2017-07-20
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl extends PartnerApiService implements AccessTokenService{

	@Override
	public AccessTokenVo getAccessToken() {
		
		//配置参数
		String partnerId = TencentConfig.partner_id;
		String partnerName = TencentConfig.partner_name;
		String bizCode = TencentConfig.access_token_biz_code;
		String bizName = TencentConfig.access_token_biz_name;
		String url = TencentConfig.access_token_url;
		
		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);
		
		AccessTokenVo vo = null;
		vo = (AccessTokenVo) JedisUtils.getObject("tencentAccessToken");
		if (vo == null) {
			Map<String, String> params = new HashMap<String, String>();
			//公共参数
			params.put("app_id", TencentConfig.appid);
			params.put("secret",TencentConfig.secret);
			params.put("grant_type", "client_credential");
			params.put("version", "1.0.0");   
			//拼接请求字符串
//			url = HttpUtils.makeQueryString(url, params);
			//发送请求
			vo = (AccessTokenVo) getForJson(url, params,AccessTokenVo.class,log);
			//应答成功时，缓存Token
			if (vo!=null&&vo.isSuccess()) {
				JedisUtils.setObject("tencentAccessToken", vo , 6600);
			}		
			return vo;
		}else {
			return vo;
		}

	}
	
}
