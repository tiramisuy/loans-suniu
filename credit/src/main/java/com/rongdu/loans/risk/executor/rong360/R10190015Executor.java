package com.rongdu.loans.risk.executor.rong360;

import com.rongdu.loans.loan.option.rongTJreportv1.SpecialCate;
import com.rongdu.loans.loan.option.rongTJreportv1.TianjiReportDetailResp;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈P2P 、银行类，投资类通话次数超过150〉
 *
 * @author yuanxianchu
 * @create 2019/2/11
 * @since 1.0.0
 */
public class R10190015Executor extends Executor {

    @Override
    public void doExecute(AutoApproveContext context) {
        // 命中的规则
        HitRule hitRule = checkRule(context);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), hitNum, evidence);

    }

    private HitRule checkRule(AutoApproveContext context) {
        HitRule hitRule = createHitRule(getRiskRule());
        TianjiReportDetailResp tianjiReportDetail = getDataInvokeService().getRongTJReportDetail(context);
        List<SpecialCate> specialCate = tianjiReportDetail.getJson().getSpecialCate();
        int limit = 100;
        int p2pTalkCnt = specialCate.get(2).getTalkCnt();//P2P类
        int bankTalkCnt = specialCate.get(12).getTalkCnt();//银行类
        int talkCnt = specialCate.get(10).getTalkCnt();//投资类
        if (p2pTalkCnt > limit || bankTalkCnt > limit || talkCnt > limit){
            //P2P 、银行类，投资类通话次数超过150
            setHitNum(1);
        }
        hitRule.setRemark(String.format("P2P 、银行类，投资类通话次数超过%d，P2P：%d，银行类：%d，投资类：%d",limit,p2pTalkCnt,bankTalkCnt,talkCnt));
        return hitRule;
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10190015);
    }
}
