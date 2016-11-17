package com.redshark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redshark.dao.CatalogDao;
import com.redshark.model.CatalogBean;
import com.redshark.service.CatalogService;



@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class CatalogServiceImpl implements CatalogService {
	
	@Resource
	private CatalogDao dao;

	@Override
	public void save(CatalogBean catalog) {
		dao.save(catalog);
		
	}

	@Override
	public boolean update(CatalogBean bean) {
		return dao.update(bean);
	}

	@Override
	public boolean delete(String id) {
		return dao.delete(id);
	}

	@Override
	public CatalogBean findById(String id) {
		return dao.findById(id);
	}

	@Override
	public List<CatalogBean> findAll() {
		return dao.findAll();
	}

	@Override
	public void deleteBatch(String[] ids) {
		dao.deleteBatch(ids);
		
	}


}
