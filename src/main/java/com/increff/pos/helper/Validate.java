package com.increff.pos.helper;

import com.increff.pos.model.BrandForm;
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

}
