package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;

@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandPojo b) throws ApiException {
		checkBrandCategoryExist(b);
		dao.insert(b);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		return checkId(id);
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}
	
	@Transactional
	public List<BrandPojo> getAllCategory(String brand) {
		return dao.selectAllCategory(brand);
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, BrandPojo b) throws ApiException {
		BrandPojo newBrand = checkId(id);
		checkBrandCategoryExist(b);
		newBrand.setBrand(b.getBrand());
		newBrand.setCategory(b.getCategory());
		dao.update(newBrand);
	}
	
	@Transactional
	public void checkBrandCategoryExist(BrandPojo b) throws ApiException {
		if(!(checkBrandCategory(b.getBrand(),b.getCategory())==null))
		{
			throw new ApiException("Brand category pair already exist");
		}
		
	}
	

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo checkId(int id) throws ApiException {
		BrandPojo b = dao.select(id);
		if (b == null) {
			throw new ApiException("Brand with given ID does not exist, id: " + id);
		}
		return b;
	}
	
	@Transactional
	public BrandPojo checkBrandCategory(String brand,String category){
		BrandPojo b=dao.selectBrandCategory(brand,category);
		return b;
	}

	
}
