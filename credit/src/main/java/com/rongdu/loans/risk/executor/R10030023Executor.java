package com.rongdu.loans.risk.executor;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustContactVO;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.option.jdq.MoxieTelecomReport;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通话详单没有与近亲联系人联系
 * @version 1.0
 * @date 2019/4/24
 * 
 */
public class R10030023Executor extends Executor {

    @Override
    public void init() {
        super.setRuleId(RuleIds.R10030023);
    }

    @Override
    public void doExecute(AutoApproveContext context) {
        //亲属列表
        List<CustContactVO> contactList = context.getUserInfo()
                .getContactList();
        // 加载风险分析数据
        IntoOrder vo = null;
        try {
            vo = getDataInvokeService().getjdqBase(context);
        } catch (Exception e) {
            logger.error("JDQ基本信息查询异常", e);
        }
        if (vo == null) {
            return;
        }
        //通话详单
        List<MoxieTelecomReport.CallContactDetailBean> detailBeans = vo.getMoxieTelecomReport().call_contact_detail;
        // Assert.notNull(ccmList,"常用联系电话(近6个月)为空");

        // 命中的规则
        HitRule hitRule = checkContactConnectCount(contactList, detailBeans);
        // 决策依据
        String evidence = hitRule.getRemark();
        // 命中规则的数量
        int hitNum = getHitNum();
        if (hitNum > 0) {
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(),
                getRuleName(), context.getUserName(), context.getApplyId(),
                getHitNum(), evidence);
    }

    /**
     * 近半年通话详单中没有与父母、配偶进行的通话记录
     *
     * @param contactList
     * @param ccmList
     * @return
     */
    private HitRule checkContactConnectCount(List<CustContactVO> contactList,
                                             List<MoxieTelecomReport.CallContactDetailBean> detailBeans) {
        HitRule hitRule = createHitRule(getRiskRule());
        String contactMobile = null;
        String ccmMobile = null;
        // 通话次数
        int connectCount = 0;
        int minConnectCount = 1;
        int relationship = 0;
        Map<String, Integer> extInfo = new LinkedHashMap<>();
        for (CustContactVO contactVO : contactList) {
            relationship = contactVO.getRelationship().intValue();
            contactMobile = contactVO.getMobile();
            // 1父母，2配偶
            if (1 == relationship || 2 == relationship) {
                for (MoxieTelecomReport.CallContactDetailBean bean : detailBeans) {
                    ccmMobile = bean.peer_num;
                    // 父母或者配偶
                    if (StringUtils.equals(ccmMobile, contactMobile)) {
                        extInfo.put(contactMobile, bean.call_cnt_6m);
                        connectCount = connectCount + bean.call_cnt_6m;
                    }
                }
            }
        }

        if (connectCount < minConnectCount) {
            setHitNum(1);
        }

        hitRule.setValue(String.valueOf(connectCount));
        String msg = String.format("通话次数：%s，详情如下：%s", connectCount,
                JsonMapper.toJsonString(extInfo));
        hitRule.setRemark(msg);
        return hitRule;
    }

}
