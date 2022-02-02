package com.increff.employee.helper;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

public class Normalizer {

	public static void normalize(BrandPojo b) {
		b.setBrand(StringUtil.toLowerCase(b.getBrand()).trim());
		b.setCategory(StringUtil.toLowerCase(b.getCategory()).trim());
	}
	
	public static void normalize(ProductPojo p) {
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()).trim());
		p.setName(StringUtil.toLowerCase(p.getName()).trim());
	}
	
	public static void normalize(InventoryData d) {
		d.setName(StringUtil.toLowerCase(d.getName()));
	}
	
	public static void normalize(OrderItemForm p) {
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()).trim());
	}
	
}
