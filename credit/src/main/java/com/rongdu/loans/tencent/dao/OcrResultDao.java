/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.dao;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.tencent.entity.OcrResult;

/**
 * 腾讯身份证OCR识别结果-数据访问接口
 * @author sunda
 * @version 2017-08-14
 */
@MyBatisDao
public interface OcrResultDao extends BaseDao<OcrResult,String> {
	
}