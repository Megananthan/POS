package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(InventoryPojo i) throws ApiException {
		dao.insert(i);
	}
	
	@Transactional
	public void delete(int id) {
		dao.delete(id);
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
	public void update(int id, InventoryPojo b) throws ApiException {
		InventoryPojo newInventory = checkId(id);
		newInventory.setQuantity(b.getQuantity());
		dao.update(newInventory);
	}
	
	@Transactional(rollbackOn  = ApiException.class)
	public void order(InventoryPojo p) throws ApiException {
		InventoryPojo i = checkId(p.getId());
		if(i.getQuantity()-p.getQuantity()<0)
		{
			throw new ApiException("Inventory only has "+i.getQuantity());
		}
		i.setQuantity(i.getQuantity()-p.getQuantity());
		dao.update(i);
	}

	
	
	@Transactional
	public InventoryPojo checkId(int id) throws ApiException {
		InventoryPojo b = dao.select(id);
		if (b == null) {
			throw new ApiException("Inventory with given id does not exist, id: " + id);
		}
		return b;
	}

	
}
