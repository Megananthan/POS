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
	private InventoryService inventoryservice;
	
	@Autowired
	private ProductService productservice;
		
	@Transactional(rollbackOn = ApiException.class)
	public void add(InventoryData inventorydata) throws ApiException {
		Normalizer.normalize(inventorydata);
		Validate.isEmpty(inventorydata);
		ProductPojo productpojo=productservice.getByName(inventorydata.getName());
		if(productpojo==null)
		{
			throw new ApiException("Product doesnot exist");
		}
		InventoryForm inventoryform=new InventoryForm();
		inventoryform.setId(productpojo.getId());
		InventoryPojo inventorypojo=inventoryservice.get(productpojo.getId());
		if(inventorypojo==null)
		{	
			inventoryform.setQuantity(inventorydata.getQuantity());
			inventoryservice.add(Convertor.convert(inventoryform));
		}
		else 
		{	
			int quantity=inventorypojo.getQuantity()+inventorydata.getQuantity();
			inventoryform.setQuantity(quantity);
			update(inventoryform.getId(),inventoryform);
		}
	}
	
	public InventoryData get(int id) throws ApiException {
		InventoryPojo inventorypojo = inventoryservice.get(id);
		ProductPojo productpojo=productservice.get(id);
		return Convertor.convert(inventorypojo,productpojo.getName(),productpojo.getBarcode());
	}
	
	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> listpojo = inventoryservice.getAll();
		List<InventoryData> listdata = new ArrayList<InventoryData>();
		for (InventoryPojo inventorypojo : listpojo) {
			ProductPojo p=productservice.get(inventorypojo.getId());
			listdata.add(Convertor.convert(inventorypojo,p.getName(),p.getBarcode()));
		}
		return listdata;
	}
	
	public void update(int id,InventoryForm inventoryform) throws ApiException {
		Validate.isEmpty(inventoryform);
		InventoryPojo inventorypojo = Convertor.convert(inventoryform);
		inventoryservice.update(id, inventorypojo);
	}
	
}
