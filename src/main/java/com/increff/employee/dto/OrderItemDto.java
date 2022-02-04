package com.increff.employee.dto;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.helper.Convertor;
import com.increff.employee.helper.Normalizer;
import com.increff.employee.model.ItemData;
import com.increff.employee.model.ItemForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrdersPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrdersService;
import com.increff.employee.service.ProductService;

@Service
public class OrderItemDto {
	
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private InventoryService inventoryservice;
	
	@Autowired
	private OrderItemService orderitemservice;
	
	@Autowired
	private OrdersService orderservice;
	
	
	@Transactional(rollbackOn = ApiException.class)
	public ItemData checkbarcode(OrderItemForm f) throws ApiException {
		Normalizer.normalize(f);
		ProductPojo p=productservice.fetchProduct(f.getBarcode());
		InventoryPojo i=inventoryservice.get(p.getId());
		if(i.getQuantity()<f.getQuantity())
		{
			throw new ApiException("The given quantity of product is not present in inventory \n Invemtory quantity :"+i.getQuantity());
		}
		return Convertor.convert(p,f.getQuantity(),i.getQuantity());
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(List<ItemForm> f) throws ApiException, IOException {
		OrdersPojo p=Convertor.convert();
		orderservice.add(p);
		InventoryPojo inventory=new InventoryPojo();
		ConvertorJavaToXML.jaxbObjectToXML(Convertor.convert(p.getId(),p.getTime(),f));
		ConvertorJavaToXML.generatePDF();
		for(ItemForm i : f) {
			inventory.setId(i.getProductId());
			inventory.setQuantity(i.getQuantity());
			inventoryservice.order(inventory);
			orderitemservice.add(Convertor.convert(i,p.getId()));
		}
		
	}
	
	
//	@Transactional(rollbackOn = ApiException.class)
//	public void download() throws ApiException, IOException {
//		ConvertorJavaToXML.doGet();
//	}
//	
//	public void delete(int id) {
//		orderitemservice.delete(id);
//	}
	
//	public OrderItemData get(int id) throws ApiException {
//		OrderItemPojo p = orderitemservice.get(id);
//		return Convertor.convert(p);
//	}
	
//	public List<OrderItemData> getAll() throws ApiException {
//		List<OrderItemPojo> listpojo = orderitemservice.getAll();
//		List<OrderItemData> listdata = new ArrayList<OrderItemData>();
//		for (OrderItemPojo p : listpojo) {
//			listdata.add(Convertor.convert(p));
//		}
//		return listdata;
//	}
		
	
	
	
	
	


}
