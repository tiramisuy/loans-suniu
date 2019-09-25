/**
 * Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.file.FileBizCode;
import com.rongdu.common.file.FileType;
import com.rongdu.common.service.BaseManager;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.basic.dao.FileInfoDAO;
import com.rongdu.loans.basic.entity.FileInfo;

/**
 * 影像资料-实体管理接口
 *
 * @author zhangxiaolong
 * @version 2017-07-13
 */
@Service("fileInfoManager")
public class FileInfoManager extends BaseManager<FileInfoDAO, FileInfo, String> {

    @Autowired
    private FileInfoDAO fileInfoDAO;

    /**
     * 查询用户指定类型的最新数据
     *
     * @param userId
     * @param bizCode
     * @return
     */
    public FileInfo getLastFile(String userId, String bizCode, String fileType) {
        return fileInfoDAO.getLastFile(userId, bizCode, fileType);
    }

    /**
     * 查询用户指定类型的所有数据
     *
     * @param userId
     * @param bizCode
     * @return
     */
    public List<FileInfo> getAllFile(String userId, String bizCode, String fileType) {
        return fileInfoDAO.getAllFile(userId, bizCode, fileType);
    }

    /**
     * 查询用户指定类型的最新数据
     *
     * @param userId
     * @return
     */
    public List<FileInfo> getLastFileByUserId(String userId, List<String> bizCodeList) {
        List<FileInfo> list = new ArrayList<>();
        for (String bizCode : bizCodeList) {
            String fileType = FileType.IMG.getType();
            // if (StringUtils.equals(FileBizCode.FACE_VERIFY.getBizCode(),
            // bizCode)){
            // fileType = FileType.VIDEO.getType();
            // }
            FileInfo fileInfo = getLastFile(userId, bizCode, fileType);
            if (fileInfo != null) {
                list.add(fileInfo);
            }
        }
        return list;
    }

    /**
     * 查询通讯录附件所有数据
     *
     * @param userId
     * @param bizCode
     * @return
     */
    public List<FileInfo> getContactFileList(String userId, String bizCode) {
        return fileInfoDAO.getContactFileList(userId, bizCode);
    }

    /**
     * 删除通讯录附件
     */
    public int deleteTruely(String id) {
        return fileInfoDAO.deleteTruely(id);
    }

    /**
     * 查询订单指定类型的最新数据
     *
     * @param applyId
     * @param bizCode
     * @param fileType
     * @return
     */
    public FileInfo getLastFileByApplyId(String applyId, String bizCode, String fileType) {
        return fileInfoDAO.getLastFileByApplyId(applyId, bizCode, fileType);
    }

    public int updateUserIdByOrderSn(String userId, String orderSn, String bizCode) {
        return fileInfoDAO.updateUserIdByOrderSn(userId, orderSn, bizCode);
    }
}