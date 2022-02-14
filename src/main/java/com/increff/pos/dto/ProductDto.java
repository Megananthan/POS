package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.helper.Validate;
import com.increff.pos.model.InventoryForm;
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
	public void add(ProductForm productform) throws ApiException {
		Normalizer.normalize(productform);
		Validate.isEmpty(productform);
		BrandPojo brandpojo=fetchBrand(productform);
		ProductPojo productpojo=Convertor.convert(productform,brandpojo.getId());
		productservice.add(productpojo);
		InventoryForm inventoryform=new InventoryForm();
		inventoryform.setId(productpojo.getId());
		inventoryform.setQuantity(0);
		inventoryservice.add(Convertor.convert(inventoryform));
	}
	
	public ProductData get(int id) throws ApiException {
		ProductPojo productpojo = productservice.get(id);
		return fetchProduct(productpojo);
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
	public void update(int id,ProductForm productform) throws ApiException {
		Normalizer.normalize(productform);
		Validate.isEmpty(productform);
		BrandPojo brandpojo=fetchBrand(productform);
		ProductPojo productpojo=Convertor.convert(productform,brandpojo.getId());
		productservice.update(id,productpojo);
	}
	
	public BrandPojo fetchBrand(ProductForm productform) throws ApiException
	{
		BrandPojo brandpojo=brandservice.checkBrandCategory(productform.getBrand(), productform.getCategory());
		if (brandpojo== null) {
			throw new ApiException("Given Brand Category pair does not exist \n brand: " + productform.getBrand()+" category: "+ productform.getCategory());
		}
		return brandpojo;
	}
	
	public ProductData fetchProduct(ProductPojo productpojo) throws ApiException
	{
		BrandPojo brandpojo=brandservice.get(productpojo.getBrand_category());
		return(Convertor.convert(productpojo,brandpojo.getBrand(),brandpojo.getCategory()));
	}
}
