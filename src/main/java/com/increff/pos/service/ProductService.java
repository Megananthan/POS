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
	public void add(ProductPojo productpojo) throws ApiException {
		checkBarcode(productpojo);
		dao.insert(productpojo);
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
	public void update(int id, ProductPojo productpojo) throws ApiException {
		ProductPojo newProductpojo = checkId(id);
		if(!(productpojo.getBarcode().equals(newProductpojo.getBarcode())))
			{
			checkBarcode(productpojo);
			}
		newProductpojo.setBarcode(productpojo.getBarcode());
		newProductpojo.setBrand_category(productpojo.getBrand_category());
		newProductpojo.setName(productpojo.getName());
		newProductpojo.setMrp(productpojo.getMrp());
		dao.update(newProductpojo);
	}
	
	@Transactional
	public void checkBarcode(ProductPojo productpojo) throws ApiException
	{
		if(!(dao.selectBarcode(productpojo.getBarcode())==null))
		{
			throw new ApiException("Barcode already exist");
		}
	}

	@Transactional
	public ProductPojo checkId(int id) throws ApiException {
		ProductPojo productpojo = dao.select(id);
		if (productpojo == null) {
			throw new ApiException("Product with given ID does not exist, id: " + id);
		}
		return productpojo;
	}
	
	@Transactional
	public ProductPojo fetchProduct(String barcode) throws ApiException
	{
		ProductPojo productpojo=dao.selectBarcode(barcode);
		if(productpojo==null)
		{
			throw new ApiException("Given barcode doesnot exist");
		}
		return(productpojo);
		
	}
	
	@Transactional
	public ProductPojo getByName(String name) throws ApiException
	{
		ProductPojo productpojo=dao.selectName(name);
		return(productpojo);
		
	}
	
}
