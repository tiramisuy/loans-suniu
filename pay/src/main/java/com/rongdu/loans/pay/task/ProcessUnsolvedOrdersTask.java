package com.rongdu.loans.pay.task;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.pay.service.BaofooWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

//@Service
//@Lazy(false)
public class ProcessUnsolvedOrdersTask extends BaseService{
	
	@Autowired
	public BaofooWithdrawService baofooWithdrawService;
	
    @Scheduled(cron="0 0/10 * * * ?")    
    public void processUnsolvedOrders(){
    	logger.debug("正在执行定时任务");
//    	try {
//			baofooWithdrawService.processUnsolvedOrders();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
    }   
}
