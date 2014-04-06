package com.fiuba.diner.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Double price;
	private Boolean active;

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

}
