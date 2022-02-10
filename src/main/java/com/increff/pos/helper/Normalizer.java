package com.increff.pos.helper;

import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.model.ReportForm;
import com.increff.pos.util.StringUtil;

public class Normalizer {

	public static void normalize(BrandForm b) {
		b.setBrand(StringUtil.toLowerCase(b.getBrand()).trim());
		b.setCategory(StringUtil.toLowerCase(b.getCategory()).trim());
	}
	
	public static void normalize(ProductForm p) {
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()).trim());
		p.setName(StringUtil.toLowerCase(p.getName()).trim());
	}
	
	public static void normalize(InventoryData d) {
		d.setName(StringUtil.toLowerCase(d.getName()));
	}
	
	public static void normalize(OrderItemForm p) {
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()).trim());
	}
	
	public static void normalize(ReportForm f) {
		f.setBrand(StringUtil.toLowerCase(f.getBrand()).trim());
		f.setCategory(StringUtil.toLowerCase(f.getCategory()).trim());
	}
}
