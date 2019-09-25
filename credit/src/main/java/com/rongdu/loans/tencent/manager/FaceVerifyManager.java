/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tencent.manager;

import com.rongdu.common.service.BaseManager;
import com.rongdu.loans.tencent.dao.FaceVerifyDao;
import com.rongdu.loans.tencent.entity.FaceVerify;
import org.springframework.stereotype.Service;

/**
 * 腾讯人脸验证-实体管理实现类
 * @author sunda
 * @version 2017-08-16
 */
@Service("faceVerifyManager")
public class FaceVerifyManager extends BaseManager<FaceVerifyDao, FaceVerify, String>{
	
}