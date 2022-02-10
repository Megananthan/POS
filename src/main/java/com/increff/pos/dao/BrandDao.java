package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

	private static String delete_id = "delete from BrandPojo b where id=:id";
	private static String select_id = "select b from BrandPojo b where id=:id";
	private static String select_all = "select b from BrandPojo b";
	private static String select_brand_category = "select b from BrandPojo b where brand=:brand and category=:category";

	@Transactional
	public void insert(BrandPojo b) {
		em().persist(b);
	}

	@Transactional
	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<String> selectAllBrand() {
		Query query = em().createNativeQuery("select distinct b.brand from brandpojo as b order by b.brand");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<String> selectAllCategory() {
		Query query = em().createNativeQuery("select distinct b.category from brandpojo as b order by b.category");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<String> selectCategoryWithBrand(String brand) {
		Query query = em().createNativeQuery("select distinct b.category from brandpojo as b where b.brand=:brand order by b.category");
		query.setParameter("brand", brand);
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public BrandPojo selectBrandCategory(String brand, String category) {
		TypedQuery<BrandPojo> query = getQuery(select_brand_category, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}

	@Transactional
	public void update(BrandPojo b) {
	}

}
