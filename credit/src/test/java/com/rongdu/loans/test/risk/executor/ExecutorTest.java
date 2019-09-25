package com.rongdu.loans.test.risk.executor;

import com.rongdu.loans.risk.common.AutoApproveContext;
import com.rongdu.loans.risk.common.ExecutorChain;


public class ExecutorTest {

	public static void main(String[] args) {
		
		String applyId = "1020170815143511200127";
		ExecutorChain chains = new ExecutorChain();
		chains.addExecutor(new A1Executor());
		chains.addExecutor(new A2Executor());
		chains.addExecutor(new A3Executor());
		chains.addExecutor(new A4Executor());
		AutoApproveContext context = new AutoApproveContext(applyId);
		chains.doExecute(context);
	}

}
