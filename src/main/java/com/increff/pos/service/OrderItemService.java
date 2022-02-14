package com.increff.pos.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.ReportForm;
import com.increff.pos.pojo.OrderItemPojo;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderItemPojo p) throws ApiException {
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
	
	@Transactional
	public List<OrderItemPojo> getOrderItemList(ReportForm f) throws ApiException {
		List<Integer> list_id=dao.selectWithRange(f.getStartDate(),f.getEndDate(),f.getBrand(),f.getCategory());
		List<OrderItemPojo> result = new ArrayList<OrderItemPojo>();
		for(int i: list_id)
		{
			result.add(get(i));
		}
		return result;
	}

	@Transactional
	public OrderItemPojo checkId(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order with given ID does not exist, id: " + id);
		}
		return p;
	}
}
