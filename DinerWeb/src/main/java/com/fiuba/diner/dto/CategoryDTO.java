package com.fiuba.diner.dto;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private boolean active;

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

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
