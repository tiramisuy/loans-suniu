/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rongdu.loans.validator;

import java.lang.reflect.Field;

import com.rongdu.loans.exception.BizException;


public interface Handler {

    public void validate(AnnotationValidable validatedObj, Field field) throws BizException;
}
