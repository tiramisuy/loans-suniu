package com.rongdu.loans.common.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动/关闭dubbo服务
 *
 * @author sunda
 *
 */
public class DubboServer {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(DubboServer.class);
	/**
	 * Spring容器
	 */
	private static ClassPathXmlApplicationContext context;

	/**
	 * 启动dubbo服务
	 * @param args
	 */
	public static void main(String[] args) {
		long start  = System.currentTimeMillis();
		logger.info("正在启动dubbo服务...");
		start();
		long end  = System.currentTimeMillis();
		logger.debug("dubbo服务已经启动,耗时{}ms",(end-start));
	}

	/**
	 * 启动dubbo服务
	 */
	public static void start() {
		context = new ClassPathXmlApplicationContext("classpath*:**/spring-**.xml");
		context.start();
	}

	/**
	 * 关闭dubbo服务
	 */
	public static void stop() {
		if (context != null) {
			context.stop();
			context.close();
			context = null;
		}
	}

}
