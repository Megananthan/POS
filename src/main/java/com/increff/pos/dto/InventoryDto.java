package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
import com.increff.pos.helper.Validate;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;

@Service
public class InventoryDto {
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ProductService productService;
		
	@Transactional(rollbackOn = ApiException.class)
	public void add(InventoryData inventoryData) throws ApiException {
		Normalizer.normalize(inventoryData);
		Validate.isEmpty(inventoryData);
		ProductPojo productPojo=productService.getByName(inventoryData.getName());
		if(productPojo==null)
		{
			throw new ApiException("Product doesnot exist");
		}
		InventoryForm inventoryForm=new InventoryForm();
		inventoryForm.setId(productPojo.getId());
		InventoryPojo inventoryPojo=inventoryService.get(productPojo.getId());
		if(inventoryPojo==null)
		{	
			inventoryForm.setQuantity(inventoryData.getQuantity());
			inventoryService.add(Convertor.convert(inventoryForm));
		}
		else 
		{	
			int quantity=inventoryPojo.getQuantity()+inventoryData.getQuantity();
			inventoryForm.setQuantity(quantity);
			update(inventoryForm.getId(),inventoryForm);
		}
	}
	
	public InventoryData get(int id) throws ApiException {
		InventoryPojo inventoryPojo = inventoryService.get(id);
		ProductPojo productPojo=productService.get(id);
		return Convertor.convert(inventoryPojo,productPojo.getName(),productPojo.getBarcode());
	}
	
	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> listPojo = inventoryService.getAll();
		List<InventoryData> listData = new ArrayList<InventoryData>();
		for (InventoryPojo inventoryPojo : listPojo) {
			ProductPojo productPojo=productService.get(inventoryPojo.getId());
			listData.add(Convertor.convert(inventoryPojo,productPojo.getName(),productPojo.getBarcode()));
		}
		return listData;
	}
	
	public void update(int id,InventoryForm inventoryForm) throws ApiException {
		Validate.isEmpty(inventoryForm);
		InventoryPojo inventoryPojo = Convertor.convert(inventoryForm);
		inventoryService.update(id, inventoryPojo);
	}
	
}
