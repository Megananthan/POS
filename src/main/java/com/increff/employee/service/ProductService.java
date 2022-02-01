package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(ProductPojo p) throws ApiException {
		checkBarcode(p);
		dao.insert(p);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
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
	public void update(int id, ProductPojo p) throws ApiException {
		ProductPojo newProduct = checkId(id);
		if(!(p.getBarcode().equals(newProduct.getBarcode())))
			{
			checkBarcode(p);
			}
		newProduct.setBarcode(p.getBarcode());
		newProduct.setBrand_category(p.getBrand_category());
		newProduct.setName(p.getName());
		newProduct.setMrp(p.getMrp());
		dao.update(newProduct);
	}
	
	@Transactional
	public void checkBarcode(ProductPojo p) throws ApiException
	{
		if(!(dao.selectBarcode(p.getBarcode())==null))
		{
			throw new ApiException("Barcode already exist");
		}
	}

	@Transactional
	public ProductPojo checkId(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Product with given ID does not exist, id: " + id);
		}
		return p;
	}
	
	@Transactional
	public ProductPojo fetchProduct(String barcode) throws ApiException
	{
		ProductPojo p=dao.selectBarcode(barcode);
		if(p==null)
		{
			throw new ApiException("Given barcode doesnot exist");
		}
		return(p);
		
	}
	
	@Transactional
	public ProductPojo getByName(String name) throws ApiException
	{
		ProductPojo p=dao.selectName(name);
		return(p);
		
	}
	
}
