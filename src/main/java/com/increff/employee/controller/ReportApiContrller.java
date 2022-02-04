package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.ReportDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ReportForm;
import com.increff.employee.service.ApiException;

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
	public List<OrderItemData> getOrderItem(@RequestBody ReportForm form) throws ApiException {
		return(dto.getOrderItem(form));
	}	
	
//	@ApiOperation(value = "Deletes a brand")
//	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable int id) {
//		dto.delete(id);
//	}
//
//	@ApiOperation(value = "Gets a brand by ID")
//	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
//	public BrandData get(@PathVariable int id) throws ApiException {
//		return(dto.get(id)); 
//	}
//
//	@ApiOperation(value = "Gets list of all employees")
//	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
//	public List<BrandData> getAll() {
//		return(dto.getAll());
//	}
//
//	@ApiOperation(value = "Updates an employee")
//	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
//	public void update(@PathVariable int id, @RequestBody BrandForm brandform) throws ApiException {
//		dto.update(id, brandform);
//	}

}
