package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao {

	private static String delete_id = "delete from OrderItemPojo p where id=:id";
	private static String select_id = "select p from OrderItemPojo p where id=:id";
	private static String select_all = "select p from OrderItemPojo p";
	
	
	@Transactional
	public void insert(OrderItemPojo p) {
		em().persist(p);
	}

	@Transactional
	public int delete(int id) {
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	@Transactional(readOnly = true)
	public List<OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Integer> selectWithRange(String start,String end,String brand,String category) {
		
		Query query= em().createNativeQuery("select a.id from orderitempojo as a inner join (select c.id from productpojo as c inner join brandpojo as d on c.brand_category=d.id where d.brand like :brand and d.category like :category) as b on a.productId=b.id "
				+ "  inner join orderspojo as c on a.ordersId=c.id where c.time between :start and :end");
  
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		
		return query.getResultList();
	}

}
