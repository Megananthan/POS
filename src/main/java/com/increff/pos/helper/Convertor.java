package com.increff.pos.helper;

import java.time.LocalDateTime;
import java.util.List;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.ItemData;
import com.increff.pos.model.ItemForm;
import com.increff.pos.model.OrderDetail;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrdersPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

public class Convertor {

	public static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());
		return d;
	}

	public static BrandPojo convert(BrandForm b) {
		BrandPojo p = new BrandPojo();
		p.setBrand(b.getBrand());
		p.setCategory(b.getCategory());
		return p;
	}

	public static ProductData convert(ProductPojo p, String brand, String category) throws ApiException {
		ProductData d = new ProductData();
		d.setId(p.getId());
		d.setBarcode(p.getBarcode());
		d.setBrand(brand);
		d.setCategory(category);
		d.setName(p.getName());
		d.setMrp(p.getMrp());
		return d;
	}

	public static ProductPojo convert(ProductForm f, int id) {
		ProductPojo p = new ProductPojo();
		p.setBarcode(f.getBarcode());
		p.setBrand_category(id);
		p.setName(f.getName());
		p.setMrp(f.getMrp());
		return p;
	}

	public static InventoryData convert(InventoryPojo i, String name,String barcode) {
		InventoryData d = new InventoryData();// data
		d.setQuantity(i.getQuantity());
		d.setId(i.getId());
		d.setName(name);
		d.setBarcode(barcode);
		return d;
	}

	public static InventoryPojo convert(InventoryForm f) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(f.getQuantity());
		p.setId(f.getId());
		return p;
	}

	public static OrderItemPojo convert(ItemForm f, int order_id) {
		OrderItemPojo p = new OrderItemPojo();
		p.setOrdersId(order_id);
		p.setProductId(f.getProductId());
		p.setSellingPrice(f.getMrp());
		p.setQuantity(f.getQuantity());
		return p;
	}

	public static ItemData convert(ProductPojo p, int quantity, int inventory) {
		ItemData d = new ItemData();
		d.setBarcode(p.getBarcode());
		d.setName(p.getName());
		d.setQuantity(quantity);
		d.setMrp(p.getMrp());
		d.setInventory(inventory);
		d.setProductId(p.getId());
		return d;
	}

	public static OrdersPojo convert() {
		OrdersPojo p = new OrdersPojo();
		LocalDateTime date_time = LocalDateTime.now();
		p.setTime(date_time);
		return p;
	}

	public static OrderDetail convert(int order_id,LocalDateTime time,List<ItemForm> form) {
		double total=0;
		OrderDetail d = new OrderDetail();
		d.setOrderId(order_id);
		d.setTime(time);
		d.setItem(form);
		for(ItemForm i :form) {
			total+=i.getMrp()*i.getQuantity();
		}
		d.setTotal(total);
		return d;
	}
	
	public static OrderItemData convert(OrderItemPojo p,String barcode,String name,String brand,String category) {
		OrderItemData d = new OrderItemData();
		d.setOrderId(p.getOrdersId());
		d.setName(name);
		d.setBarcode(barcode);
		d.setQuantity(p.getQuantity());
		d.setBrand(brand);
		d.setCategory(category);
		d.setMrp(p.getSellingPrice());
		return d;
	}
	
   

}
