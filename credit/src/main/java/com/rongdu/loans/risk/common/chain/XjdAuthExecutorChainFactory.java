package com.rongdu.loans.risk.common.chain;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;
import com.rongdu.loans.risk.executor.dwd.*;


/**
 * 自动审批
 */
public class XjdAuthExecutorChainFactory {

    /**
     * 自动审批的规则及顺序
     *
     * @return
     */
    public static ExecutorChain createAuthExecutorChain(AutoApproveContext context) {
        ExecutorChain chains = new ExecutorChain();

        chains.addExecutor(new AT001Executor());
        chains.addExecutor(new AT002Executor());
//        chains.addExecutor(new AT003Executor());
        chains.addExecutor(new AT004Executor());
        chains.addExecutor(new AT005Executor());
        chains.addExecutor(new AT006Executor());
        chains.addExecutor(new AT007Executor());
        chains.addExecutor(new AT008Executor());
        chains.addExecutor(new AT009Executor());
        chains.addExecutor(new AT010Executor());
        chains.addExecutor(new AT011Executor());
        chains.addExecutor(new AT012Executor());
        chains.addExecutor(new AT013Executor());
        chains.addExecutor(new AT014Executor());
        chains.addExecutor(new AT015Executor());
        chains.addExecutor(new AT016Executor());
        chains.addExecutor(new AT017Executor());
        chains.addExecutor(new AT018Executor());
        chains.addExecutor(new AT019Executor());
        chains.addExecutor(new AT020Executor());
        chains.addExecutor(new AT021Executor());
        chains.addExecutor(new AT022Executor());
        chains.addExecutor(new AT023Executor());
        chains.addExecutor(new AT024Executor());
        chains.addExecutor(new AT025Executor());
        chains.addExecutor(new AT026Executor());
        chains.addExecutor(new AT027Executor());
        chains.addExecutor(new AT028Executor());

        return chains;
    }

}
