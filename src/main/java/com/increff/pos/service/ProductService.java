package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(ProductPojo productPojo) throws ApiException {
		checkBarcode(productPojo);
		dao.insert(productPojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		return checkId(id);
	}

	@Transactional
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}
	

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, ProductPojo productPojo) throws ApiException {
		ProductPojo newProductPojo = checkId(id);
		if(!(productPojo.getBarcode().equals(newProductPojo.getBarcode())))
			{
			checkBarcode(productPojo);
			}
		newProductPojo.setBarcode(productPojo.getBarcode());
		newProductPojo.setBrand_category(productPojo.getBrand_category());
		newProductPojo.setName(productPojo.getName());
		newProductPojo.setMrp(productPojo.getMrp());
		dao.update(newProductPojo);
	}
	
	@Transactional
	public void checkBarcode(ProductPojo productPojo) throws ApiException
	{
		if(!(dao.selectBarcode(productPojo.getBarcode())==null))
		{
			throw new ApiException("Barcode already exist");
		}
	}

	@Transactional
	public ProductPojo checkId(int id) throws ApiException {
		ProductPojo productPojo = dao.select(id);
		if (productPojo == null) {
			throw new ApiException("Product with given ID does not exist, id: " + id);
		}
		return productPojo;
	}
	
	@Transactional
	public ProductPojo fetchProduct(String barcode) throws ApiException
	{
		ProductPojo productPojo=dao.selectBarcode(barcode);
		if(productPojo==null)
		{
			throw new ApiException("Given barcode doesnot exist");
		}
		return(productPojo);
		
	}
	
	@Transactional
	public ProductPojo getByName(String name) throws ApiException
	{
		ProductPojo productPojo=dao.selectName(name);
		return(productPojo);
		
	}
	
}
