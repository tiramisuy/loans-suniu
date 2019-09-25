package com.rongdu.common.redislock;
/**  
* @Title: RedisLockCacheKeyEnum.java  
* @Package com.rongdu.common.redislock  
* @Description: TODO(用一句话描述该文件做什么)  
* @author: yuanxianchu  
* @date 2018年8月30日  
* @version V1.0  
*/
public enum RedisLockCacheKeyEnum {
	USER_REGISTER_LOCK("user_register_lock_","用户注册锁"),
	USER_CONFIRM_BINDCARD_LOCK("user_confirm_bindcard_lock_","用户确认绑卡锁"),
	USER_PAY_SERVICEAMT_LOCK("user_pay_serviceamt_lock_","用户支付助贷服务费");
	RedisLockCacheKeyEnum(String key,String desc){
		this.key = key;
		this.desc = desc;
	}
	private String key;
	private String desc;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
