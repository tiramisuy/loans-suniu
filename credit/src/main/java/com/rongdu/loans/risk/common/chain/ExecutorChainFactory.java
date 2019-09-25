package com.rongdu.loans.risk.common.chain;

import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;
import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;

public class ExecutorChainFactory {

    public static ExecutorChain createExecutorChain(AutoApproveContext context) {
        ExecutorChain chains = null;
        LoanApplySimpleVO applyInfo = context.getApplyInfo();
        if (applyInfo != null && StringUtils.isNotBlank(applyInfo.getProductId())) {
            LoanProductEnum product = LoanProductEnum.get(applyInfo.getProductId());
            switch (product) {
                case JDQ:
                    if (context.containsValue(ChannelEnum.DAWANGDAI.getCode())
                            || context.containsValue(ChannelEnum._51JDQ.getCode())
                            || context.containsValue(ChannelEnum.YBQB.getCode())
                            || context.containsValue(ChannelEnum.CYQB.getCode())
                            || context.containsValue(ChannelEnum.CYQBIOS.getCode())) {
                        chains = SuXjddwdExecutorChainFactory.createExecutorChain(context);
                    } else if (context.containsValue(ChannelEnum.JIEDIANQIAN.getCode()) || context.containsValue(ChannelEnum.JIEDIANQIAN2.getCode())) {
                        chains = SuXjdjdqExecutorChainFactory.createExecutorChain(context);
                    }
                    break;
                case JN:
                case JN2:
                case JNFQ:
                    if (context.containsValue(ChannelEnum.DAWANGDAI.getCode())
                            || context.containsValue(ChannelEnum._51JDQ.getCode())
                            || context.containsValue(ChannelEnum.YBQB.getCode())
                            || context.containsValue(ChannelEnum.CYQB.getCode())
                            || context.containsValue(ChannelEnum.CYQBIOS.getCode())) {
                        chains = SuXjddwdExecutorChainFactory.createExecutorChain(context);
                    }else if (context.containsValue(ChannelEnum.JIEDIANQIAN.getCode()) || context.containsValue(ChannelEnum.JIEDIANQIAN2.getCode())){
                        chains = SuXjdjdqExecutorChainFactory.createExecutorChain(context);
                    }
                    break;
                default:
                    break;
            }
        }
        return chains;
    }
}
