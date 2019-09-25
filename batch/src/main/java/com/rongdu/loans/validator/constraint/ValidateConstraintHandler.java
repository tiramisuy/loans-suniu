package com.rongdu.loans.validator.constraint;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rongdu.loans.exception.BizException;
import com.rongdu.loans.validator.AnnotationValidable;
import com.rongdu.loans.validator.GetFiledValue;
import com.rongdu.loans.validator.Handler;

 public class ValidateConstraintHandler implements Handler {
	private static final Logger LOGGER = Logger.getLogger(ValidateConstraintHandler.class.getName());

	@Override
	public void validate(AnnotationValidable validatedObj, Field field)
			throws BizException {
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "validate()");
		}
		if (field.isAnnotationPresent(ValidateConstraint.class)) {
			checkConstraint(validatedObj, field);
		}
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "validate()");
		}
	}

	private void checkConstraint(AnnotationValidable filter, Field field)
			throws BizException {
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "checkNotNull()");
		}
		String message = field.getAnnotation(ValidateConstraint.class).message();
		Object dest = null;
		List obj=null;
		try {
			dest = GetFiledValue.getField(filter, field.getName());
//			Map map = (Map)getFiledCollection(filter,field.getAnnotation(ValidateConstraint.class).fileCollection());
			 obj= (List)GetFiledValue.getField(filter, field.getAnnotation(ValidateConstraint.class).fieldCollection());
		} catch  (Exception ex) {
			LOGGER.log(Level.SEVERE,
					"Get field value or cast value error. Error message: "
							+ ex.getMessage(), ex);
			throw new BizException(ex.getMessage(), ex);
		}
		if (obj.contains(dest)) {
			LOGGER.log(Level.SEVERE,
					"Validate fail. Error message: validate value is existed");
			
			BizException bizException = new  BizException(message + "The value of "
					+ field.getName() + " is existed.");
			bizException.setCode("Constraint");
			throw bizException;
		}
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(this.getClass().getName(), "checkNotNull()");
		}
	}
}
