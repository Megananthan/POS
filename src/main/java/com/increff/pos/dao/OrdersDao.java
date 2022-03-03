package com.increff.pos.dao;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.OrdersPojo;

@Repository
public class OrdersDao extends AbstractDao {

	private static String SELECT_ID = "select p from OrdersPojo p where id=:id";
	private static String SELECT_TIME = "select p from OrdersPojo p where time=:time";

	@Transactional
	public void insert(OrdersPojo p) {
		em().persist(p);
	}

	@Transactional(readOnly = true)
	public OrdersPojo select(int id) {
		TypedQuery<OrdersPojo> query = getQuery(SELECT_ID, OrdersPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	@Transactional(readOnly = true)
	public OrdersPojo selectTime(String time) {
		TypedQuery<OrdersPojo> query = getQuery(SELECT_TIME, OrdersPojo.class);
		query.setParameter("time", time);
		return getSingle(query);
	}

}
