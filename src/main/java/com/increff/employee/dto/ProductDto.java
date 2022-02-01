package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.Convert;
import com.increff.employee.helper.Normalize;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;

@Service
public class ProductDto {
	
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private BrandService brandservice;
	
	@Autowired
	private InventoryService inventoryservice;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(ProductForm f) throws ApiException {
		BrandPojo b=fetchBrand(f);
		ProductPojo p=Convert.convert(f,b.getId());
		Normalize.normalize(p);
		productservice.add(p);		
	}
	
	public void delete(int id) {
		productservice.delete(id);
		inventoryservice.delete(id);
	}
	
	public ProductData get(int id) throws ApiException {
		ProductPojo p = productservice.get(id);
		return fetchProduct(p);
	}
	
	public List<ProductData> getAll() throws ApiException {
		List<ProductPojo> listpojo = productservice.getAll();
		List<ProductData> listdata = new ArrayList<ProductData>();
		for (ProductPojo p : listpojo) {
			listdata.add(fetchProduct(p));
		}
		return listdata;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,ProductForm f) throws ApiException {
		BrandPojo b=fetchBrand(f);
		ProductPojo p=Convert.convert(f,b.getId());
		Normalize.normalize(p);
		productservice.update(id,p);
	}
	
	public BrandPojo fetchBrand(ProductForm f) throws ApiException
	{
		BrandPojo b=brandservice.checkBrandCategory(f.getBrand(), f.getCategory());
		if (b== null) {
			throw new ApiException("Given Brand Category pair does not exist \n brand: " + f.getBrand()+" category: "+ f.getCategory());
		}
		return b;
	}
	
	public ProductData fetchProduct(ProductPojo p) throws ApiException
	{
		BrandPojo b=brandservice.get(p.getBrand_category());
		return(Convert.convert(p,b.getBrand(),b.getCategory()));
	}

}
