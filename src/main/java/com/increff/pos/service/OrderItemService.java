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
	public void add(OrderItemPojo orderItemPojo) throws ApiException {
		dao.insert(orderItemPojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo get(int id) throws ApiException {
		return checkId(id);
	}
	
	@Transactional
	public List<OrderItemPojo> getOrderItemList(ReportForm reportForm) throws ApiException {
		List<Integer> listId=dao.selectWithRange(reportForm.getStartDate(),reportForm.getEndDate(),reportForm.getBrand(),reportForm.getCategory());
		List<OrderItemPojo> result = new ArrayList<OrderItemPojo>();
		for(int i: listId)
		{
			result.add(get(i));
		}
		return result;
	}

	@Transactional
	public OrderItemPojo checkId(int id) throws ApiException {
		OrderItemPojo orderItemPojo = dao.select(id);
		if (orderItemPojo == null) {
			throw new ApiException("Order with given ID does not exist, id: " + id);
		}
		return orderItemPojo;
	}
}
