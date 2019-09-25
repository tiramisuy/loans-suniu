package com.rongdu.loans.credit.tencent.service;

import com.rongdu.loans.credit.tencent.vo.TicketVo;

/**
 * 获取腾讯Ticket-服务接口
 * @author sunda
 * @version 2017-07-20
 */
public interface TicketService {
	
	/**
	 * 获取腾讯SignTicket
	 * SIGN ticket: 主要用于合作方 后台服务端业务请求生成签名鉴权参数之一，用于后台查询验证结果、调用其他业务服务等。
	 * 有效期为 3600S, 此处 api ticket 的必须缓存在磁盘，并定时刷新,
	 * 建议每 50 分钟请新的 api ticket,原 api ticket 1 小时(3600S)失效，期间两个 api ticket 都能使用
	 * @return
	 */
	public TicketVo getSignTicket();
	
	/**
	 * 获取腾讯NonceTicket
	 * NONCE ticket: 主要用于合作方 前端包含APP 和 和H5  等生成签名鉴权参数之一，启动 H5 或 SDK 人脸验证。
	 * 有效期为 120S,且一次性有效, 即每次启动 SDK刷脸都要重新请求 NONCE ticket
	 * @param userId
	 * @return
	 */
	public TicketVo getNonceTicket(String userId);
	
//	public String sign(List<String> values);
	
}
