package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest {
	@Autowired
	private BrandService service;
	
	@Autowired
	private BrandDao dao;
	
	@Test
	public void testAdd() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		service.add(brandpojo);
		BrandPojo db=dao.selectAll().get(0);
		assertEquals(brandpojo.getBrand(),db.getBrand());
		assertEquals(brandpojo.getCategory(),db.getCategory());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		BrandPojo result=service.get(brandpojo.getId());
		assertEquals(result.getBrand(),brandpojo.getBrand());
		assertEquals(result.getCategory(),brandpojo.getCategory());
	}
	
	@Test
	public void testGetAll() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(service.getAll().size(),dao.selectAll().size());
	}
	
	@Test
	public void testGetAllBrand() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(service.getAllBrand().size(),dao.selectAllBrand().size());
	}
	
	@Test
	public void testGetAllCategory() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(service.getAllCategory().size(),dao.selectAllCategory().size());
	}
	
	@Test
	public void testGetCategoryWithBrand() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(service.getCategoryWithBrand(brandpojo.getBrand()).get(0),brandpojo.getCategory());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		BrandPojo updated_brandpojo = new BrandPojo();
		updated_brandpojo.setBrand("nike");
		updated_brandpojo.setCategory("footwear");
		service.update(brandpojo.getId(),updated_brandpojo);
		BrandPojo result=dao.select(brandpojo.getId());
		assertEquals(result.getBrand(),updated_brandpojo.getBrand());
		assertEquals(result.getCategory(),updated_brandpojo.getCategory());
	}

//	@Test
//	public void testCheckBrandCategoryPair() throws ApiException {
//		BrandPojo brandpojo = new BrandPojo();
//		brandpojo.setBrand("puma");
//		brandpojo.setCategory("cloth");
//		dao.insert(brandpojo);
//		assertEquals(ApiException.class,service.checkBrandCategory(brandpojo.getBrand(), brandpojo.getCategory()));
//	}
}
