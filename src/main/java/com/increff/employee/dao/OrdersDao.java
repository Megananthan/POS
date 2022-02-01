package com.increff.employee.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrdersPojo;

@Repository
public class OrdersDao extends AbstractDao {

	private static String delete_id = "delete from OrdersPojo p where id=:id";
	private static String select_id = "select p from OrdersPojo p where id=:id";
	private static String select_time = "select p from OrdersPojo p where time=:time";

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(OrdersPojo p) {
//		System.out.println("Inside dao");

//		System.out.println(p.getTime());

		em.persist(p);
//		System.out.println(p.getTime());
//		System.out.println("finished dao");

	}

	public int delete(int id) {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public OrdersPojo select(int id) {
		TypedQuery<OrdersPojo> query = getQuery(select_id, OrdersPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	public OrdersPojo selectTime(String time) {
		TypedQuery<OrdersPojo> query = getQuery(select_time, OrdersPojo.class);
		query.setParameter("time", time);
		return getSingle(query);
	}

}
