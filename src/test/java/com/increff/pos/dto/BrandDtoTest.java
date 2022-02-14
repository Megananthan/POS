package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;

public class BrandDtoTest extends AbstractUnitTest {
	@Autowired
	private BrandDto dto;
	
	@Autowired
	private BrandDao dao;
	
	@Test
	public void testAdd() throws ApiException {
		BrandForm brandform = new BrandForm();
		brandform.setBrand("    PuMa");
		brandform.setCategory(" cLoTh ");
		dto.add(brandform);
		BrandPojo db=dao.selectAll().get(0);
		assertEquals("puma",db.getBrand());
		assertEquals("cloth",db.getCategory());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		BrandData result=dto.get(brandpojo.getId());
		assertEquals(result.getBrand(),brandpojo.getBrand());
		assertEquals(result.getCategory(),brandpojo.getCategory());
	}
	
	@Test
	public void testGetAll() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(dto.getAll().size(),dao.selectAll().size());
	}
	
	@Test
	public void testGetAllBrand() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(dto.getAllBrand().size(),dao.selectAllBrand().size());
	}
	
	@Test
	public void testGetAllCategory() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(dto.getAllCategory().size(),dao.selectAllCategory().size());
	}
	
	@Test
	public void testGetCategoryWithBrand() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		assertEquals(dto.getCategoryWithBrand(brandpojo.getBrand()).get(0),brandpojo.getCategory());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo brandpojo = new BrandPojo();
		brandpojo.setBrand("puma");
		brandpojo.setCategory("cloth");
		dao.insert(brandpojo);
		BrandForm updated_brandform = new BrandForm();
		updated_brandform.setBrand("nIKe");
		updated_brandform.setCategory("fOotWEar");
		dto.update(brandpojo.getId(),updated_brandform);
		BrandPojo result=dao.select(brandpojo.getId());
		assertEquals(result.getBrand(),"nike");
		assertEquals(result.getCategory(),"footwear");
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
