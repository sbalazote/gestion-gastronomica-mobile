package com.fiuba.diner.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Boolean active;
	private List<Subcategory> subcategories;

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

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Subcategory> getSubcategories() {
		return this.subcategories;
	}

	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

}
