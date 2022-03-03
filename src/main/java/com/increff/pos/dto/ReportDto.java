package com.increff.pos.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.helper.Validate;
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
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private OrderItemService orderitemService;

	public List<InventoryData> getInventory() throws ApiException {
		
		List<InventoryPojo> listPojo = inventoryService.getAll();
		List<InventoryData> listData = new ArrayList<InventoryData>();
		for (InventoryPojo inventoryPojo : listPojo) {
			ProductPojo productPojo=productService.get(inventoryPojo.getId());
			listData.add(Convertor.convert(inventoryPojo,productPojo.getName(),productPojo.getBarcode()));
		}
		return listData;
		
	}

	public List<ProductData> getProduct() throws ApiException {
		
		List<ProductPojo> listPojo = productService.getAll();
		List<ProductData> listData = new ArrayList<ProductData>();
		for (ProductPojo productPojo : listPojo) {
			listData.add(fetchProduct(productPojo));
		}
		return listData;

	}

	public List<BrandData> getBrand() throws ApiException {
		List<BrandPojo> listPojo = brandService.getAll();
		List<BrandData> listData = new ArrayList<BrandData>();
		for (BrandPojo brandPojo : listPojo) {
			listData.add(Convertor.convert(brandPojo));
		}
		return listData;
	}
	
	
	public ProductData fetchProduct(ProductPojo productPojo) throws ApiException
	{
		BrandPojo brandPojo=brandService.get(productPojo.getBrand_category());
		return(Convertor.convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory()));
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public List<OrderItemData> getOrderItem(ReportForm reportForm) throws ApiException, ParseException
	{   
		Normalizer.normalize(reportForm);
		Validate.isEmpty(reportForm);
		List<OrderItemPojo> listPojo = orderitemService.getOrderItemList(reportForm);
		List<OrderItemData> listData = new ArrayList<OrderItemData>();
		for (OrderItemPojo orderItemPojo : listPojo) {
			ProductPojo productPojo=productService.get(orderItemPojo.getProductId());
			BrandPojo brandPojo=brandService.get(productPojo.getBrand_category());
			listData.add(Convertor.convert(orderItemPojo,productPojo.getBarcode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory()));
		}
		return listData;
	}
	


}
