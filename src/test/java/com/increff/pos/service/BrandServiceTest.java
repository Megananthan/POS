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

}
