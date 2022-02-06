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
		Validate.isEmpty(brandform);
		BrandPojo b=Convertor.convert(brandform);
		Normalizer.normalize(b);
		brandservice.add(b);
	}
	
	public void delete(int id) {
		brandservice.delete(id);
		List<ProductPojo> p=productservice.getAllWithBrandCategory(id);
		for(ProductPojo i:p) {
			productservice.delete(i.getId());
			inventoryservice.delete(i.getId());
		}
		
	}
	
	public BrandData get(int id) throws ApiException {
		BrandPojo b = brandservice.get(id);
		return Convertor.convert(b);
	}
	
	public List<BrandData> getAll() {
		List<BrandPojo> listpojo = brandservice.getAll();
		List<BrandData> listdata = new ArrayList<BrandData>();
		for (BrandPojo p : listpojo) {
			listdata.add(Convertor.convert(p));
		}
		return listdata;
	}
	
	public void update(int id,BrandForm f) throws ApiException {
		Validate.isEmpty(f);
		BrandPojo b = Convertor.convert(f);
		Normalizer.normalize(b);
		brandservice.update(id, b);
	}
}
