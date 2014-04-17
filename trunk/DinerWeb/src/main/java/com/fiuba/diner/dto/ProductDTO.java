package com.fiuba.diner.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String description;
	private Integer subcategoryId;
	private Double price;
	private boolean active;
	private boolean celiacAllowed;
	private Integer oldSubcategoryId;

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

	public Integer getSubcategoryId() {
		return this.subcategoryId;
	}

	public void setSubcategoryId(Integer subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getOldSubcategoryId() {
		return this.oldSubcategoryId;
	}

	public void setOldSubcategoryId(Integer oldSubcategoryId) {
		this.oldSubcategoryId = oldSubcategoryId;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isCeliacAllowed() {
		return this.celiacAllowed;
	}

	public void setCeliacAllowed(boolean celiacAllowed) {
		this.celiacAllowed = celiacAllowed;
	}

}
