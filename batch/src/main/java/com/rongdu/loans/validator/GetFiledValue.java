/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rongdu.loans.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GetFiledValue {
	private static final Logger LOGGER = Logger.getLogger(GetFiledValue.class.getName());

	 
	public static Object getField(AnnotationValidable filter, String field)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(GetFiledValue.class.getName(), "getField()");
		}
		String firstLetter = field.substring(0, 1).toUpperCase();
		String getMethodName = "get" + firstLetter + field.substring(1);
		Method getMethod = filter.getClass().getMethod(getMethodName);
		Object returnValue = getMethod.invoke(filter);
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(GetFiledValue.class.getName(), "getField()",
					returnValue);
		}
		return returnValue;
	}
}
