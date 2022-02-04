package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.Convertor;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;

@Service
public class ReportDto {

	@Autowired
	private BrandService brandservice;

	@Autowired
	private ProductService productservice;

	@Autowired
	private InventoryService inventoryservice;

	public List<InventoryData> getInventory() throws ApiException {
		
		List<InventoryPojo> listpojo = inventoryservice.getAll();
		List<InventoryData> listdata = new ArrayList<InventoryData>();
		for (InventoryPojo i : listpojo) {
			ProductPojo p=productservice.get(i.getId());
			listdata.add(Convertor.convert(i,p.getName()));
		}
		return listdata;
		
	}

	public List<ProductData> getProduct() throws ApiException {
		
		List<ProductPojo> listpojo = productservice.getAll();
		List<ProductData> listdata = new ArrayList<ProductData>();
		for (ProductPojo p : listpojo) {
			listdata.add(fetchProduct(p));
		}
		return listdata;

	}

	public List<BrandData> getBrand() throws ApiException {
		List<BrandPojo> listpojo = brandservice.getAll();
		List<BrandData> listdata = new ArrayList<BrandData>();
		for (BrandPojo p : listpojo) {
			listdata.add(Convertor.convert(p));
		}
		return listdata;
	}
	
	
	public ProductData fetchProduct(ProductPojo p) throws ApiException
	{
		BrandPojo b=brandservice.get(p.getBrand_category());
		return(Convertor.convert(p,b.getBrand(),b.getCategory()));
	}
	
//	public OrderItemData getOrderItem(ReportForm form)
//	{
//		
//	}
	

//	public void add(BrandForm brandform) throws ApiException {
//		BrandPojo b=Convertor.convert(brandform);
//		Normalizer.normalize(b);
//		brandservice.add(b);
//	}
//	
//	public void delete(int id) {
//		brandservice.delete(id);
//	}
//	
//	public BrandData get(int id) throws ApiException {
//		BrandPojo b = brandservice.get(id);
//		return Convertor.convert(b);
//	}
//	
//	public List<BrandData> getAll() {
//		List<BrandPojo> listpojo = brandservice.getAll();
//		List<BrandData> listdata = new ArrayList<BrandData>();
//		for (BrandPojo p : listpojo) {
//			listdata.add(Convertor.convert(p));
//		}
//		return listdata;
//	}
//	
//	public void update(int id,BrandForm f) throws ApiException {
//		BrandPojo b = Convertor.convert(f);
//		Normalizer.normalize(b);
//		brandservice.update(id, b);
//	}
}
