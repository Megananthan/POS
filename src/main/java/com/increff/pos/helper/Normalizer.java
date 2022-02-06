package com.increff.pos.helper;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;

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
