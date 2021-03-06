package com.increff.pos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderDetail {

	private int orderId;
	
	private double total;
	
	@XmlElement(name="time")
	private String time;
	
	private List<ItemForm> item;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
	    String formattedDate = time.format(myFormatObj);  
		this.time = formattedDate;
	}

	public List<ItemForm> getItem() {
		return item;
	}

	public void setItem(List<ItemForm> item) {
		this.item = item;
	}
	
}
