/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rongdu.loans.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rongdu.loans.exception.BizException;

public class Validator {
	private static final Logger LOGGER = Logger.getLogger(Validator.class.getName());

	public final static String SUFFIX = "Handler";

	public final static String PREFIX = "Validate";

	private static Validator validator = new Validator();

	private Validator() {
	}

	 
	public static Validator getInstance() {
		return validator;
	}

	public void validate(AnnotationValidable validatedObj)
			throws BizException {
		if (null == validatedObj)
		{
			LOGGER.warning("The input validatedObj is null.");
			return;
		}
		
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "validate()");
		}

		Class  currentClass = validatedObj.getClass();
		while (currentClass != null) {
			Field[] fields = currentClass.getDeclaredFields();
			for (Field elem : fields) {
				validateField(validatedObj, elem);
			}

			Class<?> superClass = currentClass.getSuperclass();
			currentClass = AnnotationValidable.class.isAssignableFrom(superClass) ? superClass : null;
		}

		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(this.getClass().getName(), "validate()");
		}
	}

	private void validateField(AnnotationValidable validatedObj, Field field) throws BizException {
 		if(AnnotationValidable.class.isAssignableFrom(field.getType()))
		{
			Object destValue = null;
			try {
				destValue = GetFiledValue.getField(validatedObj, field.getName());
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE,
						"Get field value or cast value error for field " + field.getName() + " in Class " + validatedObj.getClass() + ". Error message: "
								+ ex.getMessage(), ex);
				throw new BizException(
							"Get field value or cast value error for field " + field.getName() + " in Class " + validatedObj.getClass() + ". Error message: " + ex.getMessage(), ex);
			}

	        if(destValue == null) {
	              return; //NULL value is allowed.
	        }
	        else
	        {
	        	validate((AnnotationValidable)destValue);
	        }
		}
		
		
		List<Annotation> annotations = getValidateAnnotations(field);
		if (annotations != null && annotations.size() > 0) {
			// loop each field annotations
			for (Annotation annotation : annotations) {
				String annotationName = annotation.annotationType().getName();
				String className = annotationName + SUFFIX;
				Handler handler = null;
				try {
					handler = (Handler) Class.forName(className).newInstance();
				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE,
							"Get validate handler error. Error message: "
									+ ex.getMessage(), ex);
					throw new BizException("Can not get the handler for " + ex.getMessage(), ex);
				}
				handler.validate(validatedObj, field);
			}
		}
	}

	private List<Annotation> getValidateAnnotations(Field field) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		Annotation[] annos = field.getAnnotations();
		for (Annotation elem : annos) {
			if (elem.annotationType().getSimpleName().startsWith(PREFIX)) {
				annotations.add(elem);
			}
		}

		return annotations;
	}
	
}
