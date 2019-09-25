package com.rongdu.loans.risk.executor.dwd;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.loan.option.dwd.PhoneList;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.Executor;
import com.rongdu.loans.risk.common.RuleIds;
import com.rongdu.loans.risk.entity.HitRule;
import com.rongdu.loans.risk.entity.RiskRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则名称：通讯录里名称有（宝宝、老婆、老公、爱人、婆娘、妻子、媳妇、亲爱的、baby、爸、爹、阿爹、阿爸、爸爸、爸比、老爸、老头、妈、娘、阿娘、阿妈、妈妈、老妈、妈咪；不包含）
 */
public class AT007Executor extends Executor {

    public static String[] equalsContactNames = new String[]{"爸","爹","爸爸","妈","娘","妈妈","家","家人","家里","亲","mama","baba"};

    public static String[] closeContactNames = new String[]{"宝宝","宝贝", "老婆", "老公", "爱人", "婆娘", "妻子", "媳妇", "亲爱的", "baby", "阿爹", "阿爸", "爸比", "老爸", "老头",  "阿娘", "阿妈", "老妈", "妈咪"
    ,"猪猪","老豆","我爸","我妈","mother","father","mum","mom","dad","mummy","mommy","daddy"};

    @Override
    public void doExecute(AutoApproveContext context) {
        List<PhoneList> addressBookList = getDataInvokeService().getdwdAdditionInfo(context).getContacts().getPhoneList();

        //通讯录紧密联系人
        List<PhoneList> addressBookCloseContacts = new ArrayList<>();
        if(addressBookList != null){
            for (PhoneList addressBook : addressBookList) {
                //通讯录紧密联系人
                if (addressBook.getName() != null) {
                    for (String name : closeContactNames) {
                        if (filterEmoji(addressBook.getName()).contains(name)) {
                            addressBookCloseContacts.add(addressBook);
                            break;
                        }
                    }

                    for (String names : equalsContactNames) {
                        if (names.equalsIgnoreCase(filterEmoji(addressBook.getName()))) {
                            addressBookCloseContacts.add(addressBook);
                            break;
                        }
                    }

                }

            }
        }
        context.put("addressBookCloseContacts", addressBookCloseContacts);

        RiskRule riskRule = getRiskRule();
        String evidence = null;
        if(addressBookCloseContacts.size() == 0) {
            addHitNum(1);
            evidence = "通讯录没有匹配到紧密联系人";
            HitRule hitRule = createHitRule(riskRule);
            hitRule.setRemark(evidence);
            addHitRule(context, hitRule);
        }
        logger.info("执行规则-【{}-{}】-{},{},命中规则的数量：{},决策依据：{}", getRuleId(), getRuleName(), context.getUserName(),
                context.getApplyId(), getHitNum(), evidence);
    }

    public static String filterEmoji(String source) {
        if (!StringUtils.isEmpty(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        } else {
            return source;
        }
    }

    @Override
    public void init() {
        super.setRuleId(RuleIds.AT007);
    }
}
