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
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;

@Service
public class BrandDto {
	
	@Autowired
	private BrandService brandService;
	
	public void add(BrandForm brandForm) throws ApiException {
		Normalizer.normalize(brandForm);
		Validate.isEmpty(brandForm);
		BrandPojo brandPojo=Convertor.convert(brandForm);
		brandService.add(brandPojo);
	}
	
	public BrandData get(int id) throws ApiException {
		BrandPojo brandPojo = brandService.get(id);
		return Convertor.convert(brandPojo);
	}
	
	public List<BrandData> getAll() {
		List<BrandPojo> listPojo = brandService.getAll();
		List<BrandData> listData = new ArrayList<BrandData>();
		for (BrandPojo brandPojo : listPojo) {
			listData.add(Convertor.convert(brandPojo));
		}
		return listData;
	}
	
	public List<String> getAllBrand() {
		return brandService.getAllBrand();
	}
	
	public List<String> getAllCategory() {
		return brandService.getAllCategory();
	}
	
	
	public List<String> getCategoryWithBrand(String brand) {
		return brandService.getCategoryWithBrand(brand);
	}
	
	public void update(int id,BrandForm brandForm) throws ApiException {
		Normalizer.normalize(brandForm);
		Validate.isEmpty(brandForm);
		BrandPojo brandPojo = Convertor.convert(brandForm);
		brandService.update(id, brandPojo);
	}
}
