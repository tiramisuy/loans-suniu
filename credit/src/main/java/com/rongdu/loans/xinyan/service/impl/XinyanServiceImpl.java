/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.xinyan.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.loans.credit.common.LogParam;
import com.rongdu.loans.credit.common.PartnerApiService;
import com.rongdu.loans.xinyan.common.XinyanConfig;
import com.rongdu.loans.xinyan.service.XinyanService;
import com.rongdu.loans.xinyan.utils.RsaCodingUtil;
import com.rongdu.loans.xinyan.utils.SecurityUtil;
import com.rongdu.loans.xinyan.vo.BlackOP;
import com.rongdu.loans.xinyan.vo.BlackVO;
import com.rongdu.loans.xinyan.vo.JbqbBlackOP;
import com.rongdu.loans.xinyan.vo.JbqbBlackVO;
import com.rongdu.loans.xinyan.vo.OverdueOP;
import com.rongdu.loans.xinyan.vo.OverdueVO;
import com.rongdu.loans.xinyan.vo.RadarOP;
import com.rongdu.loans.xinyan.vo.RadarVO;
import com.rongdu.loans.xinyan.vo.TotaldebtOP;
import com.rongdu.loans.xinyan.vo.TotaldebtVO;

/**
 * 新颜
 * 
 * @author liuzhuang
 * @version 2018-03-26
 */
@Service
public class XinyanServiceImpl extends PartnerApiService implements XinyanService {

	@Override
	public BlackVO black(BlackOP op) {
		// 配置参数
		String partnerId = XinyanConfig.partner_id;
		String partnerName = XinyanConfig.partner_name;
		String bizCode = XinyanConfig.black_biz_code;
		String bizName = XinyanConfig.black_biz_name;
		String url = XinyanConfig.black_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		String trans_id = "" + System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期

		Map<String, String> params = new HashMap<String, String>();
		params.put("member_id", XinyanConfig.member_id);
		params.put("terminal_id", XinyanConfig.terminal_id);
		params.put("trans_id", trans_id);
		params.put("trade_date", trade_date);
		params.put("versions", "1.3.0");
		params.put("phone_no", op.getMobile());
		params.put("bankcard_no", op.getCardNo());
		params.put("id_no", op.getIdNo());
		params.put("id_name", op.getName());
		params.put("industry_type", "A1");

		/***** 非必填参数 ****/
		params.put("loanTime", "");// 放款时间
		params.put("overdueDay", "");// 逾期天数
		params.put("overdueAmt", "");// 逾期金额
		params.put("memberName", "");// 商户名称

		String pfxPath = this.getClass().getResource(XinyanConfig.pfx_path).getPath();
		String base64Str = null;
		try {
			base64Str = SecurityUtil.Base64Encode(JsonMapper.toJsonString(params));
		} catch (UnsupportedEncodingException e) {
			logger.error("Xinyan black Base64Encode error", e);
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64Str, pfxPath, XinyanConfig.pfx_password);
		params.put("data_content", data_content);
		params.put("data_type", "json");
		// 发送请求
		BlackVO vo = (BlackVO) postForJson(url, params, BlackVO.class, log);
		return vo;
	}

	@Override
	public TotaldebtVO totaldebt(TotaldebtOP op) {
		// 配置参数
		String partnerId = XinyanConfig.partner_id;
		String partnerName = XinyanConfig.partner_name;
		String bizCode = XinyanConfig.totaldebt_biz_code;
		String bizName = XinyanConfig.totaldebt_biz_name;
		String url = XinyanConfig.totaldebt_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		String trans_id = "" + System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期

		Map<String, String> params = new HashMap<String, String>();
		params.put("member_id", XinyanConfig.member_id);
		params.put("terminal_id", XinyanConfig.terminal_id);
		params.put("trans_id", trans_id);
		params.put("trade_date", trade_date);
		params.put("versions", "1.1.0");
		params.put("phone_no", op.getMobile());
		params.put("bankcard_no", op.getCardNo());
		params.put("id_no", op.getIdNo());
		params.put("id_name", op.getName());
		params.put("industry_type", "A1");

		String pfxPath = this.getClass().getResource(XinyanConfig.pfx_path).getPath();
		String base64Str = null;
		try {
			base64Str = SecurityUtil.Base64Encode(JsonMapper.toJsonString(params));
		} catch (UnsupportedEncodingException e) {
			logger.error("Xinyan totaldebt Base64Encode error", e);
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64Str, pfxPath, XinyanConfig.pfx_password);
		params.put("data_content", data_content);
		params.put("data_type", "json");
		// 发送请求
		TotaldebtVO vo = (TotaldebtVO) postForJson(url, params, TotaldebtVO.class, log);
		return vo;
	}

