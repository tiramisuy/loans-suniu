/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tencent.dao.OcrResultDao;
import com.rongdu.loans.tencent.entity.OcrResult;
import org.springframework.stereotype.Service;

/**
 * 腾讯身份证OCR识别结果-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("ocrResultManager")
public class OcrResultManager extends BaseManager<OcrResultDao, OcrResult, String>{
	
}