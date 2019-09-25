package com.rongdu.loans.fileserver.service;

import org.springframework.stereotype.Service;
import com.rongdu.loans.fileserver.option.UploadFileOP;

/**
 * 
* @Description:  上传附件
* @author: 饶文彪
* @date 2018年6月25日 下午1:52:57
 */
@Service
public interface FileserverService {

	/**
	 * 
	* @Title: uploadFile
	* @Description: 附件上传接口
	* @param vo 附件参数
	* @return    设定文件
	* @return String  文件URL
	* @throws
	 */
	String uploadFile(UploadFileOP vo);

	int updateUserIdByOrderSn(String userId, String orderSn, String bizCode);
}
