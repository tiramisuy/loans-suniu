package com.rongdu.loans.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.OSSUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.web.BaseController;

/**
 * 
* @Description:  文件上传
* @author: RaoWenbiao
* @date 2018年9月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/upload")
public class UploadControl extends BaseController {

	@ResponseBody
	@RequestMapping(value = "/logo", method = RequestMethod.POST)
	public String logo(@RequestParam("file") CommonsMultipartFile file) {

		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		fileExt = StringUtils.lowerCase(fileExt);
		String id = IdGen.uuid();
		String fileName = id + "." + fileExt;

		String relativePath = "img/product_logo/" + DateUtils.getDate("yyyy/MM/dd") + "/" + fileName;

		String url = null;

		try {
			url = OSSUtils.uploadFile(file.getInputStream(), relativePath);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "{\"url\":\"" + url + "\"}";
	}
}
