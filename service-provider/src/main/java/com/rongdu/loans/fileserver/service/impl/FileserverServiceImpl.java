package com.rongdu.loans.fileserver.service.impl;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rongdu.common.file.FileBizTypeMap;
import com.rongdu.common.file.FileType;
import com.rongdu.common.service.BaseService;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.Encodes;
import com.rongdu.common.utils.FileUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.common.utils.OSSUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.FileInfoManager;
import com.rongdu.loans.fileserver.option.UploadFileOP;
import com.rongdu.loans.fileserver.service.FileserverService;

/**
 * 
* @Description:  
* @author: 饶文彪
* @date 2018年6月25日 下午2:07:14
 */
@Service("fileserverService")
public class FileserverServiceImpl  extends BaseService implements FileserverService {

	private static final String DOCTYPE = "doc|pdf|txt|xls|xlsx|json|csv";
	private static final String IMGTYPE = "jpg|jpeg|png|bmp";
	private static final String VIDEOTYPE = "mp4|flv";
	private static final String SOURSETYPE = "1|2|3|4|5";

	@Autowired
	private FileInfoManager fileInfoManager;

	@Override
	public String uploadFile(UploadFileOP vo) {

		Assert.notNull(vo.getUserId(), "用户ID不能为空");
		Assert.notNull(vo.getOrigName(), "文件原名不能为空");
		Assert.notNull(vo.getContent(), "附件不能为空");
		Assert.notNull(vo.getBizCode(), "业务类型不能为空");
		Assert.notNull(vo.getFileType(), "文件类型不能为空");
		Assert.notNull(vo.getSource(), "文件来源不能为空");
		Assert.isTrue(SOURSETYPE.contains(vo.getSource()), "文件来源有误");

		String fileExt = StringUtils.substringAfterLast(vo.getOrigName(), ".");
		fileExt = StringUtils.lowerCase(fileExt);

		if (vo.getFileType().equals(FileType.DOC.getType())) {
			Assert.isTrue(DOCTYPE.contains(fileExt), "文档只支持：" + DOCTYPE);
		} else if (vo.getFileType().equals(FileType.IMG.getType())) {
			Assert.isTrue(IMGTYPE.contains(fileExt), "图片只支持：" + IMGTYPE);
		} else if (vo.getFileType().equals(FileType.VIDEO.getType())) {
			Assert.isTrue(VIDEOTYPE.contains(fileExt), "视频只支持：" + VIDEOTYPE);
		}

		FileInfo entity = null;
		String rtnUrl = null;

		entity = new FileInfo();
		String bizName = FileBizTypeMap.getBizName(vo.getBizCode());
		String origName = vo.getOrigName();
		origName = Encodes.urlDecode(origName);
		long fileSize = vo.getFileSize();
		String fileSizeDesc = FileUtils.formatFileSize(fileSize);

		String id = IdGen.uuid();
		String fileName = id + "." + fileExt;
		String relativePath = vo.getFileType().toLowerCase() + "/" + vo.getBizCode() + "/"
				+ DateUtils.getDate("yyyy/MM/dd") + "/" + fileName;

		// String url = "http://"+server+relativePath;
		// 上传附件到OSS
		rtnUrl = OSSUtils.uploadFile(new ByteArrayInputStream(vo.getContent()), relativePath);
		entity.setUrl(rtnUrl);

		Assert.notNull(rtnUrl, "附件上传OSS失败");

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
		entity.setServer(OSSUtils.SERVER);
		entity.setAbsolutePath(null);
		entity.setRelativePath(relativePath);
		entity.setStatus(0);
		entity.setIp(vo.getIp());
		entity.setId(id);
		entity.setIsNewRecord(true);

		fileInfoManager.insert(entity);

		return rtnUrl;
	}

	@Override
	public int updateUserIdByOrderSn(String userId, String orderSn, String bizCode) {
		return fileInfoManager.updateUserIdByOrderSn(userId, orderSn, bizCode);
	}

}
