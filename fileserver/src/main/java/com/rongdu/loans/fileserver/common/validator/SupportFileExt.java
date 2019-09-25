package com.rongdu.loans.fileserver.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 通过注解定义支持的文件格式
 * @author sunda
 * version 2017-06-12
 */
@Constraint(validatedBy = SupportFileExtValidator.class) 
@Target( { java.lang.annotation.ElementType.METHOD,   java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface SupportFileExt {  
	
	String message() default "不支持该文件格式";
	/**
	 * 提示信息,可以填写国际化的key  
	 * @return
	 */
    String regexp() default "jpg|jpeg|png|bmp|mp4"; 
      
    //下面这两个属性必须添加  
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
      
}  