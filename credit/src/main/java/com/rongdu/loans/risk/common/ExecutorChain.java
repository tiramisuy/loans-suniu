package com.rongdu.loans.risk.common;

import java.util.ArrayList;
import java.util.List;
/**
 * 策略/规则执行链条，用于执行一组规则或者策略
 * 采用责任链设计模式
 * @author sunda
 * @version 2017-08-14
 */

public class ExecutorChain extends Executor {

	/**
	 * 责任链
	 */
	public List<Executor> chain= new ArrayList<Executor>();
	 
	/**
	 * 增加执行器
	 * @param executor
	 * @return
	 */
    public ExecutorChain addExecutor(Executor executor){
    	if (chain.size()>0) {		
    		Executor preExecutor = chain.get(chain.size()-1);
    		if (preExecutor!=null) {
    			preExecutor.setNextExecutor(executor);
    		}
		}
    	chain.add(executor);
        return this;
    }

    public ExecutorChain addAll(ExecutorChain executorChain){
    	if (executorChain == null || executorChain.chain == null || executorChain.chain.isEmpty()){
			return this;
		}
		for (Executor executor : executorChain.chain) {
			if (chain.size()>0) {
				Executor preExecutor = chain.get(chain.size()-1);
				if (preExecutor!=null) {
					preExecutor.setNextExecutor(executor);
				}
			}
			this.chain.add(executor);
		}
        return this;
    }

    /**
     * 依次执行责任链中的每个任务
     */

	@Override
	public void doExecute(AutoApproveContext context) {
		long start = System.currentTimeMillis();
		for (Executor executor:chain) {
			executor.execute(context);
			//每个执行者，可以根据规则命中情况，决定是否终止执行整个责任链
			if (executor.getNextExecutor()==null) {
				break;
			}			
		}
		long end = System.currentTimeMillis();
		context.setCostTime(new Long(end-start).intValue());
	}

	public void init() {
	}

}
