package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.ItemData;
import com.increff.employee.model.ItemForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {
	
	@Autowired
	private OrderItemDto dto;
	
	@ApiOperation(value = "Check an order")
	@RequestMapping(path = "/api/orderitem/check", method = RequestMethod.POST)
	public ItemData checkadd(@RequestBody OrderItemForm form) throws ApiException {
		return dto.checkadd(form);
	}

	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public void add(@RequestBody List<ItemForm> form) throws ApiException {
		dto.add(form);
	}

	
//	@ApiOperation(value = "Deletes an order")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable int id) {
//		dto.delete(id);
//	}

//	@ApiOperation(value = "Gets a order by ID")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
//	public OrderItemData get(@PathVariable int id) throws ApiException {
//		return dto.get(id);
//	}

//	@ApiOperation(value = "Gets list of all Orders")
//	@RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
//	public List<OrderItemData> getAll() throws ApiException {
//		return dto.getAll();
//	}
//
//	@ApiOperation(value = "Updates an order")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
//	public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
//		dto.update(id,f);
//	}
	
}
