/*package com.rongdu.loans.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.security.Digests;
import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.FileUtils;
import com.rongdu.common.utils.IdGen;
import com.rongdu.loans.basic.entity.FileInfo;
import com.rongdu.loans.basic.manager.FileInfoManager;

public class MyBatisDaoTest extends SpringTransactionalContextTests {
	
	@Autowired
	private FileInfoManager fileInfoManager;
	
	@Test
	public void  findAllByCriteria(){
		System.out.println("==========findAllByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		List<FileInfo>  list = fileInfoManager.findAllByCriteria(criteria);
		System.out.println(JsonMapper.toJsonString(list));
	}
	
	@Test
	public void  findAllByCriteriaList(){
		System.out.println("==========findAllByCriteriaList==========");
		List<Criteria>  criteriaList = new ArrayList<Criteria>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		List<FileInfo>  list = fileInfoManager.findAllByCriteriaList(criteriaList);
		System.out.println(JsonMapper.toJsonString(list));
	}
	
	@Test
	public void  findPage(){
		System.out.println("==========findPage==========");
		Page<FileInfo> page = new Page<FileInfo>(); 
		page = fileInfoManager.findPage(page);
		System.out.println("findPage："+JsonMapper.toJsonString(page));
	}
	
	@Test
	public void  findPageByCriteria(){
		System.out.println("==========findPageByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		Page<FileInfo> page = new Page<FileInfo>(); 
		page.setPageSize(3);
		page.setOrderBy("id DESC");
		page = fileInfoManager.findPageByCriteria(page,criteria);
		System.out.println("findPageByCriteria："+JsonMapper.toJsonString(page));
	}
	
	@Test
	public void  findPageByCriteriaList(){
		System.out.println("==========findPageByCriteriaList==========");
		List<Criteria>  criteriaList = new ArrayList<Criteria>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		Page<FileInfo> page = new Page<FileInfo>(); 
		page.setPageSize(2);
		
		Page<FileInfo>  list = fileInfoManager.findPageByCriteriaList(page,criteriaList);
		System.out.println("findPageByCriteria："+JsonMapper.toJsonString(page));
	}
	
	@Test
	public void  countByCriteria(){
		System.out.println("==========countByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		long  num = fileInfoManager.countByCriteria(criteria);
		System.out.println(JsonMapper.toJsonString(num));
	}
	
	@Test
	public void  countByCriteriaList(){
		System.out.println("==========countByCriteriaList==========");
		List<Criteria>  criteriaList = new ArrayList<Criteria>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		long  num = fileInfoManager.countByCriteriaList(criteriaList);
		System.out.println(JsonMapper.toJsonString(num));
	}
	
	@Test
	public void  insert(){
		System.out.println("==========insert==========");
		FileInfo entity = new FileInfo();
		entity.setBizId("idcard_front");
		entity.setBizName("身份证正面");
		entity.setFileName(IdGen.uuid()+".png");
		entity.setOrigName("原图.png");
		entity.setFileType("img");
		entity.setFileExt("png");
		entity.setFileSize(1000000132L);
		entity.setFileSizeDesc(FileUtils.formatFileSize(entity.getFileSize()));
		entity.setServer("img1.rongdu.com");
		entity.setRelativePath("/"+entity.getBizId()+"/"+DateUtils.getDate("yyyy/MM/dd/")+entity.getFileName());
		entity.setAbsolutePath("/www/images"+entity.getRelativePath());
		entity.setUrl("http://"+entity.getServer()+entity.getRelativePath());
		entity.setIp("127.0.0.1");
		entity.setId(Digests.md5(entity.getUrl()));
		entity.preInsert();
		fileInfoManager.insert(entity);
	}
	
	@Test
	public void  insertBatch(){
		System.out.println("==========insertBatch==========");
		List<FileInfo> list =  new ArrayList<FileInfo>();
		for (int i = 0; i < 10; i++) {		
			FileInfo entity = new FileInfo();
			entity.setBizId("idcard_front");
			entity.setBizName("身份证正面");
			entity.setFileName(IdGen.uuid()+".png");
			entity.setOrigName("原图.png");
			entity.setFileType("img");
			entity.setFileExt("png");
			entity.setFileSize(1000000132L);
			entity.setFileSizeDesc(FileUtils.formatFileSize(entity.getFileSize()));
			entity.setServer("img1.rongdu.com");
			entity.setRelativePath("/"+entity.getBizId()+"/"+DateUtils.getDate("yyyy/MM/dd/")+entity.getFileName());
			entity.setAbsolutePath("/www/images"+entity.getRelativePath());
			entity.setUrl("http://"+entity.getServer()+entity.getRelativePath());
			entity.setIp("127.0.0.1");
			entity.setId(Digests.md5(entity.getUrl()));
			entity.preInsert();
			list.add(entity);
		}
		fileInfoManager.insertBatch(list);
	}
	
	@Test
	public void  get(){
		System.out.println("==========get==========");
		FileInfo entity = (FileInfo) fileInfoManager.getById("1");
		System.out.println(JsonMapper.toJsonString(entity));
	}
	
	@Test
	public void  delete(){
		System.out.println("==========delete==========");
		fileInfoManager.delete("1");
	}
	
	@Test
	public void  deleteTruely(){
		System.out.println("==========deleteTruely==========");
		fileInfoManager.deleteTruely("1");
	}
	
	@Test
	public void  deleteBatch(){
		System.out.println("==========deleteBatch==========");
		List<String> ids = Arrays.asList(new String[]{"1","2","3"});
		fileInfoManager.deleteBatch(ids);
	}
	
	
	@Test
	public void  getByCriteria(){
		System.out.println("==========getByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("id", "13"));
		FileInfo entity = (FileInfo)fileInfoManager.getByCriteria(criteria);
		System.out.println(JsonMapper.toJsonString(entity));
	}
	
	@Test
	public void  update(){
		System.out.println("==========update==========");
		FileInfo entity = (FileInfo) fileInfoManager.getById("1");
		entity.setBizName("测试");
		int  rowNum = fileInfoManager.update(entity);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  updateByIdSelective(){
		System.out.println("==========updateByIdSelective==========");
		FileInfo entity = new FileInfo();
		entity.setId("1");
		entity.setBizName("测试123");
		int  rowNum = fileInfoManager.updateByIdSelective(entity);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	
	@Test
	public void  updateByCriteriaSelective(){
		System.out.println("==========updateByCriteriaSelective==========");
		FileInfo entity = new FileInfo();
		entity.setBizName("测试");
		entity.setUpdateTime(new Date());
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		int  rowNum = fileInfoManager.updateByCriteriaSelective(entity, criteria);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  updateByCriteriaListSelective(){
		System.out.println("==========updateByCriteriaListSelective==========");
		FileInfo entity = new FileInfo();
		entity.setBizName("测试");
		entity.setUpdateTime(new Date());

		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		int  rowNum = fileInfoManager.updateByCriteriaListSelective(entity, criteriaList);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  deleteByCriteria(){
		System.out.println("==========deleteByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		int  rowNum = fileInfoManager.deleteByCriteria(criteria);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  deleteByCriteriaList(){
		System.out.println("==========deleteByCriteriaList==========");

		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		int  rowNum = fileInfoManager.deleteByCriteriaList(criteriaList);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  deleteTruelyByCriteria(){
		System.out.println("==========deleteTruelyByCriteria==========");
		Criteria criteria = new Criteria();
		criteria.add(Criterion.eq("status", 0));
		criteria.or(Criterion.eq("status", 1));
		int  rowNum = fileInfoManager.deleteTruelyByCriteria(criteria);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
	@Test
	public void  deleteTruelyByCriteriaList(){
		System.out.println("==========deleteTruelyByCriteriaList==========");

		List<Criteria>  criteriaList = new ArrayList<>();
		Criteria criteria1 = new Criteria();
		criteria1.add(Criterion.eq("status", 0));
		criteria1.or(Criterion.eq("status", 1));
		criteriaList.add(criteria1);
		
		Criteria criteria2 = new Criteria();
		criteria2.and(Criterion.eq("del", 0));
		criteriaList.add(criteria2);
		
		Criteria criteria3 = new Criteria();
		criteria3.and(Criterion.ne("file_ext", "jpg"));
		criteriaList.add(criteria3);
		
		int  rowNum = fileInfoManager.deleteTruelyByCriteriaList(criteriaList);
		System.out.println(JsonMapper.toJsonString(rowNum));
	}
	
}*/