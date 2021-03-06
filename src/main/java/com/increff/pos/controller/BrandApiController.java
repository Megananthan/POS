package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandApiController {

	@Autowired
	private BrandDto dto;

	@ApiOperation(value = "Adds a brand")
	@RequestMapping(path = "/api/brand", method = RequestMethod.POST)
	public void add(@RequestBody BrandForm brandform) throws ApiException {
		dto.add(brandform);
	}

	@ApiOperation(value = "Gets a brand by ID")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		return(dto.get(id)); 
	}

	@ApiOperation(value = "Gets list of all Brand and category")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		return(dto.getAll());
	}
	
	@ApiOperation(value = "Gets list of all Brands")
	@RequestMapping(path = "/api/brandList", method = RequestMethod.GET)
	public List<String> getAllBrand() {
		return(dto.getAllBrand());
	}
	
	@ApiOperation(value = "Gets list of all Category")
	@RequestMapping(path = "/api/category", method = RequestMethod.GET)
	public List<String> getAllCategory() {
		return(dto.getAllCategory());
	}
	
	@ApiOperation(value = "Gets list of all Category with brand")
	@RequestMapping(path = "/api/category/{brand}", method = RequestMethod.GET)
	public List<String> getCategoryWithBrand(@PathVariable String brand) {
		return(dto.getCategoryWithBrand(brand));
	}
	
	@ApiOperation(value = "Updates an brand")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm brandform) throws ApiException {
		dto.update(id, brandform);
	}

}
