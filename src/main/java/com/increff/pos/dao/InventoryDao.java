package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

	private static String delete_id = "delete from InventoryPojo p where id=:id";
	private static String select_id = "select i from InventoryPojo i where id=:id";
	private static String select_all = "select i from InventoryPojo i";
    
	@Transactional
	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	
	@Transactional
	public void insert(InventoryPojo i) {
		em().persist(i);
	}

	@Transactional(readOnly = true)
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
		return query.getResultList();
	}

	@Transactional
	public void update(InventoryPojo b) {
	}
	
}
