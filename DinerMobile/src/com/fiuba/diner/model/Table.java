package com.fiuba.diner.model;

import java.io.Serializable;

public class Table implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private String state;

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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
