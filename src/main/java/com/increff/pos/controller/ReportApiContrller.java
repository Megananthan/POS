package com.increff.pos.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.ReportDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ReportForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ReportApiContrller {

	@Autowired
	private ReportDto dto;

	@ApiOperation(value = "Gets list of all inventory")
	@RequestMapping(path = "/api/report/inventory", method = RequestMethod.GET)
	public List<InventoryData> getInventory() throws ApiException {
		return(dto.getInventory());
	}
	
	@ApiOperation(value = "Gets list of all product")
	@RequestMapping(path = "/api/report/product", method = RequestMethod.GET)
	public List<ProductData> getProduct() throws ApiException {
		return(dto.getProduct());
	}
	
	@ApiOperation(value = "Gets list of all brand")
	@RequestMapping(path = "/api/report/brand", method = RequestMethod.GET)
	public List<BrandData> getBrand() throws ApiException {
		return(dto.getBrand());
	}
	
	@ApiOperation(value = "Gets list of all order in the given period")
	@RequestMapping(path = "/api/report", method = RequestMethod.POST)
	public List<OrderItemData> getOrderItem(@RequestBody ReportForm form) throws ApiException, ParseException {
		return(dto.getOrderItem(form));
	}	
}
