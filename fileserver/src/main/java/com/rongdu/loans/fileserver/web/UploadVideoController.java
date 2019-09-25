/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.loans.fileserver.web;

import com.rongdu.common.config.Global;
import com.rongdu.common.file.FileBizTypeMap;
import com.rongdu.common.utils.*;
import com.rongdu.common.web.BaseController;
import com.rongdu.common.web.Servlets;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.service.FileInfoService;
import com.rongdu.loans.fileserver.common.BadRequestException;
import com.rongdu.loans.fileserver.option.UploadVideoOP;
import com.rongdu.loans.sys.web.ApiResult;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;

/**
 * 上传视频
 * @author sunda
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/anon/")
public class UploadVideoController extends BaseController {

	@Autowired
	private FileInfoService fileInfoService;
	
	/**
	 * 上传视频
	 * @param vo
	 * @param errors
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	public ApiResult uploadImage(HttpServletRequest request,@Valid UploadVideoOP vo,Errors errors) throws UnsupportedEncodingException {
		if (errors.hasErrors()) {
			throw new BadRequestException(errors);
		}
		String ipAddr = Servlets.getIpAddress(request);
		logger.debug("视频上传：来源于{}",ipAddr);
		MultipartFile file = vo.getFile();
		FileInfo entity = createFileInfo(vo);
		
		
		if(StringUtils.isBlank(entity.getAbsolutePath())){//OSS上传成功
			fileInfoService.insert(entity);
			// 初始化返回对象
			ApiResult result = new ApiResult();
			result.setData(entity);
			return result;
		}
		
		InputStream input = null;
		OutputStream output = null;
		try {
			File target = FileUtils.forceCreateFile(entity.getAbsolutePath());
			input = file.getInputStream();
			output = new FileOutputStream(target);
			IOUtils.copy(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		fileInfoService.insert(entity);
		// 初始化返回对象
		ApiResult result = new ApiResult();
		result.setData(entity);
		return result;
	}

	private FileInfo createFileInfo(UploadVideoOP vo) {
		MultipartFile file = vo.getFile();		
		FileInfo entity = new FileInfo();		
		String bizName = FileBizTypeMap.getBizName(vo.getBizCode());
		String origName = vo.getOrigName();
		origName = Encodes.urlDecode(origName);
		long fileSize = file.getSize();
		String fileSizeDesc = FileUtils.formatFileSize(fileSize);
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		fileExt = StringUtils.lowerCase(fileExt);
		String id = IdGen.uuid();
		String fileName = id +"."+fileExt;
		String relativePath = vo.getFileType().toLowerCase()+"/"+vo.getBizCode()+"/"+DateUtils.getDate("yyyy/MM/dd")+"/"+fileName;
		String server = Global.getConfig("fileserver.server");
		String rootDir = Global.getConfig("fileserver.rootDir");
		String absolutePath = rootDir + "/" + relativePath;
		String url = null;
		
		try {
			url = OSSUtils.uploadFile(file.getInputStream(),relativePath);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		entity.setUserId(vo.getUserId());
		entity.setApplyId(vo.getApplyId());
		entity.setSource(Integer.parseInt(vo.getSource()));
		entity.setBizCode(vo.getBizCode());
		entity.setBizName(bizName);
		entity.setOrigName(origName);
		entity.setFileExt(fileExt);
		entity.setFileSize(fileSize);
		entity.setFileSizeDesc(fileSizeDesc);
		entity.setFileName(fileName);
		entity.setFileType(vo.getFileType());
		entity.setUrl(url);
		entity.setServer(server);
		entity.setRelativePath(relativePath);
		entity.setStatus(0);
		entity.setIp(vo.getIp());
		entity.setId(id);
		entity.setIsNewRecord(true);
		entity.setRemark(vo.getRemark());

		if(null == url){//OSS上传失败
			entity.setUrl("http://"+server+"/" +relativePath);
			entity.setServer(server);
			entity.setAbsolutePath(absolutePath);
		}
		
		return entity;
	}
	
}