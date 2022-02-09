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
		Validate.isEmpty(f);
		ProductPojo p=productservice.fetchProduct(f.getBarcode());
		InventoryPojo i=inventoryservice.get(p.getId());
		if(i==null)
		{
			throw new ApiException("Given product is not present in Inventory");
		}
		if(i.getQuantity()<f.getQuantity())
		{
			throw new ApiException("The given quantity of product is not present in inventory \n Invemtory quantity :"+i.getQuantity());
		}
		return Convertor.convert(p,f.getQuantity(),i.getQuantity());
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(List<ItemForm> f) throws ApiException, IOException, FOPException, TransformerException {
		OrdersPojo p=Convertor.convert();
		orderservice.add(p);
		InventoryPojo inventory=new InventoryPojo();
		PDFConvertor.jaxbObjectToXML(Convertor.convert(p.getId(),p.getTime(),f));
		PDFConvertor.convertToPDF();
		for(ItemForm i : f) {
			Validate.isEmpty(i);
			inventory.setId(i.getProductId());
			inventory.setQuantity(i.getQuantity());
			inventoryservice.order(inventory);
			orderitemservice.add(Convertor.convert(i,p.getId()));
		}
	}
}
