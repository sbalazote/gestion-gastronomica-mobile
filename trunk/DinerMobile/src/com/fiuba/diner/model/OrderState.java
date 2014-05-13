package com.fiuba.diner.model;

import java.io.Serializable;

public class OrderState implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;

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

}
