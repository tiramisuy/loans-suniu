package com.rongdu.loans.utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
	
	public String fieldLabel();
	
	public int fieldWidth();
	
	public String convert() default "";
	
}
