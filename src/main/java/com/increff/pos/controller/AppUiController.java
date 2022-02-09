package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.increff.pos.service.ApiException;

@Controller
public class AppUiController extends AbstractUiController {
	
	
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}
	
	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}
	
	@RequestMapping(value="/ui/product")
	public ModelAndView product()
	{
		return mav("product.html");
	}
	
	@RequestMapping(value="/ui/inventory")
	public ModelAndView inventory()
	{
		return mav("inventory.html");
	}
	
	@RequestMapping(value="/ui/orderitem")
	public ModelAndView orders() throws ApiException
	{
		return mav("orderitem.html");
	}
	
	@RequestMapping(value="/ui/report")
	public ModelAndView report() throws ApiException
	{
		return mav("report.html");
	}

}
