package com.increff.pos.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrdersDao;
import com.increff.pos.pojo.OrdersPojo;

@Service
public class OrdersService {

	@Autowired
	private OrdersDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrdersPojo p) throws ApiException {
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrdersPojo get(int id) throws ApiException {
		return checkId(id);
	}
	
	@Transactional
	public OrdersPojo checkId(int id) throws ApiException {
		OrdersPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order with given ID does not exist, id: " + id);
		}
		return p;
	}
	
}
