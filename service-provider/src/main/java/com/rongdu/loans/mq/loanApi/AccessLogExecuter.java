package com.rongdu.loans.mq.loanApi;

import com.rongdu.common.config.BaiduMap;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.app.entity.AccessLog;
import com.rongdu.loans.app.manager.AccessLogManager;
import com.rongdu.loans.basic.entity.IpLocation;
import com.rongdu.loans.basic.manager.IpLocationManager;
import com.rongdu.loans.external.manager.BaiduMapManager;
import com.rongdu.loans.external.vo.BaiduAddressDetailVO;
import com.rongdu.loans.external.vo.BaiduIpLocationVO;
import com.rongdu.loans.external.vo.BaiduPointVO;
import com.rongdu.loans.loan.option.AccessLogOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * api端访问日志
 * @author likang
 */
@Component("accessLogExecuter")
public class AccessLogExecuter {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    // 缓存时间20秒
  	private static final int CACHESECONDS = 20;
  	// 分布式锁前缀
  	private static final String LOCK_PREFIX = "lock_";
    // 锁状态
   	private static final String LOCK = "lock";
    
	/**
 	* APP访问日志-实体管理接口
 	*/
	@Autowired
	private AccessLogManager accessLogManager;
	
	@Autowired
	private IpLocationManager ipLocationManager;
	
	@Autowired
	private BaiduMapManager baiduMapManager;
	
    public void execute(Message message){
    	// 构造参数对象
    	AccessLog accessLog = new AccessLog();
		try {
			String body = new String(message.getBody(),"UTF-8");
			// 对象转换
	        accessLog = getAccessLog(body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        if(null != accessLog) {
	        // 数据入库
	        accessLog.setRemark(" ");
	        accessLog.preInsert();
        	accessLogManager.insert(accessLog);
        }
        // ip处理逻辑 TODO
//        if(StringUtils.isNotBlank(accessLog.getIp())) {
//        	ipLocation(accessLog.getIp());
//        }
    }
    
    /**
     * ip定位
     * @param ip
     */
    public void ipLocation(String ip) {
    	// 拼接分布式锁key
    	String lockKey = LOCK_PREFIX+ip;
    	// 获取锁状态
    	String sta = JedisUtils.get(lockKey);
    	// 判断锁状态
    	if(null == sta) {
    		// 添加缓存锁
    		JedisUtils.set(lockKey, LOCK, CACHESECONDS);
        	// 判断ip定位信息是否入库,入库不作处理
        	if(!ipLocationManager.isExistIp(ip)) {
        		// 从百度接口获取定位信息
            	BaiduIpLocationVO ipLocation = 
            			baiduMapManager.IpLocation(ip);
            	if(logger.isDebugEnabled()) {
        			logger.debug("ipLocation.Status:[{}]", ipLocation.getStatus());
        		}
            	if(null != ipLocation) {
            		if(ipLocation.getStatus().equals(
            				BaiduMap.SUCCSS_STATUS)){
            			IpLocation entity = new IpLocation();
            			entity.setIp(ip);
            			entity.setStatus(Global.IP_NOTIN_BLACKLIST);
            			if(null != ipLocation.getContent()) {
            				BaiduAddressDetailVO addressDetail =
            						ipLocation.getContent().getAddress_detail();
            				if(null != addressDetail) {
            					// 省份
            					entity.setProvince(addressDetail.getProvince());
            					// 城市
                    			entity.setCity(addressDetail.getCity());
                    			// 区县
                    			entity.setDistrict(addressDetail.getDistrict());
                    			// 街道
                    			entity.setStreet(addressDetail.getStreet());
                    			// 门牌
                    			entity.setStreetNumber(addressDetail.getStreet_number());
            				}
                			// 详细地址
                			entity.setAddress(
                					ipLocation.getContent().getAddress());
                			BaiduPointVO point = ipLocation.getContent().getPoint();
                			if(null != point) {
                				// 经度
                    			entity.setLongitude(point.getX());
                    			// 纬度
                    			entity.setLatitude(point.getY());
                			}
            			}
            			entity.preInsert();
            			int rz = ipLocationManager.insert(entity);
            			logger.info("ip[{}] 定位信息入库结果：[{}]", ip, rz);
            		}
            	}
        	} else {
        		if(logger.isDebugEnabled()) {
        			logger.debug("ip:[{}] 定位信息已经入库", ip);
        		}
        	}
        	// 释放缓存锁
        	JedisUtils.del(lockKey);
    	}
    }
    
    /**
     * json转换AccessLog对象
     * @param body
     * @return
     * @throws UnsupportedEncodingException 
     */
    private AccessLog getAccessLog(String body) 
    		throws UnsupportedEncodingException{
    	if(StringUtils.isBlank(body)) {
    		logger.error("消息解析异常，the body is null");
    		return null;
    	}
    	if(logger.isDebugEnabled()) {
    		logger.debug("消费消息内容:[{}]",
    				URLDecoder.decode(body, "UTF-8"));
    	}
        try {       	
        	AccessLogOP accessLogOP = 
        			JsonMapper.getInstance().fromJson(
        					body, AccessLogOP.class);
        	AccessLog accessLog = 
        			BeanMapper.map(accessLogOP, AccessLog.class);
            return accessLog;
        } catch (Exception e){
            logger.error("消息解析异常, body:[{}]", 
            		URLDecoder.decode(body, "UTF-8"));
            return null;
        }
    }
}
