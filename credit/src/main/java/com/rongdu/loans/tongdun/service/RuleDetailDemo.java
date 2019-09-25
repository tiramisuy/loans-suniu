package com.rongdu.loans.tongdun.service;


import cn.fraudmetrix.riskservice.RuleDetailClient;
import cn.fraudmetrix.riskservice.RuleDetailResult;
import cn.fraudmetrix.riskservice.object.Environment;
import cn.fraudmetrix.riskservice.ruledetail.BlackListDetail;
import cn.fraudmetrix.riskservice.ruledetail.BlackListHit;
import com.rongdu.loans.tongdun.common.TongdunConfig;

import java.util.List;


public class RuleDetailDemo {

	public static void main(String[] argv) {
		String sequenceId = "1462326266162000X065A0EDB3559014";
        queryRuleDetail(sequenceId);
    }

	private static void queryRuleDetail(String sequenceId) {
		// 填写参数
        String partnerCode = TongdunConfig.partner_code;
        String partnerKey = TongdunConfig.partner_key;
        Environment env = Environment.PRODUCT; // Environment.PRODUCT表示调用生产环境, 测试环境请修改为Environment.SANDBOX

        // 调用接口
        RuleDetailClient client = RuleDetailClient.getInstance(partnerCode, env);
        RuleDetailResult result = client.execute(partnerKey, sequenceId);
        if (result == null) return;

        // 样例：获取风险名单命中的数据
        List<BlackListDetail> find = result.find(BlackListDetail.class);
        for (BlackListDetail e : find) {
            List<BlackListHit> hits = e.getHits();
            for (BlackListHit hit : hits) {
                // hit中包含了命中风险名单的具体信息

            }
        }
	}
}
