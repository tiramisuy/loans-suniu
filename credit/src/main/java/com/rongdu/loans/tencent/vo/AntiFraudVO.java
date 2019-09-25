package com.rongdu.loans.tencent.vo;

import com.rongdu.loans.credit.common.CreditApiVo;

import java.util.List;

/**
 * 腾讯-反欺诈服务-应答报文
 * @author sunda
 * @version 2017-08-10
 */
public class AntiFraudVO extends CreditApiVo{

	private static final long serialVersionUID = -4695418047175301678L;
	/**
	 * 应答代码
	 * 公共错误码，0表示成功，其他值表示失败。详见错误码页面的公共错误码
	 */
	private String code;
	/**
	 * 应答代码
	 * 业务侧错误码。成功时返回Success，错误时返回具体业务错误原因。
	 */
	private String codeDesc;
	/**
	 * 模块错误信息描述，与接口相关
	 */
	private String message;
	/**
	 * 0-100:欺诈分值。值越高欺诈可能性越大；-1:查询不到数据
	 */
	private int riskScore;
	/**
	 * 扩展字段，对风险类型的说明
	 * riskScore为0时无此字段
	 */
	private List<RiskDetail> riskInfo;

	public AntiFraudVO() {
	}

	@Override
	public boolean isSuccess() {
		if ("0".equals(getCode())) {
			return true;
		}
		return false;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;	
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code =code;
	}

	@Override
	public String getMsg() {
		return this.message;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(int riskScore) {
		this.riskScore = riskScore;
	}

	public List<RiskDetail> getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(List<RiskDetail> riskInfo) {
		this.riskInfo = riskInfo;
	}
}