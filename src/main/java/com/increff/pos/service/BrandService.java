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
	public void add(BrandPojo brandPojo) throws ApiException {
		checkBrandCategoryExist(brandPojo);
		dao.insert(brandPojo);
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
	public List<String> getAllBrand() {
		return dao.selectAllBrand();
	}
	
	@Transactional
	public List<String> getAllCategory() {
		return dao.selectAllCategory();
	}
	
	@Transactional
	public List<String> getCategoryWithBrand(String brand) {
		return dao.selectCategoryWithBrand(brand);
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, BrandPojo brandPojo) throws ApiException {
		BrandPojo newBrand = checkId(id);
		checkBrandCategoryExist(brandPojo);
		newBrand.setBrand(brandPojo.getBrand());
		newBrand.setCategory(brandPojo.getCategory());
		dao.update(newBrand);
	}
	
	@Transactional
	public void checkBrandCategoryExist(BrandPojo brandPojo) throws ApiException {
		if(!(checkBrandCategory(brandPojo.getBrand(),brandPojo.getCategory())==null))
		{
			throw new ApiException("Brand category pair already exist");
		}
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo checkId(int id) throws ApiException {
		BrandPojo brandPojo = dao.select(id);
		if (brandPojo == null) {
			throw new ApiException("Brand with given ID does not exist, id: " + id);
		}
		return brandPojo;
	}
	
	@Transactional
	public BrandPojo checkBrandCategory(String brand,String category){
		BrandPojo brandPojo=dao.selectBrandCategory(brand,category);
		return brandPojo;
	}
}
