package com.increff.pos.model;

import java.time.LocalDateTime;

public class OrdersData{

	private int id;
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
