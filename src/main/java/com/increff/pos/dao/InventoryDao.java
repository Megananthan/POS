package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

	private static String SELECT_ID = "select i from InventoryPojo i where id=:id";
	private static String SELECT_ALL = "select i from InventoryPojo i";
	
	@Transactional
	public void insert(InventoryPojo i) {
		em().persist(i);
	}

	@Transactional(readOnly = true)
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(SELECT_ID, InventoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
		return query.getResultList();
	}

	@Transactional
	public void update(InventoryPojo b) {
	}
}
