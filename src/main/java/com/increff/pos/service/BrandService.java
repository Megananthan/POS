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
	public void add(BrandPojo brandpojo) throws ApiException {
		checkBrandCategoryExist(brandpojo);
		dao.insert(brandpojo);
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
	public void update(int id, BrandPojo brandpojo) throws ApiException {
		BrandPojo newBrand = checkId(id);
		checkBrandCategoryExist(brandpojo);
		newBrand.setBrand(brandpojo.getBrand());
		newBrand.setCategory(brandpojo.getCategory());
		dao.update(newBrand);
	}
	
	@Transactional
	public void checkBrandCategoryExist(BrandPojo brandpojo) throws ApiException {
		if(!(checkBrandCategory(brandpojo.getBrand(),brandpojo.getCategory())==null))
		{
			throw new ApiException("Brand category pair already exist");
		}
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo checkId(int id) throws ApiException {
		BrandPojo brandpojo = dao.select(id);
		if (brandpojo == null) {
			throw new ApiException("Brand with given ID does not exist, id: " + id);
		}
		return brandpojo;
	}
	
	@Transactional
	public BrandPojo checkBrandCategory(String brand,String category){
		BrandPojo b=dao.selectBrandCategory(brand,category);
		return b;
	}
}
