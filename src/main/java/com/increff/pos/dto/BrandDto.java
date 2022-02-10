package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.helper.Validate;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;

@Service
public class BrandDto {
	
	@Autowired
	private BrandService brandservice;
	
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private InventoryService inventoryservice;
	
	public void add(BrandForm brandform) throws ApiException {
		Normalizer.normalize(brandform);
		Validate.isEmpty(brandform);
		BrandPojo brandpojo=Convertor.convert(brandform);
		brandservice.add(brandpojo);
	}
	
	public void delete(int id) {
		brandservice.delete(id);
		List<ProductPojo> productpojoList=productservice.getAllWithBrandCategory(id);
		for(ProductPojo productpojo:productpojoList) {
			productservice.delete(productpojo.getId());
			inventoryservice.delete(productpojo.getId());
		}
		
	}
	
	public BrandData get(int id) throws ApiException {
		BrandPojo brandpojo = brandservice.get(id);
		return Convertor.convert(brandpojo);
	}
	
	public List<BrandData> getAll() {
		List<BrandPojo> listpojo = brandservice.getAll();
		List<BrandData> listdata = new ArrayList<BrandData>();
		for (BrandPojo p : listpojo) {
			listdata.add(Convertor.convert(p));
		}
		return listdata;
	}
	
	public List<String> getAllBrand() {
		return brandservice.getAllBrand();
	}
	
	public List<String> getAllCategory() {
		return brandservice.getAllCategory();
	}
	
	
	public List<String> getCategoryWithBrand(String brand) {
		return brandservice.getCategoryWithBrand(brand);
	}
	
	public void update(int id,BrandForm brandform) throws ApiException {
		Normalizer.normalize(brandform);
		Validate.isEmpty(brandform);
		BrandPojo brandpojo = Convertor.convert(brandform);
		brandservice.update(id, brandpojo);
	}
}
