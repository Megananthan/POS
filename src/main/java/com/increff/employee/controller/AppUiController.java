package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.service.ApiException;

@Controller
public class AppUiController extends AbstractUiController {
	
	
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
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
	
	@RequestMapping(value="/ui/orderitem",method = RequestMethod.GET)
	public ModelAndView orders() throws ApiException
	{
		return mav("orderitem.html");
	}

}