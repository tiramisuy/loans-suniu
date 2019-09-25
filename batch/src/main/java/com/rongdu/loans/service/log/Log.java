package com.rongdu.loans.service.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 * 
 * @author sunda
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
	/**
	 * 操作名称
	 */
	public String opt() default "";
	/**
	 * 操作对象
	 */
	public String obj() default "";
	/**
	 * 是否记录参数
	 */
	public boolean logArgs() default false;
	/**
	 * 是否记录返回值
	 */
	public boolean logReturns() default false;
	/**
	 * 类路径
	 */
//	public String clas() default "";
	/**
	 * 属性名称
	 */
	public String field() default "";

}
