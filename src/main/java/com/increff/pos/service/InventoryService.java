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
	public void add(InventoryPojo inventorypojo) throws ApiException {
		dao.insert(inventorypojo);
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
	public void update(int id, InventoryPojo inventorypojo) throws ApiException {
		InventoryPojo newInventorypojo = checkId(id);
		newInventorypojo.setQuantity(inventorypojo.getQuantity());
		dao.update(newInventorypojo);
	}
	
	@Transactional(rollbackOn  = ApiException.class)
	public void order(InventoryPojo inventorypojo) throws ApiException {
		InventoryPojo inventory = checkId(inventorypojo.getId());
		if(inventory.getQuantity()-inventorypojo.getQuantity()<0)
		{
			throw new ApiException("Inventory only has "+inventory.getQuantity());
		}
		inventory.setQuantity(inventory.getQuantity()-inventorypojo.getQuantity());
		dao.update(inventory);
	}

	@Transactional
	public InventoryPojo checkId(int id) throws ApiException {
		InventoryPojo inventorypojo = dao.select(id);
		if (inventorypojo == null) {
			throw new ApiException("Inventory with given id does not exist, id: " + id);
		}
		return inventorypojo;
	}

	
}
