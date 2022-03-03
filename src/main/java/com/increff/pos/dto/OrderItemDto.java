package com.increff.pos.dto;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.helper.Validate;
import com.increff.pos.model.ItemData;
import com.increff.pos.model.ItemForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrdersPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrdersService;
import com.increff.pos.service.ProductService;

@Service
public class OrderItemDto {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private OrderItemService orderitemService;
	
	@Autowired
	private OrdersService orderService;
	
	@Transactional(rollbackOn = ApiException.class)
	public ItemData checkbarcode(OrderItemForm orderItemForm) throws ApiException {
		Normalizer.normalize(orderItemForm);
		Validate.isEmpty(orderItemForm);
		ProductPojo productPojo=productService.fetchProduct(orderItemForm.getBarcode());
		InventoryPojo inventoryPojo=inventoryService.get(productPojo.getId());
		if(inventoryPojo==null)
		{
			throw new ApiException("Given product is not present in Inventory");
		}
		if(inventoryPojo.getQuantity()<orderItemForm.getQuantity())
		{
			throw new ApiException("The given quantity of product is not present in inventory \n Invemtory quantity :"+inventoryPojo.getQuantity());
		}
		return Convertor.convert(productPojo,orderItemForm.getQuantity(),inventoryPojo.getQuantity());
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public int add(List<ItemForm> itemForm) throws ApiException, IOException, FOPException, TransformerException {
		OrdersPojo orderPojo=Convertor.convert();
		orderService.add(orderPojo);
		InventoryPojo inventory=new InventoryPojo();
		PDFConvertor.jaxbObjectToXML(Convertor.convert(orderPojo.getId(),orderPojo.getTime(),itemForm));
		PDFConvertor.convertToPDF();
		for(ItemForm i : itemForm) {
			Validate.isEmpty(i);
			inventory.setId(i.getProductId());
			inventory.setQuantity(i.getQuantity());
			inventoryService.order(inventory);
			orderitemService.add(Convertor.convert(i,orderPojo.getId()));
		}
		return orderPojo.getId();
	}
}
