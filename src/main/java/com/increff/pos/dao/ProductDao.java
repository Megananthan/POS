package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

	private static String SELECT_ID = "select p from ProductPojo p where id=:id";
	private static String SELECT_ALL = "select p from ProductPojo p";
	private static String SELECT_BARCODE = "select p from ProductPojo p where barcode=:barcode";
	private static String SELECT_NAME = "select p from ProductPojo p where name=:name";

	@Transactional
	public void insert(ProductPojo p) {
		em().persist(p);
	}
	
	@Transactional(readOnly = true)
	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public ProductPojo selectBarcode(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	
	@Transactional(readOnly = true)
	public ProductPojo selectName(String name) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_NAME, ProductPojo.class);
		query.setParameter("name", name);
		return getSingle(query);
	}

	@Transactional
	public void update(ProductPojo p) {
	}



}
