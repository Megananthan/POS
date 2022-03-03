package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(InventoryPojo inventoryPojo) throws ApiException {
		dao.insert(inventoryPojo);
	}
	

	@Transactional(rollbackOn = ApiException.class)
	public InventoryPojo get(int id) throws ApiException {
		return dao.select(id);
	}

	@Transactional
	public List<InventoryPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, InventoryPojo inventoryPojo) throws ApiException {
		InventoryPojo newInventoryPojo = checkId(id);
		newInventoryPojo.setQuantity(inventoryPojo.getQuantity());
		dao.update(newInventoryPojo);
	}
	
	@Transactional(rollbackOn  = ApiException.class)
	public void order(InventoryPojo inventoryPojo) throws ApiException {
		InventoryPojo inventory = checkId(inventoryPojo.getId());
		if(inventory.getQuantity()-inventoryPojo.getQuantity()<0)
		{
			throw new ApiException("Inventory only has "+inventory.getQuantity());
		}
		inventory.setQuantity(inventory.getQuantity()-inventoryPojo.getQuantity());
		dao.update(inventory);
	}

	@Transactional
	public InventoryPojo checkId(int id) throws ApiException {
		InventoryPojo inventoryPojo = dao.select(id);
		if (inventoryPojo == null) {
			throw new ApiException("Inventory with given id does not exist, id: " + id);
		}
		return inventoryPojo;
	}

	
}
