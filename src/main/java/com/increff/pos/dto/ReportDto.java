package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.ProductService;

@Service
public class ReportDto {

	@Autowired
	private BrandService brandservice;

	@Autowired
	private ProductService productservice;

	@Autowired
	private InventoryService inventoryservice;
	
	@Autowired
	private OrderItemService orderitemservice;

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
	
	public List<OrderItemData> getOrderItem(ReportForm form) throws ApiException
	{
		List<OrderItemPojo> listpojo = orderitemservice.getOrderItemList(form);
		List<OrderItemData> listdata = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : listpojo) {
			ProductPojo product=productservice.get(p.getProductId());
			BrandPojo brand=brandservice.get(product.getBrand_category());
			listdata.add(Convertor.convert(p,product.getBarcode(),product.getName(),brand.getBrand(),brand.getCategory()));
		}
		return listdata;
	}
	

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
