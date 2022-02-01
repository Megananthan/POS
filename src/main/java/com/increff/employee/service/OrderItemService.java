package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderItemPojo p) throws ApiException {
//		checkBarcode(p);
		dao.insert(p);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo get(int id) throws ApiException {
		return checkId(id);
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}

//	@Transactional(rollbackOn  = ApiException.class)
//	public void update(int id, OrderItemPojo p) throws ApiException {
//		OrderItemPojo newOrders = checkId(id);
//		checkBarcode(p);
//		newOrders.setProductId(p.getProductId());
//		newOrders.setQuantity(p.getQuantity());
//		dao.update(newOrders);
//	}
	
//	@Transactional
//	public void checkBarcode(OrderItemPojo p) throws ApiException
//	{
//		if(!(dao.selectBarcode(p.getBarcode())==null))
//		{
//			throw new ApiException("Barcode already exist");
//		}
//	}

	@Transactional
	public OrderItemPojo checkId(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order with given ID does not exist, id: " + id);
		}
		return p;
	}
	
//	@Transactional
//	public int fetchId(String barcode) throws ApiException
//	{
//		ProductPojo p=dao.selectBarcode(barcode);
//		return(p.getId());
//		
//	}
	
//	@Transactional
//	public ProductPojo getByName(String name) throws ApiException
//	{
//		ProductPojo p=dao.selectName(name);
//		return(p);
//		
//	}
	
}
