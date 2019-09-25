package com.rongdu.loans.service.log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rongdu.core.utils.encode.JsonBinder;
import com.rongdu.loans.constant.Operations;
import com.rongdu.loans.entity.account.VUser;
import com.rongdu.loans.utils.DateUtil;


//@Aspect
public class LogAspect {
	
	private static final String LOG_POINTCUT = "@target(com.rongdu.loans.service.log.LogService)&&@annotation(com.rongdu.loans.service.log.Log)";
	
	@Autowired
	private JdbcLogWriter jdbcLogWriter;
	
	private JsonBinder jsonBinder = JsonBinder.buildNormalBinder();
	
	/**
	 * 记录操作日志
	 * 
	 * AOP 有性能损耗，因此：
	 * 1、仅在需要的时候记录增、删、改操作，一般情况不记录查询操作
	 * 2、如果不是特殊需要，就不要记录参数和返回值
	 * 
	 * @param joinPoint
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@After(LOG_POINTCUT)
	public void log(JoinPoint joinPoint) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String operaor = user.getUsername();
		String operaorName = user.getName();
		Long loginId = user.getLoginId();
		String time = DateUtil.format();
//		Object arg = joinPoint.getArgs()[0];
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		LogService typeAnnotation = joinPoint.getTarget().getClass().getAnnotation(LogService.class);
		Log methodAnnotation = method.getAnnotation(Log.class);
		//操作名称
		String optName = methodAnnotation.opt();
		if (StringUtils.isBlank(optName)) {
			String opt = joinPoint.getSignature().getName();
			optName = Operations.get(opt);
			if (StringUtils.isBlank(optName)) {
				optName = opt;
			}
		}
		//操作对象
		String obj = methodAnnotation.obj();
		if (StringUtils.isBlank(obj)) {
			obj = typeAnnotation.obj();
		}
		//参数
		boolean logArgs = methodAnnotation.logArgs();
		String args = null;
		if (logArgs) {
			args = jsonBinder.toJson(joinPoint.getArgs());
		}
		//返回值
		boolean logReturns = methodAnnotation.logReturns();
		String returns = null;
		if (logReturns) {
		}
		String field=methodAnnotation.field();
		String uuid="";
		if(StringUtils.isNotBlank(field)){   //属性名称都不为空时，
			Object[] objs=joinPoint.getArgs();
			for (Object object : objs) {
				if(object instanceof Long){
					uuid+=field+":"+(Long)object;
				}else if(object.getClass().isArray()){
					String name=object.getClass().getComponentType().getName();
					if(name.endsWith("Long")){
						Long[] lon=(Long[])object;
						uuid+=field+":";
						for (int i = 0; i < lon.length; i++) {
							uuid+=lon[i];
							if(i<lon.length-1){
								uuid+=",";
							}
						}
					}
				}else{
					Method md=object.getClass().getDeclaredMethod(returnGetMethod(field));
					uuid+=field+":"+md.invoke(object);
				}
			}
		}
//		OperationLog optLog = new OperationLog(null, operaor, operaorName,time, optName, obj, args, returns,loginId,uuid);
//		jdbcLogWriter.processMessage(optLog);
	}
	/**
	 * 获取属性的get方法名称
	 * @param field
	 * @return
	 */
	public String returnGetMethod(String field){
		String first=field.substring(0,1);
		String last=field.substring(1);
		return "get"+first.toUpperCase()+last;
	}
	
	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		LogAspect a=new LogAspect();
//		System.out.println(a.getClass().toString().substring(6));
//		
//		Device device=new Device();
//		device.setUdid("sdf:23");
//		Method m=device.getClass().getDeclaredMethod("getUdid");
//		String value=(String)m.invoke(device);
//		System.out.println(value);
		
		String[] arr=new String[]{"1","b"};
		Object obj=(Object)arr;
		if(obj.getClass().isArray()){
			System.out.println(obj.getClass().getComponentType().getName());
		}
		java.lang.String[] a=null;
		
	}
}
