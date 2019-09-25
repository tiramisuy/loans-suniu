/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rongdu.common.persistence.BaseDao;
import com.rongdu.common.persistence.annotation.MyBatisDao;
import com.rongdu.loans.basic.entity.FileInfo;

/**
 * 影像资料-数据访问接口
 * 
 * @author zhangxiaolong
 * @version 2017-07-13
 */
@MyBatisDao
public interface FileInfoDAO extends BaseDao<FileInfo, String> {

	/**
	 * 查询最新的一条数据
	 * 
	 * @param userId
	 * @param bizCode
	 * @return
	 */
	FileInfo getLastFile(@Param("userId") String userId, @Param("bizCode") String bizCode,
			@Param("fileType") String fileType);

	/**
	 * 查询所有数据
	 * 
	 * @param userId
	 * @param bizCode
	 * @return
	 */
	List<FileInfo> getAllFile(@Param("userId") String userId, @Param("bizCode") String bizCode,
			@Param("fileType") String fileType);

	/**
	 * 查询通讯录附件数据
	 * 
	 * @param userId
	 * @param bizCode
	 * @return
	 */
	List<FileInfo> getContactFileList(@Param("userId") String userId, @Param("bizCode") String bizCode);

	/**
	 * 删除一条通讯录附件
	 * 
	 * @return
	 */
	int deleteTruely(@Param("id") String id);

	/**
	 * 查询订单指定类型最新的一条数据
	 * 
	 * @param userId
	 * @param bizCode
	 * @return
	 */
	FileInfo getLastFileByApplyId(@Param("applyId") String applyId, @Param("bizCode") String bizCode,
			@Param("fileType") String fileType);

	int updateUserIdByOrderSn(@Param("userId") String userId, @Param("orderSn") String orderSn,@Param("bizCode") String bizCode);
}