package com.rongdu.loans.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rongdu.common.mapper.BeanMapper;
import com.rongdu.loans.basic.dao.StoreDAO;
import com.rongdu.loans.basic.dao.SysAreaDAO;
import com.rongdu.loans.basic.entity.Store;
import com.rongdu.loans.basic.service.StoreService;
import com.rongdu.loans.basic.vo.StoreVO;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	/**
	 * 门店-实体管理接口
	 */
	@Autowired
	private StoreDAO storeDAO;

	/**
	 * 地区-实体管理接口
	 */
	@Autowired
	private SysAreaDAO sysAreaDAO;

	@Override
	public List<StoreVO> getAllStore(String areaId,String productId) {
		List<Store> list = storeDAO.getAllStore(areaId,productId);
		return BeanMapper.mapList(list, StoreVO.class);
	}

	@Override
	public List<StoreVO> getAllGroup(String storeId) {
		List<Store> list = storeDAO.getAllGroup(storeId);
		return BeanMapper.mapList(list, StoreVO.class);
	}

	@Override
	public List<StoreVO> getAllArea() {
		List<Store> list = sysAreaDAO.getAllArea();
		return BeanMapper.mapList(list, StoreVO.class);
	}

	@Override
	public StoreVO getBycompayId(String companyId) {
		StoreVO storeVO = new StoreVO();
		Store store = storeDAO.getBycompayId(companyId);
		if (store != null) {
			BeanMapper.copy(store, storeVO);
		}
		return storeVO;
	}

	@Override
	public List<StoreVO> getAllTopCompany() {
		List<Store> list = storeDAO.getAllTopCompany();
		return BeanMapper.mapList(list, StoreVO.class);
	}

	@Override
	public List<StoreVO> getStoreByAreaAndCompany(String areaId, String companyId) {
		List<Store> list = storeDAO.getStoreByAreaAndCompany(areaId,companyId);
		return BeanMapper.mapList(list, StoreVO.class);
	}
}
