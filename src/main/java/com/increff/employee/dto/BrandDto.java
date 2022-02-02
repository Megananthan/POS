package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.Convertor;
import com.increff.employee.helper.Normalizer;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;

@Service
public class BrandDto {
	
	@Autowired
	private BrandService brandservice;
	
	public void add(BrandForm brandform) throws ApiException {
		BrandPojo b=Convertor.convert(brandform);
		Normalizer.normalize(b);
		brandservice.add(b);
	}
	
	public void delete(int id) {
		brandservice.delete(id);
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
		BrandPojo b = Convertor.convert(f);
		Normalizer.normalize(b);
		brandservice.update(id, b);
	}
}
