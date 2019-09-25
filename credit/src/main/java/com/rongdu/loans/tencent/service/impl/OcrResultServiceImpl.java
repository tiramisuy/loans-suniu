/**
 * Copyright 2015-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.service.impl;

import com.rongdu.common.service.BaseService;
import com.rongdu.loans.tencent.manager.OcrResultManager;
import com.rongdu.loans.tencent.service.OcrResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 腾讯身份证OCR识别结果-业务逻辑实现类
 * @author sunda
 * @version 2017-08-14
 */
@Service("ocrResultService")
public class OcrResultServiceImpl  extends BaseService implements  OcrResultService{
	
	/**
 	* 腾讯身份证OCR识别结果-实体管理接口
 	*/
	@Autowired
	private OcrResultManager ocrResultManager;
	
}