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
		BrandForm brandForm = new BrandForm();
		brandForm.setBrand("    PuMa");
		brandForm.setCategory(" cLoTh ");
		dto.add(brandForm);
		BrandPojo brandPojo=dao.selectAll().get(0);
		assertEquals("puma",brandPojo.getBrand());
		assertEquals("cloth",brandPojo.getCategory());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		BrandData brandData=dto.get(brandPojo.getId());
		assertEquals(brandData.getBrand(),brandPojo.getBrand());
		assertEquals(brandData.getCategory(),brandPojo.getCategory());
	}
	
	@Test
	public void testGetAll() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(dto.getAll().size(),dao.selectAll().size());
	}
	
	@Test
	public void testGetAllBrand() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(dto.getAllBrand().size(),dao.selectAllBrand().size());
	}
	
	@Test
	public void testGetAllCategory() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(dto.getAllCategory().size(),dao.selectAllCategory().size());
	}
	
	@Test
	public void testGetCategoryWithBrand() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		assertEquals(dto.getCategoryWithBrand(brandPojo.getBrand()).get(0),brandPojo.getCategory());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("puma");
		brandPojo.setCategory("cloth");
		dao.insert(brandPojo);
		BrandForm updatedBrandForm = new BrandForm();
		updatedBrandForm.setBrand("nIKe");
		updatedBrandForm.setCategory("fOotWEar");
		dto.update(brandPojo.getId(),updatedBrandForm);
		BrandPojo result=dao.select(brandPojo.getId());
		assertEquals(result.getBrand(),"nike");
		assertEquals(result.getCategory(),"footwear");
	}

}
