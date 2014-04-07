package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fiuba.diner.adapters.expandablelist.OrderProductDetail;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Double price;
	private Boolean active;

	private List<OrderProductDetail> details;

	public Product() {
		this.details = new ArrayList<OrderProductDetail>();
		OrderProductDetail detail = new OrderProductDetail();
		detail.setAmount(1);
		this.details.add(detail);
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<OrderProductDetail> getDetails() {
		return this.details;
	}

	public void setDetails(List<OrderProductDetail> details) {
		this.details = details;
	}

}
