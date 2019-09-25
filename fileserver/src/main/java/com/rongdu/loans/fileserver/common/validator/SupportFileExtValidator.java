package com.rongdu.loans.fileserver.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.rongdu.common.utils.StringUtils;

/**
 * 通过注解定义支持的文件格式
 * @author sunda
 * version 2017-06-12
 */

public class SupportFileExtValidator implements ConstraintValidator<SupportFileExt, MultipartFile> {  
	
    private String regexp;  
    /** 
     * 初始参数,获取注解中regexp的值 
     */  
    @Override  
    public void initialize(SupportFileExt arg0) {  
        this.regexp = arg0.regexp();  
    }  
  
    @Override  
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		fileExt = StringUtils.lowerCase(fileExt);
        if(StringUtils.isNotBlank(fileExt)&&fileExt.matches(regexp)){  
        	return true;
        }
        return false;  
    }  
  
}  