	public OverdueVO overdue(OverdueOP op) {
		// 配置参数
		String partnerId = XinyanConfig.partner_id;
		String partnerName = XinyanConfig.partner_name;
		String bizCode = XinyanConfig.overdue_biz_code;
		String bizName = XinyanConfig.overdue_biz_name;
		String url = XinyanConfig.overdue_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		String trans_id = "" + System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期

		Map<String, String> params = new HashMap<String, String>();
		params.put("member_id", XinyanConfig.member_id);
		params.put("terminal_id", XinyanConfig.terminal_id);
		params.put("trans_id", trans_id);
		params.put("trade_date", trade_date);
		params.put("versions", "1.3.0");
		params.put("phone_no", op.getMobile());
		params.put("bankcard_no", op.getCardNo());
		params.put("id_no", op.getIdNo());
		params.put("id_name", op.getName());
		params.put("industry_type", "A1");

		String pfxPath = this.getClass().getResource(XinyanConfig.pfx_path).getPath();
		String base64Str = null;
		try {
			base64Str = SecurityUtil.Base64Encode(JsonMapper.toJsonString(params));
		} catch (UnsupportedEncodingException e) {
			logger.error("Xinyan overdue Base64Encode error", e);
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64Str, pfxPath, XinyanConfig.pfx_password);
		params.put("data_content", data_content);
		params.put("data_type", "json");
		// 发送请求
		OverdueVO vo = (OverdueVO) postForJson(url, params, OverdueVO.class, log);
		return vo;
	}

	/*****************************************************************************************************************/
	/******************************** 私人订制 ************************************************************************/
	/*****************************************************************************************************************/
	@Override
	public JbqbBlackVO blackJbqb(JbqbBlackOP op) {
		// 配置参数
		String partnerId = XinyanConfig.partner_id;
		String partnerName = XinyanConfig.partner_name;
		String bizCode = XinyanConfig.black_jbqb_biz_code;
		String bizName = XinyanConfig.black_jbqb_biz_name;
		String url = XinyanConfig.black_jbqb_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		String trans_id = "" + System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期

		String idNo = op.getIdNo();
		if (idNo.indexOf("x") != -1) {
			idNo = idNo.replaceAll("x", "X");
		}
		idNo = md5(idNo);

		String idName = md5(op.getName());

		Map<String, String> params = new HashMap<String, String>();
		params.put("member_id", XinyanConfig.member_id);
		params.put("terminal_id", XinyanConfig.terminal_id);
		params.put("trans_id", trans_id);
		params.put("trade_date", trade_date);
		params.put("versions", "1.4.1");
		params.put("id_no", idNo);
		params.put("id_name", idName);
//		params.put("industry_type", "C9");
//		params.put("product_type", "FMLHJBD");

		/***** 非必填参数 ****/
		params.put("loanTime", "");// 放款时间
		params.put("overdueDay", "");// 逾期天数
		params.put("overdueAmt", "");// 逾期金额
		params.put("memberName", "");// 商户名称

		String pfxPath = this.getClass().getResource(XinyanConfig.pfx_path).getPath();
		String base64Str = null;
		try {
			base64Str = SecurityUtil.Base64Encode(JsonMapper.toJsonString(params));
		} catch (UnsupportedEncodingException e) {
			logger.error("Xinyan black_jbqb Base64Encode error", e);
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64Str, pfxPath, XinyanConfig.pfx_password);
		params.put("data_content", data_content);
		params.put("data_type", "json");
		// 发送请求
		JbqbBlackVO vo = (JbqbBlackVO) postForJson(url, params, JbqbBlackVO.class, log);
		return vo;
	}

	@Override
	public RadarVO radar(RadarOP op) {
		// 配置参数
		String partnerId = XinyanConfig.partner_id;
		String partnerName = XinyanConfig.partner_name;
		String bizCode = XinyanConfig.radar_biz_code;
		String bizName = XinyanConfig.radar_biz_name;
		String url = XinyanConfig.radar_url;

		LogParam log = new LogParam();
		log.setPartnerId(partnerId);
		log.setPartnerName(partnerName);
		log.setBizCode(bizCode);
		log.setBizName(bizName);

		String idNo = op.getIdNo();
		if (idNo.indexOf("x") != -1) {
			idNo = idNo.replaceAll("x", "X");
		}
		idNo = md5(idNo);

		String idName = md5(op.getName());

		String trans_id = "" + System.currentTimeMillis();// 商户订单号
		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期

		Map<String, String> params = new HashMap<String, String>();
		params.put("member_id", XinyanConfig.member_id);
		params.put("terminal_id", XinyanConfig.terminal_id);
		params.put("trans_id", trans_id);
		params.put("trade_date", trade_date);
		params.put("versions", "1.4.0");
		params.put("id_no", idNo);
		params.put("id_name", idName);
		params.put("industry_type", "C9");
		params.put("product_type", "QJLDJBD");

		String pfxPath = this.getClass().getResource(XinyanConfig.pfx_path).getPath();
		String base64Str = null;
		try {
			base64Str = SecurityUtil.Base64Encode(JsonMapper.toJsonString(params));
		} catch (UnsupportedEncodingException e) {
			logger.error("Xinyan radar Base64Encode error", e);
		}
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64Str, pfxPath, XinyanConfig.pfx_password);
		params.put("data_content", data_content);
		params.put("data_type", "json");
		// 发送请求
		RadarVO vo = (RadarVO) postForJson(url, params, RadarVO.class, log);
		return vo;
	}

	private String md5(String s) {
		String s1 = "";
		try {
			s1 = MD5Util.md5(s);
		} catch (Exception e) {
			logger.warn("md5 error");
		}
		return s1;
	}

}