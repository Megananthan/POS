package com.increff.pos.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "orderitem_id_seq", initialValue = 100001)
public class OrderItemPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_id_seq")
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private int ordersId;

	@Column(nullable = false)
	private int productId;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private double sellingPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	



	
	

}
