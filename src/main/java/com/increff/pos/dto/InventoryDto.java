package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.helper.Convertor;
import com.increff.pos.helper.Normalizer;
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
		ProductPojo p=productservice.getByName(inventorydata.getName());
		if(p==null)
		{
			throw new ApiException("Product doesnot exist");
		}
		InventoryForm f=new InventoryForm();
		f.setId(p.getId());
		InventoryPojo i=inventoryservice.get(p.getId());
		if(i==null)
		{	
			f.setQuantity(inventorydata.getQuantity());
			inventoryservice.add(Convertor.convert(f));
		}
		else {
			
			int quantity=i.getQuantity()+inventorydata.getQuantity();
			f.setQuantity(quantity);
			update(f.getId(),f);
		}
	}
	
	public void delete(int id) {
		inventoryservice.delete(id);
	}
	
	public InventoryData get(int id) throws ApiException {
		InventoryPojo i = inventoryservice.get(id);
		ProductPojo p=productservice.get(id);
		return Convertor.convert(i,p.getName());
	}
	
	public List<InventoryData> getAll() throws ApiException {
		List<InventoryPojo> listpojo = inventoryservice.getAll();
		List<InventoryData> listdata = new ArrayList<InventoryData>();
		for (InventoryPojo i : listpojo) {
			ProductPojo p=productservice.get(i.getId());
			listdata.add(Convertor.convert(i,p.getName()));
		}
		return listdata;
	}
	
	public void update(int id,InventoryForm f) throws ApiException {
		InventoryPojo b = Convertor.convert(f);
		inventoryservice.update(id, b);
	}
	
}
