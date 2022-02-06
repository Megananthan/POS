package com.increff.pos.helper;

import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.ItemForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;

public class Validate {
	
	public static void isEmpty(BrandForm f) throws ApiException {
		if(f.getBrand().isEmpty())
		{
			throw new ApiException("Brand name cannot be empty");
		}
		if(f.getCategory().isEmpty())
		{
			throw new ApiException("Category name cannot be empty");
		}
	}
	
	public static void isEmpty(InventoryData d) throws ApiException {
		if(d.getName().isEmpty())
		{
			throw new ApiException("Product name cannot be empty");
		}
		if(d.getId()<1)
		{
			throw new ApiException("ID should be greater than 0");
		}
		if(d.getQuantity()<1)
		{
			throw new ApiException("Quantity should be greater than 0");
		}
	}
	
	public static void isEmpty(InventoryForm f) throws ApiException {
		if(f.getId()<1)
		{
			throw new ApiException("ID should be greater than 0");
		}
		if(f.getQuantity()<1)
		{
			throw new ApiException("Quantity should be greater than 0");
		}
	}
	
	public static void isEmpty(OrderItemForm f) throws ApiException {
		if(f.getBarcode().isEmpty())
		{
			throw new ApiException("Barcode cannot be empty");
		}
		if(f.getQuantity()<1)
		{
			throw new ApiException("Quantity should be greater than 0");
		}
	}
	
	public static void isEmpty(ItemForm f) throws ApiException {
		if(f.getBarcode().isEmpty())
		{
			throw new ApiException("Barcode cannot be empty");
		}
		if(f.getName().isEmpty())
		{
			throw new ApiException("Product name cannot be empty");
		}
		if(f.getMrp()<0)
		{
			throw new ApiException("MRP should be greater than or equal to 0");
		}
		if(f.getQuantity()<1)
		{
			throw new ApiException("Quantity should be greater than 0");
		}
		if(f.getInventory()<0)
		{
			throw new ApiException("Inventory cannot be less than 0");
		}
		if(f.getProductId()<1)
		{
			throw new ApiException("Product ID should be greater than 0");
		}
		
	}
	
	public static void isEmpty(ProductForm f) throws ApiException {
		if(f.getBarcode().isEmpty())
		{
			throw new ApiException("Barcode cannot be empty");
		}
		if(f.getName().isEmpty())
		{
			throw new ApiException("Product name cannot be empty");
		}
		if(f.getMrp()<0)
		{
			throw new ApiException("MRP should be greater than or equal to 0");
		}
		if(f.getBrand().isEmpty())
		{
			throw new ApiException("Brand name cannot be empty");
		}
		if(f.getCategory().isEmpty())
		{
			throw new ApiException("Category name cannot be empty");
		}
	}
	
	

}
