package com.rongdu.loans.credit.tencent.service;

import com.rongdu.loans.credit.tencent.vo.FaceVerifyVo;

/**
 * 腾讯人脸验证服务-服务接口
 * @author sunda
 * @version 2017-07-20
 */
public interface FaceVerifyService {
		
//	/**
//	 * 获取腾讯NonceTicket
//	 * NONCE ticket: 主要用于合作方 前端包含APP 和 和H5  等生成签名鉴权参数之一，启动 H5 或 SDK 人脸验证。
//	 * 有效期为 120S,且一次性有效, 即每次启动 SDK刷脸都要重新请求 NONCE ticket
//	 * @param userId
//	 * @return
//	 */
//	public TicketVo getNonceTicket(String userId);
	
	/**
	 * 根据订单号，获取人脸核验的结果
	 * @param orderNo
	 * @return
	 */
	public FaceVerifyVo getFaceVerifyResult(String orderNo);
	
}
