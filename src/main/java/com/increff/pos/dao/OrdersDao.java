package com.increff.pos.dao;

import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.OrdersPojo;

@Repository
public class OrdersDao extends AbstractDao {

	private static String delete_id = "delete from OrdersPojo p where id=:id";
	private static String select_id = "select p from OrdersPojo p where id=:id";
	private static String select_time = "select p from OrdersPojo p where time=:time";


	@Transactional
	public void insert(OrdersPojo p) {
		em().persist(p);
	}

	@Transactional
	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public OrdersPojo select(int id) {
		TypedQuery<OrdersPojo> query = getQuery(select_id, OrdersPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	@Transactional(readOnly = true)
	public OrdersPojo selectTime(String time) {
		TypedQuery<OrdersPojo> query = getQuery(select_time, OrdersPojo.class);
		query.setParameter("time", time);
		return getSingle(query);
	}

}
