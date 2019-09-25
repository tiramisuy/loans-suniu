package com.rongdu.loans.credit.tencent.service;

import com.rongdu.loans.credit.tencent.vo.OcrResultVo;

/**
 * 腾讯身份证OCR识别-服务接口
 * @author sunda
 * @version 2017-07-20
 */
public interface OcrService {
	
	/**
	 * 根据订单号，获取身份证OCR识别的结果
	 * @param orderNo
	 * @return
	 */
	public OcrResultVo getOcrResult(String orderNo);
	
	/**
	 *  根据订单号，获取身份证OCR识别的结果（增强版）
	 * @param frontPhotoOrderNo 身份证正面订单号
	 * @param backPhotoOrderNo 身份证反面订单号
	 * @return
	 */
	public OcrResultVo getOcrResult(String frontPhotoOrderNo, String backPhotoOrderNo);
	
}
