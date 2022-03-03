package com.increff.pos.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.ItemData;
import com.increff.pos.model.ItemForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {
	
	@Autowired
	private OrderItemDto dto;
	
	@ApiOperation(value = "Check an order")
	@RequestMapping(path = "/api/orderitem/check", method = RequestMethod.POST)
	public ItemData checkbarcode(@RequestBody OrderItemForm form) throws ApiException {
		return dto.checkbarcode(form);
	}

	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/orderitem/order", method = RequestMethod.POST)
	public int add(@RequestBody List<ItemForm> form) throws ApiException, IOException, FOPException, TransformerException {
		return dto.add(form);
	}
}
