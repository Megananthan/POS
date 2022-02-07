package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

	private static String delete_id = "delete from ProductPojo p where id=:id";
	private static String select_id = "select p from ProductPojo p where id=:id";
	private static String select_all = "select p from ProductPojo p";
	private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
	private static String select_name = "select p from ProductPojo p where name=:name";
	private static String select_brand_category = "select p from ProductPojo p where brand_category=:brand_category";

	@Transactional
	public void insert(ProductPojo p) {
		em().persist(p);
	}

	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
		return query.getResultList();
	}
	
	public ProductPojo selectBarcode(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(select_barcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	
	public ProductPojo selectName(String name) {
		TypedQuery<ProductPojo> query = getQuery(select_name, ProductPojo.class);
		query.setParameter("name", name);
		return getSingle(query);
	}
	
	public List<ProductPojo> selectWithBrandID(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_brand_category, ProductPojo.class);
		query.setParameter("brand_category", id);
		return query.getResultList();
	}
	
	public void update(ProductPojo p) {
	}



}