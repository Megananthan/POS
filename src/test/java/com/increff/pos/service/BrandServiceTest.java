package com.increff.pos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		service.add(brandPojo);
		BrandPojo result=dao.selectAll().get(0);
		assertEquals(brandPojo.getBrand(),result.getBrand());
		assertEquals(brandPojo.getCategory(),result.getCategory());
	}
	
	@Test
	public void testAddNegative() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		service.add(brandPojo);
		try {
			service.add(brandPojo);
			fail("Brand category already exist");
		}
		catch(ApiException e){
			assertEquals(e.getMessage(),"Brand category pair already exist");
		}
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		BrandPojo result=service.get(brandPojo.getId());
		assertEquals(result.getBrand(),brandPojo.getBrand());
		assertEquals(result.getCategory(),brandPojo.getCategory());
	}
	
	@Test
	public void testGetNegative() throws ApiException {
		try {
			service.get(12);
			fail("Brand category does not exist");
		}
		catch(ApiException e){
			assertEquals(e.getMessage(),"Brand with given ID does not exist, id: 12");
		}
		
	}
	
	@Test
	public void testGetAll() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(service.getAll().size(),dao.selectAll().size());
	}
	
	@Test
	public void testGetAllBrand() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(service.getAllBrand().size(),dao.selectAllBrand().size());
	}
	
	@Test
	public void testGetAllCategory() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(service.getAllCategory().size(),dao.selectAllCategory().size());
	}
	
	@Test
	public void testGetCategoryWithBrand() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(service.getCategoryWithBrand(brandPojo.getBrand()).get(0),brandPojo.getCategory());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		BrandPojo updatedBrandPojo = new BrandPojo();
		updatedBrandPojo.setBrand("nike");
		updatedBrandPojo.setCategory("footwear");
		service.update(brandPojo.getId(),updatedBrandPojo);
		BrandPojo result=dao.select(brandPojo.getId());
		assertEquals(result.getBrand(),updatedBrandPojo.getBrand());
		assertEquals(result.getCategory(),updatedBrandPojo.getCategory());
	}
}
