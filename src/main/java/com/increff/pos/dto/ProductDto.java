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
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(ProductForm productForm) throws ApiException {
		Normalizer.normalize(productForm);
		Validate.isEmpty(productForm);
		BrandPojo brandPojo=fetchBrand(productForm);
		ProductPojo productPojo=Convertor.convert(productForm,brandPojo.getId());
		productService.add(productPojo);
		InventoryForm inventoryForm=new InventoryForm();
		inventoryForm.setId(productPojo.getId());
		inventoryForm.setQuantity(0);
		inventoryService.add(Convertor.convert(inventoryForm));
	}
	
	public ProductData get(int id) throws ApiException {
		ProductPojo productPojo = productService.get(id);
		return fetchProduct(productPojo);
	}
	
	public List<ProductData> getAll() throws ApiException {
		List<ProductPojo> listPojo = productService.getAll();
		List<ProductData> listData = new ArrayList<ProductData>();
		for (ProductPojo productPojo : listPojo) {
			listData.add(fetchProduct(productPojo));
		}
		return listData;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,ProductForm productForm) throws ApiException {
		Normalizer.normalize(productForm);
		Validate.isEmpty(productForm);
		BrandPojo brandPojo=fetchBrand(productForm);
		ProductPojo productPojo=Convertor.convert(productForm,brandPojo.getId());
		productService.update(id,productPojo);
	}
	
	public BrandPojo fetchBrand(ProductForm productForm) throws ApiException
	{
		BrandPojo brandPojo=brandService.checkBrandCategory(productForm.getBrand(), productForm.getCategory());
		if (brandPojo== null) {
			throw new ApiException("Given Brand Category pair does not exist \n brand: " + productForm.getBrand()+" category: "+ productForm.getCategory());
		}
		return brandPojo;
	}
	
	public ProductData fetchProduct(ProductPojo productPojo) throws ApiException
	{
		BrandPojo brandPojo=brandService.get(productPojo.getBrand_category());
		return(Convertor.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory()));
	}
}
