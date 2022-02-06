package com.increff.pos.pojo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "orders_id_seq", initialValue = 1001)
public class OrdersPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
	private int id;
	
	@Column(nullable = false)
	private LocalDateTime time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTime() {
		
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
