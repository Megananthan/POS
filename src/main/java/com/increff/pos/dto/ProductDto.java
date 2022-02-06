package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;

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
		ProductPojo p=Convertor.convert(f,b.getId());
		Normalizer.normalize(p);
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
		ProductPojo p=Convertor.convert(f,b.getId());
		Normalizer.normalize(p);
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
		return(Convertor.convert(p,b.getBrand(),b.getCategory()));
	}

}
