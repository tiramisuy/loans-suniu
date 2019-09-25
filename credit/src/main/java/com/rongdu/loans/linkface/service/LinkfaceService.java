package com.rongdu.loans.linkface.service;

import com.rongdu.loans.linkface.vo.IdnumberVerificationOP;
import com.rongdu.loans.linkface.vo.IdnumberVerificationVO;
/**
 * 
* @Description:  商汤接口
* @author: 饶文彪
* @date 2018年7月2日 下午3:03:22
 */
public interface LinkfaceService {
	/**
	 * 
	* @Title: idnumberVerification
	* @Description: 商汤人脸识别
	* @param op
	* @return    设定文件
	* @return IdnumberVerificationVO    返回类型
	* @throws
	 */
	IdnumberVerificationVO idnumberVerification(IdnumberVerificationOP op);
}
