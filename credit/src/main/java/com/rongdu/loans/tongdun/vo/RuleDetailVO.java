package com.rongdu.loans.tongdun.vo;

import cn.fraudmetrix.riskservice.ruledetail.ConditionDetail;
import cn.fraudmetrix.riskservice.ruledetail.RuleDetail;
import com.rongdu.common.exception.ErrInfo;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.credit.common.CreditApiVo;
import com.rongdu.loans.tongdun.common.FraudApiRespCodeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 同盾-命中规则详情查询-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class RuleDetailVO extends CreditApiVo {

	// 错误码，正常执行完扫描时为空
	private String reasonCode;
	// 错误原因描述，正常执行完扫描时为空
	private String reasonDesc;
	//命中的规则
	private List<RuleDetail> rules;

	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String getCode() {
		if (isSuccess()){
			return ErrInfo.SUCCESS.getCode();
		}else{
			return getReasonCode();
		}
	}

	@Override
	public void setCode(String code) {

	}

	@Override
	public String getMsg() {
		if (isSuccess()){
			return ErrInfo.SUCCESS.getMsg();
		}else{
			if (StringUtils.isBlank(getReasonDesc())){
				return FraudApiRespCodeUtils.getApiMsg(getReasonCode());
			}else{
				return getReasonDesc();
			}
		}
	}

	@Override
	public void setMsg(String msg) {

	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public List<RuleDetail> getRules() {
		return rules;
	}

	public void setRules(List<RuleDetail> rules) {
		this.rules = rules;
	}

	public <T> List<T> find(Class<T> clazz) {
		if(this.rules == null) {
			return new ArrayList();
		} else if(clazz == null) {
			return new ArrayList();
		} else {
			ArrayList result = new ArrayList();
			Iterator var3 = this.rules.iterator();

			while(true) {
				List conditions;
				do {
					RuleDetail e;
					do {
						if(!var3.hasNext()) {
							return result;
						}

						e = (RuleDetail)var3.next();
					} while(e == null);

					conditions = e.getConditions();
				} while(conditions == null);

				Iterator var6 = conditions.iterator();

				while(var6.hasNext()) {
					ConditionDetail condition = (ConditionDetail)var6.next();
					if(clazz.isInstance(condition)) {
						result.add(condition);
					}
				}
			}
		}
	}
}
